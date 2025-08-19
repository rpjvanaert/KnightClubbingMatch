package knight.clubbing.api;

import knight.clubbing.api.models.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchApi {

    RunResponse runMatch(RunRequest runRequest);

    String getStatus();

    List<Match> getMatchHistory(LocalDateTime from, LocalDateTime to);

    Match getMatchById(String id);

    Game getGameById(String id);

    Engine addEngine(EngineRequest engineRequest);

    List<Engine> getEngines();

    Engine getEngineById(String id);
}