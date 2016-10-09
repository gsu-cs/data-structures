/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anagram;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author keithwellman
 */
public class AnagramFilter {
    
    // remove all non alphabetic characters from each word in vector
    /*
     * Precondition: Vector list of words which may include non alphabetic characters: ex: it's
     * Postcondition: Vector list is returned void of any non-alphabetic characters.
     */
    public Vector<String> wordFilter(Vector<String> words) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i) != null) {
                words.set(i, words.get(i).replaceAll("[^a-zA-Z]", "").toLowerCase());
            }
        }    
        return words;
    }
    
    // sort the characters in each word of the vector
    /*
     * Precondition: Vector list of words containing only alphabetic characters.
     * Postcondition: Vector list is returned with each word sorted by letter alphabetically.
     */
    public void charSort(Vector<String> words) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i) != null) {
                char[] chars = words.get(i).toCharArray();
                Arrays.sort(chars);
                words.set(i, new String(chars));
            }
        }
    }
    
    // use bubble sort to sort both vectors based on the signature vector
    /*
     * Precondition: Unsorted vector list of words and a parallel vector list of words with each
     * word sorted by character alphabetically.
     * Postcondition: Both vectors are sorted by the signature vector. 
     */
    public void duelSort(Vector<String> origVector, Vector<String> sigVector) { 
        boolean flag = true;
        String tempA; 
        String tempB;
        
        while (flag) { 
            flag = false;
            for (int i = 0; i < sigVector.size() - 1; i++) {
                if (sigVector.get(i).compareTo(sigVector.get(i+1)) > 0) {
                    tempA = sigVector.get(i);
                    tempB = origVector.get(i);
                    
                    sigVector.set(i, sigVector.get(i+1));
                    origVector.set(i, origVector.get(i+1));
                    sigVector.set(i+1, tempA);
                    origVector.set(i+1, tempB);
                    flag = true;
                }
            }
        }
        
    }

    // check for duplicates (anagrams) in the signature vector and print to screen and file
    /*
     * Precondition: Parallel sorted original vector and signature vector.
     * Postcondition: Matching words in the signature vector are used to output the associated word vector to the .
     * screen and output.txt file. 
     */
    public void printAnagrams(Vector<String> fileContents, Vector<String> signature) {
        int i = 0;
        boolean flag = true;
        final String OUTPUT_FILE_PATH = "//Users//keithwellman//Documents//Fall 2016//Data Structure//testfile//";
        
        while (flag) {
            System.out.print(fileContents.get(i));
            try {
                PrintWriter output = new PrintWriter(new FileWriter(OUTPUT_FILE_PATH + "output.txt", true));
                output.print(fileContents.get(i));
                output.close();
            }
            catch (IOException e) {
                System.out.println("There was a problem with the output file.");
            }
            for (int j = 0; j < signature.size() - 1; j++) {
                if (signature.get(j).equals(signature.get(j+1))) {
                    System.out.print(" " + fileContents.get(j+1));
                    try {
                        PrintWriter output = new PrintWriter(new FileWriter(OUTPUT_FILE_PATH + "output.txt", true));
                        output.print(" " + fileContents.get(j+1));
                        output.close();
                    }
                    catch (IOException e) {
                        System.out.println("There was a problem with the output file.");
                    }
                    
                    flag = false;
                }
                else {
                    System.out.print("\n\n" + fileContents.get(j+1));
                    try {
                        PrintWriter output = new PrintWriter(new FileWriter(OUTPUT_FILE_PATH + "output.txt", true));
                        output.print("\n\n" + fileContents.get(j+1));
                        output.close();
                    }
                    catch (IOException e) {
                        System.out.println("There was a problem with the output file.");
                    }
                    
                }
            }
        }
        
    }
}
