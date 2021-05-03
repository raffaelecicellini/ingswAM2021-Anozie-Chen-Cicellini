package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.SoloGame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.util.*;

public class CLI implements Runnable, PropertyChangeListener {
    //used in online
    private int port;
    private String address;
    private ConnectionSocket connectionSocket;
    //used in offline
    private Controller controller;
    private Game model;
    private boolean isSolo;
    //used in both cases
    private final PropertyChangeSupport listener= new PropertyChangeSupport(this);
    private ModelView modelView;
    private final PrintStream output;
    private final Scanner input;
    private final AnswerHandler answerHandler;
    private boolean activeGame;

    public CLI(boolean isSolo){
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
        modelView = new ModelView();
        answerHandler = new AnswerHandler(modelView, this);
        activeGame = true;
        this.isSolo=isSolo;

        if (isSolo){
            modelView.setSoloGame(true);
            model= new SoloGame();
            model.setListener(answerHandler);
            controller= new Controller(model, answerHandler);
            listener.addPropertyChangeListener(controller);
        }
    }

    public void setup() {
        String name = null;
        boolean confirmed = false;
        int number=0;
        while (!confirmed) {
            do {
                System.out.println(">Insert your nickname: ");
                System.out.print(">");
                name = input.nextLine();
            } while (name == null || name.equalsIgnoreCase(""));
            System.out.println(">You chose: " + name);
            System.out.println(">Is it ok? [yes/no]");
            System.out.print(">");
            if (input.nextLine().equalsIgnoreCase("yes")) {
                confirmed = true;
            } else {
                name = null;
            }
        }
        modelView.setName(name);
        if (this.isSolo){
            model.createPlayer(name);
            listener.firePropertyChange("start", null, null);
        }
        else{
            confirmed=false;
            while (!confirmed) {
                try {
                    System.out.println(">Insert the number of players (if 1, a single player game will start; otherwise, you will be added to the current multiplayer game): ");
                    System.out.print(">");
                    number = input.nextInt();
                    System.out.println(">You chose: " + number);
                    System.out.println(">Is it ok? [yes/no]");
                    System.out.print(">");
                    input.nextLine();
                    if (input.nextLine().equalsIgnoreCase("yes")) {
                        confirmed = true;
                    } else {
                        confirmed=false;
                    }
                } catch (InputMismatchException e){
                    System.out.println(">A number must be provided! Please try again");
                }
            }
            modelView.setSoloGame(number == 1);
            connectionSocket = new ConnectionSocket(address, port);
            Map<String, String> map= new HashMap<>();
            map.put("action", "setup");
            map.put("number", String.valueOf(number));
            map.put("username", name);
            Gson gson= new Gson();
            String message=gson.toJson(map);
            if(!connectionSocket.setup(message, answerHandler)) {
                CLI.main(null);
            }
            System.out.println("Connection established!");
            listener.addPropertyChangeListener(new ActionParser(connectionSocket));
        }
    }

    /**
     * The main class of CLI client. It instantiates a new CLI class, running it.
     * @param args of type String[] - the standard java main parameters.
     */
    public static void main(String[] args) {
        System.out.println("Welcome!\nWhat do you want to launch?");
        System.out.println("0. LOCAL GAME (single player)\n1. ONLINE GAME (single/multi player up to 4 players)");
        System.out.println("\n>Type the number of the desired option!");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
            if (input==0){
                System.out.println("You chose local game. Have fun!");
                CLI cli=new CLI(true);
                cli.run();
            }
            else if (input==1){
                scanner=new Scanner(System.in);
                System.out.println(">Insert the server IP address");
                System.out.print(">");
                String ip = scanner.nextLine();
                System.out.println(">Insert the server port");
                System.out.print(">");
                int port = scanner.nextInt();
                CLI cli = new CLI(false);
                cli.setPort(port);
                cli.setAddress(ip);
                cli.run();
            }
            else {
                System.err.println("Invalid input!");
                main(args);
            }
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
    }

    private void setAddress(String ip) {
        this.address=ip;
    }

