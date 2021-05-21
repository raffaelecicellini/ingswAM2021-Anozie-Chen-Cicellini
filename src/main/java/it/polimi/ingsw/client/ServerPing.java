package it.polimi.ingsw.client;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a ServerPing. It is a thread instantiated by the client when it connects to the server.
 * It will be in charge of sending ping messages to the server.
 */
public class ServerPing implements Runnable{

    /**
     * Is the ConnectionSocket.
     */
    private ConnectionSocket connectionSocket;

    /**
     * Constructor ServerPing creates a new ServerPing instance.
     * @param connectionSocket is the ConnectionSocket.
     */
    public ServerPing(ConnectionSocket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    /**
     *This method sends a ping message to the server.
     */
    private void pingServer() {
        Map<String,String> ping = new HashMap<>();
        ping.put("action","ping");
        connectionSocket.send(new Gson().toJson(ping));
    }

    /**
     * Thread run method. It will just send a ping message to the server every 5 seconds (timeout/2).
     */
    @Override
    public void run() {
        do {
            try {
                //5000 is timeout/2
                Thread.sleep(10000);
                pingServer();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
        }while (connectionSocket.isActive());
            Thread.currentThread().interrupt();
        }

}
