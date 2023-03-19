package readability;

public class AutomatedReadabilityIndex {
    public static double computeARIScore(int characters, int words, int sentences) {
        return (4.71 * (characters / (double) words)) + (0.5 * (words / (double) sentences)) - 21.43;
    }

    public static String getARIOutput(double score) {
        // Truncated score
        score = Math.floor(score * 100) / 100;
        int maxAge = Main.getAge(score);
        return String.format("Automated Readability Index: %.2f (about %d-year-olds).", score, maxAge);
    }
}
