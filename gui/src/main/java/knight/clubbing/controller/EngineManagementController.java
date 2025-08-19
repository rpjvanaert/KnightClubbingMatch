package knight.clubbing.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import knight.clubbing.api.MatchApi;
import knight.clubbing.api.models.Engine;
import knight.clubbing.service.MatchApiClient;

import java.io.IOException;
import java.util.Arrays;

import static knight.clubbing.ApiConfig.API_BASE_URL;

public class EngineManagementController implements ModuleController {

    private IMainController mainController;
    private MatchApi matchApi;

    @FXML
    private TableView<Engine> engineTable;

    @FXML
    private TableColumn<Engine, String> engineNameColumn;

    @FXML
    private TableColumn<Engine, String> mvnRepositoryColumn;

    @FXML
    private TableColumn<Engine, String> engineOptionsColumn;

    @Override
    public void setMainController(IMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        this.matchApi = new MatchApiClient(API_BASE_URL);
        configureCellValueFactories();

        ObservableList<Engine> engineList = FXCollections.observableArrayList();
        engineTable.setItems(engineList);
        engineList.setAll(this.matchApi.getEngines());
    }

    private void configureCellValueFactories() {
        engineNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        mvnRepositoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().mvnRepository()));
        engineOptionsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Arrays.toString(cellData.getValue().availableOptions())));
    }

    @FXML
    private void onBackToMenu() {
        try {
            mainController.loadModule("/fxml/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddEngine(ActionEvent actionEvent) {
        System.out.println("Adding new engine..."); //todo implement engine addition logic

    }

    public void onRemoveEngine() {
        System.out.println("Removing engines: " + engineTable.getSelectionModel().getSelectedItems()); //todo implement engine removal logic
    }
}
