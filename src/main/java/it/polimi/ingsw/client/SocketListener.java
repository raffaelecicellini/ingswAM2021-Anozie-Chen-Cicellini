package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This class represents a SocketListener, a thread that will just read from the Input stream and update the listener.
 */
public class SocketListener implements Runnable {
    private final Logger logger = Logger.getLogger(getClass().getName());
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
    private final Source listener = new Source();

    /**
     * Constructor SocketListener creates a new SocketListener instance.
     *
     * @param socket        is the client's socket.
     * @param input         is the input stream.
     * @param answerHandler is the AnswerHandler.
     */
    public SocketListener(Socket socket, BufferedReader input, SourceListener answerHandler) {
        try {
            FileHandler fh = new FileHandler("%h/SocketListener.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.socket = socket;
        this.input = input;
        listener.addListener(answerHandler);
        active = true;
    }

    /**
     * This method returns the state of the thread.
     *
     * @return the state of the thread.
     */
    public synchronized boolean isActive() {
        return active;
    }

    /**
     * This method reads a message from the socket.
     *
     * @throws IOException if the connection is interrupted.
     */
    public void readMessage() throws SocketTimeoutException, IOException {
        Gson gson = new Gson();
        String line = input.readLine();
        logger.log(Level.INFO, line);
        Map<String, String> message;
        message = gson.fromJson(line, new TypeToken<Map<String, String>>() {
        }.getType());
        if (message != null)
            if (!message.get("action").equalsIgnoreCase("ping"))
                actionHandler(message);
    }

    /**
     * This method updates the listener with the message received from the server.
     *
     * @param map is the message received from the server.
     */
    public void actionHandler(Map<String, String> map) {
        String action = map.get("action");
        Message message = null;
        switch (action.toUpperCase()) {
            case "START":
                message = new StartedAnswer(map);
                break;
            case "OTHERCONNECTED":
                message = new OtherConnectedAnswer(map);
                break;
            case "STARTED":
                message = new StartedAnswer(map);
                break;
            case "CHOOSELEADERS":
                message = new ChooseLeadersAnswer(map);
                break;
            case "OKLEADERS":
                message = new OkLeadersAnswer(map);
                break;
            case "CHOOSERESOURCES":
                message = new ChooseResourcesAnswer(map);
                break;
            case "OKRESOURCES":
                message = new OkResourcesAnswer(map);
                break;
            case "YOURTURN":
                message = new YourTurnAnswer(map);
                break;
            case "BUY":
                message = new BuyAnswer(map);
                break;
            case "PRODUCE":
                message = new ProduceAnswer(map);
                break;
            case "MARKET":
                message = new MarketAnswer(map);
                break;
            case "SWAP":
                message = new SwapAnswer(map);
                break;
            case "ACTIVATE":
                message = new LeaderActionAnswer(map);
                break;
            case "DISCARD":
                message = new LeaderActionAnswer(map);
                break;
            case "ENDTURN":
                message = new EndTurnAnswer(map);
                break;
            case "ENDGAME":
                message = new EndGameAnswer(map);
                break;
            case "ERROR":
                message = new ErrorAnswer(map);
                break;
            case "END":
                message = new EndAnswer(map);
                break;
            case "OTHERDISCONNECTED":
                message = new OtherDisconnectedAnswer(map);
                break;
            default:
                message = new ErrorAnswer(map);
                break;
        }
        listener.fireUpdates(message.getAction(), message);
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
     *
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
            listener.fireUpdates("exception", null);
        } catch (IOException e) {
            listener.fireUpdates("exception", null);
        }
    }
}