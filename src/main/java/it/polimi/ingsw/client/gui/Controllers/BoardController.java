package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;

public class BoardController implements GUIController{
    private GUI gui;

    //Tutti i metodi seguenti sono in risposta alla pressione di un tasto. Recuperano controller corrispondente alla scena
    //da GUI e chiamano su di essi il metodo appropriato
    public void market(ActionEvent event){

    }

    public void buy(ActionEvent event){

    }

    public void produce(ActionEvent event){

    }

    public void activate(ActionEvent event){
        //Tramite Alert.CONFIRMATION chiedono indice leader da attivare, poi invia pack
    }

    public void discard(ActionEvent event){
        //Tramite Alert.CONFIRMATION chiedono indice leader da scartare, poi invia pack
    }

    public void show(ActionEvent event){
        //Tramite Alert.CONFIRMATION chiedono nome utente da visualizzare, poi chiama metodo show su ShowController
    }

    public void endTurn(ActionEvent event){
        //Chiede conferma fine turno con Alert.CONFIRMATION poi invia pack.
    }

    //Metodi set per cambiare le informazioni presenti in schermata?

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
