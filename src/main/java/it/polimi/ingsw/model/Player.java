package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class represents a Player of the game "Maestri del Rinascimento". Each client connected to the game is represented
 * by a Player instance. It contains an instance of PersonalBoard, that has the informations about the deposits and strongbox
 * of the player and about the DevelopCards that he has.
 */
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

    /**
     * Constructor of the Player's class.
     * @param name the name of the new player
     */
    public Player(String name){
        this.name=name;
        this.personalBoard= new PersonalBoard();
        this.leaders= new ArrayList<>();
    }

    public ArrayList<LeaderCard> getLeaders() {
        return leaders;
    }

    public int getNumberInitialResource() {
        return numberInitialResource;
    }

    public int getNumberDevelopCards() {
        return numberDevelopCards;
    }

    /**
     * Simple method that returns the name of the Player.
     * @return the name of the Player
     */
    public String getName(){
        return this.name;
    }

    /**
     * Simple method that returns a reference to the Player's PersonalBoard.
     * @return the player's PersonalBoard
     */
    public PersonalBoard getPersonalBoard(){
        return this.personalBoard;
    }

    /**
     * This method is used when the player wants to discard a leader.
     * @param pos the position of the leader to discard in the ArrayList
     * @throws InvalidActionException when the position is wrong or when the selected leader is already active (so it can't
     * be discarded)
     */
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

    /**
     * This method is used when the player wants to activate a leader.
     * @param pos the position of the leader to activate in the ArrayList
     * @throws InvalidActionException when the position is wrong or when the selected leader is already discarded(so it
     * can't be activated). Also, it can be thrown if the player doesn't have the needed requirements to activate the
     * selected leader
     */
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

    /**
     * This method is used when a player wants to activate production.
     * @param info this map contains the info about the productions that the player wants to activate (such as what productions
     *             he wants to activate, where to take the resources, etc.)
     * @throws InvalidActionException when the player selected a DevelopCard slot that does not contain a card or if one
     * of the chosen productions can't be executed (no resources, wrong positions from where to take them, etc.)
     */
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
     * This method is used inside the produce() method when a player wants to activate the base production (printed on the
     * personal board in the physical game). It removes the selected input resources from the selected places and adds a
     * selected output resource in the outputStrongbox
     * @param info this map contains the info about the selected resources and the place from where to take them
     * @param inputStrongbox this is the strongbox where to retrieve the resources if needed
     * @param deps these are the deposits where to retrieve the resources if needed
     * @param outputStrongbox this is the strongbox where to put the produced resource
     * @throws InvalidActionException
     */
    private void baseProduction(Map<String, String> info, ResourceAmount[] inputStrongbox, ArrayList<ResourceAmount> deps, ResourceAmount[] outputStrongbox)
            throws InvalidActionException{
        int index;
        String pos;
        String type;
        //pay the price
        for (int i=0; i<2; i++){
            pos="pos"+(i+1);
            type="in"+(i+1);
            index= parseChoice(info.get(pos));
            if (index==5){
                boolean found=false;
                //strongbox
                for (ResourceAmount res: inputStrongbox){
                    if (res.getColor().toString().toLowerCase().equals(info.get(type).toLowerCase()) && res.getAmount()>0){
                        found=true;
                        res.setAmount(res.getAmount()-1);
                    }
                }
                if (!found) throw new InvalidActionException("The resource is not in the specified position (strongbox)");
            }
            else if (index>=0 && index<=4){
                if (deps.size()>index){
                    ResourceAmount dep= deps.get(index);
                    if (dep.getColor().toString().toLowerCase().equals(info.get(type).toLowerCase()) && dep.getAmount()>0){
                        dep.setAmount(dep.getAmount()-1);
                    }
                    else throw new InvalidActionException("The resource is not in the specified position ("+info.get(pos)+")");
                }
                else throw new InvalidActionException("You don't have a leader deposit!");
            }
            else throw new InvalidActionException("Wrong position!");
        }
        for (ResourceAmount res: outputStrongbox){
            if (res.getColor().toString().toLowerCase().equals(info.get("out").toLowerCase())) res.setAmount(res.getAmount()+1);
        }
    }

    /**
     * Simple utility method used in baseProduction() to retrieve the index of a deposit (or the strongbox) correspondent
     * to the selected place.
     * @param chosen the place (deposit/strongbox) from where to take the resources
     * @return the index correspondent to the selected place
     */
    private int parseChoice(String chosen){
        switch (chosen.toLowerCase()){
            case "small": return 0;
            case "mid": return 1;
            case "big": return 2;
            case "sp1": return 3;
            case "sp2": return 4;
            case "strongbox": return 5;
        }
        return -1;
    }

    /**
     * This method increases the player's initial position.
     * @param faith is the player's initial faith.
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
        int pos1 = parseChoice(map.get("pos1"));
        int pos2 = parseChoice(map.get("pos2"));
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
                            deposits.get(pos2).setAmount(deposits.get(pos2).getAmount() + 1);
                        } else throw new InvalidActionException("you can't place your resources in this way.");
                    else if (pos1 == pos2)
                        deposits.get(pos1).setAmount(deposits.get(pos1).getAmount() + 1);
                    else throw new InvalidActionException("you cant place your resources in this way.");
            }
        }
        this.personalBoard.setDeposits(deposits);
    }

    /**
     * This method is used for assigning 4 Leader Cards to the Player.
     * @param leaderCards is the arraylist of LeaderCards
     */
    public void receiveLeaders(ArrayList<LeaderCard> leaderCards){
        leaders.addAll(leaderCards);
    }

    /**
     * This method is used to let the Player choose which Leader Cards he wants to keep.
     * @param leader1 is the index of the first Leader Card
     * @param leader2 is the index of the second Leader Card
     * @throws InvalidActionException
     */
    public void chooseLeader(int leader1, int leader2) throws InvalidActionException {
        if (leader1 != leader2 && leader1 >= 1 && leader1 <= leaders.size() && leader2 >= 1 && leader2 <= leaders.size()) {
            ArrayList<LeaderCard> temp = new ArrayList<LeaderCard>();
            temp.add(leaders.get(leader1 - 1));
            temp.add(leaders.get(leader2 - 1));
            leaders = temp;
        } else throw new InvalidActionException("Invalid action! Try typing two different indexes [1-4]!");
    }


    /**
     * This method is used for swapping deposits.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     */
    public void swapDeposit(Map<String,String> map) throws InvalidActionException {
        if (map.size()!= 2) throw new InvalidActionException("select the source and the destination");
        String source = map.get("source");
        String dest = map.get("dest");
        if(parseChoice(source)==5 || parseChoice(dest)==5) throw new InvalidActionException("you can't select the strongbox");
        if(parseChoice(source)==-1 || parseChoice(dest)==-1) throw new InvalidActionException("select the source and the destination");
        personalBoard.swapDeposits(source,dest);
    }

    /**
     * This method is used for buying a card.
     * @param map is where the instructions are stored.
     * @param card is the card to buy.
     * @return if the played bought the seventh card.
     * @throws InvalidActionException if the move is not valid.
     * @throws NumberFormatException if the input format is not valid.
     */
    public boolean buy (Map<String, String> map, DevelopCard card) throws InvalidActionException, NumberFormatException {
        ArrayList<ResourceAmount> deposit = personalBoard.getDeposits();
        ResourceAmount[] strongbox = personalBoard.getStrongbox();
        int ind = Integer.parseInt(map.get("ind"));
        map.remove("row");
        map.remove("column");
        map.remove("ind");
        if(ind<0 || ind>2) throw new InvalidActionException("Wrong card index");
        if (personalBoard.getTopCard(ind) == null)
            if (card.getLevel()==1)
                card.buyDevelopCard(map,strongbox,deposit);
            else
                throw new InvalidActionException("You can't place this card here.");
        else if (personalBoard.getTopCard(ind).getLevel() == card.getLevel()-1)
            card.buyDevelopCard(map,strongbox,deposit);
        else
            throw new InvalidActionException("You can't place this card here.");
        this.personalBoard.addCard(ind,card,false);
        this.personalBoard.setStrongbox(strongbox);
        this.personalBoard.setDeposits(deposit);
        this.numberDevelopCards++;
        return numberDevelopCards == 7;
    }

    public void fromMarket(Map<String, String> map, Marble[] marbles) {

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

}