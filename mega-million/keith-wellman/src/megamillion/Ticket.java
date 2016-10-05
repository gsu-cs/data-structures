package megamillion;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Keith Wellman
 * @date 9-5-2016
 */
public class Ticket {
    private int numOfTickets;
    
    public void buyTicket() {
        
        //--------------------------------------------------
        //Asks user for number of tickets (1-5) and calls genTickets
        //Precondition: numOfTickets isn't initialized with any value and no tickets exist
        //Postcondition: numOfTickets holds the user selected value as an int and 
        // genTickets(numOfTickets) is called. The return value of genTickets is 
        //converted to a string and the tickets are printed to the screen
        //--------------------------------------------------
        
        System.out.println("Please enter the number of tickets you'd like (1-5):");
        Scanner input = new Scanner(System.in);
        numOfTickets = 0;
        while (numOfTickets < 1 || numOfTickets > 5) {
            System.out.println("You may purchase from 1 to 5 tickets.");
            numOfTickets = input.nextInt();
        }
        System.out.println("");
        System.out.println("Generating random tickets...");
        System.out.println(Arrays.deepToString(genTickets(numOfTickets)));
    }
    
    /**
     * @param n the number of tickets chosen by user
     * @return populated 2 dimensional array of ints
     */
    public int[][] genTickets(int n) {
        //--------------------------------------------------
        // returns an array of ticket arrays (2 dimensional)
        // Precondition: n is equal to the number of tickets
        // Postcondition: A 2 dimensional array is initialized and populated with
        // random numbers by calls to randomNumber() and powerballRandomNumber().
        //--------------------------------------------------
        
        int tickets[][] = new int[n][6];
        for(int i = 0; i < n; i++) {
            for(int x = 0; x < 5; x++) { // GROUP 1
                int tempRandom = 0;
                boolean existsInArray = true;
                
                while (existsInArray) { 
                    tempRandom = randomNumber(); // keep generating random numbers until the random number isn't already in the array
                    for(int z = 0; z < 5; z++) { // check if num exists
                        if (tempRandom != tickets[i][z]) {
                            existsInArray = false;
                        }
                    }
                }
                
                tickets[i][x] = tempRandom;
            } 
            Arrays.sort(tickets[i], 0, 5);
            tickets[i][5] = powerballRandomNumber();
        }
        
        return tickets;
    }
    
    /**
     * @return a random number as an int
     */
    public int randomNumber() {
        //--------------------------------------------------
        // Generates a random number from 1-56
        // Precondition: n/a
        // Postcondition: A random number is returned.
        //--------------------------------------------------
        
        Random ranGen = new Random();
        int randomNum = 0;
        while (randomNum == 0) {
            randomNum = ranGen.nextInt(56);
        }
        return randomNum;
    }
    
    /**
     * @return a random number as an int
     */
    public int powerballRandomNumber() {
        //--------------------------------------------------
        // Generates a random number from 1-46
        // Precondition: n/a
        // Postcondition: A random number is returned.
        //--------------------------------------------------
        
        Random ranGen = new Random();
        int randomNum = 0;
        while (randomNum == 0) {
            randomNum = ranGen.nextInt(46);
        }
        return randomNum;
    }
    
    
    
}
