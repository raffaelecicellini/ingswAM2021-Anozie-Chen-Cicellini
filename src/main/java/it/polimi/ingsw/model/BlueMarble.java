package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import java.util.ArrayList;

/**
 * This class represents a specific type of Marble, the BlueMarble. It is a singleton.
 */
public class BlueMarble implements Marble{
    /**
     * This attribute represents the instance of the BlueMarble
     */
    private static BlueMarble instance=null;

    /**
     * Constructor of the BlueMarble, called by getInstance()
     */
    private BlueMarble() {}

    /**
     * If the instance is not already created, it creates one.
     * @return: the instance of the BlueMarble
     */
    public static BlueMarble getInstance() {
        if (instance==null){
            instance= new BlueMarble();
        }
        return instance;
    }
    /**
     * This method defines the action performed by a BlueMarble. It tries to put a Shield in the chosen deposit. If this can't be done,
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
    public int action(String chosen, ArrayList<ResourceAmount> deposits, FaithMarker faith, ArrayList<LeaderCard> leaders, Color chosenColor)
    throws InvalidActionException {
        int dep= this.parseChoice(chosen);
        if (dep>=0 && dep<3){
            // small, mid or big
            ResourceAmount current= deposits.get(dep); // current situation in the selected deposit
            if ((current.getColor()==Color.BLUE && current.getAmount()<(dep+1)) || current.getAmount()==0){
                int duplicate= checkDuplicates(deposits, dep);
                if (duplicate>=0 && deposits.get(duplicate).getAmount()<(duplicate+1)){
                    // another deposit has the BLUE ResourceAmount && it's not full
                    throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                }
                else if (duplicate>=0){
                    // another deposit has the BLUE ResourceAmount but it's full
                    if (checkSwap(deposits, duplicate)){
                        throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can do a swap to put it in another one!");
                    }
                    if ((deposits.size()>3 && deposits.get(3).getColor()==Color.BLUE && deposits.get(3).getAmount()<2) ||
                            (deposits.size()>4 && deposits.get(4).getColor()==Color.BLUE && deposits.get(4).getAmount()<2)){
                        throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                    }
                    return 1;
                }
                ResourceAmount newval= new ResourceAmount(Color.BLUE, current.getAmount()+1);
                deposits.set(dep, newval);
            }
            else if (current.getColor()==Color.BLUE && current.getAmount()==(dep+1)){
                // current is BLUE and it's full
                if (checkSwap(deposits, dep)){
                    throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can do a swap to put it in another one!");
                }
                if ((deposits.size()>3 && deposits.get(3).getColor()==Color.BLUE && deposits.get(3).getAmount()<2) ||
                        (deposits.size()>4 && deposits.get(4).getColor()==Color.BLUE && deposits.get(4).getAmount()<2)){
                    throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                }
                return 1;
            }
            else if (current.getColor()!=Color.BLUE){
                boolean space=checkSpace(deposits);
                if (space){
                    throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one! (maybe with a swap)");
                }
                if ((deposits.size()>3 && deposits.get(3).getColor()==Color.BLUE && deposits.get(3).getAmount()<2) ||
                        (deposits.size()>4 && deposits.get(4).getColor()==Color.BLUE && deposits.get(4).getAmount()<2)){
                    throw new InvalidActionException("You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one!");
                }
                return 1;
            }
        }
        if (dep>=3){
            if (deposits.size()>dep){
                ResourceAmount current=deposits.get(dep);
                if (current.getColor()!=Color.BLUE){
                    throw new InvalidActionException("You don't have a deposit leader of " + BlueMarble.getInstance() + " in this position");
                }
                if (current.getColor()==Color.BLUE && current.getAmount()<2){
                    ResourceAmount newval= new ResourceAmount(Color.BLUE, current.getAmount()+1);
                    deposits.set(dep, newval);
                }
                else if (current.getColor()==Color.BLUE && current.getAmount()==2){
                    boolean space=checkSpace(deposits);
                    if (space){
                        throw new InvalidActionException("You can't put You can't put the " + BlueMarble.getInstance() + " in the " + chosen + " deposit, but you can put it in another one! (maybe with a swap)");
                    }
                    return 1;
                }
            }
            else {
                throw new InvalidActionException("You don't have an active deposit leader in the selected position for the " + BlueMarble.getInstance());
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
        switch (chosen.toLowerCase()){
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
            if (res.getColor()==Color.BLUE && res.getAmount()>0 && i!=curr) {
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
            if ((res.getColor()==Color.BLUE && res.getAmount()>0 && res.getAmount()<(i+1)) || (res.getAmount()==0)) {
                ret=true;
            }
            else if(res.getColor()==Color.BLUE && res.getAmount()==(i+1)){
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
     * @return: the String representation of a BlueMarble
     */
    @Override
    public String toString() {
        return "BlueMarble";
    }
}
