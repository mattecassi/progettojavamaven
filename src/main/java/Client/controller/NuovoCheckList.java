package Client.controller;

import Models.Compito;
import Models.Enoteca;
import Models.Fornitore;
import Utils.Utility;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class NuovoCheckList {
    private Stage thisStage;
    private final CheckList checkList;
    final ObservableList<String> tipoCompiti = FXCollections.observableArrayList("Giornaliero", "Settimanale", "Occasionale");

    @FXML
    private JFXComboBox<String> cmbTipoCompito;

    @FXML
    private JFXTextField tfDescrizione;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXCheckBox checkLunedi;

    @FXML
    private JFXCheckBox checkMartedi;

    @FXML
    private JFXCheckBox checkMercoledi;

    @FXML
    private JFXCheckBox checkGiovedi;

    @FXML
    private JFXCheckBox checkVenerdi;

    @FXML
    private JFXCheckBox checkSabato;

    @FXML
    private JFXCheckBox checkDomenica;

    @FXML
    private HBox hboxData;

    public NuovoCheckList(CheckList checkList) {
        this.checkList = checkList;

        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/nuovoCheckList.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Nuovo compito");


            cmbTipoCompito.setItems(tipoCompiti);
            cmbTipoCompito.getSelectionModel().selectFirst();
        } catch (Exception e) {
        }
        thisStage.setOnCloseRequest((WindowEvent event1) -> {
            closeProcedure();
        });
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    private void closeProcedure() {
        //COSA FACCIO QUANDO CHIUDO
        //checkList.load();
    }

    @FXML
    private void selectTipoCompito() {
        String selection = cmbTipoCompito.getSelectionModel().getSelectedItem();
        hboxData.setVisible(false);
        datePicker.setVisible(false);
        switch (selection) {
            case "Giornaliero":
                break;
            case "Settimanale":
                hboxData.setVisible(true);
                break;
            case "Occasionale":
                datePicker.setVisible(true);
                break;
        }
    }

    @FXML
    private void addCompito() {
        if (tfDescrizione.getText().isEmpty()) {
            Utility.createWarningWindow("Inserisci la descrizione");
        } else {
            Compito compito;
            switch (cmbTipoCompito.getSelectionModel().getSelectedItem()) {
                case "Giornaliero":
                    try {
                        compito = new Compito(tfDescrizione.getText());
                        compito.insert();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "Settimanale":
                    ArrayList<Integer> giorni = new ArrayList<>();
                    if (checkLunedi.isSelected()) {
                        giorni.add(1);
                        checkLunedi.setSelected(false);
                    }
                    if (checkMartedi.isSelected()) {
                        giorni.add(2);
                        checkMartedi.setSelected(false);
                    }
                    if (checkMercoledi.isSelected()) {
                        giorni.add(3);
                        checkMercoledi.setSelected(false);
                    }
                    if (checkGiovedi.isSelected()) {
                        giorni.add(4);
                        checkGiovedi.setSelected(false);
                    }
                    if (checkVenerdi.isSelected()) {
                        giorni.add(5);
                        checkVenerdi.setSelected(false);
                    }
                    if (checkSabato.isSelected()) {
                        giorni.add(6);
                        checkSabato.setSelected(false);
                    }
                    if (checkDomenica.isSelected()) {
                        giorni.add(0);
                        checkDomenica.setSelected(false);
                    }
                    if (giorni.isEmpty())
                        giorni.add(0);
                    for (Integer cur : giorni) {
                        try {
                            compito = new Compito(tfDescrizione.getText(), cur);
                            compito.insert();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case "Occasionale":
                    if (datePicker.getValue() == null) {
                        Utility.createWarningWindow("Inserisci la data");
                    } else {
                        //SALVO LA DATA SELEZIONATA
                        try {
                            compito = new Compito(tfDescrizione.getText(), datePicker.getValue());
                            compito.insert();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //UNA VOLTA TERMINATO RESETTO I CAMPI
                        datePicker.setValue(null);
                    }
                    break;
            }
            tfDescrizione.setText("");
        }
    }
}
