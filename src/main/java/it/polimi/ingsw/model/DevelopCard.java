package it.polimi.ingsw.model;

import java.util.Arrays;

/**
 * This class represents a DevelopCard that can be bought by a player or discarded in the Solo game.
 */
public class DevelopCard {
    /**
     * This attribute specifies the level of the current card.
     */
    private int level;
    /**
     * This attribute specifies the victory points given by the current card.
     */
    private int victoryPoints;
    /**
     * This attribute specifies the faith output of the production of the current card (>=0).
     */
    private int faithOutput;
    /**
     * This attribute represents the color of the current card.
     */
    private Color color;
    /**
     * This attribute represents the cost of the current card: for each type of resource, it specifies th amount requested
     * in order to buy the card.
     */
    private ResourceAmount[] cost;
    /**
     * This attribute represents the resources requested to activate the production specified by the current card.
     */
    private ResourceAmount[] input;
    /**
     * This attribute specifies the resources produced by the current card when the production is done correctly.
     */
    private ResourceAmount[] output;

    /**
     * It instantiates a single DevelopCard.
     * @param level: it specifies the level of the card.
     * @param victoryPoints: it specifies the victory points given by the card.
     * @param faithOutput: it specifies the faith output of the card.
     * @param color: it specifies the color of the card.
     * @param cost: it specifies the cost of the card (in order to buy it).
     * @param input: it specifies the input requested to activate the production of the card.
     * @param output: it specifies the output produced by the card.
     */
    public DevelopCard(int level, int victoryPoints, int faithOutput, Color color, ResourceAmount[] cost, ResourceAmount[] input, ResourceAmount[] output) {
        this.level = level;
        this.victoryPoints = victoryPoints;
        this.faithOutput = faithOutput;
        this.color = color;
        this.cost = cost;
        this.input = input;
        this.output = output;
    }

    /**
     * Simple method to return the level of the current card.
     * @return: the level of the card.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Simple method to set the level of the card.
     * @param level: the new value of the level attribute.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Simple method to get the victory points given by the card.
     * @return: the value of the victoryPoints attribute.
     */
    public int getVictoryPoints() {
        return victoryPoints;
    }

    /**
     * Simple method to set the victory points of the card.
     * @param victoryPoints: the new value of the victoryPoints attribute.
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    /**
     * Simple method to get the faith output of the card.
     * @return: the value of the faithOutput attribute.
     */
    public int getFaithOutput() {
        return faithOutput;
    }

    /**
     * Simple method to set the faith output of the card.
     * @param faithOutput: the new value of the faithOutput attribute.
     */
    public void setFaithOutput(int faithOutput) {
        this.faithOutput = faithOutput;
    }

    /**
     * Simple method to get the color of the card.: the value of the color attribute.
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * Simple method to set the color of the card.
     * @param color: the new value of the color attribute.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Simple method to retrieve the cost requested to buy the card.
     * @return: a copy of the cost array.
     */
    public ResourceAmount[] getCost() {
        ResourceAmount[] res= new ResourceAmount[4];
        for(int i=0; i<4; i++) {
            res[i]= new ResourceAmount(this.cost[i].getColor(), this.cost[i].getAmount());
        }
        return res;
    }

    /**
     * Simple method to set the cost rquested by the card.
     * @param cost: the new array of cost.
     */
    public void setCost(ResourceAmount[] cost) {
        for (int i=0; i<4; i++) {
            Color res= cost[i].getColor();
            int amount= cost[i].getAmount();
            this.cost[i].setColor(res);
            this.cost[i].setAmount(amount);
        }
    }

    /**
     * Simple method to get the input requested by the card to activate production.
     * @return: a copy of the input array.
     */
    public ResourceAmount[] getInput() {
        ResourceAmount[] res= new ResourceAmount[4];

        for(int i=0; i<4; i++) {
            res[i]= new ResourceAmount(this.input[i].getColor(), this.input[i].getAmount());
        }
        return res;
    }

    /**
     * Simple method to set the input requested to activate production.
     * @param input: the new array of input.
     */
    public void setInput(ResourceAmount[] input) {
        for (int i=0; i<4; i++) {
            Color res= input[i].getColor();
            int amount= input[i].getAmount();
            this.input[i].setColor(res);
            this.input[i].setAmount(amount);
        }
    }

    /**
     * Simple method to get the output produced by the card.
     * @return: a copy of the output array.
     */
    public ResourceAmount[] getOutput() {
        ResourceAmount[] res= new ResourceAmount[4];

        for(int i=0; i<4; i++) {
            res[i]= new ResourceAmount(this.output[i].getColor(), this.output[i].getAmount());
        }
        return res;
    }

    /**
     * Simple method to set the output of the card.
     * @param output: the new array of output.
     */
    public void setOutput(ResourceAmount[] output) {
        for (int i=0; i<4; i++) {
            Color res= output[i].getColor();
            int amount= output[i].getAmount();
            this.output[i].setColor(res);
            this.output[i].setAmount(amount);
        }
    }

    /**
     * Utility method used in tests. It returns the String representation of the current card.
     * @return
     */
    @Override
    public String toString() {
        return "DevelopCard{" +
                "level=" + level +
                ", victoryPoints=" + victoryPoints +
                ", faithOutput=" + faithOutput +
                ", color=" + color +
                ", cost=" + Arrays.toString(cost) +
                ", input=" + Arrays.toString(input) +
                ", output=" + Arrays.toString(output) +
                '}';
    }
}
