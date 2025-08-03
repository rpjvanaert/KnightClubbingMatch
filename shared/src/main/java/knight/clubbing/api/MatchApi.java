package knight.clubbing.api;

import knight.clubbing.api.response.RunResponse;
import knight.clubbing.api.models.Match;
import knight.clubbing.api.models.Game;
import knight.clubbing.api.models.Engine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MatchApi {

    RunResponse runMatch(String engine1, String engine2, String timeControl, int amount, Map<String, String> uciOptions, boolean doSprt);

    String getStatus();

    List<Match> getMatchHistory(LocalDateTime from, LocalDateTime to);

    Match getMatchById(String id);

    Game getGameById(String id);

    Engine addEngine(String name, String id, List<String> availableOptions, String mvnRepository);

    List<Engine> getEngines();

    Engine getEngineById(String id);
}