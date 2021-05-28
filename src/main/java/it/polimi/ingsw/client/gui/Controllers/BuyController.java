package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Cards;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BuyController extends GUIController{
    private GUI gui;
    private Map<String, String> action =  new HashMap<>();
    private Stage stage;
    @FXML
    private GridPane decks;

    public void buy(){
        //Metodo chiamato quando utente da board schiaccia su tasto buy. Mostra un nuovo stage buy.fxml da cui non si può uscire
        //se non dopo aver fatto mossa o aver chiuso la finestra
        action.clear();
        showDecks();
    }

    public void select(MouseEvent event){
        //Si recuperano info su carta scelta cosi da recuperare costo (check per sconti). Per ogni risorsa, Alert.CONFIRMATION
        //per chiedere da dove recuperarla. Finito il ciclo, ultimo Alert per chiedere conferma: se ok si manda pack, altrimenti
        //si chiude stage.
        String card = ((Node) event.getSource()).getId();
        putInfo(card);
        String ind = askInd();

        //ASK INDEX
        if (ind == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("You must select the index!");
            alert.showAndWait();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            return;
        }
        action.put("ind",ind);

        //CONSTRUCT ARRAY OF COSTS (FROM THE SELECTED CARD)
        ArrayList<String> discounts = new ArrayList<>();
        if (gui.getModelView().getLeaders(gui.getModelView().getName()).get("state0").equalsIgnoreCase("active"))
            discounts.add(Cards.getDiscountById(Integer.parseInt(gui.getModelView().getLeaders(gui.getModelView().getName()).get("leader0"))));
        if (gui.getModelView().getLeaders(gui.getModelView().getName()).get("state1").equalsIgnoreCase("active"))
            discounts.add(Cards.getDiscountById(Integer.parseInt(gui.getModelView().getLeaders(gui.getModelView().getName()).get("leader1"))));
        ArrayList<String> cost;
        if (gui.getModelView().getDevelopDecks()[Integer.parseInt(action.get("column"))][Integer.parseInt(action.get("row"))]!=0) {
            cost = Cards.getCostById(gui.getModelView().getDevelopDecks()[Integer.parseInt(action.get("column"))][Integer.parseInt(action.get("row"))], discounts);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("There are no more cards in this deck! Try another one!");
            alert.showAndWait();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            return;
        }

        //LOOP WHERE EACH RESOURCE IS ASKED TO THE USER.
        int i = 1;
        for (String x : cost) {
            if (x != null) {
                String choice = askResource(x);
                if (choice == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setContentText("You must chose for each resource!");
                    alert.showAndWait();
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                    return;
                } else {
                    action.put("res"+i,choice.toLowerCase());
                    i++;
                }
            }
        }

        //ASK THE USER IF HE WANTS TO CONFIRM THE MOVE.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Do you want to confirm?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK) {
            action.clear();
            ((Node)(event.getSource())).getScene().getWindow().hide();
            return;
        }

        //NOTIFIES THE LISTENER AND CLOSES THE WINDOW
        action.put("action","buy");
        action.put("player",gui.getModelView().getName());
        gui.getModelView().setActiveTurn(false);
        Message message= new BuyMessage(action);
        gui.getListeners().fireUpdates("buy", message);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        //stage.close();
    }

    private void showDecks() {
        Scene decks = gui.getSceneFromName("buy.fxml");
        stage = new Stage();
        stage.setScene(decks);
        stage.setTitle("Buy");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void putInfo(String card) {
        switch (card) {
            case "dev00":
                action.put("row", "0");
                action.put("column", "0");
                break;
            case "dev01":
                action.put("row", "0");
                action.put("column", "1");
                break;
            case "dev02":
                action.put("row", "0");
                action.put("column", "2");
                break;
            case "dev03":
                action.put("row", "0");
                action.put("column", "3");
                break;
            case "dev10":
                action.put("row", "1");
                action.put("column", "0");
                break;
            case "dev11":
                action.put("row", "1");
                action.put("column", "1");
                break;
            case "dev12":
                action.put("row", "1");
                action.put("column", "2");
                break;
            case "dev13":
                action.put("row", "1");
                action.put("column", "3");
                break;
            case "dev20":
                action.put("row", "2");
                action.put("column", "0");
                break;
            case "dev21":
                action.put("row", "2");
                action.put("column", "1");
                break;
            case "dev22":
                action.put("row", "2");
                action.put("column", "2");
                break;
            case "dev23":
                action.put("row", "2");
                action.put("column", "3");
                break;
        }
    }

    private String askResource (String resource) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //I don't know..
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Where do you want to take resource "+resource+" from?");

        ButtonType buttonTypeOne = new ButtonType("small");
        ButtonType buttonTypeTwo = new ButtonType("mid");
        ButtonType buttonTypeThree = new ButtonType("big");
        ButtonType buttonTypeFour = new ButtonType("sp1");
        ButtonType buttonTypeFive = new ButtonType("sp2");
        ButtonType buttonTypeSix = new ButtonType("strongbox");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree,buttonTypeFour,buttonTypeFive,buttonTypeSix);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            return buttonTypeOne.getText();
        } else if (result.get() == buttonTypeTwo) {
            return buttonTypeTwo.getText();
        } else if (result.get() == buttonTypeThree) {
            return buttonTypeThree.getText();
        } else if (result.get() == buttonTypeFour) {
            return buttonTypeFour.getText();
        }else if (result.get() == buttonTypeFive) {
            return buttonTypeFive.getText();
        }else if (result.get() == buttonTypeSix) {
            return buttonTypeSix.getText();
        } else {
            //what to do?? :user chose CANCEL or closed the dialog
            return null;
        }
    }

    private String askInd () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //I don't know..
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Insert your personal board slot index in which you want to place the card, number between 0 and 2");

        ButtonType buttonTypeOne = new ButtonType("0");
        ButtonType buttonTypeTwo = new ButtonType("1");
        ButtonType buttonTypeThree = new ButtonType("2");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            return buttonTypeOne.getText();
        } else if (result.get() == buttonTypeTwo) {
            return buttonTypeTwo.getText();
        } else if (result.get() == buttonTypeThree) {
            return buttonTypeThree.getText();
        } else {
            //what to do?? :user chose CANCEL or closed the dialog
            return null;
        }
    }

    public void updateDecks() {
        ObservableList<Node> children = decks.getChildren();
        int[][] modelDecks = gui.getModelView().getDevelopDecks();
        for (Node node : children) {
            if (node instanceof ImageView) {
                int row;
                if (GridPane.getRowIndex(node) == 0)
                    row = 2;
                else if (GridPane.getRowIndex(node) == 2)
                    row = 0;
                else row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (modelDecks[col][row] != 0) {
                    ImageView image = (ImageView) node;
                    image.setImage(new Image("/PNG/cards/dc_" + modelDecks[col][row] + ".png"));
                } else {
                    ImageView image = (ImageView) node;
                    image.setImage(null);
                }
            }
        }
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
