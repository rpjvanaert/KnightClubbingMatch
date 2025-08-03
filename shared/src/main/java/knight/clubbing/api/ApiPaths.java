package knight.clubbing.api;

public class ApiPaths {
    public static final String BASE_PATH = "/api/";
    public static final String RUN_MATCH = BASE_PATH + "/run";
    public static final String STATUS = BASE_PATH + "/status";
    public static final String HISTORY = BASE_PATH + "/history";
    public static final String MATCH_BY_ID = BASE_PATH + "/{id}";
    public static final String GAME_BY_ID = BASE_PATH + "/game/{id}";
    public static final String ENGINES = BASE_PATH + "/engines";
    public static final String ADD_ENGINE = BASE_PATH + ENGINES+ "/add";
    public static final String ENGINE_BY_ID = BASE_PATH + ENGINES + "/{id}";
}