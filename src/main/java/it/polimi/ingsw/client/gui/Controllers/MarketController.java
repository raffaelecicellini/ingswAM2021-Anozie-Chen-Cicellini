package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;

import java.util.Map;

public class MarketController implements GUIController{
    private GUI gui;
    private Map<String, String> info;

    public void select(ActionEvent event){
        //Recupera info su bottone (target) che è stato schiacciato per capire row/col scelta. A partire da questo recupera
        //info da modelView. Ciclo per ogni risorsa che produce Alert.CONFIRMATION per chiedere dove salvare risorsa.
        //Una volta finite le risorse Alert.CONFIRMATION per chiedere conferma. Se ok manda pack, altrimenti svuota mappa,
        //chiude schermata e si ritorna a board.fxml
    }

    //metodo per creare un nuovo stage?
    public void market(){
        //Chiamato quando utente da board schiaccia su bottone corrispondente. Viene creato nuovo stage con scena market.fxml
        //da cui non si può uscire senza averlo chiuso o senza aver finito la mossa.
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
