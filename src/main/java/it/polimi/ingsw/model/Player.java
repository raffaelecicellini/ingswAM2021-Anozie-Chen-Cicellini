package it.polimi.ingsw.model;

public class Player {

    /**
     * Player's username
     */
    private String name;

    /**
     * Leader Cards chosen by Player
     */
    private LeaderCard[] leaders;

    /**
     * 4 Leader Cards, 2 to be chosen by Player
     */
    private LeaderCard[] leadersToChoose;

    /**
     * Player's Personal Board
     */
    private PersonalBoard personalBoard;

    /**
     * Player's number of initial Resource (depending on Player's turn)
     */
    private int numberInitialResource;

    /**
     * Player's number of Develop Cards (needed for checking when to finish the game)
     */
    private int numberDevelopCards;


}
