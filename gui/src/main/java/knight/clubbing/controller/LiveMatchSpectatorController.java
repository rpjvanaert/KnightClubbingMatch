package knight.clubbing.controller;

import javafx.fxml.FXML;

import java.io.IOException;

public class LiveMatchSpectatorController implements ModuleController {

    private IMainController mainController;

    @Override
    public void setMainController(IMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        // Initialization logic if needed
    }

    @FXML
    private void onContinueClicked() {
        try {
            mainController.loadModule("/fxml/Results.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
