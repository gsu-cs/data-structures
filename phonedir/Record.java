/**
 * The <tt> Record </tt> class is really just a tuple class.
 * The construtor Record(Scanner s) is prompts the user for
 * all of the Record's data.
 **/
import java.util.Scanner;
public class Record implements Comparable<Record> {
    public  String  firstname;
    public  String  lastname;
    public  String  phoneNumber;
    private Scanner scanner;
    public Record() { }
    public Record(Scanner s) {
        this.scanner = s;
        this.updateFirstname();
        this.updateLastname();
        this.updatePhoneNumber();
    }
    public void setScanner(Scanner s) { this.scanner = s; }
    @Override
    public String toString() {
        return this.firstname + " " + this.lastname + " " + this.phoneNumber;
    }
    public void updateFirstname() {
        System.out.print("Enter first name: ");
        this.firstname = this.scanner.next();
    }
    public void updateLastname() {
        System.out.print("Enter last name: ");
        this.lastname = this.scanner.next();
    }
    public void updatePhoneNumber() {
        System.out.print("Enter phone number: ");
        this.phoneNumber = this.scanner.next();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        Record rother = (Record) other;
        return this.firstname.equals(rother.firstname) &&
               this.lastname.equals(rother.lastname);
    }
    @Override
    public int compareTo(Record other) {
        return this.lastname.compareTo(other.lastname);
    }
}
