package Client.controller;

import API.APIC;
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
    private JFXComboBox<Integer> cmbRappresentanteID;

    @FXML
    private JFXTextField tfRappresentanteNome;

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
        thisStage.setResizable(false);
        thisStage.showAndWait();
    }

    public InfoCantina(Ricerca ricerca){
    this.ricerca = ricerca;

    thisStage = new Stage();
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infoCantina.fxml"));

        // Set this class as the controller
        loader.setController(this);

        // Load the scene
        thisStage.setScene(new Scene(loader.load()));

        // Setup the window/stage
        thisStage.setTitle("Info Cantina");

        WrapperCantina cur = ricerca.getWrapperCantina();

        cantina = cur.cantina;
        tfCantinaID.setText(String.valueOf(cantina.getID()));
        tfCantinaNome.setText(cantina.getNome());
        tfCantinaStato.setText(cantina.getStato());
        tfCantinaRegione.setText(cantina.getRegione());
        tfCantinaVia.setText(cantina.getVia());
        tfCantinaUvaggio.setText(cantina.getUvaggio());

        rappresentante=cur.rappresentante;
        tfRappresentanteMail.setText(rappresentante.getMail());
        tfRappresentanteNome.setText(rappresentante.getNome());
        tfRappresentanteTelefono.setText(rappresentante.getTelefono());
        tfRappresentanteQtaMax.setText(String.valueOf(rappresentante.getQta_max()));
        tfRappresentanteQtaMin.setText(String.valueOf(rappresentante.getQta_min()));
       // cmbRappresentanteID.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(),"ID","", Fornitore.class));
            //

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

    private void closeProcedure(){
        if (modified) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Modifica");
            alert.setHeaderText("Hai effettuato modifica alla cantina");
            alert.setContentText("Vuoi salvare tali modifiche");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    try {
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
//                try {
//                    this.ricerca.loadAllTbl();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {
                System.out.println("Non salvo");
            }
        }
    }

    @FXML
    private void valueModified(Event e){
        modified = true;
        try {
            JFXTextField tfCur = (JFXTextField) e.getSource();
            switch (tfCur.getId()) {
                case "tfCantinaNome":
                    nomeCantinaModified=true;
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
        }catch (Exception e1){
            Utility.createErrorWindow(e1.getMessage());
        }
    }

    @FXML
    private void changeRappresentante(){
        if(cmbRappresentanteID.getSelectionModel().getSelectedItem()!=rappresentante.getID()){
            try {
                modified = true;
                rappresentante.setID(cmbRappresentanteID.getSelectionModel().getSelectedIndex());
                APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
                clausolas.add(new Clausola("id", "=", rappresentante.getID().toString()));
                rappresentante = a.select(strings, clausolas).toList(Fornitore.class).get(0);
                tfRappresentanteMail.setText(rappresentante.getMail());
                tfRappresentanteNome.setText(rappresentante.getNome());
                tfRappresentanteTelefono.setText(rappresentante.getTelefono());
                tfRappresentanteQtaMax.setText(String.valueOf(rappresentante.getQta_max()));
                tfRappresentanteQtaMin.setText(String.valueOf(rappresentante.getQta_min()));
            }catch (Exception e){
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }

    @FXML
    private void btnPress(Event event){
        Button btnPressed = (Button)event.getSource();
        switch (btnPressed.getId()){
            case "btnElimina":
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma Eliminazione");
                alert.setHeaderText("Stai eliminando la cantina \""+cantina.getNome() +"\", cosi fancendo eliminerai anche tutti i vini ad essa associati!");
                alert.setContentText("Vuoi davvero eliminare la cantina?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        clausolas.add(new Clausola("idCantina","=", cantina.getID().toString()));
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
                modified=false;
                break;
        }
    }

}
