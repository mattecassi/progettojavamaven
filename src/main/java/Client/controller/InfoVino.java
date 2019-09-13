package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.Cantina;
import Models.Fornitore;
import Models.TipoVino;
import Models.Vino;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.rmi.CORBA.Util;
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
    private JFXTextField tfCantinaNome;

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
    private JFXTextField tfFornitoreNome;

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
            tfCantinaNome.setText(cantina.getNome());
            tfCantinaStato.setText(cantina.getStato());
            tfCantinaRegione.setText(cantina.getRegione());
            tfCantinaVia.setText(cantina.getVia());
            tfCantinaUvaggio.setText(cantina.getUvaggio());

            fornitore=cur.fornitore;
            tfFornitoreID.setText(String.valueOf(fornitore.getID()));
            tfFornitoreNome.setText(fornitore.getNome());
            tfFornitoreMail.setText(fornitore.getMail());
            tfFornitoreQtaMax.setText(String.valueOf(fornitore.getQta_max()));
            tfFornitoreQtaMin.setText(String.valueOf(fornitore.getQta_min()));
            tfFornitoreTelefono.setText(String.valueOf(fornitore.getTelefono()));


            thisStage.setOnCloseRequest((WindowEvent event1) -> {
                closeProcedure();
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.setResizable(false);
        thisStage.showAndWait();
    }

    private void closeProcedure() {
        if (modified) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Modifica");
            alert.setHeaderText("Hai effettuato modifica al vino");
            alert.setContentText("Vuoi salvare tali modifiche");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    try {
                        String[] strings = {};
                        boolean valid = true;
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        APIC a = new APIC("vino");
                        clausolas.add(new Clausola("nome", "like", vino.getNome()));
                        if (a.select(strings, clausolas).toList(Vino.class).size() == 1 && nomeVinoModified) {
                            valid = false;
                        }
                        clausolas.remove(0);
//                        clausolas.add(new Clausola("id", "=", String.valueOf(vino.getID())));
//                        if (a.select(strings, clausolas).toList(Vino.class).size() == 1 && idVinoModified) {
//                            valid = false;
//                        }
//                        clausolas.remove(0);
                        System.out.println(vino.getTipo().toString() + " " + cmbTipo.getSelectionModel().getSelectedItem().toString());
                        if (vino.getTipo() != cmbTipo.getSelectionModel().getSelectedItem()) {
                            vino.setTipo(cmbTipo.getSelectionModel().getSelectedItem());
                        }
                        System.out.println(valid);
                        if (valid) {
                            vino.update();
                            System.out.println(vino.toString());
                        } else {
                            Utility.createErrorWindow("Impossibile completare a causa di un altro vino con stesso nome o codice");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    cantina.update();
                    //fornitore.update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Salvo");
                try {
                    this.ricerca.loadAllTbl();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Non salvo");
            }
        }
    }

    @FXML
    private void btnPress(Event e) {
        JFXButton btnPressed = (JFXButton) e.getSource();
        switch (btnPressed.getId()) {
            case "btnAggiungi1":
                try {
                    vino.setQta(vino.getQta() + 1);
                    tfVinoQta.setText(String.valueOf(vino.getQta()));
                    modified = true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "btnTogli1":
                try {
                    vino.setQta(vino.getQta() - 1);
                    tfVinoQta.setText(String.valueOf(vino.getQta()));
                    modified = true;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "btnElimina":
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
            case "btnSalva":
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


}
