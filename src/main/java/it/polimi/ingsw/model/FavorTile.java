package it.polimi.ingsw.model;

/**
 * This Class represents a FavorTile which can be found on the FaithTrack.
 * It has an amount of victory points and may be active.
 */
public class FavorTile {
    private int victoryPoints;
    private boolean active;

    /**
     * Constructor FavorTile creates a new FavorTile instance.
     * @param victoryPoints is the amount of Victory Points this FavorTile has.
     * @param active is the state of the FavorTile.
     */
    public FavorTile(int victoryPoints,boolean active) {
        this.victoryPoints = victoryPoints;
        this.active = active;
    }

    /**
     * This method sets the FavorTile's victory points.
     * @param victoryPoints the new amount of victory points contained in the FavorTile.
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     * This method returns the amount of victory points contained in the FavorTile.
     * @return amount of victory points contained in the FavorTile.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * This method sets the state of the FavorTile.
     * @param active is the new state of the FavorTIle.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This method returns the state of the FavorTile.
     * @return the state of the FavorTile.
     */
    public boolean isActive() {
        return active;
    }
}
