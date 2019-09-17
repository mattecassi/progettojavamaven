package Client.controller.InfoPackage;

import API.APIC;
import Client.controller.Ricerca;
import ClientUtils.Clausola;
import Models.*;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class InfoVino {

    private Stage thisStage;
    private final Ricerca ricerca;

    private boolean modified = false, nomeVinoModified = false, idVinoModified = false, tipoVinoModified = false;
    private Vino vino;
    private Cantina cantina;
    private Fornitore fornitore;
    @FXML
    private JFXTextField tfVinoNome;

    @FXML
    private JFXTextField tfVinoID;

    @FXML
    private JFXComboBox<String> cmbTipo;

    @FXML
    private JFXTextField tfVinoAnnata;

    @FXML
    private JFXTextField tfVinoCosto;

    @FXML
    private JFXTextField tfVinoVendita;

    @FXML
    private JFXTextField tfVinoQta;

    @FXML
    private ComboBox<String> cmbCantinaNome;

    @FXML
    private JFXTextField tfCantinaID;

    @FXML
    private JFXTextField tfCantinaStato;

    @FXML
    private JFXTextField tfCantinaRegione;

    @FXML
    private JFXTextField tfCantinaVia;

    @FXML
    private JFXTextField tfCantinaUvaggio;

    @FXML
    private JFXTextField tfCantinaIDRappre;

    @FXML
    private ComboBox<String> cmbFornitoreNome;

    @FXML
    private JFXTextField tfFornitoreID;

    @FXML
    private JFXTextField tfFornitoreTelefono;

    @FXML
    private JFXTextField tfFornitoreMail;

    @FXML
    private JFXTextField tfFornitoreQtaMin;

    @FXML
    private JFXTextField tfFornitoreQtaMax;

    @FXML
    private JFXButton btnAggiungi1, btnTogli1, btnElimina;

    public InfoVino(Ricerca ricerca) {
        //IMPOSTO IL LAYOUT DELLO STAGE CREATO
        this.ricerca = ricerca;

        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infoVino.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Info Vino");

            //CARICO I DATI NEI RISPETTIVI COMPONENTS
            WrapperVino cur = ricerca.getWrapperVino();
            vino = cur.vino;
            tfVinoNome.setText(vino.getNome());
            tfVinoID.setText(String.valueOf(vino.getID()));
            tfVinoCosto.setText(String.valueOf(vino.getCosto()));
            tfVinoVendita.setText(String.valueOf(vino.getPrezzoVendita()));
            tfVinoQta.setText(String.valueOf(vino.getQta()));
            tfVinoAnnata.setText(String.valueOf(vino.getAnno()));
            cmbTipo.setItems(Utility.loadDataForCmb("tipo_vino", "tipo", "", TipoVino.class));
            cmbTipo.getSelectionModel().select(vino.getTipo());

            cantina = cur.cantina;
            tfCantinaID.setText(String.valueOf(cantina.getID()));
            tfCantinaIDRappre.setText(String.valueOf(cantina.getIdrappresentante()));
            tfCantinaStato.setText(cantina.getStato());
            tfCantinaRegione.setText(cantina.getRegione());
            tfCantinaVia.setText(cantina.getVia());
            tfCantinaUvaggio.setText(cantina.getUvaggio());
            cmbCantinaNome.setItems(Utility.loadDataForCmb("cantina", "nome", "", Cantina.class));
            cmbCantinaNome.getSelectionModel().select(cantina.getNome());


            fornitore = cur.fornitore;
            tfFornitoreID.setText(String.valueOf(fornitore.getID()));
            tfFornitoreMail.setText(fornitore.getMail());
            tfFornitoreQtaMax.setText(String.valueOf(fornitore.getQta_max()));
            tfFornitoreQtaMin.setText(String.valueOf(fornitore.getQta_min()));
            tfFornitoreTelefono.setText(String.valueOf(fornitore.getTelefono()));
            cmbFornitoreNome.setItems(Utility.loadDataForCmb("fornitore", "nome", "", Fornitore.class));
            cmbFornitoreNome.getSelectionModel().select(fornitore.getNome());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //DEFISCO L'EVENT HANDLER DELLA CHIUSURO DELLO STAGE
        thisStage.setOnCloseRequest((WindowEvent event1) -> {
            closeProcedure();
            try {
                ricerca.loadTblVino();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void showStage() {
        thisStage.showAndWait();
    }

    //COSA FARE IN CASO DI CHIUSURA DELLA FINESTRA
    private void closeProcedure() {
        if (modified) { //SE SONO STATE FATTE DELLE MODIFICHE CHIEDO SE SALVARLE O NO
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Modifica");
            alert.setHeaderText("Hai effettuato modifica al vino");
            alert.setContentText("Vuoi salvare tali modifiche");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) { // IN CASO POSITIVO, SALVO
                try {
                    try {
                        //CONTROLLO CHE IL NUOVO DATO NON ABBIA STESSO NOME DI UN DATO GIA PRESENTE
                        String[] strings = {};
                        boolean valid = true;
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        APIC a = new APIC("vino");
                        clausolas.add(new Clausola("nome", "like", vino.getNome()));
                        if (a.select(strings, clausolas).toList(Vino.class).size() == 1 && nomeVinoModified) {
                            valid = false;
                        }
                        clausolas.remove(0);
                        if (vino.getTipo() != cmbTipo.getSelectionModel().getSelectedItem()) {
                            vino.setTipo(cmbTipo.getSelectionModel().getSelectedItem());
                        }
                        //SE TUTTO VA BENE, CHIAMO L'UPDATE SUL DATO IN CONSIDERAZIONE
                        if (valid) {
                            vino.update();
                        } else {
                            Utility.createErrorWindow("Impossibile completare a causa di un altro vino con stesso nome o codice");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Salvo");

            } else {//NON SALVO ED ESCO
                System.out.println("Non salvo");
            }
        }
    }


    //IN BASE AL BOTTONO PREMUTO, HO RISULTATI DIVERSI
    @FXML
    private void btnPress(Event e) {
        JFXButton btnPressed = (JFXButton) e.getSource();
        switch (btnPressed.getId()) {
            case "btnAggiungi1": //AGGIUNGO UN SOLO VINO ALLA QTA ATTUALE
                try {
                    vino.setQta(vino.getQta() + 1);
                    tfVinoQta.setText(String.valueOf(vino.getQta()));
                    modified = true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "btnTogli1": //TOLOG UN SOLO VINO DALLA QTA ATTUALE
                try {
                    vino.setQta(vino.getQta() - 1);
                    tfVinoQta.setText(String.valueOf(vino.getQta()));
                    modified = true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "btnElimina": //ELIMINO IN TOTO IL VINO PRESO IN CONSIDERAZIONE, DOPO AVER CHIESTO CONFERMA ALL'UTENTE
                                //IN CASO AFFERMATIVO ELIMINO E POI CHIUDO LO STAGE
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Conferma Eliminazione");
                    alert.setHeaderText(null);
                    alert.setContentText("Vuoi davvero eliminare il vino?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        vino.delete();
                        thisStage.close();
                    } else {
                    }
                } catch (Exception e1) {
                    Utility.createErrorWindow(e1.getMessage());
                }
                break;
            case "btnSalva": //SALVO
                closeProcedure();
                modified = false;
                break;
        }
    }

    @FXML
    private void valueModified(Event e) {
        modified = true;
        try {
            JFXTextField tfCur = (JFXTextField) e.getSource();
            switch (tfCur.getId()) {
                case "tfVinoNome":
                    nomeVinoModified = true;
                    vino.setNome(tfVinoNome.getText());
                    break;
//                case "tfVinoID":
//                    idVinoModified = true;
//                    vino.setID(Integer.valueOf(tfVinoID.getText()));
//                    break;
                case "tfVinoAnnata":
                    vino.setAnno(Integer.valueOf(tfVinoAnnata.getText()));
                    break;
                case "tfVinoCosto":
                    vino.setCosto(Double.valueOf(tfVinoCosto.getText()));
                    break;
                case "tfVinoVendita":
                    vino.setPrezzoVendita(Double.valueOf(tfVinoVendita.getText()));
                    break;
                case "tfVinoQta":
                    vino.setQta(Integer.valueOf(tfVinoQta.getText()));
                    break;

            }
        } catch (Exception e1) {
        }
    }

    @FXML
    private void changeCantina(Event event) {
        if (cmbCantinaNome.getSelectionModel().getSelectedItem() != cantina.getNome()) {
            try {
                modified = true;
                cantina.setNome(cmbCantinaNome.getSelectionModel().getSelectedItem());
                APIC a = new APIC("cantina");
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
                clausolas.add(new Clausola("nome", "like", cantina.getNome()));
                cantina = a.select(strings, clausolas).toList(Cantina.class).get(0);
                tfCantinaID.setText(String.valueOf(cantina.getID()));
                tfCantinaStato.setText(cantina.getStato());
                tfCantinaRegione.setText(String.valueOf(cantina.getRegione()));
                tfCantinaUvaggio.setText(String.valueOf(cantina.getUvaggio()));
                tfCantinaVia.setText(String.valueOf(cantina.getVia()));
                vino.setIdCantina(cantina.getID());
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }

    @FXML
    private void changeFornitore(Event event) {
        if (cmbFornitoreNome.getSelectionModel().getSelectedItem() != fornitore.getNome()) {
            try {
                modified = true;
                fornitore.setNome(cmbFornitoreNome.getSelectionModel().getSelectedItem());
                APIC a = new APIC("fornitore");
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
                clausolas.add(new Clausola("nome", "like", fornitore.getNome()));
                fornitore = a.select(strings, clausolas).toList(Fornitore.class).get(0);
                tfFornitoreID.setText(String.valueOf(fornitore.getID()));
                tfFornitoreMail.setText((fornitore.getMail()));
                tfFornitoreTelefono.setText((fornitore.getTelefono()));
                tfFornitoreQtaMax.setText(String.valueOf(fornitore.getQta_max()));
                tfFornitoreQtaMin.setText(String.valueOf(fornitore.getQta_min()));
                vino.setIdFornitore(fornitore.getID());
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }
}
