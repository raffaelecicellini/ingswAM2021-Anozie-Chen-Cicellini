package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Cards;
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
import java.util.List;
import java.util.Map;

public class ShowController extends GUIController{
    private GUI gui;

    @FXML private ImageView small, mid1, mid2, big1, big2, big3, leaderRes00, leaderRes01, leaderRes10, leaderRes11;
    @FXML private Label blue_qty, purple_qty, grey_qty, yellow_qty, sp_leader0, sp_leader1;
    @FXML private ImageView dev0, dev1, dev2, leader0, leader1, slot0, slot1, slot2;
    @FXML private ImageView pos;


    /**
     * This method is called by the Board Controller whenever tha player wants to see another player's board.
     * It sets on a board all the information from the ModelView.
     * @param name the name of the player that the player wants to see.
     */
    public void prepareShow(String name) {
        //Metodo chiamato quando utente vuole vedere situazione del player name. Prepara il file show.fxml con le info di
        //tale player, poi lo mostra in un nuovo stage (da cui non si esce se non chiudendolo)

        if (gui.getModelView().getPlayers().contains(name)) {

            Image image;

            // DEPOSITS
            Map<String, String> deposits = gui.getModelView().getDeposits(name);
            ImageView[] mid = new ImageView[] {mid1, mid2};
            if (Integer.parseInt(deposits.get("smallqty")) == 1)
                image = new Image("/PNG/marbles_bg/" + deposits.get("smallres").toLowerCase() + "_ball.png");
            else image = null;
            small.setImage(image);

            for (int i = 0; i < 2; i++) {
                if (i < Integer.parseInt(deposits.get("midqty")))
                    image = new Image("/PNG/marbles_bg/" + deposits.get("midres").toLowerCase() + "_ball.png");
                else image = null;

                mid[i].setImage(image);
                /*switch (i) {
                    case 0:
                        mid1.setImage(image);
                        break;
                    case 1:
                        mid2.setImage(image);
                        break;
                }*/
            }

            ImageView[] big = new ImageView[] {big1, big2, big3};
            for (int i = 0; i < 3; i++) {
                if (i < Integer.parseInt(deposits.get("bigqty")))
                    image = new Image("/PNG/marbles_bg/" + deposits.get("bigres").toLowerCase() + "_ball.png");
                else image = null;

                big[i].setImage(image);
                /*switch (i) {
                    case 0:
                        big1.setImage(image);
                        break;
                    case 1:
                        big2.setImage(image);
                        break;
                    case 2:
                        big3.setImage(image);
                        break;
                }*/
            }

            if (deposits.containsKey("sp1res")) {
                ImageView[] leaderRes0 = new ImageView[] {leaderRes00, leaderRes01};
                ImageView[] leaderRes1 = new ImageView[] {leaderRes10, leaderRes11};
                for (int i = 0; i < 2; i++) {
                    if (i < Integer.parseInt(deposits.get("sp1qty")))
                        image = new Image("/PNG/marbles_bg/" + deposits.get("sp1res").toLowerCase() + "_ball.png");
                    else image = null;
                    if (sp_leader0.getText().equalsIgnoreCase("sp1")) {
                        leaderRes0[i].setImage(image);
                        /*if (i == 0) leaderRes00.setImage(image);
                        else if (i == 1) leaderRes01.setImage(image);*/
                    } else if (sp_leader1.getText().equalsIgnoreCase("sp1")) {
                        leaderRes1[i].setImage(image);
                        /*if (i == 0) leaderRes10.setImage(image);
                        else if (i == 1) leaderRes11.setImage(image);*/
                    }
                }
            }
            if (deposits.containsKey("sp2res")) {
                ImageView[] leaderRes0 = new ImageView[] {leaderRes00, leaderRes01};
                ImageView[] leaderRes1 = new ImageView[] {leaderRes10, leaderRes11};
                for (int i = 0; i < 2; i++) {
                    if (i < Integer.parseInt(deposits.get("sp2qty")))
                        image = new Image("/PNG/marbles_bg/" + deposits.get("sp2res").toLowerCase() + "_ball.png");
                    else image = null;
                    if (sp_leader0.getText().equalsIgnoreCase("sp2")) {
                        leaderRes0[i].setImage(image);
                        /*if (i == 0) leaderRes00.setImage(image);
                        else if (i == 1) leaderRes01.setImage(image);*/
                    } else if (sp_leader1.getText().equalsIgnoreCase("sp2")) {
                        leaderRes1[i].setImage(image);
                        /*if (i == 0) leaderRes10.setImage(image);
                        else if (i == 1) leaderRes11.setImage(image);*/
                    }
                }

                // STRONGBOX
                Map<String, String> strongbox = gui.getModelView().getStrongbox(name);
                for (int i = 0; i < strongbox.size() / 2; i++) {
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
                List<int[]> slots = gui.getModelView().getSlots(name);
                ImageView[] devs = new ImageView[] {dev0, dev1, dev2};
                for (int i = 0; i < slots.size(); i++) {
                    if (gui.getModelView().getTopId(slots.get(i)) > 0)
                        image = new Image("/PNG/cards/dc_" + gui.getModelView().getTopId(slots.get(i)) + ".png");
                    else image = null;

                    devs[i].setImage(image);
                    /*switch (i) {
                        case 0:
                            dev0.setImage(image);
                            break;
                        case 1:
                            dev1.setImage(image);
                            break;
                        case 2:
                            dev2.setImage(image);
                            break;
                    }*/
                }

                // LEADERS
                Map<String, String> leaders = gui.getModelView().getLeaders(name);
                ImageView[] leader = new ImageView[] {leader0, leader1};
                for (int i = 0; i < leaders.size() / 2; i++) {
                    if (leaders.get("state" + i).equalsIgnoreCase("active"))
                        image = new Image("/PNG/cards/lc_" + leaders.get("leader" + i)+".png");
                    else image = null;

                    leader[i].setImage(image);
                    /*switch (i) {
                        case 0:
                            leader0.setImage(image);
                            break;
                        case 1:
                            leader1.setImage(image);
                            break;
                    }*/
                }

                // TILES
                Tile[] tiles = gui.getModelView().getTiles(name);
                ImageView[] slot = new ImageView[] {slot0, slot1, slot2};
                for (int i = 0; i < tiles.length; i++) {
                    if (!tiles[i].isActive() && !tiles[i].isDiscarded())
                        image = new Image("/PNG/punchboard/quadrato" + (i + 2) + ".png");
                    else if (tiles[i].isActive())
                        image = new Image("/PNG/punchboard/active" + (i + 2) + ".png");
                    else if (tiles[i].isDiscarded()) image = null;

                    slot[i].setImage(image);
                    /*switch (i) {
                        case 0:
                            slot0.setImage(image);
                            break;
                        case 1:
                            slot1.setImage(image);
                            break;
                        case 2:
                            slot2.setImage(image);
                            break;
                    }*/
                }

                switch (gui.getModelView().getPosition(name)) {
                    case 0:
                }
            }

            // POSITION
            if (pos.getImage() == null)
                pos.setImage(new Image("/PNG/punchboard/red_cross.png"));
            setPosition(pos,gui.getModelView().getPosition(name));

            Scene show = gui.getSceneFromName("show.fxml");
            Stage stage = new Stage();
            stage.setScene(show);
            stage.setTitle("Show");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
