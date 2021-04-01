package it.polimi.ingsw.model;

import java.util.List;

public class PersonalBoard {
    /**
     * Player's personal Faith Track
     */
    private Cell[] faithTrack;

    /**
     * Player's personal Strongbox
     */
    private ResourceAmount[] strongbox;

    /**
     * Player's personal Deposits
     */
    private List<ResourceAmount> deposits;

    /**
     * Player's personal Develop Card Deposit (1)
     */
    private DevelopCard[] slot1;

    /**
     * Player's personal Develop Card Deposit (2)
     */
    private DevelopCard[] slot2;

    /**
     * Player's personal Develop Card Deposit (3)
     */
    private DevelopCard[] slot3;

    /**
     * Player's "extra Develop Card slot" (1) (activated only by Leader Cards)
     */
    private DevelopCard extraslot1;

    /**
     * Player's "extra Develop Card slot" (2) (activated only by Leader Cards)
     */
    private DevelopCard extraslot2;

    /**
     * Player's personal Faith Marker
     */
    private FaithMarker faithMarker;

    /**
     * Favor Tiles "on" the Faith Track
     */
    private FavorTile[] favorTile;

    public PersonalBoard() {
    }


    /**
     * This method returns the position of the faith marker.
     */
    public int getPosition() {
        return faithMarker.getPosition();
    }

    /**
     *This method changes the faith marker's position.
     */
    public void setPosition(int position) {
        faithMarker.setPosition(position);
    }

    /**
     *This method adds resources to the strongbox
     * @param res is the array of resources.
     */
    public void addToStrongbox(ResourceAmount[] res) {
        for (int i = 0; i < res.length; i++)
            for (int j = 0; j < strongbox.length; j++)
                if (res[i].getColor() == strongbox[j].getColor())
                    strongbox[j].setAmount(strongbox[j].getAmount()+res[i].getAmount());

    }

    /**
     * This method returns the strongbox.
     * @return the strongbox.
     */
    public ResourceAmount[] getStrongbox() {
        return strongbox;
    }

    /**
     * This method changes the strongbox reference.
     * @param strongbox is the new strongbox reference.
     */
    public void setStrongbox(ResourceAmount[] strongbox) {
        this.strongbox = strongbox;
    }

    /**
     * This method returns the Cell at that index.
     * @param pos is the index.
     * @return the Cell at that index.
     */
    public Cell getCell(int pos) {
        return faithTrack[pos];
    }

    /**
     * This method returns the state of a FavorTile at a specific index.
     * @param pos is the index of the FavorTile.
     * @return the state of the FavorTile.
     */
    public boolean getTile (int pos) {
        return favorTile[pos].isActive();
    }

    /**
     * This method changes the status of a FavorTile at a specific index.
     * @param pos is the index of the FavorTile.
     * @param status is the new status of the FavorTile.
     */
    public void setTile (int pos, boolean status) {
        favorTile[pos].setActive(status);
    }

    /**
     * This method returns the FaithMarker.
     * @return the FaithMarker.
     */
    public FaithMarker getFaithMarker() {
        return faithMarker;
    }
}
