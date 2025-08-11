package knight.clubbing;

import javafx.fxml.FXML;

import java.io.IOException;

public class MatchSetupController implements ModuleController {

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
            mainController.setStatus("Back to Menu", LabelStatusType.SUCCESS);
            mainController.loadModule("/fxml/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
