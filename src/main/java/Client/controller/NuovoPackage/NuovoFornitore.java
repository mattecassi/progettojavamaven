package Client.controller.NuovoPackage;

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
    private JFXTextField tfStato, tfRegione, tfCitta, tfVia;

    @FXML//CARICO LA COMBOBOX
    private void showElement(Event event){
        if(!false) {
            ObservableList<String> opzioni = FXCollections.observableArrayList("Rappresentante","Enoteca","Fornitore");
            cmbSelect.setItems(opzioni);
            cmbShown=true;
        }
    }

    @FXML//IN BASE ALLA SCELTA DELLA COMBOBOX FACCIO VEDERE ALTRI TEXTFIELD
    private void showTf(Event event) {
        tfStato.setVisible(false);
        tfRegione.setVisible(false);
        tfCitta.setVisible(false);
        tfVia.setVisible(false);
        if (cmbSelect.getSelectionModel().getSelectedItem() != null)   {
            switch (cmbSelect.getSelectionModel().getSelectedItem()) {
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
    }

    @FXML//CONTROLLO I DATI POI INSERISCO
    void insertElement(ActionEvent event) {
        boolean check = true;
        if(cmbSelect.getSelectionModel().getSelectedItem() == null || tfNome.getText().isEmpty() || tfMail.getText().isEmpty() || tfQtaMin.getText().isEmpty() || tfQtaMax.getText().isEmpty() || tfTelefono.getText().isEmpty()){
            check=false;
            Utility.createErrorWindow("Inserisci tutti i campi");
            }
        if(check){
            if(opzione.equalsIgnoreCase("Enoteca") && (tfStato.getText().isEmpty() || tfRegione.getText().isEmpty() || tfCitta.getText().isEmpty() || tfVia.getText().isEmpty())){
                check = false;
                Utility.createErrorWindow("Inserisci tutti i campi");
            }
        }
        try{
        if (check){
            Fornitore fornitore = new Fornitore();
            try {
                //ACQUISISCO I DATI
                fornitore.setNome(Utility.replaceAllDeniedChar(tfNome.getText()));
                fornitore.setMail(Utility.replaceAllDeniedChar(tfMail.getText()));
                fornitore.setQta_max(Integer.valueOf(tfQtaMax.getText()));
                fornitore.setQta_min((Integer.valueOf(tfQtaMin.getText())));
                fornitore.setTelefono(Utility.replaceAllDeniedChar(tfTelefono.getText()));
            }catch (NumberFormatException nfe){
                Utility.createErrorWindow("I qta max e qta min devono ESSERE DEI NUMERI");
                return;
            }catch (Exception e){
                Utility.createErrorWindow("Errore in " + e.getMessage());
                return;
            }

            try {
                //CONTROLLO NON ESISTA GIA UN FORNITORE CON STESSO NOME
                APIC a = new APIC("fornitore");
                String[] strings={"nome"};
                ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
                clausolas.add(new Clausola("nome", "LIKE", fornitore.getNome()));
                if(a.select(strings,clausolas).toList(Fornitore.class).isEmpty()) {
                    //INSERISCO POI RESETTO
                    fornitore.insert();
                    tfNome.setText("");
                    tfQtaMax.setText("");
                    tfQtaMin.setText("");
                    tfTelefono.setText("");
                    tfMail.setText("");
                    cmbSelect.getSelectionModel().clearSelection();

                }
                else{
                    Utility.createErrorWindow("Presente");
                    check = false;
                }
            }catch (Exception e){
                Utility.createErrorWindow(e.getMessage());
            }
            if(check) {//ORA CONTROLLO L'OPZIONE SCELTA E CREO UN FORNITORE SPECIALIZZATO DI CONSEGUENZA, ANALOGAMENTE
                switch (opzione) {
                    case "Enoteca":

                        Enoteca enoteca = new Enoteca();
                        enoteca.setCitta(Utility.replaceAllDeniedChar(tfCitta.getText()));
                        enoteca.setStato(Utility.replaceAllDeniedChar(tfStato.getText()));
                        enoteca.setRegione(Utility.replaceAllDeniedChar(tfRegione.getText()));
                        enoteca.setVia(Utility.replaceAllDeniedChar(tfVia.getText()));
                        enoteca.setID(fornitore.getID());

                        tfStato.setText("");
                        tfRegione.setText("");
                        tfCitta.setText("");
                        tfVia.setText("");
                        try {
                            enoteca.insert();
                            Utility.createSuccessWindow("Inserimento avvenuto con successo");

                        } catch (Exception e) {
                            Utility.createErrorWindow(e.getMessage());
                        }
                        break;
                    case "Rappresentante":

                        Rappresentante rappresentante = new Rappresentante();
                        rappresentante.setID(fornitore.getID());

                        try {
                            rappresentante.insert();
                            Utility.createSuccessWindow("Inserimento avvenuto con successo");

                        } catch (Exception e) {
                            Utility.createErrorWindow(e.getMessage());
                        }

                        break;
                        default:{
                            Utility.createSuccessWindow("Inserimento avvenuto con successo");
                        }break;
                }
            }
        }
        }catch (Exception exception){
            Utility.createErrorWindow(exception.getMessage());
        }
    }

}
