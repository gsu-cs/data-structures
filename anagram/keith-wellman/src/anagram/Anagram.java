/*
 * Author: Keith Wellman
 * Date: 9-21-16
 * Purpose: The purpose of this program is to find and group anagrams from an input file containing a list of 
 * words and output the words that are anagrams to the console and to an output file. 
 *
 * Solution: The solution I developed was to scan in each word from the input file to a vector and make a copy
 * of that vector to a "signature" vector. Each word in the signature vector is filtered to remove all non alphabetic
 * characters. After the filter, each word is put into a character array and sorted by letter. The next step was to 
 * sort both the original vector and signature together using the signature vector as the key to the sort. 
 * For this, I used the bubble sort algorithm and by using a parallel sort I was then able to check for anagrams by 
 * iterating through the signature vector looking for matching strings. 
 *
 * Data Structures: The data structures I used included 2 vectors and an AnagramFilter class whose purpose was to 
 * hold methods that would perform actions on the given vectors. 
 * 
 * To use: Change the directory in FILE_PATH = "//Users//keithwellman//Documents//Fall 2016//Data Structure//testfile/"
 * and in the AnagramFilter class OUTPUT_FILE_PATH = "//Users//keithwellman//Documents//Fall 2016//Data Structure//testfile//"
 * to the location where you input file of words is located. Run the program and type in the name of the file and press enter.
 * After running a list of anagrams from the file will be displayed and written to output.txt in the directory chosen.
 * 
 * Purpose of AnagramFilter class: This class contains methods that will filter words to remove non-alphabetic characters in a vector,
 * sort the letters of each word in a vector alphabetically, sort 2 vectors using one as a signature, and print anagrams to the screen 
 * and an output file. 
 */
package anagram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author keithwellman
 */
public class Anagram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Vector<String> fileContents = new Vector<>(50);
        Vector<String> signature = new Vector<>(50);
        Vector<String> tempVector = new Vector<>(50);
        Scanner fileinput = null; 
        int i = 0;
        int wordcount = 0;
        final String FILE_PATH = "//Users//keithwellman//Documents//Fall 2016//Data Structure//testfile/";
        
        Scanner input = new Scanner(System.in);
        // prompt user for name of file and assign to inputFile
        System.out.print("Please enter name of file: ");
        String inputFile = input.next();
        
        File inFile = new File(FILE_PATH + inputFile);
        input.close();
        
        try {
            fileinput = new Scanner(inFile);
            if (!fileinput.hasNext()) { // check if file is empty
                System.out.println("the input file is empty");
                System.exit(1);
            }
            while (fileinput.hasNext()) { // get words from file and add to vector
                String temp = fileinput.next();
                if (temp.length() <= 15) { //check length of word <=  15
                    fileContents.add(temp); //put word in vector
                    wordcount++; // increment word count
                    if (wordcount > 50) { // check word count < 50
                        System.out.println("There are more than 50 words!");
                        System.exit(1);
                    }
                } 
            }
            fileinput.close();
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(1);
        } finally {
            fileinput.close();
        }
        
        AnagramFilter filter = new AnagramFilter();
        
        tempVector = (Vector)fileContents.clone(); //copy vector so as to not overwrite original vector
        signature = filter.wordFilter(tempVector); //remove non-alphabetic characters
        filter.charSort(signature); //sort signature words by character
        filter.duelSort(fileContents, signature); //sort vectors together based on signature vector
        System.out.println("\nAnagrams:\n");
        filter.printAnagrams(fileContents, signature);
        System.out.println("\n\n");
      
    }
}
