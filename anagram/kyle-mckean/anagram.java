import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import java.nio.file.NoSuchFileException;
import java.io.IOException;
import java.io.Serializable;
import java.util.stream.Collectors;
/**
 * @author Kyle McKean
 * @since  09-20-2016
 * <h1> Assignment 2: Anagrams </h1>
 * Compilation: java anagram.java
 * -- Requires java 8
 * Execution: java -jar anagram.jar
 * Documentation: javadoc anagram.java
 * -- This source code is fully javadoc compliant
 * Purpose of Program:
 * -- The purpose of the program is to read text in from a file and
      group the words by if they are anagrams of each other. Once
      the words are grouped the words should be pretty printed to they
      console and to a file called output.txt.
 * Solution:
 * -- The solution has two stages a lexer and a grouper
 * -- Lexer:
 * ---- Turns the file stream into an array of words
 *      A word is a series of non-whitespace characters
 * -- Grouper:
 * ---- This is the key to the whole program the algorithm conceptually
 *      works like this:
 *      [1,1,1,2,2,3,4,4,4] :- [[1,1,1],[2,2],[3],[4,4,4]]
 *      In words it groups pairwise equal elements into sublists
 *      In fact this is the meat of the program we are tasked to write.
 * Data Strutures: Arrays
 * Description of Use:
 * -- 1: Follow Execution above
 * -- 2: Follow Prompts
 * Expected I/O:
 * -- Please enter the file name to read data from: [user inputs: (input.txt)]
 *    opt opt
 *    Pots
 *    sit Sit it's
 *    Pans snap
 * -- [input.txt]
 *    Pans
 *    Pots opt sit
 *    opt
 *    Sit
 *    it's
 *    snap
 * Explaination of Classes:
 * -- See above each class
 * <strong> Important </strong>
 * -- Assume every parameter has an implicit non null pre-condition
 *    Assume every return value has an implicit non null post-condition
 */
public class anagram {
    /**
     * Anagram:
     *   Constructor:
     *     1: There is only one constructor which takes a string
     *     2: Sets the varaible label to that string
     *     3: Then in creates a signature from that string
     *          1: A signature is a string thats sorted with no
     *             punctation and is all lowercase
     *   Methods:
     *     getLabel:     accesor for label varaible
     *     getSignature: accesor for signature varaible
     *   Interfaces:
     *     Anagram implements comparable based on the signature
     *     This is used to group a collection of Anagram objects
     */
    private static class Anagram implements Comparable<Anagram>, Serializable {
        private String label;
        private static String signature;
        public Anagram(String label) {
            this.label = label;
            char[] charArray = label
                .toLowerCase()
                .replaceAll("\\W","") // This program really could use a lexer
                .toCharArray();
            Arrays.sort(charArray);
            this.signature = new String(charArray);
        }
        public int compareTo(Anagram other) {
            return this.signature.compareTo(other.signature);
        }
        public static String getSignature(Anagram other) { return other.signature; }
        public static String getLabel(Anagram other) { return other.label; }
    }
    public static void main(String[] args) throws IOException {
        System.out.print("Please enter the file name to read data from: ");
        final String fileName = new Scanner(System.in).next();
        try {
            String[] words = Files.lines(Paths.get(fileName))
                .filter(line -> !line.isEmpty())
                .map(line -> line.split("[\\s]+"))
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
            if (words.length == 0) {
                System.out.println("The input file is empty.");
                return;
            }
            if (Arrays.stream(words).filter(word -> word.length() <= 15).count() > 50) {
                System.out.println("There are more than 50 words in the input file.");
                return;
            }
            Stream<String> solved =
                 Arrays.stream(words)
                .filter(word -> word.length() <= 15)
                .map(Anagram::new)
                .collect(Collectors.groupingBy(
                           Anagram::getSignature,
                           Collectors.mapping(Anagram::getLabel, Collectors.toList())))
                .values()
                .stream()
                .map(list -> {
                           String line = list.stream().collect(Collectors.joining(" "));
                           System.out.println(line);
                           return line; });
            Files.write(Paths.get("output.txt"), (Iterable<String>)solved::iterator);
        } catch (NoSuchFileException e) {
            System.out.println("File: " + fileName + " not found.");
        }
    }
}
