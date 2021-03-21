package it.polimi.ingsw;

public class FavorTile {
    private int victoryPoints;
    private boolean active;

    public FavorTile(int victoryPoints,boolean active) {
        this.victoryPoints = victoryPoints;
        this.active = active;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
