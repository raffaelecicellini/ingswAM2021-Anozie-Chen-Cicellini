package it.polimi.ingsw.messages;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This Class represents a Message. The methods in this class return a default value.
 */
public abstract class Message {
    protected Map<String, String> info;
    private final String player;
    private final String action;

    /**
     * Message constructor initiates a new Message object.
     *
     * @param info
     */
    public Message(Map<String, String> info) {
        info.entrySet().stream().collect(Collectors.toMap(e1 -> e1.getKey().toLowerCase(), e1 -> e1.getValue().toLowerCase()));
        player = info.remove("player");
        action = info.remove("action");
        this.info = info;
    }

    /**
     * This method returns the ID of the player's leader card based on a certain index.
     *
     * @param ind the index of the player's leader card that will be returned.
     * @return
     */
    public int getLeader(int ind) {
        return -1;
    }

    /**
     * This method returns a String which represents a resource at a certain index.
     *
     * @param i the index of the resource that will be returned.
     * @return the String which represents a resource at a certain index.
     */
    public String getResource(int i) {
        return null;
    }

    /**
     * This method returns where the resource will be placed.
     *
     * @param ind is the resource's index.
     * @return where the resource will be placed.
     */
    public String getPosition(int ind) {
        return null;
    }

    /**
     * This method returns the index of the player's leader card which is involved in the action.
     *
     * @return index of the player's leader card which is involved in the action.
     */
    public int getIndex() {
        return -1;
    }

    /**
     * This method returns the source of a swap move.
     *
     * @return the source of a swap move.
     */
    public String getSource() {
        return null;
    }

    /**
     * This method returns the destination of a swap message.
     *
     * @return the destination of a swap message.
     */
    public String getDest() {
        return null;
    }

    /**
     * This method returns if the production of a certain index is selected.
     *
     * @param ind is the index of the production.
     * @return if the production of a certain index is selected.
     */
    public boolean isSelected(int ind) {
        return false;
    }

    /**
     * This method returns the resource involved in a certain production.
     *
     * @param prod is the index of the production.
     * @param res  is the index of the resource.
     * @return the resource involved in a certain production.
     */
    public String getRes(int prod, int res) {
        return null;
    }

    /**
     * This method returns the location of the resource involved in a certain position.
     *
     * @param prod is the index of the production.
     * @param res  is the index of the resource.
     * @return the location of the resource involved in a certain position.
     */
    public String getPos(int prod, int res) {
        return null;
    }

    /**
     * This method returns the output resource of a certain production.
     *
     * @param prod the index of the production
     * @return output resource of a certain production.
     */
    public String getOut(int prod) {
        return null;
    }

    /**
     * This method returns the position of a resource involved in a market move.
     *
     * @param ind is the index of the resource.
     * @return the position of a resource involved in a market move.
     */
    public String getPos(int ind) {
        return null;
    }

    /**
     * This method returns the resource involved in a market move.
     *
     * @param ind is the index of the resource.
     * @return the resource involved in a market move.
     */
    public String getRes(int ind) {
        return null;
    }

    /**
     * This method returns if the player selected a row in the market move.
     *
     * @return if the player selected a row in the market move.
     */
    public boolean isRow() {
        return false;
    }

    /**
     * This method returns if the player selected a column in the market move.
     *
     * @return if the player selected a column in the market move.
     */
    public boolean isCol() {
        return false;
    }

    /**
     * This method returns the index of the row/column the player selected in his market move.
     *
     * @return
     */
    public int getMarblesIndex() {
        return -1;
    }

    /**
     * This method returns the column of the develop card the player selected.
     *
     * @return the column of the develop card the player selected.
     */
    public int getCol() {
        return -1;
    }

    /**
     * This method returns the row of the develop card the player selected.
     *
     * @return the row of the develop card the player selected.
     */
    public int getRow() {
        return -1;
    }

    /**
     * This method returns the slot the player selected for the buy move.
     *
     * @return the slot the player selected for the buy move.
     */
    public int getSlot() {
        return -1;
    }

    /**
     * This method returns a map containing the cost of the card.
     *
     * @return a map containing the cost of the card.
     */
    public Map<String, String> getCost() {
        return null;
    }

    /**
     * This method returns the card at a certain column and row.
     *
     * @param col is the column.
     * @param row is the row.
     * @return the card at a certain column and row.
     */
    public int getCard(int col, int row) {
        return -1;
    }

    /**
     * This method returns the marble at a certain row and column.
     *
     * @param col is the column.
     * @param row is the row.
     * @return the marble at a certain row and column.
     */
    public String getMarble(int col, int row) {
        return null;
    }

