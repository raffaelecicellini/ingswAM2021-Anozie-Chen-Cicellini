package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class SocketListener implements Runnable{
    private final Socket socket;
    private final BufferedReader input;
    private boolean active;
    private final PropertyChangeSupport listener= new PropertyChangeSupport(this);

    public SocketListener(Socket socket, BufferedReader input, PropertyChangeListener answerHandler) {
        this.socket = socket;
        this.input = input;
        listener.addPropertyChangeListener(answerHandler);
        active = true;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public void readMessage() throws IOException{
        Gson gson = new Gson();
        String line = input.readLine();
        Map<String,String > message;
        message = gson.fromJson(line, new TypeToken<Map<String,String>>(){}.getType());
        if (message != null)
            actionHandler(message);
    }

    public void actionHandler(Map<String,String> message) {
        String action = message.get("action");
        listener.firePropertyChange(message.get("action"),null,message);
        if (action.toLowerCase().equals("end") || action.toLowerCase().equals("endgame"))
            close();
    }

    public synchronized void close() {
        active = false;
        try {
            socket.close();
            System.out.println("The connection to the server has ended.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        try {
            while (isActive()) {
                readMessage();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

