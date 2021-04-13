package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {
    /**
     * Players that are in the Game
     */
    private ArrayList<Player> players;

    /**
     * The index of the current Player
     */
    private int current;

    /**
     * Current Active Players in the Game
     */
    private ArrayList<Player> activePlayers;

    /**
     * Current Player (has the turn)
     */
    protected Player currentPlayer;

    /**
     * First Player
     */
    private Player firstPlayer;

    /**
     * defines if a Game is over
     */
    protected boolean isEndGame;

    /**
     * the Game's Market
     */
    protected Market market;

    /**
     * the Game's Develop Decks
     */
    protected DevelopDeck[][] developDecks;

    /**
     * the Game's Leader Deck
     */
    protected LeaderDeck leaderDeck;

    /**
     * this attribute establishes if a Player has already done a mandatory action
     */
    protected boolean doneMandatory;

    /**
     * this attribute counts how many Leader actions a Player has done
     */
    protected int doneLeader;

    /**
     * Used to check the phase of the game (if an action can be done)
     */
    private GamePhase phase;

    /**
     * Constructor of the Game class. It instantiates the Market, the LeaderDeck and the DevelopDecks for the current game.
     */
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
        this.phase=GamePhase.NOTSTARTED;
    }

    /**
     * Simple method that returns the current GamePhase.
     * @return the current GamePhase
     */
    public GamePhase getPhase(){
        return this.phase;
    }

    /**
     * This method returns the players in the current game.
     * @return the list of Player
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * This method returns the active players in the current game.
     * @return the list of ActivePlayer
     */
    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * This method returns the current player.
     * @return the currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method returns if the mandatory action has been done
     * @return true if the Mandatory Action has already been done
     */
    public boolean isDoneMandatory() {
        return doneMandatory;
    }

    /**
     * Simple utility method used in Game's contructor to instantiate the DevelopDeck of a certain color
     * @param col is the index of the column correspondent to a color
     * @return the color correspondent to col
     */
    protected Color parseColor(int col){
        switch (col){
            case 0: return Color.GREEN;
            case 1: return Color.BLUE;
            case 2: return Color.YELLOW;
            case 3: return Color.PURPLE;
        }
        return null;
    }

    /**
     * This method is called by the controller when the last player is connected to the game. It shuffles the list of
     * activePlayers to select the first one. Then gives the leaders to each player (4 cards, in which 2 to be chosen) and the
     * initial resources and faith to each player if they have to.
     */
    public void start(){
        //choose first player
        Collections.shuffle(this.activePlayers);
        firstPlayer=activePlayers.get(0);
        currentPlayer=activePlayers.get(0);
        current=0;

        //receive initial leaders
        LeaderCard card;
        for (Player p: this.activePlayers) {
            ArrayList<LeaderCard> cards= new ArrayList<>();
            for (int i=0; i<4; i++){
                card=leaderDeck.removeCard();
                cards.add(card);
            }
            p.receiveLeaders(cards);
        }

        //receive initial faith and resources
        for (int i=0; i<activePlayers.size(); i++){
            if (i==1){
                activePlayers.get(i).receiveInitialResource(1);
            }
            else if (i>1){
                activePlayers.get(i).receiveInitialResource(i-1);
                activePlayers.get(i).receiveInitialFaith(1);
            }
        }

        isEndGame=false;
        phase=GamePhase.LEADER;
        //notify the first player to choose the leaders
    }

    /**
     * This method is used when a player joins the game
     * @param name is the Player's name
     */
    public void createPlayer(String name){
        Player player= new Player(name);
        players.add(player);
        activePlayers.add(player);
    }

    /**
     * This method is called by the controller when a client decided to activate productions. It checks if the player who wants
     * to do the action is the current one (if it is his turn), then calls the player's produce method.
     * @param player is the player who wants to do the action
     * @param info this map contains the info about all the productions that the player wants to activate
     * @throws InvalidActionException when it is not the turn of the player who wants to act, when he already did a mandatory action
     * or when the one of the productions was invalid (no resources or wrong positions from where to take them)
     */
    public void produce(String player, Map<String, String> info) throws InvalidActionException {
        if (currentPlayer.getName().equals(player) && !doneMandatory) {
            currentPlayer.produce(info);
            doneMandatory=true;
        }
        else if (currentPlayer.getName().equals(player) && doneMandatory) throw new InvalidActionException("You have already done a mandatory action!");
        else throw new InvalidActionException("It is not your turn!");
    }

    /**
     * This method is called by the controller when a client decided to activate a leader. It checks if the player who wants
     * to do the action is the current one (if it is his turn), then calls the player's activateLeader method.
     * @param player is the player who wants to do the action
     * @param pos it represents the leader that the player wants to activate
     * @throws InvalidActionException when it is not the turn of the player, when he already activated/discarded his leaders,
     * when he doesn't respect the requirements of the selected leader.
     */
    public void activateLeader(String player, int pos) throws InvalidActionException{
        if (currentPlayer.getName().equals(player) && doneLeader<2) {
            currentPlayer.activateLeader(pos);
            doneLeader++;
        }
        else if (currentPlayer.getName().equals(player) && doneLeader==2) throw new InvalidActionException("You can't activate another leader");
        else throw new InvalidActionException("It is not your turn!");
    }

    /**
     * This method is called by the controller when a client decided to discard a leader. It checks if the player who wants
     * to do the action is the current one (if it is his turn), then calls the player's discardLeader method.
     * @param player is the player who wants to do the action
     * @param pos it represents the leader that the player wants to discard
     * @throws InvalidActionException when it is not the turn of the player, when he already activated/discarded his leaders.
     */
    public void discardLeader(String player, int pos) throws InvalidActionException{
        if (currentPlayer.getName().equals(player) && doneLeader<2) {
            currentPlayer.discardLeader(pos);
            doneLeader++;
        }
        else if (currentPlayer.getName().equals(player) && doneLeader==2) throw new InvalidActionException("You can't discard another leader");
        else throw new InvalidActionException("It is not your turn!");
    }

    /**
     * This method is called by the controller when a client decided to end his turn. It checks if the player who wants
     * to do the action is the current one (if it is his turn), then it activates the Pope's reports if needed (setting or
     * discarding the FavorTiles of each player) and controls if the game is at the end: if so, it counts the points of
     * each player and selects the winner; otherwise, it chooses the next player that can do actions (currentPlayer).
     * @param player is the player who wants to end the turn
     * @throws InvalidActionException when it is not the turn of the player
     */
    public void endTurn(String player) throws InvalidActionException {
        if(!currentPlayer.getName().equals(player)) throw new InvalidActionException("It is not your turn!");
        else if (!doneMandatory) throw new InvalidActionException("You have to do a mandatory action (buy a DevelopCard, activate production or take resources from market)");

        //select max position and set the tiles if needed: if someone is at the end, set isEndgame
        int max=0;
        int curr;
        boolean exit=false;
        Player maxPlayer=null;
        for (Player p: activePlayers) {
            curr=p.getPersonalBoard().getFaithMarker().getPosition();
            if (curr>max){
                max=curr;
                maxPlayer=p;
            }
        }
        for (int i=2; i>=0 && !exit && maxPlayer!=null && !isEndGame; i--) {
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
        }
        else current++;
        currentPlayer=activePlayers.get(current);
        doneLeader=0;
        doneMandatory=false;

        if (isEndGame && currentPlayer.getName().equals(firstPlayer.getName())){
            //count points
            int maxpoints=0;
            int currpoints;
            Player winner=null;
            int[] points= new int[activePlayers.size()];
            for (int i=0; i<activePlayers.size(); i++){
                points[i]= getPoints(activePlayers.get(i));
                //System.out.println("Points of "+activePlayers.get(i).getName()+": "+points[i]);
                currpoints= points[i];
                if (currpoints>maxpoints){
                    maxpoints=points[i];
                    winner=activePlayers.get(i);
                }
                else if (currpoints==maxpoints){
                    winner=winnerByResources(activePlayers.get(i), winner);
                }
            }
            //segnala winner
            assert winner != null;
            System.out.println("The winner is: "+winner.getName());

            this.phase=GamePhase.ENDED;
        }
    }

    /**
     * Utility method used when a player reaches a pope's space on the FaithTrack. It checks the other players' positions
     * and activate/discard their FavorTile depending on their position.
     * @param max it is the player that triggered the action: his FavorTile has already been set in endTurn()
     * @param pos it indicates which of the 3 FavorTiles needs to be activated or discarded for each player
     */
    private void setTiles(Player max, int pos){
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

    /**
     * Utility method used to count the victory points cumulated by a player.
     * @param player the player whose points are going to be calculated
     * @return the number of points of the player passed as a parameter
     */
    protected int getPoints(Player player){
        int points=0;
        PersonalBoard pb= player.getPersonalBoard();
        DevelopCard[] slot;
        for (int i=0; i<3; i++){
            try {
                slot=pb.getSlot(i);
                for (DevelopCard card: slot) {
                    if (card!=null) points=points+card.getVictoryPoints();
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
        res=res/5;
        points=points+res;
        return points;
    }

    /**
     * Utility method called when two players have the same amount of victory points. It chooses the winner based on the
     * amount of resources left for each player.
     * @param p1 the first player
     * @param p2 th second player
     * @return the winner (the player that has more resources)
     */
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
        if (currentPlayer.getName().equals(player)) {
            if (currentPlayer.getNumberInitialResource() != 0)
                currentPlayer.chooseInitialResource(map);
            if (current < activePlayers.size()-1)
                current++;
            currentPlayer = this.activePlayers.get(current);
        }
        else throw new InvalidActionException("It is not your turn!");
        //notify next player, if nextplayer is first change GamePhase to FullGame
    }

    /**
     * This method is called when a player chooses his leaders.
     * @param player the player who wants to do the action
     * @param map this map contains the indexes of the two leaders chosen
     * @throws InvalidActionException when one or both indexes are missing or if it is not the turn of the player.
     */
    public void chooseLeaders(String player, Map<String, String> map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) {
            int leader1, leader2;
            if (map.containsKey("ind1") && map.containsKey("ind2")){
                leader1=Integer.parseInt(map.get("ind1"));
                leader2=Integer.parseInt(map.get("ind2"));
                currentPlayer.chooseLeader(leader1, leader2);
            }
            else throw new InvalidActionException("Missing parameters!");
        }
        else throw new InvalidActionException("It is not your turn!");
        //notify next player. If next is first, change phase to Resource
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
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory operation in this turn.");
        if(map.get("row")==null || map.get("column")==null) throw new InvalidActionException("You didn't select the card.");
        int row = Integer.parseInt(map.get("row"));
        int column = Integer.parseInt(map.get("column"));
        if (row<0 || row>2 || column<0 || column>3) throw new InvalidActionException("Wrong indexes selected ");
        boolean end;
        DevelopCard card = developDecks[column][row].getCard();
        end = currentPlayer.buy(map, card);
        developDecks[column][row].removeCard();
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

    /**
     * This method is used for taking resources from the Market
     * @param player is the Player's name
     * @param map is the map with the information
     * @throws InvalidActionException if the move is not valid
     */
    public void fromMarket(String player, Map<String, String> map) throws InvalidActionException {

        if (currentPlayer.getName().equals(player)){

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
                    for (Player p : players) {
                        if (!p.equals(currentPlayer)) p.getPersonalBoard().setPosition(p.getPersonalBoard().getPosition()+discarded);
                    }
                    doneMandatory = true;
                } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for row!");
            } else
                if (mapCopy.containsKey("col")) {
                    int col = Integer.parseInt(mapCopy.get("col"));
                    if (col >= 1 && col <= 4) {
                        mapCopy.remove("col");
                        int discarded = currentPlayer.fromMarket(map, market.selectColumn(col - 1));
                        market.pushColumn(col-1);
                        for (Player p : players) {
                            if (!p.equals(currentPlayer)) p.getPersonalBoard().setPosition(p.getPersonalBoard().getPosition()+discarded);
                        }
                        doneMandatory = true;
                    } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for col!");

                } else throw new InvalidActionException("Invalid action! You didn't insert \"row\" or \"col\" correctly!");
        }
        else throw new InvalidActionException("It is not your turn!");
    }

    /**
     * Just for testing
     */
    public void printSoloActions(){

    }
}
