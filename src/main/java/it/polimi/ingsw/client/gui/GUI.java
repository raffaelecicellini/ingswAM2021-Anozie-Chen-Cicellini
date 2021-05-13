package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.AnswerHandler;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.SoloGame;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GUI extends Application implements SourceListener {
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

    private Stage window;

    public GUI(){
        modelView = new ModelView();
        answerHandler = new AnswerHandler(modelView, this);
        activeGame = true;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.window=stage;
        stage.setTitle("Master of Renaissance");
        Label label = new Label("Welcome! What do you want to launch?");
        Button local= new Button("LOCAL GAME (1 player)");
        Button online= new Button("ONLINE GAME (1 to 4 players)");
        stage.setOnCloseRequest(e -> {
            e.consume();
            quit();
        });

        local.setOnAction(e -> handleLocal());
        online.setOnAction(e -> handleOnline());
        VBox layout= new VBox(label, local, online);
        layout.setSpacing(20);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleLocal(){
        this.isSolo=true;
        model=new SoloGame();
        model.setListener(answerHandler);
        modelView.setSoloGame(true);
        controller= new Controller(model, answerHandler);
        listener.addListener(controller);

        TextField nameField= new TextField();
        Button select= new Button("Confirm");
        select.setOnAction(e -> {
            String name= nameField.getText();
            modelView.setName(name);
            model.createPlayer(name);
            listener.fireUpdates("start", null);
        });

        VBox layout= new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(nameField, select);

        Scene scene= new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();
    }

    private void handleOnline(){

    }

    private void quit(){
        boolean answer=ConfirmBox.display("Quit", "Are you sure you want to quit?");
        if (answer){
            Map<String,String> action = new HashMap<>();
            action.put("action","disconnect");
            action.put("player",modelView.getName());

            listener.fireUpdates("disconnect", action);
            if (connectionSocket!=null) connectionSocket.close();
            window.close();
        }
    }

    @Override
    public void update(String propertyName, Map<String, String> value) {
        System.out.println(propertyName);
    }
}
