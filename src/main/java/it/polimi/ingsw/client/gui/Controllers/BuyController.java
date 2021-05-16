package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;

import java.util.Map;

public class BuyController implements GUIController{
    private GUI gui;
    private Map<String, String> info;

    public void buy(){
        //Metodo chiamato quando utente da board schiaccia su tasto buy. Mostra un nuovo stage buy.fxml da cui non si può uscire
        //se non dopo aver fatto mossa o aver chiuso la finestra
    }

    public void select(ActionEvent event){
        //Si recuperano info su carta scelta cosi da recuperare costo (check per sconti). Per ogni risorsa, Alert.CONFIRMATION
        //per chiedere da dove recuperarla. Finito il ciclo, ultimo Alert per chiedere conferma: se ok si manda pack, altrimenti
        //si chiude stage.
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
