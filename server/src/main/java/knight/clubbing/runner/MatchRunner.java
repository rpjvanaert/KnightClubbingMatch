package knight.clubbing.runner;

import jakarta.inject.Singleton;
import knight.clubbing.api.models.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MatchRunner {

    private final List<MatchListener> matchListeners = new ArrayList<>();

    private String runningMatchId = null;

    public boolean isRunning() {
        return runningMatchId != null;
    }

    public String getRunningMatchId() {
        return runningMatchId;
    }

    public void addListener(MatchListener listener) {
        matchListeners.add(listener);
    }

    public void removeListener(MatchListener listener) {
        matchListeners.remove(listener);
    }

    public void runMatch(String matchId, String mvnEngine1, Engine mvnEngine2, String outputPath) {
        try {
            notifyMatchStart(matchId);

            ProcessBuilder pb = new ProcessBuilder(
                    "cutechess-cli",
                    "-engine", "cmd=" + mvnEngine1,
                    "-engine", "cmd=" + mvnEngine2,
                    "-games", "100",
                    "-pgnout", outputPath
            );

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                notifyMatchProgress(matchId, line);
            }

            process.waitFor();

            notifyMatchEnd(matchId, "Match completed successfully");

        } catch (IOException e) {
            notifyMatchEnd(matchId, "Error running match: " + e.getMessage());
            throw new MatchRunningException("Failed running match", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            notifyMatchEnd(matchId, "Match interrupted");
            throw new MatchRunningException("Match was interrupted", e);
        }
    }

    private void notifyMatchStart(String matchId) {
        this.runningMatchId = matchId;
        for (MatchListener listener : matchListeners) {
            listener.onMatchStart(matchId);
        }
    }

    private void notifyMatchProgress(String matchId, String progress) {
        for (MatchListener listener : matchListeners) {
            listener.onMatchProgress(matchId, progress);
        }
    }

    private void notifyMatchEnd(String matchId, String result) {
        this.runningMatchId = null;
        for (MatchListener listener : matchListeners) {
            listener.onMatchComplete(matchId, result);
        }
    }
}
