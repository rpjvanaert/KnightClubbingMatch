package knight.clubbing.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import knight.clubbing.api.models.Match;
import knight.clubbing.api.models.Sprt;
import knight.clubbing.service.HistoryService;

import java.io.IOException;
import java.time.LocalDateTime;

import static knight.clubbing.ApiConfig.API_BASE_URL;

public class HistoryController implements ModuleController {

    private IMainController mainController;
    private HistoryService service;

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
        this.service = new HistoryService(API_BASE_URL);

        configureCellValueFactories();
        configureColumnWidths();
        configureDetailsButton();

        ObservableList<Match> matches = FXCollections.observableArrayList();
        historyTable.setItems(matches);
        matches.setAll(this.service.getMatchHistory(null, null));
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
