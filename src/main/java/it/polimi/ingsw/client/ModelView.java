package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Cell;

import java.util.List;
import java.util.Map;

public class ModelView {
    private boolean activeTurn;
    private String name;
    private int[] faithTrack;
    private int position;
    private Tile[] tiles;
    private Map<String, String> deposits;
    private Map<String, String> strongbox;
    private String[][] market;
    private String outMarble;
    private int[][] developDecks;
    private int token;
    private Map<String, String> leaders;
    private List<int[]> slots;

    public ModelView(){
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
    }

    public synchronized boolean isActiveTurn() {
        return activeTurn;
    }

    public synchronized void setActiveTurn(boolean activeTurn) {
        this.activeTurn = activeTurn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getFaithTrack() {
        return faithTrack;
    }

    public void setFaithTrack(int[] faithTrack) {
        this.faithTrack = faithTrack;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public Map<String, String> getDeposits() {
        return deposits;
    }

    public void setDeposits(Map<String, String> deposits) {
        this.deposits = deposits;
    }

    public Map<String, String> getStrongbox() {
        return strongbox;
    }

    public void setStrongbox(Map<String, String> strongbox) {
        this.strongbox = strongbox;
    }

    public String[][] getMarket() {
        return market;
    }

    public void setMarket(String[][] market) {
        this.market = market;
    }

    public String getOutMarble() {
        return outMarble;
    }

    public void setOutMarble(String outMarble) {
        this.outMarble = outMarble;
    }

    public int[][] getDevelopDecks() {
        return developDecks;
    }

    public void setDevelopDecks(int[][] developDecks) {
        this.developDecks = developDecks;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public Map<String, String> getLeaders() {
        return leaders;
    }

    public void setLeaders(Map<String, String> leaders) {
        this.leaders = leaders;
    }

    public List<int[]> getSlots() {
        return slots;
    }

    public void setSlots(List<int[]> slots) {
        this.slots = slots;
    }
}
