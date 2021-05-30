package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.client.*;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class CLI implements Runnable, SourceListener {
    //used in online
    private int port;
    private String address;
    private ConnectionSocket connectionSocket;
    //used in offline
    private Controller controller;
    private Game model;
    private boolean isSolo;
    //used in both cases
    private final Source listener= new Source();
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
            listener.addListener(controller);
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
            listener.fireUpdates("start", null);
        }
        else{
            confirmed=false;
            while (!confirmed) {
                try {
                    System.out.println(">Insert the number of players (if 1, a single player game will start; otherwise, you will be added to the current multiplayer game): ");
                    System.out.print(">");
                    number = Integer.parseInt(input.nextLine());
                    System.out.println(">You chose: " + number);
                    System.out.println(">Is it ok? [yes/no]");
                    System.out.print(">");
                    confirmed = input.nextLine().equalsIgnoreCase("yes");
                } catch (NumberFormatException e){
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
            //System.out.println("Connection established!");
            listener.addListener(new ActionParser(connectionSocket));
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
            input = Integer.parseInt(scanner.nextLine());
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
                int port = Integer.parseInt(scanner.nextLine());
                CLI cli = new CLI(false);
                cli.setPort(port);
                cli.setAddress(ip);
                cli.run();
            }
            else {
                System.err.println("Invalid input!");
                main(args);
            }
        } catch (NumberFormatException e) {
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
            if (/*modelView.isActiveTurn() && */modelView.getPhase()==GamePhase.FULLGAME){
                String command= input.nextLine();
                parseCommand(command);
            }
        }
    }

    private void parseCommand(String cmd){
        switch (cmd.toUpperCase()){
            case "BUY":
                if(modelView.isActiveTurn()) buy();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
                break;
            case "PRODUCE":
                if(modelView.isActiveTurn()) produce();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
                break;
            case "MARKET":
                if(modelView.isActiveTurn()) market();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
                break;
            case "SWAP":
                if(modelView.isActiveTurn()) swap();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
                break;
            case "ACTIVATE":
                if(modelView.isActiveTurn()) activate();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
            case "DISCARD":
                if(modelView.isActiveTurn()) discard();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
            case "ENDTURN":
                if(modelView.isActiveTurn()) endTurn();
                else {
                    System.out.println(">You cannot do it, it is not your turn!");
                    printActions();
                }
            case "QUIT":
                quit();
                break;
            case "SHOWPLAYER":
                showPlayer();
                break;
            default:
                System.err.println("Unrecognized command! Try again!");
                printActions();
                break;
        }
    }

    /**
     * This method is called when a player decides to make a buy move during his turn.
     */
    private void buy(){
        //chiede col e row, vede se ci sono leader sconto attivi e costruisce array di sconti. Chiama getCostById e per ogni
        //risorsa chiede da dove prenderla. Alla fine stampa mossa e chiede conferma: se si invia, altrimenti chiama printActions e ritorna
        if (modelView.isDoneMandatory()){
            System.err.println("You already did a mandatory action! You cannot buy a card in this turn!");
            printActions();
            return;
        }
        System.out.println(">Insert the row, number between 0 and 2");
        System.out.print(">");

        int row = -1;
        try {
            row = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("You didn't insert a number.");
            printActions();
            return;
        }

        if (row < 0 || row > 2) {
            System.err.println("Wrong number selected");
            printActions();
            return;
        }
        System.out.println(">Insert the column, number between 0 and 3");
        System.out.print(">");
        int column = -1;
        try {
            column = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("You didn't insert a number.");
            printActions();
            return;
        }

        if (column < 0 || column > 3) {
            System.err.println("Wrong number selected");
            printActions();
            return;
        }
        System.out.println(">Insert your personal board slot index in which you want to place the card, number between 0 and 2");
        System.out.print(">");
        int ind = -1;
        try {
            ind = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("You didn't insert a number.");
            printActions();
            return;
        }
        if (ind < 0 || ind > 2) {
            System.err.println("Wrong number selected");
            printActions();
            return;
        }
        ArrayList<String> discounts = new ArrayList<>();
        if (modelView.getLeaders(modelView.getName()).get("state0").equalsIgnoreCase("active"))
            discounts.add(Cards.getDiscountById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader0"))));
        if (modelView.getLeaders(modelView.getName()).get("state1").equalsIgnoreCase("active"))
            discounts.add(Cards.getDiscountById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader1"))));

        ArrayList<String> cost;
        if (modelView.getDevelopDecks()[column][row]!=0) {
            cost = Cards.getCostById(modelView.getDevelopDecks()[column][row], discounts);
        }
        else {
            System.err.println("There are no more cards in this deck! Try another one!");
            printActions();
            return;
        }

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
                System.out.println(">Resource " + x + ": tell me where you want to take it from. Possible choices: small, mid, big, sp1, sp2, strongbox");
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
        modelView.setActiveTurn(false);
        Message message= new BuyMessage(action);
        listener.fireUpdates("buy", message);
    }

    /**
     * Method used when the player decides to activate productions with Develop Cards. It checks if he has already done a mandatory
     * action. If no, for each Develop Card, it asks the Player if he wants to activate it, and for each activated Develop Card it asks
     * from where he wants to take the resources from. At the end it prints the action done by the user and asks confirmation.
     * If so, it notifies the listener of the action of the user, otherwise does nothing.
     */
    private void produce(){
        //prod0 la chiede: se no salva no, altrimenti salva yes e chiede res/pos della prima e della seconda, res di output.
        //Controlla gli slot (prendendo la carta in cima-AGGIUNGERE METODO IN MODELVIEW-): per ogni slot chiede se vuole attivarlo
        //(se presente, altrimenti mette no in mappa): se si, chiama getInputById e per ogni el chiede da dove prenderlo.
        //Per le produzioni leader, controlla se ha leader attivi (giusti): se si, chiede se vuole attivare la prod: se si
        //chiede pos di input e res di output. Alla fine stampa mossa e chiede conferma

        if (modelView.isDoneMandatory()){
            System.err.println("You already did a mandatory action! You cannot activate production in this turn!");
            printActions();
            return;
        }

        String answer;
        Map<String, String> map = new HashMap<>();
        List<String> validInputs = new ArrayList<>();
        validInputs.add("small");
        validInputs.add("mid");
        validInputs.add("big");
        validInputs.add("strongbox");
        if (Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader0")) >= 7 &&
                Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader0")) <= 10 &&
                modelView.getLeaders(modelView.getName()).get("state0").equalsIgnoreCase("active") )
            validInputs.add("sp1");
        if (Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader1")) >= 7 &&
                Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader1")) <= 10 &&
                modelView.getLeaders(modelView.getName()).get("state1").equalsIgnoreCase("active") )
            validInputs.add("sp2");

        List<String> validColors = new ArrayList<>();
        for (int i = 0; i < Color.values().length; i++) {
            validColors.add(Color.values()[i].toString());
        }
        validColors.remove("GREEN");

        int yes_count = 0;

        System.out.println(">Do you want to activate the base production? [yes/no] ");
        System.out.print(">");
        answer = input.nextLine();

        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println(">I can't understand! Only type yes or no! ");
            System.out.print(">");
            answer = input.nextLine();
        }

        if (answer.equalsIgnoreCase("yes")) {
            yes_count++;
            map.put("prod0", answer.toLowerCase());

            for (int i = 1; i < 3; i++) {
                System.out.println(">Which resource would you like to trade in? [" + i + "/2] ");
                System.out.print(">");
                answer = input.nextLine();
                while (!validColors.contains(answer.toUpperCase())) {
                    System.out.println("Wrong color selected: make sure to type correctly!");
                    System.out.print(">");
                    answer = input.nextLine();
                }
                map.put("in0" + i, answer.toUpperCase());
                System.out.println("Where would you like to take it from? [small, mid, big, sp1, sp2, strongbox] ");
                System.out.print(">");
                answer = input.nextLine();
                while (!validInputs.contains(answer.toLowerCase())) {
                    System.out.println("Wrong place selected: make sure to type correctly or if you have the special deposit!");
                    System.out.print(">");
                    answer = input.nextLine();
                }
                map.put("pos0" + i, answer.toLowerCase());
            }
            System.out.println(">Which resource would you like to produce? ");
            System.out.print(">");
            answer = input.nextLine();
            while (!validColors.contains(answer.toUpperCase())) {
                System.out.println("Wrong color selected: make sure to type correctly!");
                System.out.print(">");
                answer = input.nextLine();
            }
            map.put("out0", answer.toUpperCase());
        } else
        if (answer.equalsIgnoreCase("no")){
            map.put("prod0", answer.toLowerCase());
        }

        // for every slot
        int num_slot;
        for (num_slot = 0; num_slot < modelView.getSlots(modelView.getName()).size(); num_slot++) {

            // check the top index
            int devCardIndex = modelView.getTopIndex(modelView.getSlots(modelView.getName()).get(num_slot));

            // if a develop card is present
            if (devCardIndex >= 0 && devCardIndex <= 2) {

                System.out.println(">Would you like to activate the production in the slot " + num_slot + "? [yes/no] ");
                System.out.print(">");
                answer = input.nextLine();

                while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                    System.out.println("I can't understand! Only type yes or no! ");
                    System.out.print(">");
                    answer = input.nextLine();
                }

                // if the player wants to activate the devCard
                if (answer.equalsIgnoreCase("yes")) {
                    yes_count++;
                    map.put("prod" + (num_slot + 1), answer.toLowerCase());
                    // ask input

                    ArrayList<String> inputRes = Cards.getInputById(modelView.getSlots(modelView.getName()).get(num_slot)[devCardIndex]);
                    for (int res = 0; res < inputRes.size(); res++) {
                        System.out.println("From where would you like to take the " + inputRes.get(res) + " resource from? [small, mid, big, sp1, sp2, strongbox] ");
                        System.out.print(">");
                        answer = input.nextLine();
                        while (!validInputs.contains(answer.toLowerCase())) {
                            System.out.println("Wrong place selected: make sure to type correctly or if you have the special deposit!");
                            System.out.print(">");
                            answer = input.nextLine();
                        }
                        // "pos11"
                        map.put("pos" + (num_slot + 1) + (res + 1), answer.toLowerCase());
                    }

                } else
                if (answer.equalsIgnoreCase("no")) {
                    map.put("prod" + (num_slot + 1), answer.toLowerCase());
                }
            } else
            if (devCardIndex == -1){
                map.put("prod" + (num_slot + 1), "no");
            }
        }

        // check leaders
        for (int leadercardpos = 0; leadercardpos < modelView.getLeaders(modelView.getName()).size()/2; leadercardpos++) {
            String color = Cards.getProductionById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader" + leadercardpos)));

            // if leader card is a LevTwo leader && is active
            if (color != null && modelView.getLeaders(modelView.getName()).get("state" + leadercardpos).equals("active")) {

                System.out.println(">Would you like to activate the leader production (" + color + " resource in input)? [yes/no] ");
                System.out.print(">");
                answer = input.nextLine();

                while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                    System.out.println(">I can't understand! Only type yes or no! ");
                    System.out.print(">");
                    answer = input.nextLine();
                }

                if (answer.equalsIgnoreCase("yes")) {
                    yes_count++;
                    // prod4, yes
                    map.put("prod" + (num_slot+1), answer.toLowerCase());

                    System.out.println(">From where would you like to take the " + color + " resource from? [small, mid, big, sp1, sp2, strongbox] ");
                    System.out.print(">");
                    answer = input.nextLine();
                    while (!validInputs.contains(answer.toLowerCase())) {
                        System.out.println("Wrong place selected: make sure to type correctly or if you have the special deposit!");
                        System.out.print(">");
                        answer = input.nextLine();
                    }
                    // "pos41" o "pos51"
                    map.put("pos" + (num_slot+1) + "1", answer.toLowerCase());

                    System.out.println(">Which resource would you like to produce? ");
                    System.out.print(">");
                    answer = input.nextLine();
                    while (!validColors.contains(answer.toUpperCase())) {
                        System.out.println("Wrong color selected: make sure to type correctly!");
                        System.out.print(">");
                        answer = input.nextLine();
                    }
                    // "out4"
                    map.put("out" + (num_slot+1), answer.toUpperCase());
                } else
                if (answer.equalsIgnoreCase("no")){
                    map.put("prod" + (num_slot+1), answer.toLowerCase());
                }


                num_slot++;
            }
        }

        // send back the situation and wait for confirmation
        System.out.println("Are you sure do you want activate the production with these resources? [yes/no] ");
        StringBuilder str = new StringBuilder();
        //for (int devCard = 0; devCard < yes_count; devCard++) {

        int devCard = 0;
        for (int i = 0; i < yes_count; i++) {
            //System.out.println(map);
            while (map.containsKey("prod" + devCard) && !map.get("prod" + devCard).equalsIgnoreCase("yes")) {
                //System.out.println(devCard);
                devCard++;
            }

            str.setLength(0);
            str.append("prod").append(devCard).append(": IN = (");
            //System.out.print(map.get("prod" + devCard + ": IN = (")) ;

            if (devCard == 0) {
                str.append(map.get("in01").toUpperCase()).append(", ").append(map.get("pos01").toLowerCase()).append("), (").append(map.get("in02").toUpperCase())
                        .append(", ").append(map.get("pos02").toLowerCase()).append("); OUT = ").append(map.get("out0").toUpperCase());
                System.out.println(str);
                //System.out.print(map.get("in01") + ", " + map.get("pos01") + "), (" + map.get("in02") + ", " + map.get("pos02") + "); OUT = "  + map.get("out0"));
            } else if (devCard >= 1 && devCard <= 3) {
                int n_pos = 1;
                //System.out.println(devCard);
                ArrayList<String> inputRes = Cards.getInputById(modelView.getSlots(modelView.getName()).get(devCard - 1)[modelView.getTopIndex(modelView.getSlots(modelView.getName()).get(devCard - 1))]);
                // pos11 o pos12
                while (map.containsKey("pos" + devCard + n_pos)) {
                    // BLUE, SMALL), (GREY, MID);
                    if (n_pos == 1) {
                        str.append(inputRes.get(n_pos - 1).toUpperCase()).append(", ").append(map.get("pos" + devCard + n_pos).toLowerCase()).append(")");
                    }
                    if (n_pos == 2) {
                        str.append(", (").append(inputRes.get(n_pos - 1).toUpperCase()).append(", ").append(map.get("pos" + devCard + n_pos).toLowerCase()).append(")");
                    }
                    n_pos++;
                }
                System.out.println(str);
            } else if (devCard >= 4 && devCard <= 5) {
                //BLUE, SMALL); OUT = GREY
                str.append(Cards.getProductionById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader" + (devCard - 4))))).append(", ")
                        .append(map.get("pos" + devCard + "1").toLowerCase()).append("); OUT = ").append(map.get("out" + devCard).toUpperCase());
                System.out.println(str);
            }

            devCard++;
        }
        //}

        System.out.print(">");

        answer = input.nextLine();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println(">I can't understand! Only type yes or no! ");
            System.out.print(">");
            answer = input.nextLine();
        }

        if (answer.equalsIgnoreCase("yes")) {
            map.put("action", "produce");
            map.put("player", modelView.getName());
            modelView.setActiveTurn(false);
            Message message= new ProductionMessage(map);
            listener.fireUpdates(map.get("action"), message);
        } else
        if (answer.equalsIgnoreCase("no")) {
            System.out.println("Alright, you can type the action again!");
            printActions();
        }
    }

    /**
     * Method used when the player decides to take resources from market. It asks the user for a row/col and for an index,
     * then for each resource from the market asks the user where he wants to put it. At the end it prints the action done
     * by the user and asks confirmation. If so, it notifies the listener of the action of the user, otherwise does nothing.
     */
    private void market(){
        //chiede row o col (PARTONO DA 1): chiamo getResMarket, per ogni risorsa chiedo dove salvarla. SE RED non chiedo
        //ma metto segnalino in mappa, se WHITE controllo se ho leader attivi (se 1, stampo colore trasformato e chiedo dove
        //salvare; se 2, chiedo anche colore a scelta; se 0 metto segnalino come red). Stampa mossa e chiedi conferma

        if (modelView.isDoneMandatory()){
            System.err.println("You already did a mandatory action! You cannot take resources from market in this turn!");
            printActions();
            return;
        }

        List<String> validPositions = new ArrayList<>();
        validPositions.add("small");
        validPositions.add("mid");
        validPositions.add("big");
        validPositions.add("sp1");
        validPositions.add("sp2");
        validPositions.add("discard");

        List<String> validColors = new ArrayList<>();
        for (int i = 0; i < Color.values().length; i++) {
            validColors.add(Color.values()[i].toString());
        }
        validColors.remove("GREEN");


        Map<String, String> map= new HashMap<>();
        map.put("action", "market");
        map.put("player", modelView.getName());

        System.out.println(">Insert row/col and the index where you want to take resources from (es \"row 1\", the index must be between 1 and 3 (for row) or 4 (for col))");
        System.out.print(">");
        String[] where= input.nextLine().split(" ");
        if (where.length!=2){
            System.err.println("Invalid input! You must insert \"row\" or \"col\" and then a number! Try again!");
            printActions();
            return;
        }
        System.out.println(where[0] + " "+ where[1]);
        if (!where[0].equalsIgnoreCase("row") && !where[0].equalsIgnoreCase("col")){
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
                ArrayList<String> res= modelView.getResMarket(where[0], idx-1);
                Map<String, String> choices= new HashMap<>();
                int i=1;
                String curr;
                for (String x: res) {
                    curr="pos"+i;
                    if (x.equalsIgnoreCase("red")) map.put(curr, "small");
                    else if (x.equalsIgnoreCase("white")){
                        //controlli white
                        Map<String, String> leaders= modelView.getLeaders(modelView.getName());
                        List<String> colors= new ArrayList<>();
                        for (int j=0; j<2; j++){
                            String state= "state"+j;
                            String white= Cards.getWhiteById(Integer.parseInt(leaders.get("leader"+j)));

                            if (leaders.get(state).equalsIgnoreCase("active") && white!=null){
                                colors.add(white);
                            }
                        }
                        if (colors.isEmpty()) map.put(curr, "small");
                        else if(colors.size()==1){
                            System.out.println(">Current resource: "+colors.get(0)+". Where do you want to put it? (small, mid, big, sp1, sp2, discard)");
                            System.out.print(">");
                            String pos=input.nextLine();
                            while (!validPositions.contains(pos.toLowerCase())) {
                                System.out.println("I didn't understand, make sure to type correctly!");
                                System.out.println(">Current resource: "+x+". Where do you want to put it? (small, mid, big, sp1, sp2, discard)");
                                System.out.print(">");
                                pos=input.nextLine();
                            }
                            choices.put(curr, colors.get(0));
                            map.put(curr, pos);
                        }
                        else if (colors.size()==2){
                            System.out.println(">You have two active leaders that change the white marble! Choose the color you prefer");
                            System.out.print(">");
                            String col=input.nextLine();
                            while (!validColors.contains(col.toUpperCase())) {
                                System.out.println("I didn't understand, make sure to type correctly!");
                                System.out.println(">You have two active leaders that change the white marble! Choose the color you prefer");
                                System.out.print(">");
                                col=input.nextLine();
                            }
                            map.put("res"+i, col);

                            System.out.println(">Current resource: "+col+". Where do you want to put it? (small, mid, big, sp1, sp2, discard)");
                            System.out.print(">");
                            String pos=input.nextLine();
                            while (!validPositions.contains(pos.toLowerCase())) {
                                System.out.println("I didn't understand, make sure to type correctly!");
                                System.out.println(">Current resource: "+x+". Where do you want to put it? (small, mid, big, sp1, sp2, discard)");
                                System.out.print(">");
                                pos=input.nextLine();
                            }
                            choices.put(curr, col);
                            map.put(curr, pos);
                        }
                    }
                    else{
                        System.out.println(">Current resource: "+x+". Where do you want to put it? (small, mid, big, sp1, sp2, discard)");
                        System.out.print(">");
                        String pos=input.nextLine();
                        while (!validPositions.contains(pos.toLowerCase())) {
                            System.out.println("I didn't understand, make sure to type correctly!");
                            System.out.println(">Current resource: "+x+". Where do you want to put it? (small, mid, big, sp1, sp2, discard)");
                            System.out.print(">");
                            pos=input.nextLine();
                        }
                        choices.put(curr, x);
                        map.put(curr, pos);
                    }
                    i++;
                }
                //STAMPA MOSSA E CHIEDI CONFERMA A UTENTE, POI INVIA
                System.out.println(">Here is the action you chose to do:");
                i=1;
                curr="pos"+i;
                while (map.containsKey(curr)){
                    if (choices.containsKey(curr)){
                        System.out.println("Resource: "+choices.get(curr)+"; Position: "+map.get(curr));
                    }
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
                    Message message= new MarketMessage(map);
                    listener.fireUpdates(map.get("action"), message);
                }
                else printActions();
            }
        }
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

        Message message= new SwapMessage(action);
        listener.fireUpdates("swap", message);
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
                idx= Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
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

        System.out.println(">You chose to activate the leader in position "+idx+". Is it ok? [yes/no]");
        System.out.print(">");
        if (input.nextLine().equalsIgnoreCase("yes")){
            modelView.setActiveTurn(false);
            Message message= new LeaderActionMessage(map);
            listener.fireUpdates(map.get("action"), message);
            return;
        }
        printActions();
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
                idx= Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
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

        System.out.println(">You chose to discard the leader in position "+idx+". Is it ok? [yes/no]");
        System.out.print(">");
        if (input.nextLine().equalsIgnoreCase("yes")){
            modelView.setActiveTurn(false);
            Message message= new LeaderActionMessage(map);
            listener.fireUpdates(map.get("action"), message);
            return;
        }
        printActions();
    }

    /**
     * Method used when the player wants to end his turn. It checks if he has done a mandatory action. If yes, then it asks
     * confirmation and if he confirms, it notifies the listener, otherwise does nothing.
     */
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
                modelView.setActiveTurn(false);

                Message message= new EndTurnMessage(map);
                listener.fireUpdates(map.get("action"), message);
            } else
                if (answer.equalsIgnoreCase("no")) {
                    System.out.println("Alright, you can type the action again!");
                    printActions();
                }

        } else {
            System.out.println("You can't end your turn! You haven't done a mandatory action yet!");
            printActions();
        }
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

        Message message=new DisconnectionMessage(action);
        listener.fireUpdates("disconnect", message);

        if (connectionSocket!=null) connectionSocket.close();
        System.exit(0);
    }

    /**
     * Method used to clear the screen.
     */
    private void clearScreen(){
        try{
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e){
            System.err.println("Error in ClearScreen!");
            Thread.currentThread().interrupt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * This method is called during the player's turn at the start of the game in order to make him select his leaders.
     */
    private void chooseLeaders(){
        //Stampa i 4 leader tra cui scegliere (andando a capo). Chiede il primo, poi il secondo, poi la conferma
        System.out.println(">You have to choose between these 4 leaders.");
        System.out.println(" Index 1: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader0"))));
        System.out.println(" Index 2: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader1"))));
        System.out.println(" Index 3: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader2"))));
        System.out.println(" Index 4: "+Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader3"))));

        ArrayList<String> possibleInput = new ArrayList<>();
        possibleInput.add("1");
        possibleInput.add("2");
        possibleInput.add("3");
        possibleInput.add("4");
        String choice;
        Map<String,String> action = new HashMap<>();
        action.put("action","chooseleaders");
        action.put("player",modelView.getName());

        System.out.println(">Insert the index of the first leader.");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice)){
            System.out.println(">Insert the correct index");
            System.out.print(">");
            choice = input.nextLine();
        }

        action.put("ind1",choice);
        possibleInput.remove(choice);

        System.out.println(">Insert the index of the second leader.");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice)){
            System.out.println(">Insert the correct index");
            System.out.print(">");
            choice = input.nextLine();
        }
        action.put("ind2",choice);

        System.out.println(">You selected these leaders:");
        System.out.println(Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader"+(Integer.parseInt(action.get("ind1"))-1)))));
        System.out.println(Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(modelView.getName()).get("leader"+(Integer.parseInt(action.get("ind2"))-1)))));
        System.out.println(">Do you want to confirm? [yes/no]");
        possibleInput.clear();
        possibleInput.add("yes");
        possibleInput.add("no");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice.toLowerCase())) {
            System.out.println(">Select yes or no");
            System.out.print(">");
            choice = input.nextLine();
        }

        if (choice.equalsIgnoreCase("no")) {
            chooseLeaders();
            return;
        }

        Message message= new LeaderMessage(action);
        listener.fireUpdates("chooseleaders", message);
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
        System.out.println(">You have "+quantity+" resources.");
        for (int i = 1; i <= quantity; i++) {
            System.out.println(">Insert what resource you want: [blue/grey/purple/yellow]");
            System.out.print(">");
            choice = input.nextLine();
            while (!possibleInput.contains(choice.toUpperCase())) {
                System.out.println(">Wrong input, select [blue/grey/purple/yellow]");
                System.out.print(">");
                choice = input.nextLine();
            }
            action.put("res"+i,choice.toLowerCase());

            System.out.println(">Insert where you want to place it: [small/mid/big]");
            System.out.print(">");
            choice = input.nextLine();
            while (!possibleInput1.contains(choice.toLowerCase())) {
                System.out.println(">Wrong input, select [small/mid/big]");
                System.out.print(">");
                choice = input.nextLine();
            }
            action.put("pos"+i,choice.toLowerCase());
        }

        System.out.println(">This is your move, are you sure? [yes/no]");
        System.out.println(" Resource1: "+action.get("res1"));
        System.out.println(" Position: "+action.get("pos1"));
        if (quantity == 2) {
            System.out.println(" Resource2: " + action.get("res2"));
            System.out.println(" Position: " + action.get("pos2"));
        }

        possibleInput.clear();
        possibleInput.add("yes");
        possibleInput.add("no");
        System.out.print(">");
        choice = input.nextLine();
        while (!possibleInput.contains(choice.toLowerCase())) {
            System.out.println(">Select yes or no");
            System.out.print(">");
            choice = input.nextLine();
        }

        if (choice.equalsIgnoreCase("no")) {
            chooseResources(quantity);
            return;
        }

        Message message= new ResourceMessage(action);
        listener.fireUpdates("chooseresources", message);
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
        printTrack(modelView.getName());
        printDeps(modelView.getName());
        printSlots(modelView.getName());
        printLeaders(modelView.getName());
    }

    /**
     * Method used to print a message to the user telling him which actions he can do, based on the value of the doneMandatory
     * attribute of the ModelView
     */
    private void printActions(){
        //controlla doneMandatory e doneLeader: a seconda del valore, stampa mex mettendo tutte mosse possibili tra cui scegliere X

        StringBuilder actions = new StringBuilder();

        actions.append(">Choose your action typing one of the following words: ");

        if (modelView.isActiveTurn()) {
            if (!modelView.isDoneMandatory()) {
                actions.append("BUY, PRODUCE, MARKET, ");
            }

            if (modelView.getCountLeader() < 2) {
                actions.append("ACTIVATE, DISCARD, ");
            }

            actions.append("SWAP, ENDTURN, QUIT");
        }
        else actions.append("QUIT");

        if (!modelView.isSoloGame()) actions.append(", SHOWPLAYER");

        System.out.println(actions);
        System.out.print(">");
    }

    /**
     * This method prints the player's leader cards.
     */
    private void printLeaders(String name){
        if (name.equalsIgnoreCase(modelView.getName())) {
            System.out.println("Leader 0");
            System.out.println(" State: " + modelView.getLeaders(name).get("state0").toUpperCase());
            System.out.println(" " + Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(name).get("leader0"))));

            System.out.println("Leader 1");
            System.out.println(" State: " + modelView.getLeaders(name).get("state1").toUpperCase());
            System.out.println(" " + Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(name).get("leader1"))));
        } else {
            if (modelView.getLeaders(name).get("state0").equalsIgnoreCase("active") || modelView.getLeaders(name).get("state0").equalsIgnoreCase("discarded")) {
                System.out.println("Leader 0");
                System.out.println(" State: " + modelView.getLeaders(name).get("state0").toUpperCase());
                System.out.println(" " + Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(name).get("leader0"))));
            }

            if(modelView.getLeaders(name).get("state1").equalsIgnoreCase("active") || modelView.getLeaders(name).get("state1").equalsIgnoreCase("discarded")) {
                System.out.println("Leader 1");
                System.out.println(" State: " + modelView.getLeaders(name).get("state1").toUpperCase());
                System.out.println(" " + Cards.getLeaderById(Integer.parseInt(modelView.getLeaders(name).get("leader1"))));
            }
        }
    }

    /**
     * This method prints the player's deposits and strongbox.
     */
    private void printDeps(String name){
        //Stampa in modo formattato i depositi e lo strongbox (res=..., amount=..., slots remaining=...)
        int[] small = new int[1];
        small[0] = modelView.getDeposits(name).get("smallqty")!= null? Integer.parseInt(modelView.getDeposits(name).get("smallqty")): 0;
        int[] mid = new int[1];
        mid[0] = modelView.getDeposits(name).get("midqty")!= null? Integer.parseInt(modelView.getDeposits(name).get("midqty")): 0;
        int[] big = new int[1];
        big[0] = modelView.getDeposits(name).get("bigqty")!= null? Integer.parseInt(modelView.getDeposits(name).get("bigqty")): 0;
        int[] sp1 = new int[1];
        sp1[0] = modelView.getDeposits(name).get("sp1qty")!= null? Integer.parseInt(modelView.getDeposits(name).get("sp1qty")): 0;
        int[] sp2 = new int[1];
        sp2[0] = modelView.getDeposits(name).get("sp2qty")!= null? Integer.parseInt(modelView.getDeposits(name).get("sp2qty")): 0;
        System.out.println("+------+");
        System.out.println("|"+ printDepCell(small,"smallres",name)+"|");
        System.out.println("+------+------+");
        System.out.println("|"+ printDepCell(mid,"midres",name)+"|"+ printDepCell(mid,"midres",name)+"|");
        System.out.println("+------+------+------+   +------+------+   +------+------+ ");
        System.out.println("|"+ printDepCell(big,"bigres",name)+"|"+ printDepCell(big,"bigres",name)+"|"+ printDepCell(big,"bigres",name)+"|   |"+ printDepCell(sp1,"sp1res",name)+"|"+ printDepCell(sp1,"sp1res",name)+"|   |"+ printDepCell(sp2,"sp2res",name)+"|"+ printDepCell(sp2,"sp2res",name)+"|");
        System.out.println("+------+------+------+   +------+------+   +------+------+");
        //System.out.println("\t\tnormal   sp1 "+ printLastLineDep(1)+"        sp2 "+ printLastLineDep(2)+"  ");
        System.out.println("        normal             sp1 "+ printLastLineDep(1,name)+"        sp2 "+ printLastLineDep(2,name)+"   ");
        System.out.println("\n");
        System.out.println("|STRONGBOX|");
        System.out.println(modelView.getStrongbox(name).get("strres0").toUpperCase()+": " + modelView.getStrongbox(name).get("strqty0"));
        System.out.println(modelView.getStrongbox(name).get("strres1").toUpperCase()+": " + modelView.getStrongbox(name).get("strqty1"));
        System.out.println(modelView.getStrongbox(name).get("strres2").toUpperCase()+": " + modelView.getStrongbox(name).get("strqty2"));
        System.out.println(modelView.getStrongbox(name).get("strres3").toUpperCase()+": " + modelView.getStrongbox(name).get("strqty3"));

    }

    /**
     * Utility method used for printing a cell of the player's deposit.
     * @param i is the number of resources the player has in each deposit (small, big, medium,sp1,sp2).
     * @param dep is the player's deposit (small, big, medium,sp1,sp2).
     * @return the resource at a certain cell.
     */
    //prints each cell of the deposits
    private String printDepCell(int[] i, String dep, String name) {
        String resource = modelView.getDeposits(name).get(dep);
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
    private String printLastLineDep(int a, String name) {
        if (a == 1) {
            if (modelView.getDeposits(name).get("sp1res")!=null)
                if (modelView.getDeposits(name).get("sp1res").toUpperCase().equals("BLUE") || modelView.getDeposits(name).get("sp1res").toUpperCase().equals("GREY"))
                    return " "+modelView.getDeposits(name).get("sp1res").toUpperCase()+" ";
                else return modelView.getDeposits(name).get("sp1res").toUpperCase();
            else return "      ";
        } else {
            if (modelView.getDeposits(name).get("sp2res")!=null)
                if (modelView.getDeposits(name).get("sp2res").toUpperCase().equals("BLUE") || modelView.getDeposits(name).get("sp2res").toUpperCase().equals("GREY"))
                    return " "+modelView.getDeposits(name).get("sp2res").toUpperCase()+" ";
                else return modelView.getDeposits(name).get("sp2res").toUpperCase();
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

    /**
     * Method used to print the Market.
     */
    private void printMarket(){
        //Stampa in modo formattato il market e la outmarble

        StringBuilder str = new StringBuilder();

        System.out.println("                MARKET                ");
        System.out.println("+------1-------2-------3-------4-----+");
        System.out.println("|                                  " + modelView.getOutMarble());
        for (int row = 0; row <= 2; row++) {
            str.setLength(0);
            str.append(row+1).append("    ");
            for (int col = 0; col < 4; col++) {
                str.append(modelView.getMarket()[col][row]);
                if (col != 3) {
                    while (str.length() <= (col+1)*8+5) str.append(" ");
                } else {
                    while (str.length() <= 37) str.append(" ");
                    str.append("<--");
                }
            }
            System.out.println(str);
            if (row != 2)
                System.out.println("|                                    |");
        }
        System.out.println("+----- ^ ----- ^ ----- ^ ----- ^ ----+");
        System.out.println("       |       |       |       |     ");
    }

    /**
     * Method used to print the Faith Track.
     */
    private void printTrack(String name) {

        StringBuilder position = new StringBuilder(103);

        if (modelView.isSoloGame()) {
            position.append("|");
            if (modelView.getPosition(name) == modelView.getBlackCross()) {
                while (position.length() != modelView.getPosition(name)*4+1) position.append("   |");
                position.append("$ X|");
                while (position.length() <= 100) position.append("   |");
            } else {

                while ((position.length() != modelView.getPosition(name)*4+1) &&
                        (position.length() != modelView.getBlackCross()*4+1)) {
                    position.append("   |");
                }

                if (position.length() == modelView.getPosition(name)*4+1) {
                    position.append(" X |");
                    while (position.length() != modelView.getBlackCross()*4+1) position.append("   |");
                    position.append(" $ ");
                }
                else
                if (position.length() == modelView.getBlackCross()*4+1) {
                    position.append(" $ |");
                    while (position.length() != modelView.getPosition(name)*4+1) position.append("   |");
                    position.append(" X ");
                }

                while (position.length() <= 103) position.append("|   ");
            }
        } else {
            position.append("| ");
            while (position.length() != modelView.getPosition(name)*4+2) position.append("  | ");
            position.append("X ");
            while (position.length() <= 103) position.append("|   ");
        }

        StringBuilder tiles = new StringBuilder();
        tiles.append("+-------------------+");
        if (modelView.getTiles(name)[0].isActive() &&  !modelView.getTiles(name)[0].isDiscarded()) {
            tiles.append("   VP:").append(modelView.getTiles(name)[0].getId()).append(", ON    ");
        } else
        if (!modelView.getTiles(name)[0].isActive() &&  !modelView.getTiles(name)[0].isDiscarded()){
            tiles.append("   VP:").append(modelView.getTiles(name)[0].getId()).append(", OFF   ");
        } else
        if (modelView.getTiles(name)[0].isDiscarded()){
            tiles.append("   DISCARDED   ");
        }

        tiles.append("+-----------+");
        if (modelView.getTiles(name)[1].isActive() &&  !modelView.getTiles(name)[1].isDiscarded()) {
            tiles.append("     VP:").append(modelView.getTiles(name)[1].getId()).append(", ON      ");
        } else
        if (!modelView.getTiles(name)[1].isActive() &&  !modelView.getTiles(name)[1].isDiscarded()){
            tiles.append("     VP:").append(modelView.getTiles(name)[1].getId()).append(", OFF     ");
        } else
        if (modelView.getTiles(name)[1].isDiscarded()){
            tiles.append("     DISCARDED     ");
        }

        tiles.append("+-------+");
        if (modelView.getTiles(name)[2].isActive() &&  !modelView.getTiles(name)[2].isDiscarded()) {
            tiles.append("       VP:").append(modelView.getTiles(name)[2].getId()).append(", ON        ");
        } else
        if (!modelView.getTiles(name)[2].isActive() &&  !modelView.getTiles(name)[2].isDiscarded()){
            tiles.append("       VP:").append(modelView.getTiles(name)[2].getId()).append(", OFF       ");
        } else
        if (modelView.getTiles(name)[2].isDiscarded()){
            tiles.append("       DISCARDED       ");
        }

        tiles.append("+");
        System.out.println("                                             FAITH TRACK                                             ");
        System.out.println("+------------VP1---------VP2---------VP4---------VP6---------VP9---------VP12--------VP16-------VP20+");
        System.out.println(position);
        System.out.println(tiles);
        System.out.println("                    +---------------+           +-------------------+       +-----------------------+");

    }

    /**
     * This method is called when a player decides to see another player's situation.
     */
    private void showPlayer() {
        if (!modelView.isSoloGame()) {
            String chosen;
            List<String> names = modelView.getPlayers();
            names.remove(modelView.getName());
            System.out.println(">These are the players: ");
            for (String name : names) {
                System.out.println(" " + name);
            }
            System.out.println("\n>Which player do you want to see?");
            chosen = input.nextLine();
            while (!names.contains(chosen)) {
                System.out.println("Select one of those players.");
                chosen = input.nextLine();
            }

            printTrack(chosen);
            printDeps(chosen);
            printSlots(chosen);
            printLeaders(chosen);
        }
        else System.out.println(">This command is valid only for a multiplayer game!");
        printActions();
    }

    /**
     * Method used to print the Develop Card Slots.
     */
    private void printSlots(String name) {

        String[] card1 = Cards.getDevelopById(modelView.getTopId(modelView.getSlots(name).get(0)));
        String[] card2 = Cards.getDevelopById(modelView.getTopId(modelView.getSlots(name).get(1)));
        String[] card3 = Cards.getDevelopById(modelView.getTopId(modelView.getSlots(name).get(2)));

        String[] one = new String[4];  // the top cards
        StringBuilder list = new StringBuilder();  // the tracker of number of cards

        int lv;
        for (int slot = 0; slot < 3; slot++) {
            lv = 0;
            if (modelView.getTopId(modelView.getSlots(name).get(slot)) == 0) {
                list.append("                                            ");
            } else {
                while (lv < modelView.getSlots(name).get(slot).length) {
                    if (modelView.getSlots(name).get(slot)[lv] > 0) {
                        if (lv == 0) list.append("          ");
                        else list.append(", ");

                        list.append("LV").append(lv + 1).append(": ").append(Cards.getColorById(modelView.getSlots(name).get(slot)[lv]));
                        lv++;
                    } else break;
                }
            }
            while (list.length() <= 44*(slot+1)) list.append(" ");
        }

        System.out.println("                  SLOT0                                       SLOT1                                       SLOT2               ");
        System.out.println(list);

        for (int i = 0; i < 4; i++) {
            one[i] = card1[i] + "  " + card2[i] + "  " + card3[i];
        }

        for (int i = 0; i < 4; i++) {
            System.out.println(one[i]);
        }
    }

    //AnswerHandler notifies it of changes, cli reads the ModelView and prints the new state
    @Override
    public void update(String propertyName, Message message) {

        switch (propertyName.toUpperCase()) {
            case "START":
                System.out.println(message.getContent());
                //System.out.println(value.get("content"));
                break;

            case "OTHERCONNECTED":
                System.out.println(message.getContent());
                //System.out.println(value.get("content"));
                break;

            case "STARTED":
                System.out.println("Game started!");
                break;

            case "CHOOSELEADERS":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    chooseLeaders();
                } else {
                    System.out.println(message.getPlayer() + " is choosing his leaders!" );
                    //System.out.println(value.get("other") + " is choosing his leaders!" );
                }

                break;

            case "OKLEADERS":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    printBoard();
                } else {
                    System.out.println(message.getPlayer() + " has chosen his leaders!" );
                    //System.out.println(value.get("other") + " has chosen his leaders!" );
                }

                break;

            case "CHOOSERESOURCES":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (modelView.getName().equalsIgnoreCase(value.get("player"))) {
                    chooseResources(message.getResQty());
                    //chooseResources(Integer.parseInt(value.get("qty")));
                } else {
                    System.out.println(message.getPlayer() + " is choosing his initial resources!" );
                    //System.out.println(value.get("other") + " is choosing his initial resources!" );
                }

                break;

            case "OKRESOURCES":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    printBoard();
                } else {
                    System.out.println(message.getPlayer() + " has chosen his initial resources!" );
                    //System.out.println(value.get("other") + " has chosen his initial resources!" );
                }

                if(modelView.getPhase()==GamePhase.FULLGAME) printActions();

                break;

            case "YOURTURN":

                if (message.getPlayer().equalsIgnoreCase(modelView.getName())) {
                    //if (value.get("player").equalsIgnoreCase(modelView.getName())) {
                    System.out.println(message.getContent());
                    //System.out.println(value.get("content"));
                } else {
                    System.out.println("It's " + message.getPlayer() + "'s turn now!");
                    //System.out.println("It's " + value.get("player") + "'s turn now!");
                }

                printActions();
                break;

            case "PRODUCE":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    printBoard();
                } else {
                    System.out.println(message.getPlayer() + " has made some productions!" );
                    //System.out.println(value.get("other") + " has made some productions!" );
                }

                printActions();
                break;

            case "BUY":

                printBoard();

                if (!modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                    //if (value == null) {
                    // the player that has bought a dev card
                //} else {
                    System.out.println(message.getPlayer() + " has bought a Develop Card!" );
                    //System.out.println(value.get("other") + " has bought a Develop Card!" );
                }

                printActions();
                break;

            case "MARKET":

                printBoard();

                if (!modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    // the player that has bought a dev card
                    //printActions();
                //} else {
                    System.out.println(message.getPlayer() + " has taken resources from the Market!" );
                    //System.out.println(value.get("other") + " has taken resources from the Market!" );
                    if (message.getDiscarded() != 0) {
                    //if (Integer.parseInt(value.get("discarded")) != 0) {
                        System.out.println("Your position has been increased!");
                    }
                }
                printActions();
                break;

            case "SWAP":

                if (modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    printBoard();

                } else {
                    System.out.println(message.getPlayer() + " has swapped his deposits!" );
                    //System.out.println(value.get("other") + " has swapped his deposits!" );
                }
                printActions();
                break;

            case "ACTIVATE":

                if (!modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    printBoard();
                } else {
                    System.out.println(message.getPlayer() + " has activated his leader!");
                    //System.out.println(value.get("other") + " has activated his leader!");
                }
                printActions();
                break;

            case "DISCARD":

                if (!modelView.getName().equalsIgnoreCase(message.getPlayer())) {
                //if (message == null) {
                //if (value == null) {
                    printBoard();
                } else {
                    System.out.println(message.getPlayer() + " has discarded his leader!");
                    //System.out.println(value.get("other") + " has discarded his leader!");
                }
                printActions();
                break;

            case "ENDTURN":

                printBoard();

                if (modelView.getName().equalsIgnoreCase(message.getEndedPlayer())) {
                //if (modelView.getName().equalsIgnoreCase(value.get("endedTurnPlayer"))) {
                    if (modelView.isSoloGame()){
                        System.out.println("The Token that has been activated is: " + Cards.getTokenById(message.getToken()));
                        //System.out.println("The Token that has been activated is: " + Cards.getTokenById(Integer.parseInt(value.get("tokenActivated"))));
                    }
                } else {
                    System.out.println(message.getEndedPlayer() + " has ended his turn!" );
                    //System.out.println(value.get("other") + " has ended his turn!" );
                    if (!modelView.getName().equalsIgnoreCase(message.getCurrentPlayer())) {
                    //if (!modelView.getName().equalsIgnoreCase(value.get("currentPlayer"))) {
                        System.out.println("It's' " + message.getCurrentPlayer() + " turn now!");
                        //System.out.println("It's " + value.get("currentPlayer") + " turn now!");
                    }
                }

                if(!message.getCurrentPlayer().equalsIgnoreCase(modelView.getName())) printActions();


                break;

            case "ENDGAME":

                if (message.getWinner().equalsIgnoreCase(modelView.getName())) {
                //if (value.containsKey("winner") && value.get("winner").equalsIgnoreCase(modelView.getName())) {
                    System.out.println("You won! You made " + message.getWinnerPoints() + " points!");
                    //System.out.println("You won! You made " + value.get("winnerpoints") + " points!");
                } else {
                    System.out.println("You lost! You made " + message.getPoints() + " points! ");
                    //System.out.println("You lost! You made " + value.get("points") + " points! ");
                    if (!modelView.isSoloGame()) {
                        System.out.println(message.getWinner() + " won with " + message.getWinnerPoints() + " points! ");
                        //System.out.println(value.get("winner") + " won with " + value.get("winnerpoints") + " points! ");
                    }
                }

                setActiveGame(false);
                if (connectionSocket!=null) connectionSocket.close();
                System.exit(0);

                break;

            case "ERROR":

                if (message.getPlayer().equalsIgnoreCase(modelView.getName())) {
                //if (value.get("player").equalsIgnoreCase(modelView.getName())) {
                    System.out.println(message.getContent());
                    //System.out.println(value.get("content"));
                }
                if (message.getMethod().equalsIgnoreCase("chooseleaders")){
                //if (value.get("method").equalsIgnoreCase("chooseleaders")){
                    chooseLeaders();
                }
                else if (message.getMethod().equalsIgnoreCase("chooseresources")){
                //else if (value.get("method").equalsIgnoreCase("chooseresources")){
                    chooseResources(modelView.getInitialRes());
                }
                else printActions();
                break;

            case "END":
                System.out.println(message.getContent());
                //System.out.println(value.get("content"));
                System.exit(0);
                break;

            case "OTHERDISCONNECTED":
                System.out.println(message.getContent());
                //System.out.println(value.get("content"));
                break;

            default:
                System.out.println("\n");
                System.out.println("Unrecognized answer!");
                break;
        }
    }

}
