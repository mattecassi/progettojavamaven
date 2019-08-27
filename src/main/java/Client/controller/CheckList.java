package Client.controller;

import Client.Main2;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CheckList {

    Main2 main=new Main2();

    @FXML
    private AnchorPane menuAP;

    @FXML
    private JFXButton btnAddGiornaliero;

    @FXML
    private JFXButton btnAddSettimanale;

    @FXML
    private JFXButton btnAddMensile;

    @FXML
    void addGiornaliero(ActionEvent event) {
        try{
            main.createStage("/view/addGiornaliero.view","Aggiungi compito giornaliero");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addSettimanale(ActionEvent event) {
        try{
            main.createStage("/view/addSettimanale.view","Aggiungi compito settimanale");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMensile(ActionEvent event) {
        try{
            main.createStage("/view/addMensile.view","Aggiungi compito mensile");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
