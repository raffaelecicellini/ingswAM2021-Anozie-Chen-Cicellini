package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.List;

public class PersonalBoard {
    /**
     * Player's personal Faith Track
     */
    private Cell[] faithTrack;
    /**
     * Player's personal Strongbox
     */
    private ResourceAmount[] strongbox;
    /**
     * Player's personal Deposits
     */
    private List<ResourceAmount> deposits;
    /**
     * Player's personal slot to store the bought DevelopCards
     */
    private List<DevelopCard[]> slots;
    /**
     * Player's personal Faith Marker
     */
    private FaithMarker faithMarker;
    /**
     * Favor Tiles "on" the Faith Track
     */
    private FavorTile[] favorTile;

    /**
     * Constructor of the player's PersonalBoard.
     */
    public PersonalBoard() {
        this.faithMarker= new FaithMarker(0);
        this.favorTile= new FavorTile[3];
        this.favorTile[0] = new FavorTile(2,false,5,8);
        this.favorTile[1] = new FavorTile(3,false,12,16);
        this.favorTile[2] = new FavorTile(4,false,19,24);
        this.slots= new ArrayList<>();
        for (int i=0; i<3; i++){
            this.slots.add(new DevelopCard[3]);
        }
        this.deposits= new ArrayList<>();
        for (int i=0; i<3; i++){
            this.deposits.add(new ResourceAmount(null, 0));
        }
        this.strongbox= new ResourceAmount[4];
        this.strongbox[0]= new ResourceAmount(Color.BLUE, 0);
        this.strongbox[1]= new ResourceAmount(Color.PURPLE, 0);
        this.strongbox[2]= new ResourceAmount(Color.GREY, 0);
        this.strongbox[3]= new ResourceAmount(Color.YELLOW, 0);
        this.faithTrack= new Cell[25];
        for (int i=0; i<25; i++){
            if (i==8 || i==16 || i==24){
                if (i==8) faithTrack[i]= new Cell(2, true);
                else if (i==16) faithTrack[i]= new Cell(9, true);
                else faithTrack[i]= new Cell(20, true);
            } else if (i<=2) faithTrack[i]= new Cell(0, false);
            else if (i>=3 && i<=5) faithTrack[i]= new Cell(1, false);
            else if (i>=6 && i<=8) faithTrack[i]= new Cell(2, false);
            else if (i>=9 && i<=11) faithTrack[i]= new Cell(4, false);
            else if (i>=12 && i<=14) faithTrack[i]= new Cell(6, false);
            else if (i>=15 && i<=17) faithTrack[i]= new Cell(9, false);
            else if (i>=18 && i<=20) faithTrack[i]= new Cell(12, false);
            else if (i>=21 && i<=23) faithTrack[i]= new Cell(16, false);
        }
    }

    /**
     * This method returns the position of the faith marker.
     */
    public int getPosition() {
        return faithMarker.getPosition();
    }

    /**
     *This method changes the faith marker's position.
     */
    public void setPosition(int position) {
        faithMarker.setPosition(position);
    }

    /**
     *This method adds resources to the strongbox
     * @param res is the array of resources.
     */
    public void addToStrongbox(ResourceAmount[] res) {
        for (ResourceAmount x : res)
            for (ResourceAmount y: strongbox)
                if (x.getColor() == y.getColor())
                    y.setAmount(y.getAmount()+x.getAmount());

    }

    /**
     * This method returns a copy of the player's deposits.
     * @return: a copy of the player's deposits
     */
    public ArrayList<ResourceAmount> getDeposits() {
        ArrayList<ResourceAmount> res = new ArrayList<>();
        for (ResourceAmount resource : this.deposits) {
            res.add(new ResourceAmount(resource.getColor(), resource.getAmount()));
        }
        return res;
    }

    /**
     * This method is used to set the new state of the player's deposits.
     * @param newDeps: list of the player's deposits in the new state
     */
    public void setDeposits(ArrayList<ResourceAmount> newDeps) {
        this.deposits = newDeps;
    }

    /**
     * This method is used to swap the contents of two deposits if possible. If one of the two is a leader deposit,
     * the method moves all the resources that can be put in the destination's deposit (if the resources are the same).
     *
     * @param source: the String representation of the first deposit to swap
     * @param dest:   the String representation of the second deposit to swap
     * @throws InvalidActionException when the contents of the two deposits can't be swapped (too many resources for one
     *                                of the two deposits to contain them), when the player tries to put a resource in a leader deposit that is of a different
     *                                kind, when a player tries to swap a leader deposit but he doesn't have it, or when a player tries to swap two leader
     *                                deposits (it cannot be done).
     */
    public void swapDeposits(String source, String dest) throws InvalidActionException {
        int idx1 = parseDepIndex(source);
        int idx2 = parseDepIndex(dest);
        if (idx1 >= 0 && idx2 >= 0 && idx1 != idx2) {
            if (idx1 < 3 && idx2 < 3) {
                if (deposits.get(idx1).getAmount() <= idx2 + 1 && deposits.get(idx2).getAmount() <= idx1 + 1) {
                    ResourceAmount temp = new ResourceAmount(deposits.get(idx1).getColor(), deposits.get(idx1).getAmount());
                    deposits.set(idx1, deposits.get(idx2));
                    deposits.set(idx2, temp);
                } else throw new InvalidActionException("You can't swap these deposits! Too much resource to contain");
            } else if (idx1 >= 3 && idx2 < 3) {
                if (deposits.size() > idx1) {
                    if (deposits.get(idx1).getColor() == deposits.get(idx2).getColor() || deposits.get(idx2).getAmount() == 0) {
                        int amt1, amt2;
                        deposits.get(idx2).setColor(deposits.get(idx1).getColor());
                        while (deposits.get(idx2).getAmount() < idx2 + 1 && deposits.get(idx1).getAmount() > 0) {
                            amt1 = deposits.get(idx1).getAmount() - 1;
                            amt2 = deposits.get(idx2).getAmount() + 1;
                            deposits.get(idx1).setAmount(amt1);
                            deposits.get(idx2).setAmount(amt2);
                        }
                    } else
                        throw new InvalidActionException("You can't swap these deposits! They store different resources");
                } else throw new InvalidActionException("You don't have a leader deposit!");
            } else if (idx1 < 3 && idx2 >= 3) {
                if (deposits.size() > idx2) {
                    if (deposits.get(idx1).getColor() == deposits.get(idx2).getColor()) {
                        int amt1, amt2;
                        while (deposits.get(idx2).getAmount() < 2 && deposits.get(idx1).getAmount() > 0) {
                            amt1 = deposits.get(idx1).getAmount() - 1;
                            amt2 = deposits.get(idx2).getAmount() + 1;
                            deposits.get(idx1).setAmount(amt1);
                            deposits.get(idx2).setAmount(amt2);
                        }
                    }
                } else throw new InvalidActionException("You don't have a leader deposit!");
            } else throw new InvalidActionException("You can't swap two leader deposits!");
        }
        else throw new InvalidActionException("Invalid indexes!");
    }

