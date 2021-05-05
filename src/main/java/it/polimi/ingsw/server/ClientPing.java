package it.polimi.ingsw.server;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a ClientPing. It is a thread instantiated by the server when a clients connects to it.
 * It will be in charge of sending ping messages to the client.
 */
public class ClientPing implements Runnable{

    /**
     * The ClientHandler
     */
    private ClientHandler clientHandler;

    /**
     * Constructor ClientPing creates a new ClientPing instance.
     * @param clientHandler is the ClientHandler.
     */
    public ClientPing(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     *This method sends a ping message to the client.
     */
    private void pingClient() {
        Map<String,String> ping = new HashMap<>();
        ping.put("action","ping");
        clientHandler.send(new Gson().toJson(ping));
    }

    /**
     * Thread run method. It will just send a ping message to the client every 5 seconds (timeout/2).
     */
    @Override
    public void run() {
        do {
            try {
                //5000 is timeout/2
                Thread.currentThread().sleep(5000);
                pingClient();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }while (clientHandler.isActive());
        Thread.currentThread().interrupt();
    }
}

