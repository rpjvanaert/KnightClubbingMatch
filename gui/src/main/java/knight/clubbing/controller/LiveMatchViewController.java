package knight.clubbing.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.io.IOException;

public class LiveMatchViewController implements ModuleController {

    private IMainController mainController;

    public Label engineALabel;
    public Label engineBLabel;

    public ProgressBar progressBar;
    public Label progressLabel;
    public Label elapsedLabel;

    public TableView gamesTable;

    @Override
    public void setMainController(IMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        progressBar.setProgress(-1); // waiting state
        progressLabel.setText("Waiting for match to start...");
        elapsedLabel.setText("Elapsed: 00:00:00");
        engineALabel.setText("Engine A: Waiting");
        engineBLabel.setText("Engine B: Waiting");

        gamesTable.setPlaceholder(new Label("No games to display"));
    }

    @FXML
    private void onContinueClicked() {
        try {
            mainController.loadModule("/fxml/Results.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSpecateClicked() {
        try {
            mainController.loadModule("/fxml/LiveMatchSpectator.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
