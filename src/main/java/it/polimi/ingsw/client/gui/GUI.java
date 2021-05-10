package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.AnswerHandler;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.SoloGame;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;

import java.util.Map;

public class GUI implements SourceListener{
    //used in online
    private int port;
    private String address;
    private ConnectionSocket connectionSocket;
    //used in offline
    private Controller controller;
    private Game model;
    private boolean isSolo;
    //used in both cases
    private final Source listener= new Source();
    private ModelView modelView;
    private final AnswerHandler answerHandler;
    private boolean activeGame;

    public GUI(boolean isSolo){
        modelView = new ModelView();
        answerHandler = new AnswerHandler(modelView, this);
        activeGame = true;
        this.isSolo=isSolo;

        if (isSolo){
            modelView.setSoloGame(true);
            model= new SoloGame();
            model.setListener(answerHandler);
            controller= new Controller(model, answerHandler);
            listener.addListener(controller);
        }
    }

    public static void main(String[] args) {
        //launch();
    }

    @Override
    public void update(String propertyName, Map<String, String> value) {

    }
}
