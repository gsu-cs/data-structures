/**
 * @author Kyle McKean
 * @since  11-09-16
 * <h1> Assignment 5: calc </h1>
 * Compilation: javac calc.java
 * Execution: java -jar calc.jar
 * Documentation: javadoc calc.java
 * -- This source code is fully javadoc compliant
 * Purpose of Program:
 * -- The purpose of the program <tt> calc </tt> is to parse and then mathematical
 *    expressions. It should handle operator precedence and associativity correctly.
 *    It should also handle parse errors gracefully.
 * Solution:
 * -- 1: Generate the token list.
 *    2: Create an empty stack for keeping operators. Create an empty list for output.
 *    3: Scan the token list from left to right.
 *    4: If the token is an operand, append it to the end of the output list.
 *       If the token is a left parenthesis, push it on the stack.
 *       If the token is a right parenthesis, pop the stack until the corresponding left parenthesis is removed.
 *         Append each operator to the end of the output list.
 *       If the token is an operator push it on the stack.
 *         However, first remove any operators already on the opstack that have higher or equal precedence
 *         and append them to the output list.
 *    5: When the input expression has been completely processed, check the stack.
 *         Any operators still on the stack can be removed and appended to the end of the output list.
 * Data Strutures: Stacks
 * Description of Use:
 * -- Follow Execution above
 * Expected I/O:
 * Explaination of Classes:
 * -- See above each class
 * -- The <tt> calc </tt> class is the main entry point for the program and contains the main
 *    exection loop.
 * <strong> Important </strong>
 * -- Assume every parameter has an implicit non null pre-condition
 *    Assume every return value has an implicit non null post-condition
 **/
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.util.InputMismatchException;
public class calc {
    /**
     * The <tt> ParseException </tt> class should be thrown whenever the parser cannot
     * parse the expression or convert the expression to postfix.
     **/
    public static class ParseException extends Exception {
        public ParseException(String message) { super(message); }
    }
    public static ArrayList<Token> lex(String str) throws ParseException {
        ArrayList<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            char c = str.charAt(i);
            switch (c) {
                case ' ':
                    break;
                case '(':
                    tokens.add(new Token(TokenKind.LeftParen));
                    break;
                case ')':
                    tokens.add(new Token(TokenKind.RightParen));
                    break;
                case '*':
                    tokens.add(new Token(TokenKind.Mul));
                    break;
                case '/':
                    tokens.add(new Token(TokenKind.Div));
                    break;
                case '%':
                    tokens.add(new Token(TokenKind.Mod));
                    break;
                case '-':
                    tokens.add(new Token(TokenKind.Sub));
                    break;
                case '+':
                    tokens.add(new Token(TokenKind.Add));
                    break;
                case 'x':
                    tokens.add(new Token(TokenKind.Var));
                    break;
                case '0': case '1': case '2':
                case '3': case '4': case '5':
                case '6': case '7': case '8':
                case '9':
                    Scanner num = new Scanner(str.substring(i,str.length())).useDelimiter("\\D");
                    long l = num.nextLong();
                    tokens.add(new Token(TokenKind.Number,l));
                    i += (int)(Math.log10(l) + 1);
                    continue;
                case '.':
                    throw new ParseException("Cannot accept floating point numbers.");
                default:
                    throw new ParseException("Invalid char: " + c);
            }
            i++;
        }
        return tokens;
    }
    public static ArrayList<Token> toPostfix(ArrayList<Token> tokens) throws ParseException {
        ArrayList<Token> output = new ArrayList<>();
        Stack<Token> operators  = new Stack<Token>();
        int balParens = 0;
        Token lastToken = null;
        for (Token current: tokens) {
            switch (current.getTokenKind()) {
                case Var: case Number:
                    if (lastToken != null && lastToken.wasOperand()) {
                        throw new ParseException("No operator between operands.");
                    }
                    output.add(current);
                    break;
                case LeftParen:
                    if (lastToken != null && !lastToken.wasOperator()) {
                        throw new ParseException("No operator between operand and left parentheses.");
                    }
                    operators.push(current);
                    balParens++;
                    break;
                case RightParen:
                    while (true) {
                        if (operators.empty()) {
                            throw new ParseException("No matching left parentheses for a right parentheses.");
                        }
                        Token t = operators.pop();
                        if (t.typeIs(TokenKind.LeftParen)) break;
                        output.add(t);
                    }
                    balParens--;
                    break;
                case Mul: case Div: case Mod:
                case Sub: case Add:
                    if (lastToken != null && lastToken.wasOperator()) {
                        String err = "The " + current + " operator cannot be preceded by a " + lastToken + " operator.";
                        throw new ParseException(err);
                    }
                    while (!operators.empty() &&
                           current.precedence() <= operators.peek().precedence()) {
                        output.add(operators.pop());
                    }
                    operators.push(current);
                    break;
            }
            lastToken = current;
        }
        if (0 < balParens) throw new ParseException("No matching right parentheses for a left parentheses.");
        while (!operators.empty()) {
            output.add(operators.pop());
        }
        return output;
    }
    /**
     * @pre The tokens list is a valid postfix expression
     **/
    public static Long evaluate(ArrayList<Token> tokens, Long environment) {
        Stack<Long> operands = new Stack<>();
        for (Token current: tokens) {
            TokenKind tk = current.getTokenKind();
            switch (tk) {
                case Var:
                    operands.push(environment);
                    break;
                case Number:
                    operands.push(current.getNumber());
                    break;
                default:
                    Long op1 = operands.pop();
                    Long op2 = operands.pop();
                    switch (tk) {
                        case Mul:
                            operands.push(op2 * op1);
                            break;
                        case Div:
                            operands.push(op2 / op1);
                            break;
                        case Mod:
                            operands.push(op2 % op1);
                            break;
                        case Sub:
                            operands.push(op2 - op1);
                            break;
                        case Add:
                            operands.push(op2 + op1);
                            break;
                    }
            }
        }
        return operands.pop();
    }
    public static void main(String[] args) {
        boolean notFoundValid = true;
        ArrayList<Token> tokens = null;
        Scanner s = new Scanner(System.in);
        while (notFoundValid) {
            System.out.print("Enter infix expression: ");
            String expr = s.nextLine();
            try {
                tokens = toPostfix(lex(expr));
                String out = "";
                for (Token t: tokens) {
                    out += t + " ";
                }
                System.out.println("Converted expression: " + out);
                notFoundValid = false;
            } catch (ParseException pe) {
                System.out.println("Error in expression!! " + pe.getMessage());
            }
        }
        boolean running = true;
        while (running) {
            try {
                System.out.print("Enter value of x: ");
                Long environment = s.nextLong();
                System.out.println("Answer to expression: " + evaluate(tokens,environment));
            } catch (InputMismatchException e) {
                String quit = s.nextLine();
                if (0 < quit.length() && quit.charAt(0) == 'q') {
                    running = false;
                } else {
                    System.out.println("Please either enter an integer or 'q' to quit.");
                }
            }
        }

    }
}
