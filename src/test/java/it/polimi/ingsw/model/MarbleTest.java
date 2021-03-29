package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the classes that implements the Marble interface
 */
public class MarbleTest {
    //A test for each color. Need to test: action ok, discarded resource, invalid action, white without a leader, white with a leader,
    //red action

    /**
     * For each color (purple, yellow, grey, blue) this method tests the different cases when an action can be performed
     * correctly (big/mid/small/special deposit that already contains the corresponding type of resource but there is still space and
     * empty deposits without duplicates).
     */
    @Test
    public void okActionTest(){
        //For each color, test when an action can be done
        //BLUE
        String chosen="big";
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        FaithMarker faith= new FaithMarker(0);
        Color chosenColor=null;
        Marble test= BlueMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //GREY
        chosen="mid";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= GreyMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //PURPLE
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.PURPLE, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= PurpleMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //YELLOW
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.PURPLE, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //dep leader ok
        chosen="sp1";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.YELLOW, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        chosen="sp1";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.GREY, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= GreyMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        chosen="sp2";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.PURPLE, 0));
        deposits.add(4, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= BlueMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * For each color(purple, yellow, grey, blue) this method tests the different cases when the resource can't be stored so it
     * needs to be discarded (all deposits are full and there is no special deposits available, no swap possibilities for the deposit that
     * already contains the selected resource, the selected resource is already in another deposit but it is full).
     */
    @Test
    public void discardedTest(){
        //For each color, test when a resource needs to be discarded (no space to contain it)
        //BLUE (Magazzino ok ma pieno e no leader dep)
        String chosen="big";
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 3));
        FaithMarker faith= new FaithMarker(0);
        Color chosenColor=null;
        Marble test= BlueMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(1, res);
            if (res==1) {
                System.out.println("Ok, a resource needs to be discarded.");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Something went wrong (I had to discard and I didn't");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //GREY (non è nel magazzino indicato ma non ci sono posti)
        chosen="mid";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.BLUE, 1));
        deposits.add(2, new ResourceAmount(Color.PURPLE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= GreyMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(1, res);
            if (res==1) {
                System.out.println("Ok, a resource needs to be discarded.");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Something went wrong (I had to discard and I didn't");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //PURPLE (è già in altro magazzino ma pieno)
        chosen="mid";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.BLUE, 0));
        deposits.add(2, new ResourceAmount(Color.PURPLE, 3));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= PurpleMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(1, res);
            if (res==1) {
                System.out.println("Ok, a resource needs to be discarded.");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Something went wrong (I had to discard and I didn't");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //YELLOW (già pieno e no leader)
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.BLUE, 2));
        deposits.add(2, new ResourceAmount(Color.PURPLE, 2));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(1, res);
            if (res==1) {
                System.out.println("Ok, a resource needs to be discarded.");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Something went wrong (I had to discard and I didn't");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * For each color (purple, yellow, grey, blue) this method tests the different cases when the action is invalid (the selected
     * resource is already stored in a different deposit and there is still space available, the selected deposit is full but there is
     * a special deposit available, the selected deposit is wrong and there is an empty deposit, the selected special deposit is
     * of a different resource/is not present, the deposit is full but it can be swapped to have more space).
     */
    @Test
    public void invalidActionTest(){
        //For each color, test when an action is invalid (resource where it cannot be insert into)
        //BLUE (già in altro magazzino non pieno)
        String chosen="small";
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        FaithMarker faith= new FaithMarker(0);
        Color chosenColor=null;
        Marble test= BlueMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //GREY (magazzino sbagliato e c'è magazzino libero)
        chosen="big";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= GreyMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //PURPLE (non esiste leader dep)
        chosen="sp1";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= PurpleMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //YELLOW (magazzino pieno e c'è leader dep libero)
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.YELLOW, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //No swap ma ho magazzino leader
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 2));
        deposits.add(3, new ResourceAmount(Color.YELLOW, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //Secondo dep leader
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 3));
        deposits.add(3, new ResourceAmount(Color.BLUE, 1));
        deposits.add(4, new ResourceAmount(Color.YELLOW, 0));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //Colore sbagliato ma c'è posto con swap
        chosen="mid";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= YellowMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //colore giusto ma pieno, c'è posto con swap
        chosen="mid";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= GreyMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //Leader pieno ma c'è posto con swap
        chosen="sp1";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.PURPLE, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.PURPLE, 2));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= PurpleMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        //Leader 2 pieno ma c'è posto
        chosen="sp2";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.PURPLE, 1));
        deposits.add(4, new ResourceAmount(Color.BLUE, 2));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= BlueMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        chosen="big";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= PurpleMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        chosen="big";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.PURPLE, 3));
        deposits.add(3, new ResourceAmount(Color.PURPLE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= PurpleMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }

        chosen="big";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 0));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 3));
        deposits.add(3, new ResourceAmount(Color.BLUE, 0));
        faith= new FaithMarker(0);
        chosenColor=null;
        test= BlueMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }
    }

    /**
     * This method tests the WhiteMarble action() method when there is no active leader of this kind
     */
    @Test
    public void whiteNoLeaderTest(){
        //Test when there is not an active leader/not the right leader
        String chosen="big";
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        FaithMarker faith= new FaithMarker(0);
        Color chosenColor=null;
        LeaderCard[] leaders= new LeaderCard[2];
        leaders[0]= new LevTwoLeader(4, "LevTwo", true, false, Color.YELLOW, Color.BLUE);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        Marble test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        chosen="big";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        faith= new FaithMarker(0);
        chosenColor=null;
        leaders= new LeaderCard[2];
        leaders[0]= new LevTwoLeader(4, "LevTwo", false, false, Color.YELLOW, Color.BLUE);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",false, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method tests the WhiteMarble action() method when there is an active leader of this kind and the action can
     * be done/the resource is discarded/the action is invalid.
     */
    @Test
    public void whiteLeaderTest(){
        //Test when there is only one active WhiteBallLeader: valid and invalid
        //WhiteBallLeader is active: action ok
        String chosen="big";
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.BLUE, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.YELLOW, 1));
        FaithMarker faith= new FaithMarker(0);
        Color chosenColor=null;
        LeaderCard[] leaders= new LeaderCard[2];
        leaders[0]= new LevTwoLeader(4, "LevTwo", true, false, Color.YELLOW, Color.BLUE);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        Marble test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //WhiteBallLeader is active: action ko
        chosen="small";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.BLUE, 2));
        deposits.add(2, new ResourceAmount(Color.PURPLE, 2));
        faith= new FaithMarker(0);
        chosenColor=null;
        leaders[0]= new LevTwoLeader(4, "LevTwo", false, false, Color.YELLOW, Color.BLUE);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            assertEquals(1, res);
            if (res==1) {
                System.out.println("Ok, a resource needs to be discarded.");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Something went wrong (I had to discard and I didn't");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //WhiteBallLeader is active: exception
        chosen="sp1";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.PURPLE, 2));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        deposits.add(3, new ResourceAmount(Color.PURPLE, 2));
        faith= new FaithMarker(0);
        chosenColor=null;
        leaders= new LeaderCard[2];
        leaders[0]=new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.YELLOW,Color.BLUE,Color.PURPLE);
        leaders[1]=new ResourceLeader(3,"Resource",true,false, new ResourceAmount(Color.GREY,5),Color.PURPLE);
        test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }
    }

    /**
     * This method tests the WhiteMarble action() method when there are two active leader of this kind and the player
     * has selected how to transform the WhiteMarble. It tests when is selected the first leader, the second leader, and when
     * an exception is thrown because the selected color is wrong.
     */
    @Test
    public void whiteTwoLeaderTest(){
        //Test when there are two active WhiteBallLeader and user selected the color: valid and invalid
        //WhiteBallLeaders both active, ok the first one
        String chosen="big";
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.BLUE, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.YELLOW, 1));
        FaithMarker faith= new FaithMarker(0);
        Color chosenColor=Color.YELLOW;
        LeaderCard[] leaders= new LeaderCard[2];
        leaders[0]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.BLUE,Color.YELLOW,Color.GREY);
        Marble test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //WhiteBallLeaders both active, ok the second one
        chosen="mid";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.BLUE, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.YELLOW, 1));
        faith= new FaithMarker(0);
        chosenColor=Color.GREY;
        leaders= new LeaderCard[2];
        leaders[0]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.BLUE,Color.YELLOW,Color.GREY);
        test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            assertEquals(0, res);
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //WhiteBallLeaders both active, exception
        chosen="big";
        deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.BLUE, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.YELLOW, 1));
        faith= new FaithMarker(0);
        chosenColor=Color.BLUE;
        leaders= new LeaderCard[2];
        leaders[0]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.PURPLE,Color.GREEN,Color.YELLOW);
        leaders[1]= new TwoAndOneLeader(5,"TwoAndOne",true, false,Color.BLUE,Color.YELLOW,Color.GREY);
        test= WhiteMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, leaders, chosenColor);
            System.out.println("Something went wrong. I needed an exception");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            System.out.println("Exception thrown correctly");
        }
    }

    /**
     * This method tests the RedMarble action() method. It checks if the position of the FaithMarker is correctly increased
     * by the method.
     */
    @Test
    public void redActionTest(){
        //Test for RedMarble to increase faith position
        String chosen=null;
        ArrayList<ResourceAmount> deposits= new ArrayList<>();
        deposits.add(0, new ResourceAmount(Color.YELLOW, 1));
        deposits.add(1, new ResourceAmount(Color.GREY, 1));
        deposits.add(2, new ResourceAmount(Color.BLUE, 1));
        FaithMarker faith= new FaithMarker(5);
        Color chosenColor=null;
        Marble test= RedMarble.getInstance();
        try {
            int res= test.action(chosen, deposits, faith, null, chosenColor);
            assertEquals(0, res);
            assertEquals(6, faith.getPosition());
            if (res==0) {
                System.out.println("Azione andata a buon fine, Player agisca di conseguenza");
                System.out.println(deposits.toString());
            }
            else {
                System.out.println("Errore nell'esecuzione (ho scartato quando non dovevo)");
            }
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }
}
