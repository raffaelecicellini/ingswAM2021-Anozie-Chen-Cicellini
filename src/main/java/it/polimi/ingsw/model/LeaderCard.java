package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This abstract class represents the blueprint of any LeaderCard.
 */
public abstract class LeaderCard {
    private int victoryPoints;
    private String type;
    private boolean active;
    private boolean discarded;

    /**
     * Constructor LeaderCard creates a new LeaderCard instance.
     * @param victoryPoints are the card's victory points.
     * @param type is the leader's type.
     * @param active is the state of the card.
     * @param discarded is the state of the card.
     */
    public LeaderCard(int victoryPoints, String type, boolean active, boolean discarded) {
        this.victoryPoints = victoryPoints;
        this.type = type;
        this.active = active;
        this.discarded = discarded;
    }

    /**
     * This method returns the leader card's type.
     * @return the leader card's type.
     */
    public String getType() {
        return type;
    }

    /**
     * This method returns if the card is active.
     * @return if the card is active.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This method changes the state of the card.
     * @param active is the new state of the card.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This method returns if the card is discarded.
     * @return if the card is discarded.
     */
    public boolean isDiscarded() {
        return discarded;
    }

    /**
     * This method changes the card's state.
     * @param discarded is the card's new state.
     */
    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    /**
     * This method returns the card's victory points.
     * @return the card's victory points.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * This method returns how the white marbles need to be handled.
     * @return how the white marbles need to be handled.
     */
    public Color getWhiteBall() {
        return null;
    }

    /**
     * This method returns the color of the extra deposit.
     * @return the color of the extra deposit.
     */
    public Color getDeposit() {
        return null;
    }

    /**
     * This method returns the color of the discount.
     * @return the color of the discount.
     */
    public Color getDiscount() {
        return null;
    }

    /**
     * This method returns the color required for the extra production.
     * @return the color required for the extra production.
     */
    public Color getProduction() {
        return null;
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
    public abstract boolean checkRequirements(ArrayList<ResourceAmount> deposits, DevelopCard[] slot1, DevelopCard[] slot2, DevelopCard[] slot3, FaithMarker faithMarker, ResourceAmount[] strongbox);

    /**
     * This method returns a String which represents the object.
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return super.toString() + "LeaderCard{" +
                "victoryPoints=" + victoryPoints +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", discarded=" + discarded +
                '}';
    }
}
