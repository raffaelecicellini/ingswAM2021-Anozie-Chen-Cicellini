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
     * TODO change to private
     */
    protected ArrayList<LeaderCard> leaders;

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

    public Player(String name){
        this.name=name;
        this.personalBoard= new PersonalBoard();
        this.leaders= new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public PersonalBoard getPersonalBoard(){
        return this.personalBoard;
    }

    public void discardLeader(int pos) throws InvalidActionException {
        if (pos<0 || pos>1) throw new InvalidActionException("The index is invalid! Try again (index=0 || index=1");
        LeaderCard current= leaders.get(pos);
        if (current.isActive()) throw new InvalidActionException("The leader is already activated, you can't discard it");
        if (!current.isActive() && !current.isDiscarded()) {
            current.setDiscarded(true);
            FaithMarker fm= this.personalBoard.getFaithMarker();
            fm.setPosition(fm.getPosition()+1);
        }

    }

    public void activateLeader(int pos) throws InvalidActionException{
        if (pos<0 || pos>1) throw new InvalidActionException("The index is invalid! Try again (index=0 || index=1");
        LeaderCard current= leaders.get(pos);
        if (current.isDiscarded()) throw new InvalidActionException("The leader is already discarded, you can't activate it");
        if (!current.isActive() && !current.isDiscarded()) {
            ArrayList<ResourceAmount> deposits= this.personalBoard.getDeposits();
            ResourceAmount[] strongbox= this.personalBoard.getStrongbox();
            FaithMarker fm= this.personalBoard.getFaithMarker();
            ArrayList<DevelopCard[]> slots= new ArrayList<>();
            for (int i=0; i<3; i++){
                slots.add(i, this.personalBoard.getSlot(i));
            }

            boolean res= current.checkRequirements(deposits, slots, fm, strongbox);
            if (res) {
                current.setActive(true);
                if (current.getType().equals("Resource")){
                    personalBoard.addSpecialDeposit(current.getDeposit());
                }
                else if (current.getType().equals("LevTwo")){
                    ResourceAmount[] input={new ResourceAmount(current.getProduction(), 1), null, null, null};
                    LeaderDevelopCard card= new LeaderDevelopCard(0, 0, 1, null, null, input, null);
                    personalBoard.addCard(0, card, true);
                }
            }
            else throw new InvalidActionException("You don't have the requirements to activate this leader!");
        }
    }

    public void produce(Map<String, String> info) throws InvalidActionException{
        String curr;
        String key;
        DevelopCard card;
        int faith=0;
        ResourceAmount[] strongbox= personalBoard.getStrongbox();
        ArrayList<ResourceAmount> deposits= personalBoard.getDeposits();
        ResourceAmount[] output= new ResourceAmount[4];
        output[0]= new ResourceAmount(Color.BLUE, 0);
        output[1]= new ResourceAmount(Color.PURPLE, 0);
        output[2]= new ResourceAmount(Color.GREY, 0);
        output[3]= new ResourceAmount(Color.YELLOW, 0);
        for (int i=0; i<6; i++){
            Map<String, String> production= new HashMap<>();
            curr="prod"+i;
            if (info.containsKey(curr) && info.get(curr).equals("yes")){
                if (i==0){
                    production.put("in1", info.get("in01"));
                    production.put("pos1", info.get("pos01"));
                    production.put("in2", info.get("in02"));
                    production.put("pos2", info.get("pos02"));
                    production.put("out", info.get("out0"));
                    this.baseProduction(production, strongbox, deposits, output);
                }
                else if (i>0 && i<4){
                    key="pos"+i+"1";
                    production.put("Res1", info.get(key));
                    if (info.containsKey("pos"+i+"2")) {
                        key="pos"+i+"2";
                        production.put("Res2", info.get(key));
                    }
                    card= personalBoard.getTopCard(i-1);
                    if (card!=null){
                        faith=faith+card.activateProduction(production, strongbox, deposits, output);
                    }
                    else throw new InvalidActionException("You don't have a production in slot: "+(i-1));
                }
                else {
                    key="pos"+i+"1";
                    production.put("Res1", info.get(key));
                    key="out"+i;
                    production.put("Resout", info.get(key));
                    card= personalBoard.getTopCard(i-1);
                    faith=faith+card.activateProduction(production, strongbox, deposits, output);
                }
            }
        }
        personalBoard.setDeposits(deposits);
        personalBoard.setStrongbox(strongbox);
        personalBoard.addToStrongbox(output);
        FaithMarker fm=personalBoard.getFaithMarker();
        fm.setPosition(fm.getPosition()+faith);
    }

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
