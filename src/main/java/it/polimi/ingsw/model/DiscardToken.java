package it.polimi.ingsw.model;

/**
 * This class represents a specific type of ActionToken, the DiscardToken.
 */
public class DiscardToken implements ActionToken{
    /**
     * This attribute defines the Color of the cards to be removed from the decks.
     */
    private Color color;
    /**
     * This attribute defines the amount of cards to be removed from the decks.
     */
    private int qty;

    private int id;

    /**
     * It instantiates a DiscardToken.
     * @param color: it defines the Color of the cards to be removed.
     * @param qty: it defines the amount of cards to be removed.
     */
    public DiscardToken(Color color, int qty) {
        this.color=color;
        this.qty=qty;
        this.id=parseColor(color);
    }

    private int parseColor(Color color){
        switch (color){
            case BLUE: return 1;
            case GREEN: return 2;
            case PURPLE: return 3;
            case YELLOW: return 4;
        }
        return 0;
    }
    public int getId(){
        return this.id;
    }

    /**
     * This method specifies what needs to be done by the DiscardToken: it removes from the decks an amount of cards(qty)
     * of the specified Color (color).
     * @param blackCross: this is the position of "Lorenzo il Magnifico" on the FaithTrack.
     * @param decks: these are all of the DevelopDecks of the current game.
     * @return: it returns false, the ActionTokens' array is not to be shuffled.
     */
    @Override
    public boolean doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        DevelopDeck res= this.search(decks);
        int counter=0;
        if (res!=null) {
            if (res.getTop()>=qty-1) {
                for(int i=0; i<qty; i++) {
                    res.removeCard();
                }
            }
            else {
                while (res.getTop()>=0) {
                    res.removeCard();
                    counter++;
                }
                res= this.search(decks);
                while(counter<qty) {
                    if (res!=null) {
                        res.removeCard();
                    }
                    counter++;
                }
            }
        }
        return false;
    }

    /**
     * Utility method used in doAction(). It searches the DevelopDeck of the minimum level for the current color that still have cards.
     * @param decks: these are all of the DevelopDecks of the current game.
     * @return: the method returns the DevelopDeck of the minimum level for the current color if found, otherwise it returns null.
     */
    private DevelopDeck search(DevelopDeck[][] decks) {
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) {
                if (decks[i][j].getColor().equals(color) && decks[i][j].getTop()>=0) {
                    return decks[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Utility method used in tests. It returns the String representation of the token.
     * @return the String representation of DiscardToken
     */
    @Override
    public String toString() {
        return "DiscardToken{" +
                "color=" + color +
                ", qty=" + qty +
                '}';
    }
}
