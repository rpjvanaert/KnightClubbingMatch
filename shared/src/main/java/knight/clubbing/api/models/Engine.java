package knight.clubbing.api.models;

public record Engine(
        String id,
        String name,
        String[] availableOptions,
        String mvnRepository,
        String[] versions
) {
}
