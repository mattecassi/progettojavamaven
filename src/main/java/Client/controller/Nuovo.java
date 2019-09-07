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
    private JFXTextField tfNome;

    @FXML
    private JFXButton btnInserisci,btnFornitore,btnRappresentante,btnEnoteca,btnTipoVino;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXComboBox<String> cmbCantina,cmbTipo;

    @FXML
    private JFXTextField tfQta;

    @FXML
    private JFXTextField tfPrezzoVendita,tfCosto;

    @FXML
    private void loadCmb(Event event){

        String[] strings={"nome"};
        ArrayList<Clausola> clausolas = new ArrayList<Clausola>();

        //cantina

        APIC aCantina = new APIC("cantina");
        try {
            ObservableList<String> nomiCantina = FXCollections.observableArrayList();
            nomiCantina.add("");
            ObservableList<Cantina> fornitores = aCantina.select(strings,clausolas).toObservableList(Cantina.class);

            for(Cantina cur: fornitores){
                nomiCantina.add(cur.getNome());
            }
            cmbCantina.setItems(nomiCantina);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //tipo
        APIC aTipo = new APIC("tipo_vino");
        try {
            String[] stringsTipo = {"tipo"};
            ObservableList<String> nomiTipo = FXCollections.observableArrayList();
            nomiTipo.add("");
            ObservableList<TipoVino> tipoVinos = aTipo.select(stringsTipo,clausolas).toObservableList(TipoVino.class);

            for(TipoVino cur: tipoVinos){
                nomiTipo.add(cur.getTipo());
            }
            cmbTipo.setItems(nomiTipo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO controllare che qta e annata siano dei numeri
    @FXML
    void inserisciElement(){
        APIReturn ret;
        //|| tfCodice.getText().isEmpty()
        if (tfCosto.getText().isEmpty() || tfPrezzoVendita.getText().isEmpty()  || tfAnnata.getText().isEmpty() || cmbTipo.getSelectionModel().getSelectedItem().isEmpty() || tfQta.getText().isEmpty() || cmbCantina.getSelectionModel().getSelectedItem().isEmpty() || tfNome.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi");
        } else {

            Vino vino = new Vino();

            vino.setNome(tfNome.getText());
            vino.setAnno(Integer.valueOf(tfAnnata.getText()));
            vino.setCosto(Double.valueOf(tfCosto.getText()));
            vino.setPrezzoVendita(Double.valueOf(tfPrezzoVendita.getText()));
            vino.setQta(Integer.valueOf(tfQta.getText()));


            APIC aCantina = new APIC("cantina");
            String[] stringsCantina = {};
            ArrayList<Clausola> clausolasCantina = new ArrayList<>();
            clausolasCantina.add(new Clausola("nome", "=", cmbCantina.getSelectionModel().getSelectedItem()));
            try {
                Cantina cantina = aCantina.select(stringsCantina, clausolasCantina).toObservableList(Cantina.class).get(0);
                vino.setIdCantina(cantina.getID());
            } catch (Exception e) {
                e.printStackTrace();
            }
            vino.setTipo(cmbTipo.getSelectionModel().getSelectedItem());

            try {
                APIC aVino = new APIC("vino");
                String[] stringsVino = {"nome"};
                ArrayList<Clausola> clausolasVino = new ArrayList<Clausola>();
                clausolasVino.add(new Clausola("nome", "LIKE", vino.getNome()));
                if (aVino.select(stringsVino, clausolasVino).toList(Vino.class).isEmpty()) {
                    vino.insert();
                    tfCosto.setText("");
                    tfPrezzoVendita.setText("");
                    tfQta.setText("");
                    tfAnnata.setText("");
                    tfNome.setText("");
                    cmbCantina.getSelectionModel().selectFirst();
                    cmbTipo.getSelectionModel().selectFirst();
                } else {
                    Utility.createErrorWindow("Presente");
                }
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
            }


            try {
                vino.insert();
                Utility.createSuccessWindow("Inserimento avvenuto con successo");
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
