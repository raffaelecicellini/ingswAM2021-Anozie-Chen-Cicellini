package it.polimi.ingsw.client.gui.Controllers;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.SoloGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.util.HashMap;
import java.util.Map;

public class StartController extends GUIController {
    private GUI gui;
    @FXML
    private TextField nameLocal;
    @FXML
    private TextField nameOnline;
    @FXML
    private TextField address;
    @FXML
    private TextField port;
    @FXML
    private TextField number;

    /**
     * This method is called once the player decides to play a local game. It will show a new scene.
     *
     * @param actionEvent is the event caught by the player's click.
     */
    public void handleLocal(ActionEvent actionEvent) {
        String name = nameLocal.getText();
        ModelView modelView = gui.getModelView();
        if (!name.equals(""))
            modelView.setName(name);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error! Missing parameters!");
            alert.showAndWait();
            return;
        }
        modelView.setSoloGame(true);
        gui.setModel(new SoloGame());
        gui.getModel().setListener(gui.getAnswerHandler());
        gui.getModel().createPlayer(name);
        gui.setController(new Controller(gui.getModel(), gui.getAnswerHandler()));
        gui.getListeners().addListener(gui.getController());
        gui.getListeners().fireUpdates("start", null);
        BoardController board = (BoardController) gui.getControllerFromName("board.fxml");
        board.disableShow();
        gui.changeScene("board.fxml");
    }

    /**
     * This method is called once the player decides to play an online game. It will show a new scene.
     *
     * @param actionEvent is the event caught by the player's click.
     */
    public void handleOnline(ActionEvent actionEvent) {
        String name = nameOnline.getText();
        String addr = address.getText();
        int players = 0;
        int serverPort = 0;
        try {
            players = Integer.parseInt(number.getText());
            if (players < 1 || players > 4) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error! Players must be between 1 and 4!");
                alert.showAndWait();
                return;
            }
            serverPort = Integer.parseInt(port.getText());
            if (serverPort < 1024) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error! Port must be greater than 1024!");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error! Port and number must be integers!");
            alert.showAndWait();
            return;
        }
        if (name.equals("") || addr.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error! Missing parameters!");
            alert.showAndWait();
            return;
        }
        ModelView modelView = gui.getModelView();
        modelView.setName(name);
        modelView.setSoloGame(players == 1);

        System.out.println(players + " " + serverPort + " " + name + " " + addr);
        ConnectionSocket connectionSocket = new ConnectionSocket(addr, serverPort);
        Map<String, String> map = new HashMap<>();
        map.put("action", "setup");
        map.put("number", String.valueOf(players));
        map.put("username", name);
        Gson gson = new Gson();
        String message = gson.toJson(map);
        gui.changeScene("wait.fxml");
        WaitController controller = (WaitController) gui.getControllerFromName("wait.fxml");
        controller.setText("Configuring socket connection...");
        if (!connectionSocket.setup(message, gui.getAnswerHandler())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Server not reachable");
            alert.setContentText("The entered IP/port doesn't match any active server or the entered nickname is already in use. Please try again!");
            alert.showAndWait();
            gui.changeScene("start.fxml");
            return;
        }
        gui.setConnectionSocket(connectionSocket);
        controller.setText("Socket setup completed!");
        controller.setText("Waiting for players...");
        gui.getListeners().addListener(new ActionParser(connectionSocket));
        if (players == 1) {
            BoardController board = (BoardController) gui.getControllerFromName("board.fxml");
            board.disableShow();
        }
    }


    /**
     * This method changes the scene to local.fxml.
     *
     * @param event is the event caught by the player's click.
     */
    public void setLocal(ActionEvent event) {
        gui.changeScene("local.fxml");
    }

    /**
     * This method changes the scene to online.fxml.
     *
     * @param event is the event caught by the player's click.
     */
    public void setOnline(ActionEvent event) {
        gui.changeScene("online.fxml");
    }

    /**
     * @param gui the gui to be set
     * @see GUIController
     */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}