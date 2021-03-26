package it.polimi.ingsw.model;

/**
 * This class represents the amount of a certain color of Resource.
 * It is used different by different Classes throughout the game.
 */
public class ResourceAmount {
    private Color color;
    private int amount;

    /**
     * Constructor ResourceAmount creates a new ResourceAmount instance.
     * @param color is the type of resource.
     * @param amount is the resource's quantity.
     */
    public ResourceAmount(Color color, int amount) {
        this.color = color;
        this.amount = amount;
    }

    /**
     * This method returns the color of the resource.
     * @return the color of the resource.
     */
    public Color getColor() {
        return color;
    }

    /**
     * This method sets the color of the resource.
     * @param color is the new type of the resource.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * This method return the amount of resources.
     * @return the amount of resource.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * This method sets the amount of resources.
     * @param amount is the new quantity.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * This method converts the class to a String.
     * @return a String which represents the class.
     */
    @Override
    public String toString() {
        return "ResourceAmount{" +
                "color=" + color +
                ", amount=" + amount +
                '}';
    }
}
