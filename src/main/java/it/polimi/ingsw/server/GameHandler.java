package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SoloGame;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameHandler manages a single match, notifying the controller about the actions that the clients want to do and
 * receiving the change events of the model and the controller
 */
public class GameHandler implements SourceListener {

    /**
     * It is the Controller of the current game
     */
    private final Controller controller;
    /**
     * It is the model of the current game
     */
    private final Game model;
    /**
     * @see PropertyChangeSupport
     */
    private final Source controllerListener = new Source();
    /**
     * This attribute is used to save the ClientHandler of each player for the current game based on their names
     */
    private Map<String, ClientHandler> players;
    /**
     * It is the number of players of the current game
     */
    private int playersNumber;

    /**
     * Constructor of the GameHandler. Based on the value of the parameter, it instantiates a SoloGame or a MultiGame
     *
     * @param playersNumber the number of players for the current match
     */
    public GameHandler(int playersNumber) {
        this.playersNumber = playersNumber;
        if (playersNumber == 1) this.model = new SoloGame();
        else this.model = new Game();
        this.model.setListener(this);
        this.controller = new Controller(this.model, this);
        //this.controller.setListener(this);
        this.players = new HashMap<>();
        controllerListener.addListener(this.controller);
    }

    /**
     * playersNumber getter method
     *
     * @return the number of players of the current match
     */
    public int getPlayersNumber() {
        return this.playersNumber;
    }

    /**
     * This method is called by the Server when the number of players for the match has been reached. It starts the game
     */
    public void start() {
        //notify clients that the game is starting
        Map<String, String> message = new HashMap<>();
        message.put("action", "start");
        String content = "The game is starting! Have fun!";
        message.put("content", content);
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(message);
        System.out.println(content);
        sendAll(jsonMessage);

        //start the game
        controllerListener.fireUpdates("start", null);
    }

    /**
     * Method used to send a socket message to a single client
     *
     * @param message the serialized message that is to be sent
     * @param client  the name of the client that has to receive the message
     */
    public void sendSingle(String message, String client) {
        players.get(client).send(message);
    }

    /**
     * Method used to send a socket message to all the players of the game
     *
     * @param message the serialized message that is to be sent
     */
    public void sendAll(String message) {
        for (Player player : model.getActivePlayers()) {
            players.get(player.getName()).send(message);
        }
    }

    /**
     * Method used to send a socket message to all the players but one
     *
     * @param message the serialized message that is to be sent
     * @param client  the client that must not receive the message
     */
    public void sendAllExcept(String message, String client) {
        for (Player player : model.getActivePlayers()) {
            if (!player.getName().equals(client)) {
                players.get(player.getName()).send(message);
            }
        }
    }

