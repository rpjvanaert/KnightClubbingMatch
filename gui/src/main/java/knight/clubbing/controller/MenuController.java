package knight.clubbing.controller;

import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController implements ModuleController {

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
    private void onMatchSetupClicked() {
        try {
            mainController.loadModule("/fxml/MatchSetup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHistoryButtonClicked() {
        try {
            mainController.loadModule("/fxml/History.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEngineManagementButtonClicked() {
        try {
            mainController.loadModule("/fxml/EngineManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}