    private void setPort(int port) {
        this.port=port;
    }

    public synchronized boolean isActiveGame(){
        return activeGame;
    }

    public synchronized void setActiveGame(boolean activeGame){
        this.activeGame=activeGame;
    }

    @Override
    public void run() {
        setup();
        while(isActiveGame()){
            if (modelView.isActiveTurn() && modelView.getPhase()==GamePhase.FULLGAME){
                String command= input.nextLine();
                parseCommand(command);
            }
        }
    }

    private void parseCommand(String cmd){
        switch (cmd.toUpperCase()){
            case "BUY":
                buy();
                break;
            case "PRODUCE":
                produce();
                break;
            case "MARKET":
                market();
                break;
            case "SWAP":
                swap();
                break;
            case "ACTIVATE":
                activate();
                break;
            case "DISCARD":
                discard();
                break;
            case "ENDTURN":
                endTurn();
                break;
            case "QUIT":
                quit();
                break;
        }
    }

    /**
     * This method is called when a player decides to make a buy move during his turn.
     */
    private void buy(){
        //chiede col e row, vede se ci sono leader sconto attivi e costruisce array di sconti. Chiama getCostById e per ogni
        //risorsa chiede da dove prenderla. Alla fine stampa mossa e chiede conferma: se si invia, altrimenti chiama printActions e ritorna
        System.out.println("Insert the row, number between 0 and 2");
        System.out.print(">");

        int row = -1;
        try {
            row = input.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("You didn't insert a number.");
            return;
        }

        if (row < 0 || row > 2) {
            System.err.println("Wrong number selected");
            return;
        }

        System.out.println("Insert the column, number between 0 and 3");
        System.out.print(">");
        int column = -1;
        try {
            column = input.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("You didn't insert a number.");
            return;
        }

        if (column < 0 || column > 3) {
            System.err.println("Wrong number selected");
            return;
        }

        System.out.println("Insert your personal board slot index in which you cant to place the card, number between 0 and 2");
        System.out.print(">");
        int ind = -1;
        try {
            ind = input.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("You didn't insert a number.");
            return;
        }
        if (ind < 0 || ind > 2) {
            System.err.println("Wrong number selected");
            return;
        }

        ArrayList<String> discounts = new ArrayList<>();
        discounts.add(Cards.getDiscountById(Integer.parseInt(modelView.getLeaders().get("leader0"))));
        discounts.add(Cards.getDiscountById(Integer.parseInt(modelView.getLeaders().get("leader1"))));
        ArrayList<String> cost = Cards.getCostById(modelView.getDevelopDecks()[column][row],discounts);

        Map<String,String> action = new HashMap<>();
        action.put("action","buy");
        action.put("row", Integer.toString(row));
        action.put("column", Integer.toString(column));
        action.put("ind", Integer.toString(ind));
        action.put("player",modelView.getName().toLowerCase());

        //list of accepted strings from input
        ArrayList<String> possibleInput = new ArrayList<>();
        possibleInput.add("big");
        possibleInput.add("small");
        possibleInput.add("mid");
        possibleInput.add("sp1");
        possibleInput.add("sp2");
        possibleInput.add("strongbox");

        //loop where i ask for the source of the resources
        int i = 1;
        for (String x : cost) {
            if (x != null) {
                System.out.println("Resource " + x + " tell me where you want to take it from. Possible choices: small, mid, big, sp1, sp2, strongbox");
                System.out.print(">");
                String choice = input.nextLine();
                if (!possibleInput.contains(choice.toLowerCase())) {
                    System.err.println("Wrong word inserted");
                    return;
                } else {
                    action.put("res"+i,choice.toLowerCase());
                    i++;
                }
            }
        }

        ArrayList<String> possibleInput1 = new ArrayList<>();
        possibleInput1.add("yes");
        possibleInput1.add("no");
        System.out.println("This is your move. Do you want to confirm it? [yes/no]");
        int j = 1;
        for (String x : cost) {
            if (x != null) {
                System.out.println("Resource " + x + ": " + action.get("res" + j));
                j++;
            }
        }

        String confirmation;
        System.out.print(">");
        confirmation = input.nextLine();
        while (!possibleInput1.contains(confirmation.toLowerCase())) {
            System.out.println("Select yes or no");
            System.out.print(">");
            confirmation = input.nextLine();
        }

        if (confirmation.equalsIgnoreCase("no")) {
            printActions();
            return;
        }
        listener.firePropertyChange("buy",null,action);
    }

