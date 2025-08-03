package knight.clubbing;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import knight.clubbing.api.MatchApi;
import knight.clubbing.api.ApiPaths;
import knight.clubbing.api.models.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path(ApiPaths.BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatchResource implements MatchApi {

    @POST
    @Path("status")
    @Override
    public RunResponse runMatch(RunRequest runRequest) {
        return null;
    }

    @GET
    @Path("status")
    @Override
    public String getStatus() {
        return "";
    }

    @GET
    @Path("history")
    @Override
    public List<Match> getMatchHistory(@HeaderParam("from") LocalDateTime from, @HeaderParam("to") LocalDateTime to) {
        return List.of();
    }

    @GET
    @Path("history/match/{id}")
    @Override
    public Match getMatchById(@PathParam("id") String id) {
        return null;
    }

    @GET
    @Path("history/game/{id}")
    @Override
    public Game getGameById(@PathParam("id") String id) {
        return null;
    }

    @POST
    @Path("engines/add")
    @Override
    public Engine addEngine(EngineRequest engineRequest) {
        return null;
    }

    @GET
    @Path("/engines")
    @Override
    public List<Engine> getEngines() {
        return List.of();
    }

    @GET
    @Path("/engines/{id}")
    @Override
    public Engine getEngineById(@PathParam("id") String id) {
        return null;
    }
}