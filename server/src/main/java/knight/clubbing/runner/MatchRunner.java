package knight.clubbing.runner;

import jakarta.inject.Singleton;
import knight.clubbing.api.models.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class MatchRunner {

    private final List<MatchListener> matchListeners = new ArrayList<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private volatile String runningMatchId = null;

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
        if (isRunning()) {
            throw new IllegalStateException("A match is already running: " + runningMatchId);
        }

        executorService.submit(() -> {
            try {
                notifyMatchStart(matchId);
                runningMatchId = matchId;

                ProcessBuilder pb = new ProcessBuilder(
                        "cutechess-cli",
                        "-engine", "cmd=" + mvnEngine1,
                        "-engine", "cmd=" + mvnEngine2,
                        "-games", "100",
                        "-pgnout", outputPath
                );
                Process process = pb.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        notifyMatchProgress(matchId, line);
                    }
                }

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    notifyMatchEnd(matchId, "Match completed successfully");
                } else {
                    notifyMatchEnd(matchId, "Match failed with exit code: " + exitCode);
                }
            } catch (IOException e) {
                notifyMatchEnd(matchId, "Error running match: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                notifyMatchEnd(matchId, "Match interrupted");
            } finally {
                runningMatchId = null;
            }
        });
    }

    private void notifyMatchStart(String matchId) {
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
        for (MatchListener listener : matchListeners) {
            listener.onMatchComplete(matchId, result);
        }
    }
}