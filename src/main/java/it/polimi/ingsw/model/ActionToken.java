package it.polimi.ingsw.model;

/**
 * This interface represents a single ActionToken for the Solo game.
 */
public interface ActionToken {
    /**
     * This method defines the action done by a specific ActionToken.
     * @param blackCross: this is the position of "Lorenzo il Magnifico" on the FaithTrack.
     * @param decks: these are all of the DevelopDecks of the current game.
     * @return: this method returns true if the array of the ActionTokens needs to be shuffled.
     */
    boolean doAction(FaithMarker blackCross, DevelopDeck[][] decks);
    int getId();
}
