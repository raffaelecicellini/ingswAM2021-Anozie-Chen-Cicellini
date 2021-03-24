package it.polimi.ingsw.model;

public class FaithMarker {
    private int position;

    public FaithMarker(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "FaithMarker{" +
                "position=" + position +
                '}';
    }
}
