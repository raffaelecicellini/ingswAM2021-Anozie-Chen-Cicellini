package it.polimi.ingsw.messages;

import java.util.Map;

public abstract class Message {
    protected Map<String,String> info;
    private final String player;
    private final String action;

    public Message (Map<String, String> info){
        player=info.remove("player");
        action=info.remove("action");
        this.info=info;
    }

    public int getLeader(int ind) {
        return -1;
    }

    public String getResource(int i) {
        return null;
    }

    public String getPosition(int ind) {
        return null;
    }

    public int getIndex() {
        return -1;
    }

    public String getSource() {
        return null;
    }

    public String getDest() {
        return null;
    }

    public boolean isSelected(int ind) {
        return false;
    }

    public String getRes(int prod, int res) {
        return null;
    }

    public String getPos(int prod, int res) {
        return null;
    }

    public String getOut(int prod) {
        return null;
    }

    public String getPos(int ind) {
        return null;
    }

    public String getRes(int ind) {
        return null;
    }

    public boolean isRow() {
        return false;
    }

    public boolean isCol() {
        return false;
    }

    public int getMarblesIndex() {
        return -1;
    }

    public int getCol() {
        return -1;
    }

    public int getRow() {
        return -1;
    }

    public int getSlot() {
        return -1;
    }

    public Map<String,String> getCost() {
        return null;
    }
}
