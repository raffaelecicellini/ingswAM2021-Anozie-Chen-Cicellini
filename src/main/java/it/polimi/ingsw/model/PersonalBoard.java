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
     * This method returns the position of .
     */
    public int getPosition() {
        return 0;
    }

    /**
     *
     */
    public void setPosition(int position) {

    }

    /**
     *
     */
    public void addToStrongbox(ResourceAmount[] res) {

    }


}
