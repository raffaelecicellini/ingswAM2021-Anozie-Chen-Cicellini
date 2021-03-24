package it.polimi.ingsw.model;

public class DiscardToken implements ActionToken{
    private Color color;
    private int qty;

    public DiscardToken(Color color, int qty) {
        this.color=color;
        this.qty=qty;
    }
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

    @Override
    public String toString() {
        return "DiscardToken{" +
                "color=" + color +
                ", qty=" + qty +
                '}';
    }
}
