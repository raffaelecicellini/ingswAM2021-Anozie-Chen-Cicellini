package it.polimi.ingsw.model;

/**
 * This class represents a FaithMarker.
 * It is characterized by its position on the Faith Track.
 */
public class FaithMarker {
    private int position;

    /**
     *Constructor FaithMarker creates a new FaithMarker instance.
     * @param position is the FaithMarker's initial position.
     */
    public FaithMarker(int position) {
        this.position = position;
    }

    /**
     * This method returns the position of the FaithMarker.
     * @return the position of the FaithMarker.
     */
    public int getPosition() {
        return position;
    }

    /**
     * This methods sets the position of the FaithMarker.
     * @param position is the new position of the FaithMarker.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * This method converts the class to a String.
     * @return a String which represents the class.
     */
    @Override
    public String toString() {
        return "FaithMarker{" +
                "position=" + position +
                '}';
    }
}
