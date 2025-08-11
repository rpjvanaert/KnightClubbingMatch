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
    private void onStartButtonClicked() {
        try {
            mainController.setStatus("To Match Setup");
            mainController.loadModule("/fxml/MatchSetup.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}