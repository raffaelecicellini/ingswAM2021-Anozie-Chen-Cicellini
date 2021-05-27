package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;
import java.util.*;
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
    protected ArrayList<Player> activePlayers;

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
     * this attribute counts how many Leader actions a Player has done.
     */
    protected int doneLeader;

    /**
     * Used to check the phase of the game (if an action can be done)
     */
    private GamePhase phase;
    /**
     * @see Source
     */
    private final Source listener=new Source();

    /**
     * Constructor of the Game class. It instantiates the Market, the LeaderDeck and the DevelopDecks for the current game.
     */
    public Game(){
        this.players= new ArrayList<>();
        this.activePlayers= new ArrayList<>();
        this.market= new Market();
        this.leaderDeck= new LeaderDeck();
        this.developDecks= new DevelopDeck[4][3];
        for (int col=0; col<4; col++){
            Color color = parseColor(col);
            for (int row=0; row<3; row++){
                this.developDecks[col][row]= new DevelopDeck(row+1, color);
            }
        }
        this.isEndGame=false;
        this.phase=GamePhase.NOTSTARTED;
    }

    /**
     * Simple method used to set the view as listener of the model
     * @param view the view object that implements PropertyChangeListener
     */
    public void setListener(SourceListener view){
        this.listener.addListener(view);
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
        //send the initial state (market and developdecks) to all clients
        notifyInitialState();

        //notify the first player to choose the leaders
        notifyLeaders("chooseLeaders");
    }

    /**
     * Utility method used to notify the view of the initial state of the market and of the developDecks
     */
    private void notifyInitialState(){
        Map<String, String> state= new HashMap<>();
        String cardId, marbleId, marble;
        Marble[] array;
        int id;

        state.put("action", "started");
        for (int col=0; col<4; col++){
            for (int row=0; row<3; row++){
                cardId="card"+col+row;
                id=developDecks[col][row].getCard().getId();
                state.put(cardId, String.valueOf(id));
            }
        }
        state.put("outMarble", market.getOutMarble().toString());
        for (int i=0; i<3; i++){
            array=market.selectRow(i);
            for (int j=0; j<4; j++){
                marbleId="marble"+j+i;
                marble=array[j].toString();
                state.put(marbleId, marble);
            }
        }

        String curr;
        int i=0;
        for (Player x: activePlayers){
            curr="player"+i;
            state.put(curr, x.getName());
            i++;
        }

        //Message message= new SatrtedAnswer(state);
        //listener.fireUpdates(state.get("action"), message);
    }

    /**
     * Utility method used to notify the view that the currentPlayer needs to choose his leaders or to notify him of the
     * correct selection of the leaders
     * @param action the action that is to be sent to the player (choose leader: the player needs to choose his leaders,
     *               the id of the leaders between the player needs to choose are sent; ok leaders: the selection of the
     *               leaders is correct and the id of the selected leaders are sent)
     */
    private void notifyLeaders(String action){
        Map<String, String> state= new HashMap<>();
        List<LeaderCard> leaders;
        String cardId;
        int id;

        state.put("action", action);
        state.put("player", currentPlayer.getName());
        leaders=currentPlayer.getLeaders();
        for (int i=0; i<leaders.size(); i++){
            cardId="leader"+i;
            id=leaders.get(i).getId();
            state.put(cardId, String.valueOf(id));
        }

        Message message;
        /*if (action.equalsIgnoreCase("chooseleaders")){
            message= new ChooseLeadersAnswer(state);
        }
        else message= new OkLeadersAnswer(state);
        listener.fireUpdates(state.get("action"), message);*/
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
     * This method is used when a player leaves the game (when the match has not started yet)
     * @param name the name of the disconnected player
     */
    public void removePlayer(String name){
        for (int i=0; i<activePlayers.size(); i++){
            if (activePlayers.get(i).getName().equals(name)) activePlayers.remove(i);
        }
        for (int i=0; i<players.size(); i++){
            if (players.get(i).getName().equals(name)) players.remove(i);
        }
    }
    /**
     * This method is called by the controller when a client decided to activate productions. It checks if the player who wants
     * to do the action is the current one (if it is his turn), then calls the player's produce method.
     * @param player is the player who wants to do the action
     * @param info this map contains the info about all the productions that the player wants to activate
     * @throws InvalidActionException when it is not the turn of the player who wants to act, when he already did a mandatory action
     * or when the one of the productions was invalid (no resources or wrong positions from where to take them)
     */
    public void produce(String player, Message info) throws InvalidActionException {
        if (currentPlayer.getName().equals(player) && !doneMandatory) {
            //currentPlayer.produce(info);
            doneMandatory=true;
        }
        else if (currentPlayer.getName().equals(player) && doneMandatory) throw new InvalidActionException("You have already done a mandatory action!");
        else throw new InvalidActionException("It is not your turn!");
        //notify changes to player
        notifyProduce();
    }

    /**
     * Utility method used to notify the view of the corrected execution of a produce action. It sends to the view the
     * new situation of the deposits and strongbox of the player that did the action
     */
    private void notifyProduce(){
        Map<String, String> state= new HashMap<>();
        List<ResourceAmount> deps=currentPlayer.getPersonalBoard().getDeposits();
        ResourceAmount[] box=currentPlayer.getPersonalBoard().getStrongbox();
        String[] colors= new String[deps.size()];
        String boxres, boxqty;
        int pos=currentPlayer.getPersonalBoard().getPosition();

        state.put("action", "produce");
        state.put("player", currentPlayer.getName());
        state.put("newPos", String.valueOf(pos));

        for (int i=0; i<deps.size(); i++){
            if (deps.get(i).getColor()!=null) colors[i]=deps.get(i).getColor().toString();
            else colors[i]="empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size()>3){
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size()==5){
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        for (int i=0; i<box.length; i++){
            boxres="strres"+i;
            boxqty="strqty"+i;
            state.put(boxres, box[i].getColor().toString());
            state.put(boxqty, String.valueOf(box[i].getAmount()));
        }

        //Message message= new ProduceAnswer(state);
        //listener.fireUpdates(state.get("action"), message);
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
        //notify changes to player
        notifyLeaderAction("activate", pos);
    }

    /**
     * Utility method used to notify the view of a corrected execution of a leader action (activate/discard). it sends to
     * the view the index of the activated/discarded leader, the action performed and the new position of the player (if
     * the leader has been discarded)
     * @param action the action performed
     * @param pos it represents the leader that the player has activated/discarded
     */
    private void notifyLeaderAction(String action, int pos){
        Map<String, String> state= new HashMap<>();
        List<ResourceAmount> deps=currentPlayer.getPersonalBoard().getDeposits();
        String[] colors= new String[deps.size()];

        state.put("action", action);
        state.put("player", currentPlayer.getName());
        state.put("index", String.valueOf(pos));

        // IF ACTION IS ACTIVATE
        if (currentPlayer.getLeaders().get(pos).getType().equalsIgnoreCase("resource")){
            state.put("isDep", "yes");
            for (int i=0; i<deps.size(); i++){
                if (deps.get(i).getColor()!=null) colors[i]=deps.get(i).getColor().toString();
                else colors[i]="empty";
            }
            state.put("smallres", colors[0]);
            state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
            state.put("midres", colors[1]);
            state.put("midqty", String.valueOf(deps.get(1).getAmount()));
            state.put("bigres", colors[2]);
            state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
            if (deps.size()>3){
                state.put("sp1res", colors[3]);
                state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
            }
            if (deps.size()==5){
                state.put("sp2res", colors[4]);
                state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
            }
        }

        if (action.equalsIgnoreCase("discard")){
            int newPos= currentPlayer.getPersonalBoard().getPosition();
            state.put("newPos", String.valueOf(newPos));
        }

        state.put("countLeader", String.valueOf(doneLeader));

        //Message message= new LeaderActionAnswer(state);
        //this.listener.fireUpdates(state.get("action"), message);
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
        //notify changes to player
        notifyLeaderAction("discard", pos);
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
        if(!currentPlayer.getName().equalsIgnoreCase(player)) throw new InvalidActionException("It is not your turn!");
        else if (!doneMandatory) throw new InvalidActionException("You have to do a mandatory action (buy a DevelopCard, activate production or take resources from market)");

        //select max position and set the tiles if needed: if someone is at the end, set isEndgame
        boolean inIf = false, inElse=false;
        int max=0;
        int curr;
        Player maxPlayer=null;
        for (Player p: activePlayers) {
            curr=p.getPersonalBoard().getFaithMarker().getPosition();
            if (curr>max){
                max=curr;
                maxPlayer=p;
            }
        }
        for (int i=2; i>=0 && maxPlayer!=null && !isEndGame; i--) {
            FavorTile tile=maxPlayer.getPersonalBoard().getTile(i);
            if (max>=tile.getEnd() && !tile.isActive() && !tile.isDiscarded()){
                tile.setActive(true);
                this.setTiles(maxPlayer, i);
                if (i==2){
                    System.out.println("Someone reached the end of the track");
                    this.isEndGame=true;
                }
            }
        }
        //check endGame and nextPlayer: if true and =first, count points e select winner; else go on
        if (current==activePlayers.size()-1){
            current=0;
        }
        else current++;
        Player ended=currentPlayer;
        currentPlayer=activePlayers.get(current);
        doneLeader=0;
        doneMandatory=false;

        if (isEndGame && currentPlayer.getName().equalsIgnoreCase(firstPlayer.getName())){
            inIf=true;
            System.out.println("Counting points...");
            //count points
            int maxpoints=0;
            int currpoints;
            Player winner=activePlayers.get(0);
            int[] points= new int[activePlayers.size()];
            for (int i=0; i<activePlayers.size(); i++){
                points[i]= getPoints(activePlayers.get(i));
                //System.out.println("Points of "+activePlayers.get(i).getName()+": "+points[i]);
                currpoints= points[i];
                if (currpoints>maxpoints){
                    maxpoints=points[i];
                    winner=activePlayers.get(i);
                }
                else if (currpoints==maxpoints && currpoints>0){
                    winner=winnerByResources(activePlayers.get(i), winner);
                }
            }
            //notify players win/lose
            System.out.println("Will I send the endgame message or not?");
            for (int i=0; i<activePlayers.size(); i++){
                notifyEndGame(activePlayers.get(i).getName(), winner.getName(), points[i], maxpoints);
                System.out.println("Sent it!");
            }

            this.phase=GamePhase.ENDED;
        }
        else {
            inElse=true;
            System.out.println("Just a normal end turn");
            //notify endturn

            notifyEndTurn(ended.getName());
            System.out.println("Sent an endTurn");

            notifyTurn();
            System.out.println("Sent a yourTurn");
        }

        System.out.println("I am at the end of endTurn in Game");
        System.out.println("Entered in if: "+inIf);
        System.out.println("Entered in else: "+inElse);
    }

    /**
     * Utility method used to notify the corrected execution of the end turn action. It sends to the view the new situation
     * of the player's FavorTile. This method is called once for each player connected to the game
     * @param ended the player that ended the turn
     */
    private void notifyEndTurn(String ended){
        Map<String, String> state= new HashMap<>();
        String tile;
        state.put("action", "endturn");
        state.put("endedTurnPlayer", ended);
        state.put("currentPlayer", currentPlayer.getName());

        String curr="player";
        int j=0;

        for (Player x : activePlayers){
            String name= x.getName();
            FavorTile[] tiles =x.getPersonalBoard().getTiles();

            state.put(curr+j, name);

            for (int i=0; i<tiles.length; i++){
                tile="tile"+j+i;
                if (tiles[i].isActive()){
                    state.put(tile, "active");
                }
                else if (tiles[i].isDiscarded()){
                    state.put(tile, "discarded");
                }
                else state.put(tile, "nothing");
            }
            j++;
        }

        //Message message= new EndTurnAnswer(state);
        //this.listener.fireUpdates(state.get("action"), message);
    }

    /**
     * This method is called once for each player of the game. It sends to the view the name of the winner and the amount of points
     * scored by the addressee player
     * @param player the name of the addressee player
     * @param winner the name of the winner
     * @param points the points scored by the addressee
     */
    private void notifyEndGame(String player, String winner, int points, int winnerpoints){
        Map<String, String> state= new HashMap<>();
        state.put("action", "endgame");
        state.put("player", player);
        state.put("winner", winner);
        state.put("points", String.valueOf(points));
        state.put("winnerpoints", String.valueOf(winnerpoints));

        //Message message= new EndgameAnswer(state);
        //this.listener.fireUpdates(state.get("action"), message);
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
    public void chooseInitialResource(String player, Message map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) {
            //if (currentPlayer.getNumberInitialResource() != 0)
                //currentPlayer.chooseInitialResource(map);
        }
        else throw new InvalidActionException("It is not your turn!");
        //notify changes to player
        notifyResources("okResources");

        //notify next player, if nextplayer is first change GamePhase to FullGame
        if (current==activePlayers.size()-1){
            current=0;
        }
        else current++;
        currentPlayer=activePlayers.get(current);
        if (current==0){
            //FullGame Phase
            this.phase=GamePhase.FULLGAME;
            notifyTurn();
        }
        else notifyResources("chooseResources");
    }

    /**
     * This method is called when a player chooses his leaders.
     * @param player the player who wants to do the action
     * @param map this map contains the indexes of the two leaders chosen
     * @throws InvalidActionException when one or both indexes are missing or if it is not the turn of the player.
     */
    public void chooseLeaders(String player, Message map) throws InvalidActionException {
        if (currentPlayer.getName().equals(player)) {
            int leader1, leader2;

            //leader1=map.getLeader(1);
            //leader2=map.getLeader(2);
            //currentPlayer.chooseLeader(leader1, leader2);
        }
        else throw new InvalidActionException("It is not your turn!");

        //notify changes to player
        notifyLeaders("okleaders");

        //notify next player. If next is first, change phase to Resource
        if (current==activePlayers.size()-1){
            current=0;
        }
        else current++;
        currentPlayer=activePlayers.get(current);
        if (current==0){
            //Resource Phase
            this.phase=GamePhase.RESOURCE;
            current++;
            currentPlayer=activePlayers.get(current);
            notifyResources("chooseResources");
        }
        else {
            notifyLeaders("chooseLeaders");
        }
    }

    /**
     * Utility method used to notify the view that the player needs to choose the type and position of the initial resources
     * or that the action is completed
     * @param action the action that is to be sent to the view (chooseResources or okResources)
     */
    private void notifyResources(String action){
        Map<String, String> state= new HashMap<>();
        int qty=currentPlayer.getNumberInitialResource();
        int faith=currentPlayer.getPersonalBoard().getPosition();
        List<ResourceAmount> deps=currentPlayer.getPersonalBoard().getDeposits();
        String[] colors= new String[deps.size()];

        state.put("action", action);
        state.put("player", currentPlayer.getName());
        Message message;
        if (action.equalsIgnoreCase("chooseResources")){
            state.put("qty", String.valueOf(qty));
            if (faith>0) state.put("addpos", String.valueOf(faith));
            //message= new ChooseResourcesAnswer(state);
        }
        else if (action.equalsIgnoreCase("okResources")){
            for (int i=0; i<deps.size(); i++){
                if (deps.get(i).getColor()!=null) colors[i]=deps.get(i).getColor().toString();
                else colors[i]="empty";
            }
            state.put("smallres", colors[0]);
            state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
            state.put("midres", colors[1]);
            state.put("midqty", String.valueOf(deps.get(1).getAmount()));
            state.put("bigres", colors[2]);
            state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
            //message= new OkResourcesAnswer(state);
        }

        //listener.fireUpdates(state.get("action"), message);
    }

    /**
     * Utility method used to notify the view that is the turn of the currentPlayer
     */
    private void notifyTurn(){
        Map<String, String> state= new HashMap<>();
        String content= "It is your turn! You must do one action of your choice between buy, market, produce";
        state.put("action", "yourTurn");
        state.put("content", content);
        state.put("player", currentPlayer.getName());

        //Message message= new YourTurnAnswer(state);
        //listener.fireUpdates(state.get("action"), message);
    }

    /**
     * This method is used for a buy move during the turn.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     * @throws NumberFormatException if the format is not valid.
     */
    public void buy(String player, Message map) throws InvalidActionException, NumberFormatException {
        if (!currentPlayer.getName().equals(player)) throw new InvalidActionException("It is not your turn!");
        if (doneMandatory) throw new InvalidActionException("You have already done a mandatory operation in this turn.");
        if(map.getRow()==-1 || map.getCol()==-1) throw new InvalidActionException("You didn't select the card.");
        int row = map.getRow();
        int column = map.getCol();
        if (row<0 || row>2 || column<0 || column>3) throw new InvalidActionException("Wrong indexes selected ");
        boolean end;
        int slot, id;
        DevelopCard card = developDecks[column][row].getCard();
        if (card==null) throw new InvalidActionException("No more cards in this deck!");
        id=card.getId();
        if (map.getSlot() == -1) throw new InvalidActionException("You didn't select the slot.");
        slot = map.getSlot();
        end = currentPlayer.buy(map, card);
        developDecks[column][row].removeCard();
        if (end)
            isEndGame = true;
        doneMandatory = true;

        //notify changes to players (it is the listener that separates the infos
        //notifyBuy(slot, id, column, row);
    }

    /**
     * Utility method used to notify the view that the buy action went fine. It sends to the client the new situation of
     * deposits and strongbox, the id of the new top card for the deck from where he bought the card, and the id of the bought
     * card
     * @param slot the index of the slot where the player put the bought card
     * @param bought the id of the bought card
     * @param col the col of the deck where the player bought the card
     * @param row the row of the deck where the player bought the card
     */
    private void notifyBuy(int slot, int bought, int col, int row){
        Map<String, String> state= new HashMap<>();
        DevelopCard card=developDecks[col][row].getCard();
        int idNew=0;
        if (card!=null) idNew=card.getId();

        String res, qty, boxres, boxqty;
        List<ResourceAmount> deps=currentPlayer.getPersonalBoard().getDeposits();
        ResourceAmount[] box=currentPlayer.getPersonalBoard().getStrongbox();
        String [] colors= new String[deps.size()];

        state.put("action", "buy");
        state.put("player", currentPlayer.getName());
        state.put("col", String.valueOf(col));
        state.put("row", String.valueOf(row));
        state.put("idNew", String.valueOf(idNew));

        state.put("slot", String.valueOf(slot));
        state.put("idBought", String.valueOf(bought));

        for (int i=0; i<deps.size(); i++){
            if (deps.get(i).getColor()!=null) colors[i]=deps.get(i).getColor().toString();
            else colors[i]="empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size()>3){
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size()==5){
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        for (int i=0; i<box.length; i++){
            boxres="strres"+i;
            boxqty="strqty"+i;
            state.put(boxres, box[i].getColor().toString());
            state.put(boxqty, String.valueOf(box[i].getAmount()));
        }

        //Message message= new BuyAnswer(state);
        //listener.fireUpdates(state.get("action"), message);
    }

    /**
     * This method is used for a swapDeposit move during the turn.
     * @param player is the player's name.
     * @param map is where the information is stored.
     * @throws InvalidActionException if the move is not valid.
     */
    public void swapDeposit(String player, Message map) throws InvalidActionException {
        //if (currentPlayer.getName().equals(player)) currentPlayer.swapDeposit(map);
        //else throw new InvalidActionException("It is not your turn!");
        //notify changes to player
        notifySwap();
    }

    /**
     * Utility method used to notify the view that the swap action went fine. It sends to the client the new situation
     * of the deposits and strongbox
     */
    private void notifySwap(){
        Map<String, String> state=new HashMap<>();
        List<ResourceAmount> deps=currentPlayer.getPersonalBoard().getDeposits();
        String colors[]= new String[deps.size()];

        state.put("action", "swap");
        state.put("player", currentPlayer.getName());

        for (int i=0; i<deps.size(); i++){
            if (deps.get(i).getColor()!=null) colors[i]=deps.get(i).getColor().toString();
            else colors[i]="empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size()>3){
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size()==5){
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        //Message message= new SwapAnswer(state);
        //listener.fireUpdates(state.get("action"), message);
    }

    /**
     * This method is used for taking resources from the Market
     * @param player is the Player's name
     * @param map is the map with the information
     * @throws InvalidActionException if the move is not valid
     */
    public void fromMarket(String player, Message map) throws InvalidActionException {
        int value;
        String chosen;
        int discarded=0;
        if (currentPlayer.getName().equals(player)){

            if (doneMandatory) throw new InvalidActionException("You have already done a mandatory action in this turn!");

            /*if (map.isRow()) {
                int row = map.getMarblesIndex();
                value=row-1;
                chosen="row";
                if (row >= 1 && row <= 3) {
                    discarded = currentPlayer.fromMarket(map, market.selectRow(row - 1));
                    market.pushRow(row-1);
                    for (Player p : players) {
                        if (!p.equals(currentPlayer)) p.getPersonalBoard().setPosition(p.getPersonalBoard().getPosition()+discarded);
                    }
                    doneMandatory = true;
                } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for row!");
            } else
            if (map.isCol()) {
                int col = map.getMarblesIndex();
                value=col-1;
                chosen="col";
                if (col >= 1 && col <= 4) {
                    discarded = currentPlayer.fromMarket(map, market.selectColumn(col - 1));
                    market.pushColumn(col-1);
                    for (Player p : players) {
                        if (!p.equals(currentPlayer)) p.getPersonalBoard().setPosition(p.getPersonalBoard().getPosition()+discarded);
                    }
                    doneMandatory = true;
                } else throw new InvalidActionException("Invalid action! You didn't insert a correct index for col!");

            } else throw new InvalidActionException("Invalid action! You didn't insert \"row\" or \"col\" correctly!");*/
        }
        else throw new InvalidActionException("It is not your turn!");
        //notify changes
        //notifyMarket(chosen, value, discarded);
    }

    /**
     * Utility method used to notify the view that a fromMarket action went fine. It sends to the client the new situation
     * of the deposits and strongbox, the new situation of the market and (to the other clients) the amount to add to their
     * position and (only to the client that did the action) his new position
     * @param chosen row or col of the market chosen by the player
     * @param value the index of the row or col
     * @param discarded the amount of resources discarded by the player
     */
    private void notifyMarket(String chosen, int value, int discarded){
        Map<String, String> state=new HashMap<>();
        List<ResourceAmount> deps=currentPlayer.getPersonalBoard().getDeposits();
        Marble[] marbles;
        Marble out;
        String res;
        String colors[]= new String[deps.size()];

        state.put("action", "market");
        state.put("player", currentPlayer.getName());

        state.put("discarded", String.valueOf(discarded));
        state.put("newPos", String.valueOf(currentPlayer.getPersonalBoard().getPosition()));
        if (chosen.equalsIgnoreCase("col")){
            state.put("col", String.valueOf(value));
            marbles=market.selectColumn(value);
            for (int i=0; i<marbles.length; i++){
                res="res"+i;
                state.put(res, marbles[i].toString());
            }
        }
        else if (chosen.equalsIgnoreCase("row")){
            state.put("row", String.valueOf(value));
            marbles=market.selectRow(value);
            for (int i=0; i<marbles.length; i++){
                res="res"+i;
                state.put(res, marbles[i].toString());
            }
        }
        out=market.getOutMarble();
        state.put("out", out.toString());

        for (int i=0; i<deps.size(); i++){
            if (deps.get(i).getColor()!=null) colors[i]=deps.get(i).getColor().toString();
            else colors[i]="empty";
        }
        state.put("smallres", colors[0]);
        state.put("smallqty", String.valueOf(deps.get(0).getAmount()));
        state.put("midres", colors[1]);
        state.put("midqty", String.valueOf(deps.get(1).getAmount()));
        state.put("bigres", colors[2]);
        state.put("bigqty", String.valueOf(deps.get(2).getAmount()));
        if (deps.size()>3){
            state.put("sp1res", colors[3]);
            state.put("sp1qty", String.valueOf(deps.get(3).getAmount()));
        }
        if (deps.size()==5){
            state.put("sp2res", colors[4]);
            state.put("sp2qty", String.valueOf(deps.get(4).getAmount()));
        }

        //Message message= new MarketAnswer(state);
        //listener.fireUpdates(state.get("action"), message);
    }

    /**
     * Just for testing
     * @param gamePhase the phase
     */
    public void setPhase(GamePhase gamePhase) {
        this.phase = gamePhase;
    }
}
