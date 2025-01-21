import java.util.*;

public class Leaderboard {
    private Map<String, Integer> scores;
    private Map<String, Double> completionTimes;

    public Leaderboard() {
        scores = new HashMap<>();
        completionTimes = new HashMap<>();
        // Initialize preset data
        initializePresetData();
    }

    // Method to update or add a player's score and completion time
    public void updateScore(String playerName, int score, double completionTime) {
        scores.put(playerName, score);
        completionTimes.put(playerName, completionTime);
    }

    // Method to get the leaderboard sorted by scores
    public List<Map.Entry<String, Integer>> getLeaderboardByScore() {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(scores.entrySet());
        Collections.sort(sortedList, (a, b) -> b.getValue().compareTo(a.getValue())); // Sort in descending order of scores
        return sortedList;
    }

    // Method to get the leaderboard sorted by completion times
    public List<Map.Entry<String, Double>> getLeaderboardByCompletionTime() {
        List<Map.Entry<String, Double>> sortedList = new ArrayList<>(completionTimes.entrySet());
        Collections.sort(sortedList, (a, b) -> {
            // Convert integer scores to double for comparison
            double scoreA = scores.getOrDefault(a.getKey(), 0).doubleValue();
            double scoreB = scores.getOrDefault(b.getKey(), 0).doubleValue();
            
            // Compare scores in descending order
            int scoreComparison = Double.compare(scoreB, scoreA);
            if (scoreComparison != 0) {
                return scoreComparison;
            } else {
                // If scores are equal, compare completion times in ascending order
                return a.getValue().compareTo(b.getValue());
            }
        });
        return sortedList;
    }



    // Method to get score by player name
    public int getScore(String playerName) {
        return scores.getOrDefault(playerName, 0);
    }

    // Method to initialize preset data
    private void initializePresetData() {
        // Add preset data here
        updateScore("Dahlia", 100, 12.5);
        updateScore("Lily", 16, 15.7);
        updateScore("Tulip", 83, 14.3);
        // Add as many players, scores, and completion times as needed
    }
}
