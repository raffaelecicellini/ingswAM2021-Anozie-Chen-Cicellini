package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Cards;
import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BuyController implements GUIController{
    private GUI gui;
    private Map<String, String> action =  new HashMap<>() {{put("action","buy");put("player",gui.getModelView().getName());}};

    public void buy(){
        //Metodo chiamato quando utente da board schiaccia su tasto buy. Mostra un nuovo stage buy.fxml da cui non si può uscire
        //se non dopo aver fatto mossa o aver chiuso la finestra
        if (!gui.getModelView().isActiveTurn()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("It is not your turn!");
            alert.showAndWait();
            return;
        }
        if (!gui.getModelView().isDoneMandatory()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("You have already done a mandatory move!");
            alert.showAndWait();
            return;
        }
        action.entrySet().removeIf(x->x.getKey().startsWith("res"));
        action.remove("row");
        action.remove("column");
        action.remove("ind");
        showDecks();
    }

    public void select(ActionEvent event){
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
            return;
        }

        //NOTIFIES THE LISTENER AND CLOSES THE WINDOW
        gui.getModelView().setActiveTurn(false);
        gui.getListeners().fireUpdates("buy", action);
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private void showDecks() {
        Stage stage = new Stage();
        stage.setScene(gui.getSceneFromName("buy.fxml"));
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

    public String askResource (String resource) {
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

    public String askInd () {
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

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
