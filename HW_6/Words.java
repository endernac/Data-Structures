package hw6;

import java.util.Scanner;

/**
 * Count unique words.
 *
 * Read text from standard input, count how often each unique word appears,
 * write the results to standard output. Note that the definiton of "word"
 * is rather arbitrary and won't make the linguists among you very happy.
 */
public final class Words {
    private static Map<String, Integer> data;

    // Make checkstyle happy.
    private Words() {}

    /**
     * Main method.
     * @param args Command line arguments (ignored).
     */
    public static void main(String[] args) {
        data = new TreapMap<>();
        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            for (String str: args) {
                if (str.matches("[a-zA-Z0-9]+")) {
                    if (str.length() > 1) {
                        addWord(str);
                    }
                }
            }
        } else {
            while (scanner.hasNext()) {
                String word = scanner.next();
                // The regular expression splits strings on whitespace and
                // non-word characters (anything except [a-zA-Z_0-9]). Far
                // from perfect, but close enough for this simple program.
                if (word.matches("[a-zA-Z0-9]+")) {
                    if (word.length() > 1) {
                        // Skip "short" words, most of which just "dirty up"
                        // the statistics.
                        addWord(word);
                    }
                }
            }
        }

        for (String word: data) {
            System.out.println(word + ": " + data.get(word));
        }
    }

    private static void addWord(String word) {
        if (data.has(word)) {
            data.put(word, data.get(word) + 1);
        } else {
            data.insert(word, 1);
        }
    }
}
