package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    public void leaderTest(){
        Player test= new Player("test");
        assertEquals("test", test.getName());
        test.leaders.add(0, new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY, 0));
        test.leaders.add(1, new LevTwoLeader(4,"LevTwo",false,false,Color.YELLOW,Color.BLUE, 0));

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
        DevelopCard card1= new DevelopCard(1, vp, faith, Color.PURPLE, cost, input, output, 0);
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
        DevelopCard card2= new DevelopCard(2, vp, faith, Color.YELLOW, cost, input, output, 0);
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
        info.put("action","produce");
        info.put("player","test");
        ArrayList<ResourceAmount> deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.PURPLE, 0));
        deps.add(new ResourceAmount(Color.GREY, 2));
        deps.add(new ResourceAmount(Color.BLUE, 2));
        test.getPersonalBoard().setDeposits(deps);
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
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
        DevelopCard card= new DevelopCard(1, vp, faith, Color.PURPLE, cost, input, output, 0);
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
        card= new DevelopCard(1, vp, faith, Color.PURPLE, cost, input, output, 0);
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
        card= new DevelopCard(1, vp, faith, Color.BLUE, cost, input, output, 0);
        test.getPersonalBoard().addCard(0, card, false);

        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 2));
        deps.add(new ResourceAmount(Color.GREY, 3));
        test.getPersonalBoard().setDeposits(deps);
        try {
            test.produce(new ProductionMessage(info));
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
        info.put("player", "test");
        info.put("action", "produce");
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 2));
        deps.add(new ResourceAmount(Color.GREY, 3));
        test.getPersonalBoard().setDeposits(deps);

        ResourceAmount[] inputcard={new ResourceAmount(Color.BLUE,1), new ResourceAmount(Color.PURPLE, 0), new ResourceAmount(Color.YELLOW, 0), new ResourceAmount(Color.GREY, 0)};
        test.getPersonalBoard().addCard(0, new LeaderDevelopCard(0, 0, 1, Color.BLUE, null, inputcard, null, 0), true);
        try {
            test.produce(new ProductionMessage(info));
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
        ArrayList<LeaderCard> leaders = new ArrayList<>();
        leaders.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.GREEN,Color.PURPLE, 0));
        leaders.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY, 0));
        leaders.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE, 0));
        leaders.add(new LevTwoLeader(4,"LevTwo",false,false,Color.YELLOW,Color.BLUE, 0));

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

    /**
     * Tests if the player can the same resources in the same deposit.
     */
    @Test
    public void chooseInitialResourceTest1() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","yellow");
        test.put("pos1","mid");
        test.put("pos2","mid");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),2);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     *Test where you can see that the player can't put two different resources in the same deposit. The deposits are unchanged.
     */
    @Test
    public void chooseInitialResourceTest2() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","mid");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
        }catch (InvalidActionException e) {
            e.printStackTrace();
            p1.receiveInitialResource(2);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        }
    }

    /**
     * Test where you can see that the player can put two resources in two different deposits.
     */
    @Test
    public void chooseInitialResourceTest3() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","small");
        Player p1 = new Player("test");
        test.put("action", "chooseresources");
        test.put("player", "test");
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),Color.BLUE);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Test where you can see that the player can put two resources in two different deposits.
     */
    @Test
    public void chooseInitialResourceTest4() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","big");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),Color.BLUE);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Test where you can see that the player can't put resources in the extra deposit. The player's deposits are unchanged.
     */
    @Test
    public void chooseInitialResourceTest5() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","sp2");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
        }catch (InvalidActionException e) {
            e.printStackTrace();
            p1.receiveInitialResource(2);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        }
    }

    /**
     * Test where you can see that the player can't get more resources than he should. The player's deposits are unchanged.
     */
    @Test
    public void chooseInitialResourceTest6() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","sp2");
        test.put("pos3","mid");
        test.put("res3","yellow");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
        }catch (InvalidActionException e) {
            e.printStackTrace();
            p1.receiveInitialResource(2);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        }
    }

    /**
     * Test where you can see that the player can't store the same resource in two different deposits and the player's deposit are unchanged.
     */
    @Test
    public void chooseInitialResourceTest7() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","yellow");
        test.put("pos1","mid");
        test.put("pos2","small");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
        }catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        }
    }

    /**
     * Test if swapDeposits works as expected.
     */
    @Test
    public void swapTest1() {
        Map<String,String> map = new HashMap<>();
        map.put("source","big");
        map.put("dest","mid");
        map.put("action", "swap");
        map.put("player", "test");
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","big");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
            p1.swapDeposit(new SwapMessage(map));
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),Color.YELLOW);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),Color.BLUE);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test shows that swapDeposit will throw an exception if the amount of information is not as expected.
     */
    @Test
    public void swapTest2() {
        Map<String,String> map = new HashMap<>();
        map.put("source","big");
        map.put("dest","mid");
        map.put("player","test");
        map.put("action","swap");
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("res2","blue");
        test.put("pos1","mid");
        test.put("pos2","big");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(2);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
            p1.swapDeposit(new SwapMessage(map));
        }catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),Color.BLUE);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        }
    }

    /**
     * Tests the receiveInitialFaith method.
     */
    @Test
    public void receiveInitialFaithTest() {
        Player p1 = new Player("1");
        Player p2 = new Player("2");
        Player p3 = new Player("3");
        Player p4 = new Player("4");
        p1.receiveInitialFaith(0);
        p2.receiveInitialFaith(1);
        p3.receiveInitialFaith(2);
        p4.receiveInitialFaith(3);
        assertEquals(p1.getPersonalBoard().getPosition(),0);
        assertEquals(p2.getPersonalBoard().getPosition(),1);
        assertEquals(p3.getPersonalBoard().getPosition(),2);
        assertEquals(p4.getPersonalBoard().getPosition(),3);
    }

    /**
     * Tests if cards are bought correctly.
     */
    @Test
    public void buyTest1() {
        Player p = new Player("test");
        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0]= new ResourceAmount(Color.BLUE, 10);
        strongbox[1]= new ResourceAmount(Color.PURPLE, 10);
        strongbox[2]= new ResourceAmount(Color.GREY, 10);
        strongbox[3]= new ResourceAmount(Color.YELLOW, 10);
        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,1));
        deposits.add(new ResourceAmount(Color.GREY,2));
        deposits.add(new ResourceAmount(Color.YELLOW,3));
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.BLUE,1);
        cost[1] = new ResourceAmount(Color.PURPLE,1);
        cost[2] = new ResourceAmount(Color.GREY,1);
        cost[3] = new ResourceAmount(Color.YELLOW,0);
        ResourceAmount[] cost1 = new ResourceAmount[4];
        cost1[0] = new ResourceAmount(Color.BLUE,0);
        cost1[1] = new ResourceAmount(Color.PURPLE,0);
        cost1[2] = new ResourceAmount(Color.GREY,3);
        cost1[3] = new ResourceAmount(Color.YELLOW,3);
        p.getPersonalBoard().setDeposits(deposits);
        p.getPersonalBoard().setStrongbox(strongbox);
        DevelopCard t = new DevelopCard(1,2,0,Color.GREEN,cost,null,null, 0);
        DevelopCard t1 = new DevelopCard(2,1,0,Color.BLUE,cost1,null,null, 0);
        Map<String,String> map = new HashMap<>();
        map.put("res1","small");
        map.put("res2","strongbox");
        map.put("res3","mid");
        map.put("ind","1");
        map.put("action","buy");
        map.put("player","test");
        Map<String,String> map1 = new HashMap<>();
        map1.put("res1","mid");
        map1.put("res2","strongbox");
        map1.put("res3","strongbox");
        map1.put("res4","strongbox");
        map1.put("res5","strongbox");
        map1.put("res6","strongbox");
        map1.put("ind","1");
        map1.put("action","buy");
        map1.put("player","test");
        ArrayList<LeaderCard>leaders= new ArrayList<>();
        leaders.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.GREEN,Color.PURPLE, 3));
        leaders.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE, 11));
        p.receiveLeaders(leaders);
        try {
            p.buy(new BuyMessage(map),t);
            assertEquals(p.getPersonalBoard().getDeposits().get(0).getColor(),Color.BLUE);
            assertEquals(p.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getColor(),Color.GREY);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p.getPersonalBoard().getDeposits().get(2).getColor(),Color.YELLOW);
            assertEquals(p.getPersonalBoard().getDeposits().get(2).getAmount(),3);
            assertEquals(p.getPersonalBoard().getStrongbox()[1].getAmount(),9);
            assertEquals(p.getPersonalBoard().getStrongbox()[1].getColor(),Color.PURPLE);
            p.buy((new BuyMessage(map1)),t1);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getColor(),Color.GREY);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(p.getPersonalBoard().getTopCard(1),t1);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test shows that the player can't buy a level 2 card and place it on an empty stack. The player's deposits and strongbox are unchanged.
     */
    @Test
    public void buyTest2() {
        Player p = new Player("test");
        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0]= new ResourceAmount(Color.BLUE, 10);
        strongbox[1]= new ResourceAmount(Color.PURPLE, 10);
        strongbox[2]= new ResourceAmount(Color.GREY, 10);
        strongbox[3]= new ResourceAmount(Color.YELLOW, 10);
        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,1));
        deposits.add(new ResourceAmount(Color.GREY,2));
        deposits.add(new ResourceAmount(Color.YELLOW,3));
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.BLUE,1);
        cost[1] = new ResourceAmount(Color.PURPLE,1);
        cost[2] = new ResourceAmount(Color.GREY,1);
        cost[3] = new ResourceAmount(Color.YELLOW,0);
        p.getPersonalBoard().setDeposits(deposits);
        p.getPersonalBoard().setStrongbox(strongbox);
        DevelopCard t = new DevelopCard(2,2,0,Color.GREEN,cost,null,null, 0);
        Map<String,String> map = new HashMap<>();
        map.put("res1","small");
        map.put("res2","strongbox");
        map.put("res3","mid");
        map.put("ind","1");
        map.put("action","buy");
        map.put("player","test");
        ArrayList<LeaderCard>leaders= new ArrayList<>();
        leaders.add(new OneAndOneLeader(2,"OneAndOne",false,false,Color.YELLOW,Color.GREEN,Color.PURPLE, 3));
        leaders.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE, 11));
        p.receiveLeaders(leaders);
        try {
            p.buy(new BuyMessage(map),t);
        }catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(p.getPersonalBoard().getDeposits().get(0).getColor(),Color.BLUE);
            assertEquals(p.getPersonalBoard().getDeposits().get(0).getAmount(),1);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getColor(),Color.GREY);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getAmount(),2);
            assertEquals(p.getPersonalBoard().getDeposits().get(2).getColor(),Color.YELLOW);
            assertEquals(p.getPersonalBoard().getDeposits().get(2).getAmount(),3);
        }
    }

    @Test
    public void buyTest3(){
        Player p = new Player("test");
        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0]= new ResourceAmount(Color.BLUE, 10);
        strongbox[1]= new ResourceAmount(Color.PURPLE, 10);
        strongbox[2]= new ResourceAmount(Color.GREY, 10);
        strongbox[3]= new ResourceAmount(Color.YELLOW, 10);
        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,1));
        deposits.add(new ResourceAmount(Color.GREY,2));
        deposits.add(new ResourceAmount(Color.YELLOW,3));
        ResourceAmount[] cost = new ResourceAmount[4];
        cost[0] = new ResourceAmount(Color.BLUE,1);
        cost[1] = new ResourceAmount(Color.PURPLE,1);
        cost[2] = new ResourceAmount(Color.GREY,1);
        cost[3] = new ResourceAmount(Color.YELLOW,0);
        p.getPersonalBoard().setDeposits(deposits);
        p.getPersonalBoard().setStrongbox(strongbox);
        DevelopCard t = new DevelopCard(1,2,0,Color.GREEN,cost,null,null, 0);
        Map<String,String> map = new HashMap<>();
        map.put("res1","small");
        map.put("res2","mid");
        map.put("ind","1");
        map.put("action","buy");
        map.put("player","test");
        ArrayList<LeaderCard>leaders= new ArrayList<>();
        leaders.add(new OneAndOneLeader(2,"OneAndOne",true,false,Color.YELLOW,Color.GREEN,Color.PURPLE, 3));
        leaders.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.YELLOW,Color.BLUE,Color.PURPLE, 11));
        p.receiveLeaders(leaders);
        try {
            p.buy(new BuyMessage(map),t);
            assertEquals(p.getPersonalBoard().getDeposits().get(0).getColor(),Color.BLUE);
            assertEquals(p.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getColor(),Color.GREY);
            assertEquals(p.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p.getPersonalBoard().getDeposits().get(2).getColor(),Color.YELLOW);
            assertEquals(p.getPersonalBoard().getDeposits().get(2).getAmount(),3);
            assertEquals(p.getPersonalBoard().getStrongbox()[1].getAmount(),10);
            assertEquals(p.getPersonalBoard().getStrongbox()[1].getColor(),Color.PURPLE);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing fromMarket, all successful inserts
     */
    @Test
    public void fromMarketTest1(){
        Player p = new Player("test");
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 0));
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE, 0));
        p.receiveLeaders(leaderCards);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(null,0));
        deposits.add(new ResourceAmount(null,0));
        deposits.add(new ResourceAmount(null,0));
        deposits.add(new ResourceAmount(Color.YELLOW,0));
        deposits.add(new ResourceAmount(Color.GREY,0));
        p.getPersonalBoard().setDeposits(deposits);

        Map<String,String> map = new HashMap<>();
        map.put("pos1","small");
        map.put("pos2","mid");
        map.put("pos3","big");
        map.put("pos4", "mid");
        map.put("action","produce");
        map.put("player","test");

        Marble[] marbles = new Marble[4];
        marbles[0] = BlueMarble.getInstance();
        marbles[1] = GreyMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();
        marbles[3] = GreyMarble.getInstance();


        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(3).getColor());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(3).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(4).getColor());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(4).getAmount());

        //------------------------------------------------------------------------------------

        marbles = new Marble[3];
        marbles[0] = GreyMarble.getInstance();
        marbles[1] = YellowMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();

        map.clear();
        map.put("pos1","sP2");
        map.put("pos2","big");
        map.put("pos3","big");
        map.put("action","produce");
        map.put("player","test");
        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(3, p.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(3).getColor());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(3).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(4).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(4).getAmount());

        //--------------------------------------------------------------------------

        marbles[0] = GreyMarble.getInstance();
        marbles[1] = YellowMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();

        map.clear();
        map.put("pos1","sP2");
        map.put("pos2","Sp1");
        map.put("pos3","SP1");
        map.put("action","produce");
        map.put("player","test");
        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(3, p.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(3).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(3).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(4).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(4).getAmount());

        //-------------------------------------------------------------------------------------
        marbles[0] = RedMarble.getInstance();
        marbles[1] = RedMarble.getInstance();
        marbles[2] = RedMarble.getInstance();

        map.clear();
        map.put("pos1","sP2");
        map.put("pos2","Sp1");
        map.put("pos3","SP1");
        map.put("action","produce");
        map.put("player","test");
        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(3, p.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(3).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(3).getAmount());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(4).getColor());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(4).getAmount());
        assertEquals(3, p.getPersonalBoard().getPosition());

    }

    /**
     * Testing fromMarket, random order inserting
     */
    @Test
    public void fromMarketTest2(){
        Player p = new Player("test");
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 0));
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE, 0));
        p.receiveLeaders(leaderCards);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,0));
        deposits.add(new ResourceAmount(Color.GREY,0));
        deposits.add(new ResourceAmount(Color.YELLOW,0));
        p.getPersonalBoard().setDeposits(deposits);

        Marble[] marbles = new Marble[3];
        marbles[0] = BlueMarble.getInstance();
        marbles[1] = GreyMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();

        Map<String,String> map = new HashMap<>();
        map.put("pos1","mid");
        map.put("pos2","BIG");
        map.put("pos3","SmAlL");
        map.put("action","produce");
        map.put("player","test");

        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(2).getAmount());

        //----------------------------------------------------------------------------

        marbles[0] = WhiteMarble.getInstance();
        marbles[1] = WhiteMarble.getInstance();
        marbles[2] = WhiteMarble.getInstance();

        map.clear();
        map.put("pos1","mid");
        map.put("pos2","BIG");
        map.put("pos3","SmAlL");
        map.put("action","produce");
        map.put("player","test");

        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(2).getAmount());
    }

    /**
     * Testing fromMarket, full deposits
     */
    @Test
    public void fromMarketTest3(){
        Player p = new Player("test");
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 0));
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE, 0));
        p.receiveLeaders(leaderCards);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,1));
        deposits.add(new ResourceAmount(Color.GREY,2));
        deposits.add(new ResourceAmount(Color.YELLOW,3));
        p.getPersonalBoard().setDeposits(deposits);

        Marble[] marbles = new Marble[3];
        marbles[0] = BlueMarble.getInstance();
        marbles[1] = GreyMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();

        Map<String,String> map = new HashMap<>();
        map.put("pos1","mid");
        map.put("pos2","BIG");
        map.put("pos3","SmAlL");
        map.put("action","produce");
        map.put("player","test");
        int disc = 0;

        try {
            disc = p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(3, p.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(3, disc);
    }

    /**
     * Testing fromMarket, exceptions
     */
    @Test
    public void fromMarketTest4(){
        Player p = new Player("test");
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 0));
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE, 0));
        p.receiveLeaders(leaderCards);

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,0));
        deposits.add(new ResourceAmount(Color.GREY,2));
        deposits.add(new ResourceAmount(Color.YELLOW,0));
        p.getPersonalBoard().setDeposits(deposits);

        Marble[] marbles = new Marble[4];
        marbles[0] = BlueMarble.getInstance();
        marbles[1] = GreyMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();
        marbles[3] = WhiteMarble.getInstance();

        Map<String,String> map = new HashMap<>();
        map.put("pos1","mid");
        //map.put("pos2","BIG");
        //map.put("pos3","SmAlL");
        map.put("action","produce");
        map.put("player","test");

        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(2).getAmount());
    }

    /**
     * Testing fromMarket, with TwoAndOneLeader
     */
    @Test
    public void fromMarketTest5(){
        Player p = new Player("test");
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 0));
        leaderCards.add(new ResourceLeader(3,"Resource",false,false, new ResourceAmount(Color.YELLOW,5),Color.GREY, 0));
        p.receiveLeaders(leaderCards);

        DevelopCard developCard1 = new DevelopCard(0, 0, 0, Color.PURPLE, null, null, null, 0);
        p.getPersonalBoard().addCard(0, developCard1, false);
        DevelopCard developCard2 = new DevelopCard(0, 0, 0, Color.PURPLE, null, null, null, 0);
        p.getPersonalBoard().addCard(1, developCard2, false);
        DevelopCard developCard3 = new DevelopCard(0, 0, 0, Color.GREEN, null, null, null, 0);
        p.getPersonalBoard().addCard(2, developCard3, false);

        try {
            p.activateLeader(0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,0));
        deposits.add(new ResourceAmount(Color.GREY,0));
        deposits.add(new ResourceAmount(Color.YELLOW,0));
        p.getPersonalBoard().setDeposits(deposits);

        Marble[] marbles = new Marble[3];
        marbles[0] = WhiteMarble.getInstance();
        marbles[1] = GreyMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();

        Map<String,String> map = new HashMap<>();
        map.put("pos1","BIG");
        map.put("pos2","mid");
        map.put("pos3","BIG");
        map.put("action","produce");
        map.put("player","test");

        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(2).getAmount());

    }

    /**
     * Testing fromMarket, with 2 TwoAndOneLeaders
     */
    @Test
    public void fromMarketTest6(){
        Player p = new Player("test");
        ArrayList<LeaderCard> leaderCards = new ArrayList<>();
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW, 0));
        leaderCards.add(new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.GREEN,Color.PURPLE,Color.BLUE, 0));
        p.receiveLeaders(leaderCards);

        DevelopCard developCard1 = new DevelopCard(1, 0, 0, Color.PURPLE, null, null, null, 0);
        p.getPersonalBoard().addCard(0, developCard1, false);
        DevelopCard developCard2 = new DevelopCard(2, 0, 0, Color.PURPLE, null, null, null, 0);
        p.getPersonalBoard().addCard(0, developCard2, false);
        DevelopCard developCard3 = new DevelopCard(1, 0, 0, Color.GREEN, null, null, null, 0);
        p.getPersonalBoard().addCard(1, developCard3, false);
        DevelopCard developCard4 = new DevelopCard(2, 0, 0, Color.GREEN, null, null, null, 0);
        p.getPersonalBoard().addCard(1, developCard4, false);
        DevelopCard developCard5 = new DevelopCard(3, 0, 0, Color.GREEN, null, null, null, 0);
        p.getPersonalBoard().addCard(1, developCard5, false);
        DevelopCard developCard6 = new DevelopCard(3, 0, 0, Color.GREEN, null, null, null, 0);
        p.getPersonalBoard().addCard(0, developCard6, false);


        try {
            p.activateLeader(0);
            p.activateLeader(1);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        deposits.add(new ResourceAmount(Color.BLUE,0));
        deposits.add(new ResourceAmount(Color.GREY,0));
        deposits.add(new ResourceAmount(Color.YELLOW,0));
        p.getPersonalBoard().setDeposits(deposits);

        Marble[] marbles = new Marble[3];
        marbles[0] = WhiteMarble.getInstance();
        marbles[1] = GreyMarble.getInstance();
        marbles[2] = YellowMarble.getInstance();

        Map<String,String> map = new HashMap<>();
        map.put("pos1","big");
        //map.put("res1","BLUE");
        map.put("res1","YELLOW");
        map.put("pos2","mid");
        map.put("pos3","BIG");
        map.put("action","produce");
        map.put("player","test");

        try {
            p.fromMarket(new MarketMessage(map), marbles);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, p.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(Color.GREY, p.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(Color.YELLOW, p.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(0, p.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(1, p.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(2, p.getPersonalBoard().getDeposits().get(2).getAmount());
    }

    /**
     * Tests that the player can correctly receive one initial resource.
     */
    @Test
    public void chooseInitialResourceTest8() {
        Map<String,String> test = new HashMap<>();
        test.put("res1","yellow");
        test.put("pos1","mid");
        test.put("action", "chooseresources");
        test.put("player", "test");
        Player p1 = new Player("test");
        p1.receiveInitialResource(1);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),0);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
        assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
        try {
            p1.chooseInitialResource(new ResourceMessage(test));
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getAmount(),1);
            assertEquals(p1.getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getAmount(),0);
            assertEquals(p1.getPersonalBoard().getDeposits().get(2).getColor(),null);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }
}
