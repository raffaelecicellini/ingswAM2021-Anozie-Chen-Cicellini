package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GamePhase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a representation (a bean) of the Model on the client. It is used by the view to read the changes occurred
 * on the real model (on the server).
 */
public class ModelView {
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
     * It is a representation of the faithtrack. Every elements represents the victory points given by that cell
     */
    private int[] faithTrack;
    /**
     * It represents the position of the player on the faith track
     */
    private int position;
    /**
     * It is a representation of the FavorTiles of the player
     */
    private Tile[] tiles;
    /**
     * It is a String representation of the situation of the player's deposits. For each deposits there is a value indicating
     * the type of resource and a value indicating the amount
     */
    private Map<String, String> deposits;
    /**
     * It is a String representation of the situation of the player's strongbox. For each resource, there is a value
     * indicating its type and a value indicating its amount
     */
    private Map<String, String> strongbox;
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
     * String representation of the player's leadercards. For each card, there is a value indicating if it is active, discarded
     * or available for action (activate/discard). And a String int for the id?
     */
    private Map<String, String> leaders;
    /**
     * Representation of the DevelopCards' slots of the player. Each card is represented by its id
     */
    private List<int[]> slots;
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

    private int initialRes;

    private String currentPlayer;

    /**
     * Constructor of the class. It instantiates the faithtrack and the tiles
     */
    public ModelView(){
        faithTrack=new int[25];
        tiles=new Tile[3];
        for (int i=0; i<3; i++){
            tiles[i]=new Tile(i+2);
        }
        for (int i=0; i<25; i++){
            if (i==8 || i==16 || i==24){
                if (i==8) faithTrack[i]= 2;
                else if (i==16) faithTrack[i]= 9;
                else faithTrack[i]= 20;
            } else if (i<=2) faithTrack[i]= 0;
            else if (i>=3 && i<=5) faithTrack[i]= 1;
            else if (i>=6 && i<=8) faithTrack[i]= 2;
            else if (i>=9 && i<=11) faithTrack[i]= 4;
            else if (i>=12 && i<=14) faithTrack[i]= 6;
            else if (i>=15 && i<=17) faithTrack[i]= 9;
            else if (i>=18 && i<=20) faithTrack[i]= 12;
            else if (i>=21 && i<=23) faithTrack[i]= 16;
        }
        this.slots= new ArrayList<>();
        this.slots.add(new int[3]);
        this.slots.add(new int[3]);
        this.slots.add(new int[3]);
        this.deposits=new HashMap<>();
        deposits.put("smallres", "empty");
        deposits.put("smallqty", String.valueOf(0));
        deposits.put("midres", "empty");
        deposits.put("midqty", String.valueOf(0));
        deposits.put("bigres", "empty");
        deposits.put("bigqty", String.valueOf(0));
        this.strongbox= new HashMap<>();
        String[] res={"BLUE", "PURPLE", "GREY", "YELLOW"};
        String box, qty;
        for (int i=0; i<res.length; i++){
            box="strres"+i;
            qty="strqty"+i;
            strongbox.put(box, res[i]);
            strongbox.put(qty, String.valueOf(0));
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
    public int[] getFaithTrack() {
        return faithTrack;
    }

    /**
     * Unused?
     * @param faithTrack
     */
    public void setFaithTrack(int[] faithTrack) {
        this.faithTrack = faithTrack;
    }

    /**
     * Player's position getter method
     * @return player's position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Player's position setter method
     * @param position the new value for player's position
     */
    public void setPosition(int position) {
        if (position > 24) position = 24;
        this.position = position;
    }

    /**
     * Player's tiles getter method
     * @return the player's tiles
     */
    public Tile[] getTiles() {
        return tiles;
    }

    /**
     * Player's tiles setter method
     * @param tiles the new values of the tiles (if activated or discarded)
     */
    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    /**
     * Player's deposits getter method
     * @return the player's deposits
     */
    public Map<String, String> getDeposits() {
        return deposits;
    }

    /**
     * Player's deposits setter method
     * @param deposits the new value for player's deposits
     */
    public void setDeposits(Map<String, String> deposits) {
        this.deposits = deposits;
    }

    /**
     * Player's strongbox getter method
     * @return the player's strongbox
     */
    public Map<String, String> getStrongbox() {
        return strongbox;
    }

    /**
     * Player's strongbox setter method
     * @param strongbox the new value for player's strongbox
     */
    public void setStrongbox(Map<String, String> strongbox) {
        this.strongbox = strongbox;
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
    public Map<String, String> getLeaders() {
        return leaders;
    }

    /**
     * Player's leaders setter method
     * @param leaders the new player's leaders
     */
    public void setLeaders(Map<String, String> leaders) {
        this.leaders = leaders;
    }

    /**
     * Player's slots getter method
     * @return the player's slots
     */
    public List<int[]> getSlots() {
        return slots;
    }

    /**
     * Player's slots setter method
     * @param slots the new player's slots
     */
    public void setSlots(List<int[]> slots) {
        this.slots = slots;
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
}
