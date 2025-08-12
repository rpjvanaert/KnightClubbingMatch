package knight.clubbing;

import javafx.fxml.FXML;

import java.io.IOException;

public class LiveMatchViewController implements ModuleController {

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
            mainController.loadModule("/fxml/LiveMatchSpectator.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
