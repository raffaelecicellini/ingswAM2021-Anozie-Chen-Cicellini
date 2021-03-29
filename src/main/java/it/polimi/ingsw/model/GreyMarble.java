package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;

/**
 * This class represents a specific type of Marble, the GreyMarble. It is a singleton.
 */
public class GreyMarble implements Marble{
    /**
     * This attribute represents the instance of the GreyMarble
     */
    private static GreyMarble instance=null;
    /**
     * Constructor of the GreyMarble, called by getInstance()
     */
    private GreyMarble() {}
    /**
     * If the instance is not already created, it creates one.
     * @return: the instance of the GreyMarble
     */
    public static GreyMarble getInstance() {
        if (instance==null){
            instance= new GreyMarble();
        }
        return instance;
    }
    /**
     * This method defines the action performed by a GreyMarble. It tries to put a Stone in the chosen deposit. If this can't be done,
     * the resource is discarded (return 1) or an exception is thrown.
     * @param chosen: a String representing the deposit chosen by the client in which to put the resource
     * @param deposits: a List of all the deposits of the player
     * @param faith: the FaithMarker of the player
     * @param leaders: the leaders of the player
     * @param chosenColor: used in case a player has two active leaders that change the value of the white marble: the user needs to choose which color he wants
     * @return: the amount of resources discarded during the action (can be 0 or 1)
     * @throws InvalidActionException: it is thrown when the action can't be performed
     */
    @Override
    public int action(String chosen, ArrayList<ResourceAmount> deposits, FaithMarker faith, LeaderCard[] leaders, Color chosenColor)
            throws InvalidActionException {
        int dep= this.parseChoice(chosen);
        if (dep>=0 && dep<3){
            ResourceAmount current= deposits.get(dep);
            if ((current.getColor()==Color.GREY && current.getAmount()<(dep+1)) || current.getAmount()==0){
                int duplicate= checkDuplicates(deposits, dep);
                if (duplicate>=0 && deposits.get(duplicate).getAmount()<(duplicate+1)){
                    throw new InvalidActionException("You can't put it here but you can put it in another deposit");
                }
                else if (duplicate>=0){
                    if (checkSwap(deposits, duplicate)){
                        throw new InvalidActionException("You can't put it here, but you can do a swap to put it in a deposit");
                    }
                    if ((deposits.size()>3 && deposits.get(3).getColor()==Color.GREY && deposits.get(3).getAmount()<2) ||
                            (deposits.size()>4 && deposits.get(4).getColor()==Color.GREY && deposits.get(4).getAmount()<2)){
                        throw new InvalidActionException("You can't put it here but you can put it in another deposit");
                    }
                    return 1;
                }
                ResourceAmount newval= new ResourceAmount(Color.GREY, current.getAmount()+1);
                deposits.set(dep, newval);
            }
            else if (current.getColor()==Color.GREY && current.getAmount()==(dep+1)){
                if (checkSwap(deposits, dep)){
                    throw new InvalidActionException("You can't put it here, but you can do a swap to put it in a deposit");
                }
                if ((deposits.size()>3 && deposits.get(3).getColor()==Color.GREY && deposits.get(3).getAmount()<2) ||
                        (deposits.size()>4 && deposits.get(4).getColor()==Color.GREY && deposits.get(4).getAmount()<2)){
                    throw new InvalidActionException("You can't put it here but you can put it in another deposit");
                }
                return 1;
            }
            else if (current.getColor()!=Color.GREY){
                boolean space=checkSpace(deposits);
                if (space){
                    throw new InvalidActionException("You can't put it here but you can put it in another deposit (maybe with a swap)");
                }
                if ((deposits.size()>3 && deposits.get(3).getColor()==Color.GREY && deposits.get(3).getAmount()<2) ||
                        (deposits.size()>4 && deposits.get(4).getColor()==Color.GREY && deposits.get(4).getAmount()<2)){
                    throw new InvalidActionException("You can't put it here but you can put it in another deposit");
                }
                return 1;
            }
        }
        if (dep>=3){
            if (deposits.size()>dep){
                ResourceAmount current=deposits.get(dep);
                if (current.getColor()!=Color.GREY){
                    throw new InvalidActionException("You don't have a deposit leader of this type in this position");
                }
                if (current.getColor()==Color.GREY && current.getAmount()<2){
                    ResourceAmount newval= new ResourceAmount(Color.GREY, current.getAmount()+1);
                    deposits.set(dep, newval);
                }
                else if (current.getColor()==Color.GREY && current.getAmount()==2){
                    boolean space=checkSpace(deposits);
                    if (space){
                        throw new InvalidActionException("You can't put it here but you can put it in another deposit (maybe with a swap)");
                    }
                    return 1;
                }
            }
            else {
                throw new InvalidActionException("You don't have an active deposit leader");
            }
        }
        return 0;
    }
    /**
     * Utility method used to parse the String representation of the chosen deposit in a index of the deposits' list
     * @param chosen: the String representation of the chosen deposit
     * @return: the index of the chosen deposit
     */
    private int parseChoice(String chosen){
        switch (chosen){
            case "small": return 0;
            case "mid": return 1;
            case "big": return 2;
            case "sp1": return 3;
            case "sp2": return 4;
        }
        return -1;
    }
    /**
     * Utility method used to check if a Shield is already stored in a different deposit
     * @param deposits: the list of the player's deposit
     * @param curr: the index of the chosen deposit
     * @return: the index of the deposit which already contains a Shield if present, otherwise it returns -1
     */
    private int checkDuplicates(ArrayList<ResourceAmount> deposits, int curr){
        for (int i=0; i<3; i++) {
            ResourceAmount res= deposits.get(i);
            if (res.getColor()==Color.GREY && res.getAmount()>0 && i!=curr) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Utility method used to check if there is a deposit that can store a Shield
     * @param deposits: the list of the player's deposit
     * @return: true if there is a deposit, false otherwise
     */
    private boolean checkSpace(ArrayList<ResourceAmount> deposits){
        boolean ret=false;
        for (int i=0; i<3; i++) {
            ResourceAmount res= deposits.get(i);
            if ((res.getColor()==Color.GREY && res.getAmount()>0 && res.getAmount()<(i+1)) || (res.getAmount()==0)) {
                ret=true;
            }
            else if(res.getColor()==Color.GREY && res.getAmount()==(i+1)){
                if (checkSwap(deposits, i)){
                    ret=true;
                }
                else {
                    return false;
                }
            }
        }
        return ret;
    }
    /**
     * Utility method used to check if two deposit can be swapped.
     * @param deposits: the list of the player's deposits
     * @param curr: the index of the chosen deposit
     * @return: true if two deposits can be swapped, false otherwise
     */
    private boolean checkSwap(ArrayList<ResourceAmount> deposits, int curr){
        ResourceAmount res= deposits.get(curr);
        for(int i=0; i<3; i++){
            if (i<curr && res.getAmount()<=i+1){
                return true;
            }
            else if (i>curr && deposits.get(i).getAmount()<=curr+1){
                return true;
            }
        }
        return false;
    }
    /**
     * Simple method used in tests
     * @return: the String representation of a GreyMarble
     */
    public String toString() {
        return "GreyMarble";
    }
}
