package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderDevelopCardTest {

    /**
     * resource from strongbox
     */
    @Test
    public void activateProductionTest1() {
        int level = 0;
        int victoryPoints = 0;
        int faithOutput = 1;
        Color color = Color.BLUE;
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.YELLOW, 0);
        cost[1] = new ResourceAmount(Color.BLUE, 0);
        cost[2] = new ResourceAmount(Color.PURPLE, 0);
        cost[3] = new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input = new ResourceAmount[4];
        input[0] = new ResourceAmount(Color.BLUE, 1);
        input[1] = new ResourceAmount(Color.YELLOW, 0);
        input[2] = new ResourceAmount(Color.PURPLE, 0);
        input[3] = new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output = new ResourceAmount[4];
        output[0] = new ResourceAmount(Color.YELLOW, 0);
        output[1] = new ResourceAmount(Color.BLUE, 0);
        output[2] = new ResourceAmount(Color.PURPLE, 0);
        output[3] = new ResourceAmount(Color.GREY, 0);

        DevelopCard test = new LeaderDevelopCard(level, victoryPoints, faithOutput, color, cost, input, output, 0);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 3);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 3);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.YELLOW, 3));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "Strongbox");
        map.put("Resout", "BLUE");
        int i = 0;
        try {
            i = test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(2, strongbox[3].getAmount());
        assertEquals(1, strongboxOutput[3].getAmount());
        assertEquals(1, i);
    }

    /**
     * resource from deposits
     */
    @Test
    public void activateProductionTest2() {
        int level = 0;
        int victoryPoints = 0;
        int faithOutput = 1;
        Color color = Color.BLUE;
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.YELLOW, 0);
        cost[1] = new ResourceAmount(Color.BLUE, 0);
        cost[2] = new ResourceAmount(Color.PURPLE, 0);
        cost[3] = new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input = new ResourceAmount[4];
        input[0] = new ResourceAmount(Color.BLUE, 1);
        input[1] = new ResourceAmount(Color.YELLOW, 0);
        input[2] = new ResourceAmount(Color.PURPLE, 0);
        input[3] = new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output = new ResourceAmount[4];
        output[0] = new ResourceAmount(Color.YELLOW, 0);
        output[1] = new ResourceAmount(Color.BLUE, 0);
        output[2] = new ResourceAmount(Color.PURPLE, 0);
        output[3] = new ResourceAmount(Color.GREY, 0);

        DevelopCard test = new LeaderDevelopCard(level, victoryPoints, faithOutput, color, cost, input, output, 0);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 1));
        deposits.add(new ResourceAmount(Color.GREY, 2));
        deposits.add(new ResourceAmount(Color.YELLOW, 3));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "SMall");
        map.put("Resout", "BLUE");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(0, deposits.get(0).getAmount());
        assertEquals(2, deposits.get(1).getAmount());
        assertEquals(3, deposits.get(2).getAmount());
        assertEquals(0, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0, strongbox[2].getAmount());
        assertEquals(0, strongbox[3].getAmount());
        assertEquals(0, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(1, strongboxOutput[3].getAmount());

    }

    /**
     * resource not available
     */
    @Test
    public void activateProductionTest3() {
        int level = 0;
        int victoryPoints = 0;
        int faithOutput = 1;
        Color color = Color.BLUE;
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.YELLOW, 0);
        cost[1] = new ResourceAmount(Color.BLUE, 0);
        cost[2] = new ResourceAmount(Color.PURPLE, 0);
        cost[3] = new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input = new ResourceAmount[4];
        input[0] = new ResourceAmount(Color.BLUE, 1);
        input[1] = new ResourceAmount(Color.YELLOW, 0);
        input[2] = new ResourceAmount(Color.PURPLE, 0);
        input[3] = new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output = new ResourceAmount[4];
        output[0] = new ResourceAmount(Color.YELLOW, 0);
        output[1] = new ResourceAmount(Color.BLUE, 0);
        output[2] = new ResourceAmount(Color.PURPLE, 0);
        output[3] = new ResourceAmount(Color.GREY, 0);

        LeaderDevelopCard test = new LeaderDevelopCard(level, victoryPoints, faithOutput, color, cost, input, output, 0);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 0));
        deposits.add(new ResourceAmount(Color.GREY, 0));
        deposits.add(new ResourceAmount(Color.YELLOW, 0));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        map.put("Res1", "small");
        //map.put("Res1", "Strongbox");
        map.put("Resout", "BLUE");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(0, deposits.get(0).getAmount());
        assertEquals(0, deposits.get(1).getAmount());
        assertEquals(0, deposits.get(2).getAmount());
        assertEquals(0, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0, strongbox[2].getAmount());
        assertEquals(0, strongbox[3].getAmount());
        assertEquals(0, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(0, strongboxOutput[3].getAmount());

    }

    /**
     * wrong inputs
     */
    @Test
    public void activateProductionTest4() {
        int level = 0;
        int victoryPoints = 0;
        int faithOutput = 1;
        Color color = Color.BLUE;
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.YELLOW, 0);
        cost[1] = new ResourceAmount(Color.BLUE, 0);
        cost[2] = new ResourceAmount(Color.PURPLE, 0);
        cost[3] = new ResourceAmount(Color.GREEN, 0);
        ResourceAmount[] input = new ResourceAmount[4];
        input[0] = new ResourceAmount(Color.BLUE, 1);
        input[1] = new ResourceAmount(Color.YELLOW, 0);
        input[2] = new ResourceAmount(Color.PURPLE, 0);
        input[3] = new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] output = new ResourceAmount[4];
        output[0] = new ResourceAmount(Color.YELLOW, 0);
        output[1] = new ResourceAmount(Color.BLUE, 0);
        output[2] = new ResourceAmount(Color.PURPLE, 0);
        output[3] = new ResourceAmount(Color.GREY, 0);

        LeaderDevelopCard test = new LeaderDevelopCard(level, victoryPoints, faithOutput, color, cost, input, output, 0);

        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.YELLOW, 0);
        strongbox[1] = new ResourceAmount(Color.PURPLE, 0);
        strongbox[2] = new ResourceAmount(Color.GREY, 0);
        strongbox[3] = new ResourceAmount(Color.BLUE, 0);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE, 0));
        deposits.add(new ResourceAmount(Color.GREY, 0));
        deposits.add(new ResourceAmount(Color.YELLOW, 0));

        ResourceAmount[] strongboxOutput = new ResourceAmount[4];
        strongboxOutput[0] = new ResourceAmount(Color.YELLOW, 0);
        strongboxOutput[1] = new ResourceAmount(Color.PURPLE, 0);
        strongboxOutput[2] = new ResourceAmount(Color.GREY, 0);
        strongboxOutput[3] = new ResourceAmount(Color.BLUE, 0);

        Map<String, String> map = new HashMap<>();
        //map.put("Res", "Deposits");
        //map.put("Res1", "Strongbo");
        //map.put("Res1", "Strongbox");
        //map.put("Resou", "BLUE");

        try {
            test.activateProduction(map, strongbox, deposits, strongboxOutput);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(0, deposits.get(0).getAmount());
        assertEquals(0, deposits.get(1).getAmount());
        assertEquals(0, deposits.get(2).getAmount());
        assertEquals(0, strongbox[0].getAmount());
        assertEquals(0, strongbox[1].getAmount());
        assertEquals(0, strongbox[2].getAmount());
        assertEquals(0, strongbox[3].getAmount());
        assertEquals(0, strongboxOutput[0].getAmount());
        assertEquals(0, strongboxOutput[1].getAmount());
        assertEquals(0, strongboxOutput[2].getAmount());
        assertEquals(0, strongboxOutput[3].getAmount());

    }
}
