package knight.clubbing;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MatchSetupController implements ModuleController {

    private IMainController mainController;

    @FXML
    private Spinner<Integer> baseTimeSpinner;

    @FXML
    private ComboBox<String> baseTimeUnitComboBox;

    @FXML
    private Spinner<Integer> incrementSpinner;

    @FXML
    private ComboBox<String> incrementUnitComboBox;

    @FXML
    private Spinner<Integer> amountMatchesSpinner;

    @FXML
    private CheckBox sprtCheckBox;

    @FXML
    private Pane sprtSettingsBox;
    @FXML
    private Spinner<Double> sprtAlphaSpinner;
    @FXML
    private Spinner<Double> sprtBetaSpinner;
    @FXML
    private Spinner<Integer> sprtElo0Spinner;
    @FXML
    private Spinner<Integer> sprtElo1Spinner;

    @Override
    public void setMainController(IMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        this.sprtCheckBox.selectedProperty().addListener((_, _, isSelected) -> {
            sprtSettingsBox.setVisible(isSelected);
            sprtSettingsBox.setManaged(isSelected);
        });
        this.baseTimeUnitComboBox.setValue("minutes");
        this.incrementUnitComboBox.setValue("seconds");
    }

    @FXML
    private void onStart() {
        try {
            mainController.loadModule("/fxml/LiveMatchView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onStartAndWatch() {
        try {
            mainController.loadModule("/fxml/LiveMatchSpectator.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBackToMenu() {
        try {
            mainController.loadModule("/fxml/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
