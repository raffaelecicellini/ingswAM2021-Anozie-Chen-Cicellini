package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;

/**
 * This class represents a specific type of Marble, the RedMarble. It is a singleton.
 */
public class RedMarble implements Marble {
    /**
     * This attribute represents the instance of the RedMarble
     */
    private static RedMarble instance = null;

    /**
     * Constructor of the RedMarble, called by getInstance()
     */
    private RedMarble() {
    }

    /**
     * If the instance is not already created, it creates one.
     *
     * @return: the instance of the RedMarble
     */
    public static RedMarble getInstance() {
        if (instance == null) {
            instance = new RedMarble();
        }
        return instance;
    }

    /**
     * This method defines the action performed by a RedMarble. It increases by 1 the position of the player's FaithMarker.
     *
     * @param chosen:      a String representing the deposit chosen by the client in which to put the resource
     * @param deposits:    a List of all the deposits of the player
     * @param faith:       the FaithMarker of the player
     * @param leaders:     the leaders of the player
     * @param chosenColor: used in case a player has two active leaders that change the value of the white marble: the user needs to choose which color he wants
     * @throws InvalidActionException: it is thrown when the action can't be performed
     * @return: the amount of resources discarded during the action (can be 0 or 1)
     */
    @Override
    public int action(String chosen, ArrayList<ResourceAmount> deposits, FaithMarker faith, ArrayList<LeaderCard> leaders, Color chosenColor)
            throws InvalidActionException {
        int pos = faith.getPosition();
        pos = pos + 1;
        faith.setPosition(pos);
        return 0;
    }

    /**
     * Simple method used in tests
     *
     * @return: the String representation of a RedMarble
     */
    public String toString() {
        return "RED";
    }
}
