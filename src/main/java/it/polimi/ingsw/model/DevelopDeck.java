package it.polimi.ingsw.model;

import java.util.*;

/**
 * This class represents a DevelopDeck, a deck of DevelopCards of a certain color and level.
 */
public class DevelopDeck {
    /**
     * This attribute represents the array of the DevelopCards of the current deck.
     */
    private DevelopCard[] cards;
    /**
     * This attribute specifies the level of the cards of the current DevelopDeck.
     */
    private int level;
    /**
     * This attribute represents the color of the cards of the current DevelopDeck.
     */
    private Color color;
    /**
     * This attribute specifies the top of the deck. The card in this position is the one that can be bought by the user.
     */
    private int top;

    /**
     * It instantiates a DevelopDeck.
     *
     * @param level: it specifies the level of the current DevelopDeck.
     * @param color: it specifies the color of the current DevelopDeck.
     */
    public DevelopDeck(int level, Color color) {
        this.level = level;
        this.color = color;
        this.top = 3;
        this.cards = new DevelopCard[4];
        this.generateCards();
        this.shuffle();
    }

    /**
     * Simple method to get the level value.
     *
     * @return: the level of this deck.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Simple method to get the color value.
     *
     * @return: the color of this deck.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Simple method to get the top value.
     *
     * @return: the top of this deck.
     */
    public int getTop() {
        return top;
    }

    /**
     * Utility method used to shuffle tha array of cards.
     */
    private void shuffle() {
        List<DevelopCard> list = Arrays.asList(cards);
        Collections.shuffle(list);
        list.toArray(cards);
    }

    /**
     * Simple method used when a player buys correctly a card from the current deck.
     */
    public void removeCard() {
        if (this.top >= 0) {
            this.top = this.top - 1;
        }

    }

    /**
     * Simple method used when a player wants to buy a card from the current deck.
     *
     * @return: it returns the card in the top position.
     */
    public DevelopCard getCard() {
        if (this.top >= 0) {
            return this.cards[top];
        } else return null;
    }

