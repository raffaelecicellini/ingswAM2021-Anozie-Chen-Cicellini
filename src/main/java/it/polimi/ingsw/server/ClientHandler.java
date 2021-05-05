package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a ClientHandler. It is a thread instantiated by the server when a clients connects to it.
 * It will be in charge of exchanging messages with the client.
 */
public class ClientHandler implements Runnable{

    /**
     * The socket.
     */
    private final Socket socket;

    /**
     * The server.
     */
    private final Server server;

    /**
     * The client's game.
     */
    private GameHandler game;

    /**
     * The client's name.
     */
    private String name;

    /**
     * The client's preferred number of players.
     */
    private int prefNumber;

    /**
     * Input stream.
     */
    private BufferedReader input;

    /**
     * Output stream.
     */
    private OutputStreamWriter output;

    /**
     * The state of the ClientHandler
     */
    private boolean active;

    /**
     * Registers if the client has already done a setup action.
     */
    private boolean doneSetup;

    /**
     * This method is called when a client sends a setup message. It will check if the client username is available and
     * preferred number of players. If the infos sent by the client are "ok" it will add the username to the list and
     * assigns the client a game.
     * @param message is a map which represents the client's message.
     */
    public synchronized void setup(Map <String, String> message) {
        if (!doneSetup) {
            this.prefNumber = Integer.parseInt(message.get("number"));
            if (this.prefNumber <= 0 || prefNumber > 4) {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("content", "Wrong number of players, it must be between 1 and 4. Pick a new one and connect again.");
                send(new Gson().toJson(error));
                close();
                active = false;
                return;
            }
            if (server.checkName(message.get("username"))) {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("content", "Duplicate username. Pick a new name and connect again.");
                send(new Gson().toJson(error));
                active = false;
                return;
            }
            this.name = message.get("username");
            server.addToGame(this);
            doneSetup = true;
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("content", "You are already connected.");
            send(new Gson().toJson(error));
        }
    }

    /**
     * This method initiates the ClientPing and sets a timeout on the socket.
     */
    private void clientPingInitiate() {
        try {
            //10000 is the timeout
            socket.setSoTimeout(10000);
        }catch (SocketException e) {
            System.err.println(e.getMessage());
        }
        ClientPing ping = new ClientPing(this);
        Thread clientPing = new Thread(ping);
        clientPing.start();
    }

    /**
     * This method returns if the ClientHandler is active.
     * @return if the ClientHandler is active.
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * This method is used when the ClientHandler receives a message from the client, in order to call the right method.
     * @param message is the message received from the client.
     */
    public void actionHandler(Map<String,String> message) {
        String action = message.get("action");
        switch (action.toLowerCase()) {
            case "setup":
                setup(message);
                break;
            case "disconnect":
                if (game!=null) server.manageDisconnection(this);
                close();
                break;
            case "buy" :
                makeAction(message);
                break;
            case "produce":
                makeAction(message);
                break;
            case "swap":
                makeAction(message);
                break;
            case "endturn":
                makeAction(message);
                break;
            case "activate":
                makeAction(message);
                break;
            case "discard":
                makeAction(message);
                break;
            case "chooseresources":
                makeAction(message);
                break;
            case "chooseleaders":
                makeAction(message);
                break;
            case "market":
                makeAction(message);
                break;
            case "ping":
                break;
            default:
                Map<String, String> error= new HashMap<>();
                error.put("action", "error");
                error.put("content", "Unrecognized command, please try again");
                Gson gson= new Gson();
                String back=gson.toJson(error);
                send(back);
        }
    }

    /**
     * This method is used when a client sends a move message to the server. The message will be then sent to the
     * GameHandler.
     * @param message is the message received from the client.
     */
    public void makeAction(Map<String,String> message) {
        if (game!=null){
            game.makeAction(message,name);
        }
        else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("content", "You didn't do the setup yet.");
            send(new Gson().toJson(error));
        }
    }

    /**
     * This method assigns a game to the client.
     * @param game is the game assigned to the client.
     */
    public void setGame(GameHandler game) {
        this.game = game;
    }


    /**
     * This method sends a message to the client.
     * @param message is the message that will be sent to the client.
     */
    public synchronized void send(String message) {
        try {
            output.write(message+"\n");
            output.flush();
        } catch (IOException e) {
            if (game!=null){
                System.out.println("Error in send");
                server.manageDisconnection(this);
            }
            System.out.println("Failed to send the message.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructor ClientHandler creates a new ClientHandler instance.
     * @param socket is the socket used for exchanging messages.
     * @param server is the ClientHandler's server.
     */
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            active = true;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Couldn't initialize the ClientHandler");
            System.out.println(e.getMessage());
        }
        doneSetup=false;
    }

    /**
     * This method returns the client's preferred number of players.
     * @return the client's preferred number of players.
     */
    public int getPrefNumber() {
        return prefNumber;
    }

    /**
     * This method returns the client's name.
     * @return the client's name.
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the client's game.
     * @return the client's game.
     */
    public GameHandler getGame() {
        return game;
    }

    /**
     * This method reads a message from socket.
     * @throws IOException if the connection was interrupted.
     */
    public void readMessage() throws IOException{
        Gson gson = new Gson();
        String line = input.readLine();
        Map<String,String > message;
        message = gson.fromJson(line, new TypeToken<Map<String,String>>(){}.getType());
        if (message != null)
            actionHandler(message);
    }

    /**
     * This method closes the socket.
     */
    public synchronized void close() {
        active = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //for endgame

    /**
     * This method removes the client from the server's list.
     */
    public void removeClient() {
        server.removeClient(this);
    }

    /**
     * Thread run method that will run as long as the thread is active.
     */
    @Override
    public void run() {
        clientPingInitiate();
        try {
            while (isActive()) {
                readMessage();
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Connection Timeout. The client has disconnected.");
            if (name != null && game != null) server.manageDisconnection(this);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
            if (name != null && game != null) server.manageDisconnection(this);
        }
    }
}
