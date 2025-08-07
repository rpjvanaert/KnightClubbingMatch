package knight.clubbing.engine;

import jakarta.inject.Singleton;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.supplier.RepositorySystemSupplier;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.DefaultRepositorySystemSession;

import java.nio.file.Paths;
import java.util.List;

@Singleton
public class EnginePrepper {

    public static final String REPO_PATH = "target/local-repo";

    private final RepositorySystem system;
    private final RepositorySystemSession session;

    public EnginePrepper() {
        this.system = newRepositorySystem();
        this.session = newRepositorySystemSession(system);
    }

    public List<String> getAllVersions(String repositoryUrl, String engineId) throws VersionRangeResolutionException {
        RemoteRepository repo = new RemoteRepository.Builder(
                "custom", "default", repositoryUrl
        ).build();

        DefaultArtifact artifact = new DefaultArtifact(engineId + ":[0,)");

        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact(artifact);
        rangeRequest.addRepository(repo);

        VersionRangeResult rangeResult = system.resolveVersionRange(session, rangeRequest);

        return rangeResult.getVersions()
                .stream()
                .map(Object::toString)
                .toList();
    }

    public String downloadArtifactIfNeeded(String repositoryUrl, String engineId, String version) throws ArtifactResolutionException {
        RemoteRepository repo = new RemoteRepository.Builder(
                "custom", "default", repositoryUrl
        ).build();

        DefaultArtifact artifact = new DefaultArtifact(engineId + ":" + version);

        String artifactPath = Paths.get(
                REPO_PATH,
                engineId.replace('.', java.io.File.separatorChar),
                version,
                engineId.substring(engineId.lastIndexOf('.') + 1) + "-" + version + ".jar"
        ).toString();
        java.io.File file = new java.io.File(artifactPath);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        // Download artifact
        org.eclipse.aether.resolution.ArtifactRequest request =
                new org.eclipse.aether.resolution.ArtifactRequest();
        request.setArtifact(artifact);
        request.addRepository(repo);

        org.eclipse.aether.resolution.ArtifactResult result =
                system.resolveArtifact(session, request);

        return result.getArtifact().getFile().getAbsolutePath();
    }

    private static RepositorySystem newRepositorySystem() {
        return new RepositorySystemSupplier().get();
    }

    private static RepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
        DefaultRepositorySystemSession session = new DefaultRepositorySystemSession();
        LocalRepository localRepo = new LocalRepository(REPO_PATH);
        session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));
        return session;
    }
}