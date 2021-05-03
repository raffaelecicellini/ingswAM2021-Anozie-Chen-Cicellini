package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;

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
        //System.out.println(Cards.getDevelopById(1)+" "+ Cards.getDevelopById(8)+" "+ Cards.getDevelopById(7)+" "+ ah .getDevelopById(10));
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
    }

    private void produce(){
        //prod0 la chiede: se no salva no, altrimenti salva yes e chiede res/pos della prima e della seconda, res di output.
        //Controlla gli slot (prendendo la carta in cima-AGGIUNGERE METODO IN MODELVIEW-): per ogni slot chiede se vuole attivarlo
        //(se presente, altrimenti mette no in mappa): se si, chiama getInputById e per ogni el chiede da dove prenderlo.
        //Per le produzioni leader, controlla se ha leader attivi (giusti): se si, chiede se vuole attivare la prod: se si
        //chiede pos di input e res di output. Alla fine stampa mossa e chiede conferma

        /*

        if (modelView.isDoneMandatory()){
            System.err.println("You already did a mandatory action! You cannot take resources from market in this turn!");
            printActions();
            return;
        }

        String answer;
        Map<String, String> map = new HashMap<>();

        System.out.println("Do you want to activate the base production? [yes/no] ");
        System.out.print(">");
        answer = input.nextLine();

        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("I can't understand! Only type yes or no! ");
            System.out.print(">");
            answer = input.nextLine();
        }

        if (answer.equalsIgnoreCase("yes")) {
            map.put("prod0", answer.toLowerCase());

            for (int i = 1; i < 3; i++) {
                System.out.println("Which resource would you like to trade in? [" + i + "/2] ");
                System.out.print(">");
                answer = input.nextLine();
                map.put("in0" + i, answer);
                System.out.println("Where would you like to take it from? [small, mid, big, sp1, sp2, strongbox] ");
                System.out.print(">");
                answer = input.nextLine();
                map.put("pos0" + i, answer);
            }
            System.out.println("Which resource would you like to produce? ");
            System.out.print(">");
            answer = input.nextLine();
            map.put("out0", answer);
        } else
            if (answer.equalsIgnoreCase("no")){
                map.put("prod0", answer.toLowerCase());
        }

        // for every slot
        int num_slots;
        for (num_slots = 0; num_slots < modelView.getSlots().size(); num_slots++) {

            // check the top index
            int devCardIndex = getTopIndex(modelView.getSlots().get(num_slots));

            // if a develop card is present
            if (devCardIndex >= 1 && devCardIndex <= 3) {

                System.out.println("Would you like to activate the production in the slot " + (num_slots + 1) + "? [yes/no] ");
                System.out.print(">");
                answer = input.nextLine();

                while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                    System.out.println("I can't understand! Only type yes or no! ");
                    System.out.print(">");
                    answer = input.nextLine();
                }

                // if the player wants to activate the devCard
                if (answer.equalsIgnoreCase("yes")) {
                    map.put("prod" + (num_slots + 1), answer.toLowerCase());
                    // ask input
                    if (devCardIndex < modelView.getSlots().get(num_slots).length) {
                        List<String> inputRes = getInputById(modelView.getSlots().get(num_slots)[devCardIndex]);
                        for (int res = 0; res < inputRes.size(); res++) {
                            System.out.println("From where would you like to take the " + inputRes.get(res) + " resource from? [small, mid, big, sp1, sp2, strongbox] ");
                            System.out.print(">");
                            answer = input.nextLine();
                            // "pos11"
                            map.put("pos" + (num_slots + 1) + (res + 1), answer);
                        }
                    }
                } else
                    if (answer.equalsIgnoreCase("no")) {
                    map.put("prod" + (num_slots + 1), answer.toLowerCase());
                }
            }
        }

        // check leaders
        for (int leadercardpos = 0; leadercardpos < modelView.getLeaders().size()/2; leadercardpos++) {
            String color = Cards.getProductionById(Integer.parseInt(modelView.getLeaders().get("leader" + leadercardpos)));

            // if leader card is a LevTwo leader && is active
            if (color != null && modelView.getLeaders().get("state" + leadercardpos).equals("active")) {

                System.out.println("Would you like to activate the leader production (" + color + " resource in input)? [yes/no] ");
                System.out.print(">");
                answer = input.nextLine();

                while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                    System.out.println("I can't understand! Only type yes or no! ");
                    System.out.print(">");
                    answer = input.nextLine();
                }

                if (answer.equalsIgnoreCase("yes")) {
                    map.put("prod" + (num_slots+1), answer.toLowerCase());

                    System.out.println("From where would you like to take the " + color + " resource from? [small, mid, big, sp1, sp2, strongbox] ");
                    System.out.print(">");
                    answer = input.nextLine();
                    // "pos41"
                    map.put("pos" + (num_slots+1) + (leadercardpos+1), answer);

                    System.out.println("Which resource would you like to produce? ");
                    System.out.print(">");
                    answer = input.nextLine();
                    // "out4"
                    map.put("out" + (num_slots+1), answer);
                } else
                    if (answer.equalsIgnoreCase("no")){
                    map.put("prod" + (num_slots+1), answer.toLowerCase());
                }
            }
        }

        // send back the situation and wait for confirmation
        System.out.println("Are you sure do you want activate the production with these resources? [yes/no] ");
        for (int i = 0; i < map.size(); i++) {
            if (map.containsKey("prod" + i) && map.get("prod" + i).equals("yes")) {
                System.out.print(map.get("prod" + i + ": IN = (")) ;

                if (i == 0) {
                    System.out.print(map.get("in01") + ", " + map.get("pos01") + "), (" + map.get("in02") + ", " + map.get("pos02") + "); OUT = "  + map.get("out0"));
                } else
                    if (i >= 1 && i <=3) {
                        int n_pos = 1;
                        while (map.containsKey("pos" + i + n_pos)) {
                            if (n_pos == 2) System.out.print(", (");
                            System.out.print(          ", " + map.get("pos" + i + n_pos) + ")");
                            if (n_pos == 2) System.out.print(";");
                            n_pos++;
                        }
                    } else
                        if (i >= 4 && i <= 5) {
                            System.out.print(       ", " + map.get("pos" + i + "1") + "); OUT = " + map.get("out" + i));
                            if (i == 5) System.out.println(";");
                        }
            }

        }

        System.out.print(">");

        answer = input.nextLine();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("I can't understand! Only type yes or no! ");
            System.out.print(">");
            answer = input.nextLine();
        }

        if (answer.equalsIgnoreCase("yes")) {
            map.put("action", "produce");
            map.put("player", modelView.getName());
            listener.firePropertyChange(map.get("action"), null, map);
        } else
            if (answer.equalsIgnoreCase("no")) {
                System.out.println("Alright, you can type the action again!");
                printActions();
                System.out.print(">");
            }*/

    }

    private int getTopIndex(int[] slot) {
        int devCardIndex = 0;

        while (slot[devCardIndex] != 0 && devCardIndex < slot.length) {
            devCardIndex++;
        }

        return devCardIndex;
    }

    private void market(){
        //chiede row o col (PARTONO DA 1): chiamo getResMarket, per ogni risorsa chiedo dove salvarla. SE RED non chiedo
        //ma metto segnalino in mappa, se WHITE controllo se ho leader attivi (se 1, stampo colore trasformato e chiedo dove
        //salvare; se 2, chiedo anche colore a scelta; se 0 metto segnalino come red). Stampa mossa e chiedi conferma
    }

    private void swap(){
        //chiede i due dep, conferma
    }

    private void activate(){
        //chiede indice leader e conferma
    }

    private void discard(){
        //chiede indice leader e conferma
    }

    private void endTurn(){
        //controlla doneMandatory: se true chiede conferma e invia, altrimenti stampa err

        if (modelView.isDoneMandatory()) {
            System.out.println("Are you sure you want to end your turn? [yes/no] ");
            System.out.print(">");
            String answer = input.nextLine();

            while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                System.out.println("I can't understand! Only type yes or no! ");
                System.out.print(">");
                answer = input.nextLine();
            }

            if (answer.equalsIgnoreCase("yes")) {
                Map<String, String> map = new HashMap<>();
                map.put("action", "endturn");
                map.put("player", modelView.getName());
                listener.firePropertyChange(map.get("action"), null, map);
            } else
                if (answer.equalsIgnoreCase("no")) {
                    System.out.println("Alright, you can type the action again!");
                    printActions();
                }

        } else {
            System.out.println("You can't end your turn! You haven't done a mandatory action yet!");
        }
    }

    private void quit(){
        //chiede conferma
    }

    private void clearScreen(){
        //comandi per pulire console
        System.out.print("\033[H\033[2J");
        System.out.flush();
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

        StringBuilder str = new StringBuilder();

        System.out.println("                MARKET                ");
        System.out.println("+------1-------2-------3-------4-----+");
        System.out.println("|                                  " + modelView.getOutMarble());
        for (int row = 2; row >= 0; row--) {
            str.setLength(0);
            str.append(row+1).append("    ");
            for (int col = 0; col < 4; col++) {
                str.append(modelView.getMarket()[col][row]);
                if (col != 3) {
                    while (str.length() != (col+1)*8+5) str.append(" ");
                } else {
                    while (str.length() != 37) str.append(" ");
                    str.append("<--");
                }
            }
            System.out.println(str);
            if (row != 0)
                System.out.println("|                                    |");
        }
        System.out.println("+----- ^ ----- ^ ----- ^ ----- ^ ----+");
        System.out.println("       |       |       |       |     ");

        //Stampa in modo formattato il market e la outmarble
    }

    private void printTrack() {

        StringBuilder position = new StringBuilder(103);

        if (modelView.isSoloGame()) {
            position.append("|");
            if (modelView.getPosition() == modelView.getBlackCross()) {
                while (position.length() != modelView.getPosition()*4+1) position.append("   |");
                position.append("$ X|");
                while (position.length() != 101) position.append("   |");
            } else {

                while ((position.length() != modelView.getPosition()*4+1) &&
                        (position.length() != modelView.getBlackCross()*4+1)) {
                    position.append("   |");
                }

                if (position.length() == modelView.getPosition()*4+1) {
                    position.append(" X |");
                    while (position.length() != modelView.getBlackCross()*4+1) position.append("   |");
                    position.append(" $ ");
                }
                else
                if (position.length() == modelView.getBlackCross()*4+1) {
                    position.append(" $ |");
                    while (position.length() != modelView.getPosition()*4+1) position.append("   |");
                    position.append(" X ");
                }

                while (position.length() != 103) position.append("|  ");
            }
        } else {
            position.append("| ");
            while (position.length() != modelView.getPosition()*4+2) position.append("  | ");
            position.append("X ");
            while (position.length() != 103) position.append("|  ");
        }

        StringBuilder tiles = new StringBuilder();
        tiles.append("+-------------------+");
        if (modelView.getTiles()[0].isActive() &&  !modelView.getTiles()[0].isDiscarded()) {
            tiles.append("   VP:").append(modelView.getTiles()[0].getId()).append(", ON    ");
        } else
        if (!modelView.getTiles()[0].isActive() &&  !modelView.getTiles()[0].isDiscarded()){
            tiles.append("   VP:").append(modelView.getTiles()[0].getId()).append(", OFF   ");
        } else
        if (modelView.getTiles()[0].isDiscarded()){
            tiles.append("   DISCARDED   ");
        }

        tiles.append("+-----------+");
        if (modelView.getTiles()[1].isActive() &&  !modelView.getTiles()[1].isDiscarded()) {
            tiles.append("     VP:").append(modelView.getTiles()[1].getId()).append(", ON      ");
        } else
        if (!modelView.getTiles()[1].isActive() &&  !modelView.getTiles()[1].isDiscarded()){
            tiles.append("     VP:").append(modelView.getTiles()[1].getId()).append(", OFF     ");
        } else
        if (modelView.getTiles()[1].isDiscarded()){
            tiles.append("     DISCARDED     ");
        }

        tiles.append("+-------+");
        if (modelView.getTiles()[2].isActive() &&  !modelView.getTiles()[2].isDiscarded()) {
            tiles.append("       VP:").append(modelView.getTiles()[2].getId()).append(", ON        ");
        } else
        if (!modelView.getTiles()[2].isActive() &&  !modelView.getTiles()[2].isDiscarded()){
            tiles.append("       VP:").append(modelView.getTiles()[2].getId()).append(", OFF       ");
        } else
        if (modelView.getTiles()[2].isDiscarded()){
            tiles.append("       DISCARDED       ");
        }

        tiles.append("+");

        System.out.println("                                             FAITH TRACK                                             ");
        System.out.println("+------------VP1---------VP2---------VP4---------VP6---------VP9---------VP12--------VP16-------VP20+");
        System.out.println(position);
        //System.out.println("| X |   |   |   |   |   |   |   | + |   |   |   |   |   |   |   | + |   |   |   |   |   |   |   | + |");
        System.out.println(tiles);
        //System.out.println("+-------------------+   VP:2, OFF   +-----------+     VP:3, OFF     +-------+       VP:4, OFF       +");
        System.out.println("                    +---------------+           +-------------------+       +-----------------------+");

    }

    private void printSlots() {

        String slot1 = String.valueOf(getTopIndex( modelView.getSlots().get(0)));
        String slot2 = String.valueOf(getTopIndex( modelView.getSlots().get(1)));
        String slot3 = String.valueOf(getTopIndex( modelView.getSlots().get(2)));

        System.out.println("      +----------------------");
        System.out.println("\nslot1 -> ");
        System.out.println("\n\n\nslot2 -> \n");
        System.out.println("\nslot3 -> ");
    }

    private List<String> getResMarket(String chosen, int ind) {
        List<String> res = new ArrayList<>();

        if (chosen.equalsIgnoreCase("row")) {
            for (int col = 0; col < 4; col++) {
                res.add(modelView.getMarket()[col][ind]);
            }
        } else
            if (chosen.equalsIgnoreCase("col")) {
                for (int row = 0; row < 3; row++) {
                    res.add(modelView.getMarket()[ind][row]);
                }
            }

        return res;
    }

    //AnswerHandler notifies it of changes, cli reads the ModelView and prints the new state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        Map<String, String> map = (Map<String, String>) evt.getNewValue();
        String action = evt.getPropertyName();

        switch (action.toUpperCase()) {

            case "STARTED":

                printBoard();

                break;

            case "CHOOSELEADERS":

                if (map == null) {
                    // print leaders
                } else {
                    System.out.println(map.get("other") + " is choosing his leaders!" );
                }

                break;

            case "OKLEADERS":

                if (map == null) {
                    // print leaders
                } else {
                    System.out.println(map.get("other") + " has chosen his leaders!" );
                }

                break;

            case "CHOOSERESOURCES":

                if (modelView.getName().equalsIgnoreCase(map.get("player"))) {

                    if (map.containsKey("addpos")) {
                        // System.out.println("Your position on the Faith Track has also been increased!");
                    }

                } else {
                    System.out.println(map.get("other") + " is choosing his initial resources!" );
                }

                break;

            case "OKRESOURCES":

                if (map == null) {
                    printDeps();
                    printTrack();
                } else {
                    System.out.println(map.get("other") + " has chosen his initial resources!" );
                }

                break;

            case "YOURTURN":

                if (map.get("player").equalsIgnoreCase(modelView.getName())) {
                    System.out.println(map.get("content"));
                } else {
                    System.out.println("It's " + map.get("player") + "'s turn now!");
                }

            case "PRODUCE":

                if (map == null) {
                    printBoard();
                } else {
                    System.out.println(map.get("other") + " has made some productions!" );
                }

                break;

            case "BUY":

                if (map == null) {
                    // the player that has bought a dev card
                    System.out.println("Here is your new situation!");
                    printBoard();

                } else {
                    System.out.println(map.get("other") + " has bought a Develop Card!" );
                }

                printDecks();

                break;

            case "MARKET":

                if (map == null) {
                    // the player that has bought a dev card
                    System.out.println("Here is your new situation!");
                    printBoard();

                } else {
                    System.out.println(map.get("other") + " has taken resources from the Market!" );
                    if (Integer.parseInt(map.get("discarded")) != 0) {
                        System.out.println("Your position has been increased!");
                        printBoard();
                    }
                }

                break;

            case "SWAP":

                if (map == null) {
                    //printDeps();
                    printBoard();
                } else {
                    System.out.println(map.get("other") + " has swapped his deposits!" );
                }

                break;

            case "ACTIVATE":

                if (map == null) {
                    // print leaders
                    // print extra deposit
                } else {
                    System.out.println(map.get("other") + " has activated his leader!" );
                }

                break;

            case "DISCARD":

                if (map == null) {
                    // print leaders
                    // print position
                    printBoard();
                } else {
                    System.out.println(map.get("other") + " has discaded his leader!" );
                }

                break;

            case "ENDTURN":

                if (modelView.getName().equalsIgnoreCase(map.get("player"))) {
                    System.out.println("The Token that has been activated is: " + map.get("tokenActivated"));
                    // come attivo il token?
                    printBoard();
                } else {
                    System.out.println(map.get("other") + " has ended his turn!" );
                    //printBoard();
                }

                break;

            case "ENDGAME":

                if (map.get("winner").equalsIgnoreCase(modelView.getName())) {
                    System.out.println("You won! You made " + map.get("winnerpoints") + " points! ");
                } else {
                    System.out.println("You lost! You made " + map.get("points") + " points! ");
                    System.out.println(map.get("winner") + " won with " + map.get("winnerpoints") + " points! ");
                }

                break;

            case "ERROR":

                if (map.get("player").equalsIgnoreCase(modelView.getName())) {
                    System.out.println(map.get("content"));
                }

                break;

        }

        //a seconda del propertyName passato da answerhandler, chiama un metodo diverso (magari stampando un mex prima)
    }

}
