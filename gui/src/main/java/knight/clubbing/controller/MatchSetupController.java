package knight.clubbing.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Pane;
import knight.clubbing.api.models.RunRequest;

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

    @FXML
    private Spinner<Integer> amountThreadsSpinner;

    @FXML
    private CheckBox ponderingCheckBox;

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
        this.baseTimeUnitComboBox.setValue("seconds");
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
            versions.addAll("v15.1", "v14.1", "v13.1", "v12.1"); //todo from engine manager
        }
    }

    @FXML
    private void onStart() {
        try {
            RunRequest request = determineRunRequest();
            System.out.println(request);

            mainController.loadModule("/fxml/LiveMatchView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RunRequest determineRunRequest() {
        String engine1 = engine1ComboBox.getValue() + ":" + engine1VersionComboBox.getValue();
        String engine2 = engine2ComboBox.getValue() + ":" + engine2VersionComboBox.getValue();
        String timeControl = getTimeControl();

        //todo threads, pondering

        return new RunRequest(
                engine1,
                engine2,
                timeControl,
                amountMatchesSpinner.getValue(),
                null,
                sprtCheckBox.isSelected()
        );
    }

    private String getTimeControl() {
        int baseTime = baseTimeSpinner.getValue();
        if (baseTimeUnitComboBox.getValue().equals("minutes")) {
            baseTime = baseTime * 60;
        }

        int increment = incrementSpinner.getValue();
        if (incrementUnitComboBox.getValue().equals("minutes")) {
            increment = increment * 60;
        }

        return baseTime + "|" + increment;
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
