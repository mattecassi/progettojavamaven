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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class InfoCantina {

    private Stage thisStage;
    private final Ricerca ricerca;

    private boolean modified = false, nomeCantinaModified = false;
    private Cantina cantina;
    private Fornitore rappresentante;

    @FXML
    private JFXTextField tfCantinaID;

    @FXML
    private JFXTextField tfCantinaNome;

    @FXML
    private JFXTextField tfCantinaStato;

    @FXML
    private JFXTextField tfCantinaRegione;

    @FXML
    private JFXTextField tfCantinaVia;

    @FXML
    private JFXTextField tfCantinaUvaggio;

    @FXML
    private JFXComboBox<String> cmbRappresentanteNome;

    @FXML
    private JFXTextField tfRappresentanteID;

    @FXML
    private JFXTextField tfRappresentanteQtaMin;

    @FXML
    private JFXTextField tfRappresentanteQtaMax;

    @FXML
    private JFXTextField tfRappresentanteTelefono;

    @FXML
    private JFXTextField tfRappresentanteMail;

    @FXML
    private JFXButton btnElimina;

    @FXML
    private JFXButton btnSalva;

    public void showStage() {
        thisStage.showAndWait();
    }

    public InfoCantina(Ricerca ricerca) {
        this.ricerca = ricerca;

        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infoCantina.fxml"));

            // IMPOSTO LA CLASSE ATTUALE COME CONTROLLER
            loader.setController(this);

            // CARICA LA SCENA
            thisStage.setScene(new Scene(loader.load()));

            // INIZIALIZZO LO STAGE
            thisStage.setTitle("Info Cantina");


            //IMPOSTO IL DATO DA PRENDERE IN CONSIDERARIONE
            // CARICO TUTTI I DATI RELATIVI ALLA CANTINA
            WrapperCantina cur = ricerca.getWrapperCantina();

            cantina = cur.cantina;
            tfCantinaID.setText(String.valueOf(cantina.getID()));
            tfCantinaNome.setText(cantina.getNome());
            tfCantinaStato.setText(cantina.getStato());
            tfCantinaRegione.setText(cantina.getRegione());
            tfCantinaVia.setText(cantina.getVia());
            tfCantinaUvaggio.setText(cantina.getUvaggio());

            rappresentante = cur.rappresentante;
            tfRappresentanteMail.setText(rappresentante.getMail());
            tfRappresentanteID.setText(String.valueOf(rappresentante.getID()));
            tfRappresentanteTelefono.setText(rappresentante.getTelefono());
            tfRappresentanteQtaMax.setText(String.valueOf(rappresentante.getQta_max()));
            tfRappresentanteQtaMin.setText(String.valueOf(rappresentante.getQta_min()));
            cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", "", Fornitore.class));
            cmbRappresentanteNome.getSelectionModel().select(rappresentante.getNome());
            //COSA FARE QUANDO DEVO CHIUDERE LO STAGE
            thisStage.setOnCloseRequest((WindowEvent event1) -> {
                closeProcedure();
            });
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //COSA FARE QUANDO DEVO CHIUDERE LO STAGE
    private void closeProcedure() {
        if (modified) {//SE HO MODIFICATO UN VALORE CHIEDO CONFERMA DI SALVATAGGIO
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Modifica");
            alert.setHeaderText("Hai effettuato modifica alla cantina");
            alert.setContentText("Vuoi salvare tali modifiche");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    try {
                        //CONTROLLO CHE IL NUOVO DATO NON ABBIA NOME UGUAUGLE AD UNO GIA PRESENTE NEL DB
                        String[] strings = {};
                        boolean valid = true;
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        APIC a = new APIC("cantina");
                        clausolas.add(new Clausola("nome", "like", cantina.getNome()));
                        if (a.select(strings, clausolas).toList(Cantina.class).size() == 1 && nomeCantinaModified) {
                            valid = false;
                        }
                        if (valid) {
                            cantina.update();
                            System.out.println(cantina.toString());
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
            } else {
                System.out.println("Non salvo");
            }
        }
    }

    @FXML
    private void valueModified(Event e) {
        modified = true;
        try {
            JFXTextField tfCur = (JFXTextField) e.getSource();
            switch (tfCur.getId()) {
                case "tfCantinaNome":
                    nomeCantinaModified = true;
                    cantina.setNome(tfCantinaNome.getText());
                    break;
                case "tfCantinaUvaggio":
                    cantina.setUvaggio(tfCantinaUvaggio.getText());
                    break;
                case "tfCantinaStato":
                    cantina.setStato(tfCantinaStato.getText());
                    break;
                case "tfCantinaRegione":
                    cantina.setRegione(tfCantinaRegione.getText());
                    break;
                case "tfCantinaVia":
                    cantina.setVia(tfCantinaVia.getText());
                    break;
            }
        } catch (Exception e1) {
            Utility.createErrorWindow(e1.getMessage());
        }
    }

    @FXML
    private void changeRappresentante() {
        if (cmbRappresentanteNome.getSelectionModel().getSelectedItem() != rappresentante.getNome()) {
            try {
                modified = true;
                rappresentante.setNome(cmbRappresentanteNome.getSelectionModel().getSelectedItem());
                APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
                clausolas.add(new Clausola("nome", "like", rappresentante.getNome()));
                rappresentante = a.select(strings, clausolas).toList(Fornitore.class).get(0);
                tfRappresentanteMail.setText(rappresentante.getMail());
                tfRappresentanteID.setText(String.valueOf(rappresentante.getID()));
                tfRappresentanteTelefono.setText(rappresentante.getTelefono());
                tfRappresentanteQtaMax.setText(String.valueOf(rappresentante.getQta_max()));
                tfRappresentanteQtaMin.setText(String.valueOf(rappresentante.getQta_min()));
                cantina.setIdrappresentante(rappresentante.getID());
            } catch (Exception e) {
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }

    @FXML
    private void btnPress(Event event) {
        Button btnPressed = (Button) event.getSource();
        switch (btnPressed.getId()) {
            case "btnElimina":
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma Eliminazione");
                alert.setHeaderText("Stai eliminando la cantina \"" + cantina.getNome() + "\", cosi fancendo eliminerai anche tutti i vini ad essa associati!");
                alert.setContentText("Vuoi davvero eliminare la cantina?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        clausolas.add(new Clausola("idCantina", "=", cantina.getID().toString()));
                        ricerca.eliminaVini(clausolas);
                        cantina.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    thisStage.close();
                } else {
                }
                break;
            case "btnSalva":
                closeProcedure();
                modified = false;
                break;
        }
    }

}
