package Client;

import javafx.scene.control.Alert;

public class AlertWindow {
    static public void createErrorWindow(String s){
        Alert error = new Alert(Alert.AlertType.ERROR, s);
        error.setHeaderText(null);
        error.showAndWait();
    }
}
