package it.polimi.ingsw.server;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SoloGame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * GameHandler manages a single match, notifying the controller about the actions that the clients want to do and
 * receiving the change events of the model and the controller
 */
public class GameHandler implements PropertyChangeListener {
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
    private final PropertyChangeSupport controllerListener=new PropertyChangeSupport(this);
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
     * @param playersNumber the number of players for the current match
     */
    public GameHandler(int playersNumber){
        this.playersNumber=playersNumber;
        if (playersNumber==1) this.model=new SoloGame();
        else this.model=new Game();
        this.model.setListener(this);
        this.controller=new Controller(this, this.model);
        this.players=new HashMap<>();
        controllerListener.addPropertyChangeListener(this.controller);
    }

    /**
     * playersNumber getter method
     * @return the number of players of the current match
     */
    public int getPlayersNumber(){
        return this.playersNumber;
    }

    /**
     * This method is called by the Server when the number of players for the match has been reached. It starts the game
     */
    public void start(){
        //notify clients that the game is starting
        Map<String, String> message=new HashMap<>();
        message.put("action", "start");
        String content="The game is starting! Have fun";
        message.put("content", content);
        Gson gson= new Gson();
        String jsonMessage= gson.toJson(message);
        sendAll(jsonMessage);

        //start the game
        controllerListener.firePropertyChange("start", null, null);
    }

    /**
     * Method used to send a socket message to a single client
     * @param message the serialized message that is to be sent
     * @param client the name of the client that has to receive the message
     */
    public void sendSingle(String message, String client){
        players.get(client).send(message);
    }

    /**
     * Method used to send a socket message to all the players of the game
     * @param message the serialized message that is to be sent
     */
    public void sendAll(String message){
        for (Player player:model.getActivePlayers()) {
            players.get(player.getName()).send(message);
        }
    }

    /**
     * Method used to send a socket message to all the players but one
     * @param message the serialized message that is to be sent
     * @param client the client that must not receive the message
     */
    public void sendAllExcept(String message, String client){
        for (Player player:model.getActivePlayers()) {
            if (!player.getName().equals(client)){
                players.get(player.getName()).send(message);
            }
        }
    }

    /**
     * Method called by a ClientHandler when a client wants to do an action
     * @param message the message sent by the client: it contains all the info needed to perform the action
     * @param player the name of the player that sent the message
     */
    public void makeAction(Map<String, String> message, String player){
        //controls if the move can be done
        controllerListener.firePropertyChange(message.get("action"), null, message);
    }

    /**
     * Method used to add a player to the game
     * @param name the name of the player
     * @param client the ClientHandler of the player
     */
    public void setPlayer(String name, ClientHandler client){
        this.players.put(name, client);
        this.model.createPlayer(name);
    }

    /**
     * Method used to remove a player when the game has not started yet
     * @param name the name of the player that disconnected from the game
     */
    public void removePlayer(String name){
        this.players.remove(name);
        model.removePlayer(name);
    }

    /**
     * Method used to remove a player when the game has started. It notifies all the other players that the game is going to end
     * @param name the name of the disconnected player
     */
    public void manageDisconnection(String name){
        Map<String, String> map= new HashMap<>();
        String content="Player "+name+" has quit the game. Game is now ending...";
        map.put("action", "end");
        map.put("content", content);
        Gson gson=new Gson();
        String message=gson.toJson(map);
        sendAllExcept(message, name);
        //close connections of ClientHandlers

    }

    /**
     * Utility method used to create a different message for the other players when a player has bought a card
     * @param original the original message that will be sent only to the player that did the action
     * @return a message that is to be sent to all the other players
     */
    private Map<String, String> createBuyMessage(Map<String, String> original){
        Map<String, String> copy= new HashMap<>();
        copy.put("action", original.get("action"));
        copy.put("player", original.get("player"));
        copy.put("row", original.get("row"));
        copy.put("col", original.get("col"));
        copy.put("idNew", original.get("idNew"));
        return copy;
    }

    /**
     * Utility method used to create a different message for the other players when a player has taken resources from the market
     * @param original the original message that will be sent only to the player that did the action
     * @return a message that is to be sent to all the other players
     */
    private Map<String, String> createMarketMessage(Map<String, String> original){
        Map<String, String> copy= new HashMap<>();
        copy.put("action", original.get("action"));
        copy.put("player", original.get("player"));
        if (original.containsKey("row")){
            copy.put("row", original.get("row"));
            String res;
            for (int i=0; i<4; i++){
                res="res"+i;
                copy.put(res, original.get(res));
            }
        }
        else if (original.containsKey("col")){
            copy.put("col", original.get("col"));
            String res;
            for (int i=0; i<3; i++){
                res="res"+i;
                copy.put(res, original.get(res));
            }
        }
        copy.put("out", original.get("out"));
        copy.put("discarded", original.remove("discarded"));
        return copy;
    }

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //switch for different kind of maps sent by model
        String type=evt.getPropertyName();
        Map<String, String> message= (Map<String, String>)evt.getNewValue();
        Gson gson=new Gson();
        String jsonMessage;
        String addressee;
        switch (type.toUpperCase()){
            case "STARTED":
                //sendall
                jsonMessage=gson.toJson(message);
                sendAll(jsonMessage);
            case "YOURTURN":
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "CHOOSELEADERS":
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "OKLEADERS":
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "CHOOSERESOURCES":
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "OKRESOURCES":
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "BUY":
                //different sendall
                jsonMessage=gson.toJson(message);
                addressee=message.get("player");
                sendSingle(jsonMessage, addressee);
                if (playersNumber>1){
                    Map<String, String> buyToOthers=createBuyMessage(message);
                    String jsonBuyOthers=gson.toJson(buyToOthers);
                    sendAllExcept(jsonBuyOthers, addressee);
                }
            case "PRODUCE":
                //singlesend
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "MARKET":
                //different sendall
                jsonMessage=gson.toJson(message);
                addressee=message.get("player");
                sendSingle(jsonMessage, addressee);
                if (playersNumber>1){
                    Map<String, String> buyToOthers=createMarketMessage(message);
                    String jsonMarketToOthers=gson.toJson(buyToOthers);
                    sendAllExcept(jsonMarketToOthers, addressee);
                }
            case "SWAP":
                //singlesend
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "ACTIVATE":
                //singlesend
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "DISCARD":
                //singlesend
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "ENDTURN":
                //singlesend (model fires different messages for each client/player)
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "ENDGAME":
                //singlesend
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
            case "ERROR":
                //singlesend
                addressee=message.get("player");
                jsonMessage=gson.toJson(message);
                sendSingle(jsonMessage, addressee);
        }
    }
}
