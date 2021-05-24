package it.polimi.ingsw;


import java.util.InputMismatchException;
import java.util.Scanner;

public class FakeStartParams {

    Boolean asGui = true; // default.
    Boolean asServer = false;
    String serverIP = "127.0.0.0.1";
    int serverPort = 1234;


    static FakeStartParams fromArgs(String[] args){

        FakeStartParams fp = new FakeStartParams();

        // TODO: call: AskToHumans
        // here simply:
        fp.asGui = true;

        return fp;
    }


    void AskToHumans() {

        System.out.println("Hi! Welcome to Master of Renaissance!\nWhat do you want to launch?");
        System.out.println("0. SERVER\n1. CLIENT (CLI INTERFACE)\n2. CLIENT (GUI INTERFACE)");
        System.out.println("\n>Type the number of the desired option!");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (
                InputMismatchException e) {
            System.err.println("Numeric format requested, application will now close...");
            // TODO: no, create a routine that will ask until ok
            System.exit(-1);
        }

        // TODO: fill and return
    }
}
