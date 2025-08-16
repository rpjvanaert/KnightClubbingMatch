package knight.clubbing;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import knight.clubbing.api.models.Match;
import knight.clubbing.api.models.Sprt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class HistoryController implements ModuleController {

    private IMainController mainController;

    @FXML
    private TableView<Match> historyTable;

    @FXML
    private TableColumn<Match, Void> detailsColumn;

    @FXML
    private TableColumn<Match, String> resultColumn;

    @FXML
    private TableColumn<Match, String> engine1Column;

    @FXML
    private TableColumn<Match, String> engine2Column;

    @FXML
    private TableColumn<Match, String> dateColumn;

    @FXML
    private TableColumn<Match, String> sprtColumn;

    @FXML
    private TableColumn<Match, String> statusColumn;

    @Override
    public void setMainController(IMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        configureCellValueFactories();
        configureColumnWidths();
        configureDetailsButton();

        ObservableList<Match> matches = FXCollections.observableArrayList(
                new Match("idOfMatch1", "EngineA", "EngineB", "5+0", "1-0", "completed",
                        LocalDateTime.now(), null, null,
                        new Sprt("passed", 1500, 1450, 1475, 0.05, 0.05, 1400, 1550)),
                new Match("idOfMatch2", "EngineC", "EngineB", "5+0", "1/2-1/2", "completed",
                        LocalDateTime.now(), null, null,
                        new Sprt("passed", 1500, 1450, 1475, 0.05, 0.05, 1400, 1550)),
                new Match("idOfMatch2", "EngineA", "EngineC", "5+0", "0-0", "completed",
                        LocalDateTime.now(), null, null,
                        new Sprt("passed", 1500, 1450, 1475, 0.05, 0.05, 1400, 1550))
        );
        historyTable.setItems(matches);
    }

    private void configureCellValueFactories() {
        resultColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().result()));
        engine1Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().engine1()));
        engine2Column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().engine2()));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().status()));
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().completedAt() != null ? cellData.getValue().completedAt().toLocalDate().toString() : ""
                )
        );
        sprtColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().sprt() != null ? cellData.getValue().sprt().state() : ""
                )
        );
    }

    private void configureColumnWidths() {
        resultColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.125));
        engine1Column.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.250));
        engine2Column.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.250));
        dateColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.125));
        sprtColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.125));
        statusColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.125));

        resultColumn.setResizable(false);
        engine1Column.setResizable(false);
        engine2Column.setResizable(false);
        dateColumn.setResizable(false);
        sprtColumn.setResizable(false);
        statusColumn.setResizable(false);
    }

    private void configureDetailsButton() {
        detailsColumn.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            private final javafx.scene.control.Button btn = new javafx.scene.control.Button("Details");
            {
                btn.setOnAction(event -> {
                    Match match = getTableView().getItems().get(getIndex());
                    showMatchDetails(match.id());
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void showMatchDetails(String matchId) {
        // Implement navigation or dialog showing details for matchId
        System.out.println("Show details for match: " + matchId);
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
