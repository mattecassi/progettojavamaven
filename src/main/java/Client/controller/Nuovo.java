package Client.controller;

import API.APIC;
import Client.Main2;
import ClientUtils.Clausola;
import Models.*;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Nuovo{

    @FXML
    private  JFXTextField tfID;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXButton btnInserisci,btnFornitore,btnRappresentante,btnEnoteca,btnTipoVino;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXComboBox<String> cmbCantina,cmbTipo, cmbFornitore;

    @FXML
    private JFXTextField tfQta;

    @FXML
    private JFXTextField tfPrezzoVendita,tfCosto;

    @FXML
    private void loadCmb(Event event){
        try{
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            cmbTipo.setItems(Utility.loadDataForCmb("tipo_vino","tipo","",TipoVino.class));
            cmbCantina.setItems(Utility.loadDataForCmb("cantina","nome","",Cantina.class));
            cmbFornitore.setItems(Utility.loadDataForCmb("fornitore","nome","",Cantina.class));
        }catch (Exception e){
            e.getMessage();
        }
    }

    //TODO controllare che qta e annata siano dei numeri
    @FXML
    void inserisciElement(){
        APIReturn ret;
        //|| tfCodice.getText().isEmpty()
        if (tfID.getText().isEmpty() || tfCosto.getText().isEmpty() || tfPrezzoVendita.getText().isEmpty()  || tfAnnata.getText().isEmpty() || cmbTipo.getSelectionModel().getSelectedItem().isEmpty() || tfQta.getText().isEmpty() || cmbCantina.getSelectionModel().getSelectedItem().isEmpty() || tfNome.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi");
        } else {

            Vino vino = new Vino();

            //TODO CONTROLLO NUMERI

            vino.setID(Integer.valueOf(tfID.getText()));
            vino.setNome(tfNome.getText());
            vino.setAnno(Integer.valueOf(tfAnnata.getText()));
            vino.setCosto(Double.valueOf(tfCosto.getText()));
            vino.setPrezzoVendita(Double.valueOf(tfPrezzoVendita.getText()));
            vino.setQta(Integer.valueOf(tfQta.getText()));
            vino.setTipo(cmbTipo.getSelectionModel().getSelectedItem());

            //IN BASE AL NOME SELEZIONATO, FACCIO IL SELECT DI TALE CANTINA E NE PRENDO L'ID
            APIC aCantina = new APIC("cantina");
            String[] stringsCantina = {};
            ArrayList<Clausola> clausolasCantina = new ArrayList<>();
            clausolasCantina.add(new Clausola("nome", "=", cmbCantina.getSelectionModel().getSelectedItem()));
            try {
                Cantina cantina = aCantina.select(stringsCantina, clausolasCantina).toObservableList(Cantina.class).get(0);
                vino.setIdCantina(cantina.getID());
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
                e.printStackTrace();
            }
//
//            APIC aFornitore = new APIC("fornitore");
//            String[] stringsFornitore = {};
//            ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
//            clausolasCantina.add(new Clausola("nome", "=", cmbFornitore.getSelectionModel().getSelectedItem()));
//            try {
//                Fornitore fornitore = aFornitore.select(stringsFornitore, clausolasFornitore).toObservableList(Fornitore.class).get(0);
//                vino.setIdFornitore(fornitore.getID());
//            } catch (Exception e) {
//                Utility.createErrorWindow(e.getMessage());
//                e.printStackTrace();
//            }


            try {
                APIC aVino = new APIC("vino");
                String[] stringsVino = {"nome"};
                ArrayList<Clausola> clausolasVino = new ArrayList<Clausola>();
                clausolasVino.add(new Clausola("nome", "LIKE", vino.getNome()));
                if (aVino.select(stringsVino, clausolasVino).toList(Vino.class).isEmpty()) {
                    vino.insert();
                    Utility.createSuccessWindow("Inserimento avvenuto con successo");
                    tfCosto.setText("");
                    tfPrezzoVendita.setText("");
                    tfQta.setText("");
                    tfAnnata.setText("");
                    tfNome.setText("");
                    tfID.setText("");
                    cmbCantina.getSelectionModel().selectFirst();
                    cmbTipo.getSelectionModel().selectFirst();
                    cmbFornitore.getSelectionModel().selectFirst();
                    System.out.println(vino.toString());
                } else {
                    Utility.createErrorWindow("Presente");
                }
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }

    @FXML
    private void changeScene(Event event) throws Exception {
        Main2 main = new Main2();
        Stage stage;

        FXMLLoader loader = new FXMLLoader();
        Button btnPressed = (Button)event.getSource();
        switch (btnPressed.getId()){
            case "btnFornitore":
                stage = main.createStage("/view/nuovoFornitore.fxml", "Nuovo Fornitore");
                break;
            case "btnCantina":
                stage = main.createStage("/view/nuovoCantina.fxml", "Nuova Cantina");
                break;
            case "btnTipoVino":
                stage = main.createStage("/view/nuovoTipoVino.fxml", "Nuovo Tipo Vino");
                break;
        }

    }
}
