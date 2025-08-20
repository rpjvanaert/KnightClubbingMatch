package knight.clubbing.service;

import knight.clubbing.api.MatchApi;
import knight.clubbing.api.models.Engine;
import knight.clubbing.api.models.EngineRequest;

import java.util.List;

public class EngineService {
    private final MatchApi matchApi;

    public EngineService(String baseUrl) {
        this.matchApi = new MatchApiClient(baseUrl);
    }

    public Engine addEngine(EngineRequest engineRequest) {
        try {
            return matchApi.addEngine(engineRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Engine> getEngines() {
        return matchApi.getEngines();
    }

    public Engine getEngineById(String id) {
        try {
            return matchApi.getEngineById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
