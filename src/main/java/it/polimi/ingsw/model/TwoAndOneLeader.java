package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class represents a TwoAndOneLeader, a leader card which has as requirement owning two development cards of a certain color and a development card of another color.
 * This card handles white marbles as a marble of a certain color.
 */
public class TwoAndOneLeader extends LeaderCard {
    private Color twoColor;
    private Color oneColor;
    private Color newType;

    /**
     * Constructor TwoAndOneLeader creates a new TwoAndOneLeader instance.
     *
     * @param victoryPoints are the card's victory points.
     * @param type          is the leader's type.
     * @param active        is the state of the card.
     * @param discarded     is the state of the card.
     * @param twoColor      is the color of the two development cards needed.
     * @param oneColor      is the color of the other development card needed.
     * @param newType       is how the white marbles will be handled.
     */
    public TwoAndOneLeader(int victoryPoints, String type, boolean active, boolean discarded, Color twoColor, Color oneColor, Color newType, int id) {
        super(victoryPoints, type, active, discarded, id);
        this.twoColor = twoColor;
        this.oneColor = oneColor;
        this.newType = newType;
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
        int two = 0;
        int one = 0;
        DevelopCard[] slot1 = slots.get(0);
        DevelopCard[] slot2 = slots.get(1);
        DevelopCard[] slot3 = slots.get(2);
        for (DevelopCard x : slot1) {
            if (x != null && x.getColor() == twoColor)
                two++;
            if (x != null && x.getColor() == oneColor)
                one++;
        }

        for (DevelopCard x : slot2) {
            if (x != null && x.getColor() == twoColor)
                two++;
            if (x != null && x.getColor() == oneColor)
                one++;
        }

        for (DevelopCard x : slot3) {
            if (x != null && x.getColor() == twoColor)
                two++;
            if (x != null && x.getColor() == oneColor)
                one++;
        }

        if (two >= 2 && one >= 1)
            return true;
        return false;
    }

    /**
     * This method returns the color of how the white marbles will be handled.
     *
     * @return the color of how the white marbles will be handled.
     */
    @Override
    public Color getWhiteBall() {
        return newType;
    }

    /**
     * This method converts the object to a string.
     *
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return "TwoAndOneLeader{" +
                "twoColor=" + twoColor +
                ", oneColor=" + oneColor +
                ", newType=" + newType +
                '}';
    }
}
