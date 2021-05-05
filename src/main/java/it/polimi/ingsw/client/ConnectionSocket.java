package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.notifications.SourceListener;
import it.polimi.ingsw.server.ClientPing;

import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a ConnectionSocket, a way to connect to the server.
 */
public class ConnectionSocket {

    /**
     * Server's ip address.
     */
    private final String serverAddress;

    /**
     * Server's port.
     */
    private final int serverPort;

    /**
     * Output stream.
     */
    private OutputStreamWriter output;

    /**
     * Socket used for the communication.
     */
    private Socket socket;

    /**
     * Thread instantiated only for receiving messages from the server.
     */
    private SocketListener socketListener;

    /**
     * Constructor ConnectionSocket create a new ConnectionSocket instance.
     * @param serverAddress is the server's ip address.
     * @param serverPort is the server's port.
     */
    public ConnectionSocket(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * This method sends a message to the server.
     * @param message is the message that will be sent to the server.
     */
    public synchronized void send(String message) {
        try {
            output.write(message+"\n");
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send the message.");
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method closes the socket and terminates the SocketListener thread.
     */
    public void close() {
        socketListener.setActive(false);
        try {
            socket.close();
            System.out.println("The connection to the server has ended.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is called when a client wants connect to the server.
     * @param message is the message containing the client's setup message.
     * @param answerHandler is the answerHandler that will be given to the SocketListener.
     * @return if the client successfully connected to the server
     */
    public boolean setup(String message, SourceListener answerHandler) {
        BufferedReader input;
        Gson gson = new Gson();
        Map<String, String> answer;

        try {
            socket = new Socket(serverAddress, serverPort);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
            return false;
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
            return false;
        }
        try {
            output = new OutputStreamWriter(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Couldn't initialize the Client");
            return false;
        }

        try {
            //10000 is the timeout
            socket.setSoTimeout(10000);
        }catch (SocketException e) {
            System.err.println(e.getMessage());
        }

        try {
            output.write(message+"\n");
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send the message.");
            System.out.println(e.getMessage());
            return false;
        }

        boolean exit = false;
        while (!exit) {
            try {
                String line = input.readLine();
                answer = gson.fromJson(line, new TypeToken<Map<String, String>>() {
                }.getType());
                if (!answer.get("action").equalsIgnoreCase("ping"))
                    exit = true;
            } catch (SocketTimeoutException e) {
                System.out.println("Connection timeout");
                return false;
            } catch (IOException e) {
                System.out.println("Couldn't read the message from socket.");
                return false;
            }
        }

        if (answer.get("action").equalsIgnoreCase("error")) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close the socket");
            }finally {
                System.err.println(answer.get("content"));
                return false;
            }
        }
        System.out.println(answer.get("content"));
        socketListener = new SocketListener(socket, input, answerHandler);
        Thread socketListenerThread = new Thread(socketListener);
        socketListenerThread.start();
        serverPingInitiate();
        return true;
    }

    /**
     * This method initiates a serverPing.
     */
    private void serverPingInitiate() {
        ServerPing ping = new ServerPing(this);
        Thread serverPing = new Thread(ping);
        serverPing.start();
    }

    /**
     * This method checks if the connection is active.
     */

    public boolean isActive() {
        return socketListener.isActive();
    }
}
