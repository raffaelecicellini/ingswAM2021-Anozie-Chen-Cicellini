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
            } while (name == null);
            System.out.println(">You chose: " + name);
            System.out.println(">Is it ok? [yes/no] ");
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
                    System.out.println(">Is it ok? [yes/no] ");
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
        //System.out.println(Cards.getDevelopById(1)+" "+ Cards.getDevelopById(8)+" "+ Cards.getDevelopById(7)+" "+ Cards.getDevelopById(10));
        //System.out.println("Col:Purple; "+"Lev:1; "+"Cost:[2P]; "+"Col:Blue; "+"Lev:1; "+"Cost:[2Y]; "+"Col:Blue; "+"Lev:1; "+"Cost:[3Y]; "+"Col:Green; "+"Lev:1; "+"Cost:[3B]; ");
        //System.out.println("In:[1G]; "+"Out:[1F]; "+"VP:1; "+"In:[1B]; "+"Out:[1F]; "+"VP:1; "+"In:[2G]; "+"Out:[1Y, 1P, 1B]; "+"VP:3; "+"In:[2P]; "+"Out:[1Y, 1B, 1G]; "+"VP:3;");

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
        //System.out.println("Hello!");
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

    private void market(){
        //chiede row o col (PARTONO DA 1): chiamo getResMarket, per ogni risorsa chiedo dove salvarla. SE RED non chiedo
        //ma metto segnalino in mappa, se WHITE controllo se ho leader attivi (se 1, stampo colore trasformato e chiedo dove
        //salvare; se 2, chiedo anche colore a scelta; se 0 metto segnalino come red). Stampa mossa e chiedi conferma
    }

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
        System.out.println(">");
        source = input.nextLine();
        if (!possibleInput.contains(source.toLowerCase())) {
            System.err.println("Wrong word inserted.");
            printActions();
            return;
        }

        System.out.println("Select the second deposit, Possible choices: small, mid, big, sp1, sp2");
        System.out.println(">");
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

    private void activate(){
        //chiede indice leader e conferma
    }

    private void discard(){
        //chiede indice leader e conferma
    }

    private void endTurn(){
        //controlla doneMandatory: se true chiede conferma e invia, altrimenti stampa err
    }

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

    private void chooseLeaders(){
        //Stampa i 4 leader tra cui scegliere (andando a capo). Chiede il primo, poi il secondo, poi la conferma
    }

    private void chooseResources(){
        //Stampa mex dicendo quante risorse può scegliere: per ogni res, chiede tipo e pos, poi conferma
    }

    private void printBoard(){
        //ClearScreen, stampa lo stato del gioco (usando printDeps, Decks, Market), poi stampa slots, faithtrack, favortile, leaders
    }

    private void printActions(){
        //controlla doneMandatory: a seconda del valore, stampa mex mettendo tutte mosse possibili tra cui scegliere
    }

    private void printDeps(){
        //Stampa in modo formattato i depositi e lo strongbox (res=..., amount=..., slots remaining=...)
    }

    private void printDecks(){
        //Stampa in modo formattato la matrice di decks
    }

    private void printMarket(){
        //Stampa in modo formattato il market e la outmarble
    }

    //AnswerHandler notifies it of changes, cli reads the ModelView and prints the new state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //a seconda del propertyName passato da answerhandler, chiama un metodo diverso (magari stampando un mex prima)
    }
}
