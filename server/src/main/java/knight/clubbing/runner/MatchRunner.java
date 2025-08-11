package knight.clubbing.runner;

import jakarta.inject.Singleton;
import knight.clubbing.api.models.Engine;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class MatchRunner implements MatchProcessListener {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private MatchListener matchListener;
    private MatchProcess process = null;

    public boolean isRunning() {
        return process != null;
    }

    public String getRunningMatchId() {
        return process != null ? process.getMatchId() : null;
    }

    public void addListener(MatchListener listener) {
        matchListener = listener;
    }

    public void removeListener() {
        matchListener = null;
    }

    public void runMatch(String matchId, String mvnEngine1, Engine mvnEngine2, String outputPath) {
        if (isRunning()) {
            throw new IllegalStateException("A match is already running: " + process.getMatchId());
        }

        process = new MatchProcess(matchId, mvnEngine1, mvnEngine2, outputPath);

        executorService.submit(() -> {
            try {
                notifyMatchStart(matchId);
                process.start(this);
            } catch (IOException e) {
                notifyMatchEnd(matchId, "Error running match: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                notifyMatchEnd(matchId, "Match was interrupted");

            } finally {
                process = null;
            }
        });
    }

    public void cancelMatch() {
        if (process != null) {
            process.cancel();
            notifyMatchEnd(process.getMatchId(), "Match was canceled");
            process = null;
        }
    }

    private void notifyMatchStart(String matchId) {
        if (matchListener != null) {
            matchListener.onMatchStart(matchId);
        }
    }

    private void notifyMatchEnd(String matchId, String result) {
        if (matchListener != null) {
            matchListener.onMatchComplete(matchId, result);
        }
    }

    @Override
    public void onProgress(String matchId, String progress) {
        if (matchListener != null) {
            matchListener.onMatchProgress(matchId, progress);
        }
    }

    @Override
    public void onComplete(String matchId, String result) {
        notifyMatchEnd(matchId, result);
    }
}