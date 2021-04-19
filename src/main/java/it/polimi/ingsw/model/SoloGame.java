package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class SoloGame extends Game{

    /**
     * this attribute represents Lorenzo il Magnifico in a Single Player Game
     */
    private FaithMarker blackCross;

    /**
     * Solo Actions used in a Single Player Game
     */
    private SoloActions soloActions;

    /**
     * Used to check the phase of the game (if an action can be done)
     */
    private GamePhase phase;

    /**
     * This method is used when the player joins the game
     * @param name is the Player's name
     */
    @Override
    public void createPlayer(String name) {
        currentPlayer = new Player(name);
    }

    /**
     * Constructor of the SoloGame class. It instantiates the Market, the LeaderDeck and the DevelopDecks for the current game.
     */
    public SoloGame() {
        this.market= new Market();
        this.leaderDeck= new LeaderDeck();
        this.developDecks= new DevelopDeck[4][3];
        for (int col=0; col<4; col++){
            Color color = parseColor(col);
            for (int row=0; row<3; row++){
                this.developDecks[col][row]= new DevelopDeck(row+1, color);
            }
        }
        phase=GamePhase.NOTSTARTED;
    }

    /**
     * This method is called by the controller when the player wants to start the solo game. It gives the leaders to the player (4 cards, in which 2 to be chosen).
     * It positions the blackCross on the Faith Track.
     */
    @Override
    public void start() {
        this.soloActions = new SoloActions();
        blackCross = new FaithMarker(0);
        //receive initial leaders
        LeaderCard card;
        ArrayList<LeaderCard> cards= new ArrayList<>();
        for (int i=0; i<4; i++){
            card=leaderDeck.removeCard();
            cards.add(card);
        }
        currentPlayer.receiveLeaders(cards);
        phase=GamePhase.LEADER;
        //notify the player to choose the leaders
    }


    /**
     * This method is used for a buy move during the turn.
     * @param player is ignored.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     * @throws NumberFormatException if the format is not valid.
     */
    @Override
    public void buy(String player, Map<String, String> map) throws InvalidActionException, NumberFormatException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory operation in this turn.");
        if(map.get("row")==null || map.get("column")==null) throw new InvalidActionException("You didn't select the card.");
        int row = Integer.parseInt(map.get("row"));
        int column = Integer.parseInt(map.get("column"));
        if (row<0 || row>2 || column<0 || column>3) throw new InvalidActionException("Wrong indexes selected ");

        DevelopCard card = developDecks[column][row].getCard();
        isEndGame = currentPlayer.buy(map, card);
        developDecks[column][row].removeCard();
        doneMandatory = true;
        if (isEndGame) {
            System.out.println("You won! You made " + getPoints(currentPlayer) + " points!");
        }
    }

    /**
     * This method is called by the controller when the player decided to activate productions. It calls the player's produce method.
     * @param player is ignored
     * @param info this map contains the info about all the productions that the player wants to activate
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void produce(String player, Map<String, String> info) throws InvalidActionException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory operation in this turn.");
        currentPlayer.produce(info);
        doneMandatory=true;
    }

    /**
     * This method is used for taking resources from the Market
     * @param player is ignored
     * @param map is the map with the information
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void fromMarket(String player, Map<String, String> map) throws InvalidActionException {
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory action in this turn!");

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        if (mapCopy.containsKey("row")) {
            int row = Integer.parseInt(mapCopy.get("row"));
            if (row >= 1 && row <= 3) {
                mapCopy.remove("row");
                int discarded = currentPlayer.fromMarket(mapCopy, market.selectRow(row - 1));
                market.pushRow(row-1);
                blackCross.setPosition(blackCross.getPosition()+discarded);
                doneMandatory = true;
            } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for row!");
        } else if (mapCopy.containsKey("col")) {
            int col = Integer.parseInt(mapCopy.get("col"));
            if (col >= 1 && col <= 4) {
                mapCopy.remove("col");
                int discarded = currentPlayer.fromMarket(map, market.selectColumn(col - 1));
                market.pushColumn(col-1);
                blackCross.setPosition(blackCross.getPosition()+discarded);
                doneMandatory = true;
            } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for col!");

        } else throw new InvalidActionException("Invalid action! You didn't insert \"row\" or \"col\" correctly!");
    }

    /**
     * This method is used for a swapDeposit move during the turn.
     * @param player is ignored
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void swapDeposit(String player, Map<String,String> map) throws InvalidActionException {
        currentPlayer.swapDeposit(map);
    }

    /**
     * This method is called when the player chooses his leaders.
     * @param player is ignored
     * @param map this map contains the indexes of the two leaders chosen
     * @throws InvalidActionException when one or both indexes are missing
     */
    @Override
    public void chooseLeaders(String player, Map<String, String> map) throws InvalidActionException{
        int leader1, leader2;
        if (map.containsKey("ind1") && map.containsKey("ind2")){
            leader1=Integer.parseInt(map.get("ind1"));
            leader2=Integer.parseInt(map.get("ind2"));
            currentPlayer.chooseLeader(leader1, leader2);
        }
        else throw new InvalidActionException("Missing parameters!");
        phase=GamePhase.FULLGAME;
        //notify the player that it is his turn
    }

    /**
     * This method is called by the controller when the player decided to activate a leader. It calls the player's activateLeader method.
     * @param player is ignored
     * @param pos it represents the leader that the player wants to activate
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void activateLeader(String player, int pos) throws InvalidActionException{
        if (doneLeader<2) {
            currentPlayer.activateLeader(pos);
            doneLeader++;
        } else if (doneLeader==2) throw new InvalidActionException("You can't activate another leader");
    }

    /**
     *  This method is called by the controller when the player decided to discard a leader. It calls the player's discardLeader method.
     * @param player is ignored
     * @param pos it represents the leader that the player wants to discard
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void discardLeader(String player, int pos) throws InvalidActionException{
        if (doneLeader<2) {
            currentPlayer.discardLeader(pos);
            doneLeader++;
        }
        else if (doneLeader==2) throw new InvalidActionException("You can't discard another leader");
    }

    /**
     * This method is called by the controller when the player decided to end his turn. It activates the Pope's reports if needed (setting or
     * discarding the FavorTiles of each player) and controls if the game is at the end: if so, if the player won, it counts his points notify
     * that he is the winner; otherwise, it notifies that he lost.
     * @param player is ignored
     * @throws InvalidActionException if the move is not valid.
     */
    @Override
    public void endTurn(String player) throws InvalidActionException{

     if (!doneMandatory) throw new InvalidActionException("You have to do a mandatory action (buy a DevelopCard, activate production or take resources from market)");


     if (!isEndGame) {

         soloActions.doAction(blackCross, developDecks);

         doneLeader = 0;
         doneMandatory = false;

         int i = 0;
         while (i < 3 && !isEndGame) {
             if (developDecks[i][2].getTop() == -1) isEndGame = true;
             else i++;
         }
         if (isEndGame) System.out.println("You lost!");


         //select max position and set the tiles if needed: if someone is at the end, set isEndgame

         if (blackCross.getPosition() > currentPlayer.getPersonalBoard().getPosition()) {
             if (blackCross.getPosition() >= currentPlayer.getPersonalBoard().getTile(2).getEnd()) {
                 isEndGame = true;
                 System.out.println("You lost!");
             }
             for (int j = 1; j >= 0; j--) {
                 FavorTile tile = currentPlayer.getPersonalBoard().getTile(j);
                 if (blackCross.getPosition() >= tile.getEnd() && !tile.isActive() && !tile.isDiscarded()) {
                     if (currentPlayer.getPersonalBoard().getPosition() >= tile.getStart()) {
                         if (!tile.isDiscarded()) {
                             tile.setActive(true);
                         } else tile.setDiscarded(true);
                     }
                 }
             }
         } else {
             for (int j = 1; j >= 0; j--) {
             FavorTile tile = currentPlayer.getPersonalBoard().getTile(j);
             if (currentPlayer.getPersonalBoard().getPosition() >= tile.getEnd() && !tile.isActive() && !tile.isDiscarded()) {
                 if (currentPlayer.getPersonalBoard().getPosition() >= tile.getStart()) {
                     if (!tile.isDiscarded()) {
                         tile.setActive(true);
                     } else tile.setDiscarded(true);
                 }
             }
         }
             if (currentPlayer.getPersonalBoard().getPosition() >= currentPlayer.getPersonalBoard().getTile(2).getEnd()) {
                 isEndGame = true;
                 System.out.println("You won! You made " + getPoints(currentPlayer) + " points!");
             }

         }

     }

    }

    /**
     * Method to get the black cross (JUST FOR TESTING)
     * @return the blackCross
     */
    protected FaithMarker getBlackCross() {
        return blackCross;
    }

    /**
     * Just for testing
     */
    protected void printSoloActions() {
        System.out.println(soloActions);
    }

    /**
     * Just for testing
     * @param soloActions soloActions
     */
    protected void setSoloActions(SoloActions soloActions) {
        this.soloActions = soloActions;
    }
}
