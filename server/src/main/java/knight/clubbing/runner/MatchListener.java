package knight.clubbing.runner;

public interface MatchListener {

    void onMatchStart(String matchId);
    void onMatchProgress(String matchId, String progress);
    void onMatchComplete(String matchId, String result);
}
