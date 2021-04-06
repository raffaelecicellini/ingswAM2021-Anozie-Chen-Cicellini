package it.polimi.ingsw.model;

/**
 * This Class represents a FavorTile which can be found on the FaithTrack.
 * It has an amount of victory points and may be active.
 */
public class FavorTile {
    private int victoryPoints;
    private boolean active;
    private boolean discarded;
    private int start;
    private int end;

    /**
     * Constructor FavorTile creates a new FavorTile instance.
     * @param victoryPoints is the amount of Victory Points this FavorTile has.
     * @param active is the state of the FavorTile.
     * @param start is where the vatican report section starts, might be null.
     * @param end is where the vatican report section ends, might be null.
     */
    public FavorTile(int victoryPoints,boolean active, int start, int end) {
        this.victoryPoints = victoryPoints;
        this.active = active;
        this.start = start;
        this.end = end;
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
     * This method sets the state (active) of the FavorTile.
     * @param active is the new state of the FavorTIle.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This method sets the state (discarded) of the FavorTile.
     * @param discarded is the new state of the FavorTIle.
     */
    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    /**
     * This method returns the state of the FavorTile.
     * @return the state (active) of the FavorTile.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This method sets the state of the FavorTile.
     * @return the state(discarded) of the FavorTile.
     */
    public boolean isDiscarded() {
        return this.discarded;
    }

    /**
     * This method returns the end of the report section.
     * @return end of the report section.
     */
    public int getEnd() {
        return end;
    }

    /**
     * This method returns the start of the report section.
     * @return the start of the report section.
     */
    public int getStart() {
        return start;
    }
}
