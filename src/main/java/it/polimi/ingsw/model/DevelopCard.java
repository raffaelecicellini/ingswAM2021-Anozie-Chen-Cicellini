package it.polimi.ingsw.model;

import java.util.Arrays;

public class DevelopCard {
    private int level;
    private int victoryPoints;
    private int faithOutput;
    private Color color;
    private ResourceAmount[] cost;
    private ResourceAmount[] input;
    private ResourceAmount[] output;

    public DevelopCard(int level, int victoryPoints, int faithOutput, Color color, ResourceAmount[] cost, ResourceAmount[] input, ResourceAmount[] output) {
        this.level = level;
        this.victoryPoints = victoryPoints;
        this.faithOutput = faithOutput;
        this.color = color;
        this.cost = cost;
        this.input = input;
        this.output = output;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getFaithOutput() {
        return faithOutput;
    }

    public void setFaithOutput(int faithOutput) {
        this.faithOutput = faithOutput;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ResourceAmount[] getCost() {
        ResourceAmount[] res= new ResourceAmount[4];

        for(int i=0; i<4; i++) {
            res[i]= new ResourceAmount(this.cost[i].getColor(), this.cost[i].getAmount());
        }
        return res;
    }

    public void setCost(ResourceAmount[] cost) {
        for (int i=0; i<4; i++) {
            Color res= cost[i].getColor();
            int amount= cost[i].getAmount();
            this.cost[i].setColor(res);
            this.cost[i].setAmount(amount);
        }
    }

    public ResourceAmount[] getInput() {
        ResourceAmount[] res= new ResourceAmount[4];

        for(int i=0; i<4; i++) {
            res[i]= new ResourceAmount(this.input[i].getColor(), this.input[i].getAmount());
        }
        return res;
    }

    public void setInput(ResourceAmount[] input) {
        for (int i=0; i<4; i++) {
            Color res= input[i].getColor();
            int amount= input[i].getAmount();
            this.input[i].setColor(res);
            this.input[i].setAmount(amount);
        }
    }

    public ResourceAmount[] getOutput() {
        ResourceAmount[] res= new ResourceAmount[4];

        for(int i=0; i<4; i++) {
            res[i]= new ResourceAmount(this.output[i].getColor(), this.output[i].getAmount());
        }
        return res;
    }

    public void setOutput(ResourceAmount[] output) {
        for (int i=0; i<4; i++) {
            Color res= output[i].getColor();
            int amount= output[i].getAmount();
            this.output[i].setColor(res);
            this.output[i].setAmount(amount);
        }
    }

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
