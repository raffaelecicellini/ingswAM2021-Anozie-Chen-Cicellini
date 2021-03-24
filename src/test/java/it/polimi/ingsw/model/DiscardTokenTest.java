package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

class DiscardTokenTest {
    @Test
    public void discardGreenTest() {
        DiscardToken test= new DiscardToken(Color.GREEN, 2);
        FaithMarker black= new FaithMarker(0);
        DevelopDeck[][] decks= new DevelopDeck[4][3];
        Color actual= Color.GREEN;
        int col=0;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.BLUE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual= Color.YELLOW;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.PURPLE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }
    @Test
    public void discardBlueTest() {
        DiscardToken test= new DiscardToken(Color.BLUE, 2);
        FaithMarker black= new FaithMarker(0);
        DevelopDeck[][] decks= new DevelopDeck[4][3];
        Color actual= Color.GREEN;
        int col=0;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.BLUE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual= Color.YELLOW;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.PURPLE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }
    @Test
    public void discardYellowTest() {
        DiscardToken test= new DiscardToken(Color.YELLOW, 2);
        FaithMarker black= new FaithMarker(0);
        DevelopDeck[][] decks= new DevelopDeck[4][3];
        Color actual= Color.GREEN;
        int col=0;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.BLUE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual= Color.YELLOW;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.PURPLE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }
    @Test
    public void discardPurpleTest() {
        DiscardToken test= new DiscardToken(Color.PURPLE, 2);
        FaithMarker black= new FaithMarker(0);
        DevelopDeck[][] decks= new DevelopDeck[4][3];
        Color actual= Color.GREEN;
        int col=0;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.BLUE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual= Color.YELLOW;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.PURPLE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }
    @Test
    public void discardOddCardTest() {
        DiscardToken test= new DiscardToken(Color.PURPLE, 2);
        FaithMarker black= new FaithMarker(0);
        DevelopDeck[][] decks= new DevelopDeck[4][3];
        Color actual= Color.GREEN;
        int col=0;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.BLUE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual= Color.YELLOW;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.PURPLE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                d2.removeCard();
            }
        }
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
        test.doAction(black, decks);
        for (DevelopDeck[] d:decks) {
            for (DevelopDeck d2: d) {
                System.out.println(d2);
                System.out.println(d2.getTop());
            }
        }
    }
}
