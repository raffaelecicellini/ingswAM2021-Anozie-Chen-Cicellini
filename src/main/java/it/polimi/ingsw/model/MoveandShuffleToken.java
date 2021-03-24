package it.polimi.ingsw.model;

public class MoveandShuffleToken implements ActionToken{
    private int qty;

    public MoveandShuffleToken(int qty) {
        this.qty=qty;
    }

    @Override
    public boolean doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        int newpos= blackCross.getPosition() + qty;
        blackCross.setPosition(newpos);
        return true;
    }

    @Override
    public String toString() {
        return "MoveandShuffleToken{" +
                "qty=" + qty +
                '}';
    }
}
