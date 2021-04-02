package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.List;
import java.util.Map;

public class LeaderDevelopCard extends DevelopCard{

    /**
     * It instantiates a new DevelopCard, after the activation of a LevTwoLeader effect.
     *
     * @param level         : it specifies the level of the card.
     * @param victoryPoints : it specifies the victory points given by the card.
     * @param faithOutput   : it specifies the faith output of the card.
     * @param color         : it specifies the color of the card.
     * @param cost          : it specifies the cost of the card (in order to buy it).
     * @param input         : it specifies the input requested to activate the production of the card.
     * @param output        : it specifies the output produced by the card.
     */
    public LeaderDevelopCard(int level, int victoryPoints, int faithOutput, Color color, ResourceAmount[] cost, ResourceAmount[] input, ResourceAmount[] output) {
        super(level, victoryPoints, faithOutput, color, cost, input, output);
    }


    /**
     * Method to activate a production on a Leader Develop Card
     * @param map             : the map indicates which resource and from where you want to take it, to start a production
     * @param strongbox       : indicates the reference to the strongbox
     * @param deposits        : indicates the reference to the deposits
     * @param strongboxOutput : indicates the output strongbox, which contains the resource that has been produced
     */
    @Override
    public void activateProduction(Map<String, String> map, ResourceAmount[] strongbox, List<ResourceAmount> deposits, ResourceAmount[] strongboxOutput) throws InvalidActionException {


        for (Map.Entry<String, String> m : map.entrySet()) {

            boolean exit = false;
            int i = parseChoice(m.getValue());

                if (m.getValue().equals("Strongbox")) {
                    i = 0;
                while (i < strongbox.length && !exit) {
                    if (getInput()[0].getColor() == strongbox[i].getColor()) {
                        if (strongbox[i].getAmount() > 0) {
                            strongbox[i].setAmount(strongbox[i].getAmount() - 1);
                            exit = true;
                        } else
                            throw new InvalidActionException("There's not enough " + getInput()[0].getColor() + " resource in the Strongbox! [" + strongbox[i].getAmount() + "]");
                    }
                    i++;
                }
            } else
                if (i >= 0 && i <= 2) {
                        if (deposits.get(i) != null) {
                            if (getInput()[0].getColor() == deposits.get(i).getColor()) {
                                if (deposits.get(i).getAmount() > 0) {
                                    deposits.get(i).setAmount(deposits.get(i).getAmount() - 1);
                                } else
                                    throw new InvalidActionException("There's not enough " + getInput()[0].getColor() + " resource in that Deposit!");
                            } else throw new InvalidActionException("There's no " + getInput()[0].getColor() + " resource in that Deposit! Try choosing another one!");
                        } else throw new InvalidActionException("The chosen Deposit is empty!");
                    } else
                        if (i >= 3 && i <= 4) {
                            if (deposits.get(parseChoice(m.getValue())) != null) {
                                if (getInput()[0].getColor() == deposits.get(i).getColor()) {
                                    if (deposits.get(i).getAmount() > 0) {
                                        deposits.get(i).setAmount(deposits.get(i).getAmount() - 1);
                                    } else throw new InvalidActionException("There's not enough " + getInput()[0].getColor() + " resource in that Deposits!");
                                } else throw new InvalidActionException("There's no " + getInput()[0].getColor() + " resource in that Deposits! Try choosing another one!");
                            } else throw new InvalidActionException("That Deposit does not exist, yet!");
                        } else
                            if (m.getKey().equals("Resout")) {
                                i = 0;
                                while (i < strongboxOutput.length && !exit) {
                                    if (!strongboxOutput[i].getColor().toString().equals(m.getValue())) i++;
                                    else exit = true;
                                }
                                strongboxOutput[i].setAmount(strongboxOutput[i].getAmount() + 1);
                    } else throw new InvalidActionException("Invalid action! ( suggestion: ([\"Res1\"], [\"Strongbox\"]/[\"Deposits\"]), ([\"Resout\"], [\"COLOR\"]) )");
                }
    }
}
