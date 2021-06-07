package it.polimi.ingsw;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.server.Server;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main App class. Its main is the beginning of the application and let the user select what he wants to launch (Server,
 * CLI, GUI).
 */
public class App {
    /**
     * Method main selects CLI, GUI or Server based on the arguments provided.
     *
     * @param args of type String[]
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0].toUpperCase()) {
                case "SERVER":
                    Server.main(null);
                    break;
                case "CLI":
                    CLI.main(null);
                    break;
                case "GUI":
                    System.out.println("You selected the GUI interface, have fun!\nStarting...");
                    GUI.main(null);
                    break;
                default:
                    System.err.println("Invalid argument, please run the executable again.");
                    break;
            }
        } else {
            System.out.println("Starting GUI...");
            GUI.main(null);
        }
    }
}
