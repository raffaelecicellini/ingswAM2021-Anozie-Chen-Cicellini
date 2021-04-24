package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class represents a FaithLeader, a leader card which has as requirement the player's faith marker to be at a certain position.
 */
public class FaithLeader extends LeaderCard{
    private int position;

    /**
     * Constructor FaithLeader creates a new FaithLeader instance.
     * @param victoryPoints are the card's victory points.
     * @param type is the leader's type.
     * @param active is the state of the card.
     * @param discarded is the state of the card.
     * @param position is the faith marker's position required in order to activate this card.
     */
    public FaithLeader(int victoryPoints, String type, boolean active, boolean discarded, int position, int id) {
        super(victoryPoints, type, active, discarded, id);
        this.position = position;
    }

    /**
     * This method checks if the player respects the requirements. Not all parameters may be used.
     * @param deposits is the list of the player's deposits.
     * @param slots is the list of the player's DevelopCard slots.
     * @param faithMarker is the player's faith marker.
     * @param strongbox is the player's strongbox
     * @return if the player respects the requirements.
     */
    @Override
    public boolean checkRequirements(ArrayList<ResourceAmount> deposits, ArrayList<DevelopCard[]> slots, FaithMarker faithMarker, ResourceAmount[] strongbox) {
        if (faithMarker != null && faithMarker.getPosition() >= position)
            return true;
        return false;
    }

    /**
     * This method converts the object to a string.
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return super.toString() + "FaithLeader{" +
                "position=" + position +
                '}';
    }
}
