package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;

/**
 * This class represents a specific type of Marble, the WhiteMarble. It is a singleton.
 */
public class WhiteMarble implements Marble{
    /**
     * This attribute represents the instance of the WhiteMarble
     */
    private static WhiteMarble instance=null;
    /**
     * Constructor of the WhiteMarble, called by getInstance()
     */
    private WhiteMarble() {}
    /**
     * If the instance is not already created, it creates one.
     * @return: the instance of the WhiteMarble
     */
    public static WhiteMarble getInstance() {
        if (instance==null){
            instance= new WhiteMarble();
        }
        return instance;
    }
    /**
     * This method defines the action performed by a WhiteMarble. It checks if there is at least one active leader that changes the color
     * of the White Marble. If there is one, it is called the method of the corresponding Marble (Blue, Yellow, Purple, Grey). If there are two,
     * it checks the color chosen by the player and calls the method of the corresponding marble (or an exception if this color is wrong).
     * If there is none, the method returns 0.
     * @param chosen: a String representing the deposit chosen by the client in which to put the resource
     * @param deposits: a List of all the deposits of the player
     * @param faith: the FaithMarker of the player
     * @param leaders: the leaders of the player
     * @param chosenColor: used in case a player has two active leaders that change the value of the white marble: the user needs to choose which color he wants
     * @return: the amount of resources discarded during the action (can be 0 or 1)
     * @throws InvalidActionException: it is thrown when the action can't be performed
     */
    @Override
    public int action(String chosen, ArrayList<ResourceAmount> deposits, FaithMarker faith, ArrayList<LeaderCard> leaders, Color chosenColor)
            throws InvalidActionException
    {
        //check active leaders and call getWhiteBall(): if !=null and I have two active WBLeaders, check the chosenColor (exception
        //if there is no match). Then if there is at least one active WBLeader, check the color, get the instance of the
        //same marble and call the action method on it and return its value (or exception). Otherwise return 0.
        Color firstLeader=null;
        Color secondLeader=null;
        Color current;
        for(LeaderCard leader: leaders){
            if (leader.isActive()){
                current=leader.getWhiteBall();
                if (current!=null && firstLeader==null){
                    firstLeader=current;
                }
                else if (current!=null && secondLeader==null){
                    secondLeader=current;
                }
            }
        }
        if (firstLeader!=null && secondLeader!=null){
            if (firstLeader==chosenColor){
                Marble marble= parseColor(firstLeader);
                if (marble!=null){
                    return marble.action(chosen, deposits, faith, leaders, chosenColor);
                }
            }
            else if (secondLeader==chosenColor){
                Marble marble= parseColor(secondLeader);
                if (marble!=null){
                    return marble.action(chosen, deposits, faith, leaders, chosenColor);
                }
            }
            else throw new InvalidActionException("You don't have this type of leader");
        }
        else if (firstLeader!=null){
            Marble marble= parseColor(firstLeader);
            if (marble!=null){
                return marble.action(chosen, deposits, faith, leaders, chosenColor);
            }
        }
        else if (secondLeader!=null){
            Marble marble= parseColor(secondLeader);
            if (marble!=null){
                return marble.action(chosen, deposits, faith, leaders, chosenColor);
            }
        }
        return 0;
    }

    /**
     * Utility method used to get the instance of the marble of the corresponding color
     * @param color: the color in which the white marble is transformed
     * @return: the instance of the corresponding marble
     */
    private Marble parseColor(Color color){
        switch (color){
            case BLUE: return BlueMarble.getInstance();
            case GREY: return GreyMarble.getInstance();
            case PURPLE: return PurpleMarble.getInstance();
            case YELLOW: return YellowMarble.getInstance();
        }
        return null;
    }
    /**
     * Simple method used in tests
     * @return: the String representation of a WhiteMarble
     */
    public String toString() {
        return "WhiteMarble";
    }
}
