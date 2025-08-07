package knight.clubbing.runner;

import knight.clubbing.api.models.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MatchProcess {

    private final String matchId;
    private final String mvnEngine1;
    private final Engine mvnEngine2;
    private final String outputPath;
    private Process process;

    public MatchProcess(String matchId, String mvnEngine1, Engine mvnEngine2, String outputPath) {
        this.matchId = matchId;
        this.mvnEngine1 = mvnEngine1;
        this.mvnEngine2 = mvnEngine2;
        this.outputPath = outputPath;
    }

    public void start(MatchProcessListener listener) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "cutechess-cli",
                "-engine", "cmd=" + mvnEngine1,
                "-engine", "cmd=" + mvnEngine2,
                "-games", "100",
                "-pgnout", outputPath
        );
        process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listener.onProgress(matchId, line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            listener.onComplete(matchId, "Match completed successfully");
        } else {
            listener.onComplete(matchId, "Match failed with exit code: " + exitCode);
        }
    }

    public void cancel() {
        if (process != null) {
            process.destroy();
        }
    }

    public String getOutputPath() {
        return outputPath;
    }

    public Engine getMvnEngine2() {
        return mvnEngine2;
    }

    public String getMvnEngine1() {
        return mvnEngine1;
    }

    public String getMatchId() {
        return matchId;
    }
}