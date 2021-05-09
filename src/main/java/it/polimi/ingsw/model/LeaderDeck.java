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
        // EXTRA LEADERS
        cards.add(new OneOfAllLeader(8,"OneOfAll",false,false, 1));
        cards.add(new FaithLeader(7,"Faith",false,false,14, 2));
        // LEADERS THAT GIVE A DISCOUNT ON BUYING A DEV CARD
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.GREEN,Color.PURPLE, 3));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.BLUE,Color.PURPLE,Color.BLUE, 4));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.GREEN,Color.BLUE,Color.GREY, 5));
        cards.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.PURPLE,Color.YELLOW, 6));
        // LEADERS THAT GIVE AN EXTRA DEPOSIT
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY, 7));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.PURPLE,5),Color.BLUE, 8));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.BLUE,5),Color.YELLOW, 9));
        cards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.GREY,5),Color.PURPLE, 10));
        // LEADERS THAT CHANGE THE WHITE MARBLE IN THE MARKET
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE, 11));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE, 12));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.BLUE,Color.YELLOW,Color.GREY, 13));
        cards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 14));
        // LEADERS THAT GIVE AN EXTRA PRODUCTION
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.YELLOW,Color.BLUE, 15));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.BLUE,Color.PURPLE, 16));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.PURPLE,Color.GREY, 17));
        cards.add(new LevTwoLeader(4,"LevTwo",false,false,Color.GREEN,Color.YELLOW, 18));

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
