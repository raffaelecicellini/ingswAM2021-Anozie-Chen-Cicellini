package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * This class represents a SocketListener, a thread that will just read from the Input stream and update the listener.
 */
public class SocketListener implements Runnable{

    /**
     * Is the socket.
     */
    private final Socket socket;

    /**
     * Is the input stream
     */
    private final BufferedReader input;

    /**
     * Is the thread's state.
     */
    private boolean active;

    /**
     * Is the thread's listener.
     */
    private final PropertyChangeSupport listener = new PropertyChangeSupport(this);

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     * @param socket is the client's socket.
     * @param input is the input stream.
     * @param answerHandler is the AnswerHandler.
     */
    public SocketListener(Socket socket, BufferedReader input, PropertyChangeListener answerHandler) {
        this.socket = socket;
        this.input = input;
        listener.addPropertyChangeListener(answerHandler);
        active = true;
    }

    /**
     * This method returns the state of the thread.
     * @return the state of the thread.
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * This method reads a message from the socket.
     * @throws IOException if the connection is interrupted.
     */
    public void readMessage() throws IOException{
        Gson gson = new Gson();
        String line = input.readLine();
        Map<String,String > message;
        message = gson.fromJson(line, new TypeToken<Map<String,String>>(){}.getType());
        if (message != null)
            if (!message.get("action").equalsIgnoreCase("ping"))
                actionHandler(message);
    }

    /**
     * This method updates the listener with the message received from the server.
     * @param message is the message received from the server.
     */
    public void actionHandler(Map<String,String> message) {
        String action = message.get("action");
        listener.firePropertyChange(message.get("action"),null,message);
        if (action.equalsIgnoreCase("end") || action.equalsIgnoreCase("endgame"))
            close();
    }

    /**
     * This method closes the socket and interrupts the thread.
     */
    public synchronized void close() {
        active = false;
        try {
            socket.close();
            System.out.println("The connection to the server has ended.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method sets the state of the thread.
     * @param active is the new state of the thread.
     */
    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Thread run method that will run as long as the thread is active.
     */
    @Override
    public void run() {
        try {
            while (isActive()) {
                readMessage();
            }
        } catch (SocketTimeoutException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

