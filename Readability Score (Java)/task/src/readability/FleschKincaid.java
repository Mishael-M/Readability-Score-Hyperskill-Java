package readability;

public class FleschKincaid {

    public static double computeFKScore(int words, int sentences, int syllables) {
        return (0.39 * (words / (double) sentences)) + (11.8 * (syllables / (double) words)) - 15.59;
    }

    public static String getFKOutput(double score) {
        // Truncated score
        score = Math.floor(score * 100) / 100;
        int maxAge = Main.getAge(score);
        return String.format("Flesch-Kincaid readability tests: %.2f (about %d-year-olds).", score, maxAge);
    }
}
