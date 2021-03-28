package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class represents a OneOfAllLeader, a leader card which has as requirement owning a development card of all colors.
 */
public class OneOfAllLeader extends LeaderCard {

    /**
     * Constructor OneOfAllLeader creates a new OneOfAllLeader instance.
     * @param victoryPoints are the card's victory points.
     * @param type is the leader's type.
     * @param active is the state of the card.
     * @param discarded is the state of the card.
     */
    public OneOfAllLeader(int victoryPoints, String type, boolean active, boolean discarded) {
        super(victoryPoints, type, active, discarded);
    }

    /**
     * This method checks if the player respects the requirements. Not all parameters may be used.
     * @param deposits is the list of the player's deposits.
     * @param slot1 is the player's first stack of development cards.
     * @param slot2 is the player's second stack of development cards.
     * @param slot3 is the player's third stack of development cards.
     * @param faithMarker is the player's faith marker.
     * @param strongbox is the player's strongbox
     * @return if the player respects the requirements.
     */
    @Override
    public boolean checkRequirements(ArrayList<ResourceAmount> deposits, DevelopCard[] slot1, DevelopCard[] slot2, DevelopCard[] slot3, FaithMarker faithMarker, ResourceAmount[] strongbox) {
        boolean yellow = false;
        boolean green = false;
        boolean blue = false;
        boolean purple = false;

        for (DevelopCard x: slot1) {
            if(x!= null && x.getColor() == Color.YELLOW)
                yellow = true;
            if(x!= null && x.getColor() == Color.GREEN)
                green = true;
            if(x!= null && x.getColor() == Color.BLUE)
                blue = true;
            if(x!= null && x.getColor() == Color.PURPLE)
                purple = true;
        }

        for (DevelopCard x: slot2) {
            if(x!= null && x.getColor() == Color.YELLOW)
                yellow = true;
            if(x!= null && x.getColor() == Color.GREEN)
                green = true;
            if(x!= null && x.getColor() == Color.BLUE)
                blue = true;
            if(x!= null && x.getColor() == Color.PURPLE)
                purple = true;
        }

        for (DevelopCard x: slot3) {
            if(x!= null && x.getColor() == Color.YELLOW)
                yellow = true;
            if(x!= null && x.getColor() == Color.GREEN)
                green = true;
            if(x!= null && x.getColor() == Color.BLUE)
                blue = true;
            if(x!= null && x.getColor() == Color.PURPLE)
                purple = true;
        }
        return yellow&&green&&blue&&purple;
    }

    /**
     * This method converts the object to a string.
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
