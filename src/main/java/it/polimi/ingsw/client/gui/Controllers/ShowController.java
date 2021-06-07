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

import java.util.List;
import java.util.Map;

public class ShowController extends GUIController {
    private GUI gui;

    @FXML
    private ImageView small, mid1, mid2, big1, big2, big3, leaderRes00, leaderRes01, leaderRes10, leaderRes11;
    @FXML
    private Label blue_qty, purple_qty, grey_qty, yellow_qty, sp_leader0, sp_leader1;
    @FXML
    private ImageView dev0, dev1, dev2, leader0, leader1, tile0, tile1, tile2;
    @FXML
    private ImageView pos;


    /**
     * This method is called by the Board Controller whenever tha player wants to see another player's board.
     * It sets on a board all the information from the ModelView.
     *
     * @param name the name of the player that the player wants to see.
     */
    public void prepareShow(String name) {
        //Metodo chiamato quando utente vuole vedere situazione del player name. Prepara il file show.fxml con le info di
        //tale player, poi lo mostra in un nuovo stage (da cui non si esce se non chiudendolo)

        if (gui.getModelView().getPlayers().contains(name)) {

            // LEADERS
            Map<String, String> leaders = gui.getModelView().getLeaders(name);
            ImageView[] leader = new ImageView[]{leader0, leader1};
            for (int i = 0; i < leaders.size() / 2; i++) {
                if (leaders.containsKey("state" + i))
                    if (leaders.get("state" + i).equalsIgnoreCase("active"))
                        leader[i].setImage(new Image("/PNG/cards/lc_" + leaders.get("leader" + i) + ".png"));
                    else leader[i].setImage(null);
            }

            // DEPOSITS
            Map<String, String> deposits = gui.getModelView().getDeposits(name);
            ImageView[] mid = new ImageView[]{mid1, mid2};
            if (Integer.parseInt(deposits.get("smallqty")) == 1)
                small.setImage(new Image("/PNG/marbles_bg/" + deposits.get("smallres").toLowerCase() + "_ball.png"));
            else small.setImage(null);

            for (int i = 0; i < 2; i++) {
                if (i < Integer.parseInt(deposits.get("midqty")))
                    mid[i].setImage(new Image("/PNG/marbles_bg/" + deposits.get("midres").toLowerCase() + "_ball.png"));
                else mid[i].setImage(null);
            }

            ImageView[] big = new ImageView[]{big1, big2, big3};
            for (int i = 0; i < 3; i++) {
                if (i < Integer.parseInt(deposits.get("bigqty")))
                    big[i].setImage(new Image("/PNG/marbles_bg/" + deposits.get("bigres").toLowerCase() + "_ball.png"));
                else big[i].setImage(null);
            }

            ImageView[] leaderRes0 = new ImageView[]{leaderRes00, leaderRes01};
            ImageView[] leaderRes1 = new ImageView[]{leaderRes10, leaderRes11};
            for (int i = 0; i < 2; i++) {
                leaderRes0[i].setImage(null);
                leaderRes1[i].setImage(null);
            }
            if (deposits.containsKey("sp1res")) {
                if (deposits.get("sp1res").equalsIgnoreCase(Cards.getResourceById(Integer.parseInt(leaders.get("leader0"))))) {
                    for (int i = 0; i < leaderRes0.length; i++) {
                        if (i > Integer.parseInt(deposits.get("sp1qty")) - 1) leaderRes0[i].setImage(null);
                        else leaderRes0[i].setImage(new Image(getPath(deposits.get("sp1res"))));
                    }
                } else if (deposits.get("sp1res").equalsIgnoreCase(Cards.getResourceById(Integer.parseInt(leaders.get("leader1"))))) {
                    for (int i = 0; i < leaderRes1.length; i++) {
                        if (i > Integer.parseInt(deposits.get("sp1qty")) - 1) leaderRes1[i].setImage(null);
                        else leaderRes1[i].setImage(new Image(getPath(deposits.get("sp1res"))));
                    }
                }

                if (deposits.containsKey("sp2res")) {
                    if (deposits.get("sp2res").equalsIgnoreCase(Cards.getResourceById(Integer.parseInt(leaders.get("leader0"))))) {
                        for (int i = 0; i < leaderRes0.length; i++) {
                            if (i > Integer.parseInt(deposits.get("sp2qty")) - 1) leaderRes0[i].setImage(null);
                            else leaderRes0[i].setImage(new Image(getPath(deposits.get("sp2res"))));
                        }
                    } else if (deposits.get("sp2res").equalsIgnoreCase(Cards.getResourceById(Integer.parseInt(leaders.get("leader1"))))) {
                        for (int i = 0; i < leaderRes1.length; i++) {
                            if (i > Integer.parseInt(deposits.get("sp2qty")) - 1) leaderRes1[i].setImage(null);
                            else leaderRes1[i].setImage(new Image(getPath(deposits.get("sp2res"))));
                        }
                    }
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
            ImageView[] devs = new ImageView[]{dev0, dev1, dev2};
            for (int i = 0; i < slots.size(); i++) {
                if (gui.getModelView().getTopId(slots.get(i)) > 0)
                    devs[i].setImage(new Image("/PNG/cards/dc_" + gui.getModelView().getTopId(slots.get(i)) + ".png"));
                else devs[i].setImage(null);
            }

            // TILES
            Tile[] tiles = gui.getModelView().getTiles(name);
            ImageView[] tilesImages = new ImageView[]{tile0, tile1, tile2};
            for (int i = 0; i < tiles.length; i++) {
                if (!tiles[i].isActive() && !tiles[i].isDiscarded())
                    tilesImages[i].setImage(new Image("/PNG/punchboard/quadrato" + (i + 2) + ".png"));
                else if (tiles[i].isActive() && !tiles[i].isDiscarded())
                    tilesImages[i].setImage(new Image("/PNG/punchboard/active" + (i + 2) + ".png"));
                else if (tiles[i].isDiscarded())
                    tilesImages[i].setImage(null);
            }

            // POSITION
            if (pos.getImage() == null)
                pos.setImage(new Image("/PNG/punchboard/red_cross.png"));
            setPosition(pos, gui.getModelView().getPosition(name));

            Scene show = gui.getSceneFromName("show.fxml");
            Stage stage = new Stage();
            stage.setScene(show);
            stage.setTitle("Show");
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.setResizable(false);
            stage.show();
        }
    }

    /**
     * @param gui the gui to be set
     * @see GUIController
     */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }
}