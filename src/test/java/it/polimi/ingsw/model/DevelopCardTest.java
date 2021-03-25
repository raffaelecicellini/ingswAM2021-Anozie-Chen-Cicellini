package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

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
}