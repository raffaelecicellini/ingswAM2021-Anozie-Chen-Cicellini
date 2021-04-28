package it.polimi.ingsw.server;

import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the server used for the game.
 */
public class Server{
    /**
     * Server Port.
     */
    private int port;

    /**
     * Server socket.
     */
    private ServerSocket socket;

    /**
     * List of connected that successfully connected to the server.
     */
    private List<String> connectedClients;

    /**
     * List of active multiplayer games.
     */
    private List<GameHandler> games;

    /**
     * List of clients that are waiting for a multiplayer game to start.
     */
    private List<ClientHandler> waitingClients;

    /**
     * This method sets the server port.
     * @param port is the new server port.
     */
    public void setPort(int port){
        this.port=port;
        connectedClients = new ArrayList<>();
        games = new ArrayList<>();
        waitingClients = new ArrayList<>();
    }

    /**
     * This method starts the server.
     */
    public void start(){
        ExecutorService executor= Executors.newCachedThreadPool();
        try{
            socket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println("Failed to initialize server on port "+port+". Application will now close...");
            System.exit(0);
        }
        System.out.println("Server socket ready on port: " + port);
        while (true) {
            try {
                Socket client = socket.accept();
                executor.submit(new ClientHandler(client,this));
            } catch(IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    /**
     * This method is used when a client disconnects from the server.
     * @param client is the disconnected client.
     */
    public synchronized void manageDisconnection(ClientHandler client) {
        connectedClients.remove(client.getName());
        waitingClients.remove(client);

        if (client.getGame().isStarted()) {
            client.getGame().manageDisconnection(client.getName());
            for (ClientHandler x : client.getGame().getPlayers()) {
                connectedClients.remove(x.getName());
            }
            games.remove(client.getGame());
        } else client.getGame().removePlayer(client.getName());
    }

    //for endgame

    /**
     * This method removes a client from the server. It is mainly used when a game ends.
     * @param client is the client to remove.
     */
    public synchronized void removeClient(ClientHandler client) {
        connectedClients.remove(client.getName());
        games.remove(client.getGame());
    }

    /**
     * This method is used to add a waiting client to a game.
     * @param client is the client that will be added to a game.
     */
    public synchronized void addToGame(ClientHandler client){
        if (client.getPrefNumber() == 1) {
            GameHandler game = new GameHandler(1);
            client.setGame(game);
            game.setPlayer(client.getName(), client);
            System.out.println("Starting singleplayer game for "+client.getName());
            client.getGame().start();
        } else {
            waitingClients.add(client);
            if (waitingClients.size() == 1) {
                GameHandler game = new GameHandler(client.getPrefNumber());
                client.setGame(game);
                game.setPlayer(client.getName(), client);
                games.add(game);
                System.out.println("created new multiplayer game for "+client.getName());
            } else if (waitingClients.size() < games.get(games.size() - 1).getPlayersNumber()){
                client.setGame(games.get(games.size() - 1));
                waitingClients.get(0).getGame().setPlayer(client.getName(), client);
                System.out.println("Added player "+client.getName()+" to the current game");
            }
            else if (waitingClients.size() == games.get(games.size() - 1).getPlayersNumber()) {
                client.setGame(games.get(games.size() - 1));
                waitingClients.get(0).getGame().setPlayer(client.getName(), client);
                System.out.println("Last player connected, starting...");
                waitingClients.get(0).getGame().start();
                waitingClients.clear();
            }
        }
    }

    /**
     * This method is used to check if an username is already stored in the server.
     * @param name is the name that will be checked.
     * @return if the name is stored in the server.
     */
    public synchronized boolean checkName(String name){
        if (name !=null && !connectedClients.contains(name)) {
            this.connectedClients.add(name);
            return false;
        }
        return true;
    }

    /**
     * Main method. used if you want to start the application as a server.
     * @param args array of Strings passed as parameters when you run the application through command line.
     */
    public static void main(String[] args) {
        System.out.println("Master of Renaissance server | Welcome!");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the port which server will listen on.");
        System.out.print(">");
        int port = 0;
        try {
            port = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
        if (port < 1024) {
            System.err.println("Error: ports accepted start from 1024! Please insert a new value.");
            main(null);
        }
        Server server=new Server();
        server.setPort(port);
        System.out.println("Starting server...");
        server.start();
    }
}
