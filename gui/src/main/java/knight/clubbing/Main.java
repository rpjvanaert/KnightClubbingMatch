package knight.clubbing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Process serverProcess;

    @Override
    public void start(Stage stage) throws Exception {
        //this.serverProcess = createServerProcess();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Knight Clubbing Match");
        stage.setOnCloseRequest(_ -> destroyServerProcess());
        stage.show();
    }

    private void destroyServerProcess() {
        if (this.serverProcess != null && serverProcess.isAlive())
            serverProcess.destroy();
    }

    private Process createServerProcess() throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "java", "-jar", "../server/target/server-1.0-SNAPSHOT.jar"
        );
        pb.inheritIO();
        return pb.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}