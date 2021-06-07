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
     *
     * @param modelView is the modelView on which the AnswerHandler makes changes.
     * @param view      is the View (CLI/GUI) what is listening on the Answer Handler.
     */
    public AnswerHandler(ModelView modelView, SourceListener view) {
        this.modelView = modelView;
        this.viewListener.addListener(view);
    }

    /**
     * Utility method used to set the initial state of the ModelView for the client
     *
     * @param message the message arrived from the model
     */
    private void started(Message message) {
        int[][] developDecks = new int[4][3];
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
    }

    /**
     * Utility method used to set the leaders from which the player can choose
     *
     * @param message the message arrived from the model
     */
    private void chooseLeaders(Message message) {
        modelView.setPhase(GamePhase.LEADER);

        modelView.setCurrentPlayer(message.getPlayer());

        // it puts the four leaders on the modelview
        Map<String, String> leaders = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            leaders.put("leader" + i, String.valueOf(message.getLeaders(i)));
        }
        modelView.setLeaders(leaders, message.getPlayer());
    }

    /**
     * Utility method used to set the selected leaders in the ModelView
     *
     * @param message the message arrived from the model
     */
    private void okLeaders(Message message) {
        modelView.setPhase(GamePhase.RESOURCE);

        // it puts the two selected leaders on the modelview
        Map<String, String> leaders = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            leaders.put("leader" + i, String.valueOf(message.getLeaders(i)));
            leaders.put("state" + i, "available");
        }
        modelView.setLeaders(leaders, message.getPlayer());
    }

    /**
     * Utility method used to set the initial resources/position for the player
     *
     * @param message the message arrived from the model
     */
    private void chooseResources(Message message) {
        modelView.setPhase(GamePhase.RESOURCE);

        modelView.setCurrentPlayer(message.getPlayer());

        if (message.getAddPos() != -1) {
            modelView.setPosition(message.getAddPos(), message.getPlayer());
        }

        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            // the player who has to choose initial resources
            modelView.setInitialRes(message.getResQty());
        }
    }

    /**
     * Utility method used to update the deposits after a chooseResources
     *
     * @param message the message arrived from the model
     */
    private void okResources(Message message) {
        if (message.getPlayer().equalsIgnoreCase(modelView.getName())) {
            modelView.setPhase(GamePhase.FULLGAME);
        }

        modelView.setDeposits(message.getDeposits(), message.getPlayer());
    }

    /**
     * Utility method used to update the ModelView when it is the turn of the player
     *
     * @param message the message arrived from the model
     */
    private void yourTurn(Message message) {
        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            modelView.setPhase(GamePhase.FULLGAME);
            modelView.setDoneMandatory(false);
            modelView.setActiveTurn(true);
        }
    }

    /**
     * Utility method used when a BuyAnswer arrives from the model. It updates the decks in ModelView and the
     * deposits/strongbox of the player who bought the card (PlayerView)
     *
     * @param message the message arrived from the model
     */
    private void buy(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        int[][] developDecks = modelView.getDevelopDecks();
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
    }

    /**
     * Utility method used when a ProductionAnswer arrives from the model. It updates the deposits and strongbox of the player
     * who did the move (PlayerView)
     *
     * @param message the message arrived from the model
     */
    private void produce(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        modelView.setDeposits(message.getDeposits(), message.getPlayer());

        modelView.setStrongbox(message.getStrongbox(), message.getPlayer());

        modelView.setPosition(message.getNewPos(), message.getPlayer());

        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {

            modelView.setDoneMandatory(true);
            modelView.setActiveTurn(true);

        }
    }

    /**
     * Utility method used when a MarketAnswer arrives from the model. It updates the market in ModelView and the deposits
     * of the player who did the move (PlayerView)
     *
     * @param message the message arrived from the model
     */
    private void market(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        String[][] market = modelView.getMarket();
        if (message.isCol()) {
            int col = message.getMarblesIndex();
            for (int i = 0; i < 3; i++) {
                market[col][i] = message.getRes(i);
            }
        } else if (message.isRow()) {
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

        for (String player : modelView.getPlayers()) {
            if (!player.equalsIgnoreCase(message.getPlayer())) {
                modelView.setPosition(modelView.getPosition(player) + message.getDiscarded(), player);
            }
        }

        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            //the player who did fromMarket
            modelView.setDoneMandatory(true);
            modelView.setActiveTurn(true);

        }
    }

    /**
     * Utility method used when a SwapAnswer arrives from the model. It updates the deposits of the player who did
     * the move (PlayerView)
     *
     * @param message the message arrived from the model
     */
    private void swap(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        modelView.setDeposits(message.getDeposits(), message.getPlayer());

        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            // the player who called the swap method
            modelView.setActiveTurn(true);
        }
    }

    /**
     * Utility method used when a LeaderActionAnswer arrives from the model. It updates the leaders of the player who
     * did the move (PlayerView)
     *
     * @param message the message arrived from the model
     */
    private void activate(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        // If the activated leader is a "resource" leader, it notifies the player by adding him the new deposit
        if (message.isDep()) {
            modelView.setDeposits(message.getDeposits(), message.getPlayer());
            modelView.addLeaderDepOrder(message.getIndex());
        }

        //if the activated leader is a production leader
        int id = Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader" + message.getIndex()));
        if (id >= 15 && id <= 18)
            modelView.addLeaderProdOrder(message.getIndex());

        Map<String, String> leaders = modelView.getLeaders(message.getPlayer());
        leaders.put("state" + message.getIndex(), "active");
        modelView.setLeaders(leaders, message.getPlayer());

        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            // the player who activated a leader
            modelView.setActiveTurn(true);

            modelView.setCountLeader(message.getCountLeader());

        }
    }

    /**
     * Utility method used when a LeaderActionAnswer arrives from the model. It updates the leaders of the player who
     * did the move (PlayerView)
     *
     * @param message the message arrived from the model
     */
    private void discard(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        modelView.setPosition(message.getNewPos(), message.getPlayer());
        Map<String, String> leaders = modelView.getLeaders(message.getPlayer());
        leaders.put("state" + message.getIndex(), "discarded");
        modelView.setLeaders(leaders, message.getPlayer());


        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            modelView.setActiveTurn(true);

            modelView.setCountLeader(message.getCountLeader());

        }
    }

    /**
     * Utility method used when a player end the turn. It updates the position and FavorTiles of all the players in the
     * current game. If it is a SoloGame, it sets the activated token in ModelView
     *
     * @param message the message arrived from the model
     */
    private void endTurn(Message message) {
        modelView.setPhase(GamePhase.FULLGAME);

        int player = 0;

        while (message.containsPlayer(player)) {

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

                int[][] developDecks = new int[4][3];
                int col;
                for (col = 0; col < 4; col++) {
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
    }

    /**
     * Utility method used when the game finishes. It notifies the player if he won or not
     */
    private void endGame() {
        modelView.setPhase(GamePhase.ENDED);
    }

    /**
     * Utility method used when the player who did the move receives an error for a bad move
     *
     * @param message the message arrived from the model
     */
    private void error(Message message) {
        if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
            if (modelView.getCurrentPlayer().equalsIgnoreCase(modelView.getName())) {
                modelView.setActiveTurn(true);
            }
        }
    }

    /**
     * @see SourceListener
     */
    @Override
    public void update(String propertyName, Message message) {

        switch (propertyName.toUpperCase()) {

            case "START":
                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "OTHERCONNECTED":
                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "STARTED":

                started(message);
                viewListener.fireUpdates(message.getAction(), null);

                break;

            case "CHOOSELEADERS":

                chooseLeaders(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "OKLEADERS":

                okLeaders(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "CHOOSERESOURCES":

                chooseResources(message);

                viewListener.fireUpdates(message.getAction(), message); //UNICO

                break;

            case "OKRESOURCES":

                okResources(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "YOURTURN":

                yourTurn(message);

                viewListener.fireUpdates(message.getAction(), message);
                break;

            case "BUY":

                buy(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "PRODUCE":

                produce(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "MARKET":

                market(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "SWAP":

                swap(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "ACTIVATE":

                activate(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "DISCARD":

                discard(message);

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "ENDTURN":

                endTurn(message);

                viewListener.fireUpdates(message.getAction(), message); //UNICO

                break;

            case "ENDGAME":

                endGame();

                viewListener.fireUpdates(message.getAction(), message);

                break;

            case "ERROR":

                error(message);
                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
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