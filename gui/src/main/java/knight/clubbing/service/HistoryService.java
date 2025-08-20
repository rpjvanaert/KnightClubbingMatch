package knight.clubbing.service;

import knight.clubbing.api.MatchApi;
import knight.clubbing.api.models.Game;
import knight.clubbing.api.models.Match;

import java.time.LocalDateTime;
import java.util.List;

public class HistoryService {
    private final MatchApi matchApi;

    public HistoryService(String baseUrl) {
        this.matchApi = new MatchApiClient(baseUrl);
    }

    public List<Match> getMatchHistory(LocalDateTime start, LocalDateTime end) {
        return matchApi.getMatchHistory(start, end);
    }

    public Match getMatchById(String id) {
        return matchApi.getMatchById(id);
    }

    public Game getGameById(String id) {
        return matchApi.getGameById(id);
    }
}
