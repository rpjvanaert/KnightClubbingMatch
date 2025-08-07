package knight.clubbing.engine;

import jakarta.inject.Singleton;
import knight.clubbing.api.models.Engine;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class EngineStore {

    private final List<Engine> engines = new ArrayList<>();

    public void addEngine(Engine engine) {
        engines.add(engine);
    }

    public List<Engine> getEngines() {
        return new ArrayList<>(engines);
    }

    public Engine getEngineById(String id) {
        return engines.stream()
                .filter(engine -> engine.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void removeEngine(String id) {
        engines.removeIf(engine -> engine.id().equals(id));
    }
}
