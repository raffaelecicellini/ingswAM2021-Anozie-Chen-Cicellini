package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    private int port;
    private ServerSocket socket;
    private ExecutorService executor;
    private List<String> connectedClients;
    private List<GameHandler> games;
    private List<ClientHandler> waitingClients;

    public void setPort(int port){
        this.port=port;
    }

    public void start(){
        try{
            socket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println("Failed to initialize server on port "+port+". Application will now close...");
            System.exit(0);
        }
        System.out.println("Server socket ready on port: " + port);

        executor= Executors.newCachedThreadPool();
        while (true) {
            try {
                Socket client = socket.accept();
                executor.submit(new ClientHandler(client));
            } catch(IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    public synchronized void addToGame(ClientHandler client, int prefNumber){

    }

    public synchronized boolean checkName(String name){
        return false;
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
