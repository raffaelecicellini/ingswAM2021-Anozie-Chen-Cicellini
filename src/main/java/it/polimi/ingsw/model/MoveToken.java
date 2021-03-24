package it.polimi.ingsw.model;

public class MoveToken implements ActionToken{
    private int qty;

    public MoveToken(int qty) {
        this.qty=qty;
    }

    @Override
    public boolean doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        int newpos= blackCross.getPosition() + qty;
        blackCross.setPosition(newpos);
        return false;
    }

    @Override
    public String toString() {
        return "MoveToken{" +
                "qty=" + qty +
                '}';
    }
}
