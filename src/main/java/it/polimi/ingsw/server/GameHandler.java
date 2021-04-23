package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;

public class GameHandler implements PropertyChangeListener {
    private Controller controller;
    private Game model;
    private List<ClientHandler> players;
    private int playersNumber;
    private PropertyChangeSupport controllerListener;

    public GameHandler(int playersNumber){
        this.playersNumber=playersNumber;
        //to do
    }
    public void start(){

    }

    public void removePlayer(ClientHandler player) {
        players.remove(player);
    }

    public void end(){
    }

    public List<ClientHandler> getPlayers() {
        return players;
    }

    public void setPlayersNumber(int playersNumber) {

    }

    public boolean isStarted() {
        return true;
    }

    public void sendSingle(String message, String client){

    }
    public void sendToAll(String message){

    }
    public void makeAction(Map<String,String> message, String player){

    }
    public void setPlayer(ClientHandler client){

    }

    public Game getModel() {
        return model;
    }

    public void manageDisconnection(String name) {

    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
