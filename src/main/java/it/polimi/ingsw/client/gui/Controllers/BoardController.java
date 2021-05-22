package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Tile;
import it.polimi.ingsw.client.Cards;
import it.polimi.ingsw.client.gui.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

import java.util.*;

public class BoardController extends GUIController{
    private GUI gui;
    @FXML private Label blue_qty, yellow_qty, grey_qty, purple_qty;
    @FXML private ImageView small, mid1, mid2, big1, big2, big3;
    @FXML private ImageView leader_res00, leader_res01, leader_res10, leader_res11;
    @FXML private ImageView dev0, dev1, dev2;
    @FXML private ImageView leader0, leader1;
    @FXML private Label sp_leader0, sp_leader1;
    @FXML private Label slot0, slot1, slot2;
    @FXML
    private ImageView tile0,tile1,tile2;
    @FXML
    private ImageView pos,blackCross;

    //Tutti i metodi seguenti sono in risposta alla pressione di un tasto. Recuperano controller corrispondente alla scena
    //da GUI e chiamano su di essi il metodo appropriato

    /**
     * Method called when a user clicks on the market button in board.fxml. It checks if the user can do it (his turn and
     * !doneMandatory), then calls the market() method on the MarketController
     * @param event event generated by the press of the button
     */
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

    /**
     * Method called when a user clicks on the buy button in board.fxml. It checks if the user can do it (his turn and
     * !doneMandatory), then calls the buy() method on the BuyController
     * @param event event generated by the press of the button
     */
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

    /**
     * Method called when a user clicks on the produce button in board.fxml. It checks if the user can do it (his turn and
     * !doneMandatory), then calls the produce() method on the ProduceController
     * @param event event generated by the press of the button
     */
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

