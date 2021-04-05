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
            if (res.getColor().toString().toLowerCase().equals(info.get("out"))) res.setAmount(res.getAmount()+1);
        }
    }

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
}