    /**
     * Method called by a ClientHandler when a client wants to do an action
     *
     * @param message the message sent by the client: it contains all the info needed to perform the action
     * @param player  the name of the player that sent the message
     */
    public synchronized void makeAction(Message message, String player) {
        System.out.println("I am in gameHandler.makeAction. Action: " + message.getAction() + "; Player: " + message.getPlayer());
        //controls if the move can be done
        if (model.getPhase() != GamePhase.NOTSTARTED) {
            if (model.getCurrentPlayer().getName().equalsIgnoreCase(player)) {
                System.out.println("Passing action to Controller...");
                System.out.println("Player: " + player + ", Action: " + message.getAction());
                controllerListener.fireUpdates(message.getAction(), message);
                System.out.println(message);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("action", "error");
                map.put("content", "It is not your turn!");
                Gson gson = new Gson();
                String error = gson.toJson(map);
                sendSingle(error, player);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("action", "error");
            map.put("content", "Game has not started yet!");
            Gson gson = new Gson();
            String error = gson.toJson(map);
            sendSingle(error, player);
        }
    }

    /**
     * Method used to add a player to the game
     *
     * @param name   the name of the player
     * @param client the ClientHandler of the player
     */
    public void setPlayer(String name, ClientHandler client) {
        this.players.put(name, client);
        this.model.createPlayer(name);

        Map<String, String> map = new HashMap<>();
        map.put("action", "otherConnected");
        map.put("content", "Someone connected! " + (playersNumber - players.size()) + " slots left.");
        Gson gson = new Gson();
        String message = gson.toJson(map);
        sendAllExcept(message, name);

        map.clear();
        map.put("action", "connected");
        map.put("content", "Connection established");
        message = gson.toJson(map);
        System.out.println("Added a player to the current game, gameHandler.setPlayer");
        sendSingle(message, name);
    }

    /**
     * Method used to remove a player when the game has not started yet
     *
     * @param name the name of the player that disconnected from the game
     */
    public void removePlayer(String name) {
        this.players.remove(name);
        model.removePlayer(name);

        Map<String, String> map = new HashMap<>();
        map.put("action", "otherDisconnected");
        map.put("content", "Someone disconnected before the start of the game. " + (playersNumber - players.size()) + " slots left.");
        Gson gson = new Gson();
        String message = gson.toJson(map);
        System.out.println("Removing player " + name + " from the current game, gameHandler.removePlayer");
        sendAllExcept(message, name);
    }

    /**
     * Method used to remove a player when the game has started. It notifies all the other players that the game is going to end
     *
     * @param name the name of the disconnected player
     */
    public void manageDisconnection(String name) {
        Map<String, String> map = new HashMap<>();
        String content = "Player " + name + " has quit the game. Game is now ending...";
        map.put("action", "end");
        map.put("content", content);
        Gson gson = new Gson();
        String message = gson.toJson(map);
        System.out.println(content);
        sendAllExcept(message, name);
        //close connections of ClientHandlers
        for (ClientHandler client : players.values()) {
            if (!client.getName().equalsIgnoreCase(name)) client.close();
        }
    }

    /**
     * Simple method to get the model of the current game
     *
     * @return the model (Game class) of the current game
     */
    public Game getModel() {
        return model;
    }

    /**
     * Synchronized method used to check if the game has already started
     *
     * @return true if the game has started, false otherwise
     */
    public synchronized boolean isStarted() {
        return model.getPhase() != GamePhase.NOTSTARTED && model.getPhase() != GamePhase.ENDED;
    }

    /**
     * players getter method
     *
     * @return the list of ClientHandler of the current game
     */
    public List<ClientHandler> getPlayers() {
        return new ArrayList<ClientHandler>(players.values());
    }

    @Override
    public void update(String propertyName, Message message) {
        //switch for different kind of maps sent by model and controller
        Gson gson = new Gson();
        String jsonMessage;
        String addressee;
        String player = message.getPlayer() != null ? message.getPlayer() : message.getEndedPlayer();
        Map<String, String> value = message.getAll();
        System.out.println("I am in GameHandler, ready to send a message. Action: " + propertyName + "; Player: " + player);
        switch (propertyName.toUpperCase()) {
            case "STARTED":
                //sendall
                jsonMessage = gson.toJson(value);
                System.out.println("I am sending a started message, GameHandler");
                sendAll(jsonMessage);
                break;
            case "YOURTURN":
                addressee = message.getPlayer();
                jsonMessage = gson.toJson(value);
                sendSingle(jsonMessage, addressee);
                break;
            case "CHOOSELEADERS":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                System.out.println("I am sending a choose leaders message");
                break;
            case "OKLEADERS":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "CHOOSERESOURCES":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "OKRESOURCES":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "BUY":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "PRODUCE":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "MARKET":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "SWAP":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "ACTIVATE":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "DISCARD":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "ENDTURN":
                jsonMessage = gson.toJson(value);
                sendAll(jsonMessage);
                break;
            case "ENDGAME":
                //singlesend
                addressee = message.getPlayer();
                jsonMessage = gson.toJson(value);
                sendSingle(jsonMessage, addressee);

                //CLOSE CONNECTION
                players.get(addressee).removeClient();
                players.get(addressee).close();
                break;
            case "ERROR":
                //singlesend
                addressee = message.getPlayer();
                jsonMessage = gson.toJson(value);
                System.out.println(jsonMessage);
                sendSingle(jsonMessage, addressee);
                break;
            default:
                System.out.println("Urecognized command!");
                System.out.println(message.getAction());
                break;
        }
    }
}
