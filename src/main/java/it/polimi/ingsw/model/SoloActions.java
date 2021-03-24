package it.polimi.ingsw.model;

import java.util.*;

public class SoloActions {
    private ActionToken[] tokens;
    private int current;

    public SoloActions(){
        this.current=0;
        this.tokens= new ActionToken[7];
        this.generateTokens();
        this.shuffle();
    }
    private void shuffle() {
        List<ActionToken> list= Arrays.asList(tokens);
        Collections.shuffle(list);
        list.toArray(tokens);
        this.current=0;
    }
    public void doAction(FaithMarker blackCross, DevelopDeck[][] decks) {
        boolean res=tokens[current].doAction(blackCross, decks);
        if (res) {
            this.shuffle();
        }
        else this.current++;
    }
    private void generateTokens() {
        this.tokens[0]= new DiscardToken(Color.BLUE, 2);
        this.tokens[1]= new DiscardToken(Color.GREEN, 2);
        this.tokens[2]= new DiscardToken(Color.PURPLE, 2);
        this.tokens[3]= new DiscardToken(Color.YELLOW, 2);
        this.tokens[4]= new MoveToken(2);
        this.tokens[5]= new MoveToken(2);
        this.tokens[6]= new MoveandShuffleToken(1);
    }

    @Override
    public String toString() {
        return "SoloActions{" +
                "tokens=" + Arrays.toString(tokens) +
                ", "+ current +
                '}';
    }
}
