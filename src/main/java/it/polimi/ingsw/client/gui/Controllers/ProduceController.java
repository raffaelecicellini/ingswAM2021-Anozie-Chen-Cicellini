package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Cards;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class ProduceController extends GUIController {
    @FXML
    private Button base, confirm;

    @FXML
    private ImageView dev0, dev1, dev2;
    @FXML
    private ImageView leader0, leader1;


    private GUI gui;
    private Map<String, String> info = new HashMap<>();
    Stage stage;

    /**
     * This method is called by BoardController, whenever the player clicks on produce.
     */
    public void produce() {
        //Metodo chiamato quando utente da board.fxml schiaccia su pulsante corrispondente. Mostra nuovo stage da cui
        //non si può uscire se non dopo averlo chiuso o aver fatto la mossa

        info.put("prod0", "no");
        info.put("prod1", "no");
        info.put("prod2", "no");
        info.put("prod3", "no");
        info.put("prod4", "no");
        info.put("prod5", "no");


        Scene produce = gui.getSceneFromName("produce.fxml");
        stage = new Stage();
        stage.setScene(produce);
        stage.setTitle("Produce");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest((e) -> {
            info.clear();
        });
        //stage.setResizable(false);
        stage.show();
    }

    /**
     * This method is activated when the user clicks on the base production.
     *
     * @param event the mouse click of the user.
     */
    public void base(ActionEvent event) {
        //Metodo chiamato quando utente seleziona una produzione da produce.fxml. Prima si recupera produzione scelta,
        //poi per ogni risorsa di input si chiede da dove prenderla mediante Alert.CONFIRMATION. Salva info in mappa

        info.put("prod0", "yes");

        info.put("in01", chooseColor("in"));
        info.put("pos01", choosePos(info.get("in01")));
        info.put("in02", chooseColor("in"));
        info.put("pos02", choosePos(info.get("in02")));
        info.put("out0", chooseColor("out"));

    }

    /**
     * This method is activated when a player clicks on one of the develop cards. It asks the player from where he wants to
     * take his resources.
     *
     * @param event the mouse click of the player on a develop card.
     */
    public void selectDev(MouseEvent event) {

        // to get the index of the slot
        int slot = Integer.parseInt(((Node) event.getSource()).getId().replaceAll("[^0-9]", ""));
        ImageView[] devs = new ImageView[]{dev0, dev1, dev2};

        if (devs[slot].getImage() == null) return;

        /*switch (slot) {
            case 0:
                if (dev0.getImage() == null) return;
                break;
            case 1:
                if (dev1.getImage() == null) return;
                break;
            case 2:
                if (dev2.getImage() == null) return;
                break;
            default:
                return;
        }*/

        info.put("prod" + (slot + 1), "yes");
        List<String> input = Cards.getInputById(gui.getModelView().getTopId(gui.getModelView().getSlots(gui.getModelView().getName()).get(slot)));
        int i = 1;
        for (String res : input) {
            info.put("pos" + (slot + 1) + i, choosePos(res));
            i++;
        }
    }

    /**
     * This method is activated when the player clicks on one of the leader productions. It asks which resource and
     * from where he wants to take it and the resource he wants to produce.
     *
     * @param event when the player clicks on a leader.
     */
    public void selectLeader(MouseEvent event) {

        // to get the index of the slot
        int leader = Integer.parseInt(((Node) event.getSource()).getId().replaceAll("[^0-9]", ""));

        ImageView[] leaders = new ImageView[]{leader0, leader1};
        if (leaders[leader].getImage() == null) return;

        int index = gui.getModelView().getLeaderProdOrder(leader);
        info.put("prod" + (index + 4), "yes");
        String input = Cards.getProductionById(Integer.parseInt(gui.getModelView().getLeaders(gui.getModelView().getName()).get("leader" + leader)));
        info.put("pos" + (index + 4) + "1", choosePos(input));

        info.put("out" + (index + 4), chooseColor("out"));
    }

    /**
     * This method is activated when the user clicks confirm to confirm his production. Is asks for confirmation and if
     * it is confirmed, it notifies the answer handler of the change.
     *
     * @param event when the player clicks on confirm.
     */
    public void confirm(ActionEvent event) {
        //Metodo chiamato quando utente conferma la mossa. Check per controllare se almeno una produzione è stata attivata
        //(altrimenti Alert.ERROR). Alert.CONFIRMATION per chiedere conferma: se si pack inviato, altrimenti si chiude stage
        //e si torna a board.fxml

        // check if at least 1 yes
        boolean confirm = false;
        for (int i = 0; i < 6; i++) {
            if (info.get("prod" + i).equalsIgnoreCase("yes")) {
                confirm = true;
                break;
            }
        }

        if (confirm) {

            StringBuilder action = new StringBuilder();
            int devCard = 0;

            while (devCard < 6) {
                while (info.containsKey("prod" + devCard) && !info.get("prod" + devCard).equalsIgnoreCase("yes")) {
                    devCard++;
                }

                if (devCard < 6) {

                    action.append("\n").append("prod").append(devCard).append(": IN = (");

                    if (devCard == 0) {
                        action.append(info.get("in01").toUpperCase()).append(", ").append(info.get("pos01").toLowerCase()).append("), (")
                                .append(info.get("in02").toUpperCase()).append(", ").append(info.get("pos02").toLowerCase())
                                .append("); OUT = ").append(info.get("out0").toUpperCase());
                    } else if (devCard >= 1 && devCard <= 3) {
                        int n_pos = 1;
                        List<int[]> slots = gui.getModelView().getSlots(gui.getModelView().getName());
                        ArrayList<String> inputRes = Cards.getInputById(slots.get(devCard - 1)[gui.getModelView().getTopIndex(slots.get(devCard - 1))]);
                        // pos11 o pos12
                        while (info.containsKey("pos" + devCard + n_pos)) {
                            // BLUE, SMALL), (GREY, MID);
                            if (n_pos == 1) {
                                action.append(inputRes.get(n_pos - 1).toUpperCase()).append(", ").append(info.get("pos" + devCard + n_pos).toLowerCase()).append(")");
                            }
                            if (n_pos == 2) {
                                action.append(", (").append(inputRes.get(n_pos - 1).toUpperCase()).append(", ").append(info.get("pos" + devCard + n_pos).toLowerCase()).append(")");
                            }
                            n_pos++;
                        }
                    } else if (devCard >= 4 && devCard <= 5) {
                        //BLUE, SMALL); OUT = GREY
                        action.append(Cards.getProductionById(Integer.parseInt(gui.getModelView().getLeaders(gui.getModelView().getName()).get("leader" + (devCard - 4))))).append(", ")
                                .append(info.get("pos" + devCard + "1").toLowerCase()).append("); OUT = ").append(info.get("out" + devCard).toUpperCase());
                    }
                    devCard++;
                }
            }


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setHeaderText("Confirm production");
            alert.setContentText("Do you want to confirm?" + action);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                info.put("player", gui.getModelView().getName());
                info.put("action", "produce");
                gui.getModelView().setActiveTurn(false);
                Message message = new ProductionMessage(info);
                gui.getListeners().fireUpdates("produce", message);
            }
            info.clear();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText("You must start at least one production!");
            alert.showAndWait();
        }
    }

    /**
     * This method asks the user which color of resource he prefers to trade in/out (using a confimation alert).
     *
     * @param in_out in/out indicates if the player wants to trade in or produce the resource, respectively.
     * @return the chosen color.
     */
    private String chooseColor(String in_out) {
        Alert chooseColor = new Alert(Alert.AlertType.CONFIRMATION);
        chooseColor.setHeaderText("Choose color");
        if (in_out.equalsIgnoreCase("in")) chooseColor.setContentText("Choose the color you want to trade in!");
        else if (in_out.equalsIgnoreCase("out")) chooseColor.setContentText("Choose the color you want to produce!");
        ButtonType blue = new ButtonType("BLUE");
        ButtonType grey = new ButtonType("GREY");
        ButtonType yellow = new ButtonType("YELLOW");
        ButtonType purple = new ButtonType("PURPLE");
        chooseColor.getButtonTypes().setAll(blue, grey, yellow, purple);
        chooseColor.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> color = chooseColor.showAndWait();
        if (color.isPresent()) {
            if (color.get().equals(blue)) return "BLUE";
            else if (color.get().equals(grey)) return "GREY";
            else if (color.get().equals(yellow)) return "YELLOW";
            else if (color.get().equals(purple)) return "PURPLE";
        }

        return null;
    }

    /**
     * This method asks the user where he wants to take the resource from (using a confimation alert).
     *
     * @param color is the color of the resource.
     * @return the chosen position.
     */
    private String choosePos(String color) {
        Alert chooseDep = new Alert(Alert.AlertType.CONFIRMATION);
        chooseDep.setHeaderText("Choose dep");
        chooseDep.setContentText("Choose where you want to take the " + color.toUpperCase() + " resource from!");
        ButtonType small = new ButtonType("SMALL");
        ButtonType mid = new ButtonType("MID");
        ButtonType big = new ButtonType("BIG");
        ButtonType strongbox = new ButtonType("STRONGBOX");
        ButtonType sp1 = new ButtonType("SP1");
        ButtonType sp2 = new ButtonType("SP2");
        chooseDep.getButtonTypes().setAll(small, mid, big, strongbox, sp1, sp2);
        chooseDep.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> pos = chooseDep.showAndWait();
        if (pos.isPresent()) {
            if (pos.get().equals(small)) return "small";
            else if (pos.get().equals(mid)) return "mid";
            else if (pos.get().equals(big)) return "big";
            else if (pos.get().equals(strongbox)) return "strongbox";
            else if (pos.get().equals(sp1)) {
                if (gui.getModelView().getDeposits(gui.getModelView().getName()).containsKey("sp1res")) return "sp1";
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!");
                    alert.setTitle("Error!");
                    alert.setContentText("You don't have a leader deposit here! Try again!");
                    alert.showAndWait();
                    return choosePos(color);
                }
            } else if (pos.get().equals(sp2)) {
                if (gui.getModelView().getDeposits(gui.getModelView().getName()).containsKey("sp2res")) return "sp2";
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!");
                    alert.setTitle("Error!");
                    alert.setContentText("You don't have a leader deposit here! Try again!");
                    alert.showAndWait();
                    return choosePos(color);
                }
            }
        }
        return null;
    }

    /**
     * This method updates the Slots situation in the Produce scene, by setting the correct images.
     */
    public void updateSlots() {
        Image image;
        List<int[]> slots = gui.getModelView().getSlots(gui.getModelView().getName());
        ImageView[] devs = new ImageView[]{dev0, dev1, dev2};
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

        Map<String, String> leaders = gui.getModelView().getLeaders(gui.getModelView().getName());
        ImageView[] leaderDevs = new ImageView[]{leader0, leader1};
        for (int i = 0; i < leaders.size() / 2; i++) {
            if (leaders.get("state" + i).equalsIgnoreCase("active") &&
                    Integer.parseInt(leaders.get("leader" + i)) >= 15 &&
                    Integer.parseInt(leaders.get("leader" + i)) <= 18)
                image = new Image("PNG/marbles_bg/dev_leader_" + leaders.get("leader" + i) + ".png");
            else image = null;

            leaderDevs[i].setImage(image);
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