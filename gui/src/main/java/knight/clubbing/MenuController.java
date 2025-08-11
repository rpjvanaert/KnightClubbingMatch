package knight.clubbing;

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
            mainController.setStatus("To Match Setup");
            mainController.loadModule("/fxml/MatchSetup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHistoryButtonClicked() {
        try {
            mainController.setStatus("To History");
            mainController.loadModule("/fxml/History.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEngineManagementButtonClicked() {
        try {
            mainController.setStatus("To Engine Management");
            mainController.loadModule("/fxml/EngineManagement.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}