    /**
     * Utility method used to generate the DevelopCards of level and color specified by the attributes.
     */
    private void generateCards() {
        int vp = 0;
        int faith = 0;
        ResourceAmount[] cost;
        ResourceAmount[] input;
        ResourceAmount[] output;
        switch (this.level) {
            case 1:
                switch (this.color) {
                    case PURPLE:
                        vp = 1;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 2);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 1);
                        vp = 4;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 2);
                        cost[1] = new ResourceAmount(Color.GREY, 2);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 1);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 2);
                        vp = 3;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 3);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 1);
                        output[2] = new ResourceAmount(Color.GREY, 1);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 3);
                        vp = 2;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 1);
                        cost[1] = new ResourceAmount(Color.PURPLE, 1);
                        cost[2] = new ResourceAmount(Color.YELLOW, 1);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 1);
                        output[1] = new ResourceAmount(Color.GREY, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 4);
                        break;
                    case BLUE:
                        vp = 2;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 1);
                        cost[1] = new ResourceAmount(Color.PURPLE, 1);
                        cost[2] = new ResourceAmount(Color.GREY, 1);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.GREY, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 5);
                        vp = 4;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 2);
                        cost[1] = new ResourceAmount(Color.PURPLE, 2);
                        cost[2] = new ResourceAmount(Color.GREY, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.GREY, 1);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 6);
                        vp = 1;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 2);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 7);
                        vp = 3;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 3);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.BLUE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 1);
                        output[1] = new ResourceAmount(Color.PURPLE, 1);
                        output[2] = new ResourceAmount(Color.BLUE, 1);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 8);
                        break;
                    case GREEN:
                        vp = 2;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 1);
                        cost[1] = new ResourceAmount(Color.PURPLE, 1);
                        cost[2] = new ResourceAmount(Color.GREY, 1);
                        cost[3] = new ResourceAmount(Color.YELLOW, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 9);
                        vp = 3;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 3);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.GREY, 0);
                        cost[3] = new ResourceAmount(Color.YELLOW, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 2);
                        input[1] = new ResourceAmount(Color.GREY, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.BLUE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 1);
                        output[2] = new ResourceAmount(Color.GREY, 1);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 10);
                        vp = 1;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 2);
                        cost[1] = new ResourceAmount(Color.YELLOW, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 11);
                        vp = 4;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 2);
                        cost[1] = new ResourceAmount(Color.YELLOW, 2);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.PURPLE, 1);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.BLUE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 2);
                        output[1] = new ResourceAmount(Color.PURPLE, 0);
                        output[2] = new ResourceAmount(Color.BLUE, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 12);
                        break;
                    case YELLOW:
                        vp = 3;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 3);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 2);
                        input[1] = new ResourceAmount(Color.PURPLE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.GREY, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 1);
                        output[1] = new ResourceAmount(Color.PURPLE, 1);
                        output[2] = new ResourceAmount(Color.GREY, 1);
                        output[3] = new ResourceAmount(Color.BLUE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 13);
                        vp = 4;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 2);
                        cost[1] = new ResourceAmount(Color.BLUE, 2);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.PURPLE, 1);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.BLUE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 2);
                        output[1] = new ResourceAmount(Color.PURPLE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 14);
                        vp = 1;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 2);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.YELLOW, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 1);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.BLUE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 15);
                        vp = 2;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 1);
                        cost[1] = new ResourceAmount(Color.GREY, 1);
                        cost[2] = new ResourceAmount(Color.YELLOW, 1);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.GREY, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 1);
                        output[1] = new ResourceAmount(Color.PURPLE, 0);
                        output[2] = new ResourceAmount(Color.BLUE, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 16);
                        break;
                }
                break;
            case 2:
                switch (this.color) {
                    case PURPLE:
                        vp = 5;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 4);
                        cost[1] = new ResourceAmount(Color.YELLOW, 0);
                        cost[2] = new ResourceAmount(Color.GREY, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.PURPLE, 0);
                        input[3] = new ResourceAmount(Color.GREY, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 17);
                        vp = 8;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 3);
                        cost[1] = new ResourceAmount(Color.BLUE, 3);
                        cost[2] = new ResourceAmount(Color.GREY, 0);
                        cost[3] = new ResourceAmount(Color.YELLOW, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 18);
                        vp = 7;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 5);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.GREY, 0);
                        cost[3] = new ResourceAmount(Color.YELLOW, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 2);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.BLUE, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 19);
                        vp = 6;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 3);
                        cost[1] = new ResourceAmount(Color.YELLOW, 2);
                        cost[2] = new ResourceAmount(Color.BLUE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.PURPLE, 1);
                        input[2] = new ResourceAmount(Color.BLUE, 0);
                        input[3] = new ResourceAmount(Color.GREY, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 3);
                        output[1] = new ResourceAmount(Color.PURPLE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 20);
                        break;
                    case BLUE:
                        vp = 6;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 3);
                        cost[1] = new ResourceAmount(Color.GREY, 2);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.GREY, 1);
                        input[2] = new ResourceAmount(Color.BLUE, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 3);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 21);
                        vp = 7;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 5);
                        cost[1] = new ResourceAmount(Color.GREY, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 2);
                        output[1] = new ResourceAmount(Color.GREY, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 22);
                        vp = 5;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 4);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 23);
                        vp = 8;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 3);
                        cost[1] = new ResourceAmount(Color.GREY, 3);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 24);
                        break;
                    case GREEN:
                        vp = 6;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 3);
                        cost[1] = new ResourceAmount(Color.PURPLE, 2);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.PURPLE, 1);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.GREY, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 3);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 25);
                        vp = 5;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 4);
                        cost[1] = new ResourceAmount(Color.GREY, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 0);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 26);
                        vp = 8;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 3);
                        cost[1] = new ResourceAmount(Color.YELLOW, 3);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 2);
                        output[1] = new ResourceAmount(Color.PURPLE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 27);
                        vp = 7;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 5);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 28);
                        break;
                    case YELLOW:
                        vp = 6;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 3);
                        cost[1] = new ResourceAmount(Color.BLUE, 2);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 1);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 3);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 29);
                        vp = 8;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 3);
                        cost[1] = new ResourceAmount(Color.PURPLE, 3);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 30);
                        vp = 7;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 5);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 2);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 31);
                        vp = 5;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 4);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 0);
                        output[1] = new ResourceAmount(Color.GREY, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 32);
                        break;
                }
                break;
            case 3:
                switch (this.color) {
                    case PURPLE:
                        vp = 12;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 4);
                        cost[1] = new ResourceAmount(Color.BLUE, 4);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 3);
                        output[1] = new ResourceAmount(Color.PURPLE, 1);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 33);
                        vp = 10;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 5);
                        cost[1] = new ResourceAmount(Color.YELLOW, 2);
                        cost[2] = new ResourceAmount(Color.GREY, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 1);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 2);
                        output[1] = new ResourceAmount(Color.PURPLE, 2);
                        output[2] = new ResourceAmount(Color.BLUE, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 34);
                        vp = 11;
                        faith = 3;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 7);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.PURPLE, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 35);
                        vp = 9;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.PURPLE, 6);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 3);
                        output[1] = new ResourceAmount(Color.GREY, 0);
                        output[2] = new ResourceAmount(Color.BLUE, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 36);
                        break;
                    case BLUE:
                        vp = 12;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 4);
                        cost[1] = new ResourceAmount(Color.GREY, 4);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.GREY, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 3);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 37);
                        vp = 9;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 6);
                        cost[1] = new ResourceAmount(Color.GREY, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 3);
                        output[1] = new ResourceAmount(Color.GREY, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 38);
                        vp = 11;
                        faith = 3;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 7);
                        cost[1] = new ResourceAmount(Color.BLUE, 0);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 1);
                        output[1] = new ResourceAmount(Color.PURPLE, 0);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 39);
                        vp = 10;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.YELLOW, 5);
                        cost[1] = new ResourceAmount(Color.GREY, 2);
                        cost[2] = new ResourceAmount(Color.BLUE, 0);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 1);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 2);
                        output[1] = new ResourceAmount(Color.GREY, 2);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.BLUE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 40);
                        break;
                    case GREEN:
                        vp = 10;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 5);
                        cost[1] = new ResourceAmount(Color.PURPLE, 2);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.YELLOW, 1);
                        input[1] = new ResourceAmount(Color.PURPLE, 1);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.BLUE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.BLUE, 2);
                        output[1] = new ResourceAmount(Color.GREY, 2);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 41);
                        vp = 9;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 6);
                        cost[1] = new ResourceAmount(Color.GREY, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.PURPLE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 2);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 3);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 42);
                        vp = 12;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 4);
                        cost[1] = new ResourceAmount(Color.YELLOW, 4);
                        cost[2] = new ResourceAmount(Color.PURPLE, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 3);
                        output[1] = new ResourceAmount(Color.BLUE, 1);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 43);
                        vp = 11;
                        faith = 3;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.BLUE, 7);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.GREY, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.PURPLE, 1);
                        input[1] = new ResourceAmount(Color.BLUE, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 1);
                        output[1] = new ResourceAmount(Color.GREY, 0);
                        output[2] = new ResourceAmount(Color.BLUE, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 44);
                        break;
                    case YELLOW:
                        vp = 9;
                        faith = 2;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 6);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 2);
                        input[1] = new ResourceAmount(Color.GREY, 0);
                        input[2] = new ResourceAmount(Color.YELLOW, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 3);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[0] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 45);
                        vp = 11;
                        faith = 3;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 7);
                        cost[1] = new ResourceAmount(Color.PURPLE, 0);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.PURPLE, 1);
                        output[1] = new ResourceAmount(Color.BLUE, 0);
                        output[2] = new ResourceAmount(Color.YELLOW, 0);
                        output[3] = new ResourceAmount(Color.GREY, 0);
                        cards[1] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 46);
                        vp = 12;
                        faith = 0;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 4);
                        cost[1] = new ResourceAmount(Color.PURPLE, 4);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.BLUE, 1);
                        input[1] = new ResourceAmount(Color.YELLOW, 0);
                        input[2] = new ResourceAmount(Color.GREY, 0);
                        input[3] = new ResourceAmount(Color.PURPLE, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.GREY, 1);
                        output[1] = new ResourceAmount(Color.PURPLE, 3);
                        output[2] = new ResourceAmount(Color.BLUE, 0);
                        output[3] = new ResourceAmount(Color.YELLOW, 0);
                        cards[2] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 47);
                        vp = 10;
                        faith = 1;
                        cost = new ResourceAmount[4];
                        cost[0] = new ResourceAmount(Color.GREY, 5);
                        cost[1] = new ResourceAmount(Color.PURPLE, 2);
                        cost[2] = new ResourceAmount(Color.YELLOW, 0);
                        cost[3] = new ResourceAmount(Color.BLUE, 0);
                        input = new ResourceAmount[4];
                        input[0] = new ResourceAmount(Color.GREY, 1);
                        input[1] = new ResourceAmount(Color.PURPLE, 1);
                        input[2] = new ResourceAmount(Color.BLUE, 0);
                        input[3] = new ResourceAmount(Color.YELLOW, 0);
                        output = new ResourceAmount[4];
                        output[0] = new ResourceAmount(Color.YELLOW, 2);
                        output[1] = new ResourceAmount(Color.BLUE, 2);
                        output[2] = new ResourceAmount(Color.GREY, 0);
                        output[3] = new ResourceAmount(Color.PURPLE, 0);
                        cards[3] = new DevelopCard(this.level, vp, faith, this.color, cost, input, output, 48);
                        break;
                }
                break;
        }
    }

    /**
     * Utility method used in tests. It returns the string representation of the current deck (the cards, the level, color
     * and top).
     *
     * @return
     */
    @Override
    public String toString() {
        return "DevelopDeck{" +
                "cards=" + Arrays.toString(cards) +
                ", level=" + level +
                ", color=" + color +
                ", top=" + top +
                '}';
    }
}
