package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    /**
     * Players that are in the Game
     */
    private ArrayList<Player> players;
    private int current;
    /**
     * Current Active Players in the Game
     */
    private ArrayList<Player> activePlayers;

    /**
     * Current Player (has the turn)
     */
    private Player currentPlayer;

    /**
     * First Player
     */
    private Player firstPlayer;

    /**
     * defines if a Game is over
     */
    private boolean isEndGame;

    /**
     * the Game's Market
     */
    private Market market;

    /**
     * the Game's Develop Decks
     */
    private DevelopDeck[][] developDecks;

    /**
     * the Game's Leader Deck
     */
    private LeaderDeck leaderDeck;

    /**
     * this attribute establishes if a Player has already done a mandatory action
     */
    private boolean doneMandatory;

    /**
     * this attribute counts how many Leader actions a Player has done
     */
    private int doneLeader;

    /**
     * this attribute establishes if a Game is in a Single Player Mode or not
     */
    private boolean isSolo;

    /**
     * this attribute represents Lorenzo il Magnifico in a Single Player Game
     */
    private FaithMarker blackCross;

    /**
     * Solo Actions used in a Single Player Game
     */
    private SoloActions soloActions;

    public Game(){
        this.players= new ArrayList<>();
        this.activePlayers= new ArrayList<>();
        this.market= new Market();
        this.leaderDeck= new LeaderDeck();
        this.developDecks= new DevelopDeck[4][3];
        Color color;
        for (int col=0; col<4; col++){
            color= parseColor(col);
            for (int row=0; row<3; row++){
                this.developDecks[col][row]= new DevelopDeck(row+1, color);
            }
        }
    }

    private Color parseColor(int col){
        switch (col){
            case 0: return Color.GREEN;
            case 1: return Color.BLUE;
            case 2: return Color.YELLOW;
            case 3: return Color.PURPLE;
        }
        return null;
    }

    public void start(){

    }

    private void chooseFirst(){

    }

    public void createPlayer(String name){
        Player player= new Player(name);
        players.add(player);
        activePlayers.add(player);
    }

    public void produce(String player, Map<String, String> info) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.produce(info);
        else throw new InvalidActionException("It is not your turn!");
    }

    public void activateLeader(String player, int pos) throws InvalidActionException{
        if (currentPlayer.getName().equals(player)) currentPlayer.activateLeader(pos);
        else throw new InvalidActionException("It is not your turn!");
    }

    public void discardLeader(String player, int pos) throws InvalidActionException{
        if (currentPlayer.getName().equals(player)) currentPlayer.discardLeader(pos);
        else throw new InvalidActionException("It is not your turn!");
    }

    public void endTurn(){

    }

    /**
     * This method is used for a chooseInitialResource move at the start of the game.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException is the move is not valid.
     */
    public void chooseInitialResource(String player,Map<String, String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.chooseInitialResource(map);
        else throw new InvalidActionException("It is not your turn!");
    }

    /**
     * This method is used for a buy move during the turn.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     * @throws NumberFormatException if the format is not valid.
     */
    public void buy(String player, Map<String, String> map) throws InvalidActionException, NumberFormatException {
        if (!currentPlayer.getName().equals(player)) throw new InvalidActionException("It is not your turn!");
        if (doneMandatory) throw new InvalidActionException("you have already done a mandatory operation in this turn.");
        if(map.get("row")==null || map.get("column")==null) throw new InvalidActionException("you didn't select the card.");
        int row = Integer.parseInt(map.get("row"));
        int column = Integer.parseInt(map.get("column"));
        if (row<0 || row>=4 || column<0 || column>=3) throw new InvalidActionException("wrong indexes selected ");
        boolean end;
        DevelopCard card = developDecks[row][column].getCard();
        end = currentPlayer.buy(map, card);
        developDecks[row][column].removeCard();
        if (end)
            isEndGame = true;
        doneMandatory = true;
    }

    /**
     * This method is used for a swapDeposit move during the turn.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     */
    public void swapDeposit(String player, Map<String,String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.swapDeposit(map);
        else throw new InvalidActionException("It is not your turn!");
    }


    public void fromMarket(String player, int i, Map<String, String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.fromMarket(map, market.selectColumn(i));
        else throw new InvalidActionException("It is not your turn!");
    }

}