    /**
     * Utility method use to parse the String representation of the chosen deposit in the correspondent index of the List.
     *
     * @param dep: the String representation to parse
     * @return: the index of the correspondent deposit
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
        }
        return -1;
    }

    /**
     * This method is used when a ResourceLeader is activated. It adds a special deposit to the list.
     * @param color: the color of the resource that the special deposit can contain
     */
    public void addSpecialDeposit(Color color) {
        this.deposits.add(new ResourceAmount(color, 0));
    }

    /**
     * This method is used at the end of the game to check the victoryPoints given by each DevelopCard bought by the Player
     * @param index: the index of the slot to return
     * @return: the slot at the given index
     * @throws InvalidActionException: when the given index is wrong because the player doesn't have a special production
     */
    public DevelopCard[] getSlot(int index) throws InvalidActionException {
        if (index>=0 && index<=2){
            return slots.get(index);
        }
        else if (index>2){
            if (slots.size()>index){
                return slots.get(index);
            }
            else throw new InvalidActionException("You don't have a special production!");
        }
        return null;
    }

    /**
     * This method returns the top card of the selected slot. It is used to check if a card can be put in the slot and
     * when a Player wants to activate the production of the card in the selected slot.
     * @param index: the index of the selected slot from which to get the card
     * @return: the top card of the selected slot (null if the index is out of bound)
     * @throws InvalidActionException: when a Player wants to activate a special production but he doesn't have it.
     */
    public DevelopCard getTopCard(int index) throws InvalidActionException {
        if (index>=0 && index<=2){
            DevelopCard[] current= slots.get(index);
            for (int i=2; i>=0; i--){
                if (current[i]!=null) return current[i];
            }
        }
        else if (index>2){
            if (slots.size()>index){
                return slots.get(index)[0];
            }
            else throw new InvalidActionException("You don't have a special production!");
        }
        return null;
    }

    /**
     * This method adds a card to the selected slot when a Player correctly buys the card or activates a LevTwoLeader.
     * @param index: the index of the selected slot where to put the card
     * @param card: the card bought by the player or the extra production card of the activated leader
     * @param isLeader: used to check if the card is a extra production card of a LevTwoLeader
     */
    public void addCard(int index, DevelopCard card, boolean isLeader) {
        if (!isLeader){
            if (index>=0 && index<=2){
                DevelopCard[] current= slots.get(index);
                for (int i=0; i<3; i++){
                    if (current[i]==null){
                        current[i]=card;
                        slots.set(index, current);
                        return;
                    }
                }
            }
        }
        else {
            DevelopCard[] slot= new DevelopCard[1];
            slot[0]=card;
            slots.add(slot);
        }
    }

    /**
     * This method returns a copy of the strongbox.
     * @return the strongbox.
     */
    public ResourceAmount[] getStrongbox() {
        ResourceAmount[] clone = new ResourceAmount[4];
        for (int i = 0; i < 4; i++)
            clone[i] = new ResourceAmount(strongbox[i].getColor(),strongbox[i].getAmount());
        return clone;
    }

    /**
     * This method changes the strongbox reference.
     * @param strongbox is the new strongbox reference.
     */
    public void setStrongbox(ResourceAmount[] strongbox) {
        this.strongbox = strongbox;
    }

    /**
     * This method returns the Cell at that index.
     * @param pos is the index.
     * @return the Cell at that index.
     */
    public Cell getCell(int pos) {
        return faithTrack[pos];
    }

    /**
     * This method returns the state of a FavorTile at a specific index.
     * @param pos is the index of the FavorTile.
     * @return the state of the FavorTile.
     */
    public boolean getTileState (int pos) {
        return favorTile[pos].isActive();
    }

    /**
     * This method returns the FavorTile in the selected index
     * @param pos is the index of the position in the Faith Track
     * @return the FavorTile
     */
    public FavorTile getTile (int pos){
        return favorTile[pos];
    }
    /**
     * This method changes the status of a FavorTile at a specific index.
     * @param pos is the index of the FavorTile.
     * @param status is the new status of the FavorTile.
     */
    public void setTile (int pos, boolean status) {
        favorTile[pos].setActive(status);
    }

    /**
     * This method returns the FaithMarker.
     * @return the FaithMarker.
     */
    public FaithMarker getFaithMarker() {
        return faithMarker;
    }

    /**
     * This method returns the FavorTile array
     * @return the FavorTile array
     */
    public FavorTile[] getTiles() {
        return favorTile;
    }
}