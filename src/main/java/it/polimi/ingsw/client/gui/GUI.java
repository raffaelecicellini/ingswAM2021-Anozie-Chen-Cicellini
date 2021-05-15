package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.AnswerHandler;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.Controllers.GUIController;
import it.polimi.ingsw.client.gui.Controllers.LeadersController;
import it.polimi.ingsw.client.gui.Controllers.ResourceController;
import it.polimi.ingsw.client.gui.Controllers.WaitController;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.List;

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

    private final HashMap<String, Scene> mapNameScene = new HashMap<>();

    private final HashMap<String, GUIController> mapNameController = new HashMap<>();

    private Scene currentScene;
    private Stage stage;

    private Stage window;

    public GUI(){
        modelView = new ModelView();
        answerHandler = new AnswerHandler(modelView, this);
        activeGame = true;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        setup();
    }

    public static void main(String[] args) {
        launch(args);
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

    public void setup() {
        List<String> list = new ArrayList<>(Arrays.asList("start.fxml", "online.fxml", "local.fxml", "wait.fxml", "board.fxml", "buy.fxml", "market.fxml", "produce.fxml", "chooseLeaders.fxml", "chooseResources.fxml", "show.fxml"));
        try {
            for (String path : list) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                mapNameScene.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGui(this);
                mapNameController.put(path, controller);
            }
        } catch (IOException e) {
            System.exit(-1);
        }
        currentScene = mapNameScene.get("start.fxml");
        stage.setScene(currentScene);
        stage.show();
    }


    private void setActiveGame(boolean active) {
        this.activeGame=active;
    }

    public ModelView getModelView() {
        return this.modelView;
    }

    public AnswerHandler getAnswerHandler() {
        return this.answerHandler;
    }

    public void setConnectionSocket(ConnectionSocket connectionSocket) {
        this.connectionSocket=connectionSocket;
    }

    public Source getListeners() {
        return this.listener;
    }

    public void setModel(Game game) {
        this.model = game;
    }

    public Game getModel() {
        return model;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public void changeScene(String scene) {
        currentScene=mapNameScene.get(scene);
        stage.setScene(currentScene);
        stage.show();
    }

    public GUIController getControllerFromName(String name) {
        return mapNameController.get(name);
    }

    private void updateBoard(){

    }

    private void chooseLeaders() {
        ArrayList<String> location = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String pos = modelView.getLeaders(modelView.getName()).get("leader"+i);
            location.add("/PNG/cards/lc_"+pos+".png");
        }
        LeadersController controller;
        controller= (LeadersController) mapNameController.get("chooseLeaders.fxml");
        controller.setLeaders(location);
        changeScene("chooseLeaders.fxml");
    }

    private void chooseResources(int initialRes){
        ResourceController controller;
        controller = (ResourceController) mapNameController.get("chooseResources.fxml");
        controller.setResources(initialRes);
        changeScene("chooseResources.fxml");
    }

    @Override
    public void update(String propertyName, Map<String, String> value) {
        switch (propertyName.toUpperCase()) {
            case "START":
                Platform.runLater(()->{
                    WaitController controller= (WaitController) mapNameController.get("wait.fxml");
                    controller.setText(value.get("content"));
                });

                break;

            case "OTHERCONNECTED":
                Platform.runLater(()->{
                    WaitController controller= (WaitController) mapNameController.get("wait.fxml");
                    controller.setText(value.get("content"));
                });

                break;

            case "STARTED":
                Platform.runLater(()->{
                    WaitController controller= (WaitController) mapNameController.get("wait.fxml");
                    controller.setText(value.get("content"));
                });
                break;

            case "CHOOSELEADERS":

                if (value == null) {
                    chooseLeaders();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("ChooseLeaders");
                        alert.setContentText(value.get("other") + " is choosing his leaders!");
                        alert.showAndWait();
                    });
                }

                break;

            case "OKLEADERS":

                if (value == null) {
                    updateBoard();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("OkLeaders");
                        alert.setContentText(value.get("other") + " has chosen his leaders!");
                        alert.showAndWait();
                    });
                }

                break;

            case "CHOOSERESOURCES":

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    chooseResources(Integer.parseInt(value.get("qty")));
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("ChooseResources");
                        alert.setContentText(value.get("other") + " is choosing his initial resources!");
                        alert.showAndWait();
                    });
                }

                break;

            case "OKRESOURCES":

                if (value == null) {
                    updateBoard();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("OkResources");
                        alert.setContentText(value.get("other") + " has chosen his initial resources!");
                        alert.showAndWait();
                    });
                }

                break;

            case "YOURTURN":

                if (value.get("player").equalsIgnoreCase(modelView.getName())) {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("YourTurn");
                        alert.setContentText("It is your turn now! Choose your move!");
                        alert.showAndWait();
                    });
                }
                break;

            case "PRODUCE":

                if (value == null) {
                    updateBoard();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Produce");
                        alert.setContentText(value.get("other") + " has made some productions!");
                        alert.showAndWait();
                    });
                }

                break;

            case "BUY":

                if (value == null) {
                    updateBoard();
                } else {
                    updateBoard();
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Buy");
                        alert.setContentText(value.get("other") + " has bought a Develop Card!");
                        alert.showAndWait();
                    });
                }
                break;

            case "MARKET":

                if (value == null) {
                    updateBoard();
                } else {
                    updateBoard();
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Market");
                        alert.setContentText(value.get("other") + " has taken resources from the Market!");
                        alert.showAndWait();
                    });
                }

                break;

            case "SWAP":

                if (value == null) {
                    updateBoard();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Swap");
                        alert.setContentText(value.get("other") + " has swapped his deposits!");
                        alert.showAndWait();
                    });
                }

                break;

            case "ACTIVATE":

                if (value == null) {
                    updateBoard();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("ActivateLeader");
                        alert.setContentText(value.get("other") + " has activated his leader!");
                        alert.showAndWait();
                    });
                }

                break;

            case "DISCARD":

                if (value == null) {
                    updateBoard();
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("DiscardLeader");
                        alert.setContentText(value.get("other") + " has discarded his leader!");
                        alert.showAndWait();
                    });
                }

                break;

            case "ENDTURN":
                updateBoard();
                if (modelView.getName().equalsIgnoreCase(value.get("endedTurnPlayer"))) {
                    if (modelView.isSoloGame()){
                        Platform.runLater(() -> {
                            Alert alert= new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("EndTurn");
                            alert.setContentText("The Token that has been activated is: ");
                            String token="/PNG/punchboard/cerchio"+value.get("tokenActivated")+".png";
                            Image image= new Image(token);
                            ImageView img= new ImageView(image);
                            alert.setGraphic(img);
                            alert.showAndWait();
                        });
                    }
                } else {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("EndTurn");
                        alert.setContentText(value.get("other") + " has ended his turn!"+" It is " + value.get("currentPlayer") + " turn now!");
                        alert.showAndWait();
                    });
                }

                break;

            case "ENDGAME":

                if (value.containsKey("winner") && value.get("winner").equalsIgnoreCase(modelView.getName())) {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Winner");
                        alert.setContentText("You won! You made " + value.get("winnerpoints") + " points! ");
                        alert.showAndWait();
                    });
                } else {
                    Platform.runLater(() -> {
                        String content="You lost! You made " + value.get("points") + " points! ";
                        if (!modelView.isSoloGame()) {
                            content=content+value.get("winner") + " won with " + value.get("winnerpoints") + " points! ";
                        }
                        Alert alert= new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Loser");
                        alert.setContentText(content);
                        alert.showAndWait();
                    });
                }

                setActiveGame(false);
                if (connectionSocket!=null) connectionSocket.close();
                System.exit(0);

                break;

            case "ERROR":

                if (value.get("player").equalsIgnoreCase(modelView.getName())) {
                    Platform.runLater(() -> {
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Error");
                        alert.setContentText(value.get("content"));
                        alert.showAndWait();
                    });
                }
                if (value.get("method").equalsIgnoreCase("chooseleaders")){
                    chooseLeaders();
                }
                else if (value.get("method").equalsIgnoreCase("chooseresources")){
                    chooseResources(modelView.getInitialRes());
                }
                break;

            case "END":
                Platform.runLater(() -> {
                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("End");
                    alert.setContentText(value.get("content"));
                    alert.showAndWait();
                });
                System.exit(0);
                break;

            case "OTHERDISCONNECTED":
                Platform.runLater(() -> {
                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("End");
                    alert.setContentText(value.get("content"));
                    alert.showAndWait();
                });
                System.out.println(value.get("content"));
                break;

            default:
                Platform.runLater(() -> {
                    Alert alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("WHAT?!");
                    alert.setContentText("Unrecognized answer!");
                    alert.showAndWait();
                });
                break;
        }
    }
}
