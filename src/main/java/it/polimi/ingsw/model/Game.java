package it.polimi.ingsw.model;

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

}
