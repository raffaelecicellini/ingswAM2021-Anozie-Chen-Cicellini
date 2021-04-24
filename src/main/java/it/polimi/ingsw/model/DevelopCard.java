package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.*;

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
     * This attribute is the id of the card: it is sent to the client as a representation of the card
     */
    private int id;

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
    public DevelopCard(int level, int victoryPoints, int faithOutput, Color color, ResourceAmount[] cost, ResourceAmount[] input, ResourceAmount[] output, int id) {
        this.level = level;
        this.victoryPoints = victoryPoints;
        this.faithOutput = faithOutput;
        this.color = color;
        this.cost = cost;
        this.input = input;
        this.output = output;
        this.id=id;
    }

    /**
     * This method returns the id representing the card
     * @return the id of the card
     */
    public int getId(){
        return this.id;
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
     * @return the color of the card
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
     * Simple method to set the cost requested by the card.
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

    protected int parseChoice(String chosen){
        switch (chosen.toLowerCase()){
            case "small": return 0;
            case "mid": return 1;
            case "big": return 2;
            case "sp1": return 3;
            case "sp2": return 4;
        }
        return -1;
    }

    /**
     * Method to activate a production on a single Develop Card
     * @param map: the map indicates which resource and from where you want to take it, to start a production
     * @param strongbox : indicates the reference to the strongbox
     * @param deposits : indicates the reference to the deposits
     * @param strongboxOutput : indicates the output strongbox, which contains the resources that have been produced
     */
    public int activateProduction(Map<String, String> map, ResourceAmount[] strongbox, List<ResourceAmount> deposits, ResourceAmount[] strongboxOutput) throws InvalidActionException{

        boolean exit;

        // re-writing input in another vector (newInput)
        ResourceAmount[] newInput = new ResourceAmount[2];
        int cont, j = 0;
        for (ResourceAmount resourceAmount : input) {
            cont = 0;
            while (cont != resourceAmount.getAmount()) {
                newInput[j] = new ResourceAmount(resourceAmount.getColor(), 1);
                j++;
                cont++;
            }
        }
        if (map.size() != j) throw new InvalidActionException("Invalid action! Check the number of resources!");

        // try to modify the temporary strongbox and deposits
        for (Map.Entry<String, String> m : map.entrySet()) {

            exit = false;

            String s = m.getKey().replaceAll("[^0-9]", "");
            if (s.equals("")) throw new InvalidActionException("Invalid action! Only [\"Res#\"] accepted!");
            int i;
            int k = Integer.parseInt(s) - 1;

            if (m.getValue().toLowerCase().equals("strongbox")) {
                i = 0;
                    while (i < strongbox.length && !exit) {
                        if (newInput[k].getColor() == strongbox[i].getColor()) {
                            if (strongbox[i].getAmount() > 0) {
                                strongbox[i].setAmount(strongbox[i].getAmount() - 1);
                                exit = true;
                            } else  throw new InvalidActionException("There's not enough " + newInput[k].getColor() + " resource in the Strongbox!");
                        }
                        i++;
                    }
            } else {
                i = parseChoice(m.getValue());
                if (i >= 0 && i <= 2) {
                    if (deposits.get(i) != null) {
                        if (newInput[k].getColor() == deposits.get(i).getColor()) {
                            if (deposits.get(i).getAmount() > 0) {
                                deposits.get(i).setAmount(deposits.get(i).getAmount() - 1);
                            } else
                                throw new InvalidActionException("There's not enough " + newInput[k].getColor() + " resource in that Deposit!");
                        } else
                            throw new InvalidActionException("There's no " + newInput[k].getColor() + " resource in that Deposit! Try choosing another one!");
                    } else throw new InvalidActionException("The chosen Deposit is empty!");
                } else if (i >= 3 && i <= 4) {
                    if (deposits.get(parseChoice(m.getValue())) != null) {
                        if (newInput[k].getColor() == deposits.get(i).getColor()) {
                            if (deposits.get(i).getAmount() > 0) {
                                deposits.get(i).setAmount(deposits.get(i).getAmount() - 1);
                            } else
                                throw new InvalidActionException("There's not enough " + newInput[k].getColor() + " resource in that Deposits!");
                        } else
                            throw new InvalidActionException("There's no " + newInput[k].getColor() + " resource in that Deposits! Try choosing another one!");
                    } else throw new InvalidActionException("That Deposit does not exist, yet!");
                } else
                    throw new InvalidActionException("Invalid action! Be sure you typed correctly!");
            }
        }


        // if there is no errors, put everything in the strongboxOutput
        for (int i = 0; i < output.length; i++) {
            int x = 0;
            while (output[x].getColor() != strongboxOutput[i].getColor()) { x++; }
            strongboxOutput[i].setAmount(strongboxOutput[i].getAmount() + output[x].getAmount());
        }
        return faithOutput;
    }


    /**
     * Method to buy the develop card
     * @param map: the map indicates which resource and from where you want to take it, to buy a Develop Card
     * @param strongbox : indicates the reference to the strongbox
     * @param deposits : indicates the reference to the deposits
     */
    public void buyDevelopCard(Map<String, String> map, ResourceAmount[] strongbox, List<ResourceAmount> deposits) throws InvalidActionException{

        boolean exit;

        // re-writing cost in another vector (newCost)
        ResourceAmount[] newCost = new ResourceAmount[8];
        int cont, j = 0;
        for (ResourceAmount resourceAmount : cost) {
            cont = 0;
            while (cont != resourceAmount.getAmount()) {
                newCost[j] = new ResourceAmount(resourceAmount.getColor(), 1);
                j++;
                cont++;
            }
        }
        if (map.size() != j) throw new InvalidActionException("Invalid action! Check the number of resources!");

        for (Map.Entry<String, String> m : map.entrySet()) {

            exit = false;

            String s = m.getKey().replaceAll("[^0-9]", "");
            if (s.equals("")) throw new InvalidActionException("Invalid action! Only [\"res#\"] accepted!");
            int i = parseChoice(m.getValue());
            int k = Integer.parseInt(s) - 1;

            if (m.getValue().toLowerCase().equals("strongbox")) {
                i = 0;
                while (i < strongbox.length && !exit) {
                    if (newCost[k].getColor() == strongbox[i].getColor()) {
                        if (strongbox[i].getAmount() > 0) {
                            strongbox[i].setAmount(strongbox[i].getAmount() - 1);
                            exit = true;
                        } else
                            throw new InvalidActionException("There's not enough " + newCost[k].getColor() + " resources in the Strongbox! [" + newCost[k].getAmount() + "]");
                    }
                    i++;
                }
            } else
            if (i >= 0 && i <= 2) {
                if (deposits.get(i) != null) {
                    if (newCost[k].getColor() == deposits.get(i).getColor()) {
                        if (deposits.get(i).getAmount() > 0) {
                            deposits.get(i).setAmount(deposits.get(i).getAmount() - 1);
                        } else throw new InvalidActionException("There's not enough " + newCost[k].getColor() + " resource in that Deposit!");
                    } else throw new InvalidActionException("There's no " + newCost[k].getColor() + " resource in that Deposit! Try choosing another one!");
                } else throw new InvalidActionException("The chosen Deposit is empty!");
            } else
            if (i >= 3 && i <= 4) {
                if (deposits.get(parseChoice(m.getValue())) != null) {
                    if (newCost[k].getColor() == deposits.get(i).getColor()) {
                        if (deposits.get(i).getAmount() > 0) {
                            deposits.get(i).setAmount(deposits.get(i).getAmount() - 1);
                        } else throw new InvalidActionException("There's not enough " + newCost[k].getColor() + " resource in that Deposits!");
                    } else throw new InvalidActionException("There's no " + newCost[k].getColor() + " resource in that Deposits! Try choosing another one!");
                } else throw new InvalidActionException("That Deposit does not exist, yet!");
            } else
                throw new InvalidActionException("Invalid action! Be sure you typed correctly!");
        }
    }


    /**
     * Utility method used in tests. It returns the String representation of the current card.
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
