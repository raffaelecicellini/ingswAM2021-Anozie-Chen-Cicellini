package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Tile;
import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ShowController implements GUIController{
    private GUI gui;

    @FXML private ImageView small, mid1, mid2, big1, big2, big3, leaderRes00, leaderRes01, leaderRes10, leaderRes11;
    @FXML private Label blue_qty, purple_qty, grey_qty, yellow_qty, sp_leader0, sp_leader1;
    @FXML private ImageView dev0, dev1, dev2, leader0, leader1, slot0, slot1, slot2;


    /**
     * This method is called by the Board Controller whenever tha player wants to see another player's board.
     * It sets on a board all the information from the ModelView.
     * @param name the name of the player that the player wants to see.
     */
    public void prepareShow(String name){
        //Metodo chiamato quando utente vuole vedere situazione del player name. Prepara il file show.fxml con le info di
        //tale player, poi lo mostra in un nuovo stage (da cui non si esce se non chiudendolo)

        if (gui.getModelView().getPlayers().contains(name)) {

            Image image = null;

            // DEPOSITS
            Map<String, String> deposits = gui.getModelView().getDeposits(name);
            if (Integer.parseInt(deposits.get("smallqty")) == 1) {
                image = new Image("@../PNG/marbles_bg/" + deposits.get("smallres").toLowerCase() + "_ball.png");
                small.setImage(image);
            }
            for (int i = 0; i < Integer.parseInt(deposits.get("midqty")); i++) {
                image = new Image("@../PNG/marbles_bg/" + deposits.get("midres").toLowerCase() + "_ball.png");
                if (i == 0) mid1.setImage(image);
                else if (i == 1) mid2.setImage(image);
            }
            for (int i = 0; i < Integer.parseInt(deposits.get("bigqty")); i++) {
                image = new Image("@../PNG/marbles_bg/" + deposits.get("bigres").toLowerCase() + "_ball.png");
                if (i == 0) big1.setImage(image);
                else if (i == 1) big2.setImage(image);
                else if (i == 2) big3.setImage(image);
            }
            if (deposits.containsKey("sp1res")) {
                for (int i = 0; i < Integer.parseInt(deposits.get("sp1qty")); i++) {
                    image = new Image("@../PNG/marbles_bg/" + deposits.get("sp1res").toLowerCase() + "_ball.png");
                    if (sp_leader0.getText().equalsIgnoreCase("sp1")) {
                        if (i == 0) leaderRes00.setImage(image);
                        else if (i == 1) leaderRes01.setImage(image);
                    }
                    else if (sp_leader1.getText().equalsIgnoreCase("sp1")) {
                        if (i == 0) leaderRes10.setImage(image);
                        else if (i == 1) leaderRes11.setImage(image);
                    }
                }
            }
            if (deposits.containsKey("sp2res")) {
                for (int i = 0; i < Integer.parseInt(deposits.get("sp2qty")); i++) {
                    image = new Image("@../PNG/marbles_bg/" + deposits.get("sp2res").toLowerCase() + "_ball.png");
                    if (sp_leader0.getText().equalsIgnoreCase("sp2")) {
                        if (i == 0) leaderRes00.setImage(image);
                        else if (i == 1) leaderRes01.setImage(image);
                    }
                    else if (sp_leader1.getText().equalsIgnoreCase("sp2")) {
                        if (i == 0) leaderRes10.setImage(image);
                        else if (i == 1) leaderRes11.setImage(image);
                    }
                }
            }

            // STRONGBOX
            Map<String, String> strongbox = gui.getModelView().getStrongbox(name);
            for (int i = 0; i < strongbox.size()/2; i++) {
                switch (strongbox.get("strres" + i).toUpperCase()) {
                    case "BLUE":
                        blue_qty.setText("x " + strongbox.get("strqty" + i));
                        break;
                    case "YELLOW":
                        yellow_qty.setText("x " + strongbox.get("strqty" + i));
                        break;
                    case "GREY":
                        grey_qty.setText("x " + strongbox.get("strqty" + i));
                        break;
                    case "PURPLE":
                        purple_qty.setText("x " + strongbox.get("strqty" + i));
                        break;
                }
            }

            // SLOTS
            for (int i = 0; i < gui.getModelView().getSlots(name).size(); i++) {
                if (gui.getModelView().getTopId(gui.getModelView().getSlots(name).get(i)) > 0) {
                    image = new Image("/PNG/cards/dc_" + gui.getModelView().getTopId(gui.getModelView().getSlots(name).get(i)) + ".png");
                    if (i == 0) dev0.setImage(image);
                    else if (i == 1) dev1.setImage(image);
                    else if (i == 2) dev2.setImage(image);
                }
            }

            // LEADERS
            Map<String, String> leaders = gui.getModelView().getLeaders(name);
            for (int i = 0; i < leaders.size() / 2; i++) {
                if (leaders.get("state" + i).equalsIgnoreCase("active")) {
                    // todo (da aggiungere controllo sp1 o sp2)
                    image = new Image("../PNG/cards/lc_" + leaders.get("leader" + i));
                    if (i == 0) leader0.setImage(image);
                    else if (i == 1) leader1.setImage(image);
                }
            }

            Tile[] tiles = gui.getModelView().getTiles(name);
            for (int i = 0; i < tiles.length; i++) {
                if (!tiles[i].isActive() && !tiles[i].isDiscarded()) image = new Image("../PNG/punchboard/quadrato" + (i + 2) + ".png");
                else if (tiles[i].isActive()) image = new Image("../PNG/punchboard/active" + (i + 2) + ".png");
                //else if (tiles[i].isDiscarded()) image = null;

                switch (i) {
                    case 0:
                        slot0.setImage(image);
                        break;
                    case 1:
                        slot1.setImage(image);
                        break;
                    case 2:
                        slot2.setImage(image);
                        break;
                }
            }

            //gui.getModelView().getPosition(name);
        }

        Scene produce = gui.getSceneFromName("show.fxml");
        Stage stage = new Stage();
        stage.setScene(produce);
        stage.setTitle("Produce");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
