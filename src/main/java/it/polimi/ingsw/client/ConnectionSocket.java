package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ConnectionSocket {
    private final String serverAddress;
    private final int serverPort;
    private OutputStreamWriter output;
    private Socket socket;
    private SocketListener socketListener;


    public ConnectionSocket(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
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

    public void close() {
        socketListener.setActive(false);
        try {
            socket.close();
            System.out.println("The connection to the server has ended.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean setup(String message, PropertyChangeListener answerHandler) {
        BufferedReader input;
        Gson gson = new Gson();
        Map<String, String> answer = new HashMap<>();

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
            output.write(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("Failed to send the message.");
            System.out.println(e.getMessage());
            return false;
        }

        try {
            String line = input.readLine();
            answer = gson.fromJson(line, new TypeToken<Map<String, String>>() {}.getType());
        } catch (IOException e) {
            System.out.println("Couldn't read the message from socket.");
        }

        if (answer.get("action").equals("error")) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close the socket");
            }finally {
                return false;
            }
        }

        socketListener = new SocketListener(socket, input, answerHandler);
        Thread socketListenerThread = new Thread(socketListener);
        socketListenerThread.start();
        return true;
    }
}
