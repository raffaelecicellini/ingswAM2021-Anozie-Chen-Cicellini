package it.polimi.ingsw.model;

import java.util.*;

/**
 * This class represents the ActionTokens' array used in the Solo game.
 */
public class SoloActions {

    /**
     * This attribute represents the ActionTokens' array of the current game.
     */
    private ActionToken[] tokens;

    /**
     * This attribute specifies the current token that is yet to be activated.
     */
    private int current;

    /**
     * It instantiates the SoloActions used in the current game.
     */
    public SoloActions(){
        this.current=0;
        this.tokens= new ActionToken[7];
        this.generateTokens();
        this.shuffle();
    }

    /**
     * Utility method used to shuffle the array (called at the beginning of the game and if a MoveandShuffleToken is picked).
     */
    private void shuffle() {
        List<ActionToken> list= Arrays.asList(tokens);
        Collections.shuffle(list);
        list.toArray(tokens);
        this.current=0;
    }

    /**
     * This method is called by the Solo game. It calls the doAction() method of the ActionToken indicated
     * by current and, eventually, shuffles the array (if the current token is a MoveandShuffleToken).
     * @param blackCross: this is the position of "Lorenzo il Magnifico" on the FaithTrack.
     * @param decks: these are all of the DevelopDecks of the current game.
     */
    public void doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        boolean res=tokens[current].doAction(blackCross, decks);
        if (res || current==6) {
            this.shuffle();
        }
        else this.current++;
    }

    /**
     * Utility method called by the constructor. It instantiates the different ActionTokens of the current game.
     */
    private void generateTokens() {
        this.tokens[0]= new DiscardToken(Color.BLUE, 2);
        this.tokens[1]= new DiscardToken(Color.GREEN, 2);
        this.tokens[2]= new DiscardToken(Color.PURPLE, 2);
        this.tokens[3]= new DiscardToken(Color.YELLOW, 2);
        this.tokens[4]= new MoveToken(2);
        this.tokens[5]= new MoveToken(2);
        this.tokens[6]= new MoveandShuffleToken(1);
    }

    /**
     * Utility method used in tests. It returns the String representation of the token's array and the value of current.
     * @return the String representation of SoloActions
     */
    @Override
    public String toString() {
        return "SoloActions{" +
                "tokens=" + Arrays.toString(tokens) +
                ", "+ current +
                '}';
    }

    /**
     * Just for testing
     * @param tokens tokens
     */
    protected void setTokens(ActionToken[] tokens) {
        this.tokens = tokens;
    }
}
