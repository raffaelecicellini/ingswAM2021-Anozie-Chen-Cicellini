package it.polimi.ingsw.client;

public class Tile {
    private boolean isDiscarded;
    private boolean isActive;
    private int id;

    public Tile(int id){
        this.id=id;
        this.isActive=false;
        this.isDiscarded=false;
    }

    public boolean isDiscarded() {
        return isDiscarded;
    }

    public void setDiscarded(boolean discarded) {
        isDiscarded = discarded;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
