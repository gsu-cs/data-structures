package megamillion;

/**
 *
 * @author Keith Wellman
 * @date 9-5-2016
 * @purpose The purpose of this program is to allow a user to choose a specified
 * number of tickets to purchase (1-5) and generate that number of tickets populated
 * with random numbers. The first 5 numbers are sorted and have a range of 1-56 and
 * the last number is a special number from 1-46.
 * The first 5 numbers are all unique. After the user enters a number in the console,
 * the tickets are generated and displayed as arrays.
 * 
 * The purpose of the Ticket class is to hold the number of tickets and the methods
 * required to generate each ticket.
 * 
 * @datatype I used a 2 dimensional array as the primary data type.
 * 
 * @solution My solution for this problem involves using a Ticket object which has
 * 4 methods: buyTicket() - accepts number of tickets from console and calls genTickets 
 * with the number of tickets as the parameter
 * genTickets() - This method iterates through each ticket and it's contents to 
 * ensure there are no duplicates in the first 5 positions and makes calls to 
 * randomNumber and powerballRandomNumber to populate the array.
 * randomNumber() - generates a random number (1-56) using the Random class
 * powerballRandomNumber() - generates a random number (1-46) using the Random class
 * 
 * @algorithm The algorithm I used is to iterate through a 2 dimensional array to populate
 * each index with a random number while checking to ensure that number didn't already
 * exist.
 * 
 * @how Run the program and click in the console area to enter a number 1 - 5
 * Tickets will be generated and displayed as arrays.
 * 
 */
public class MegaMillion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Ticket ticket = new Ticket(); 
        ticket.buyTicket();
         
    }
    
}
