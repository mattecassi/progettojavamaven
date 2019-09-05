package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.Enoteca;
import Models.Fornitore;
import Models.Rappresentante;
import Models.TipoVino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

public class NuovoFornitore {

    private boolean cmbShown = false;
    private String opzione = "Fornitore";

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXTextField tfMail;

    @FXML
    private JFXTextField tfQtaMin;

    @FXML
    private JFXTextField tfQtaMax;

    @FXML
    private JFXTextField tfTelefono;

    @FXML
    private ComboBox<String> cmbSelect;

    @FXML
    private JFXTextField tfCognome;

    @FXML
    private JFXTextField tfStato, tfRegione, tfCitta, tfVia;

    @FXML
    private void showElement(Event event){
        if(!false) {
            ObservableList<String> opzioni = FXCollections.observableArrayList("Rappresentante","Enoteca","Fornitore");
            cmbSelect.setItems(opzioni);
            cmbShown=true;
        }
    }

    @FXML
    private void showTf(Event event){
        tfCognome.setVisible(false);
        tfStato.setVisible(false);
        tfRegione.setVisible(false);
        tfCitta.setVisible(false);
        tfVia.setVisible(false);
        switch(cmbSelect.getSelectionModel().getSelectedItem()){
            case "Rappresentante":
                tfCognome.setVisible(true);
                break;
            case "Enoteca":
                tfStato.setVisible(true);
                tfRegione.setVisible(true);
                tfCitta.setVisible(true);
                tfVia.setVisible(true);
                break;
            default:
                break;
        }
        opzione = cmbSelect.getSelectionModel().getSelectedItem();
    }

    @FXML
    void insertElement(ActionEvent event) {
        boolean check = true;
        if(tfNome.getText().isEmpty() || tfMail.getText().isEmpty() || tfQtaMin.getText().isEmpty() || tfQtaMax.getText().isEmpty() || tfTelefono.getText().isEmpty()){
            check=false;
            Utility.createErrorWindow("Inserisci tutti i campi");
            }
        if(check){
            if(opzione.equalsIgnoreCase("Rappresentante") && tfCognome.getText().isEmpty()){
                check = false;
                Utility.createErrorWindow("Inserisci tutti i campi");
            }
            if(opzione.equalsIgnoreCase("Enoteca") && (tfStato.getText().isEmpty() || tfRegione.getText().isEmpty() || tfCitta.getText().isEmpty() || tfVia.getText().isEmpty())){
                check = false;
                Utility.createErrorWindow("Inserisci tutti i campi");
            }
        }
        try{
        if (check){
            //Button btnPressed = (Button)event.getSource();
            Fornitore fornitore = new Fornitore();
            try {
                fornitore.setNome(tfNome.getText());
                fornitore.setMail(tfMail.getText());
                fornitore.setQta_max(Integer.valueOf(tfQtaMax.getText()));
                fornitore.setQta_min((Integer.valueOf(tfQtaMin.getText())));
                fornitore.setTelefono(tfNome.getText());
            }catch (Exception e){
                Utility.createErrorWindow("Errore in " + e.getMessage());
            }

            try {
                boolean presente = false;
                APIC a = new APIC("fornitore");
                APIReturn ret;
                String[] strings={"nome"};
                ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
                clausolas.add(new Clausola("nome", "LIKE", fornitore.getNome()));
                if(a.select(strings,clausolas).toList(Fornitore.class).isEmpty()) {
                    fornitore.insert();
                    tfNome.setText("");
                    tfQtaMax.setText("");
                    tfQtaMin.setText("");
                    tfTelefono.setText("");
                    tfMail.setText("");
                }
                else{
                    Utility.createErrorWindow("Presente");
                    check = false;
                }
            }catch (Exception e){
                Utility.createErrorWindow(e.getMessage());
            }
            if(check) {
                switch (opzione) {
                    case "Enoteca":

                        Enoteca enoteca = new Enoteca();
                        enoteca.setCitta(tfCitta.getText());
                        enoteca.setStato(tfStato.getText());
                        enoteca.setRegione(tfRegione.getText());
                        enoteca.setVia(tfVia.getText());
                        enoteca.setID(fornitore.getID());

                        tfStato.setText("");
                        tfRegione.setText("");
                        tfCitta.setText("");
                        tfVia.setText("");
                        try {
                            enoteca.insert();
                        } catch (Exception e) {
                            Utility.createErrorWindow(e.getMessage());
                        }
                        break;
                    case "Rappresentante":

                        Rappresentante rappresentante = new Rappresentante();
                        rappresentante.setCognome(tfCognome.getText());
                        rappresentante.setNome_rappresentante(fornitore.getNome());
                        rappresentante.setID(fornitore.getID());

                        tfCognome.setText("");

                        try {
                            rappresentante.insert();
                        } catch (Exception e) {
                            Utility.createErrorWindow(e.getMessage());
                        }

                        break;
                }
            }
        }
        }catch (Exception exception){
            Utility.createErrorWindow(exception.getMessage());
        }
    }

}
