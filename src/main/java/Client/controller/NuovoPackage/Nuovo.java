package Client.controller.NuovoPackage;

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
        try{    //CARICO COMBO BOX
            cmbTipo.setItems(Utility.loadDataForCmb("tipo_vino","tipo","",TipoVino.class));
            cmbCantina.setItems(Utility.loadDataForCmb("cantina","nome","",Cantina.class));
            cmbFornitore.setItems(Utility.loadDataForCmb("fornitore","nome","",Fornitore.class));
        }catch (Exception e){
            e.getMessage();
        }
    }

    @FXML
    void inserisciElement(){ //CONTROLLO DI AVERE INSERITO TUTTI I CAMPI
        if (cmbFornitore.getSelectionModel().getSelectedItem()==null||  tfID.getText().isEmpty() || tfCosto.getText().isEmpty() || tfPrezzoVendita.getText().isEmpty()  || tfAnnata.getText().isEmpty() || cmbTipo.getSelectionModel().getSelectedItem() == null || tfQta.getText().isEmpty() || cmbCantina.getSelectionModel().getSelectedItem() == null || tfNome.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi");
        } else {

            Vino vino = new Vino();
            //ACQUISISCO I VALORI
            try {
                vino.setID(Integer.valueOf(tfID.getText()));
                vino.setNome(Utility.replaceAllDeniedChar(tfNome.getText()));
                vino.setAnno(Integer.valueOf(tfAnnata.getText()));
                vino.setCosto(Double.valueOf(tfCosto.getText().replace(",",".")));
                vino.setPrezzoVendita(Double.valueOf(tfPrezzoVendita.getText().replace(",",".")));
                vino.setQta(Integer.valueOf(tfQta.getText()));
                vino.setTipo(cmbTipo.getSelectionModel().getSelectedItem());
            }catch (NumberFormatException nfe){
                Utility.createErrorWindow("I campi Annata,costo,qta,prezzzo vendita, codice DEVONO ESSERE DEI NUMERI");
                return;
            }

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
            //ANALOGO PER IL FORNITORE
            APIC aFornitore = new APIC("fornitore");
            String[] stringsFornitore = {};
            ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
            clausolasFornitore.add(new Clausola("nome", "=", cmbFornitore.getSelectionModel().getSelectedItem()));
            try {
                Fornitore fornitore = aFornitore.select(stringsFornitore, clausolasFornitore).toObservableList(Fornitore.class).get(0);
                vino.setIdFornitore(fornitore.getID());
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
                e.printStackTrace();
            }


            try { //CONTROLLO CHE NON ESISTA GIA UN VINO CON STESSO NOME
                APIC aVino = new APIC("vino");
                String[] stringsVino = {"nome"};
                ArrayList<Clausola> clausolasVino = new ArrayList<Clausola>();
                clausolasVino.add(new Clausola("nome", "LIKE", vino.getNome()));
                if (aVino.select(stringsVino, clausolasVino).toList(Vino.class).isEmpty()) {
                    vino.insert();
                    Utility.createSuccessWindow("Inserimento avvenuto con successo");
                    //RESETTO I CAMPI COSI DA POTER INSERIRE SUBITO UN VINO NUOVO
                    tfCosto.setText("");
                    tfPrezzoVendita.setText("");
                    tfQta.setText("");
                    tfAnnata.setText("");
                    tfNome.setText("");
                    tfID.setText("");
                    cmbCantina.getSelectionModel().clearSelection();
                    cmbTipo.getSelectionModel().clearSelection();
                    cmbFornitore.getSelectionModel().clearSelection();
                    System.out.println(vino.toString());
                } else {
                    Utility.createErrorWindow("Presente");
                }
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }


    //CREO NUOVI STAGE IN BASE AL BOTTONO CLICCATO
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
