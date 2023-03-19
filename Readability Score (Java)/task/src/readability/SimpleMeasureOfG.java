package readability;

public class SimpleMeasureOfG {

    public static double computeSMOGScore(int polysyllables, int sentences) {
        return (1.043 * (Math.sqrt(polysyllables * (30 / (double) sentences))) + 3.1291);
    }

    public static String getSMOGOutput(double score) {
        // Truncated score
        score = Math.floor(score * 100) / 100;
        int maxAge = Main.getAge(score);
        return String.format("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).", score, maxAge);
    }
}
