package knight.clubbing.runner;

public class MatchRunningException extends RuntimeException {
    public MatchRunningException(String message) {
        super(message);
    }

    public MatchRunningException(String message, Throwable cause) {
        super(message, cause);
    }
}
