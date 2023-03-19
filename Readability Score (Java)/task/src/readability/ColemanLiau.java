package readability;

public class ColemanLiau {

    public static double computeCLScore(int characters, int words, int sentences) {
        double L = characters / (double) words * 100;
        double S = sentences / (double) words * 100;
        return (0.0588 * L) - (0.296 * S) - 15.8;
    }

    public static String getCLOutput(double score) {
        // Truncated score
        score = Math.floor(score * 100) / 100;
        int maxAge = Main.getAge(score);
        return String.format("Coleman-Liau index: %.2f (about %d-year-olds).", score, maxAge);
    }
}
