package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResourceController extends GUIController {
    private GUI gui;
    private int resources;
    private Map<String, String> action = new HashMap<>();
    private int count = 1;

    @FXML
    Label label;

    /**
     * Utility method used for updating the label that informs the player on how many resources he can choose.
     *
     * @param resources it's the number of resources the player can choose.
     */
    public void setResources(int resources) {
        this.resources = resources;
        label.setText("You can choose " + resources + " initial resources. Make your choice!");
    }

    /**
     * This method is called when the player chose his resources. Once the player confirms it will change the scene to
     * board.fxml
     *
     * @param event is the event caught once the player clicks on an Imageview which represents a resource.
     */
    public void chosen(MouseEvent event) {
        //Controlla colore scelto, mostra Alert.CONFIRMATION per chiedere posizione in cui salvare. Salva in mappa le due
        //info. Controlla se ha diritto a un'altra risorsa: se si rimane in questa schermata (cambiando label?), altrimenti
        //Alert.CONFIRMATION per chiedere se mossa va bene. Alla conferma manda pack, altrimenti pulisce mappa e resetta la scena
        //Se conferma cambia scena, altrimenti rimane su questa
        String position = position();
        if (position == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Error! You must select the resource's position.");
            alert.showAndWait();
            return;
        }
        String color = ((Node) event.getSource()).getId();
        action.put("res" + count, color);
        action.put("pos" + count, position);
        count++;
        if (count == resources + 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Do you want to confirm?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                count = 1;
                action.clear();
                return;
            }
            action.put("action", "chooseresources");
            action.put("player", gui.getModelView().getName());
            Message message = new ResourceMessage(action);
            gui.getListeners().fireUpdates("chooseresources", message);
            gui.changeScene("board.fxml");

        } else {
            label.setText("You have one more resource!");
            return;
        }

    }

    /**
     * Utility method used for displaying a confirmation that will ask the user where he wants to place the selected
     * resource.
     *
     * @return a String which represents the player's choice.
     */
    private String position() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //I don't know..
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Where do you want to place it");

        ButtonType buttonTypeOne = new ButtonType("small");
        ButtonType buttonTypeTwo = new ButtonType("mid");
        ButtonType buttonTypeThree = new ButtonType("big");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
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

    /**
     * @param gui the gui to be set
     * @see GUIController
     */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}