package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class represents a OneAndOneLeader, a leader card which has as requirement owning two development cards of two different colors.
 * This card gives a discount.
 */
public class OneAndOneLeader extends LeaderCard {
    private Color firstColor;
    private Color secondColor;
    private Color discount;

    /**
     * Constructor OneAndOneLeader creates a new OneAndOneLeader instance.
     * @param victoryPoints are the card's victory points.
     * @param type is the leader's type.
     * @param active is the state of the card.
     * @param discarded is the state of the card.
     * @param firstColor is the color of the first development cart required.
     * @param secondColor is the color of the second development card required.
     * @param discount is the color of the discount.
     */
    public OneAndOneLeader(int victoryPoints, String type, boolean active, boolean discarded, Color firstColor, Color secondColor, Color discount, int id) {
        super(victoryPoints, type, active, discarded, id);
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.discount = discount;
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
        boolean first = false;
        boolean second = false;
        DevelopCard[] slot1= slots.get(0);
        DevelopCard[] slot2= slots.get(1);
        DevelopCard[] slot3= slots.get(2);

        for (DevelopCard x: slot1) {
            if (x!= null && x.getColor() == firstColor)
                first = true;
            if (x!= null && x.getColor() == secondColor)
                second = true;
        }

        for (DevelopCard x: slot2) {
            if (x!= null && x.getColor() == firstColor)
                first = true;
            if (x!= null && x.getColor() == secondColor)
                second = true;
        }

        for (DevelopCard x: slot3) {
            if (x!= null && x.getColor() == firstColor)
                first = true;
            if (x!= null && x.getColor() == secondColor)
                second = true;
        }

        return first&&second;
    }

    /**
     * This method returns the color of the discount.
     * @return the color of the discount.
     */
    @Override
    public Color getDiscount() {
        return discount;
    }

    /**
     * This method converts the object to a string.
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return super.toString() +"OneAndOneLeader{" +
                "firstColor=" + firstColor +
                ", secondColor=" + secondColor +
                ", discount=" + discount +
                '}';
    }
}
