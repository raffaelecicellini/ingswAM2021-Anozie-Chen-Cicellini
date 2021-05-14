package it.polimi.ingsw.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerView {
    /**
     * It is a representation of the faithtrack. Every elements represents the victory points given by that cell
     */
    private int[] faithTrack;
    /**
     * It represents the position of the player on the faith track
     */
    private int position;
    /**
     * It is a representation of the FavorTiles of the player
     */
    private Tile[] tiles;
    /**
     * It is a String representation of the situation of the player's deposits. For each deposits there is a value indicating
     * the type of resource and a value indicating the amount
     */
    private Map<String, String> deposits;
    /**
     * It is a String representation of the situation of the player's strongbox. For each resource, there is a value
     * indicating its type and a value indicating its amount
     */
    private Map<String, String> strongbox;
    /**
     * String representation of the player's leadercards. For each card, there is a value indicating if it is active, discarded
     * or available for action (activate/discard). And a String int for the id?
     */
    private Map<String, String> leaders;
    /**
     * Representation of the DevelopCards' slots of the player. Each card is represented by its id
     */
    private List<int[]> slots;

    public PlayerView(){
        faithTrack=new int[25];
        tiles=new Tile[3];
        for (int i=0; i<3; i++){
            tiles[i]=new Tile(i+2);
        }
        for (int i=0; i<25; i++){
            if (i==8 || i==16 || i==24){
                if (i==8) faithTrack[i]= 2;
                else if (i==16) faithTrack[i]= 9;
                else faithTrack[i]= 20;
            } else if (i<=2) faithTrack[i]= 0;
            else if (i>=3 && i<=5) faithTrack[i]= 1;
            else if (i>=6 && i<=8) faithTrack[i]= 2;
            else if (i>=9 && i<=11) faithTrack[i]= 4;
            else if (i>=12 && i<=14) faithTrack[i]= 6;
            else if (i>=15 && i<=17) faithTrack[i]= 9;
            else if (i>=18 && i<=20) faithTrack[i]= 12;
            else if (i>=21 && i<=23) faithTrack[i]= 16;
        }
        this.slots= new ArrayList<>();
        this.slots.add(new int[3]);
        this.slots.add(new int[3]);
        this.slots.add(new int[3]);
        this.deposits=new HashMap<>();
        deposits.put("smallres", "empty");
        deposits.put("smallqty", String.valueOf(0));
        deposits.put("midres", "empty");
        deposits.put("midqty", String.valueOf(0));
        deposits.put("bigres", "empty");
        deposits.put("bigqty", String.valueOf(0));
        this.strongbox= new HashMap<>();
        String[] res={"BLUE", "PURPLE", "GREY", "YELLOW"};
        String box, qty;
        for (int i=0; i<res.length; i++){
            box="strres"+i;
            qty="strqty"+i;
            strongbox.put(box, res[i]);
            strongbox.put(qty, String.valueOf(0));
        }
    }

    public int[] getFaithTrack() {
        return this.faithTrack;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position=position;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles=tiles;
    }

    public Map<String, String> getDeposits() {
        return this.deposits;
    }

    public void setDeposits(Map<String, String> deposits) {
        this.deposits=deposits;
    }

    public Map<String, String> getStrongbox() {
        return this.strongbox;
    }

    public void setStrongbox(Map<String, String> strongbox) {
        this.strongbox=strongbox;
    }

    public Map<String, String> getLeaders() {
        return this.leaders;
    }

    public void setLeaders(Map<String, String> leaders) {
        this.leaders=leaders;
    }

    public List<int[]> getSlots() {
        return this.slots;
    }

    public void setSlots(List<int[]> slots) {
        this.slots=slots;
    }
}
