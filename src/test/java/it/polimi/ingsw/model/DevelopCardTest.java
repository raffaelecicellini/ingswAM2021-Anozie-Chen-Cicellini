package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DevelopCardTest {
    /**
     * Level test. We are testing if the get and set level methods work.
     */
    @Test
    public void levelTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        assertEquals(1, test.getLevel());
        test.setLevel(2);
        assertEquals(2, test.getLevel());

    }

    /**
     * Victory points test. We are testing if the get and set victory points methods work.
     */
    @Test
    public void victoryPointsTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        assertEquals(1, test.getVictoryPoints());
        test.setVictoryPoints(3);
        assertEquals(3, test.getVictoryPoints());
    }

    /**
     * Faith output test. We are testing if get and set faith output methods work.
     */
    @Test
    public void faithOutputTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        assertEquals(1, test.getFaithOutput());
        test.setFaithOutput(3);
        assertEquals(3, test.getFaithOutput());
    }

    /**
     * Color test. We are testing if the get and set color methods work.
     */
    @Test
    public void colorTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        assertEquals(Color.BLUE, test.getColor());
        test.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, test.getColor());
    }

    /**
     * Cost test. We are testing if the get and set cost method work.
     */
    @Test
    public void costTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        int i=0;
        ResourceAmount[] cardCost= test.getCost();
        for (ResourceAmount res: cardCost) {
            assertEquals(cost[i].getAmount(), res.getAmount());
            assertEquals(cost[i].getColor(), res.getColor());
            i++;
        }
        cost[1].setAmount(3);
        test.setCost(cost);
        cardCost= test.getCost();
        i=0;
        for (ResourceAmount res: cardCost) {
            assertEquals(cost[i].getAmount(), res.getAmount());
            assertEquals(cost[i].getColor(), res.getColor());
            i++;
        }
    }

    /**
     * Input test. We are testing if the get and set input methods work.
     */
    @Test
    public void inputTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        int i=0;
        ResourceAmount[] cardInput= test.getInput();
        for (ResourceAmount res: cardInput) {
            assertEquals(input[i].getAmount(), res.getAmount());
            assertEquals(input[i].getColor(), res.getColor());
            i++;
        }
        input[1].setAmount(3);
        test.setInput(input);
        cardInput= test.getInput();
        i=0;
        for (ResourceAmount res: cardInput) {
            assertEquals(input[i].getAmount(), res.getAmount());
            assertEquals(input[i].getColor(), res.getColor());
            i++;
        }
    }

    /**
     * Output test. We are testing if the get and set output method work.
     */
    @Test
    public void outputTest() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,0);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREEN, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);
        int i=0;
        ResourceAmount[] cardOutput= test.getOutput();
        for (ResourceAmount res: cardOutput) {
            assertEquals(output[i].getAmount(), res.getAmount());
            assertEquals(output[i].getColor(), res.getColor());
            i++;
        }
        output[1].setAmount(3);
        test.setOutput(output);
        cardOutput= test.getOutput();
        i=0;
        for (ResourceAmount res: cardOutput) {
            assertEquals(output[i].getAmount(), res.getAmount());
            assertEquals(output[i].getColor(), res.getColor());
            i++;
        }
    }


    // Testing the activateProduction method
    /**
     * 2 different resources from strongbox
     */
    @Test
    public void activateProductionTest1() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW,1);
        input[1]= new ResourceAmount(Color.PURPLE, 1);
        input[2]= new ResourceAmount(Color.BLUE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,1);
        output[1]= new ResourceAmount(Color.BLUE, 1);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test = new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[2];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 6);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 4);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Strongbox");
        map.put("Res2", "Strongbox");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(5 ,strongbox[0].getAmount());
        assertEquals(3, strongbox[1].getAmount());
        assertEquals(1, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(1, strongboxOutput[3].getAmount());


    }

    /**
     * 2 same resources from strongbox
     */
    @Test
    public void activateProductionTest2() {
        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW, 2);
        input[1]= new ResourceAmount(Color.PURPLE, 0);
        input[2]= new ResourceAmount(Color.BLUE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 5);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 5);
        strongbox[2] = new ResourceAmount(Color.GREY, 5);
        strongbox[3] = new ResourceAmount(Color.BLUE, 5);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Strongbox");
        map.put("Res2", "Strongbox");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(3, strongbox[0].getAmount());
        assertEquals(5, strongbox[1].getAmount());
        assertEquals(5, strongbox[2].getAmount());
        assertEquals(5, strongbox[3].getAmount());
        assertEquals(0, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(0, strongboxOutput[3].getAmount());
    }

    /**
     * 2 different resources from deposits
     */
    @Test
    public void activateProductionTest3() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW, 1);
        input[1]= new ResourceAmount(Color.PURPLE, 1);
        input[2]= new ResourceAmount(Color.BLUE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,9);
        output[1]= new ResourceAmount(Color.BLUE, 9);
        output[2]= new ResourceAmount(Color.PURPLE, 9);
        output[3]= new ResourceAmount(Color.GREY, 9);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.YELLOW, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.PURPLE, 3));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Deposits");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(0, deposits.get(0).getAmount());
        assertEquals(2, deposits.get(1).getAmount());
        assertEquals(2, deposits.get(2).getAmount());
        assertEquals(9, strongboxOutput[0].getAmount());
        assertEquals(9, strongboxOutput[1].getAmount());
        assertEquals(9, strongboxOutput[2].getAmount());
        assertEquals(9, strongboxOutput[3].getAmount());
    }

    /**
     * 2 same resources from strongbox
     */
    @Test
    public void activateProductionTest4() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.GREY, 2);
        input[1]= new ResourceAmount(Color.PURPLE, 0);
        input[2]= new ResourceAmount(Color.BLUE, 0);
        input[3]= new ResourceAmount(Color.YELLOW, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,5);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.YELLOW, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.PURPLE, 3));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Deposits");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(1, deposits.get(0).getAmount());
        assertEquals(0, deposits.get(1).getAmount());
        assertEquals(3, deposits.get(2).getAmount());
        assertEquals(5, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(0, strongboxOutput[3].getAmount());

    }

    /**
     * resources from both Strongbox and Deposits
     */
    @Test
    public void activateProductionTest5() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.GREY, 2);
        input[1]= new ResourceAmount(Color.PURPLE, 0);
        input[2]= new ResourceAmount(Color.BLUE, 0);
        input[3]= new ResourceAmount(Color.YELLOW, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,5);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 6);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.YELLOW, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.PURPLE, 3));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Strongbox");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(1, deposits.get(0).getAmount());
        assertEquals(1, deposits.get(1).getAmount());
        assertEquals(3, deposits.get(2).getAmount());
        assertEquals(0, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(5, strongbox[2].getAmount());
        assertEquals(0, strongbox[3].getAmount());
        assertEquals(5, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(0, strongboxOutput[3].getAmount());

    }

    /**
     * trying activateProduction exceptions
     */
    @Test
    public void activateProductionTest6() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.BLUE, 1);
        input[1]= new ResourceAmount(Color.YELLOW, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.PURPLE, 3));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Deposits");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        assertEquals(0, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(0, strongboxOutput[3].getAmount());
    }


    // Testing buyDevelopCard method

    /**
     * buying Develop Card with resources from Strongbox
     */
    @Test
    public void buyDevelopCardTest1() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW,3);
        cost[1]= new ResourceAmount(Color.BLUE, 3);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.BLUE, 1);
        input[1]= new ResourceAmount(Color.YELLOW, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 3);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 3);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.PURPLE, 3));

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Strongbox");
        map.put("Res2", "Strongbox");
        map.put("Res3", "Strongbox");
        map.put("Res4", "Strongbox");
        map.put("Res5", "Strongbox");
        map.put("Res6", "Strongbox");

        try {
            test.buyDevelopCard(map, strongbox, deposits);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(0 ,strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0 ,strongbox[2].getAmount());
        assertEquals(0, strongbox[3].getAmount());
    }

    /**
     * buying Develop Card with resources from Deposits
     */
    @Test
    public void buyDevelopCardTest2() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW, 2);
        cost[1]= new ResourceAmount(Color.BLUE, 1);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.BLUE, 1);
        input[1]= new ResourceAmount(Color.YELLOW, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 3);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 3);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.YELLOW, 3));

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Deposits");
        map.put("Res3", "Deposits");

        try {
            test.buyDevelopCard(map, strongbox, deposits);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(0, deposits.get(0).getAmount());
        assertEquals(2, deposits.get(1).getAmount());
        assertEquals(1, deposits.get(2).getAmount());
        assertEquals(3, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0, strongbox[2].getAmount());
        assertEquals(3, strongbox[3].getAmount());
    }

    /**
     * buying Develop Card with resources from both Strongbox and Deposits
     */
    @Test
    public void buyDevelopCardTest3() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW, 6);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.BLUE, 1);
        input[1]= new ResourceAmount(Color.YELLOW, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 3);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 3);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.YELLOW, 3));

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Deposits");
        map.put("Res3", "Deposits");
        map.put("Res4", "Strongbox");
        map.put("Res5", "Strongbox");
        map.put("Res6", "Strongbox");

        try {
            test.buyDevelopCard(map, strongbox, deposits);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(1, deposits.get(0).getAmount());
        assertEquals(2, deposits.get(1).getAmount());
        assertEquals(0, deposits.get(2).getAmount());
        assertEquals(0, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0, strongbox[2].getAmount());
        assertEquals(3, strongbox[3].getAmount());
    }

    /**
     * trying buyDevelopCard exceptions
     */
    @Test
    public void buyDevelopCardTest4() {

        int level=1;
        int victoryPoints=1;
        int faithOutput=1;
        Color color=Color.BLUE;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW, 4);
        cost[1]= new ResourceAmount(Color.BLUE, 4);
        cost[2]= new ResourceAmount(Color.PURPLE, 0);
        cost[3]= new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.BLUE, 1);
        input[1]= new ResourceAmount(Color.YELLOW, 1);
        input[2]= new ResourceAmount(Color.PURPLE, 0);
        input[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW,0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.PURPLE, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);

        DevelopCard test= new DevelopCard(level, victoryPoints, faithOutput, color, cost, input, output);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 1);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 2);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.YELLOW, 3));

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Deposits");
        map.put("Res2", "Deposits");
        map.put("Res3", "Deposits");
        map.put("Res4", "Strongbox");
        map.put("Res5", "Strongbox");
        map.put("Res6", "Strongbox");
        map.put("Res7", "Deposits");
        map.put("Res8", "Strongbox");

        try {
            test.buyDevelopCard(map, strongbox, deposits);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        /*assertEquals(1, deposits.get(0).getAmount());
        assertEquals(2, deposits.get(1).getAmount());
        assertEquals(0, deposits.get(2).getAmount());
        assertEquals(0, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0, strongbox[2].getAmount());
        assertEquals(3, strongbox[3].getAmount());*/
    }
}