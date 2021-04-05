package it.polimi.ingsw.model;

public class SoloGame extends Game{

    /**
     * this attribute represents Lorenzo il Magnifico in a Single Player Game
     */
    private FaithMarker blackCross;

    /**
     * Solo Actions used in a Single Player Game
     */
    private SoloActions soloActions;

    @Override
    public void start() {
        super.start();
    }

}
