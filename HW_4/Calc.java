// Calc.java

package hw4;

import java.util.Stack;
import java.util.Scanner;


/** A program for an RPN calculator that uses a stack. */
public final class Calc {

    // Hush checkstyle
    private Calc() {}

    private static void operate(Stack<Integer> s, String operator) {
        int x;
        int y;
        switch (operator) {
            case "*":
                s.push(s.pop() * s.pop());
                break;
            case "/":
                x = s.pop();
                y = s.pop();
                if (x != 0) {
                    s.push(y / x);
                } else {
                    System.err.println("Error: Division by zero");
                }
                s.push(y);
                s.push(x);
                break;
            case "%":
                x = s.pop();
                y = s.pop();
                if (x != 0) {
                    s.push(y % x);
                } else {
                    System.err.println("Error: Division by zero");
                }
                s.push(y);
                s.push(x);
                break;
            case "+":
                s.push(s.pop() + s.pop());
                break;
            case "-":
                x = s.pop();
                y = s.pop();
                s.push(y - x);
                break;
            default:
        }
    }

    /**
     * The main function.
     * @param args Not used.
     */
    public static void main(String[] args) {
        Stack<Integer> rpn = new Stack<>();
        Scanner in = new Scanner(System.in);
        boolean enoughElements = true;

        while (in.hasNext()) {
            String input = in.next();
            if (input.matches("-?\\d+")) {
                rpn.push(Integer.valueOf(input));
            }
            else if (input.matches("[-+*/%]")) {
                if (rpn.size() >= 2) {
                    operate(rpn, input);
                }
                else {
                    System.err.println("ERROR: Not enough arguments.");
                }
            }
            else if ("?".equals(input)) {
                System.out.println(rpn.toString());
            }
            else if (".".equals(input)) {
                if (!rpn.empty()) {
                    System.out.println(rpn.peek());
                } else {
                    System.err.println("ERROR: Nothing to show");
                }
            }
            else if ("!".equals(input)) {
                break;
            }
            else {
                System.err.println("ERROR: bad token");
            }
        }
    }
}
