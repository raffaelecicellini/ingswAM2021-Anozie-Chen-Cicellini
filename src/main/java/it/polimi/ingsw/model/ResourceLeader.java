package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class represents a ResourceLeader, a leader card which has as requirement owning an amount of resources of a certain color in the deposits.
 * This card gives an extra deposit.
 */
public class ResourceLeader extends LeaderCard {
    private ResourceAmount requirements;
    private Color typeDeposit;

    /**
     * Constructor ResourceLeader creates a new ResourceLeader instance.
     *
     * @param victoryPoints are the card's victory points.
     * @param type          is the leader's type.
     * @param active        is the state of the card.
     * @param discarded     is the state of the card.
     * @param requirements  is the amount of resources needed.
     * @param typeDeposit   is the color of the extra deposit.
     */
    public ResourceLeader(int victoryPoints, String type, boolean active, boolean discarded, ResourceAmount requirements, Color typeDeposit, int id) {
        super(victoryPoints, type, active, discarded, id);
        this.requirements = requirements;
        this.typeDeposit = typeDeposit;
    }

    /**
     * This method checks if the player respects the requirements. Not all parameters may be used.
     *
     * @param deposits    is the list of the player's deposits.
     * @param slots       is the list of the player's Developcard slots.
     * @param faithMarker is the player's faith marker.
     * @param strongbox   is the player's strongbox
     * @return if the player respects the requirements.
     */
    @Override
    public boolean checkRequirements(ArrayList<ResourceAmount> deposits, ArrayList<DevelopCard[]> slots, FaithMarker faithMarker, ResourceAmount[] strongbox) {
        int counter = 0;
        for (ResourceAmount x : deposits)
            if (x != null && x.getColor() == requirements.getColor())
                counter += x.getAmount();
        for (ResourceAmount x : strongbox)
            if (x != null && x.getColor() == requirements.getColor())
                counter += x.getAmount();
        if (counter >= requirements.getAmount())
            return true;
        return false;
    }

    /**
     * This method returns the color of the extra deposit.
     *
     * @return the color of the extra deposit.
     */
    @Override
    public Color getDeposit() {
        return this.typeDeposit;
    }

    /**
     * This method converts the object to a string.
     *
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return super.toString() + "ResourceLeader{" +
                "requirements=" + requirements +
                ", typeDeposit=" + typeDeposit +
                '}';
    }
}
