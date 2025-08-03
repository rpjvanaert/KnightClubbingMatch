package knight.clubbing.api.models;

import java.time.LocalDateTime;

public record Game(
    String id,
    String matchId,
    String result,
    String pgn,
    LocalDateTime completedAt
) {
}
