package knight.clubbing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML
    private ComboBox<String> engine1ComboBox;

    @FXML
    private ComboBox<String> engine1VersionComboBox;

    @FXML
    private ComboBox<String> engine2ComboBox;

    @FXML
    private ComboBox<String> engine2VersionComboBox;

    private ObservableList<String> engines;
    private ObservableList<String> versions1;
    private ObservableList<String> versions2;

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

        this.engines = FXCollections.observableArrayList();
        this.versions1 = FXCollections.observableArrayList();
        this.versions2 = FXCollections.observableArrayList();
        this.engine1ComboBox.setItems(engines);
        this.engine2ComboBox.setItems(engines);
        this.engine1VersionComboBox.setItems(versions1);
        this.engine2VersionComboBox.setItems(versions2);
        this.engine1VersionComboBox.setDisable(true);
        this.engine2VersionComboBox.setDisable(true);
        this.engines.add("");
        this.engines.addAll("Stockfish", "Lc0", "Komodo", "Ethereal", "Torch"); //todo from engine manager


        this.engine1ComboBox.valueProperty().addListener((_, oldValue, newValue) ->
                onEngineComboBoxChanged(oldValue, newValue, engine1VersionComboBox, versions1)
        );

        this.engine2ComboBox.valueProperty().addListener((_, oldValue, newValue) ->
                onEngineComboBoxChanged(oldValue, newValue, engine2VersionComboBox, versions2)
        );
    }

    public void onEngineComboBoxChanged(String oldValue, String newValue, ComboBox<String> engineVersionComboBox, ObservableList<String> versions) {
        if (oldValue != null && oldValue.equals(newValue))
            return;

        if (newValue == null || newValue.isEmpty()) {
            engineVersionComboBox.getSelectionModel().clearSelection();
            engineVersionComboBox.setDisable(true);
        } else {
            engineVersionComboBox.setDisable(false);
            versions.clear();
            versions.addAll(newValue + ":latest", "v15.1", "v14.1", "v13.1", "v12.1"); //todo from engine manager
        }
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
