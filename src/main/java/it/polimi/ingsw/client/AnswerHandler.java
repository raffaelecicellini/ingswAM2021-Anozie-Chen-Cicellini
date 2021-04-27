package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerHandler implements PropertyChangeListener {

    // riceve già serializzato, lo casto a mappa

    ModelView modelView;
    PropertyChangeSupport viewListener = new PropertyChangeSupport(this);

    public AnswerHandler(ModelView modelView, PropertyChangeListener view) {
        this.modelView = modelView;
        this.viewListener.addPropertyChangeListener(view);
    }

    public void initialGamePhase(String answer) {

    }

    public void fullGamePhase(String answer) {

    }

    public void answerHandler(String answer) {

    }

    public void action(String message) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        /*String newValue = (String) evt.getNewValue();
        Gson gson = new Gson();

        Map<String, String> map = new HashMap<>();
        map = gson.fromJson(newValue, new TypeToken<Map<String, String>>(){}.getType());

        viewListener.firePropertyChange("update", null, map);*/


        Map<String, String> newValues = (Map<String, String>) evt.getNewValue();

        String event = evt.getPropertyName();

        switch (event.toUpperCase()) {

            case "BUY":

                if (modelView.getName().equals(newValues.get("player"))) {
                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", newValues.get("smallres"));
                    deposits.put("smallqty", newValues.get("smallqty"));
                    deposits.put("midres", newValues.get("midres"));
                    deposits.put("midqty", newValues.get("midqty"));
                    deposits.put("bigres", newValues.get("bigres"));
                    deposits.put("bigqty", newValues.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", newValues.get("sp1res"));
                        deposits.put("sp1qty", newValues.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", newValues.get("sp2res"));
                            deposits.put("sp2qty", newValues.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);

                    Map<String, String> strongbox = new HashMap<>();
                    for (int i = 0; i < 4; i++) {
                        strongbox.put("strres" + i, newValues.get("strres" + i));
                        strongbox.put("strqty" + i, newValues.get("strqty" + i));
                    }
                    modelView.setStrongbox(strongbox);

                    // NON SONO SICURO DEL CODICE
                    int slot = Integer.parseInt(newValues.get("slot"));
                    List<int[]> slots = modelView.getSlots();
                    slots.get(slot)[slots.get(slot).length + 1] = Integer.parseInt(newValues.get("idBought"));
                    modelView.setSlots(slots);

                    modelView.setDoneMandatory(true);

                }

                // NON SONO SICURO DEGLI INDICI E DEL CODICE
                int[][] developDecks = modelView.getDevelopDecks();
                int col = Integer.parseInt(newValues.get("col"));
                int row = Integer.parseInt(newValues.get("row"));
                developDecks[col][row] = Integer.parseInt(newValues.get("idNew"));
                modelView.setDevelopDecks(developDecks);

                viewListener.firePropertyChange("update", null, null);

                break;

            case "PRODUCE":

                if (modelView.getName().equals(newValues.get("player"))) {

                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", newValues.get("smallres"));
                    deposits.put("smallqty", newValues.get("smallqty"));
                    deposits.put("midres", newValues.get("midres"));
                    deposits.put("midqty", newValues.get("midqty"));
                    deposits.put("bigres", newValues.get("bigres"));
                    deposits.put("bigqty", newValues.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", newValues.get("sp1res"));
                        deposits.put("sp1qty", newValues.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", newValues.get("sp2res"));
                            deposits.put("sp2qty", newValues.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);

                    Map<String, String> strongbox = new HashMap<>();
                    for (int i = 0; i < 4; i++) {
                        strongbox.put("strres" + i, newValues.get("strres" + i));
                        strongbox.put("strqty" + i, newValues.get("strqty" + i));
                    }
                    modelView.setStrongbox(strongbox);

                    modelView.setPosition(Integer.parseInt(newValues.get("newPos")));

                    modelView.setDoneMandatory(true);
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "FROMMARKET":

                if (modelView.getName().equals(newValues.get("player"))) {
                    //the player who did fromMarket

                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", newValues.get("smallres"));
                    deposits.put("smallqty", newValues.get("smallqty"));
                    deposits.put("midres", newValues.get("midres"));
                    deposits.put("midqty", newValues.get("midqty"));
                    deposits.put("bigres", newValues.get("bigres"));
                    deposits.put("bigqty", newValues.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", newValues.get("sp1res"));
                        deposits.put("sp1qty", newValues.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", newValues.get("sp2res"));
                            deposits.put("sp2qty", newValues.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);

                    modelView.setPosition(Integer.parseInt(newValues.get("newPos")));

                    modelView.setDoneMandatory(true);

                } else {
                    // other players
                    modelView.setPosition(modelView.getPosition() + Integer.parseInt(newValues.get("discarded")));
                }

                String[][] market;
                market = modelView.getMarket();

                if (newValues.containsKey("col")) {
                    col = Integer.parseInt(newValues.get("col"));
                    for (int i = 0; i < 3; i++) {
                        market[i][col] = newValues.get("res" + i);
                        modelView.setMarket(market);
                    }
                } else
                    if (newValues.containsKey("row")) {
                        row = Integer.parseInt(newValues.get("row"));
                        for (int i = 0; i < 4; i++) {
                            market[row][i] = newValues.get("res" + i);
                            modelView.setMarket(market);
                        }
                    }

                modelView.setOutMarble(newValues.get("out"));

                if (modelView.isSoloGame()) {
                    modelView.setBlackCross(Integer.parseInt(newValues.get("blackPos")));
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "SWAP":

                if (modelView.getName().equals(newValues.get("player"))) {
                    newValues.remove("player");
                    newValues.remove("action");
                    modelView.setDeposits(newValues);
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "OKRESOURCES":

                if (modelView.getName().equals(newValues.get("player"))) {
                    newValues.remove("player");
                    newValues.remove("action");
                    newValues.remove("qty");
                    modelView.setDeposits(newValues);
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "CHOOSERESOURCES":

                if (modelView.getName().equals(newValues.get("player"))) {
                    newValues.remove("player");
                    newValues.remove("action");
                    if (newValues.containsKey("addpos")) {
                        modelView.setPosition(modelView.getPosition() + Integer.parseInt(newValues.get("addpos")));
                    }
                }

                viewListener.firePropertyChange("update", null, newValues);

                break;

            case "CHOOSELEADERS":

            case "OKLEADERS":

                if (modelView.getName().equals(newValues.get("player"))) {
                    newValues.remove("action");
                    newValues.remove("player");
                    modelView.setLeaders(newValues);
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "ACTIVATE":

                if (modelView.getName().equals(newValues.get("player"))) {

                    // SE IL LEADER ATTIVATO DA' DEPOSITO EXTRA, LO NOTIFICO ALL'UTENTE
                    //if (modelView.getLeaders().get(newValues.get("index"))){}

                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", newValues.get("smallres"));
                    deposits.put("smallqty", newValues.get("smallqty"));
                    deposits.put("midres", newValues.get("midres"));
                    deposits.put("midqty", newValues.get("midqty"));
                    deposits.put("bigres", newValues.get("bigres"));
                    deposits.put("bigqty", newValues.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", newValues.get("sp1res"));
                        deposits.put("sp1qty", newValues.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", newValues.get("sp2res"));
                            deposits.put("sp2qty", newValues.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "DISCARD":

                if (modelView.getName().equals(newValues.get("player"))) {
                    modelView.setPosition(Integer.parseInt(newValues.get("newPos")));
                }

                viewListener.firePropertyChange("update", null, null);

                break;

            case "ENDTURN":

                //if (modelView.getName().equals(newValues.get("player"))) {

                // DUBBIO SE METTERE QUESTO FUORI DAL PRIMO IF
                if (modelView.isSoloGame()) {
                    modelView.setToken(Integer.parseInt(newValues.get("tokenActivated")));
                }


                Tile[] tiles = modelView.getTiles();
                for (int i = 0; i < 3; i++) {
                    if (newValues.get("tile" + i).equals("active")) {
                        tiles[i].setActive(true);
                    } else if (newValues.get("tile" + i).equals("discarded")) {
                        tiles[i].setDiscarded(true);
                    }
                }
                modelView.setTiles(tiles);

                viewListener.firePropertyChange("update", null, null);

                break;

            case "ENDGAME":

                viewListener.firePropertyChange("update", null, newValues);

                break;

            case "ERROR":

                if (modelView.getName().equals(newValues.get("player"))) {
                    viewListener.firePropertyChange("update", null, newValues);
                }

                break;
        }
    }
}
