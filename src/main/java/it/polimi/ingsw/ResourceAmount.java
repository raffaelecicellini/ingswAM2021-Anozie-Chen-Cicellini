package it.polimi.ingsw;

public class ResourceAmount {
    private Color color;
    private int amount;

    public ResourceAmount(Color color, int amount) {
        this.color = color;
        this.amount = amount;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
