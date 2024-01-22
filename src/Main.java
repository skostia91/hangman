import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final String DICTIONARY = "dictionary.txt";
    private static final Path dir = Paths.get("src/resources/", DICTIONARY);


    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        while (startOrEnd()) {
            Scanner scanner = new Scanner(System.in);
            List<String> words = readFile(dir);
            String word = getWord(words);
            List<String> letters = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append("*".repeat(word.length()));
            int count = 5;
            do {
                System.out.println("Введите букву");
                String letter = scanner.nextLine();
                if (letter.equals(letter.toLowerCase()) && letter.length() == 1
                        && Pattern.matches(".*\\p{InCyrillic}.*", letter) && !letters.contains(letter)) {
                    letters.add(letter);
                    boolean isValid = false;
                    for (int i = 0; i < word.length(); i++) {
                        if (word.charAt(i) == letter.charAt(0)) {
                            sb.replace(i, i + 1, letter);
                            isValid = true;
                        }
                    }
                    if (isValid) {
                        System.out.println(sb);
                    } else {
                        System.out.println("\nУпс, такой буквы нет!\nУ Вас осталось " + --count + " ошибки.\n");
                        printViselica(count);
                        if (count == 0) {
                            System.out.println("Вы проиграли. Не отгадали слово - " + word.toUpperCase());
                            break;
                        }
                        System.out.println(sb);
                    }
                    System.out.println("Вы использовали буквы: " + letters + "\n");
                    if (checkGameState(sb.toString())) {
                        System.out.println("Вы выиграли. Отгадали слово - " + word.toUpperCase());
                        break;
                    }
                }
            } while (true);
        }
    }

    private static boolean startOrEnd() {
        boolean isStart = false;
        while (true) {
            System.out.println("[Н]овая игра или [В]ыход?");
            Scanner scanner = new Scanner(System.in);
            String choose = scanner.nextLine().toLowerCase();
            if (choose.equals("н")) {
                isStart = true;
                break;
            } else if (choose.equals("в")) {
                break;
            }
        }
        return isStart;
    }

    private static List<String> readFile(Path path) {
        List<String> words = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()),32)) {
            while (bufferedReader.ready()) {
                words.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return words;
    }

    private static String getWord(List<String> words) {
        String word = words.get(new Random().nextInt(words.size()));
        return word.trim().toLowerCase();
    }


    private static boolean checkGameState(String word) {
        boolean isGuess = true;
        char[] array = word.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '*') {
                isGuess = false;
            }
        }
        return isGuess;
    }

    private static void printViselica(int count) {
        if (count == 4) {
            System.out.println("|\n|\n|\n|\n|\n|\n|\n");
        } else if (count == 3) {
            System.out.println(" ---");
            System.out.println("|/  |");
            System.out.println("|\n|\n|\n|\n|\n|\n");

        } else if (count == 2) {
            System.out.println(" ---");
            System.out.println("|/  |");
            System.out.println("|   *");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|\n");
        } else if (count == 1) {
            System.out.println(" ---");
            System.out.println("|/  |");
            System.out.println("|   *");
            System.out.println("|  /||");
            System.out.println("|   |");
            System.out.println("|");
            System.out.println("|\n");
        } else if (count == 0) {
            System.out.println(" ---");
            System.out.println("|/  |");
            System.out.println("|   *");
            System.out.println("|  /|\\");
            System.out.println("|   |");
            System.out.println("|  / \\");
            System.out.println("|\n");
        }
    }
}
