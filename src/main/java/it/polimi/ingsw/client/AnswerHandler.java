package it.polimi.ingsw.client;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerHandler implements PropertyChangeListener {

    /**
     * This attribute represents the ModelView on which the AnswerHandler makes changes
     */
    ModelView modelView;
    /**
     *
     */
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

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        Map<String, String> newValues = (Map<String, String>) evt.getNewValue();

        // Map used just to notify other players who hasn't done the action with a message containing the name of the
        // player who has done the action and the action.
        Map<String, String> map = new HashMap<>();
        map.put("player", newValues.get("player"));
        map.put("action", newValues.get("action"));


        switch (evt.getPropertyName().toUpperCase()) {

            case "STARTED":

                int[][] developDecks = new int[4][3];
                for (int col = 0; col < 4; col++) {
                    for (int row = 0; row < 3; row++) {
                        developDecks[col][row] = Integer.parseInt(newValues.get("card" + (col+row)));
                    }
                }
                modelView.setDevelopDecks(developDecks);

                String[][] market = new String[4][3];
                for (int col = 0; col < 4; col++) {
                    for (int row = 0; row < 3; row++) {
                        market[col][row] = newValues.get("marble" + (col+row));
                    }
                }
                modelView.setMarket(market);

                modelView.setOutMarble(newValues.get("outMarble"));

                // VANNO SETTATE ANCHE LE TILES
                //modelView.setTiles();

                // VA FATTO ANCHE SETPHASE?
                //modelview.setPhase(Phase.InitialPhase);

                viewListener.firePropertyChange(map.get("action"), null, map);

                break;

            case "YOURTURN":

                if (modelView.getName().equals(newValues.get("player"))) {
                    modelView.setDoneMandatory(false);
                    modelView.setActiveTurn(true);
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "BUY":

                developDecks = modelView.getDevelopDecks();
                int col = Integer.parseInt(newValues.get("col"));
                int row = Integer.parseInt(newValues.get("row"));
                if (!newValues.get("idNew").equalsIgnoreCase("empty")) {
                    developDecks[col][row] = Integer.parseInt(newValues.get("idNew"));
                } else {
                    developDecks[col][row] = 0;
                }
                modelView.setDevelopDecks(developDecks);

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
                    int i = 0;
                    while (slots.get(slot)[i] != 0 && i < slots.get(slot).length) i++;
                    if (i < 3) {
                        slots.get(slot)[i] = Integer.parseInt(newValues.get("idBought"));
                    }
                    modelView.setSlots(slots);

                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, map);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

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
                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, map);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "FROMMARKET":

                market = modelView.getMarket();

                if (newValues.containsKey("col")) {
                    col = Integer.parseInt(newValues.get("col"));
                    for (int i = 0; i < 3; i++) {
                        market[col][i] = newValues.get("res" + i);
                    }
                } else
                if (newValues.containsKey("row")) {
                    row = Integer.parseInt(newValues.get("row"));
                    for (int i = 0; i < 4; i++) {
                        market[i][row] = newValues.get("res" + i);
                    }
                }
                modelView.setMarket(market);

                modelView.setOutMarble(newValues.get("out"));

                if (modelView.isSoloGame()) {
                    modelView.setBlackCross(Integer.parseInt(newValues.get("blackPos")));
                }

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
                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, map);

                } else {
                    // other players
                    modelView.setPosition(modelView.getPosition() + Integer.parseInt(newValues.get("discarded")));

                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);

                }

                break;

            case "SWAP":

                if (modelView.getName().equals(newValues.get("player"))) {
                    // the player who called the swap method
                    newValues.remove("player");
                    newValues.remove("action");
                    modelView.setDeposits(newValues);

                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, map);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "OKRESOURCES":

                if (modelView.getName().equals(newValues.get("player"))) {
                    // the player who has chosen the correct resources
                    newValues.remove("action");
                    newValues.remove("player");
                    modelView.setDeposits(newValues);

                    //modelView.setPhase();

                    viewListener.firePropertyChange(map.get("action"), null, map);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "CHOOSERESOURCES":

                if (modelView.getName().equals(newValues.get("player"))) {
                    // the payer who has to choose initial resources

                    //modelView.setPhase();

                    if (newValues.containsKey("addpos")) {
                        modelView.setPosition( Integer.parseInt(newValues.get("addpos")) );
                    }

                    map.put("qty", newValues.get("qty"));
                    viewListener.firePropertyChange(newValues.get("action"), null, newValues);
                }

                break;

            case "CHOOSELEADERS":

                if (modelView.getName().equals(newValues.get("player"))) {
                    newValues.remove("action");
                    newValues.remove("player");
                    modelView.setLeaders(newValues);

                    //modelView.setPhase();
                }

                viewListener.firePropertyChange(map.get("action"), null, map);

                break;

            case "OKLEADERS":

                if (modelView.getName().equals(newValues.get("player"))) {
                    newValues.remove("action");
                    newValues.remove("player");
                    newValues.put("state0", "available");
                    newValues.put("state1", "available");
                    modelView.setLeaders(newValues);

                    viewListener.firePropertyChange(map.get("action"), null, map);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "ACTIVATE":

                if (modelView.getName().equals(newValues.get("player"))) {
                    // the player who activated a leader

                    // If the activated leader is a "resource" leader, it notifies the player by adding him the new deposit
                    if (newValues.containsKey("isDep")){
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

                    Map<String, String> leaders = modelView.getLeaders();
                    leaders.put("state" + Integer.parseInt(newValues.get("index")), "active");
                    modelView.setLeaders(leaders);

                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, map);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "DISCARD":

                if (modelView.getName().equals(newValues.get("player"))) {

                    modelView.setPosition(Integer.parseInt(newValues.get("newPos")));

                    Map<String, String> leaders = modelView.getLeaders();
                    leaders.put("state" + Integer.parseInt(newValues.get("index")), "discarded");
                    modelView.setLeaders(leaders);

                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, map);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "ENDTURN":

                Tile[] tiles = modelView.getTiles();
                for (int i = 0; i < 3; i++) {
                    if (newValues.get("tile" + i).equals("active")) {
                        tiles[i].setActive(true);
                    } else if (newValues.get("tile" + i).equals("discarded")) {
                        tiles[i].setDiscarded(true);
                    }
                }
                modelView.setTiles(tiles);

                if (modelView.getName().equals(newValues.get("player"))) {
                    // the player who ended his turn

                    // DUBBIO SE METTERE QUESTO FUORI DAL PRIMO IF
                    if (modelView.isSoloGame()) {
                        modelView.setToken(Integer.parseInt(newValues.get("tokenActivated")));
                        modelView.setBlackCross(Integer.parseInt(newValues.get("blackPos")));
                    } else {
                        modelView.setActiveTurn(false);
                    }

                    viewListener.firePropertyChange(map.get("action"), null, map);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    map.put("currentPlayer", newValues.get("currentPlayer"));
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "ENDGAME":

                viewListener.firePropertyChange(newValues.get("action"), null, newValues);

                break;

            case "ERROR":

                if (modelView.getName().equals(newValues.get("player"))) {
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;
        }
    }
}
