package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

class DiscardTokenTest {
    /**
     * Action test. We are testing if the DiscardToken correctly discard the amount of cards specified by qty and of the
     * correct color. Also, we are testing if it correctly finds the deck of the minimum level of the specified color that
     * still has some cards.
     */
    @Test
    public void discardGreenTest() {
        DiscardToken test = new DiscardToken(Color.GREEN, 2);
        FaithMarker black = new FaithMarker(0);
        DevelopDeck[][] decks = new DevelopDeck[4][3];
        Color actual = Color.GREEN;
        int col = 0;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.BLUE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.YELLOW;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.PURPLE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }

    /**
     * Action test. We are testing if the DiscardToken correctly discard the amount of cards specified by qty and of the
     * correct color. Also, we are testing if it correctly finds the deck of the minimum level of the specified color that
     * still has some cards.
     */
    @Test
    public void discardBlueTest() {
        DiscardToken test = new DiscardToken(Color.BLUE, 2);
        FaithMarker black = new FaithMarker(0);
        DevelopDeck[][] decks = new DevelopDeck[4][3];
        Color actual = Color.GREEN;
        int col = 0;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.BLUE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.YELLOW;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.PURPLE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }

    /**
     * Action test. We are testing if the DiscardToken correctly discard the amount of cards specified by qty and of the
     * correct color. Also, we are testing if it correctly finds the deck of the minimum level of the specified color that
     * still has some cards.
     */
    @Test
    public void discardYellowTest() {
        DiscardToken test = new DiscardToken(Color.YELLOW, 2);
        FaithMarker black = new FaithMarker(0);
        DevelopDeck[][] decks = new DevelopDeck[4][3];
        Color actual = Color.GREEN;
        int col = 0;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.BLUE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.YELLOW;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.PURPLE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }

    /**
     * Action test. We are testing if the DiscardToken correctly discard the amount of cards specified by qty and of the
     * correct color. Also, we are testing if it correctly finds the deck of the minimum level of the specified color that
     * still has some cards.
     */
    @Test
    public void discardPurpleTest() {
        DiscardToken test = new DiscardToken(Color.PURPLE, 2);
        FaithMarker black = new FaithMarker(0);
        DevelopDeck[][] decks = new DevelopDeck[4][3];
        Color actual = Color.GREEN;
        int col = 0;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.BLUE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.YELLOW;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.PURPLE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }

    /**
     * Action test. We are testing if the DiscardToken correctly discard the amount of cards specified by qty and of the
     * correct color. We are testing if the token is able to discard all the cards left from a deck and other cards from
     * the deck of a higher level in order to discard the correct amount of cards. This happens where there are not enough
     * cards in the minimum level deck to be discarded, so it is necessary to discard the remain cards from a higher level
     * deck.
     */
    @Test
    public void discardOddCardTest() {
        DiscardToken test = new DiscardToken(Color.PURPLE, 2);
        FaithMarker black = new FaithMarker(0);
        DevelopDeck[][] decks = new DevelopDeck[4][3];
        Color actual = Color.GREEN;
        int col = 0;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.BLUE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.YELLOW;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.PURPLE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                d2.removeCard();
            }
        }
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d : decks) {
            for (DevelopDeck d2 : d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }
}
