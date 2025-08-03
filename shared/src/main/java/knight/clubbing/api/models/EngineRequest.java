package knight.clubbing.api.models;

import java.util.List;

public record EngineRequest (
        String name,
        String id,
        List<String> availableOptions,
        String mvnRepository
){
}
