package knight.clubbing.api.models;

import java.util.Map;

public record RunRequest(
        String engine1,
        String engine2,
        String timeControl,
        int amount,
        Map<String, String> uciOptions,
        boolean doSprt
) {
}
