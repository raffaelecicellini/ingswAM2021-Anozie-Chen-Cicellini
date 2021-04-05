package it.polimi.ingsw.model;

import com.sun.tools.javac.util.ArrayUtils;
import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.HashMap;
import java.util.Map;

public class Game {
    /**
     * Players that are in the Game
     */
    private Player[] players;

    /**
     * Current Active Players in the Game
     */
    private Player[] activePlayers;

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


    public void endTurn() {

    }

    public void chooseInitialResource(String player,Map<String, String> map) throws InvalidActionException {
        currentPlayer.chooseInitialResource(map);
    }

    public void buy(String player,Map<String, String> map) throws InvalidActionException, NumberFormatException {
        if (doneMandatory == true) throw new InvalidActionException("you have already done a mandatory operation");
        if(map.get("row")==null || map.get("column")==null) throw new InvalidActionException("you didn't select the card.");
        int row = Integer.parseInt(map.get("row"));
        int column = Integer.parseInt(map.get("column"));
        if (row<0 || row>4 || column<0 || column>3) throw new InvalidActionException("wrong indexes selected ");
        boolean end;
        DevelopCard card = developDecks[row][column].getCard();
        end = currentPlayer.buy(map, card);
        developDecks[row][column].removeCard();
        if (end)
            isEndGame = true;
        doneMandatory = true;
    }
}
