package knight.clubbing.api.models;

public record Engine(
        String id, //Maven artifact: groupdId:artifactId
        String name, // Name of the engine
        String[] availableOptions, // UCI options available for this engine, could be redundant later
        String mvnRepository, // Maven repository URL where the engine can be found
        String[] versions // Available versions of the engine in the repository
) {
}
