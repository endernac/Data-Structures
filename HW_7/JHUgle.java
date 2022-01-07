package hw7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Create a search engine to query for urls associated with a keyword.
 */
public final class JHUgle {


    private JHUgle() {}

    /**
     * Main method.
     * @param args Command line arguments
     * @throws FileNotFoundException for illegal arguments.
     */
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        HashMap<String, List<String>> engine;

        engine = buildEngine(file);

        Scanner query = new Scanner(System.in);
        Stack<List<String>> queryResults = new Stack<>();

        System.out.print("> ");

        while (query.hasNext()) {
            String next = query.next();

            if ("!".equals(next)) {
                return;
            } else if ("?".equals(next) && !queryResults.isEmpty()) {
                printList(queryResults.peek());
            } else if ("&&".equals(next)) {
                addAnd(queryResults);
            } else if ("||".equals(next)) {
                addOr(queryResults);
            } else if (engine.has(next)) {
                queryResults.push(engine.get(next));
            } else {
                queryResults.push(new ArrayList<>());
            }

            System.out.print("> ");
        }
    }

    private static HashMap<String, List<String>> buildEngine(File f)
            throws FileNotFoundException {

        HashMap<String, List<String>> engine = new HashMap<>();
        Scanner input = new Scanner(f);

        while (input.hasNextLine()) {
            String url = input.nextLine();
            if (input.hasNextLine()) {
                String tokens = input.nextLine();
                String[] tok = tokens.split("\\s");
                for (String key : tok) {
                    if (engine.has(key)) {
                        List<String> valueList = engine.get(key);
                        valueList.add(url);
                    } else {
                        engine.insert(key, new ArrayList<>());
                        List<String> newValue = engine.get(key);
                        newValue.add(url);
                    }
                }
            }
        }

        input.close();
        System.out.println("Index Created");
        return engine;
    }

    private static void printList(List<String> list) {
        for (String url : list) {
            System.out.println(url);
        }
    }

    private static void addAnd(Stack<List<String>> stack)
            throws IllegalArgumentException {


        List l1;
        List l2;
        List<String> newResults;

        if (stack.size() < 2) {
            System.err.println("Not enough arguments");
            return;
        } else {
            l2 = stack.pop();
            l1 = stack.pop();
        }

        if (l1 != null && l2 != null) {
            newResults = new ArrayList<>();

            for (Object url : l1) {
                if (l2.contains(url)) {
                    newResults.add((String) url);
                }
            }
        } else {
            newResults = null;
        }

        stack.push(newResults);
    }

    private static void addOr(Stack<List<String>> stack)
            throws IllegalArgumentException {

        List l1;
        List l2;
        List<String> newResults;

        if (stack.size() < 2) {
            System.err.println("Not enough arguments");
            return;
        } else {
            l2 = stack.pop();
            l1 = stack.pop();
        }

        if (l1 != null && l2 != null) {
            newResults = new ArrayList<>();

            for (Object url : l1) {
                if (!l2.contains(url)) {
                    newResults.add((String) url);
                }
            }

            for (Object url : l2) {
                newResults.add((String) url);
            }
        } else {
            newResults = null;
        }

        stack.push(newResults);
    }

}