    /**
     * Method called when a user clicks on the activate button in board.fxml. It checks if the user can do it (his turn),
     * then shows a custom Alert asking the user to pick the leader he wants to activate
     * @param event event generated by the press of the button
     */
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
        confirm.setContentText("You chose to activate leader in position "+pack.get("pos")+". Is it ok?");
        confirm.initModality(Modality.APPLICATION_MODAL);
        result=confirm.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK){
            gui.getModelView().setActiveTurn(false);
            gui.getListeners().fireUpdates(pack.get("action"), pack);
        }
    }

    /**
     * Method called when a user clicks on the discard button in board.fxml. It checks if the user can do it (his turn),
     * then shows a custom Alert asking the user to pick the leader he wants to discard
     * @param event event generated by the press of the button
     */
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
        confirm.setContentText("You chose to discard leader in position "+pack.get("pos")+". is it ok?");
        confirm.initModality(Modality.APPLICATION_MODAL);
        result=confirm.showAndWait();
        if (result.isPresent() && result.get()==ButtonType.OK){
            gui.getModelView().setActiveTurn(false);
            gui.getListeners().fireUpdates(pack.get("action"), pack);
        }
    }

    /**
     * Method called when a user clicks on the swap button in board.fxml. It checks if the user can do it (his turn),
     * then shows a custom Alert asking the user to pick the deposit he wants to swap (one alert for the first and one
     * for the second)
     * @param event event generated by the press of the button
     */
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

    /**
     * Method called when a user clicks on the show button in board.fxml. It checks if the user can do it (!soloGame),
     * then shows the user a choiceDialog asking the name of the player he wants to see
     * @param event event generated by the press of the button
     */
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

    /**
     * Method called when a user clicks on the endturn button in board.fxml. It checks if the user can do it (his turn and
     * doneMandatory), then sends the action gui's listener(s)
     * @param event event generated by the press of the button
     */
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

    /**
     * This method is used to update the deposits view of a player. It updates the images representing the state of the
     * deposits of the player
     */
    public void updateDeposits(){
        List<ImageView> mid= Arrays.asList(mid1, mid2);
        List<ImageView> big= Arrays.asList(big1, big2, big3);
        Map<String, String> deps=gui.getModelView().getDeposits(gui.getModelView().getName());

        if (Integer.parseInt(deps.get("smallqty"))==0) small.setImage(null);
        else small.setImage(new Image(getPath(deps.get("smallres"))));

        for (int i=0; i<mid.size(); i++){
            if (i> Integer.parseInt(deps.get("midqty")) -1) mid.get(i).setImage(null);
            else mid.get(i).setImage(new Image(getPath(deps.get("midres"))));
        }

        for (int i=0; i<big.size(); i++){
            if (i> Integer.parseInt(deps.get("bigqty")) -1) big.get(i).setImage(null);
            else big.get(i).setImage(new Image(getPath(deps.get("bigres"))));
        }

        if (deps.containsKey("sp1")) updateLeaderDeps(deps);
    }

    /**
     * This method is used when a user has at least one active Leadercard that gives a special deposit. It updates the
     * resources in the special deposit
     * @param deps the Map containing the info on the deposits of the player
     */
    private void updateLeaderDeps(Map<String, String> deps){
        Map<String, String> leaders= gui.getModelView().getLeaders(gui.getModelView().getName());
        List<ImageView> lead0= Arrays.asList(leader_res00, leader_res01);
        List<ImageView> lead1= Arrays.asList(leader_res10, leader_res11);

        System.out.println(deps.get("sp1res")+" "+deps.get("sp1qty"));

        if (deps.get("sp1res").equals(Cards.getResourceById(Integer.parseInt(leaders.get("leader0"))))){
            for (int i=0; i<lead0.size(); i++){
                if (i> Integer.parseInt(deps.get("sp1qty")) -1) lead0.get(i).setImage(null);
                else lead0.get(i).setImage(new Image(getPath(deps.get("sp1res"))));
            }
        }
        else if (deps.get("sp1res").equals(Cards.getResourceById(Integer.parseInt(leaders.get("leader1"))))){
            for (int i=0; i<lead1.size(); i++){
                if (i> Integer.parseInt(deps.get("sp1qty")) -1) lead1.get(i).setImage(null);
                else lead1.get(i).setImage(new Image(getPath(deps.get("sp1res"))));
            }
        }

        if (deps.containsKey("sp2res")){
            if (deps.get("sp2res").equals(Cards.getResourceById(Integer.parseInt(leaders.get("leader0"))))){
                for (int i=0; i<lead0.size(); i++){
                    if (i> Integer.parseInt(deps.get("sp2qty")) -1) lead0.get(i).setImage(null);
                    else lead0.get(i).setImage(new Image(getPath(deps.get("sp2res"))));
                }
            }
            else if (deps.get("sp2res").equals(Cards.getResourceById(Integer.parseInt(leaders.get("leader1"))))){
                for (int i=0; i<lead1.size(); i++){
                    if (i> Integer.parseInt(deps.get("sp2qty")) -1) lead1.get(i).setImage(null);
                    else lead1.get(i).setImage(new Image(getPath(deps.get("sp2res"))));
                }
            }
        }
    }

    /**
     * This method is used to update the strongbox view of a player. It changes the label that represents the quantity of
     * a certain resource in the strongbox
     */
    public void updateStrongbox(){
        Map<String, String> strongbox= gui.getModelView().getStrongbox(gui.getModelView().getName());
        List<Label> labels= Arrays.asList(blue_qty, purple_qty, grey_qty, yellow_qty);

        String curr;
        for (int i=0; i<labels.size(); i++){
            curr="strqty"+i;
            labels.get(i).setText("x "+strongbox.get(curr));
        }
    }

    /**
     * This method updates the Slots on the personal Board.
     */
    public void updateSlots() {
        Image image;
        List<int[]> slots = gui.getModelView().getSlots(gui.getModelView().getName());
        ImageView[] devs = new ImageView[]{dev0, dev1, dev2};
        Label[] slotsLabel = new Label[]{slot0, slot1, slot2};
        int lv;
        StringBuilder label = new StringBuilder();
        for (int slot = 0; slot < slots.size(); slot++) {
            lv = 0;
            label.setLength(0);
            if (gui.getModelView().getTopId(slots.get(slot)) > 0) {
                // set image
                image = new Image("/PNG/cards/dc_" + gui.getModelView().getTopId(slots.get(slot)) + ".png");

                // set label
                while (lv < slots.get(slot).length) {
                    if (slots.get(slot)[lv] != 0) {
                        if (lv != 0) label.append(" - ");

                        label.append(" LV").append(lv + 1).append(": ").append(Cards.getColorById(slots.get(slot)[lv]));
                        lv++;
                    } else break;
                }
            }
            else image = null;

            devs[slot].setImage(image);
            slotsLabel[slot].setText(label.toString());
        }
    }

    /**
     * This method updates the Leaders on the personal Board (after activate/discard leader actions).
     */
    public void updateLeader() {
        Image image;
        Map<String, String> leaders = gui.getModelView().getLeaders(gui.getModelView().getName());
        for (int i = 0; i < leaders.size() / 2; i++) {
            if (leaders.get("state" + i).equalsIgnoreCase("discarded")) image = null;
            else image = new Image("/PNG/cards/lc_" + leaders.get("leader" + i)+".png");

            switch (i) {
                case 0:
                    if (Integer.parseInt(leaders.get("leader" + i)) >= 7 &&
                            Integer.parseInt(leaders.get("leader" + i)) <= 10 &&
                            leaders.get("state" + i).equalsIgnoreCase("active")) {
                        if (Integer.parseInt(leaders.get("leader" + (i + 1))) >= 7 &&
                                Integer.parseInt(leaders.get("leader" + (i + 1))) <= 10 &&
                                leaders.get("state" + (i + 1)).equalsIgnoreCase("active"))
                            sp_leader0.setText("SP2");
                        else sp_leader0.setText("SP1");
                        sp_leader0.setOpacity(1);
                    }
                    sp_leader0.setOpacity(0);
                    leader0.setImage(image);
                    if (leaders.get("state" + i).equalsIgnoreCase("active")) leader0.setOpacity(1);
                    else if (leaders.get("state" + i).equalsIgnoreCase("available")) leader0.setOpacity(0.45);
                    break;
                case 1:
                    if (Integer.parseInt(leaders.get("leader" + i)) >= 7 &&
                            Integer.parseInt(leaders.get("leader" + i)) <= 10 &&
                            leaders.get("state" + i).equalsIgnoreCase("active")) {
                        if (Integer.parseInt(leaders.get("leader" + (i - 1))) >= 7 &&
                                Integer.parseInt(leaders.get("leader" + (i - 1))) <= 10 &&
                                leaders.get("state" + (i - 1)).equalsIgnoreCase("active"))
                            sp_leader1.setText("SP2");
                        else sp_leader1.setText("SP1");
                        sp_leader1.setOpacity(1);
                    }
                    sp_leader1.setOpacity(0);
                    leader1.setImage(image);
                    if (leaders.get("state" + i).equalsIgnoreCase("active")) leader1.setOpacity(1);
                    else if (leaders.get("state" + i).equalsIgnoreCase("available")) leader1.setOpacity(0.45);
                    break;
            }
        }
    }

    public void updateTiles() {
        Tile[] model = gui.getModelView().getTiles(gui.getModelView().getName());
        ImageView[] tiles = new ImageView[] {tile0,tile1,tile2};
        for (int i = 0; i < model.length; i++) {
            if (model[i].isActive() && !model[i].isDiscarded())
                tiles[i].setImage(new Image("/PNG/punchboard/active"+(i+2)+".png"));
            else if (!model[i].isActive() && !model[i].isDiscarded())
                tiles[i].setImage(new Image("/PNG/punchboard/quadrato"+(i+2)+".png"));
            else if (model[i].isDiscarded())
                tiles[i].setImage(null);
        }
    }

    public void updatePosition() {
        if (gui.getModelView().isSoloGame()) {
            if (blackCross.getImage() == null)
                blackCross.setImage(new Image("/PNG/punchboard/croce.png"));
            setPosition(blackCross,gui.getModelView().getBlackCross());
            blackCross.setLayoutX(blackCross.getLayoutX()+5);
            blackCross.setLayoutY(blackCross.getLayoutY()+5);
        }
        if (pos.getImage() == null)
            pos.setImage(new Image("/PNG/punchboard/red_cross.png"));
        setPosition(pos,gui.getModelView().getPosition(gui.getModelView().getName()));
    }

    /**
     * @see GUIController
     * @param gui the gui to be set
     */
    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
