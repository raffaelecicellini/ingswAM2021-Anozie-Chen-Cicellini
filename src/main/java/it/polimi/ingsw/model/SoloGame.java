package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class SoloGame extends Game{

    private Player player;

    /**
     * this attribute represents Lorenzo il Magnifico in a Single Player Game
     */
    private FaithMarker blackCross;

    /**
     * Solo Actions used in a Single Player Game
     */
    private SoloActions soloActions;

    @Override
    public void createPlayer(String name) {
        player = new Player(name);
    }

    public SoloGame() {
        soloActions = new SoloActions();
        this.market= new Market();
        this.leaderDeck= new LeaderDeck();
        this.developDecks= new DevelopDeck[4][3];
        Color color;
        for (int col=0; col<4; col++){
            color= parseColor(col);
            for (int row=0; row<3; row++){
                this.developDecks[col][row]= new DevelopDeck(row+1, color);
            }
        }
        this.isEndGame=false;
    }

    @Override
    public void start() {
        blackCross = new FaithMarker(0);
        //receive initial leaders
        LeaderCard card;
        ArrayList<LeaderCard> cards= new ArrayList<>();
        for (int i=0; i<4; i++){
            card=leaderDeck.removeCard();
            cards.add(card);
        }
        player.receiveLeaders(cards);

        isEndGame=false;
    }

    public void buy(Map<String, String> map) throws InvalidActionException, NumberFormatException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory operation in this turn.");
        if(map.get("row")==null || map.get("column")==null) throw new InvalidActionException("You didn't select the card.");
        int row = Integer.parseInt(map.get("row"));
        int column = Integer.parseInt(map.get("column"));
        if (row<0 || row>2 || column<0 || column>3) throw new InvalidActionException("Wrong indexes selected ");
        boolean end;
        DevelopCard card = developDecks[column][row].getCard();
        end = player.buy(map, card);
        developDecks[column][row].removeCard();
        if (end)
            isEndGame = true;
        doneMandatory = true;
    }

    public void produce(Map<String, String> info) throws InvalidActionException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory operation in this turn.");
        player.produce(info);
        doneMandatory=true;
    }

    public void fromMarket(Map<String, String> map) throws InvalidActionException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory action in this turn!");

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        if (mapCopy.containsKey("row")) {
            int row = Integer.parseInt(mapCopy.get("row"));
            if (row >= 1 && row <= 3) {
                mapCopy.remove("row");
                player.fromMarket(mapCopy, market.selectRow(row - 1));
                doneMandatory = true;
            } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for row!");
        } else if (mapCopy.containsKey("col")) {
            int col = Integer.parseInt(mapCopy.get("col"));
            if (col >= 1 && col <= 4) {
                mapCopy.remove("col");
                player.fromMarket(map, market.selectColumn(col - 1));
                doneMandatory = true;
            } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for col!");

        } else throw new InvalidActionException("Invalid action! You didn't insert \"row\" or \"col\" correctly!");
    }

    public void swapDeposit(Map<String,String> map) throws InvalidActionException {
        player.swapDeposit(map);
    }

    public void activateLeader(int pos) throws InvalidActionException{
        if (doneLeader<2) {
            player.activateLeader(pos);
            doneLeader++;
        } else if (doneLeader==2) throw new InvalidActionException("You can't activate another leader");
    }

    public void discardLeader(int pos) throws InvalidActionException{
        if (doneLeader<2) {
            player.discardLeader(pos);
            doneLeader++;
        }
        else if (doneLeader==2) throw new InvalidActionException("You can't discard another leader");
    }

    public void endTurn() throws InvalidActionException{

     if (!doneMandatory) throw new InvalidActionException("You have to do a mandatory action (buy a DevelopCard, activate production or take resources from market)");

     soloActions.doAction(blackCross, developDecks);

     if (blackCross.getPosition()>=24) { System.out.println("You lost!"); }
     else if (player.getPersonalBoard().getPosition()>=24) {
         System.out.println("You won! You made " + getPoints(player) + " points!");
     }

     boolean done = false;
     int i = 0;
     while (i < 3 && !done){
         if (developDecks[i][2].getTop() == -1) done = true;
         else i++;
     }
     if (done) System.out.println("You lost!");


     //select max position and set the tiles if needed: if someone is at the end, set isEndgame
        if (blackCross.getPosition() > player.getPersonalBoard().getPosition()){
            /*if (blackCross.getPosition()>=player.getPersonalBoard().getTile(blackCross.getPosition()).getEnd() &&
                    !player.getPersonalBoard().getTile(blackCross.getPosition()).isActive() &&
                    !player.getPersonalBoard().getTile(blackCross.getPosition()).isDiscarded()) {*/
            for (int j=2; j>=0; j--) {
                FavorTile tile = player.getPersonalBoard().getTile(j);
                if (blackCross.getPosition() >= tile.getEnd() && !tile.isActive() && !tile.isDiscarded()) {
                    tile.setActive(true);
                    if (j == 2) {
                        isEndGame = true;
                    }
                }
            }
            //}
        } else {
            for (int j=2; j>=0; j--) {
                FavorTile tile = player.getPersonalBoard().getTile(j);
                if (player.getPersonalBoard().getPosition() >= tile.getEnd() && !tile.isActive() && !tile.isDiscarded()) {
                    tile.setActive(true);
                    if (j == 2) {
                        isEndGame = true;
                    }
                }
            }
        }

        doneLeader=0;
        doneMandatory=false;

        //check endGame and nextPlayer: if true and =first, count points e select winner; else go on
        if (isEndGame){
            //segnala winner
        }
    }
}
