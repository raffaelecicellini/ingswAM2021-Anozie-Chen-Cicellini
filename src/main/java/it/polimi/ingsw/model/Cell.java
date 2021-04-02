package it.polimi.ingsw.model;

/**
 * This class represents a Cell of the Faith Track.
 * It has an amount of Victory Points and it may be a Pope Space.
 */
public class Cell {
    private int victoryPoints;
    private boolean popeSpace;

    /**
     * Constructor Space creates a new Space instance.
     */
    public Cell(int victoryPoints, boolean popeSpace) {
        this.victoryPoints = victoryPoints;
        this.popeSpace = popeSpace;
    }

    /**
     * This method returns the amount of the Cell's Victory Points.
     * @return Cell's Victory Points
     */
    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    /**
     * This method sets the Cell's amount of Victory Points
     * @param victoryPoints is the amount of Victory Points to give to the Cell
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     * This method returns if the Cell is a Pope Space.
     * @return if the Cell is Pope Space.
     */
    public boolean isPopeSpace() {
        return this.popeSpace;
    }

    /**
     * This method sets PopeSpace attribute of the Cell.
     * @param popeSpace sets the PopeSpace attribute for the class.
     */
    public void setPopeSpace(boolean popeSpace) {
        this.popeSpace = popeSpace;
    }
}
