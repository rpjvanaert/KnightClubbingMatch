package knight.clubbing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import knight.clubbing.api.MatchApi;
import knight.clubbing.api.models.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static knight.clubbing.api.ApiPaths.*;

public class MatchApiClient implements MatchApi {

    private final String apiBaseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public MatchApiClient(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public RunResponse runMatch(RunRequest runRequest) {
        return null;
    }

    @Override
    public String getStatus() {
        return "";
    }

    @Override
    public List<Match> getMatchHistory(LocalDateTime from, LocalDateTime to) {
        return List.of();
    }

    @Override
    public Match getMatchById(String id) {
        return null;
    }

    @Override
    public Game getGameById(String id) {
        return null;
    }

    @Override
    public Engine addEngine(EngineRequest engineRequest) {
        try {
            String body = objectMapper.writeValueAsString(engineRequest);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseUrl + ADD_ENGINE))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), Engine.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add engine", e);
        }
    }

    @Override
    public List<Engine> getEngines() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseUrl + ENGINES))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Engine[] engines = objectMapper.readValue(response.body(), Engine[].class);
            return Arrays.asList(engines);
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Engine getEngineById(String id) {
        try {
            String path = apiBaseUrl + ENGINE_BY_ID.replace("{id}", id);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(path))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), Engine.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get engine by id", e);
        }
    }
}
