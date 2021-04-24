package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketListener implements Runnable{
    private Socket socket;
    private BufferedReader input;

    public SocketListener(Socket socket, InputStream inputStream) throws IOException{
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }

    @Override
    public void run() {

    }
}
