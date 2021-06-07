package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

import java.util.*;

public class LeadersController extends GUIController {
    private GUI gui;
    @FXML
    private ImageView leader1, leader2, leader3, leader4;

    private Map<String, String> action = new HashMap<>();
    private int count = 1;

    /**
     * This method displays the leathers the player can choose.
     *
     * @param location is the list of leaders the player can choose.
     */
    public void setLeaders(ArrayList<String> location) {
        List<ImageView> list = new ArrayList<>(Arrays.asList(leader1, leader2, leader3, leader4));
        Image img;
        for (int i = 0; i < location.size(); i++) {
            img = new Image(location.get(i));
            list.get(i).setImage(img);
        }
        setShadesOfGray();
    }

    /**
     * This method is called once the player selected a leader. It will just save the information about the move.
     *
     * @param event is the event caught when the players clicks on a leader.
     */
    public void chosen(MouseEvent event) {
        //Controlla leader selezionati: se in numero corretto li mette in mappa, poi Alert.INFORMATION per chiedere conferma
        //(showandWait). Se utente conferma spedisco il pack e vado su board.fxml, altrimenti pulisco mappa e rimango qua
        //Se in numero errato Alert.ERROR notificando errore e rimango su questa scena
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        ColorAdjust normal = new ColorAdjust();
        normal.setSaturation(0);
        String selected = ((Node) event.getSource()).getId();
        selected = selected.replaceAll("[^0-9]", "");
        ImageView card = ((ImageView) event.getSource());
        ColorAdjust color = (ColorAdjust) card.getEffect();
        if (color.getSaturation() == 0) {
            String ind = getKeyByValue(action, selected);
            if (ind != null) action.remove(ind);
            card.setEffect(grayscale);
            count--;
            return;
        }
        if (count == 3) return;
        card.setEffect(normal);
        action.put("ind" + count, selected);
        count++;
    }

    /**
     * This method is called when the user clicks on the "confirm" button. It asks the player if he wants to confirm.
     * And changes scene.
     *
     * @param event is the event caught when the user clicks on the "confirm" button.
     */
    public void confirm(ActionEvent event) {
        if (count == 3) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Do you want to confirm?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                action.put("action", "chooseleaders");
                action.put("player", gui.getModelView().getName());
                Message message = new LeaderMessage(action);
                gui.getListeners().fireUpdates("chooseleaders", message);
                gui.changeScene("board.fxml");
            } else {
                action.clear();
                setShadesOfGray();
                count = 1;
                return;
            }
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

    /**
     * Utility method used for setting the saturation of each imageview.
     */
    private void setShadesOfGray() {
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
        List<ImageView> list = new ArrayList<>(Arrays.asList(leader1, leader2, leader3, leader4));
        for (ImageView x : list)
            x.setEffect(grayscale);
    }

    /**
     * This method returns the key of a certain map value.
     *
     * @param map   is the map
     * @param value is the value
     * @return the key of the value.
     */
    private static String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}