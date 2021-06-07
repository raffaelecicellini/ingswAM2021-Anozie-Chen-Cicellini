package it.polimi.ingsw.client;

/**
 * This class represents a FavorTile on the client
 */
public class Tile {
    /**
     * Represents if the tile is discarded
     */
    private boolean isDiscarded;
    /**
     * Represents if the tile is active
     */
    private boolean isActive;
    /**
     * Represents the id of the tile (for the Gui) or the victory points given by the tile (for Cli)
     */
    private int id;

    /**
     * Constructor of a Tile
     *
     * @param id the value of the id attribute
     */
    public Tile(int id) {
        this.id = id;
        this.isActive = false;
        this.isDiscarded = false;
    }

    /**
     * Used to check if the tile is discarded
     *
     * @return the value of isDiscarded
     */
    public boolean isDiscarded() {
        return isDiscarded;
    }

    /**
     * isDiscarded setter method
     *
     * @param discarded the new value of isDiscarded
     */
    public void setDiscarded(boolean discarded) {
        isDiscarded = discarded;
    }

    /**
     * Used to check if the tile is active
     *
     * @return the value of isActive
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * isActive setter method
     *
     * @param active the new value of isActive
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * id getter method
     *
     * @return the value of the id
     */
    public int getId() {
        return id;
    }

    /**
     * id setter method
     *
     * @param id the new value for the id
     */
    public void setId(int id) {
        this.id = id;
    }
}