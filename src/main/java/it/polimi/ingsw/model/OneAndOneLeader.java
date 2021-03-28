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
    public OneAndOneLeader(int victoryPoints, String type, boolean active, boolean discarded, Color firstColor, Color secondColor, Color discount) {
        super(victoryPoints, type, active, discarded);
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.discount = discount;
    }

    /**
     * This method checks if the player respects the requirements. Not all parameters may be used.
     * @param deposits is the list of the player's deposits.
     * @param slot1 is the player's first stack of development cards.
     * @param slot2 is the player's second stack of development cards.
     * @param slot3 is the player's third stack of development cards.
     * @param faithMarker is the player's faith marker.
     * @return if the player respects the requirements.
     */
    @Override
    public boolean checkRequirements(ArrayList<ResourceAmount> deposits, DevelopCard[] slot1, DevelopCard[] slot2, DevelopCard[] slot3, FaithMarker faithMarker) {
        boolean first = false;
        boolean second = false;

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
