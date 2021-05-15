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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StartController implements GUIController{
    private Stage stage;
    private Scene scene;
    private Parent root;
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

    public void toChooseNameLocal(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/chooseName.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        //scene.getStylesheets().add(locator.locateAsURL)
        stage.setScene(scene);
        stage.show();
    }

    public void toChooseNameOnline(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/server_port.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLocal(ActionEvent actionEvent){
        String name = nameLocal.getText();
        ModelView modelView = gui.getModelView();
        if (!name.equals(""))
            modelView.setName(name);
        else {
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error! Missing parameters!");
            alert.showAndWait();
            return;
        }
        modelView.setSoloGame(true);
        gui.setModel(new SoloGame());
        gui.getModel().setListener(gui.getAnswerHandler());
        gui.setController(new Controller(gui.getModel(),gui.getAnswerHandler()));
        gui.getListeners().addListener(gui.getController());
        gui.getListeners().fireUpdates("start",null);
        gui.changeScene("board.fxml");
    }

    public void handleOnline(ActionEvent actionEvent){
        String name= nameOnline.getText();
        String addr= address.getText();
        int players=0;
        int serverPort=0;
        try {
            players = Integer.parseInt(number.getText());
            serverPort = Integer.parseInt(port.getText());
        }
        catch (NumberFormatException e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error! Port and number must be integers!");
            alert.showAndWait();
            return;
        }
        if (name.equals("") || addr.equals("")){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error! Missing parameters!");
            alert.showAndWait();
            return;
        }
        ModelView modelView=gui.getModelView();
        modelView.setName(name);
        modelView.setSoloGame(players==1);

        ConnectionSocket connectionSocket = new ConnectionSocket(addr, serverPort);
        Map<String, String> map= new HashMap<>();
        map.put("action", "setup");
        map.put("number", String.valueOf(number));
        map.put("username", name);
        Gson gson= new Gson();
        String message=gson.toJson(map);
        if (!connectionSocket.setup(message, gui.getAnswerHandler())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Server not reachable");
            alert.setContentText("The entered IP/port doesn't match any active server or the server is not running. Please try again!");
            alert.showAndWait();
            gui.changeScene("start.fxml");
            return;
        }
        gui.setConnectionSocket(connectionSocket);
        WaitController controller= (WaitController) gui.getControllerFromName("wait.fxml");
        controller.setText("SOCKET CONNECTION \nSETUP COMPLETED!");
        controller.setText("WAITING FOR PLAYERS");
        gui.getListeners().addListener(new ActionParser(connectionSocket));
        gui.changeScene("wait.fxml");
    }


    public void setLocal(ActionEvent event) {
        gui.changeScene("local.fxml");
    }

    public void setOnline(ActionEvent event){
        gui.changeScene("online.fxml");
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
