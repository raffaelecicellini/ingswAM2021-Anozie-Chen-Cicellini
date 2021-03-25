package it.polimi.ingsw.model;

/**
 * This class represents a specific type of ActionToken, the MoveToken.
 */
public class MoveToken implements ActionToken{
    /**
     * This attribute specifies how much the current position of the FaithMarker needs to be increased.
     */
    private int qty;

    /**
     * It instantiates a MoveToken.
     * @param qty: it specifies how much the current position of the FaithMarker needs to be increased.
     */
    public MoveToken(int qty) {
        this.qty=qty;
    }

    /**
     * This method specifies what needs to be done by the MoveToken: it increases the position of the blackCross
     * FaithMarker by the amount specified by the attribute "qty".
     * @param blackCross: this is the position of "Lorenzo il Magnifico" on the FaithTrack.
     * @param decks: these are all of the DevelopDecks of the current game.
     * @return: it returns false, the ActionTokens' array is not to be shuffled.
     */
    @Override
    public boolean doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        int newpos= blackCross.getPosition() + qty;
        blackCross.setPosition(newpos);
        return false;
    }
    /**
     * Utility method used in tests. It returns the String representation of the token.
     * @return
     */
    @Override
    public String toString() {
        return "MoveToken{" +
                "qty=" + qty +
                '}';
    }
}