    private void produce(){
        //prod0 la chiede: se no salva no, altrimenti salva yes e chiede res/pos della prima e della seconda, res di output.
        //Controlla gli slot (prendendo la carta in cima-AGGIUNGERE METODO IN MODELVIEW-): per ogni slot chiede se vuole attivarlo
        //(se presente, altrimenti mette no in mappa): se si, chiama getInputById e per ogni el chiede da dove prenderlo.
        //Per le produzioni leader, controlla se ha leader attivi (giusti): se si, chiede se vuole attivare la prod: se si
        //chiede pos di input e res di output. Alla fine stampa mossa e chiede conferma
    }

    /**
     * Method used when the player decides to take resources from market. It asks the user for a row/col and for an index,
     * then for each resource from the market asks the user where he wants to put it. At the end it prints the action done
     * by the user and asks confirmation. If so, it notifies the listener of the action of the user, otherwise does nothing.
     */
    private void market(){/*
        //chiede row o col (PARTONO DA 1): chiamo getResMarket, per ogni risorsa chiedo dove salvarla. SE RED non chiedo
        //ma metto segnalino in mappa, se WHITE controllo se ho leader attivi (se 1, stampo colore trasformato e chiedo dove
        //salvare; se 2, chiedo anche colore a scelta; se 0 metto segnalino come red). Stampa mossa e chiedi conferma
        if (modelView.isDoneMandatory()){
            System.err.println("You already did a mandatory action! You cannot take resources from market in this turn!");
            printActions();
            return;
        }
        Map<String, String> map= new HashMap<>();
        map.put("action", "market");
        map.put("player", modelView.getName());

        System.out.println(">Insert row/col and the index where you want to take resources from (es \"row 1\", the index must be between 1 and 3 (for row) or 4 (for col))");
        System.out.print(">");
        String[] where= input.nextLine().split(" ");
        if (!where[0].equalsIgnoreCase("row") || !where[0].equalsIgnoreCase("col")){
            System.err.println("Invalid input! You must insert \"row\" or \"col\"! Try again!");
            printActions();
        }
        else {
            int idx=-1;
            try{
                idx=Integer.parseInt(where[1]);
            }catch(NumberFormatException e){
                System.err.println("Invalid input! After row/col, a number must be provided!");
                printActions();
                return;
            }

            if (where[0].equalsIgnoreCase("row") && (idx<1 || idx>3)){
                System.err.println("Invalid input! For a row, you must insert a number between 1 and 3. Try again!");
                printActions();
            }
            else if (where[0].equalsIgnoreCase("col") && (idx<1 || idx>4)){
                System.err.println("Invalid input! For a col, you must insert a number between 1 and 4. Try again!");
                printActions();
            }
            else{
                map.put(where[0].toLowerCase(), where[1]);
                ArrayList<String> res= modelView.getResMarket(where[0], idx);
                int i=1;
                String curr;
                for (String x: res) {
                    curr="pos"+i;
                    if (x.equalsIgnoreCase("red")) map.put(curr, "x");
                    else if (x.equalsIgnoreCase("white")){
                        //controlli white
                        Map<String, String> leaders= modelView.getLeaders();
                        List<String> colors= new ArrayList<>();
                        for (int j=0; j<2; j++){
                            String state= "state"+j;
                            String white= Cards.getWhiteById(Integer.parseInt(leaders.get("leader"+j)));

                            if (leaders.get(state).equalsIgnoreCase("active") && white!=null){
                                colors.add(white);
                            }
                        }
                        if (colors.isEmpty()) map.put(curr, "x");
                        else if(colors.size()==1){
                            System.out.println(">Current resource: "+colors.get(0)+". Where do you want to put it? (small, mid, big, sp1, sp2)");
                            System.out.print(">");
                            String pos=input.nextLine();
                            map.put(curr, pos);
                        }
                        else if (colors.size()==2){
                            System.out.println(">You have two active leaders that change the white marble! Choose the color you prefer");
                            System.out.print(">");
                            String col=input.nextLine();
                            map.put("res"+i, col);
                            System.out.println(">Current resource: "+col+". Where do you want to put it? (small, mid, big, sp1, sp2)");
                            System.out.print(">");
                            String pos=input.nextLine();
                            map.put(curr, pos);
                        }
                    }
                    else{
                        System.out.println(">Current resource: "+x+". Where do you want to put it? (small, mid, big, sp1, sp2)");
                        System.out.print(">");
                        String pos=input.nextLine();
                        map.put(curr, pos);
                    }
                }
                //STAMPA MOSSA E CHIEDI CONFERMA A UTENTE, POI INVIA
                System.out.println(">Here is the action you chose to do:");
                i=1;
                curr="pos"+i;
                while (map.containsKey(curr)){
                    System.out.println("Position for resource "+i+": "+map.get(curr));
                    i++;
                    curr="pos"+i;
                }
                String confirmed;
                do{
                    System.out.println(">Do you confirm your action? [yes/no]");
                    System.out.print(">");
                    confirmed=input.nextLine();
                } while(!confirmed.equalsIgnoreCase("yes") && !confirmed.equalsIgnoreCase("no"));

                if (confirmed.equalsIgnoreCase("yes")){
                    modelView.setActiveTurn(false);
                    listener.firePropertyChange(map.get("action"), null, map);
                }
            }
        }*/
    }

