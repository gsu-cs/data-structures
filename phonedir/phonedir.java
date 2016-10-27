/**
 * @author Kyle McKean
 * @since  10-26-16
 * <h1> Assignment 4: phonedir </h1>
 * Compilation: javac phonedir.java
 * Execution: java -jar phonedir.jar
 * Documentation: javadoc phonedir.java
 * -- This source code is fully javadoc compliant
 * Purpose of Program:
 * -- The purpose of the program 'phonedir' is to implement a basic
 *    phone book program with a command line interface.
 * Solution:
 * -- The main algorithm used is this program is insertion sort.
 *    Everytime you put a value into the list you insert it as if
 *    you where performing the insertion portion of an insertion sort.
 *    This keeps the invariant that the list is always sorted.
 *    One small caveat is that when you update the lastname of a <tt> Record </tt>
 *    you should first remove the element then modify it and re-insert it. This
 *    maintains the invariant.
 * Data Strutures: Linked Lists
 * Description of Use:
 * -- Follow Execution above
 * Expected I/O:
 * -- Refer to <a href="http://grid.cs.gsu.edu/jbhola/CSc_2720/Fall16/assign4_phon.html"> reference. </a>
 *    The basic outline is as follows:
 *    1: Show commands
 *    2: Prompt for input
 *    3: Execute a command based on input
 *    4: goto 1
 * Explaination of Classes:
 * -- See above each class
 * -- The <tt> phonedir </tt> class is the main entry point for the program and contains the main
 *    exection loop.
 * <strong> Important </strong>
 * -- Assume every parameter has an implicit non null pre-condition
 *    Assume every return value has an implicit non null post-condition
 */
import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.*;
import java.util.stream.*;
import java.util.Optional;
public class phonedir {
    private static boolean running;
    public static void main(String[] args) {
        running = true;
        Scanner s = new Scanner(System.in);
        LinkedList<Command> commands = new LinkedList<>();
        commands.add(new Command('a', "Show all records", olist -> {
                    boolean notEmpty = false;
                    String fname   = "First Name";
                    String lname   = "Last Name";
                    String pnumber = "Phone Number";
                    int fnameMaxLen = fname.length();
                    int lnameMaxLen = lname.length();
                    int pnumberMaxLen = pnumber.length();
                    for (Record r:olist) {
                        notEmpty = true;
                        int fnameCurLen = r.firstname.length();
                        if (fnameMaxLen < fnameCurLen) fnameMaxLen = fnameCurLen;
                        int lnameCurLen = r.lastname.length();
                        if (lnameMaxLen < lnameCurLen) lnameMaxLen = lnameCurLen;
                        int pnumberCurLen = r.phoneNumber.length();
                        if (pnumberMaxLen < pnumberCurLen) pnumberMaxLen = pnumberCurLen;
                    }
                    if (notEmpty) {
                        String format = "%-" + fnameMaxLen + "s %-" + lnameMaxLen + "s %-" + pnumberMaxLen + "s\n";
                        System.out.format(format, fname, lname, pnumber);
                        String line   = "%." + fnameMaxLen + "s %." + lnameMaxLen + "s %." + pnumberMaxLen + "s\n";
                        String dashes = "--------------------------------------------";
                        System.out.format(line, dashes, dashes, dashes);
                        for (Record r:olist) {
                            System.out.format(format, r.firstname, r.lastname, r.phoneNumber);
                        }
                    } else {
                        System.out.println("No records.");
                    }
        }));
        commands.add(new Command('d', "Delete the current record", olist -> {
                    Record deleted = olist.delete();
                    if (deleted == null) {
                        System.out.println("No current record");
                    } else {
                        System.out.println("Deleted: " + deleted);
                    }
        }));
        commands.add(new Command('f' ,"Change the first name in the current record", olist -> {
                    Record selected = olist.delete();
                    if (selected == null) {
                        System.out.println("No current record");
                    } else {
                        selected.updateFirstname();
                        olist.insert(selected);
                        System.out.println("Current record is: " + olist.getSelection());
                    }
        }));
        commands.add(new Command('l', "Change the last name in the current record", olist -> {
                    Record selected = olist.delete();
                    if (selected == null) {
                        System.out.println("No current record");
                    } else {
                        selected.updateLastname();
                        olist.insert(selected);
                        System.out.println("Current record is: " + olist.getSelection());
                    }
        }));
        commands.add(new Command('n', "Add a new record", olist -> {
                    olist.insert(new Record(s));
                    System.out.println("Current record is: " + olist.getSelection());
        }));
        commands.add(new Command('p', "Change the phone number in the current record", olist -> {
                    Record selected = olist.delete();
                    if (selected == null) {
                        System.out.println("No current record");
                    } else {
                        selected.updatePhoneNumber();
                        olist.insert(selected);
                        System.out.println("Current record is: " + olist.getSelection());
                    }
        }));
        commands.add(new Command('q', "Quit", olist -> { running = false; }));
        commands.add(new Command('s', "Select a record from the record list to become the current record", olist -> {
                    Record builder = new Record();
                    builder.setScanner(s);
                    builder.updateFirstname();
                    builder.updateLastname();
                    if (olist.select(builder)) {
                        System.out.println("Current record is: " + olist.getSelection());
                    } else {
                        System.out.println("No matching record found.");
                    }
        }));
        OList<Record> records = new OList<>();
        String helpText = "";
        for(Command c: commands) {
            helpText += "    " + c + "\n";
        }
        System.out.println("A program to keep a Phone Directory:");
        while (running) {
            System.out.print(helpText);
            System.out.print("Enter a command from the list above (q to quit): ");
            char c = s.next().charAt(0);
            boolean notFound = true;
            for(Command command:commands) {
                if (command.isCommand(c)) {
                    command.perfomAction(records);
                    notFound = false;
                    break;
                }
            }
            if (notFound) {
                System.out.println("Illegal command");
            }
        }
    }
}
