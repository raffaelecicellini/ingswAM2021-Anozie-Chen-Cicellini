package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * LevTwoLeader represents a LevTwoLeader, a leader cards which has as requirement owning a level 2 development card of a certain color.
 * This card gives an extra production.
 */
public class LevTwoLeader extends LeaderCard {
    private Color color;
    private Color production;

    /**
     * LevTwoLeader constructor creates a new LevTwoLeader instance.
     *
     * @param victoryPoints are the card's victory points.
     * @param type          is the leader's type.
     * @param active        is the state of the card.
     * @param discarded     is the state of the card.
     * @param color         is the color of the level 2 development card needed for the requirements.
     * @param production    is the color of the resource needed for the extra production.
     */
    public LevTwoLeader(int victoryPoints, String type, boolean active, boolean discarded, Color color, Color production, int id) {
        super(victoryPoints, type, active, discarded, id);
        this.color = color;
        this.production = production;
    }

    /**
     * This method checks if the player respects the requirements. Not all parameters may be used.
     *
     * @param deposits    is the list of the player's deposits.
     * @param slots       is the list of the player's DevelopCard slots.
     * @param faithMarker is the player's faith marker.
     * @param strongbox   is the player's strongbox
     * @return if the player respects the requirements.
     */
    @Override
    public boolean checkRequirements(ArrayList<ResourceAmount> deposits, ArrayList<DevelopCard[]> slots, FaithMarker faithMarker, ResourceAmount[] strongbox) {
        DevelopCard[] slot1 = slots.get(0);
        DevelopCard[] slot2 = slots.get(1);
        DevelopCard[] slot3 = slots.get(2);
        for (DevelopCard x : slot1)
            if (x != null && x.getColor() == color && x.getLevel() == 2)
                return true;

        for (DevelopCard x : slot2)
            if (x != null && x.getColor() == color && x.getLevel() == 2)
                return true;

        for (DevelopCard x : slot3)
            if (x != null && x.getColor() == color && x.getLevel() == 2)
                return true;
        return false;
    }

    /**
     * This method returns the color needed for the extra production.
     *
     * @return the color needed for the extra production.
     */
    @Override
    public Color getProduction() {
        return production;
    }

    /**
     * This method returns a String which represents the object.
     *
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return super.toString() + "LevTwoLeader{" +
                "color=" + color +
                ", production=" + production +
                '}';
    }
}