    /**
     * This method is called when a player decides to make a swapDeposits move during his turn.
     */
    private void swap(){
        //chiede i due dep, conferma
        ArrayList<String> possibleInput = new ArrayList<>();
        possibleInput.add("big");
        possibleInput.add("small");
        possibleInput.add("mid");
        possibleInput.add("sp1");
        possibleInput.add("sp2");

        String source;
        String dest;

        System.out.println("Select the first deposit, Possible choices: small, mid, big, sp1, sp2");
        System.out.print(">");
        source = input.nextLine();
        if (!possibleInput.contains(source.toLowerCase())) {
            System.err.println("Wrong word inserted.");
            printActions();
            return;
        }

        System.out.println("Select the second deposit, Possible choices: small, mid, big, sp1, sp2");
        System.out.print(">");
        dest = input.nextLine();
        if (!possibleInput.contains(dest.toLowerCase())) {
            System.err.println("Wrong word inserted.");
            printActions();
            return;
        }

        Map<String,String> action = new HashMap<>();
        action.put("player", modelView.getName());
        action.put("source",source.toLowerCase());
        action.put("dest",dest.toLowerCase());
        action.put("action","swap");

        ArrayList<String> possibleInput1 = new ArrayList<>();
        possibleInput1.add("yes");
        possibleInput1.add("no");
        String confirmation;

        System.out.println("This is your move. Do you want to confirm it? [yes/no]");
        System.out.println("Action: swap");
        System.out.println("First deposit: "+source);
        System.out.println("Second deposit: "+dest);


        System.out.print(">");
        confirmation = input.nextLine();
        while (!possibleInput1.contains(confirmation.toLowerCase())) {
            System.out.println("Select yes or no");
            System.out.print(">");
            confirmation = input.nextLine();
        }

        if (confirmation.equalsIgnoreCase("no")) {
            printActions();
            return;
        }

        listener.firePropertyChange("swap",null,action);
    }

