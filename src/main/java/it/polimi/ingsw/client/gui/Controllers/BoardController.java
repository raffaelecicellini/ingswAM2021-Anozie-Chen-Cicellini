package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Modality;

import java.util.*;

public class BoardController implements GUIController{
    private GUI gui;

    //Tutti i metodi seguenti sono in risposta alla pressione di un tasto. Recuperano controller corrispondente alla scena
    //da GUI e chiamano su di essi il metodo appropriato
    public void market(ActionEvent event){
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        if (gui.getModelView().isDoneMandatory()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("You already did a mandatory action in this turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        MarketController controller= (MarketController) gui.getControllerFromName("market.fxml");
        controller.market();
    }

    public void buy(ActionEvent event){
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        if (gui.getModelView().isDoneMandatory()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("You already did a mandatory action in this turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        BuyController controller= (BuyController) gui.getControllerFromName("buy.fxml");
        controller.buy();
    }

    public void produce(ActionEvent event){
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        if (gui.getModelView().isDoneMandatory()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("You already did a mandatory action in this turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        ProduceController controller= (ProduceController) gui.getControllerFromName("produce.fxml");
        controller.produce();
    }

    public void activate(ActionEvent event){
        //Tramite Alert.CONFIRMATION chiedono indice leader da attivare, poi invia pack
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        Map<String, String> pack= new HashMap<>();
        pack.put("action", "activate");
        pack.put("player", gui.getModelView().getName());

        Alert activate= new Alert(Alert.AlertType.CONFIRMATION);
        activate.setHeaderText("Choose leader");
        activate.setContentText("Choose the leader you want to activate");
        ButtonType one=new ButtonType("Leader 0");
        ButtonType two= new ButtonType("Leader 1");
        activate.getButtonTypes().setAll(one, two);
        activate.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> result=activate.showAndWait();

        if (result.isPresent() && result.get()==one) {
            pack.put("pos", "0");
        }
        else if (result.isPresent() && result.get()==two) {
            pack.put("pos", "1");
        }
        else return;

        Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Choose leader");
        confirm.setContentText("You chose to activate leader in position "+pack.get("pos"));
        confirm.initModality(Modality.APPLICATION_MODAL);
        result=confirm.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK){
            gui.getModelView().setActiveTurn(false);
            gui.getListeners().fireUpdates(pack.get("action"), pack);
        }
    }

    public void discard(ActionEvent event){
        //Tramite Alert.CONFIRMATION chiedono indice leader da scartare, poi invia pack
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        Map<String, String> pack= new HashMap<>();
        pack.put("action", "discard");
        pack.put("player", gui.getModelView().getName());

        Alert discard= new Alert(Alert.AlertType.CONFIRMATION);
        discard.setHeaderText("Choose leader");
        discard.setContentText("Choose the leader you want to discard");
        ButtonType one=new ButtonType("Leader 0");
        ButtonType two= new ButtonType("Leader 1");
        discard.getButtonTypes().setAll(one, two);
        discard.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> result=discard.showAndWait();

        if (result.isPresent() && result.get()==one) {
            pack.put("pos", "0");
        }
        else if (result.isPresent() && result.get()==two) {
            pack.put("pos", "1");
        }
        else return;

        Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Choose leader");
        confirm.setContentText("You chose to discard leader in position "+pack.get("pos"));
        confirm.initModality(Modality.APPLICATION_MODAL);
        result=confirm.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK){
            gui.getModelView().setActiveTurn(false);
            gui.getListeners().fireUpdates(pack.get("action"), pack);
        }
    }

    public void swap(ActionEvent event){
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        Map<String, String> pack= new HashMap<>();
        List<String> deps= new ArrayList<>(Arrays.asList("small", "mid", "big"));
        if (gui.getModelView().getDeposits(gui.getModelView().getName()).containsKey("sp1res")) deps.add("sp1");
        if (gui.getModelView().getDeposits(gui.getModelView().getName()).containsKey("sp2res")) deps.add("sp2");

        ChoiceDialog<String> choices= new ChoiceDialog<>(deps.get(0), deps);
        choices.initModality(Modality.APPLICATION_MODAL);
        choices.setHeaderText("Swap");
        choices.setContentText("Choose the first deposit");
        Optional<String> result=choices.showAndWait();
        if (result.isPresent()){
            deps.remove(result.get());
            pack.put("source", result.get());
            choices=new ChoiceDialog<>(deps.get(0), deps);
            choices.initModality(Modality.APPLICATION_MODAL);
            choices.setHeaderText("Swap");
            choices.setContentText("Choose the second deposit");
            result=choices.showAndWait();
            result.ifPresent(s -> pack.put("dest", s));
        }

        if (pack.containsKey("source") && pack.containsKey("dest")){
            Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Swap");
            confirm.setContentText("First dep: "+pack.get("source")+" ; Second dep: "+pack.get("dest")+". Do you confirm the action?");
            confirm.initModality(Modality.APPLICATION_MODAL);
            Optional<ButtonType> res= confirm.showAndWait();
            if (result.isPresent() && res.get()==ButtonType.OK){
                pack.put("action", "swap");
                pack.put("player", gui.getModelView().getName());
                gui.getListeners().fireUpdates(pack.get("action"), pack);
            }
        }
    }

    public void show(ActionEvent event){
        //Tramite Alert.CONFIRMATION chiedono nome utente da visualizzare, poi chiama metodo show su ShowController
        if (gui.getModelView().isSoloGame()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("This action is available only for a multiplayer game!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }
        List<String> names=gui.getModelView().getPlayers();
        names.remove(gui.getModelView().getName());
        ChoiceDialog<String> choices= new ChoiceDialog<>(names.get(0), names);
        choices.setHeaderText("Show Player");
        choices.setContentText("Choose the player you want to see");
        choices.initModality(Modality.APPLICATION_MODAL);
        Optional<String> result=choices.showAndWait();
        if (result.isPresent()) {
            ShowController controller=(ShowController) gui.getControllerFromName("show.fxml");
            controller.prepareShow(result.get());
        }
    }

    public void endTurn(ActionEvent event){
        //Chiede conferma fine turno con Alert.CONFIRMATION poi invia pack.
        if (!gui.getModelView().isActiveTurn()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("It is not your turn!");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        if (!gui.getModelView().isDoneMandatory()){
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error! You can't do it!");
            error.setContentText("You must do one action between BUY, MARKET, PRODUCE before ending turn");
            error.initModality(Modality.APPLICATION_MODAL);
            error.showAndWait();
            return;
        }

        Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Ending Turn");
        confirm.setContentText("Are you sure you want to end your turn?");
        confirm.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> result= confirm.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK){
            Map<String, String> pack= new HashMap<>();
            pack.put("action", "endturn");
            pack.put("player", gui.getModelView().getName());
            gui.getListeners().fireUpdates(pack.get("action"), pack);
        }

    }

    //Metodi set per cambiare le informazioni presenti in schermata?

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
