package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ProduceController implements GUIController{
    private GUI gui;
    private Map<String, String> info;

    public void produce() throws IOException {
        //Metodo chiamato quando utente da board.fxml schiaccia su pulsante corrispondente. Mostra nuovo stage da cui
        //non si può uscire se non dopo averlo chiuso o aver fatto la mossa
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dio.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void select(ActionEvent event){
        //Metodo chiamato quando utente seleziona una produzione da produce.fxml. Prima si recupera produzione scelta,
        //poi per ogni risorsa di input si chiede da dove prenderla mediante Alert.CONFIRMATION. Salva info in mappa



    }

    public void confirm (ActionEvent event){
        //Metodo chiamato quando utente conferma la mossa. Check per controllare se almeno una produzione è stata attivata
        //(altrimenti Alert.ERROR). Alert.CONFIRMATION per chiedere conferma: se si pack inviato, altrimenti si chiude stage
        //e si torna a board.fxml
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
