package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;

/**
 * This class represents a specific type of Marble, the PurpleMarble. It is a singleton.
 */
public class PurpleMarble extends Marble {
    /**
     * This attribute represents the instance of the PurpleMarble
     */
    private static PurpleMarble instance = null;

    /**
     * Constructor of the PurpleMarble, called by getInstance()
     */
    private PurpleMarble() {
    }

    /**
     * If the instance is not already created, it creates one.
     *
     * @return: the instance of the PurpleMarble
     */
    public static PurpleMarble getInstance() {
        if (instance == null) {
            instance = new PurpleMarble();
        }
        return instance;
    }

    /**
     * This method defines the action performed by a PurpleMarble. It tries to put a Servant in the chosen deposit. If this can't be done,
     * the resource is discarded (return 1) or an exception is thrown.
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
        int dep = this.parseChoice(chosen);
        if (dep >= 0 && dep < 3) {
            ResourceAmount current = deposits.get(dep);
            if (current.getAmount() == 0 || (current.getColor() == Color.PURPLE && current.getAmount() < (dep + 1))) {
                int duplicate = checkDuplicates(deposits, dep, Color.PURPLE);
                if (duplicate >= 0 && deposits.get(duplicate).getAmount() < (duplicate + 1)) {
                    throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                } else if (duplicate >= 0) {
                    if (checkSwap(deposits, duplicate)) {
                        throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can try doing a swap!");
                    }
                    if ((deposits.size() > 3 && deposits.get(3).getColor() == Color.PURPLE && deposits.get(3).getAmount() < 2) ||
                            (deposits.size() > 4 && deposits.get(4).getColor() == Color.PURPLE && deposits.get(4).getAmount() < 2)) {
                        throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                    }
                    return 1;
                }
                ResourceAmount newval = new ResourceAmount(Color.PURPLE, current.getAmount() + 1);
                deposits.set(dep, newval);
                return 0;
            } else if (current.getColor() == Color.PURPLE && current.getAmount() == (dep + 1)) {
                if (checkSwap(deposits, dep)) {
                    throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can try doing a swap!");
                }
                if ((deposits.size() > 3 && deposits.get(3).getColor() == Color.PURPLE && deposits.get(3).getAmount() < 2) ||
                        (deposits.size() > 4 && deposits.get(4).getColor() == Color.PURPLE && deposits.get(4).getAmount() < 2)) {
                    throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                }
                return 1;
            } else if (current.getColor() != Color.PURPLE && current.getAmount() > 0) {
                boolean space = checkSpace(deposits, Color.PURPLE);
                if (space) {
                    throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one! (maybe with a swap)");
                }
                if ((deposits.size() > 3 && deposits.get(3).getColor() == Color.PURPLE && deposits.get(3).getAmount() < 2) ||
                        (deposits.size() > 4 && deposits.get(4).getColor() == Color.PURPLE && deposits.get(4).getAmount() < 2)) {
                    throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                }
                return 1;
            }
        }
        if (dep >= 3) {
            if (deposits.size() > dep) {
                ResourceAmount current = deposits.get(dep);
                if (current.getColor() != Color.PURPLE) {
                    throw new InvalidActionException("You don't have a deposit leader of " + PurpleMarble.getInstance() + " in this position");
                }
                if (current.getColor() == Color.PURPLE && current.getAmount() < 2) {
                    ResourceAmount newval = new ResourceAmount(Color.PURPLE, current.getAmount() + 1);
                    deposits.set(dep, newval);
                    return 0;
                } else if (current.getColor() == Color.PURPLE && current.getAmount() == 2) {
                    boolean space = checkSpace(deposits, Color.PURPLE);
                    if (space) {
                        throw new InvalidActionException("You can't put the " + PurpleMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one! (maybe with a swap)");
                    }
                    return 1;
                }
            } else {
                throw new InvalidActionException("You don't have an active deposit leader in the selected position for the " + PurpleMarble.getInstance());
            }
        }
        return 0;
    }

    /**
     * Simple method used in tests
     *
     * @return: the String representation of a PurpleMarble
     */
    public String toString() {
        return "PURPLE";
    }
}
