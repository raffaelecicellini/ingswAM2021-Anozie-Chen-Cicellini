package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.AnswerHandler;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.Controllers.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
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
    private final Source listener = new Source();
    private ModelView modelView;
    private final AnswerHandler answerHandler;
    private boolean activeGame;

    private final HashMap<String, Scene> mapNameScene = new HashMap<>();

    private final HashMap<String, GUIController> mapNameController = new HashMap<>();

    private Scene currentScene;
    private Stage stage;

    private Stage window;

    public GUI() {
        modelView = new ModelView();
        answerHandler = new AnswerHandler(modelView, this);
        activeGame = true;
    }

    /**
     * @see Application
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        setup();
    }

    /**
     * @see Application
     */
    @Override
    public void stop() {
        System.out.println("Stopping...");
        System.exit(0);
    }

    /**
     * Main method of GUI
     *
     * @param args arguments of the main
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method loads the Scene and the controllers for each fxml and put them in two maps
     */
    public void setup() {
        List<String> list = new ArrayList<>(Arrays.asList("online.fxml", "local.fxml", "start.fxml", "wait.fxml", "board.fxml", "buy.fxml", "market.fxml", "produce.fxml", "chooseLeaders.fxml", "chooseResources.fxml", "show.fxml"));
        try {
            for (String path : list) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + path));
                mapNameScene.put(path, new Scene(loader.load()));
                GUIController controller = loader.getController();
                controller.setGui(this);
                mapNameController.put(path, controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        currentScene = mapNameScene.get("start.fxml");
        stage.setScene(currentScene);
        stage.setTitle("Maestri");
        stage.getIcons().add(new Image("/PNG/punchboard/calamaio.png"));
        stage.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quit");
            alert.setHeaderText("Are you sure you want to quit?");
            alert.initModality(Modality.APPLICATION_MODAL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (modelView.getName() != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("player", modelView.getName());
                    map.put("action", "disconnect");
                    Message message = new DisconnectionMessage(map);
                    listener.fireUpdates("disconnect", message);
                }
                Platform.exit();
                System.exit(0);
            }
        });
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Method used to set the state of the current game
     *
     * @param active the new value of attribute activeGame
     */
    private void setActiveGame(boolean active) {
        this.activeGame = active;
    }

    /**
     * ModelView getter method used by the GuiControllers
     *
     * @return the ModelView of the current game
     */
    public ModelView getModelView() {
        return this.modelView;
    }

    /**
     * AnswerHandler getter method used by GuiControllers
     *
     * @return the AnswerHandler of this class
     */
    public AnswerHandler getAnswerHandler() {
        return this.answerHandler;
    }

    /**
     * ConnectionSocket setter method used by GuiControllers
     *
     * @param connectionSocket the value for the attribute connectionSocket
     */
    public void setConnectionSocket(ConnectionSocket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    /**
     * Listener getter method used by GuiControllers to fire changes caused by user actions
     *
     * @return the value of the attribute listener
     */
    public Source getListeners() {
        return this.listener;
    }

    /**
     * Model setter method used by GuiControllers (in SoloGame)
     *
     * @param game the value of the model attribute
     */
    public void setModel(Game game) {
        this.model = game;
    }

    /**
     * Model getter method used by GuiControllers (in SoloGame)
     *
     * @return the value of the attribute model
     */
    public Game getModel() {
        return model;
    }

    /**
     * Controller setter method used by GuiControllers (in SoloGame)
     *
     * @param controller the value of the controller attribute
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Controller getter method used by GuiControllers (in SoloGame)
     *
     * @return the value of the controller attribute
     */
    public Controller getController() {
        return controller;
    }

    /**
     * This method is used to change the scene in the main stage
     *
     * @param scene the name of the fxml file to show in the main stage
     */
    public void changeScene(String scene) {
        currentScene = mapNameScene.get(scene);
        stage.setScene(currentScene);
        stage.show();
    }

    /**
     * This method is used to get the GuiController associated to the fxml whose name is passed as a parameter
     *
     * @param name the name of the fxml file
     * @return the GuiController associated to the fxml file
     */
    public GUIController getControllerFromName(String name) {
        return mapNameController.get(name);
    }

    /**
     * This method is used to get the Scene associated to the fxml whose name is passed as a parameter
     *
     * @param name the name of the fxml file
     * @return the Scene associated to the fxml file
     */
    public Scene getSceneFromName(String name) {
        return mapNameScene.get(name);
    }

    /**
     * This method is used to update the situation after a Market action.
     */
    private void updateMarket() {
        //board.updateDeps, market.updateMarket, board.updatePosition
        Platform.runLater(() -> {
            BoardController board = (BoardController) getControllerFromName("board.fxml");
            board.updateDeposits();
            board.updatePosition();
            MarketController market = (MarketController) getControllerFromName("market.fxml");
            market.updateMarket();
        });
    }

    /**
     * This method is used to update the situation after a Buy action.
     */
    private void updateBuy() {
        //board.updateDeps, updateStr, updateSlots, buy.updateDecks, produce.updateSlots
        Platform.runLater(() -> {
            BoardController board = (BoardController) getControllerFromName("board.fxml");
            board.updateDeposits();
            board.updateStrongbox();
            board.updateSlots();
            BuyController buy = (BuyController) getControllerFromName("buy.fxml");
            buy.updateDecks();
            ProduceController produce = (ProduceController) getControllerFromName("produce.fxml");
            produce.updateSlots();
        });
    }

    /**
     * This method is used to update the situation after a Produce action.
     */
    private void updateProduce() {
        //board.updateDeps, updateStr, updatePosition
        Platform.runLater(() -> {
            BoardController board = (BoardController) getControllerFromName("board.fxml");
            board.updateDeposits();
            board.updateStrongbox();
            board.updatePosition();
        });
    }

    /**
     * This method is used to update the situation after a Swap action.
     */
    private void updateSwap() {
        //board.updateDeps
        Platform.runLater(() -> {
            BoardController board = (BoardController) getControllerFromName("board.fxml");
            board.updateDeposits();
        });
    }

    /**
     * This method is used to update the situation after a Leader action.
     */
    private void updateLeader() {
        //board.updateLeader, board.updatePosition
        Platform.runLater(() -> {
            BoardController board = (BoardController) getControllerFromName("board.fxml");
            board.updateLeader();
            board.updatePosition();
            ProduceController produce = (ProduceController) getControllerFromName("produce.fxml");
            produce.updateSlots();
        });
    }

    /**
     * This method is used to update the situation after a Player has ended his turn.
     */
    private void updateEndTurn() {
        //board.updateTiles
        Platform.runLater(() -> {
            BoardController board = (BoardController) getControllerFromName("board.fxml");
            board.updateTiles();
            board.updateCurrentPlayer();
        });
    }


    /**
     * This method is called when the client receive a ChooseLeader message from the model. It changes the scene and
     * the fxml file associated to that scene
     */
    private void chooseLeaders() {
        Platform.runLater(() -> {
            ArrayList<String> location = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                String pos = modelView.getLeaders(modelView.getName()).get("leader" + i);
                location.add("/PNG/cards/lc_" + pos + ".png");
            }
            LeadersController controller;
            controller = (LeadersController) mapNameController.get("chooseLeaders.fxml");
            controller.setLeaders(location);
            changeScene("chooseLeaders.fxml");
        });
    }

    /**
     * This method is called when the client receive a ChooseResource message from the model. It changes the scene
     *
     * @param initialRes the amount of resources the user has
     */
    private void chooseResources(int initialRes) {
        Platform.runLater(() -> {
            ResourceController controller;
            controller = (ResourceController) mapNameController.get("chooseResources.fxml");
            controller.setResources(initialRes);
            changeScene("chooseResources.fxml");
        });
    }

    /**
     * Method called when the user receives confirmation of an ending turn action. It calls the corresponding method
     * on the BoardController
     */
    private void disableButtons() {
        BoardController board = (BoardController) getControllerFromName("board.fxml");
        board.disableButtons();
    }

    /**
     * Method called when the user receives a message telling that it is his turn. It calls the corresponding method
     * on the BoardController
     */
    private void enableButtons() {
        BoardController board = (BoardController) getControllerFromName("board.fxml");
        board.enableButtons();
    }

    /**
     * Method called when the user receives confirmation of a mandatory action. It calls the corresponding method on the
     * BoardController
     */
    private void disableMandatory() {
        BoardController board = (BoardController) getControllerFromName("board.fxml");
        board.disableMandatory();
    }

    /**
     * This method shows an information alert on screen.
     *
     * @param header  is the header of the Alert.
     * @param message is the message of the Alert.
     */
    private void showAlert(String header, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showEndAlert(String header, String message) {
        setActiveGame(false);
        if (connectionSocket != null) connectionSocket.close();
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.showAndWait();
            System.exit(0);
        });
    }

    /**
     * Method called when a started message arrives from the model. It does the first update of the personalboard, market
     * and decks
     */
    private void started(){
        Platform.runLater(() -> {
            WaitController controller = (WaitController) mapNameController.get("wait.fxml");
            controller.setText("Game started!");
            BoardController board = (BoardController) mapNameController.get("board.fxml");
            board.updateTiles();
            board.updateCurrentPlayer();
            BuyController buy = (BuyController) mapNameController.get("buy.fxml");
            buy.updateDecks();
            MarketController market = (MarketController) mapNameController.get("market.fxml");
            market.updateMarket();
        });
    }

    /**
     * Method called when a EndTurnAnswer arrives from the model. If the player ended the turn and it is a solo game,
     * this method shows an alert containing the token activated. If the player ended the turn but it is a multiplayer
     * game, the buttons on the board.fxml are disabled. Otherwise, this method shows an alert containing infos about the
     * player that ended the turn and the current player
     * @param message the Message arrived from the model
     */
    private void endTurn(Message message){
        if (modelView.getName().equalsIgnoreCase(message.getEndedPlayer())) {
            if (modelView.isSoloGame()) {
                Platform.runLater(() -> {
                    BuyController buy = (BuyController) getControllerFromName("buy.fxml");
                    buy.updateDecks();
                    BoardController board = (BoardController) getControllerFromName("board.fxml");
                    board.updatePosition();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("EndTurn");
                    alert.setContentText("The Token that has been activated is: ");
                    String token = "/PNG/punchboard/cerchio" + message.getToken() + ".png";
                    Image image = new Image(token);
                    ImageView img = new ImageView(image);
                    alert.setGraphic(img);
                    alert.showAndWait();
                });
            } else {
                Platform.runLater(this::disableButtons);
            }
        } else {
            showAlert(message.getAction(), message.getEndedPlayer() + " has ended his turn! " + " It is " + message.getCurrentPlayer() + " turn now!");
        }
    }

    /**
     * @see SourceListener
     */
    @Override
    public void update(String propertyName, Message message) {

        switch (propertyName.toUpperCase()) {
            case "START":
                Platform.runLater(() -> {
                    WaitController controller = (WaitController) mapNameController.get("wait.fxml");
                    controller.setText("Game is starting! Have fun!");
                });

                break;

            case "OTHERCONNECTED":
                Platform.runLater(() -> {
                    WaitController controller = (WaitController) mapNameController.get("wait.fxml");
                    controller.setText(message.getContent());
                });

                break;

            case "STARTED":

                started();
                break;

            case "CHOOSELEADERS":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    chooseLeaders();
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " is choosing his leaders!");
                }

                break;

            case "OKLEADERS":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateLeader();
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " has chosen his leaders!");
                }

                break;

            case "CHOOSERESOURCES":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    chooseResources(message.getResQty());
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " is choosing his initial resources!");
                }

                break;

            case "OKRESOURCES":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    Platform.runLater(() -> {
                        BoardController board = (BoardController) getControllerFromName("board.fxml");
                        board.updateDeposits();
                        board.updatePosition();
                    });
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " has chosen his initial resources!");
                }

                break;

            case "YOURTURN":

                if (message.getPlayer().equalsIgnoreCase(modelView.getName())) {
                    showAlert("Your Turn", "It is your turn now! Choose your move!");
                    Platform.runLater(this::enableButtons);
                }
                break;

            case "PRODUCE":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateProduce();
                    Platform.runLater(this::disableMandatory);
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " has made some productions!");
                }

                break;

            case "BUY":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateBuy();
                    Platform.runLater(this::disableMandatory);
                } else {
                    updateBuy();
                    showAlert(message.getAction(), message.getPlayer() + " has bought a Develop Card!");
                }
                break;

            case "MARKET":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateMarket();
                    Platform.runLater(this::disableMandatory);
                } else {
                    updateMarket();
                    showAlert(message.getAction(), message.getPlayer() + " has taken resources from the Market!");
                }

                break;

            case "SWAP":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateSwap();
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " has swapped his deposits!");
                }

                break;

            case "ACTIVATE":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateLeader();
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " has activated his leader!");
                }

                break;

            case "DISCARD":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    updateLeader();
                } else {
                    showAlert(message.getAction(), message.getPlayer() + " has discarded his leader!");
                }

                break;

            case "ENDTURN":

                updateEndTurn();

                endTurn(message);

                break;

            case "ENDGAME":

                if (message.getWinner() != null && message.getWinner().equalsIgnoreCase(modelView.getName())) {
                    showEndAlert("Winner", "You won! You made " + message.getWinnerPoints() + " points!");
                } else {
                    String content = "You lost! You made " + message.getPoints() + " points! ";
                    if (!modelView.isSoloGame()) {
                        content = content + message.getWinner() + " won with " + message.getWinnerPoints() + " points!";
                    }
                    showEndAlert("Loser", content);
                }

                break;

            case "ERROR":

                if (message.getPlayer().equalsIgnoreCase(modelView.getName())) {
                    showAlert("Error", message.getContent());
                }
                if (message.getMethod().equalsIgnoreCase("chooseleaders")) {
                    chooseLeaders();
                } else if (message.getMethod().equalsIgnoreCase("chooseresources")) {
                    chooseResources(modelView.getInitialRes());
                }
                break;

            case "END":
                showAlert("End", message.getContent());
                Platform.runLater(() -> System.exit(0));
                break;

            case "OTHERDISCONNECTED":
                showAlert("End", message.getContent());
                System.out.println(message.getContent());
                break;

            default:
                showAlert("WHAT?!", "Unrecognized answer!");
                break;
        }
    }
}