    /**
     * Method used when the player decides to activate a leader. It asks the user the index (0/1) of the leader he wants
     * to activate, then asks for confirmation. If so, it notifies the listener of the action of the user, otherwise does nothing.
     */
    private void activate(){
        //chiede indice leader e conferma
        Map<String, String> map= new HashMap<>();
        map.put("action", "activate");
        map.put("player", modelView.getName());

        boolean exit= false;
        int idx=-1;
        while(!exit){
            System.out.println(">Type the index of the leader you want to activate (0/1)");
            System.out.print(">");
            try{
                idx=input.nextInt();
            }catch (InputMismatchException e){
                System.err.println("A number must be provided! Please try again!");
                exit=false;
            }
            if (idx<0 || idx>1){
                System.err.println("A number between 0 and 1 must be provided! Please try again!");
                exit=false;
            }
            else{
                exit=true;
                map.put("pos", String.valueOf(idx));
            }
        }

        input.nextLine();
        System.out.println(">You chose to activate the leader in position "+idx+". Is it ok? [yes/no]");
        System.out.print(">");
        if (input.nextLine().equalsIgnoreCase("yes")){
            modelView.setActiveTurn(false);
            listener.firePropertyChange(map.get("action"), null, map);
        }
    }

    /**
     * Method used when the player decides to discard a leader. It asks the user the index (0/1) of the leader he wants
     * to discard, then asks for confirmation. If so, it notifies the listener of the action of the user, otherwise does nothing.
     */
    private void discard(){
        //chiede indice leader e conferma
        Map<String, String> map= new HashMap<>();
        map.put("action", "discard");
        map.put("player", modelView.getName());

        boolean exit= false;
        int idx=-1;
        while(!exit){
            System.out.println(">Type the index of the leader you want to discard (0/1)");
            System.out.print(">");
            try{
                idx=input.nextInt();
            }catch (InputMismatchException e){
                System.err.println("A number must be provided! Please try again!");
                exit=false;
            }
            if (idx<0 || idx>1){
                System.err.println("A number between 0 and 1 must be provided! Please try again!");
                exit=false;
            }
            else{
                exit=true;
                map.put("pos", String.valueOf(idx));
            }
        }

        input.nextLine();
        System.out.println(">You chose to discard the leader in position "+idx+". Is it ok? [yes/no]");
        System.out.print(">");
        if (input.nextLine().equalsIgnoreCase("yes")){
            modelView.setActiveTurn(false);
            listener.firePropertyChange(map.get("action"), null, map);
        }
    }

    private void endTurn(){
        //controlla doneMandatory: se true chiede conferma e invia, altrimenti stampa err
    }

    /**
     * This method is called when a player decides to quit the game.
     */
    private void quit(){
        //chiede conferma
        ArrayList<String> possibleInput1 = new ArrayList<>();
        possibleInput1.add("yes");
        possibleInput1.add("no");
        String confirmation;

        System.out.println("Are you sure you want to quit? [yes/no]");
        System.out.print(">");
        confirmation = input.nextLine();
        while (!possibleInput1.contains(confirmation.toLowerCase())) {
            System.out.println("Select yes or no");
            System.out.print(">");
            confirmation = input.nextLine();
        }

        if (confirmation.equalsIgnoreCase("no")) {
            printActions();
            return;
        }

        Map<String,String> action = new HashMap<>();
        action.put("action","disconnect");
        action.put("player",modelView.getName());

        listener.firePropertyChange("disconnect",null,action);
    }

    private void clearScreen(){
        //comandi per pulire console
    }

