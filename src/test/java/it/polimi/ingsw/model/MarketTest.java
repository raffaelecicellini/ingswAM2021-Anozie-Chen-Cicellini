package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the Market class.
 */
public class MarketTest {
    /**
     * Creation test. We are testing if the market is instantiated correctly
     */
    @Test
    public void creationTest(){
        //Test if the Market is created and shuffled correctly
        Market test= new Market();
        System.out.println(test.toString());
    }

    /**
     * Selection test. We are testing if a row or column, once selected, is correctly returned to the caller
     */
    @Test
    public void selectionTest(){
        //Test if the selection of a row/column is done right
        Market test= new Market();
        Marble[] array= test.selectColumn(0);
        System.out.println(Arrays.toString(array) + Arrays.toString(test.selectColumn(0)));
        assertEquals(3, array.length);

        array= test.selectRow(2);
        System.out.println(Arrays.toString(array) + Arrays.toString(test.selectRow(2)));
        assertEquals(4, array.length);
    }

    /**
     * Push test. We are testing if a row or column is correctly pushed by the corresponding method
     */
    @Test
    public void pushTest(){
        //Test if, once the player action is done well, the market pushes correctly the right row/column
        Market test= new Market();
        Marble[] array= test.selectColumn(0);
        System.out.println(Arrays.toString(array) + Arrays.toString(test.selectColumn(0)));
        assertEquals(3, array.length);
        System.out.println(test.toString());
        test.pushColumn(0);
        System.out.println(test.toString());

        array= test.selectRow(2);
        System.out.println(Arrays.toString(array) + Arrays.toString(test.selectRow(2)));
        assertEquals(4, array.length);
        System.out.println(test.toString());
        test.pushRow(2);
        System.out.println(test.toString());
    }
}
