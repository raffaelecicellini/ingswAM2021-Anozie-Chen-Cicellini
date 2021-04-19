package it.polimi.ingsw.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private int port;
    private Socket socket;
    private GameHandler game;
    private String name;
    private InputStream input;
    private OutputStream output;
    private boolean active;

    public boolean setup() {
        return false;
    }

    public synchronized boolean  isActive() {
        return false;
    }

    public void insertUsername() {
    }

    public void selectGameType() {
    }

    public int selectPlayersNumber() {
        return 0;
    }

    public void actionHandler(String message) {
    }

    public void setGame(GameHandler game) {
    }
    public void send (String message) {
    }

    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        
    }
}
