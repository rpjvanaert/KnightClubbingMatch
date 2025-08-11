package knight.clubbing;

import java.io.IOException;

public interface IMainController {
    void loadModule(String fxmlPath) throws IOException;
    void setStatus(String status);
    void setStatus(String status, LabelStatusType type);
}