    /**
     * This method is called during the player's turn at the start of the game in order to make him select his leaders.
     */
    private void chooseLeaders(){
        //Stampa i 4 leader tra cui scegliere (andando a capo). Chiede il primo, poi il secondo, poi la conferma
        System.out.println("You have to choose between these 4 leaders.");
        System.out.println("ID: "+modelView.getLeaders().get("leader0")+ "\n: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders().get("leader0"))));
        System.out.println("ID: "+modelView.getLeaders().get("leader1")+ "\n: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders().get("leader1"))));
        System.out.println("ID: "+modelView.getLeaders().get("leader2")+ "\n: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders().get("leader2"))));
        System.out.println("ID: "+modelView.getLeaders().get("leader3")+ "\n: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders().get("leader3"))));

        ArrayList<String> possibleInput = new ArrayList<>();
        possibleInput.add(modelView.getLeaders().get("leader0"));
        possibleInput.add(modelView.getLeaders().get("leader1"));
        possibleInput.add(modelView.getLeaders().get("leader2"));
        possibleInput.add(modelView.getLeaders().get("leader3"));
        String choice;
        Map<String,String> action = new HashMap<>();
        action.put("action","chooseleaders");
        action.put("player",modelView.getName());

        System.out.println("Insert the first leader id.");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice)){
            System.out.println("Insert the correct id");
            System.out.print(">");
            choice = input.nextLine();
        }

        action.put("leader0",choice);
        possibleInput.remove(choice);

        System.out.println("Insert the second leader id.");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice)){
            System.out.println("Insert the correct id");
            System.out.print(">");
            choice = input.nextLine();
        }
        action.put("leader1",choice);

        System.out.println("You selected these leaders:");
        System.out.println("ID :"+action.get("leader0"));
        System.out.println(Cards.getLeaderById(Integer.parseInt(action.get("leader0"))));
        System.out.println("ID :"+action.get("leader1"));
        System.out.println(Cards.getLeaderById(Integer.parseInt(action.get("leader1"))));
        System.out.println("Do you want to confirm? [yes/no]");
        possibleInput.clear();
        possibleInput.add("yes");
        possibleInput.add("no");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice.toLowerCase())) {
            System.out.println("Select yes or no");
            System.out.print(">");
            choice = input.nextLine();
        }

        if (choice.equalsIgnoreCase("no")) {
            chooseLeaders();
            return;
        }

        listener.firePropertyChange("chooseleaders",null,action);


    }

    /**
     * This method is called during the player's turn at the start of the game in order to make him select his initial resources.
     * @param quantity is the number of initial resources the player can have.
     */
    private void chooseResources(int quantity){
        ArrayList<String> possibleInput = new ArrayList<>();

        possibleInput.add("GREY");
        possibleInput.add("YELLOW");
        possibleInput.add("PURPLE");
        possibleInput.add("BLUE");

        ArrayList<String> possibleInput1 = new ArrayList<>();
        possibleInput1.add("small");
        possibleInput1.add("mid");
        possibleInput1.add("big");

        Map<String,String> action = new HashMap<>();
        action.put("action","chooseresources");
        action.put("player", modelView.getName());

        String choice;
        System.out.println("You have "+quantity+" resources.");
        for (int i = 1; i <= quantity; i++) {
            System.out.println("Insert what resource you want: [blue/grey/purple/yellow]");
            System.out.print(">");
            choice = input.nextLine();
            while (!possibleInput.contains(choice.toUpperCase())) {
                System.out.println("Wrong input, select [blue/grey/purple/yellow]");
                System.out.print(">");
                choice = input.nextLine();
            }
            action.put("res"+i,choice.toLowerCase());

            System.out.println("Insert where you want to place it: [small/mid/big]");
            System.out.print(">");
            choice = input.nextLine();
            while (!possibleInput.contains(choice.toLowerCase())) {
                System.out.println("Wrong input, select [small/mid/big]");
                System.out.print(">");
                choice = input.nextLine();
            }
            action.put("pos"+i,choice.toLowerCase());
        }

        System.out.println("This is your move, are you sure? [yes/no]");
        System.out.println("Resource1: "+action.get("res1"));
        System.out.println("Position: "+action.get("pos1"));
        System.out.println("Resource2: "+action.get("res2"));
        System.out.println("Position: "+action.get("pos2"));

        possibleInput.clear();
        possibleInput.add("yes");
        possibleInput.add("no");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice.toLowerCase())) {
            System.out.println("Select yes or no");
            System.out.print(">");
            choice = input.nextLine();
        }

        if (choice.equalsIgnoreCase("no")) {
            chooseResources(quantity);
            return;
        }

        listener.firePropertyChange("chooseresources",null,action);
    }

    /**
     * Method used to print the new state of the game. It is simply a container method, used to invoke other specialized
     * methods to print all the state in order
     */
    private void printBoard(){
        //ClearScreen, stampa lo stato del gioco (usando printDeps, Decks, Market), poi stampa slots, faithtrack, favortile, leaders
        clearScreen();
        printDecks();
        printMarket();
        printTrack();
        printDeps();
        printSlots();
        printLeaders();
    }

    /**
     * Method used to print a message to the user telling him which actions he can do, based on the value of the doneMandatory
     * attribute of the ModelView
     */
    private void printActions(){
        //controlla doneMandatory: a seconda del valore, stampa mex mettendo tutte mosse possibili tra cui scegliere X
        if (modelView.isDoneMandatory()){
            System.out.println(">Choose your action typing one of the following words: ACTIVATE, DISCARD, SWAP, ENDTURN, QUIT");
            System.out.print(">");
        }
        else{
            System.out.println(">Choose your action typing one of the following words: BUY, PRODUCE, MARKET, ACTIVATE, DISCARD, SWAP, QUIT");
            System.out.print(">");
        }
    }

    /**
     * This method prints the player's leader cards.
     */
    private void printLeaders(){
        System.out.println("Leader 1");
        System.out.println("ID: "+ modelView.getLeaders().get("leader0"));
        System.out.println("State: "+modelView.getLeaders().get("state0").toUpperCase());
        System.out.println(Cards.getLeaderById(Integer.parseInt(modelView.getLeaders().get("leader0"))));

        System.out.println("Leader 2");
        System.out.println("ID: "+ modelView.getLeaders().get("leader1"));
        System.out.println("State: "+modelView.getLeaders().get("state1").toUpperCase());
        System.out.println(Cards.getLeaderById(Integer.parseInt(modelView.getLeaders().get("leader1"))));
    }

    /**
     * This method prints the player's deposits and strongbox.
     */
    private void printDeps(){
        //Stampa in modo formattato i depositi e lo strongbox (res=..., amount=..., slots remaining=...)
        int small[] = new int[1];
        small[0] = modelView.getDeposits().get("smallqty")!= null? Integer.parseInt(modelView.getDeposits().get("smallqty")): 0;
        int mid[] = new int[1];
        mid[0] = modelView.getDeposits().get("midqty")!= null? Integer.parseInt(modelView.getDeposits().get("midqty")): 0;
        int big[] = new int[1];
        big[0] = modelView.getDeposits().get("bigqty")!= null? Integer.parseInt(modelView.getDeposits().get("bigqty")): 0;
        int sp1[] = new int[1];
        sp1[0] = modelView.getDeposits().get("sp1qty")!= null? Integer.parseInt(modelView.getDeposits().get("sp1qty")): 0;
        int sp2[] = new int[1];
        sp2[0] = modelView.getDeposits().get("sp2qty")!= null? Integer.parseInt(modelView.getDeposits().get("sp2qty")): 0;
        System.out.println("+------+");
        System.out.println("|"+ printDepCell(small,"small")+"|");
        System.out.println("+------+------+");
        System.out.println("|"+ printDepCell(mid,"mid")+"|"+ printDepCell(mid,"mid")+"|");
        System.out.println("+------+------+------+   +------+------+   +------+------+ ");
        System.out.println("|"+ printDepCell(big,"big")+"|"+ printDepCell(big,"big")+"|"+ printDepCell(big,"big")+"|   |"+ printDepCell(sp1,"sp1")+"|"+ printDepCell(sp1,"sp1")+"|   |"+ printDepCell(sp2,"sp2")+"|"+ printDepCell(sp2,"sp2")+"|");
        System.out.println("+------+------+------+   +------+------+   +------+------+");
        System.out.println("\t\tnormal             sp1 "+ printLastLineDep(1)+"        sp2 "+ printLastLineDep(2)+"  ");
        System.out.println("\n");
        System.out.println("|STRONGBOX|");
        System.out.println(modelView.getStrongbox().get("strres1").toUpperCase()+": " + modelView.getStrongbox().get("strres1qty"));
        System.out.println(modelView.getStrongbox().get("strres2").toUpperCase()+": " + modelView.getStrongbox().get("strres2qty"));
        System.out.println(modelView.getStrongbox().get("strres3").toUpperCase()+": " + modelView.getStrongbox().get("strres3qty"));
        System.out.println(modelView.getStrongbox().get("strres4").toUpperCase()+": " + modelView.getStrongbox().get("strres4qty"));

    }

    /**
     * Utility method used for printing a cell of the player's deposit.
     * @param i is the number of resources the player has in each deposit (small, big, medium,sp1,sp2).
     * @param dep is the player's deposit (small, big, medium,sp1,sp2).
     * @return the resource at a certain cell.
     */
    //prints each cell of the deposits
    private String printDepCell(int i[], String dep) {
        String resource = modelView.getDeposits().get(dep);
        if (resource == null)
            return "      ";
        else {
            switch (resource.toUpperCase()) {
                case "BLUE":
                    if (i[0] > 0) {
                        i[0]--;
                        return " BLUE ";
                    } else return "      ";
                case "YELLOW":
                    if (i[0] > 0) {
                        i[0]--;
                        return "YELLOW";
                    } else return "      ";
                case "PURPLE":
                    if (i[0] > 0) {
                        i[0]--;
                        return "PURPLE";
                    } else return "      ";
                case "GREY":
                    if (i[0] > 0) {
                        i[0]--;
                        return " GREY ";
                    } else return "      ";
                default:
                    return "      ";
            }
        }
    }

    /**
     * Utility method used for printing the last line of the deposit.
     * @param a is the index representing the special deposit (1 = sp1, 2 = sp2).
     * @return the resource of the special deposit.
     */
    private String printLastLineDep(int a) {
        if (a == 1) {
            if (modelView.getDeposits().get("sp1")!=null)
                if (modelView.getDeposits().get("sp1").toUpperCase() == "BLUE" || modelView.getDeposits().get("sp1").toUpperCase() == "GREY")
                    return " "+modelView.getDeposits().get("sp1").toUpperCase()+" ";
                else return modelView.getDeposits().get("sp1").toUpperCase();
            else return "      ";
        } else {
            if (modelView.getDeposits().get("sp2")!=null)
                if (modelView.getDeposits().get("sp2").toUpperCase() == "BLUE" || modelView.getDeposits().get("sp2").toUpperCase() == "GREY")
                    return " "+modelView.getDeposits().get("sp2").toUpperCase()+" ";
                else return modelView.getDeposits().get("sp2").toUpperCase();
            else return "      ";
        }
    }

    /**
     * Method used to print in a formatted way the state of the developDecks, where the user can buy a DevelopCard
     */
    private void printDecks(){
        //Stampa in modo formattato la matrice di decks
        int[][] decks= modelView.getDevelopDecks();
        StringBuilder layer= new StringBuilder();
        for (int row=2; row>=0; row--){
            for (int i=0; i<4; i++){
                layer.setLength(0);
                for (int col=0; col<4; col++){
                    if (i==1 && col==0){
                        layer.append("Row:").append(row).append(" ");
                    }
                    else if (col==0){
                        layer.append("      ");
                    }
                    else layer.append(" ");
                    layer.append(Cards.getDevelopById(decks[col][row])[i]);
                }
                System.out.println(layer);
            }
        }
        layer.setLength(0);
        for (int col=0; col<4; col++){
            if (col==0){
                layer.append("                          ").append("Col:").append(col).append("                  ");
            }
            else layer.append("                   ").append("Col:").append(col).append("                  ");

        }
        System.out.println(layer);
    }

    private void printMarket(){
        //Stampa in modo formattato il market e la outmarble X
    }

    private void printSlots(){

    }

    private void printTrack(){
        //print track e favortile
    }


    //AnswerHandler notifies it of changes, cli reads the ModelView and prints the new state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //a seconda del propertyName passato da answerhandler, chiama un metodo diverso (magari stampando un mex prima)
    }
}
