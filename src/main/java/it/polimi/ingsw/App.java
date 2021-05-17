package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.Controllers.ControllerProva;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.Main;
import it.polimi.ingsw.server.Server;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main App class. Its main is the beginning of the application and let the user select what he wants to launch (Server,
 * CLI, GUI).
 */
public class App 
{
    /**
     * Method main selects CLI, GUI or Server based on the arguments provided.
     * @param args of type String[]
     */
    public static void main(String[] args){
        /*System.out.println("Hi! Welcome to Master of Renaissance!\nWhat do you want to launch?");
        System.out.println("0. SERVER\n1. CLIENT (CLI INTERFACE)\n2. CLIENT (GUI INTERFACE)");
        System.out.println("\n>Type the number of the desired option!");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            System.exit(-1);
        }
        switch (input) {*/
        switch (2) {
            case 0:
                Server.main(null);
                break;
            case 1:
                CLI.main(null);
                break;
            case 2:
                System.out.println("You selected the GUI interface, have fun!\nStarting...");
                //GUI.main(null);
                //ControllerProva.main(null);
                Main.main(null);
                break;
            default:
                System.err.println("Invalid argument, please run the executable again.");
                break;
        }
    }
}