    /**
     * This method returns the name of the player at a certain index.
     *
     * @param ind the index of the player.
     * @return the name of the player at a certain index.
     */
    public String getPlayer(int ind) {
        return null;
    }

    /**
     * This method returns the color of the marble outside the market.
     *
     * @return the color of the marble outside the market.
     */
    public String getOutMarble() {
        return null;
    }

    /**
     * This method returns the content of the message.
     *
     * @return the content of the message.
     */
    public String getContent() {
        return null;
    }

    /**
     * This method returns the leader at a certain index.
     *
     * @param ind the index of the leader.
     * @return the leader at a certain index.
     */
    public String getLeaders(int ind) {
        return null;
    }

    /**
     * This method returns the initial position of the player.
     *
     * @return the initial position of the player.
     */
    public int getAddPos() {
        return -1;
    }

    /**
     * This method returns how many initial resources the players has.
     *
     * @return
     */
    public int getResQty() {
        return -1;
    }

    /**
     * This method returns a map which contains the state of the player's deposits.
     *
     * @return map which contains the state of the player's deposits.
     */
    public Map<String, String> getDeposits() {
        return null;
    }

    /**
     * This method returns the ID of the card that is now at the top of the slot.
     *
     * @return returns the ID of the card that is now at the top of the slot.
     */
    public int getIdNew() {
        return -1;
    }

    /**
     * This method returns a map which contains the state of the player's strongbox.
     *
     * @return map which contains the state of the player's strongbox.
     */
    public Map<String, String> getStrongbox() {
        return null;
    }

    /**
     * This message returns the id of the card that was bought.
     *
     * @return the id of the card that was bought.
     */
    public int getIdBought() {
        return -1;
    }

    /**
     * This method returns the position of the black cross.
     *
     * @return returns the position of the black cross.
     */
    public int getBlackPos() {
        return -1;
    }

    /**
     * This method returns the new position of the player after a market move.
     *
     * @return the new position of the player after a market move.
     */
    public int getNewPos() {
        return -1;
    }

    /**
     * This method returns how many resources the player discarded during his market move.
     *
     * @return how many resources the player discarded during his market move.
     */
    public int getDiscarded() {
        return -1;
    }

    /**
     * This method returns if the player activated a leader card that gives an extra deposit.
     *
     * @return if the player activated a leader card that gives an extra deposit.
     */
    public boolean isDep() {
        return false;
    }

    /**
     * This method returns the number of leader actions.
     *
     * @return returns the number of leader actions.
     */
    public int getCountLeader() {
        return -1;
    }

    /**
     * This method returns the name of the winner.
     *
     * @return the name of the winner.
     */
    public String getWinner() {
        return null;
    }

    /**
     * This method returns the winner's amount of points.
     *
     * @return the winner's amount of points.
     */
    public int getWinnerPoints() {
        return -1;
    }

    /**
     * This method returns the player's points.
     *
     * @return the player's points.
     */
    public int getPoints() {
        return -1;
    }

    /**
     * This returns the method involved in the error.
     *
     * @return the method involved in the error.
     */
    public String getMethod() {
        return null;
    }

    /**
     * This method returns the state of the player's tile.
     *
     * @param player the player's name.
     * @param index  the index of the tile.
     * @return the state of the player's tile.
     */
    public String getTileState(int player, int index) {
        return null;
    }

    /**
     * This method returns the name of the current player.
     *
     * @return the name of the current player.
     */
    public String getCurrentPlayer() {
        return null;
    }

    /**
     * This method returns the name of the player that ended his turn.
     *
     * @return the name of the player that ended his turn.
     */
    public String getEndedPlayer() {
        return null;
    }

    /**
     * This method returns the token played by lorenzo.
     *
     * @return the token played by lorenzo.
     */
    public int getToken() {
        return -1;
    }

    /**
     * This method returns the message's action.
     *
     * @return the message's action.
     */
    public String getAction() {
        return action;
    }

    /**
     * This method returns the player involved in the message.
     *
     * @return the player involved in the message.
     */
    public String getPlayer() {
        return player;
    }

    /**
     * This method returns the size of the information contained in the message.
     *
     * @return size of the information contained in the message.
     */
    public int size() {
        return info.size();
    }


    /**
     * This method returns if the message has a player field.
     *
     * @param i is the index of the player.
     * @return if the message has a player field.
     */
    public boolean containsPlayer(int i) {
        return false;
    }

    /**
     * This method returns a map containing the player and the action.
     *
     * @return returns a map containing the player and the action.
     */
    public Map<String, String> getAll() {
        info.put("player", player);
        info.put("action", action);
        return info;
    }
}
