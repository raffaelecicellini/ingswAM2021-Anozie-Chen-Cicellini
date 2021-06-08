package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;

/**
 * This abstract class represents a generic Marble that has a method action() redefined by the classes that implements it.
 */
public abstract class Marble {
    /**
     * This method defines the action performed by a specific Marble.
     *
     * @param chosen:      a String representing the deposit chosen by the client in which to put the resource
     * @param deposits:    a List of all the deposits of the player
     * @param faith:       the FaithMarker of the player
     * @param leaders:     the leaders of the player
     * @param chosenColor: used in case a player has two active leaders that change the value of the white marble: the user needs to choose which color he wants
     * @throws InvalidActionException: it is thrown when the action can't be performed
     * @return: the amount of resources discarded during the action (can be 0 or 1)
     */
    public abstract int action(String chosen, ArrayList<ResourceAmount> deposits, FaithMarker faith, ArrayList<LeaderCard> leaders, Color chosenColor) throws InvalidActionException;

    /**
     * Utility method used to parse the String representation of the chosen deposit in a index of the deposits' list
     *
     * @param chosen: the String representation of the chosen deposit
     * @return: the index of the chosen deposit
     */
    public int parseChoice(String chosen) {
        switch (chosen.toLowerCase()) {
            case "small":
                return 0;
            case "mid":
                return 1;
            case "big":
                return 2;
            case "sp1":
                return 3;
            case "sp2":
                return 4;
        }
        return -1;
    }

    /**
     * Utility method used to check if two deposit can be swapped.
     *
     * @param deposits: the list of the player's deposits
     * @param curr:     the index of the chosen deposit
     * @return: true if two deposits can be swapped, false otherwise
     */
    public boolean checkSwap(ArrayList<ResourceAmount> deposits, int curr) {
        ResourceAmount res = deposits.get(curr);
        for (int i = 0; i < 3; i++) {
            if (i < curr && res.getAmount() <= i + 1) {
                return true;
            } else if (i > curr && deposits.get(i).getAmount() <= curr + 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utility method used to check if a resource of the color passed as a parameter is already stored in a different deposit
     *
     * @param deposits: the list of the player's deposit
     * @param curr:     the index of the chosen deposit
     * @param color: the color to search for duplicates
     * @return: the index of the deposit which already contains a Shield if present, otherwise it returns -1
     */
    public int checkDuplicates(ArrayList<ResourceAmount> deposits, int curr, Color color){
        for (int i = 0; i < 3; i++) {
            ResourceAmount res = deposits.get(i);
            if (res.getColor() == color && res.getAmount() > 0 && i != curr) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Utility method used to check if there is a deposit that can store a resource of color passed as a parameter
     *
     * @param deposits: the list of the player's deposit
     * @param color: the color to check space for
     * @return: true if there is a deposit, false otherwise
     */
    public boolean checkSpace(ArrayList<ResourceAmount> deposits, Color color){
        boolean ret = false;
        for (int i = 0; i < 3; i++) {
            ResourceAmount res = deposits.get(i);
            if ((res.getColor() == color && res.getAmount() > 0 && res.getAmount() < (i + 1)) || (res.getAmount() == 0)) {
                ret = true;
            } else if (res.getColor() == color && res.getAmount() == (i + 1)) {
                if (checkSwap(deposits, i)) {
                    ret = true;
                } else {
                    return false;
                }
            }
        }
        return ret;
    }

    /**
     * Overrides standard toString for a Marble (it is overridden by each type of Marble)
     *
     * @return a String representation of the Marble
     */
    public abstract String toString();
}
