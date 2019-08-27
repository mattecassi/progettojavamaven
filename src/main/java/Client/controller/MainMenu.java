package Client.controller;


import Client.Main2;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class MainMenu {

    Main2 changer = new Main2();
    Stage popUpStage = new Stage();
    Scene popUpScene;
    Pane popUpPane;
    AnchorPane newLoadedPane;
    @FXML
    private JFXButton btnNuovoView;

    @FXML
    private JFXButton btnAggiornaView;

    @FXML
    private JFXButton btnCheckListView;

    @FXML
    private JFXButton btnInventarioView;

    @FXML
    private Pane menuPane;

    @FXML
    private JFXButton btnRicercaView;



    @FXML
    void btnPressMainMenu(Event btnPressEvent) throws  Exception{
        Button btnPressed = (Button)btnPressEvent.getSource();
        //Layer layer = new Layer();
        switch (btnPressed.getId()){
            case "btnRicercaView":
                changer.changeScene("view/ricerca.fxml");
                System.out.println("Provo");
                //layer.load("../view/ricerca.fxml");
                break;
            case "btnAggiornaView":
                changer.changeScene("view/aggiorna.fxml");
                //layer.load("../view/aggiorna.fxml");
                break;
            case "btnNuovoView":
                changer.changeScene("view/nuovo.fxml");
                //layer.load("../view/nuovo.fxml");
                break;
            case "btnInventarioView":
                popUpPane = FXMLLoader.load(getClass().getResource("../view/inventario.fxml"));
                popUpScene = new Scene(popUpPane);
                popUpStage.setScene(popUpScene);
                popUpStage.setTitle("Inventario");
                //popUpStage.initOwner(Main2.window);
                //popUpStage.initModality(Modality.WINDOW_MODAL);
                popUpStage.show();
                break;
            case  "btnCheckListView":
                changer.changeScene("view/checkList.fxml");
                //layer.load("../view/checkList.fxml");
                break;
            default:
                System.out.println(btnPressed.getId());
        }

    }
}
