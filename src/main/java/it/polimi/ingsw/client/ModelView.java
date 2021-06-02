package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GamePhase;

import java.util.*;

/**
 * This class is a representation (a bean) of the Model on the client. It is used by the view to read the changes occurred
 * on the real model (on the server).
 */
public class ModelView {
    private Map<String, PlayerView> players;
    /**
     * This attribute represents if it is the turn of the client
     */
    private boolean activeTurn;
    /**
     * This attribute represents in which GamePhase the match is in
     */
    private GamePhase phase;
    /**
     * It is the name chosen by the user for the game
     */
    private String name;

    /**
     * String representation of the Marbles in the market
     */
    private String[][] market;
    /**
     * String representation of the out Marble in the market
     */
    private String outMarble;
    /**
     * Representation of the top card of each developdecks based on the id (int) of the card
     */
    private int[][] developDecks;
    /**
     * Representation of the activated token at the end of the turn (used ONLY in SoloGame)
     */
    private int token;

    /**
     * This attribute indicates if the player already did a mandatory action (in order to change the list of available
     * commands presented to the player)
     */
    private boolean doneMandatory;
    /**
     * This attribute indicates if the current game is a solo game
     */
    private boolean soloGame;
    /**
     * This attribute indicate the black cross in the solo game
     */
    private int blackCross;
    /**
     * The number of initial resources that a player can have.
     */
    private int initialRes;
    /**
     * The name of the current Player.
     */
    private String currentPlayer;
    /**
     *  this attribute counts how many Leader actions a Player has done.
     */
    private int countLeader;

    /**
     * This list is used for storing in chronological order of activation the deposit leaders.
     */
    private List<Integer> leaderDepsOrder = new ArrayList<>();

    /**
     * This list is used for storing in chronological order of activation the production leaders.
     */
    private List<Integer> leaderProdOrder = new ArrayList<>();

    /**
     * Constructor of the class. It instantiates the faithtrack and the tiles
     */
    public ModelView(){
        this.players= new HashMap<>();
    }

    public List<String> getPlayers(){
        return new ArrayList<>(players.keySet());
    }

    public void setPlayers(List<String> players){
        for (String x:players){
            PlayerView player= new PlayerView();
            this.players.put(x, player);
        }
    }

    public int getInitialRes(){
        return this.initialRes;
    }

