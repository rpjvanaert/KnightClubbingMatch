package knight.clubbing;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import knight.clubbing.api.models.Engine;

import java.io.IOException;
import java.util.Arrays;

public class EngineManagementController implements ModuleController {

    private IMainController mainController;

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
        configureCellValueFactories();

        ObservableList<Engine> engineList = FXCollections.observableArrayList(
                new Engine("org.example:engine1", "Engine 1", new String[]{"option1", "option2"}, "https://repo.maven.apache.org/maven2/org/example/engine1",
                        new String[]{"1.0", "1.1"}),
                new Engine("org.example:engine2", "Engine 2", new String[]{"optionA", "optionB"}, "https://repo.maven.apache.org/maven2/org/example/engine2",
                        new String[]{"2.0", "2.1"})
        ); //todo replace with actual engine data retrieval logic
        engineTable.setItems(engineList);
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
