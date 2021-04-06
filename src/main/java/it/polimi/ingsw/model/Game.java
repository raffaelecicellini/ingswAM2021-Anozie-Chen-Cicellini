package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    /**
     * Players that are in the Game
     */
    private ArrayList<Player> players;
    private int current;
    /**
     * Current Active Players in the Game
     */
    private ArrayList<Player> activePlayers;

    /**
     * Current Player (has the turn)
     */
    private Player currentPlayer;

    /**
     * First Player
     */
    private Player firstPlayer;

    /**
     * defines if a Game is over
     */
    private boolean isEndGame;

    /**
     * the Game's Market
     */
    private Market market;

    /**
     * the Game's Develop Decks
     */
    private DevelopDeck[][] developDecks;

    /**
     * the Game's Leader Deck
     */
    private LeaderDeck leaderDeck;

    /**
     * this attribute establishes if a Player has already done a mandatory action
     */
    private boolean doneMandatory;

    /**
     * this attribute counts how many Leader actions a Player has done
     */
    private int doneLeader;

    /**
     * this attribute establishes if a Game is in a Single Player Mode or not
     */
    private boolean isSolo;

    /**
     * this attribute represents Lorenzo il Magnifico in a Single Player Game
     */
    private FaithMarker blackCross;

    /**
     * Solo Actions used in a Single Player Game
     */
    private SoloActions soloActions;

    private boolean isFirstTurn;

    public Game(){
        this.players= new ArrayList<>();
        this.activePlayers= new ArrayList<>();
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
    private Color parseColor(int col){
        switch (col){
            case 0: return Color.GREEN;
            case 1: return Color.BLUE;
            case 2: return Color.YELLOW;
            case 3: return Color.PURPLE;
        }
        return null;
    }

    public void start(){

    }

    private void chooseFirst(){

    }

    public void createPlayer(String name){
        Player player= new Player(name);
        players.add(player);
        activePlayers.add(player);
    }

    public void produce(String player, Map<String, String> info) throws InvalidActionException {
        if (currentPlayer.getName().equals(player) && !doneMandatory) {
            currentPlayer.produce(info);
            doneMandatory=true;
        }
        else if (doneMandatory) throw new InvalidActionException("You have already done a mandatory action!");
        else throw new InvalidActionException("It is not your turn!");
    }

    public void activateLeader(String player, int pos) throws InvalidActionException{
        if (currentPlayer.getName().equals(player) && doneLeader<2) {
            currentPlayer.activateLeader(pos);
            doneLeader++;
        }
        else if (doneLeader==2) throw new InvalidActionException("You can't activate another leader");
        else throw new InvalidActionException("It is not your turn!");
    }

    public void discardLeader(String player, int pos) throws InvalidActionException{
        if (currentPlayer.getName().equals(player) && doneLeader<2) {
            currentPlayer.discardLeader(pos);
            doneLeader++;
        }
        else if (doneLeader==2) throw new InvalidActionException("You can't discard another leader");
        else throw new InvalidActionException("It is not your turn!");
    }

    public void endTurn(){
        //select max position and set the tiles if needed: if someone is at the end, set isEndgame
        int max=0;
        int curr;
        boolean exit=false;
        Player maxPlayer=null;
        for (Player player: activePlayers) {
            curr=player.getPersonalBoard().getFaithMarker().getPosition();
            if (curr>max){
                max=curr;
                maxPlayer=player;
            }
        }
        for (int i=2; i>=0 && !exit && maxPlayer!=null; i--) {
            FavorTile tile=maxPlayer.getPersonalBoard().getTile(i);
            if (max>=tile.getEnd() && !tile.isActive() && !tile.isDiscarded()){
                exit=true;
                tile.setActive(true);
                this.setTiles(maxPlayer, i);
                if (i==2){
                    this.isEndGame=true;
                }
            }
        }
        //check endGame and nextPlayer: if true and =first, count points e select winner; else go on
        if (current==activePlayers.size()-1){
            current=0;
            if (isFirstTurn) isFirstTurn=false;
        }
        else current++;
        currentPlayer=activePlayers.get(current);
        doneLeader=0;
        doneMandatory=false;

        if (isEndGame && currentPlayer.getName().equals(firstPlayer.getName())){
            //count points
            int maxpoints=0;
            Player winner=null;
            int[] points= new int[activePlayers.size()];
            for (int i=0; i<activePlayers.size(); i++){
                points[i]= getPoints(activePlayers.get(i));
                if (points[i]>maxpoints){
                    maxpoints=points[i];
                    winner=activePlayers.get(i);
                }
                else if (points[i]==maxpoints){
                    winner=winnerByResources(activePlayers.get(i), winner);
                }
            }
            //segnala winner
        }
    }

    private void setTiles(Player max, int pos){
        //CHECK DISCARDED
        for (Player player: activePlayers) {
            if (!player.getName().equals(max.getName())){
                FavorTile tile=player.getPersonalBoard().getTile(pos);
                if (player.getPersonalBoard().getPosition()>=tile.getStart() && !tile.isDiscarded()){
                    tile.setActive(true);
                }
                else tile.setDiscarded(true);
            }
        }
    }

    private int getPoints(Player player){
        int points=0;
        PersonalBoard pb= player.getPersonalBoard();
        DevelopCard[] slot;
        for (int i=0; i<3; i++){
            try {
                slot=pb.getSlot(i);
                for (DevelopCard card: slot) {
                    points=points+card.getVictoryPoints();
                }
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }
        points=points+ pb.getCell(pb.getPosition()).getVictoryPoints();
        for (int i=0; i<3; i++){
            if (pb.getTileState(i)){
                points=points+pb.getTile(i).getVictoryPoints();
            }
        }
        for (LeaderCard leader: player.leaders) {
            if(leader.isActive()) points=points+leader.getVictoryPoints();
        }
        int res=0;
        ArrayList<ResourceAmount> deps=pb.getDeposits();
        ResourceAmount[] strongbox=pb.getStrongbox();
        for (ResourceAmount dep: deps) {
            res=res+dep.getAmount();
        }
        for(ResourceAmount x: strongbox){
            res=res+x.getAmount();
        }
        res=res%5;
        points=points+res;
        return points;
    }

    private Player winnerByResources(Player p1, Player p2){
        int res1=0;
        int res2=0;
        if (p2!=null){
            PersonalBoard pb1=p1.getPersonalBoard();
            PersonalBoard pb2=p2.getPersonalBoard();

            ArrayList<ResourceAmount> deps=pb1.getDeposits();
            ResourceAmount[] strongbox=pb1.getStrongbox();
            for (ResourceAmount dep: deps) {
                res1=res1+dep.getAmount();
            }
            for(ResourceAmount x: strongbox){
                res1=res1+x.getAmount();
            }

            deps=pb2.getDeposits();
            strongbox=pb2.getStrongbox();
            for (ResourceAmount dep: deps) {
                res2=res2+dep.getAmount();
            }
            for(ResourceAmount x: strongbox){
                res2=res2+x.getAmount();
            }

            return res1>res2? p1 : p2;
        }
        else return p1;
    }

    /**
     * This method is used for a chooseInitialResource move at the start of the game.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException is the move is not valid.
     */
    public void chooseInitialResource(String player,Map<String, String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.chooseInitialResource(map);
        else throw new InvalidActionException("It is not your turn!");
    }

    /**
     * This method is used for a buy move during the turn.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     * @throws NumberFormatException if the format is not valid.
     */
    public void buy(String player, Map<String, String> map) throws InvalidActionException, NumberFormatException {
        if (!currentPlayer.getName().equals(player)) throw new InvalidActionException("It is not your turn!");
        if (doneMandatory) throw new InvalidActionException("you have already done a mandatory operation in this turn.");
        if(map.get("row")==null || map.get("column")==null) throw new InvalidActionException("you didn't select the card.");
        int row = Integer.parseInt(map.get("row"));
        int column = Integer.parseInt(map.get("column"));
        if (row<0 || row>4 || column<0 || column>3) throw new InvalidActionException("wrong indexes selected ");
        boolean end;
        DevelopCard card = developDecks[row][column].getCard();
        end = currentPlayer.buy(map, card);
        developDecks[row][column].removeCard();
        if (end)
            isEndGame = true;
        doneMandatory = true;
    }

    /**
     * This method is used for a swapDeposit move during the turn.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     */
    public void swapDeposit(String player, Map<String,String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) currentPlayer.swapDeposit(map);
        else throw new InvalidActionException("It is not your turn!");
    }

}
