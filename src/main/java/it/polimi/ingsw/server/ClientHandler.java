package it.polimi.ingsw.server;

import java.net.Socket;

public class ClientHandler implements Runnable{
    private int port;
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
        
    }
}
