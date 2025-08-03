package knight.clubbing.api.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record Match(
        String id,
        String engine1,
        String engine2,
        String timeControl,
        String result,
        String status,
        LocalDateTime completedAt,
        Map<String, String> uciOptions,
        List<String> games,
        Sprt sprt
) {
}