    public void setInitialRes(int res){
        this.initialRes=res;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Synchronized method used to check if the client can perform an action
     * @return the value of activeTurn
     */
    public synchronized boolean isActiveTurn() {
        return activeTurn;
    }

    /**
     * Synchronized method used to set activeTurn attribute
     * @param activeTurn the new value of activeTurn
     */
    public synchronized void setActiveTurn(boolean activeTurn) {
        this.activeTurn = activeTurn;
    }

    /**
     * Player's name getter method
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Player's name setter method
     * @param name the player's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * faith track getter method (used by cli to print)
     * @return the faith track
     */
    public int[] getFaithTrack(String name) {
        return players.get(name).getFaithTrack();
    }

    /**
     * Player's position getter method
     * @return player's position
     */
    public int getPosition(String name) {
        return players.get(name).getPosition();
    }

    /**
     * Player's position setter method
     * @param position the new value for player's position
     */
    public void setPosition(int position, String name) {
        if (position > 24) position = 24;
        players.get(name).setPosition(position);
    }

    /**
     * Player's tiles getter method
     * @return the player's tiles
     */
    public Tile[] getTiles(String name) {
        return players.get(name).getTiles();
    }

    /**
     * Player's tiles setter method
     * @param tiles the new values of the tiles (if activated or discarded)
     */
    public void setTiles(Tile[] tiles, String name) {
        players.get(name).setTiles(tiles);
    }

    /**
     * Player's deposits getter method
     * @return the player's deposits
     */
    public Map<String, String> getDeposits(String name) {
        return players.get(name).getDeposits();
    }

    /**
     * Player's deposits setter method
     * @param deposits the new value for player's deposits
     */
    public void setDeposits(Map<String, String> deposits, String name) {
        players.get(name).setDeposits(deposits);
    }

    /**
     * Player's strongbox getter method
     * @return the player's strongbox
     */
    public Map<String, String> getStrongbox(String name) {
        return players.get(name).getStrongbox();
    }

    /**
     * Player's strongbox setter method
     * @param strongbox the new value for player's strongbox
     */
    public void setStrongbox(Map<String, String> strongbox, String name) {
        players.get(name).setStrongbox(strongbox);
    }

    /**
     * Market getter method
     * @return the current market
     */
    public String[][] getMarket() {
        return market;
    }

    /**
     * Market setter method
     * @param market the new market
     */
    public void setMarket(String[][] market) {
        this.market = market;
    }

    /**
     * outMarble getter method
     * @return the current outMarble
     */
    public String getOutMarble() {
        return outMarble;
    }

    /**
     * outMarble setter method
     * @param outMarble the new outMarble
     */
    public void setOutMarble(String outMarble) {
        this.outMarble = outMarble;
    }

    /**
     * developDecks getter method
     * @return the current developDecks
     */
    public int[][] getDevelopDecks() {
        return developDecks;
    }

    /**
     * developDecks setter method
     * @param developDecks the new developDecks
     */
    public void setDevelopDecks(int[][] developDecks) {
        this.developDecks = developDecks;
    }

    /**
     * Player's token getter method
     * @return the current token
     */
    public int getToken() {
        return token;
    }

    /**
     * Player's token setter method
     * @param token the new token
     */
    public void setToken(int token) {
        this.token = token;
    }

    /**
     * Player's leaders getter method
     * @return the player's leaders
     */
    public Map<String, String> getLeaders(String name) {
        return players.get(name).getLeaders();
    }

    /**
     * Player's leaders setter method
     * @param leaders the new player's leaders
     */
    public void setLeaders(Map<String, String> leaders, String name) {
        players.get(name).setLeaders(leaders);
    }

    /**
     * Player's slots getter method
     * @return the player's slots
     */
    public List<int[]> getSlots(String name) {
        return players.get(name).getSlots();
    }

    /**
     * Player's slots setter method
     * @param slots the new player's slots
     */
    public void setSlots(List<int[]> slots, String name) {
        players.get(name).setSlots(slots);
    }

    /**
     * Used to check if the client already did a mandatory action
     * @return doneMandatory value
     */
    public boolean isDoneMandatory() {
        return doneMandatory;
    }

    /**
     * Sets the new value of doneMandatory
     * @param doneMandatory the new value of doneMandatory
     */
    public void setDoneMandatory(boolean doneMandatory) {
        this.doneMandatory = doneMandatory;
    }

    /**
     * Used to check if it is a soloGame
     * @return the soloGame value
     */
    public boolean isSoloGame() {
        return soloGame;
    }

    /**
     * Used to set soloGame
     * @param soloGame the new value of soloGame
     */
    public void setSoloGame(boolean soloGame) {
        this.soloGame = soloGame;
    }

    /**
     * Used to get the BlackCross position in a soloGame
     * @return the BlackCross position
     */
    public int getBlackCross() {
        return blackCross;
    }

    /**
     * Used to set the BlackCross position in a soloGame
     * @param pos the new value for the BlackCross position
     */
    public void setBlackCross(int pos) {
        if (pos > 24) pos = 24;
        this.blackCross = pos;
    }

    /**
     * phase getter method
     * @return the current value of phase
     */
    public GamePhase getPhase() {
        return phase;
    }

    /**
     * phase setter method
     * @param phase the new value of phase
     */
    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    /**
     * Method used to return a list of marbles, given a row or col of the market.
     * @param chosen is the chosen line (row/col).
     * @param ind is the index of the row/col.
     * @return the list of strings of marbles.
     */
    public ArrayList<String> getResMarket(String chosen, int ind) {
        ArrayList<String> res = new ArrayList<>();

        if (chosen.equalsIgnoreCase("row")) {
            for (int col = 0; col < 4; col++) {
                res.add(getMarket()[col][ind]);
            }
        } else
        if (chosen.equalsIgnoreCase("col")) {
            for (int row = 0; row < 3; row++) {
                res.add(getMarket()[ind][row]);
            }
        }

        return res;
    }

    /**
     * Method used to return the Index of the Top Develop Card, given a slot.
     * @param slot is the array that represents the slot.
     * @return the Index of the Top Develop Card (-1 if empty slot).
     */
    public int getTopIndex(int[] slot) {
        int devCardIndex = 0;

        while (devCardIndex < slot.length) {
            if (slot[devCardIndex] == 0) return devCardIndex-1;
            devCardIndex++;
        }

        return devCardIndex-1;
    }

    /**
     * Method that return the ID of the top card in the slot.
     * @param slot the slot to search the card from.
     * @return the ID of the top card in the slot.
     */
    public int getTopId(int[] slot){
        int devCardIndex = 0;

        while (devCardIndex < slot.length) {
            if (slot[devCardIndex] == 0) {
                if (devCardIndex == 0) {
                    return 0;
                } else {
                    return slot[devCardIndex-1];
                }
            }
            devCardIndex++;
        }

        return slot[devCardIndex-1];
    }

    /**
     * Method that return how many leader actions have been done by the Player.
     * @return the number of leader actions already done.
     */
    public int getCountLeader(){
        return countLeader;
    }

    /**
     * Method to set how many leader actions a Player has done.
     * @param num the number of leader actions.
     */
    public void setCountLeader(int num) {
        countLeader = num;
    }

    /**
     * This method adds a leader to the list of deposit leaders.
     * @param index is the index of the leader
     */
    public void addLeaderDepOrder(int index) {
        leaderDepsOrder.add(index);
    }

    /**
     * This method adds a leader to the list of production leaders.
     * @param index is the index of the leader
     */
    public void addLeaderProdOrder(int index) {
        leaderProdOrder.add(index);
    }

    /**
     * This method returns the position of a leader in the list.
     * @param leader is the index of the leader
     * @return
     */
    public int getLeaderDepsOrder(int leader) {
        return leaderDepsOrder.indexOf(leader);
    }

    /**
     * This method returns the position of a leader in the list.
     * @param leader is the index of the leader
     * @return
     */
    public int getLeaderProdOrder(int leader) {
        return leaderProdOrder.indexOf(leader);
    }

}
