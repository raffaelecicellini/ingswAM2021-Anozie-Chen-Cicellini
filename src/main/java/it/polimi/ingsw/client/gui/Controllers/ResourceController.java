package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class ResourceController implements GUIController {
    private GUI gui;
    private int res;
    private Map<String, String> info;
    private int count=1;

    @FXML Label label;

    public void setResources(int resources) {
        this.res=resources;
        label.setText("You can choose "+res+" initial resources. Make your choice!");
    }

    public void chosen(ActionEvent event){
        //Controlla colore scelto, mostra Alert.CONFIRMATION per chiedere posizione in cui salvare. Salva in mappa le due
        //info. Controlla se ha diritto a un'altra risorsa: se si rimane in questa schermata (cambiando label?), altrimenti
        //Alert.CONFIRMATION per chiedere se mossa va bene. Alla conferma manda pack, altrimenti pulisce mappa e resetta la scena
        //Se conferma cambia scena, altrimenti rimane su questa
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
