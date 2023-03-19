package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static String VOWEL_REGEX = "[a|e|i|o|u|y]";

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) {
        String inputFile = args[0];

        String input = "";
        try {
            input = readFileAsString(inputFile);
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }

        System.out.println("The text is: ");
        System.out.println(input);
        String[] wordList = input.split("\\s+");
        int words = wordList.length;
        int characters = input.replaceAll("\\s+", "").length();
        int sentences = input.split("[!.?]+").length;
        int syllables = countSyllables(wordList);
        int polysyllables = countPolysyllables(wordList);

        System.out.println("\nWords: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = new Scanner(System.in).next();
        printOutput(choice, characters, words, sentences, syllables, polysyllables);


    }

    public static int countSyllables(String[] wordList) {
        int numVowels = 0;

        for (String word : wordList) {
            // Count vowels for current word
            int numVowelsWord = 0;
            numVowelsWord = getNumVowelsWord(VOWEL_REGEX, word, numVowelsWord);

            // If there are no vowels, add one because it is considered one syllable
            if (numVowelsWord == 0) {
                numVowelsWord = 1;
            }
            numVowels += numVowelsWord;
        }
        return numVowels;
    }

    public static int countPolysyllables(String[] wordList) {
        int polysyllables = 0;


        for (String word : wordList) {
            // Count vowels for current word
            int numVowelsWord = 0;
            numVowelsWord = getNumVowelsWord(VOWEL_REGEX, word, numVowelsWord);
            if (numVowelsWord > 2) {
                polysyllables++;
            }


        }
        return polysyllables;

    }

    private static int getNumVowelsWord(String vowelRegex, String word, int numVowelsWord) {
        // Some words start with a capital letter that is a vowel
        word = word.toLowerCase();
        // Remove all non-alphabetical characters
        word = word.replaceAll("[^a-z]+", "");
        boolean prevIndexVowel = false;
        for (int i = 0; i < word.length(); i++) {
            String currentChar = String.valueOf(word.charAt(i));

            // Check if last character ends with e as it doesn't count as a vowel
            if (i == word.length() - 1 && currentChar.equals("e")) {
                break;
            }

            // Count if current character is a vowel
            if (currentChar.matches(vowelRegex)) {
                // Makes sure it doesn't double count vowels
                if (prevIndexVowel) {
                    continue;
                }
                prevIndexVowel = true;
                numVowelsWord++;
            } else {
                prevIndexVowel = false;
            }
        }
        return numVowelsWord;
    }

    public static int getAge(double score) {
        int age = 0;
        int wholeScore = (int) Math.ceil(score);
        int maxAge = wholeScore >= 14 ? wholeScore + 8 : wholeScore + 5;
        return maxAge;
    }
    public static void printOutput(String choice, int characters, int words, int sentences, int syllables, int polysyllables) {
        double aRIScore = AutomatedReadabilityIndex.computeARIScore(characters, words, sentences);
        double fKScore = FleschKincaid.computeFKScore(words, sentences, syllables);
        double sMOGScore = SimpleMeasureOfG.computeSMOGScore(polysyllables, sentences);
        double cLScore = ColemanLiau.computeCLScore(characters, words, sentences);
        double totalAge = 0.0;
        double methodsChosen = 1.0;

        System.out.println();
        switch (choice) {
            case "ARI":
                System.out.println(AutomatedReadabilityIndex.getARIOutput(aRIScore));
                totalAge += getAge(aRIScore);
                break;
            case "FK":
                System.out.println(FleschKincaid.getFKOutput(fKScore));
                totalAge += getAge(fKScore);
                break;
            case "SMOG":
                System.out.println(SimpleMeasureOfG.getSMOGOutput(sMOGScore));
                totalAge += getAge(sMOGScore);
                break;
            case "CL":
                System.out.println(ColemanLiau.getCLOutput(cLScore));
                totalAge += getAge(cLScore);
                break;
            case "all":
                System.out.println(AutomatedReadabilityIndex.getARIOutput(aRIScore));
                System.out.println(FleschKincaid.getFKOutput(fKScore));
                System.out.println(SimpleMeasureOfG.getSMOGOutput(sMOGScore));
                System.out.println(ColemanLiau.getCLOutput(cLScore));
                totalAge += getAge(aRIScore);
                totalAge += getAge(fKScore);
                totalAge += getAge(sMOGScore);
                totalAge += getAge(cLScore);

                methodsChosen = 4.0;
                break;
            default:
                return;
        }
        double averageAge = totalAge / methodsChosen;
        System.out.println(String.format("\nThis text should be understood in average by %.2f-year-olds", averageAge));
    }
}