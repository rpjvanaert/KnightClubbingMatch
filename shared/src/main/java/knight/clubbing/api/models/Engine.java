package knight.clubbing.api.models;

import java.util.List;

public record Engine(
        String id,
        String name,
        List<String> availableOptions
) {
}
