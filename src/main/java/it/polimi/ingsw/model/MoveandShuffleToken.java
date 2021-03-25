package it.polimi.ingsw.model;

/**
 * This class represents a specific type of ActionToken, the MoveandShuffleToken.
 */
public class MoveandShuffleToken implements ActionToken{
    /**
     * This attribute specifies how much the current position of the FaithMarker needs to be increased.
     */
    private int qty;

    /**
     * It instantiates a MoveandShuffleToken.
     * @param qty: it specifies how much the current position of the FaithMarker needs to be increased.
     */
    public MoveandShuffleToken(int qty) {
        this.qty=qty;
    }

    /**
     * This method specifies what needs to be done by the MoveandShuffleToken: it increases the position of the blackCross
     * FaithMarker by the amount specified by the attribute "qty" and specifies that the ActionTokens' array needs to be
     * shuffled.
     * @param blackCross: this is the position of "Lorenzo il Magnifico" on the FaithTrack.
     * @param decks: these are all of the DevelopDecks of the current game.
     * @return: it returns true, the ActionTokens' array needs to be shuffled.
     */
    @Override
    public boolean doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        int newpos= blackCross.getPosition() + qty;
        blackCross.setPosition(newpos);
        return true;
    }

    /**
     * Utility method used in tests. It returns the String representation of the token.
     * @return
     */
    @Override
    public String toString() {
        return "MoveandShuffleToken{" +
                "qty=" + qty +
                '}';
    }
}
