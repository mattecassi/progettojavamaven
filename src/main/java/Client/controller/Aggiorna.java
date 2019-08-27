package Client.controller;

import Client.Main2;
import Client.excel.*;
import com.jfoenix.controls.JFXListView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;

import java.io.*;
import java.util.List;

public class Aggiorna extends Client.controller.MainMenu {


    @FXML
    private Button btnAquisto,btnVendita,btnAltro;

    @FXML
    private JFXListView lstViewVendita;

    @FXML
    private Label lblCheck;

    @FXML
    public void btnPressOperazione(Event event) throws Exception {
        Stage stage;
        Main2 main = new Main2();
        Button btnPressed = (Button)event.getSource();
        switch (btnPressed.getId()){
            case "btnVendita":
                stage= main.createStage("/view/vendita.fxml","Vendita");
                break;
            case "btnAcquisto":
                break;
            case "btnAltro":
                break;
            default:
                break;
        }
    }
}
