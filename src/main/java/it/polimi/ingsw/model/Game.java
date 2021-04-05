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

    public void fromMarket(String player, int i, Map<String, String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.fromMarket(map, market.selectColumn(i));
        else throw new InvalidActionException("It is not your turn!");
    }

}
