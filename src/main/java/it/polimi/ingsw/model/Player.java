package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {

    /**
     * Player's username
     */
    private String name;

    /**
     * Leader Cards chosen by Player
     */
    private LeaderCard[] leaders;

    /**
     * 4 Leader Cards, 2 to be chosen by Player
     */
    private LeaderCard[] leadersToChoose;

    /**
     * Player's Personal Board
     */
    private PersonalBoard personalBoard;

    /**
     * Player's number of initial Resource (depending on Player's turn)
     */
    private int numberInitialResource;

    /**
     * Player's number of Develop Cards (needed for checking when to finish the game)
     */
    private int numberDevelopCards;


    /**
     * This method increases the player's initial position.
     * @param faith
     */
    public void receiveInitialFaith(int faith) {
        personalBoard.setPosition(personalBoard.getPosition()+faith);
    }

    /**
     * This method sets the player's numberInitialResource.
     * @param numberInitialResource is the number of resources the player will receive when the game starts.
     */
    public void receiveInitialResource(int numberInitialResource) {
        this.numberInitialResource = numberInitialResource;
    }

    /**
     * This method is used for getting the player's initial resources.
     * @param map is where the instruction on the turns are stored.
     * @throws InvalidActionException if the move is not valid.
     */
    public void chooseInitialResource(Map<String, String> map) throws InvalidActionException {
        ArrayList<ResourceAmount> deposits = personalBoard.getDeposits();
        Color color1 = parseColor(map.get("res1"));
        Color color2 = parseColor(map.get("res2"));
        int pos1 = parseDepIndex(map.get("pos1"));
        int pos2 = parseDepIndex(map.get("pos2"));
        if (color1 == null || color2 == null) throw new InvalidActionException("You had to select a color.");
        if (pos1 == -1 || pos2 == -1) throw new InvalidActionException("You had to select the position.");
        if (map.size() != numberInitialResource*2) throw new InvalidActionException("You didn't select the right amount of resources.");
        if (numberInitialResource > 0 && numberInitialResource <= 2) {
            if (pos1<0 || pos1>2 || pos2<0 || pos2>2)
                throw new InvalidActionException("Wrong deposit selected.");
            else {
                deposits.get(pos1).setColor(color1);
                deposits.get(pos1).setAmount(deposits.get(pos1).getAmount() + 1);
                if (numberInitialResource == 2)
                    if (color2 != color1)
                        if (pos1 != pos2) {
                            deposits.get(pos2).setColor(color2);
                            deposits.get(pos2).setAmount(deposits.get(pos1).getAmount() + 1);
                        } else throw new InvalidActionException("you can't place your resources in this way.");
                    else if (pos1 == pos2)
                        deposits.get(pos1).setAmount(deposits.get(pos1).getAmount() + 1);
                    else throw new InvalidActionException("you cant place your resources in this way.");
            }
        }
    }

    /**
     * This method is used for swapping deposits.
     * @param source is the first deposit.
     * @param dest is the second deposit.
     * @throws InvalidActionException if the move is not valid.
     */
    public void swapDeposit(String source, String dest) throws InvalidActionException {
        personalBoard.swapDeposits(source,dest);
    }

    /**
     * This method is used for buying a card.
     * @param map is where the instructions are stored.
     * @param card is the card to buy.
     * @return if the played bought the seventh card.
     * @throws InvalidActionException
     */
    public boolean buy (Map<String, String> map, DevelopCard card) throws InvalidActionException, NumberFormatException {
        ArrayList<ResourceAmount> deposit = personalBoard.getDeposits();
        ResourceAmount[] strongbox = personalBoard.getStrongbox();
        int ind = Integer.parseInt(map.get("ind"));
        Map<String,String> map1 = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet())
            if(!entry.getKey().equals("row") || !entry.getKey().equals("column") || !entry.getKey().equals("ind"))
                map1.put(entry.getKey(), entry.getValue());
        if(ind<0 || ind>3) throw new InvalidActionException("Wrong card index");
        if (personalBoard.getTopCard(ind) == null)
            if (card.getLevel()==1)
                card.buyDevelopCard(map1,strongbox,deposit);
            else
                throw new InvalidActionException("You can't place this card here.");
        else if (personalBoard.getTopCard(ind).getLevel() == card.getLevel()-1)
            card.buyDevelopCard(map1,strongbox,deposit);
            else
                throw new InvalidActionException("You can't place this card here.");
        personalBoard.addCard(ind,card,false);
        personalBoard.setStrongbox(strongbox);
        personalBoard.setDeposits(deposit);
        numberDevelopCards++;
        return numberDevelopCards == 7;
    }

    /**
     * Utility method used for parsing the color from the map.
     * @param color is the color in form of a string.
     * @return the color in form of a Color type.
     */
    private Color parseColor(String color) {
        switch(color) {
            case "yellow": return Color.YELLOW;
            case "blue": return Color.BLUE;
            case "grey": return Color.GREY;
            case "purple": return Color.PURPLE;
            default: return null;
        }
    }

    /**
     * Utility method used for parsing the deposit index.
     * @param dep is the index in form of a string.
     * @return the index in form of an int.
     */
    private int parseDepIndex(String dep) {
        switch (dep) {
            case "small":
                return 0;
            case "mid":
                return 1;
            case "big":
                return 2;
            case "sp1":
                return 3;
            case "sp2":
                return 4;
            default:
                return -1;
        }
    }


}
