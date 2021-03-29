package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;

/**
 * This interface represents a generic Marble that has a method action() redefined by the classes that implements it.
 */
public interface Marble {
    /**
     * This method defines the action performed by a specific Marble.
     * @param chosen: a String representing the deposit chosen by the client in which to put the resource
     * @param deposits: a List of all the deposits of the player
     * @param faith: the FaithMarker of the player
     * @param leaders: the leaders of the player
     * @param chosenColor: used in case a player has two active leaders that change the value of the white marble: the user needs to choose which color he wants
     * @return: the amount of resources discarded during the action (can be 0 or 1)
     * @throws InvalidActionException: it is thrown when the action can't be performed
     */
    public int action (String chosen, ArrayList<ResourceAmount> deposits, FaithMarker faith, LeaderCard[] leaders, Color chosenColor) throws InvalidActionException;
}
