package it.polimi.ingsw.model;

public class Cell {
    private int victoryPoints;
    private boolean popeSpace;

    public Cell(int victoryPoints, boolean popeSpace) {
        this.victoryPoints = victoryPoints;
        this.popeSpace = popeSpace;
    }

    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public boolean isPopeSpace() {
        return popeSpace;
    }

    public void setPopeSpace(boolean popeSpace) {
        this.popeSpace = popeSpace;
    }
}
