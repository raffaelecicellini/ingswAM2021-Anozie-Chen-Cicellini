package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LeadersController implements GUIController{
    private GUI gui;
    @FXML private ImageView leader1png, leader2png, leader3png, leader4png;
    @FXML private CheckBox leader1, leader2, leader3, leader4;

    private int leaderCount = 0;
    private Map<String, String> leaders;

    public void setLeaders(ArrayList<String> location) {
        List<ImageView> list= new ArrayList<>(Arrays.asList(leader1png, leader2png, leader3png, leader4png));
        Image img;
        for (int i=0; i<location.size(); i++){
            img= new Image(location.get(i));
            list.get(i).setImage(img);
        }
    }

    public void chosen(ActionEvent event){
        //Controlla leader selezionati: se in numero corretto li mette in mappa, poi Alert.INFORMATION per chiedere conferma
        //(showandWait). Se utente conferma spedisco il pack e vado su board.fxml, altrimenti pulisco mappa e rimango qua
        //Se in numero errato Alert.ERROR notificando errore e rimango su questa scena

    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
