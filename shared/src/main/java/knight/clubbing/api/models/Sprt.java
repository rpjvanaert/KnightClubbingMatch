package knight.clubbing.api.models;

public record Sprt(
        String state,
        double elo0,
        double elo1,
        double elo,
        double alpha,
        double beta,
        double lowerBound,
        double upperBound
) {
}
