package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import java.util.*;

public class LeadersController extends GUIController{
    private GUI gui;
    @FXML
    private ImageView leader1png, leader2png, leader3png, leader4png;
    @FXML
    private CheckBox leader1, leader2, leader3, leader4;

    public void setLeaders(ArrayList<String> location) {
        List<ImageView> list= new ArrayList<>(Arrays.asList(leader1png, leader2png, leader3png, leader4png));
        Image img;
        for (int i=0; i<location.size(); i++){
            img= new Image(location.get(i));
            list.get(i).setImage(img);
        }
    }

    public void chosen(ActionEvent event) {
        //Controlla leader selezionati: se in numero corretto li mette in mappa, poi Alert.INFORMATION per chiedere conferma
        //(showandWait). Se utente conferma spedisco il pack e vado su board.fxml, altrimenti pulisco mappa e rimango qua
        //Se in numero errato Alert.ERROR notificando errore e rimango su questa scena
        List<CheckBox> choice = new ArrayList<>(Arrays.asList(leader1, leader2, leader3, leader4));
        int n = 0;
        for (CheckBox x : choice)
            if (x.isSelected())
                n++;
        if (n != 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("Error! You must select 2 leaders!");
            alert.showAndWait();
            return;
        }
        Map<String, String> action = new HashMap<>();
        action.put("action", "chooseleaders");
        action.put("player", gui.getModelView().getName());
        for (int i = 0, j = 1; i < choice.size(); i++)
            if (choice.get(i).isSelected()) {
                action.put("ind" + j, Integer.toString(i + 1));
                j++;
            }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setContentText("Do you want to confirm?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK)
            return;
        //Message message= new LeaderMessage(action);
        //gui.getListeners().fireUpdates("chooseleaders", message);
        gui.changeScene("board.fxml");
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
