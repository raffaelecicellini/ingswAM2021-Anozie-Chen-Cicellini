/*
package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionSocket {
    private String serverAddress;
    private int serverPort;
    private OutputStreamWriter output;
    private Socket socket;
    private SocketListener listener;


    public ConnectionSocket(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        try {
            socket = new Socket(serverAddress,serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
            System.exit(1);
        }
        try {
            output = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Couldn't initialize the Client");
        }

        try {
            //listener = new SocketListener(socket);
        } catch (IOException e) {
            System.out.println("Couldn't initialize the SocketListener");
            System.exit(1);
        }

        Thread socketlistener =new Thread(listener);
        socketlistener.start();
    }

    public void send (String message ) {
        try {
            output.write(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send the message.");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String []args) {

    }
}
*/