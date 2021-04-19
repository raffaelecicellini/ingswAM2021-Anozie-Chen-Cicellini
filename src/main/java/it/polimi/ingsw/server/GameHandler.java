package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

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
    public void sendSingle(String message, String client){

    }
    public void sendToAll(String message){

    }
    public void makeAction(String message, String player){

    }
    public void setPlayer(ClientHandler client){

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
