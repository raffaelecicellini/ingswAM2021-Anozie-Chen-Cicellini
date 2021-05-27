package it.polimi.ingsw.messages;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class Message {
    protected Map<String,String> info;
    private final String player;
    private final String action;

    public Message (Map<String, String> info){
        info.entrySet().stream().collect(Collectors.toMap(e1 -> e1.getKey().toLowerCase(), e1 -> e1.getValue().toLowerCase()));
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

    public int getCard(int col, int row) {
        return -1;
    }

    public String getMarble(int col, int row) {
        return null;
    }

    public String getPlayer(int ind) {
        return null;
    }

    public String getOutMarble() {
        return null;
    }

    public String getContent() {
        return null;
    }

    public String getLeaders(int ind) {
        return null;
    }

    public int getAddPos() {
        return -1;
    }

    public int getResQty() {
        return -1;
    }

    public Map<String,String> getDeposits() {
        return null;
    }

    public int getIdNew() {
        return -1;
    }

    public Map<String,String> getStrongbox() {
        return null;
    }

    public int getIdBought() {
        return -1;
    }

    public int getBlackPos() {
        return -1;
    }

    public int getNewPos() {
        return -1;
    }

    public int getDiscarded() {
        return -1;
    }

    public boolean isDep() {
        return false;
    }

    public int getCountLeader() {
        return -1;
    }

    public String getWinner() {
        return null;
    }

    public int getWinnerPoints() {
        return -1;
    }

    public int getPoints() {
        return -1;
    }

    public String getMethod() {
        return null;
    }

    public String getTileState(int player, int index) {
        return null;
    }

    public String getCurrentPlayer() {
        return null;
    }

    public String getEndedPlayer() {
        return null;
    }

    public int getToken() {
        return -1;
    }

    public String getAction() {
        return action;
    }

    public String getPlayer() {
        return player;
    }

    public int size() {
        return info.size();
    }

    public Map<String, String> getAll(){
        map.put("player", player);
        map.put("action", action);
        return map;
    }
}
