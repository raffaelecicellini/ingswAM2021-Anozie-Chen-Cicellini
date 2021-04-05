package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void leaderTest(){
        Player test= new Player("test");
        assertEquals("test", test.getName());
        test.leaders.add(0, new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY));
        test.leaders.add(1, new LevTwoLeader(4,"LevTwo",false,false,Color.YELLOW,Color.BLUE));

        try {
            test.discardLeader(0);
            assertTrue(test.leaders.get(0).isDiscarded());
            assertEquals(1, test.getPersonalBoard().getFaithMarker().getPosition());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.activateLeader(0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertFalse(test.leaders.get(0).isActive());
        }
        try {
            test.discardLeader(2);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        PersonalBoard pb= test.getPersonalBoard();
        int vp= 1;
        int faith=1;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.PURPLE, 2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.YELLOW, 0);
        cost[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[] input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.GREY, 1);
        input[1]= new ResourceAmount(Color.BLUE, 0);
        input[2]= new ResourceAmount(Color.YELLOW, 0);
        input[3]= new ResourceAmount(Color.PURPLE, 0);
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.GREY, 0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.YELLOW, 0);
        output[3]= new ResourceAmount(Color.PURPLE, 0);
        DevelopCard card1= new DevelopCard(1, vp, faith, Color.PURPLE, cost, input, output);
        pb.addCard(0, card1, false);

        vp= 6;
        faith=0;
        cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.GREY, 3);
        cost[1]= new ResourceAmount(Color.BLUE, 2);
        cost[2]= new ResourceAmount(Color.YELLOW, 0);
        cost[3]= new ResourceAmount(Color.PURPLE, 0);
        input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.GREY, 1);
        input[1]= new ResourceAmount(Color.BLUE, 1);
        input[2]= new ResourceAmount(Color.YELLOW, 0);
        input[3]= new ResourceAmount(Color.PURPLE, 0);
        output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.YELLOW, 3);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.GREY, 0);
        output[3]= new ResourceAmount(Color.PURPLE, 0);
        DevelopCard card2= new DevelopCard(2, vp, faith, Color.YELLOW, cost, input, output);
        pb.addCard(0, card2, false);

        try {
            test.activateLeader(1);
            assertTrue(test.leaders.get(1).isActive());
            assertNotNull(pb.getSlot(3));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.discardLeader(1);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertFalse(test.leaders.get(1).isDiscarded());
            assertEquals(1, test.getPersonalBoard().getFaithMarker().getPosition());
        }
        try {
            test.activateLeader(4);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        test.leaders.get(0).setDiscarded(false);
        try {
            test.activateLeader(0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertFalse(test.leaders.get(0).isActive());
        }
        ResourceAmount[] strongbox= {new ResourceAmount(Color.YELLOW, 5), new ResourceAmount(Color.BLUE, 0), new ResourceAmount(Color.GREY, 0), new ResourceAmount(Color.PURPLE, 0)};
        test.getPersonalBoard().setStrongbox(strongbox);
        try {
            test.activateLeader(0);
            assertTrue(test.leaders.get(0).isActive());
            assertNotNull(test.getPersonalBoard().getDeposits().get(3));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void baseProductionTest(){
        Player test= new Player("test");
        Map<String, String> info= new HashMap<>();
        info.put("prod0", "yes");
        info.put("in01", "blue");
        info.put("pos01", "big");
        info.put("in02", "grey");
        info.put("pos02", "mid");
        info.put("out0", "purple");
        ArrayList<ResourceAmount> deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.PURPLE, 0));
        deps.add(new ResourceAmount(Color.GREY, 2));
        deps.add(new ResourceAmount(Color.BLUE, 2));
        test.getPersonalBoard().setDeposits(deps);
        try {
            test.produce(info);
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //strongbox ok
        info.clear();
        info.put("prod0", "yes");
        info.put("in01", "blue");
        info.put("pos01", "big");
        info.put("in02", "purple");
        info.put("pos02", "strongbox");
        info.put("out0", "yellow");
        try {
            test.produce(info);
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //strongbox ko
        info.clear();
        info.put("prod0", "yes");
        info.put("in01", "grey");
        info.put("pos01", "mid");
        info.put("in02", "purple");
        info.put("pos02", "strongbox");
        info.put("out0", "yellow");
        try {
            test.produce(info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        }

        //dep ko
        info.clear();
        info.put("prod0", "yes");
        info.put("in01", "grey");
        info.put("pos01", "mid");
        info.put("in02", "purple");
        info.put("pos02", "small");
        info.put("out0", "yellow");
        try {
            test.produce(info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        }
        
        //spdep ko
        test.getPersonalBoard().addSpecialDeposit(Color.BLUE);
        info.clear();
        info.put("prod0", "yes");
        info.put("in01", "grey");
        info.put("pos01", "mid");
        info.put("in02", "blue");
        info.put("pos02", "sp1");
        info.put("out0", "yellow");
        try {
            test.produce(info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        }

        //spdep ok
        deps= test.getPersonalBoard().getDeposits();
        deps.set(3, new ResourceAmount(Color.BLUE, 2));
        test.getPersonalBoard().setDeposits(deps);
        test.getPersonalBoard().setDeposits(deps);
        info.clear();
        info.put("prod0", "yes");
        info.put("in01", "grey");
        info.put("pos01", "mid");
        info.put("in02", "blue");
        info.put("pos02", "sp1");
        info.put("out0", "yellow");
        try {
            test.produce(info);
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(2).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(3).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //wrong input
        info.clear();
        info.put("prod0", "yes");
        info.put("in01", "grey");
        info.put("pos01", "dep");
        info.put("in02", "blue");
        info.put("pos02", "sp1");
        info.put("out0", "yellow");
        try {
            test.produce(info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(2).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(3).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
        }
    }
    @Test
    public void productionTest(){
        Player test= new Player("test");
        Map<String, String> info= new HashMap<>();
        info.put("prod0", "no");
        info.put("prod1", "yes");
        info.put("pos11", "small");
        info.put("pos12", "big");
        info.put("prod2", "yes");
        info.put("pos21", "mid");
        info.put("pos22", "mid");
        info.put("prod3", "yes");
        info.put("pos31", "big");
        ArrayList<ResourceAmount> deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 2));
        deps.add(new ResourceAmount(Color.GREY, 3));
        test.getPersonalBoard().setDeposits(deps);

        int vp= 1;
        int faith=1;
        ResourceAmount[] cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.PURPLE, 2);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.YELLOW, 0);
        cost[3]= new ResourceAmount(Color.GREY, 0);
        ResourceAmount[]input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.GREY, 1);
        input[1]= new ResourceAmount(Color.BLUE, 0);
        input[2]= new ResourceAmount(Color.YELLOW, 0);
        input[3]= new ResourceAmount(Color.PURPLE, 0);
        ResourceAmount[]output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.GREY, 0);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.YELLOW, 0);
        output[3]= new ResourceAmount(Color.PURPLE, 0);
        DevelopCard card= new DevelopCard(1, vp, faith, Color.PURPLE, cost, input, output);
        test.getPersonalBoard().addCard(2, card, false);
        vp= 3;
        faith=0;
        cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.PURPLE, 3);
        cost[1]= new ResourceAmount(Color.BLUE, 0);
        cost[2]= new ResourceAmount(Color.YELLOW, 0);
        cost[3]= new ResourceAmount(Color.GREY, 0);
        input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.YELLOW, 2);
        input[1]= new ResourceAmount(Color.BLUE, 0);
        input[2]= new ResourceAmount(Color.GREY, 0);
        input[3]= new ResourceAmount(Color.PURPLE, 0);
        output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.PURPLE, 1);
        output[1]= new ResourceAmount(Color.BLUE, 1);
        output[2]= new ResourceAmount(Color.GREY, 1);
        output[3]= new ResourceAmount(Color.YELLOW, 0);
        card= new DevelopCard(1, vp, faith, Color.PURPLE, cost, input, output);
        test.getPersonalBoard().addCard(1, card, false);
        vp= 4;
        faith=1;
        cost= new ResourceAmount[4];
        cost[0]= new ResourceAmount(Color.YELLOW, 2);
        cost[1]= new ResourceAmount(Color.PURPLE, 2);
        cost[2]= new ResourceAmount(Color.GREY, 0);
        cost[3]= new ResourceAmount(Color.BLUE, 0);
        input= new ResourceAmount[4];
        input[0]= new ResourceAmount(Color.BLUE, 1);
        input[1]= new ResourceAmount(Color.GREY, 1);
        input[2]= new ResourceAmount(Color.YELLOW, 0);
        input[3]= new ResourceAmount(Color.PURPLE, 0);
        output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.PURPLE, 2);
        output[1]= new ResourceAmount(Color.BLUE, 0);
        output[2]= new ResourceAmount(Color.YELLOW, 0);
        output[3]= new ResourceAmount(Color.GREY, 0);
        card= new DevelopCard(1, vp, faith, Color.BLUE, cost, input, output);
        test.getPersonalBoard().addCard(0, card, false);

        try {
            test.produce(info);
            assertEquals(0, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(1, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
            System.out.println(test.getPersonalBoard().getFaithMarker().getPosition());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        info.clear();
        info.put("prod0", "no");
        info.put("prod1", "no");
        info.put("prod2", "yes");
        info.put("pos21", "mid");
        info.put("pos22", "mid");
        info.put("prod3", "yes");
        info.put("pos31", "big");
        info.put("prod4", "yes");
        info.put("pos41", "Strongbox");
        info.put("out4", "blue");
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 2));
        deps.add(new ResourceAmount(Color.GREY, 3));
        test.getPersonalBoard().setDeposits(deps);
        try {
            test.produce(info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(1, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(2, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(3, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
            System.out.println(test.getPersonalBoard().getFaithMarker().getPosition());
        }

        info.clear();
        info.put("prod0", "no");
        info.put("prod1", "no");
        info.put("prod2", "yes");
        info.put("pos21", "mid");
        info.put("pos22", "mid");
        info.put("prod3", "yes");
        info.put("pos31", "big");
        info.put("prod4", "yes");
        info.put("pos41", "Strongbox");
        info.put("out4", "blue");
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 2));
        deps.add(new ResourceAmount(Color.GREY, 3));
        test.getPersonalBoard().setDeposits(deps);

        ResourceAmount[] inputcard={new ResourceAmount(Color.BLUE,1), new ResourceAmount(Color.PURPLE, 0), new ResourceAmount(Color.YELLOW, 0), new ResourceAmount(Color.GREY, 0)};
        test.getPersonalBoard().addCard(0, new LeaderDevelopCard(0, 0, 1, Color.BLUE, null, inputcard, null), true);
        try {
            test.produce(info);
            assertEquals(1, test.getPersonalBoard().getDeposits().get(0).getAmount());
            assertEquals(0, test.getPersonalBoard().getDeposits().get(1).getAmount());
            assertEquals(2, test.getPersonalBoard().getDeposits().get(2).getAmount());
            System.out.println(test.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(test.getPersonalBoard().getStrongbox()));
            System.out.println(test.getPersonalBoard().getFaithMarker().getPosition());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }


    /**
     * testing chooseLeader
     */
    @Test
    public void chooseLeaderTest(){
        ArrayList<LeaderCard> leaders = new ArrayList<LeaderCard>();
        leaders.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.GREEN,Color.PURPLE));
        leaders.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY));
        leaders.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE));
        leaders.add(new LevTwoLeader(4,"LevTwo",false,false,Color.YELLOW,Color.BLUE));

        Player player = new Player("giacomino");
        player.receiveLeaders(leaders);

        try {
            player.chooseLeader(1, 2);
            assertEquals("OneAndOne", player.leaders.get(0).getType());
            assertEquals("Resource", player.leaders.get(1).getType());
            assertEquals(2, player.leaders.size());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }
}
