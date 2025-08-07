package knight.clubbing.runner;

public interface MatchProcessListener {
    void onProgress(String matchId, String progress);
    void onComplete(String matchId, String result);
}