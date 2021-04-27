package it.polimi.ingsw.client.cli;

import com.google.gson.Gson;
import it.polimi.ingsw.client.ActionParser;
import it.polimi.ingsw.client.AnswerHandler;
import it.polimi.ingsw.client.ConnectionSocket;
import it.polimi.ingsw.client.ModelView;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
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
                    input.reset();
                    System.out.println(">You chose: " + name);
                    System.out.println(">Is it ok? [yes/no] ");
                    System.out.print(">");
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
            listener.addPropertyChangeListener(new ActionParser(connectionSocket, modelView));
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

    @Override
    public void run() {
        setup();

        System.out.println("Hello!");
    }

    //AnswerHandler notifies it of changes, cli reads the ModelView and prints the new state
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
