package it.polimi.ingsw.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final Server server;
    private GameHandler game;
    private String name;
    private int prefNumber;
    private BufferedReader input;
    private OutputStreamWriter output;
    private boolean active;
    private boolean doneSetup;

    public synchronized void setup(Map <String, String> message) {
        if (!doneSetup) {
            this.prefNumber = Integer.parseInt(message.get("number"));
            if (this.prefNumber <= 0 || prefNumber > 4) {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("content", "Wrong number of players, it must be between 1 and 4. Pick a new one and connect again.");
                send(new Gson().toJson(error));
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

    public synchronized boolean isActive() {
        return active;
    }

    public void actionHandler(Map<String,String> message) {
        String action = message.get("action");
        switch (action.toLowerCase()) {
            case "setup": setup(message);
            case "disconnect": server.removeClient(this);
            case "buy" : makeAction(message);
            case "produce": makeAction(message);
            case "swap": makeAction(message);
            case "endturn": makeAction(message);
            case "activate": makeAction(message);
            case "discard": makeAction(message);
            case "chooseresources": makeAction(message);
            case "chooseleaders": makeAction(message);
            case "market": makeAction(message);
        }
    }

    public void makeAction(Map<String,String> message) {
        if (game.isStarted()) {
            if (game.getModel().getCurrentPlayer().getName().equals(name))
                game.makeAction(message,name);
            else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("content", "It is not your turn.");
                send(new Gson().toJson(error));
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("content", "The game has not started yet.");
            send(new Gson().toJson(error));
        }
    }

    public void setGame(GameHandler game) {
        this.game = game;
    }


    public void send(String message) {
        try {
            output.write(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send the message.");
            System.out.println(e.getMessage());
        }
    }

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
    }

    public int getPrefNumber() {
        return prefNumber;
    }

    public String getName() {
        return name;
    }

    public GameHandler getGame() {
        return game;
    }

    public void readMessage() throws IOException{
        Gson gson = new Gson();
        String line = input.readLine();
        Map<String,String > message;
        message = gson.fromJson(line, new TypeToken<Map<String,String>>(){}.getType());
        if (message != null)
            actionHandler(message);
    }

    public synchronized void close() {
        active = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (isActive()) {
            try {
                readMessage();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                if (name != null)
                    server.removeClient(this);
            }
        }

    }

}
