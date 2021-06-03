package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerHandler implements SourceListener {

    /**
     * This attribute represents the ModelView on which the AnswerHandler makes changes.
     */
    ModelView modelView;
    /**
     * It represents the View (CLI/GUI) that is listening on the Answer Handler.
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
    public void update(String propertyName, Message message) {
        Map<String,String> leaders;
        int[][] developDecks;

        switch (propertyName.toUpperCase()) {

            case "START":
                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "OTHERCONNECTED":
                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "STARTED":

                developDecks = new int[4][3];
                for (int col = 0; col < 4; col++) {
                    for (int row = 0; row < 3; row++) {
                        developDecks[col][row] = message.getCard(col, row);
                    }
                }
                modelView.setDevelopDecks(developDecks);

                String[][] market = new String[4][3];
                for (int col = 0; col < 4; col++) {
                    for (int row = 0; row < 3; row++) {
                        market[col][row] = message.getMarble(col, row);
                    }
                }
                modelView.setMarket(market);

                List<String> players = new ArrayList<>();
                int number = 0;
                while (message.getPlayer(number) != null) {
                    players.add(message.getPlayer(number));
                    number++;
                }
                modelView.setPlayers(players);

                modelView.setOutMarble(message.getOutMarble());

                modelView.setPhase(GamePhase.LEADER);

                viewListener.fireUpdates(message.getAction(), null);

                break;

            case "CHOOSELEADERS":

                modelView.setPhase(GamePhase.LEADER);

                modelView.setCurrentPlayer(message.getPlayer());

                // it puts the four leaders on the modelview
                leaders = new HashMap<>();
                for (int i = 0; i < 4; i++) {
                    leaders.put("leader"+i, String.valueOf(message.getLeaders(i)));
                }
                modelView.setLeaders(leaders, message.getPlayer());

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "OKLEADERS":

                modelView.setPhase(GamePhase.RESOURCE);

                // it puts the two selected leaders on the modelview
                leaders = new HashMap<>();
                for (int i = 0; i < 2; i++) {
                    leaders.put("leader" + i, String.valueOf(message.getLeaders(i)));
                    leaders.put("state" + i, "available");
                }
                modelView.setLeaders(leaders, message.getPlayer());

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "CHOOSERESOURCES":

                modelView.setPhase(GamePhase.RESOURCE);

                modelView.setCurrentPlayer(message.getPlayer());

                if (message.getAddPos()!=-1) {
                    modelView.setPosition(message.getAddPos(), message.getPlayer());
                }

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    // the player who has to choose initial resources

                    modelView.setInitialRes(message.getResQty());
                }

                viewListener.fireUpdates(message.getAction(), message); //UNICO

                break;

            case "OKRESOURCES":

                modelView.setPhase(GamePhase.FULLGAME);

                modelView.setDeposits(message.getDeposits(), message.getPlayer());

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "YOURTURN":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    modelView.setPhase(GamePhase.FULLGAME);
                    modelView.setDoneMandatory(false);
                    modelView.setActiveTurn(true);
                }
                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "BUY":

                modelView.setPhase(GamePhase.FULLGAME);

                developDecks = modelView.getDevelopDecks();
                developDecks[message.getCol()][message.getRow()] = message.getIdNew();

                modelView.setDevelopDecks(developDecks);

                modelView.setDeposits(message.getDeposits(), message.getPlayer());

                modelView.setStrongbox(message.getStrongbox(), message.getPlayer());


                int slot = message.getSlot();
                List<int[]> slots = modelView.getSlots(message.getPlayer());
                int j = 0;
                while (slots.get(slot)[j] != 0 && j < slots.get(slot).length) j++;
                if (j < 3) {
                    slots.get(slot)[j] = message.getIdBought();
                }
                modelView.setSlots(slots, message.getPlayer());

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {

                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                }
                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "PRODUCE":

                modelView.setPhase(GamePhase.FULLGAME);

                modelView.setDeposits(message.getDeposits(), message.getPlayer());

                modelView.setStrongbox(message.getStrongbox(), message.getPlayer());

                modelView.setPosition(message.getNewPos(), message.getPlayer());

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())){

                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                }

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "MARKET":

                modelView.setPhase(GamePhase.FULLGAME);

                market = modelView.getMarket();
                if (message.isCol()) {
                    int col = message.getMarblesIndex();
                    for (int i = 0; i < 3; i++) {
                        market[col][i] = message.getRes(i);
                    }
                } else
                if (message.isRow()) {
                    int row = message.getMarblesIndex();
                    for (int i = 0; i < 4; i++) {
                        market[i][row] = message.getRes(i);
                    }
                }
                modelView.setMarket(market);

                modelView.setOutMarble(message.getOutMarble());

                if (modelView.isSoloGame()) {
                    modelView.setBlackCross(message.getBlackPos());
                }

                modelView.setDeposits(message.getDeposits(), message.getPlayer());

                modelView.setPosition(message.getNewPos(), message.getPlayer());

                for (String player: modelView.getPlayers()){
                    if (!player.equalsIgnoreCase(message.getPlayer())) {
                        modelView.setPosition(modelView.getPosition(player) + message.getDiscarded(), player);
                    }
                }

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    //the player who did fromMarket
                    modelView.setDoneMandatory(true);
                    modelView.setActiveTurn(true);

                }

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "SWAP":

                modelView.setPhase(GamePhase.FULLGAME);

                modelView.setDeposits(message.getDeposits(), message.getPlayer());

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    // the player who called the swap method
                    modelView.setActiveTurn(true);
                }

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "ACTIVATE":

                modelView.setPhase(GamePhase.FULLGAME);

                // If the activated leader is a "resource" leader, it notifies the player by adding him the new deposit
                if (message.isDep()) {
                    modelView.setDeposits(message.getDeposits(), message.getPlayer());
                    modelView.addLeaderDepOrder(message.getIndex());
                }

                //if the activated leader is a production leader
                int id = Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader"+message.getIndex()));
                if (id >= 15 && id <= 18)
                    modelView.addLeaderProdOrder(message.getIndex());

                leaders = modelView.getLeaders(message.getPlayer());
                leaders.put("state" + message.getIndex(), "active");
                modelView.setLeaders(leaders, message.getPlayer());

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    // the player who activated a leader
                    modelView.setActiveTurn(true);

                    modelView.setCountLeader(message.getCountLeader());

                }

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "DISCARD":

                modelView.setPhase(GamePhase.FULLGAME);

                modelView.setPosition(message.getNewPos(), message.getPlayer());
                leaders = modelView.getLeaders(message.getPlayer());
                leaders.put("state" + message.getIndex(), "discarded");
                modelView.setLeaders(leaders, message.getPlayer());


                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    modelView.setActiveTurn(true);

                    modelView.setCountLeader(message.getCountLeader());

                }

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "ENDTURN":

                modelView.setPhase(GamePhase.FULLGAME);

                int player=0;

                while(message.containsPlayer(player)){

                    System.out.println(message.getPlayer(player));
                    Tile[] tiles = modelView.getTiles(message.getPlayer(player));
                    for (int i = 0; i < 3; i++) {
                        if (message.getTileState(player, i).equalsIgnoreCase("active")) {
                            tiles[i].setActive(true);
                        } else if (message.getTileState(player, i).equalsIgnoreCase("discarded")) {
                            tiles[i].setDiscarded(true);
                        }
                    }
                    modelView.setTiles(tiles, message.getPlayer(player));
                    player++;
                }

                modelView.setCurrentPlayer(message.getCurrentPlayer());

                if (modelView.getName().equalsIgnoreCase(message.getEndedPlayer())) {
                    // the player who ended his turn

                    // FORSE QUESTO FUORI DAL PRIMO IF
                    if (modelView.isSoloGame()) {
                        modelView.setToken(message.getToken());
                        modelView.setBlackCross(message.getBlackPos());

                        developDecks = new int[4][3];
                        int col;
                        for (col=0; col<4; col++) {
                            int row;
                            for (row = 0; row < 3; row++) {
                                developDecks[col][row] = message.getCard(col, row);
                            }
                        }
                        modelView.setDevelopDecks(developDecks);
                        modelView.setActiveTurn(false);

                    } else {
                        modelView.setActiveTurn(false);
                    }

                }

                viewListener.fireUpdates(message.getAction(), message); //UNICO

                break;

            case "ENDGAME":

                modelView.setPhase(GamePhase.ENDED);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "ERROR":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                    if (modelView.getCurrentPlayer().equalsIgnoreCase(modelView.getName())) {
                        modelView.setActiveTurn(true);
                    }
                    viewListener.fireUpdates(message.getAction(), message);
                }

                break;

            case "END":
                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "OTHERDISCONNECTED":
                viewListener.fireUpdates(message.getAction(), message);
                break;
            default:
                viewListener.fireUpdates("default", null);
                break;
        }
    }
}
