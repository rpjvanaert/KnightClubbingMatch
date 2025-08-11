package knight.clubbing;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController implements IMainController {

    public static final String FONT_SIZE_DEFAULT = "-fx-font-size: 14px; ";
    @FXML
    private StackPane moduleContainer;

    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        try {
            loadModule("/fxml/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadModule(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Node module = loader.load();
        Object controller = loader.getController();
        if (controller instanceof ModuleController)
            ((ModuleController) controller).setMainController(this);

        moduleContainer.getChildren().setAll(module);
    }

    @Override
    public void setStatus(String status) {
        setStatus(status, LabelStatusType.NORMAL);
    }

    @Override
    public void setStatus(String status, LabelStatusType type) {
        statusLabel.setText(status);
        switch (type) {
            case NORMAL:
                statusLabel.setStyle(FONT_SIZE_DEFAULT + "-fx-text-fill: black;");
                break;
            case WARNING:
                statusLabel.setStyle(FONT_SIZE_DEFAULT + "-fx-text-fill: orange;");
                break;
            case ERROR:
                statusLabel.setStyle(FONT_SIZE_DEFAULT + "-fx-text-fill: red;");
                break;
            case SUCCESS:
                statusLabel.setStyle(FONT_SIZE_DEFAULT + "-fx-text-fill: green;");
                break;
            case PENDING:
                statusLabel.setStyle(FONT_SIZE_DEFAULT + "-fx-text-fill: gray;");
                break;
            default:
                statusLabel.setStyle(FONT_SIZE_DEFAULT + "-fx-text-fill: black;");
                break;
        }
    }
}
