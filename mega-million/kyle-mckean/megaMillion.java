/*********************************************************************************
 * Assignment 1: megaMillion by Kyle McKean
 * 1: Purpose of the Program
 * -- The high level purpose of this program is to generate lottery tickets in
 *    a specific manner. The exact program loop is as follows:
 * -- 1: Ask the user the number of tickets they wish to purchase.
 * -- 2: Generate that number of tickets and print them to stdout.
 * ---- Discussion of Ticket construction:
 * ------ 1: There must be two groups and the first group must contain 5
 *           randomly generated unique numbers in the inclusive range 1-56.
 *           The second group will be a random number in the inclusive range 1-46.
 * ------ 2: All tickets must differ by at least one number (ie: no duplicates).
 * -- 3: Ask the user if they wish to buy more tickets.
 * ---- A: user input = "yes" => goto 1
 * ---- B: user input = "no" => halt program
 * 2: Solution and Algorithms
 * -- Solution: Generate tickets using the linear search algorithm described below
                to check for duplicates at the ticket and group level.
 * -- Algorithms: The only algorithm of substance used in this program is called a
 *                linear search. When generating random unique numbers, I generate a
 *                number then perform a linear search over the array to check for
 *                duplication.
 * 3: Data Structures
 * -- Arrays: I used an array in the Ticket object as the number of elements in
 *            group1 is statically known and not equal to 1.
 * 4: Use of program and I/O
 * -- Use: `java -jar megaMillion.jar` should begin execution. After that, the
 *         prompts are trivial to follow.
 * -- I/O: An example interaction should be as follows:
 *         Please enter the number of tickets you wish to purchase: (User enters: 3)
 *          5 49 50 51 56  16
 *          2  3 17 42 55  14
 *         14 15 32 36 43  26
 *         Do you wish to buy more tickets? [Y/n]: (User enters: no)
 *         #Program Termination#
 * 5: Class Explanations: Please look above each class for it's documentation comment.
 *********************************************************************************/
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.String;

public class megaMillion {

   /****************************************************************************
    * This class stores the random generator to minimize the calls to system
    * level entropy generators which could in theory be slow.
    * Method getRandom(): Returns an int in the range (min,max) inclusive.
    ***************************************************************************/
    private static class RandomRange {
        private int min;
        private int max;
        private Random gen;
        public RandomRange(int min, int max) {
            this.min = min;
            this.max = max;
            this.gen = new Random();
        }
        public int getRandom() {
            return this.gen.nextInt(this.max - this.min + 1) + this.min;
        }
    }

   /****************************************************************************
    * This is a helper function to ensure well formated output. It checks if
    * the number of digits in the number is 1. If it is it inserts a space.
    ***************************************************************************/
    private static String twoDigitIntToString(int i) {
        int numDigits = (int) (Math.log10(i)+1);
        if (numDigits == 1) {
            return " " + i;
        } else {
            return "" + i;
        }
    }

   /****************************************************************************
    * This class bundles up ticket generation and pretty printing.
    * Constructors:
    * -- Ticket(): Default ticket constructor group1 size set at 5.
    * -- Ticket(n): Makes a ticket with group1 size set to n.
    * Method reGenerate(): Changes group2 to a new random number different
    *                      from the old group2.
    * Method equals(other): Standard value level equality
    * Method toString(): Standard pretty printing
    ***************************************************************************/
    private static class Ticket {
        private int[] group1;
        private int   group2;
        public Ticket() { this(5); }
        public Ticket(int group1Length) {
            RandomRange group1Range = new RandomRange(1,56);
            RandomRange group2Range = new RandomRange(1,46);
            this.group1 = new int[group1Length];
            for(int i=0; i < group1Length; i++) {
                int rand;
                boolean foundUnique;
                do {
                    rand = group1Range.getRandom();
                    foundUnique = true; // Asume found
                    for(int j=0; j < i; j++) { // Search up to initalizated values
                        if (this.group1[j] == rand) {
                            foundUnique = false;
                            break;
                        }
                    }
                } while (!foundUnique);
                this.group1[i] = rand;
            }
            Arrays.sort(this.group1);
            this.group2 = group2Range.getRandom();
        }
        public void reGenerate() {
            RandomRange group2Range = new RandomRange(1,46);
            int newGroup2;
            boolean foundUnique = false;
            do {
                newGroup2 = group2Range.getRandom();
                if (newGroup2 != this.group2) foundUnique = true;
            } while (!foundUnique);
            this.group2 = newGroup2;
        }
        public boolean equals(Ticket other) {
            if (this == other) return true; // If the pointers are equal the objects are trivally equal
            if (this.group2 != this.group2 ||
                this.group1.length != other.group1.length) return false;
            for(int i=0; i < this.group1.length; i++) {
                if (this.group1[i] != other.group1[i]) return false;
            }
            return true;
        }

        @Override
        public String toString() {
            String group1Str = "[ ";
            String group2Str = "[ " + twoDigitIntToString(this.group2) + " ]";
            for(int i=0; i < this.group1.length; i++) {
                group1Str += twoDigitIntToString(this.group1[i]) + " ";
            }
            return group1Str + "] " + group2Str;
        }
    }

   /****************************************************************************
    * This function generates n unique tickets and stores them in an Array.
    ***************************************************************************/
    private static Ticket[] generate(int numberTickets) {
        Ticket[] tickets = new Ticket[numberTickets];
        for(int i=0; i < numberTickets; i++) {
            Ticket ticket = new Ticket();
            boolean foundUnique;
            do {
                foundUnique = true;
                for(int j=0; j < i; j++) {
                    if (ticket.equals(tickets[j])) {
                        ticket.reGenerate();
                        foundUnique = false;
                    }
                }
            } while (!foundUnique);
            tickets[i] = ticket;
        }
        return tickets;
    }

    public static void main(String[] args) {
        boolean running = true;
        Scanner input = new Scanner(System.in);
        while (running) {
            int numberTickets = 0; // Java's initalization checker isn't smart enough, but it will get reset
            boolean selected = false;
            System.out.print("Please enter the number of tickets you wish to purchase: ");
            while (!selected) {
                try {
                    numberTickets = input.nextInt();
                    if (1 <= numberTickets && numberTickets <= 5) {
                        selected = true;
                    } else {
                        System.out.print("Please enter a number between 1 and 5: ");
                    }
                } catch (java.util.InputMismatchException e) {
                    input.next(); // Remove un-parsable token, this is very important
                    System.out.print("Please enter a vaild integer: ");
                }
            }
            Ticket[] tickets = generate(numberTickets);
            for(int i=0; i < tickets.length; i++) {
                System.out.println("Ticket# " + (i + 1) + ": " + tickets[i]);
            }
            selected = false;
            System.out.print("Do you wish to buy more tickets? [Y/n]: ");
            while(!selected) {
                String response = input.next();
                selected = true;
                switch (response.toLowerCase()) {
                    case "yes":
                        break;
                    case "y":
                        break;
                    case "no":
                        running = false;
                        break;
                    case "n":
                        running = false;
                        break;
                    default:
                        selected = false;
                        System.out.print("Please input [Y]es or [N]o: ");
                        break;
                }
            }
        }
    }

}
