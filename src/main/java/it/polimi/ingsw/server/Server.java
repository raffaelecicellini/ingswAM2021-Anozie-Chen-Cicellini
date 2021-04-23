package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    private int port;
    private ServerSocket socket;
    private List<String> connectedClients;
    private List<GameHandler> games;
    private List<ClientHandler> waitingClients;

    public void setPort(int port){
        this.port=port;
        connectedClients = new ArrayList<>();
        games = new ArrayList<>();
        waitingClients = new ArrayList<>();
    }

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

    //chiudere la partita
    public synchronized void removeClient(ClientHandler client) {
        connectedClients.remove(client.getName());
        waitingClients.remove(client);
        if (client.getGame() != null)
            if (client.getGame().isStarted()) {
                client.getGame().manageDisconnection(client.getName());
                for (ClientHandler x : client.getGame().getPlayers()) {
                    connectedClients.remove(x.getName());
                }
                games.remove(client.getGame());
            } else
                client.getGame().removePlayer(client);
    }

    public synchronized void addToGame(ClientHandler client){
        if (client.getPrefNumber() == 1) {
            GameHandler game = new GameHandler(1);
            client.setGame(game);
            game.setPlayer(client);
            games.add(game);
            client.getGame().start();
        } else {
            waitingClients.add(client);
            if (waitingClients.size() == 1) {
                GameHandler game = new GameHandler(client.getPrefNumber());
                client.setGame(game);
                game.setPlayer(client);
                games.add(game);
            } else {
                client.setGame(games.get(games.size() - 1));
                waitingClients.get(0).getGame().setPlayer(client);
            }
            if (waitingClients.size() == games.get(games.size() - 1).getPlayers().size()) {
                waitingClients.get(0).getGame().start();
                waitingClients.clear();
            }
        }
    }

    public synchronized boolean checkName(String name){
        if (!connectedClients.contains(name)) {
            this.connectedClients.add(name);
            return false;
        }
        return true;
    }

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
        if (port < 0 || (port > 0 && port < 1024)) {
            System.err.println("Error: ports accepted started from 1024! Please insert a new value.");
            main(null);
        }
        Server server=new Server();
        server.setPort(port);
        System.out.println("Starting server...");
        server.start();
    }
}
