package it.polimi.ingsw.client;

import java.util.ArrayList;

public class Cards {
    //formatted representation of a LeaderCard
    public static String getLeaderById(int id){
        switch (id){
            case 1:
                return "Requirements: One DevelopCard of each color; "+"Points: 8; "+"Effect: No special effect;";
            case 2:
                return "Requirements: Position>=14 on the Faith Track; "+"Points: 7; "+"Effect: No special effect;";
            case 3:
                return "Requirements: One Yellow card and One Green card; "+"Points: 2; "+"Effect: Discount (-1 PURPLE);";
            case 4:
                return "Requirements: One Blue card and One Purple card; "+"Points: 2; "+"Effect: Discount (-1 BLUE);";
            case 5:
                return "Requirements: One Green card and One Blue card; "+"Points: 2; "+"Effect: Discount (-1 GREY);";
            case 6:
                return "Requirements: One Yellow card and One Purple card; "+"Points: 2; "+"Effect: Discount (-1 YELLOW);";
            case 7:
                return "Requirements: 5 Yellow resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for GREY);";
            case 8:
                return "Requirements: 5 Purple resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for BLUE);";
            case 9:
                return "Requirements: 5 Blue resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for YELLOW);";
            case 10:
                return "Requirements: 5 Grey resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for PURPLE);";
            case 11:
                return "Requirements: Two Yellow cards and One Blue card; "+"Points: 5; "+"Effect: WHITE=PURPLE (in Market);";
            case 12:
                return "Requirements: Two Green cards and One Purple card; "+"Points: 5; "+"Effect: WHITE=BLUE (in Market);";
            case 13:
                return "Requirements: Two Blue cards and One Yellow card; "+"Points: 5; "+"Effect: WHITE=GREY (in Market);";
            case 14:
                return "Requirements: Two Purple cards and One Green card; "+"Points: 5; "+"Effect: WHITE=YELLOW (in Market);";
            case 15:
                return "Requirements: One Yellow card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 BLUE, output=your choice +1 faith;";
            case 16:
                return "Requirements: One Blue card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 PURPLE, output=your choice +1 faith;";
            case 17:
                return "Requirements: One Purple card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 GREY, output=your choice +1 faith;";
            case 18:
                return "Requirements: One Green card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 YELLOW, output=your choice +1 faith;";
        }
        return null;
    }
    //representation of the color of the effect
    public static String getDiscountById(int id){
        switch (id){
            case 3: return "PURPLE";
            case 4: return "BLUE";
            case 5: return "GREY";
            case 6: return "YELLOW";
            default: return null;
        }
    }
    public static String getWhiteById(int id){
        switch (id){
            case 11: return "PURPLE";
            case 12: return "BLUE";
            case 13: return "GREY";
            case 14: return "YELLOW";
            default: return null;
        }
    }
    public static String getProductionById(int id){
        switch (id) {
            case 15: return "BLUE";
            case 16: return "PURPLE";
            case 17: return "GREY";
            case 18: return "YELLOW";
            default: return null;
        }
    }
    //formatted representation of a DevelopCard
    public static String getDevelopById(int id){
        switch (id){
            case 1:
                return "Col:Purple; "+"Lev:1; "+"Cost:[2P]; "+"In:[1G]; "+"Out:[1F]; "+"VP:1;";
            case 2:
                return "Color: Purple; "+"Level: 1; "+"Cost: [2 PURPLE, 2 GREY]; "+"Input: [1 YELLOW, 1 BLUE]; "+"Output: [2 GREY, 1 Faith]; "+"Points: 4;";
            case 3:
                return "Color: Purple; "+"Level: 1; "+"Cost: [3 PURPLE]; "+"Input: [2 YELLOW]; "+"Output: [1 PURPLE, 1 BLUE, 1 GREY]; "+"Points: 3;";
            case 4:
                return "Color: Purple; "+"Level: 1; "+"Cost: [1 BLUE, 1 PURPLE, 1 YELLOW]; "+"Input: [1 YELLOW]; "+"Output: [1 BLUE]; "+"Points: 2;";
            case 5:
                return "Color: Blue; "+"Level: 1; "+"Cost: [1 YELLOW, 1 PURPLE, 1 GREY]; "+"Input: [1 PURPLE]; "+"Output: [1 GREY]; "+"Points: 2;";
            case 6:
                return "Color: Blue; "+"Level: 1; "+"Cost: [2 YELLOW 2 PURPLE]; "+"Input: [1 BLUE, 1 GREY]; "+"Output: [2 PURPLE, 1 Faith]; "+"Points: 4;";
            case 7:
                return "Col:Blue; "+"Lev:1; "+"Cost:[2Y]; "+"In:[1B]; "+"Out:[1F]; "+"VP:1;";
            case 8:
                return "Col:Blue; "+"Lev:1; "+"Cost:[3Y]; "+"In:[2G]; "+"Out:[1Y, 1P, 1B]; "+"VP:3;";
            case 9:
                return "Color: Green; "+"Level: 1; "+"Cost: [1 BLUE, 1 PURPLE, 1 GREY]; "+"Input: [1 GREY]; "+"Output: [1 PURPLE]; "+"Points: 2;";
            case 10:
                return "Col:Green; "+"Lev:1; "+"Cost:[3B]; "+"In:[2P]; "+"Out:[1Y, 1B, 1G]; "+"VP:3;";
            case 11:
                return "Color: Green; "+"Level: 1; "+"Cost: [2 BLUE]; "+"Input: [1 YELLOW]; "+"Output: [1 Faith]; "+"Points: 1;";
            case 12:
                return "Color: Green; "+"Level: 1; "+"Cost: [2 BLUE, 2 YELLOW]; "+"Input: [1 GREY, 1 PURPLE]; "+"Output: [2 YELLOW, 1 Faith]; "+"Points: 4;";
            case 13:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [3 GREY]; "+"Input: [2 BLUE]; "+"Output: [1 YELLOW, 1 PURPLE, 1 GREY]; "+"Points: 3;";
            case 14:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [2 GREY, 2 BLUE]; "+"Input: [1 YELLOW, 1 PURPLE]; "+"Output: [2 BLUE, 1 Faith]; "+"Points: 4;";
            case 15:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [2 GREY]; "+"Input: [1 PURPLE]; "+"Output: [1 Faith]; "+"Points: 1;";
            case 16:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [1 BLUE, 1 GREY, 1 YELLOW]; "+"Input: [1 BLUE]; "+"Output: [1 YELLOW]; "+"Points: 2;";
            case 17:
                return "Color: Purple; "+"Level: 2; "+"Cost: [4 PURPLE]; "+"Input: [1 YELLOW]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 18:
                return "Color: Purple; "+"Level: 2; "+"Cost: [3 PURPLE, 3 BLUE]; "+"Input: [2 GREY]; "+"Output: [2 PURPLE, 1 Faith]; "+"Points: 8;";
            case 19:
                return "Color: Purple; "+"Level: 2; "+"Cost: [5 PURPLE]; "+"Input: [2 GREY]; "+"Output: [2 YELLOW, 2 Faith]; "+"Points: 7;";
            case 20:
                return "Color: Purple; "+"Level: 2; "+"Cost: [3 PURPLE, 2 YELLOW]; "+"Input: [1 YELLOW, 1 PURPLE]; "+"Output: [3 BLUE]; "+"Points: 6;";
            case 21:
                return "Color: Blue; "+"Level: 2; "+"Cost: [3 YELLOW, 2 GREY]; "+"Input: [1 YELLOW, 1 GREY]; "+"Output: [3 PURPLE]; "+"Points: 6;";
            case 22:
                return "Color: Blue; "+"Level: 2; "+"Cost: [5 YELLOW]; "+"Input: [2 PURPLE]; "+"Output: [2 BLUE, 2 Faith]; "+"Points: 7;";
            case 23:
                return "Color: Blue; "+"Level: 2; "+"Cost: [4 YELLOW]; "+"Input: [1 PURPLE]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 24:
                return "Color: Blue; "+"Level: 2; "+"Cost: [3 YELLOW, 3 GREY]; "+"Input: [1 PURPLE]; "+"Output: [2 GREY, 1 Faith]; "+"Points: 8;";
            case 25:
                return "Color: Green; "+"Level: 2; "+"Cost: [3 BLUE, 2 PURPLE]; "+"Input: [1 BLUE, 1 PURPLE]; "+"Output: [3 GREY]; "+"Points: 6;";
            case 26:
                return "Color: Green; "+"Level: 2; "+"Cost: [4 BLUE]; "+"Input: [1 GREY]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 27:
                return "Color: Green; "+"Level: 2; "+"Cost: [3 BLUE, 3 YELLOW]; "+"Input: [1 YELLOW]; "+"Output: [2 BLUE, 1 Faith]; "+"Points: 8;";
            case 28:
                return "Color: Green; "+"Level: 2; "+"Cost: [5 BLUE]; "+"Input: [2 YELLOW]; "+"Output: [2 GREY, 2 Faith]; "+"Points: 7;";
            case 29:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [3 GREY, 2 BLUE]; "+"Input: [1 GREY, 1 BLUE]; "+"Output: [3 YELLOW]; "+"Points: 6;";
            case 30:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [3 GREY, 3 PURPLE]; "+"Input: [1 BLUE]; "+"Output: [2 YELLOW, 1 Faith]; "+"Points: 8;";
            case 31:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [5 GREY]; "+"Input: [2 BLUE]; "+"Output: [2 PURPLE, 2 Faith]; "+"Points: 7;";
            case 32:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [4 GREY]; "+"Input: [1 BLUE]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 33:
                return "Color: Purple; "+"Level: 3; "+"Cost: [4 PURPLE, 4 BLUE]; "+"Input: [1 YELLOW]; "+"Output: [3 GREY, 1 PURPLE]; "+"Points: 12;";
            case 34:
                return "Color: Purple; "+"Level: 3; "+"Cost: [5 PURPLE, 2 YELLOW]; "+"Input: [1 GREY, 1 BLUE]; "+"Output: [2 YELLOW, 2 PURPLE, 1 Faith]; "+"Points: 10;";
            case 35:
                return "Color: Purple; "+"Level: 3; "+"Cost: [7 PURPLE]; "+"Input: [1 YELLOW]; "+"Output: [1 GREY, 3 Faith]; "+"Points: 11;";
            case 36:
                return "Color: Purple; "+"Level: 3; "+"Cost: [6 PURPLE]; "+"Input: [2 GREY]; "+"Output: [3 YELLOW, 2 Faith]; "+"Points: 9;";
            case 37:
                return "Color: Blue; "+"Level: 3; "+"Cost: [4 YELLOW, 4 GREY]; "+"Input: [1 PURPLE]; "+"Output: [1 YELLOW, 3 BLUE]; "+"Points: 12;";
            case 38:
                return "Color: Blue; "+"Level: 3; "+"Cost: [6 YELLOW]; "+"Input: [2 PURPLE]; "+"Output: [3 BLUE, 2 Faith]; "+"Points: 9;";
            case 39:
                return "Color: Blue; "+"Level: 3; "+"Cost: [7 YELLOW]; "+"Input: [1 GREY]; "+"Output: [1 BLUE, 3 Faith]; "+"Points: 11;";
            case 40:
                return "Color: Blue; "+"Level: 3; "+"Cost: [5 YELLOW, 2 GREY]; "+"Input: [1 YELLOW, 1 BLUE]; "+"Output: [2 PURPLE, 2 GREY, 1 Faith]; "+"Points: 10;";
            case 41:
                return "Color: Green; "+"Level: 3; "+"Cost: [5 BLUE, 2 PURPLE]; "+"Input: [1 YELLOW, 1 PURPLE]; "+"Output: [2 BLUE, 2 GREY, 1 Faith]; "+"Points: 10;";
            case 42:
                return "Color: Green; "+"Level: 3; "+"Cost: [6 BLUE]; "+"Input: [2 PURPLE]; "+"Output: [3 GREY, 2 Faith]; "+"Points: 9;";
            case 43:
                return "Color: Green; "+"Level: 3; "+"Cost: [4 BLUE, 4 YELLOW]; "+"Input: [1 GREY]; "+"Output: [3 YELLOW, 1 BLUE]; "+"Points: 12;";
            case 44:
                return "Color: Green; "+"Level: 3; "+"Cost: [7 BLUE]; "+"Input: [1 PURPLE]; "+"Output: [1 YELLOW, 3 Faith]; "+"Points: 11;";
            case 45:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [6 GREY]; "+"Input: [2 BLUE]; "+"Output: [3 PURPLE, 2 Faith]; "+"Points: 9;";
            case 46:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [7 GREY]; "+"Input: [1 BLUE]; "+"Output: [1 PURPLE, 3 Faith]; "+"Points: 11;";
            case 47:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [4 GREY, 4 PURPLE]; "+"Input: [1 BLUE]; "+"Output: [1 GREY, 3 PURPLE]; "+"Points: 12;";
            case 48:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [5 GREY, 2 PURPLE]; "+"Input: [1 GREY, 1 PURPLE]; "+"Output: [2 YELLOW, 2 BLUE, 1 Faith]; "+"Points: 10;";
        }
        return null;
    }
    //For each resource requested as cost, it gives a String representation of it
    public static ArrayList<String> getCostById(int id, ArrayList<String> colors){

        return null;
    }
    //For each resource requested as input, it gives a String representation of it
    public static ArrayList<String> getInputById(int id){

        return null;
    }

}
