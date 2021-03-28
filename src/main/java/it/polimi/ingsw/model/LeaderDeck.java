package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * LeaderDeck class represents the leader deck used in the game.
 */
public class LeaderDeck {
    private ArrayList<LeaderCard> cards;

    /**
     * Constructor LeaderDeck creates a new LeaderDeck instance.
     */
    public LeaderDeck() {
        cards = new ArrayList<>();
        cards.add(new OneOfAllLeader(8,"OneOfAll",false,false));
        cards.add(new FaithLeader(7,"Faith",false,false,14));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.GREEN,Color.PURPLE));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.BLUE,Color.PURPLE,Color.BLUE));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.GREEN,Color.BLUE,Color.GREY));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.PURPLE,Color.YELLOW));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.PURPLE,5),Color.BLUE));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.BLUE,5),Color.YELLOW));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.GREY,5),Color.PURPLE));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.BLUE,Color.YELLOW,Color.GREY));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.YELLOW,Color.BLUE));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.BLUE,Color.PURPLE));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.PURPLE,Color.GREY));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.GREEN,Color.YELLOW));

        this.shuffle();
    }

    /**
     * This method shuffles the deck.
     */
    private void shuffle() {
        Collections.shuffle(cards);
    }
    /**
     * This method removes a card from the deck and returns it.
     * @return the card removed from the deck.
     */
    public LeaderCard removeCard() {
        return cards.remove(cards.size()-1);
    }

    /**
     * This method returns a String which represents the object.
     * @return a String which represents the object.
     */
    @Override
    public String toString() {
        return "LeaderDeck{" +
                "cards=" + cards +
                '}';
    }

}
