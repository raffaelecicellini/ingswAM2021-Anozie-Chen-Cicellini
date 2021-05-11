package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerHandler implements SourceListener {

    /**
     * This attribute represents the ModelView on which the AnswerHandler makes changes.
     */
    ModelView modelView;
    /**
     * It represents the View (CLI/GUI) what is listening on the Answer Handler.
     */
    Source viewListener = new Source();

    /**
     * AnswerHandler constructor
     * @param modelView is the modelView on which the AnswerHandler makes changes.
     * @param view is the View (CLI/GUI) what is listening on the Answer Handler.
     */
    public AnswerHandler(ModelView modelView, SourceListener view) {
        this.modelView = modelView;
        this.viewListener.addListener(view);
    }

    @Override
    public void update(String propertyName, Map<String, String> value) {
        // Map used just to notify other players who hasn't done the action with a message containing the name of the
        // player who has done the action and the action.
        Map<String, String> map = new HashMap<>();
        map.put("player", value.get("player"));
        map.put("action", value.get("action"));


        switch (propertyName.toUpperCase()) {

            case "START":
                viewListener.fireUpdates(value.get("action"), value);
                break;

            case "OTHERCONNECTED":
                viewListener.fireUpdates(value.get("action"), value);
                break;

            case "STARTED":

                int[][] developDecks = new int[4][3];
                for (int col = 0; col < 4; col++) {
                    for (int row = 0; row < 3; row++) {
                        developDecks[col][row] = Integer.parseInt(value.get("card"+col+row));
                    }
                }
                modelView.setDevelopDecks(developDecks);

                String[][] market = new String[4][3];
                for (int col = 0; col < 4; col++) {
                    for (int row = 0; row < 3; row++) {
                        market[col][row] = value.get("marble"+col+row);
                    }
                }
                modelView.setMarket(market);

                modelView.setOutMarble(value.get("outMarble"));

                modelView.setPhase(GamePhase.LEADER);

                viewListener.fireUpdates(map.get("action"), null);

                break;

            case "CHOOSELEADERS":

                modelView.setPhase(GamePhase.LEADER);

                modelView.setCurrentPlayer(value.get("player"));

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {

                    // it puts the four leaders on the modelview
                    Map<String, String> leaders = new HashMap<>();
                    for (int i = 0; i < 4; i++) {
                        leaders.put("leader" + i, value.get("leader" + i));
                    }
                    modelView.setLeaders(leaders);

                    viewListener.fireUpdates(value.get("action"), null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "OKLEADERS":

                modelView.setPhase(GamePhase.RESOURCE);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {

                    // it puts the two selected leaders on the modelview
                    Map<String, String> leaders = new HashMap<>();
                    for (int i = 0; i < value.size() - 2; i++) {
                        leaders.put("leader" + i, value.get("leader" + i));
                        leaders.put("state" + i, "available");
                    }
                    modelView.setLeaders(leaders);

                    viewListener.fireUpdates(value.get("action"), null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "CHOOSERESOURCES":

                modelView.setPhase(GamePhase.RESOURCE);

                modelView.setCurrentPlayer(value.get("player"));

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    // the payer who has to choose initial resources

                    if (value.containsKey("addpos")) {
                        modelView.setPosition( Integer.parseInt(value.get("addpos")) );
                    }
                    modelView.setInitialRes(Integer.parseInt(value.get("qty")));
                    // in newValues there are: player, action and qty
                    viewListener.fireUpdates(value.get("action"), value);
                }

                break;

            case "OKRESOURCES":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    // the player who has chosen the correct resources
                    value.remove("action");
                    value.remove("player");
                    modelView.setDeposits(value);

                    viewListener.fireUpdates(map.get("action"),null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "YOURTURN":

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    modelView.setPhase(GamePhase.FULLGAME);
                    modelView.setDoneMandatory(false);
                    modelView.setActiveTurn(true);

                }
                viewListener.fireUpdates(value.get("action"), value);
                break;

            case "BUY":

                modelView.setPhase(GamePhase.FULLGAME);

                developDecks = modelView.getDevelopDecks();
                int col = Integer.parseInt(value.get("col"));
                int row = Integer.parseInt(value.get("row"));
                if (!value.get("idNew").equalsIgnoreCase("empty")) {
                    developDecks[col][row] = Integer.parseInt(value.get("idNew"));
                } else {
                    developDecks[col][row] = 0;
                }
                modelView.setDevelopDecks(developDecks);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", value.get("smallres"));
                    deposits.put("smallqty", value.get("smallqty"));
                    deposits.put("midres", value.get("midres"));
                    deposits.put("midqty", value.get("midqty"));
                    deposits.put("bigres", value.get("bigres"));
                    deposits.put("bigqty", value.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", value.get("sp1res"));
                        deposits.put("sp1qty", value.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", value.get("sp2res"));
                            deposits.put("sp2qty", value.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);


                    Map<String, String> strongbox = new HashMap<>();
                    for (int i = 0; i < 4; i++) {
                        strongbox.put("strres" + i, value.get("strres" + i));
                        strongbox.put("strqty" + i, value.get("strqty" + i));
                    }
                    modelView.setStrongbox(strongbox);


                    int slot = Integer.parseInt(value.get("slot"));
                    List<int[]> slots = modelView.getSlots();
                    int i = 0;
                    while (slots.get(slot)[i] != 0 && i < slots.get(slot).length) i++;
                    if (i < 3) {
                        slots.get(slot)[i] = Integer.parseInt(value.get("idBought"));
                    }
                    modelView.setSlots(slots);

                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                    viewListener.fireUpdates(value.get("action"), null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "PRODUCE":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {

                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", value.get("smallres"));
                    deposits.put("smallqty", value.get("smallqty"));
                    deposits.put("midres", value.get("midres"));
                    deposits.put("midqty", value.get("midqty"));
                    deposits.put("bigres", value.get("bigres"));
                    deposits.put("bigqty", value.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", value.get("sp1res"));
                        deposits.put("sp1qty", value.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", value.get("sp2res"));
                            deposits.put("sp2qty", value.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);


                    Map<String, String> strongbox = new HashMap<>();
                    for (int i = 0; i < 4; i++) {
                        strongbox.put("strres" + i, value.get("strres" + i));
                        strongbox.put("strqty" + i, value.get("strqty" + i));
                    }
                    modelView.setStrongbox(strongbox);


                    modelView.setPosition(Integer.parseInt(value.get("newPos")));

                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                    viewListener.fireUpdates(value.get("action"), null);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "MARKET":

                modelView.setPhase(GamePhase.FULLGAME);

                market = modelView.getMarket();

                if (value.containsKey("col")) {
                    col = Integer.parseInt(value.get("col"));
                    for (int i = 0; i < 3; i++) {
                        market[col][i] = value.get("res" + i);
                    }
                } else
                if (value.containsKey("row")) {
                    row = Integer.parseInt(value.get("row"));
                    for (int i = 0; i < 4; i++) {
                        market[i][row] = value.get("res" + i);
                    }
                }
                modelView.setMarket(market);

                modelView.setOutMarble(value.get("out"));

                if (modelView.isSoloGame()) {
                    modelView.setBlackCross(Integer.parseInt(value.get("blackPos")));
                }

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    //the player who did fromMarket

                    Map<String, String> deposits = new HashMap<>();
                    deposits.put("smallres", value.get("smallres"));
                    deposits.put("smallqty", value.get("smallqty"));
                    deposits.put("midres", value.get("midres"));
                    deposits.put("midqty", value.get("midqty"));
                    deposits.put("bigres", value.get("bigres"));
                    deposits.put("bigqty", value.get("bigqty"));
                    if (modelView.getDeposits().size() > 6) {
                        deposits.put("sp1res", value.get("sp1res"));
                        deposits.put("sp1qty", value.get("sp1qty"));
                        if (modelView.getDeposits().size() > 8) {
                            deposits.put("sp2res", value.get("sp2res"));
                            deposits.put("sp2qty", value.get("sp2qty"));
                        }
                    }
                    modelView.setDeposits(deposits);

                    modelView.setPosition(Integer.parseInt(value.get("newPos")));

                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                    viewListener.fireUpdates(value.get("action"), null);

                } else {
                    // other players
                    modelView.setPosition(modelView.getPosition() + Integer.parseInt(value.get("discarded")));

                    map.put("other", value.get("player"));
                    map.remove("player");
                    map.put("discarded", value.get("discarded"));
                    viewListener.fireUpdates(map.get("action"), map);

                }

                break;

            case "SWAP":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    // the player who called the swap method
                    value.remove("player");
                    value.remove("action");
                    modelView.setDeposits(value);

                    modelView.setActiveTurn(true);

                    viewListener.fireUpdates(map.get("action"), null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"),  map);
                }

                break;

            case "ACTIVATE":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    // the player who activated a leader

                    // If the activated leader is a "resource" leader, it notifies the player by adding him the new deposit
                    if (value.containsKey("isDep")){
                        Map<String, String> deposits = new HashMap<>();
                        deposits.put("smallres", value.get("smallres"));
                        deposits.put("smallqty", value.get("smallqty"));
                        deposits.put("midres", value.get("midres"));
                        deposits.put("midqty", value.get("midqty"));
                        deposits.put("bigres", value.get("bigres"));
                        deposits.put("bigqty", value.get("bigqty"));
                        if (value.containsKey("sp1res")) {
                            deposits.put("sp1res", value.get("sp1res"));
                            deposits.put("sp1qty", value.get("sp1qty"));
                        }
                        if (value.containsKey("sp2res")) {
                            deposits.put("sp2res", value.get("sp2res"));
                            deposits.put("sp2qty", value.get("sp2qty"));
                        }
                        modelView.setDeposits(deposits);
                    }

                    Map<String, String> leaders = modelView.getLeaders();
                    leaders.put("state" + Integer.parseInt(value.get("index")), "active");
                    modelView.setLeaders(leaders);

                    modelView.setActiveTurn(true);

                    modelView.setCountLeader(Integer.parseInt(value.get("countLeader")));

                    viewListener.fireUpdates(value.get("action"), null);
                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"),  map);
                }

                break;

            case "DISCARD":

                modelView.setPhase(GamePhase.FULLGAME);

                if (modelView.getName().equalsIgnoreCase(value.get("player"))) {

                    modelView.setPosition(Integer.parseInt(value.get("newPos")));

                    Map<String, String> leaders = modelView.getLeaders();
                    leaders.put("state" + Integer.parseInt(value.get("index")), "discarded");
                    modelView.setLeaders(leaders);

                    modelView.setActiveTurn(true);

                    modelView.setCountLeader(Integer.parseInt(value.get("countLeader")));

                    viewListener.fireUpdates(value.get("action"), null);

                } else {
                    // other players
                    map.put("other", map.get("player"));
                    map.remove("player");
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "ENDTURN":

                modelView.setPhase(GamePhase.FULLGAME);

                Tile[] tiles = modelView.getTiles();
                for (int i = 0; i < 3; i++) {
                    if (value.get("tile" + i).equals("active")) {
                        tiles[i].setActive(true);
                    } else if (value.get("tile" + i).equals("discarded")) {
                        tiles[i].setDiscarded(true);
                    }
                }
                modelView.setTiles(tiles);

                modelView.setCurrentPlayer(value.get("currentPlayer"));

                if (modelView.getName().equalsIgnoreCase(value.get("endedTurnPlayer"))) {
                    // the player who ended his turn

                    // FORSE QUESTO FUORI DAL PRIMO IF
                    if (modelView.isSoloGame()) {
                        modelView.setToken(Integer.parseInt(value.get("tokenActivated")));
                        modelView.setBlackCross(Integer.parseInt(value.get("blackPos")));

                        developDecks = new int[4][3];
                        for (col=0; col<4; col++) {
                            for (row = 0; row < 3; row++) {
                                developDecks[col][row] = Integer.parseInt(value.get("card" + col + row));
                            }
                        }
                        modelView.setDevelopDecks(developDecks);
                        modelView.setActiveTurn(false);

                    } else {
                        modelView.setActiveTurn(false);
                    }

                    viewListener.fireUpdates(value.get("action"), value);

                } else {
                    // other players
                    map.put("other", map.get("endedTurnPlayer"));
                    map.remove("player");
                    map.put("currentPlayer", value.get("currentPlayer"));
                    viewListener.fireUpdates(map.get("action"), map);
                }

                break;

            case "ENDGAME":

                modelView.setPhase(GamePhase.ENDED);

                viewListener.fireUpdates(value.get("action"), value);

                break;

            case "ERROR":

                if (modelView.getName().equals(value.get("player"))) {
                    if (modelView.getCurrentPlayer().equalsIgnoreCase(modelView.getName())) {
                        modelView.setActiveTurn(true); //DA CONTROLLARE
                    }
                    viewListener.fireUpdates(value.get("action"), value);
                }

                break;

            case "END":
                viewListener.fireUpdates(value.get("action"), value);
                break;

            case "OTHERDISCONNECTED":
                viewListener.fireUpdates(value.get("action"), value);
                break;
        }
    }
}
