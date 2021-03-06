package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoloGame extends Game {

    /**
     * this attribute represents Lorenzo il Magnifico in a Single Player Game
     */
    private FaithMarker blackCross;

    /**
     * Solo Actions used in a Single Player Game
     */
    private SoloActions soloActions;

    /**
     * Used to check the phase of the game (if an action can be done)
     */
    private GamePhase phase;

    /**
     * @see Source
     */
    private final Source listener = new Source();

    /**
     * Simple method used to set the view as listener of the model
     *
     * @param view the view object that implements PropertyChangeListener
     */
    public void setListener(SourceListener view) {
        this.listener.addListener(view);
    }

    /**
     * This method is used when the player joins the game
     *
     * @param name is the Player's name
     */
    @Override
    public void createPlayer(String name) {
        currentPlayer = new Player(name);
        activePlayers.add(currentPlayer);
    }

    /**
     * This method returns the active players in the current game.
     *
     * @return the list of ActivePlayer
     */
    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Simple method that returns the current GamePhase.
     *
     * @return the current GamePhase
     */
    public GamePhase getPhase() {
        return this.phase;
    }

    /**
     * Constructor of the SoloGame class. It instantiates the Market, the LeaderDeck and the DevelopDecks for the current game.
     */
    public SoloGame() {
        this.market = new Market();
        this.leaderDeck = new LeaderDeck();
        this.developDecks = new DevelopDeck[4][3];
        for (int col = 0; col < 4; col++) {
            Color color = parseColor(col);
            for (int row = 0; row < 3; row++) {
                this.developDecks[col][row] = new DevelopDeck(row + 1, color);
            }
        }
        phase = GamePhase.NOTSTARTED;
    }

    /**
     * This method is called by the controller when the player wants to start the solo game. It gives the leaders to the player (4 cards, in which 2 to be chosen).
     * It positions the blackCross on the Faith Track.
     */
    @Override
    public void start() {
        this.soloActions = new SoloActions();
        blackCross = new FaithMarker(0);
        //receive initial leaders
        LeaderCard card;
        ArrayList<LeaderCard> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            card = leaderDeck.removeCard();
            cards.add(card);
        }
        currentPlayer.receiveLeaders(cards);
        phase = GamePhase.LEADER;
        notifyInitialState();
        notifyLeaders("chooseLeaders");
    }

    /**
     * Utility method used to notify the view of the initial state of the market and of the developDecks
     */
    private void notifyInitialState() {
        Map<String, String> state = new HashMap<>();
        String cardId, marbleId, marble;
        Marble[] array;
        int id;

        state.put("action", "started");
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                cardId = "card" + col + row;
                id = developDecks[col][row].getCard().getId();
                state.put(cardId, String.valueOf(id));
            }
        }
        state.put("outMarble", market.getOutMarble().toString());
        for (int i = 0; i < 3; i++) {
            array = market.selectRow(i);
            for (int j = 0; j < 4; j++) {
                marbleId = "marble" + j + i;
                marble = array[j].toString();
                state.put(marbleId, marble);
            }
        }

        String curr;
        int i = 0;
        for (Player x : activePlayers) {
            curr = "player" + i;
            state.put(curr, x.getName());
            i++;
        }

        Message message = new StartedAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is called when the player chooses his leaders.
     *
     * @param player  is ignored
     * @param message is the message that contains all the info about the action.
     * @throws InvalidActionException when one or both indexes are missing
     */
    @Override
    public void chooseLeaders(String player, Message message) throws InvalidActionException {
        currentPlayer.chooseLeader(message.getLeader(1), message.getLeader(2));
        phase = GamePhase.FULLGAME;

        notifyLeaders("okLeaders");
        notifyTurn();
    }

    /**
     * Utility method used to notify the view that is the turn of the currentPlayer
     */
    private void notifyTurn() {
        Map<String, String> state = new HashMap<>();
        String content = "It is your turn! You must do one action of your choice between buy, market, produce";
        state.put("action", "yourTurn");
        state.put("content", content);
        state.put("player", currentPlayer.getName());
        Message message = new YourTurnAnswer(state);

        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * Utility method used to notify the view that the currentPlayer needs to choose his leaders or to notify him of the
     * correct selection of the leaders
     *
     * @param action the action that is to be sent to the player (choose leader: the player needs to choose his leaders,
     *               the id of the leaders between the player needs to choose are sent; ok leaders: the selection of the
     *               leaders is correct and the id of the selected leaders are sent)
     */
    private void notifyLeaders(String action) {
        Map<String, String> state = new HashMap<>();
        List<LeaderCard> leaders;
        String cardId;
        int id;

        state.put("action", action);
        state.put("player", currentPlayer.getName());
        leaders = currentPlayer.getLeaders();
        for (int i = 0; i < leaders.size(); i++) {
            cardId = "leader" + i;
            id = leaders.get(i).getId();
            state.put(cardId, String.valueOf(id));
        }

        Message message;
        if (action.equalsIgnoreCase("chooseleaders")) {
            message = new ChooseLeadersAnswer(state);
        } else message = new OkLeadersAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is used for a buy move during the turn.
     *
     * @param player  is ignored.
     * @param message is the message that contains all the info about the action.
     * @throws InvalidActionException if the move is not valid.
     * @throws NumberFormatException  if the format is not valid.
     */
    @Override
    public void buy(String player, Message message) throws InvalidActionException, NumberFormatException {
        if (doneMandatory)
            throw new InvalidActionException("You have already done a mandatory operation in this turn.");

        DevelopCard card = developDecks[message.getCol()][message.getRow()].getCard();
        isEndGame = currentPlayer.buy(message, card);
        developDecks[message.getCol()][message.getRow()].removeCard();
        doneMandatory = true;
        notifyBuy(message.getSlot(), card.getId(), message.getCol(), message.getRow());
        if (isEndGame) {
            notifyEndGame(true, getPoints(currentPlayer));
            phase = GamePhase.ENDED;
        }
    }

    /**
     * Utility method used to notify the view that the buy action went fine. It sends to the client the new situation of
     * deposits and strongbox, the id of the new top card for the deck from where he bought the card, and the id of the bought
     * card
     *
     * @param slot   the index of the slot where the player put the bought card
     * @param bought the id of the bought card
     * @param col    the col of the deck where the player bought the card
     * @param row    the row of the deck where the player bought the card
     */
    private void notifyBuy(int slot, int bought, int col, int row) {
        Map<String, String> state = new HashMap<>();
        DevelopCard card = developDecks[col][row].getCard();
        int idNew = 0;
        if (card != null) idNew = card.getId();
        String boxres, boxqty;
        List<ResourceAmount> deps = currentPlayer.getPersonalBoard().getDeposits();
        ResourceAmount[] box = currentPlayer.getPersonalBoard().getStrongbox();
        String[] colors = new String[deps.size()];

        state.put("action", "buy");
        state.put("player", currentPlayer.getName());
        state.put("col", String.valueOf(col));
        state.put("row", String.valueOf(row));
        state.put("idNew", String.valueOf(idNew));

        state.put("slot", String.valueOf(slot));
        state.put("idBought", String.valueOf(bought));

        for (int i = 0; i < deps.size(); i++) {
            if (deps.get(i).getColor() != null) colors[i] = deps.get(i).getColor().toString();
            else colors[i] = "empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size() > 3) {
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size() == 5) {
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        for (int i = 0; i < box.length; i++) {
            boxres = "strres" + i;
            boxqty = "strqty" + i;
            state.put(boxres, box[i].getColor().toString());
            state.put(boxqty, String.valueOf(box[i].getAmount()));
        }

        Message message = new BuyAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is called by the controller when the player decided to activate productions. It calls the player's produce method.
     *
     * @param player  is ignored
     * @param message is the message that contains all the info about the action.
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void produce(String player, Message message) throws InvalidActionException {
        if (doneMandatory)
            throw new InvalidActionException("You have already done a mandatory operation in this turn.");

        currentPlayer.produce(message);
        doneMandatory = true;
        notifyProduce();
    }

    /**
     * Utility method used to notify the view of the corrected execution of a produce action. It sends to the view the
     * new situation of the deposits and strongbox of the player that did the action
     */
    private void notifyProduce() {
        Map<String, String> state = new HashMap<>();
        List<ResourceAmount> deps = currentPlayer.getPersonalBoard().getDeposits();
        ResourceAmount[] box = currentPlayer.getPersonalBoard().getStrongbox();
        String[] colors = new String[deps.size()];
        String boxres, boxqty;
        int pos = currentPlayer.getPersonalBoard().getPosition();

        state.put("action", "produce");
        state.put("player", currentPlayer.getName());
        state.put("newPos", String.valueOf(pos));

        for (int i = 0; i < deps.size(); i++) {
            if (deps.get(i).getColor() != null) colors[i] = deps.get(i).getColor().toString();
            else colors[i] = "empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size() > 3) {
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size() == 5) {
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        for (int i = 0; i < box.length; i++) {
            boxres = "strres" + i;
            boxqty = "strqty" + i;
            state.put(boxres, box[i].getColor().toString());
            state.put(boxqty, String.valueOf(box[i].getAmount()));
        }

        Message message = new ProduceAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is used for taking resources from the Market
     *
     * @param player  is ignored
     * @param message is the message that contains all the info about the action.
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void fromMarket(String player, Message message) throws InvalidActionException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory action in this turn!");
        if (message.isRow()) {
            int row = message.getMarblesIndex();
            if (row >= 1 && row <= 3) {
                int discarded = currentPlayer.fromMarket(message, market.selectRow(row - 1));
                market.pushRow(row - 1);
                blackCross.setPosition(blackCross.getPosition() + discarded);
                doneMandatory = true;
            } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for row!");
            notifyMarket("row", row - 1);
        } else if (message.isCol()) {
            int col = message.getMarblesIndex();
            if (col >= 1 && col <= 4) {
                int discarded = currentPlayer.fromMarket(message, market.selectColumn(col - 1));
                market.pushColumn(col - 1);
                blackCross.setPosition(blackCross.getPosition() + discarded);
                doneMandatory = true;
            } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for col!");
            notifyMarket("col", col - 1);
        } else throw new InvalidActionException("Invalid action! You didn't insert \"row\" or \"col\" correctly!");
    }

    /**
     * Utility method used to notify the view that a fromMarket action went fine. It sends to the client the new situation
     * of the deposits and strongbox, the new situation of the market and the new positions of the client faithmarker and
     * of the blackCross
     *
     * @param chosen row or col of the market chosen by the player
     * @param value  the index of the row or col
     */
    private void notifyMarket(String chosen, int value) {
        Map<String, String> state = new HashMap<>();
        List<ResourceAmount> deps = currentPlayer.getPersonalBoard().getDeposits();
        Marble[] marbles;
        Marble out;
        String res;
        String[] colors = new String[deps.size()];

        state.put("action", "market");
        state.put("player", currentPlayer.getName());

        state.put("blackPos", String.valueOf(blackCross.getPosition()));
        state.put("newPos", String.valueOf(currentPlayer.getPersonalBoard().getPosition()));
        if (chosen.equalsIgnoreCase("col")) {
            state.put("col", String.valueOf(value));
            marbles = market.selectColumn(value);
            for (int i = 0; i < marbles.length; i++) {
                res = "res" + i;
                state.put(res, marbles[i].toString());
            }
        } else if (chosen.equalsIgnoreCase("row")) {
            state.put("row", String.valueOf(value));
            marbles = market.selectRow(value);
            for (int i = 0; i < marbles.length; i++) {
                res = "res" + i;
                state.put(res, marbles[i].toString());
            }
        }
        out = market.getOutMarble();
        state.put("out", out.toString());

        for (int i = 0; i < deps.size(); i++) {
            if (deps.get(i).getColor() != null) colors[i] = deps.get(i).getColor().toString();
            else colors[i] = "empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size() > 3) {
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size() == 5) {
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        Message message = new MarketAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is used for a swapDeposit move during the turn.
     *
     * @param player  is ignored
     * @param message is the message that contains all the info about the action.
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void swapDeposit(String player, Message message) throws InvalidActionException {
        currentPlayer.swapDeposit(message);
        notifySwap();
    }

    /**
     * Utility method used to notify the view that the swap action went fine. It sends to the client the new situation
     * of the deposits and strongbox
     */
    private void notifySwap() {
        Map<String, String> state = new HashMap<>();
        List<ResourceAmount> deps = currentPlayer.getPersonalBoard().getDeposits();
        String[] colors = new String[deps.size()];

        state.put("action", "swap");
        state.put("player", currentPlayer.getName());

        for (int i = 0; i < deps.size(); i++) {
            if (deps.get(i).getColor() != null) colors[i] = deps.get(i).getColor().toString();
            else colors[i] = "empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size() > 3) {
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size() == 5) {
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        Message message = new SwapAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is called by the controller when the player decided to activate a leader. It calls the player's activateLeader method.
     *
     * @param player is ignored
     * @param pos    it represents the leader that the player wants to activate
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void activateLeader(String player, int pos) throws InvalidActionException {
        if (doneLeader < 2) {
            currentPlayer.activateLeader(pos);
            doneLeader++;
        } else if (doneLeader == 2) throw new InvalidActionException("You can't activate another leader");
        notifyLeaderAction("activate", pos);
    }

    /**
     * This method is called by the controller when the player decided to discard a leader. It calls the player's discardLeader method.
     *
     * @param player is ignored
     * @param pos    it represents the leader that the player wants to discard
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void discardLeader(String player, int pos) throws InvalidActionException {
        if (doneLeader < 2) {
            currentPlayer.discardLeader(pos);
            doneLeader++;
        } else if (doneLeader == 2) throw new InvalidActionException("You can't discard another leader");
        notifyLeaderAction("discard", pos);
    }

    /**
     * Utility method used to notify the view of a corrected execution of a leader action (activate/discard). it sends to
     * the view the index of the activated/discarded leader, the action performed and the new position of the player (if
     * the leader has been discarded)
     *
     * @param action the action performed
     * @param pos    the new position of the player
     */
    private void notifyLeaderAction(String action, int pos) {
        Map<String, String> state = new HashMap<>();
        List<ResourceAmount> deps = currentPlayer.getPersonalBoard().getDeposits();
        String[] colors = new String[deps.size()];

        state.put("action", action);
        state.put("player", currentPlayer.getName());
        state.put("index", String.valueOf(pos));

        // IF ACTION IS ACTIVATE A LEADER THAT GIVES AN EXTRA DEPOSIT
        if (currentPlayer.getLeaders().get(pos).getType().equalsIgnoreCase("resource")) {
            state.put("isDep", "yes");
            for (int i = 0; i < deps.size(); i++) {
                if (deps.get(i).getColor() != null) colors[i] = deps.get(i).getColor().toString();
                else colors[i] = "empty";
            }
            state.put("smallres", colors[0]);
            state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
            state.put("midres", colors[1]);
            state.put("midqty", String.valueOf(deps.get(1).getAmount()));
            state.put("bigres", colors[2]);
            state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
            if (deps.size() > 3) {
                state.put("sp1res", colors[3]);
                state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
            }
            if (deps.size() == 5) {
                state.put("sp2res", colors[4]);
                state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
            }
        }

        if (action.equalsIgnoreCase("discard")) {
            int newPos = currentPlayer.getPersonalBoard().getPosition();
            state.put("newPos", String.valueOf(newPos));
        }

        state.put("countLeader", String.valueOf(doneLeader));

        Message message = new LeaderActionAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method is called by the controller when the player decided to end his turn. It activates the Pope's reports if needed (setting or
     * discarding the FavorTiles of each player) and controls if the game is at the end: if so, if the player won, it counts his points notify
     * that he is the winner; otherwise, it notifies that he lost.
     *
     * @param player is ignored
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void endTurn(String player) throws InvalidActionException {

        if (!doneMandatory)
            throw new InvalidActionException("You have to do a mandatory action (buy a DevelopCard, activate production or take resources from market)");


        if (!isEndGame) {

            //solo action
            int id = soloActions.getCurrentId();
            soloActions.doAction(blackCross, developDecks);

            doneLeader = 0;
            doneMandatory = false;

            //select max position and set the tiles if needed: if someone is at the end, set isEndgame

            if (blackCross.getPosition() > currentPlayer.getPersonalBoard().getPosition()) {
                for (int j = 1; j >= 0; j--) {
                    FavorTile tile = currentPlayer.getPersonalBoard().getTile(j);
                    if (blackCross.getPosition() >= tile.getEnd() && !tile.isActive() && !tile.isDiscarded()) {
                        if (currentPlayer.getPersonalBoard().getPosition() >= tile.getStart() && !tile.isDiscarded()) {
                            tile.setActive(true);
                        } else tile.setDiscarded(true);
                    }
                }
            } else {
                for (int j = 1; j >= 0; j--) {
                    FavorTile tile = currentPlayer.getPersonalBoard().getTile(j);
                    if (currentPlayer.getPersonalBoard().getPosition() >= tile.getEnd() && !tile.isActive() && !tile.isDiscarded()) {
                        tile.setActive(true);
                    }
                }
            }

            //check if the player is at the end
            if (currentPlayer.getPersonalBoard().getPosition() >= currentPlayer.getPersonalBoard().getTile(2).getEnd()) {
                isEndGame = true;
                currentPlayer.getPersonalBoard().getTile(2).setActive(true);
                notifyEndTurn(id);
                notifyEndGame(true, getPoints(currentPlayer));
                phase = GamePhase.ENDED;
                return;
            }

            if (blackCross.getPosition() >= currentPlayer.getPersonalBoard().getTile(2).getEnd()) {
                isEndGame = true;
                if (currentPlayer.getPersonalBoard().getPosition()>=currentPlayer.getPersonalBoard().getTile(2).getStart()){
                    currentPlayer.getPersonalBoard().getTile(2).setActive(true);
                }
                else {
                    currentPlayer.getPersonalBoard().getTile(2).setDiscarded(true);
                }
                notifyEndTurn(id);
                notifyEndGame(false, getPoints(currentPlayer));
                phase = GamePhase.ENDED;
                return;
            }

            //check decks
            int i = 0;
            while (i <= 3 && !isEndGame) {
                if (developDecks[i][2].getTop() == -1) isEndGame = true;
                else i++;
            }
            if (isEndGame) {
                notifyEndTurn(id);
                notifyEndGame(false, getPoints(currentPlayer));
                phase = GamePhase.ENDED;
                return;
            }

            notifyEndTurn(id);
            notifyTurn();
        }

    }

    /**
     * Utility method used to notify the corrected execution of the end turn action. It sends to the view the new situation
     * of the player's FavorTile. This method also sends the current position of the blackCross and the activated token
     *
     * @param token is the activated token at the end of the turn
     */
    private void notifyEndTurn(int token) {
        Map<String, String> state = new HashMap<>();
        FavorTile[] tiles = currentPlayer.getPersonalBoard().getTiles();
        String tile, cardId;
        DevelopCard card;
        int id;
        state.put("action", "endturn");
        state.put("endedTurnPlayer", currentPlayer.getName());
        state.put("currentPlayer", currentPlayer.getName());

        state.put("player0", currentPlayer.getName());
        for (int i = 0; i < tiles.length; i++) {
            tile = "tile0" + i;
            if (tiles[i].isActive()) {
                state.put(tile, "active");
            } else if (tiles[i].isDiscarded()) {
                state.put(tile, "discarded");
            } else state.put(tile, "nothing");
        }

        state.put("blackPos", String.valueOf(blackCross.getPosition()));
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                cardId = "card" + col + row;
                card = developDecks[col][row].getCard();
                if (card != null) {
                    id = card.getId();
                    state.put(cardId, String.valueOf(id));
                } else state.put(cardId, String.valueOf(0));
            }
        }

        state.put("tokenActivated", String.valueOf(token));

        Message message = new EndTurnAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }

    /**
     * This method notifies the view that the game is ended. It sends the client the points scored and if he won or lost
     *
     * @param win    boolean indicating if the client won
     * @param points the points scored by the client
     */
    private void notifyEndGame(boolean win, int points) {
        Map<String, String> state = new HashMap<>();
        String content;
        state.put("action", "endgame");
        state.put("player", currentPlayer.getName());
        if (win) {
            content = "You won!";
            state.put("winner", currentPlayer.getName());
        } else {
            content = "You lost!";
        }
        state.put("content", content);
        state.put("points", String.valueOf(points));
        state.put("winnerpoints", String.valueOf(points));

        Message message = new EndGameAnswer(state);
        listener.fireUpdates(message.getAction(), message);
    }


    // some testing needed methods

    /**
     * Method to get the black cross (JUST FOR TESTING)
     *
     * @return the blackCross
     */
    protected FaithMarker getBlackCross() {
        return blackCross;
    }

    /**
     * Just for testing
     */
    protected void printSoloActions() {
        System.out.println(soloActions);
    }

    /**
     * Just for testing
     *
     * @param soloActions soloActions
     */
    protected void setSoloActions(SoloActions soloActions) {
        this.soloActions = soloActions;
    }
}
