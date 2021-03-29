package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the Market where a player can get the resources
 */
public class Market {
    /**
     * This attribute represents the Marble that is outside the matrix of the market
     */
    private Marble outMarble;
    /**
     * This attribute represents the matrix of the market
     */
    private Marble[][] table;

    /**
     * Constructor of the Market. It instantiates the Marbles in a list, then it shuffles it and creates the matrix and
     * puts the last Marble as the outMarble (calling the instantiate() method).
     */
    public Market() {
        List<Marble> list=new ArrayList<>();
        this.table= new Marble[4][3];
        for (int i=0; i<4; i++){
            list.add(WhiteMarble.getInstance());
        }
        for (int i=0; i<2; i++) {
            list.add(BlueMarble.getInstance());
        }
        for (int i=0; i<2; i++) {
            list.add(GreyMarble.getInstance());
        }
        for (int i=0; i<2; i++) {
            list.add(YellowMarble.getInstance());
        }
        for (int i=0; i<2; i++) {
            list.add(PurpleMarble.getInstance());
        }
        list.add(RedMarble.getInstance());
        Collections.shuffle(list);
        this.instantiate(list);
    }

    /**
     * Method calle dby the constructor. It creates the matrix of the market and instantiates the outMarble
     * @param list: the list of the marbles
     */
    private void instantiate(List<Marble> list) {
        int counter=0;
        for(int col=0; col<4; col++) {
            for(int row=0; row<3; row++){
                this.table[col][row]= list.get(counter);
                counter++;
            }
        }
        this.outMarble=list.get(counter);
    }

    /**
     * Method used when a player correctly takes resources from a row of the market. It changes the configuration of the market
     * by shifting the selected row.
     * @param row: the index of the row to push
     */
    public void pushRow(int row){
        Marble temp=outMarble;
        outMarble=this.table[0][row];
        for(int col=0; col<3; col++){
            this.table[col][row]=this.table[col+1][row];
        }
        this.table[3][row]=temp;
    }

    /**
     * Method used when a player correctly takes resources from a column of the market. It changes the configuration of the market
     * by shifting the selected column.
     * @param col: the index of the column to push
     */
    public void pushColumn(int col){
        Marble temp=outMarble;
        outMarble=this.table[col][0];
        for(int row=0; row<2; row++){
            this.table[col][row]=this.table[col][row+1];
        }
        this.table[col][2]=temp;
    }

    /**
     * This method returns a copy of the row from which the player wants to take the resources.
     * @param row: the index of the selected row
     * @return: a copy of the selected row
     */
    public Marble[] selectRow(int row){
        Marble[] res= new Marble[4];
        for (int col=0; col<4; col++){
            res[col]=this.table[col][row];
        }
        return res;
    }

    /**
     * This method returns a copy of the column from which the player wants to take the resources.
     * @param col: the index of the selected column
     * @return: a copy of the selected column
     */
    public Marble[] selectColumn(int col){
        Marble[] res= new Marble[3];
        for (int row=0; row<3; row++){
            res[row]=this.table[col][row];
        }
        return res;
    }

    /**
     * Utility method used in tests
     * @return: the String representation of the market
     */
    @Override
    public String toString() {
        return "Market{" +
                "outMarble=" + outMarble +
                ", table=" + Arrays.deepToString(table) +
                '}';
    }
}
