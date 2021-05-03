package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GamePhase;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerHandler implements PropertyChangeListener {

    /**
     * This attribute represents the ModelView on which the AnswerHandler makes changes.
     */
    ModelView modelView;
    /**
     * It represents the View (CLI/GUI) what is listening on the Answer Handler.
     */
    PropertyChangeSupport viewListener = new PropertyChangeSupport(this);

    /**
     * AnswerHandler constructor
     * @param modelView is the modelView on which the AnswerHandler makes changes.
     * @param view is the View (CLI/GUI) what is listening on the Answer Handler.
     */
    public AnswerHandler(ModelView modelView, PropertyChangeListener view) {
        this.modelView = modelView;
        this.viewListener.addPropertyChangeListener(view);
    }

    /*public void initialGamePhase(String answer) {

    }

    public void fullGamePhase(String answer) {

    }

    public void answerHandler(String answer) {

    }

    public void action(String message) {

    }*/

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        // newValues is the map with all the information
        Map<String, String> newValues = (Map<String, String>) evt.getNewValue();

        // Map used just to notify other players who haven't done the action with a message containing the name of the
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

                modelView.setPhase(GamePhase.LEADER);

                viewListener.firePropertyChange(map.get("action"), null, null);

                break;

            case "CHOOSELEADERS":

                //modelView.setPhase(GamePhase.LEADER);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {

                    // it puts the four leaders on the modelview
                    Map<String, String> leaders = new HashMap<>();
                    for (int i = 0; i < newValues.size() - 2; i++) {
                        leaders.put("leader" + i, newValues.get("leader" + i));
                    }
                    modelView.setLeaders(leaders);

                    viewListener.firePropertyChange(newValues.get("action"), null, null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "OKLEADERS":

                modelView.setPhase(GamePhase.RESOURCE);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {

                    // it puts the two selected leaders on the modelview
                    Map<String, String> leaders = new HashMap<>();
                    for (int i = 0; i < newValues.size() - 2; i++) {
                        leaders.put("leader" + i, newValues.get("leader" + i));
                        leaders.put("state" + i, "available");
                    }
                    modelView.setLeaders(leaders);

                    viewListener.firePropertyChange(newValues.get("action"), null, null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "CHOOSERESOURCES":

                //modelView.setPhase(GamePhase.RESOURCE);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
                    // the payer who has to choose initial resources

                    if (newValues.containsKey("addpos")) {
                        modelView.setPosition( Integer.parseInt(newValues.get("addpos")) );
                    }

                    // in newValues there are: player, action and qty
                    viewListener.firePropertyChange(newValues.get("action"), null, newValues);
                }

                break;

            case "OKRESOURCES":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
                    // the player who has chosen the correct resources
                    newValues.remove("action");
                    newValues.remove("player");
                    modelView.setDeposits(newValues);

                    viewListener.firePropertyChange(map.get("action"), null, null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "YOURTURN":

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
                    modelView.setPhase(GamePhase.FULLGAME);
                    modelView.setDoneMandatory(false);
                    modelView.setActiveTurn(true);
                    viewListener.firePropertyChange(newValues.get("action"), null, newValues);
                } else {
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "BUY":

                modelView.setPhase(GamePhase.FULLGAME);

                developDecks = modelView.getDevelopDecks();
                int col = Integer.parseInt(newValues.get("col"));
                int row = Integer.parseInt(newValues.get("row"));
                if (!newValues.get("idNew").equalsIgnoreCase("empty")) {
                    developDecks[col][row] = Integer.parseInt(newValues.get("idNew"));
                } else {
                    developDecks[col][row] = 0;
                }
                modelView.setDevelopDecks(developDecks);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
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

                    viewListener.firePropertyChange(newValues.get("action"), null, null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "PRODUCE":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {

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

                    viewListener.firePropertyChange(newValues.get("action"), null, null);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "MARKET":

                modelView.setPhase(GamePhase.FULLGAME);

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
                    modelView.setBlackCross(modelView.getBlackCross() + Integer.parseInt(newValues.get("blackPos")));
                }

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
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

                    viewListener.firePropertyChange(newValues.get("action"), null, null);

                } else {
                    // other players
                    modelView.setPosition(modelView.getPosition() + Integer.parseInt(newValues.get("discarded")));

                    //map.put("other", map.get("player"));
                    //map.remove("player");
                    newValues.put("other", newValues.get("player"));
                    newValues.remove("player");
                    viewListener.firePropertyChange(newValues.get("action"), null, newValues);

                }

                break;

            case "SWAP":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
                    // the player who called the swap method
                    newValues.remove("player");
                    newValues.remove("action");
                    modelView.setDeposits(newValues);

                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(map.get("action"), null, null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "ACTIVATE":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
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

                    viewListener.firePropertyChange(newValues.get("action"), null, newValues);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "DISCARD":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {

                    modelView.setPosition(Integer.parseInt(newValues.get("newPos")));

                    Map<String, String> leaders = modelView.getLeaders();
                    leaders.put("state" + Integer.parseInt(newValues.get("index")), "discarded");
                    modelView.setLeaders(leaders);

                    modelView.setActiveTurn(true);

                    viewListener.firePropertyChange(newValues.get("action"), null, null);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "ENDTURN":

                modelView.setPhase(GamePhase.FULLGAME);

                Tile[] tiles = modelView.getTiles();
                for (int i = 0; i < 3; i++) {
                    if (newValues.get("tile" + i).equals("active")) {
                        tiles[i].setActive(true);
                    } else if (newValues.get("tile" + i).equals("discarded")) {
                        tiles[i].setDiscarded(true);
                    }
                }
                modelView.setTiles(tiles);

                if (modelView.getName().equalsIgnoreCase(newValues.get("player"))) {
                    // the player who ended his turn

                    // FORSE QUESTO FUORI DAL PRIMO IF
                    if (modelView.isSoloGame()) {
                        modelView.setToken(Integer.parseInt(newValues.get("tokenActivated")));
                        modelView.setBlackCross(Integer.parseInt(newValues.get("blackPos")));
                    } else {
                        modelView.setActiveTurn(false);
                    }

                    viewListener.firePropertyChange(newValues.get("action"), null, null);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    map.put("currentPlayer", newValues.get("currentPlayer"));
                    viewListener.firePropertyChange(map.get("action"), null, map);
                }

                break;

            case "ENDGAME":

                modelView.setPhase(GamePhase.ENDED);

                viewListener.firePropertyChange(newValues.get("action"), null, newValues);

                break;

            case "ERROR":

                if (modelView.getName().equals(newValues.get("player"))) {
                    viewListener.firePropertyChange(newValues.get("action"), null, newValues);
                }

                break;
        }
    }
}
