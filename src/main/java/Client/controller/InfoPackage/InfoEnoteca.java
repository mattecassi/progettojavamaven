package Client.controller.InfoPackage;

import API.APIC;
import Client.controller.Ricerca;
import ClientUtils.Clausola;
import Models.Cantina;
import Models.Enoteca;
import Models.Fornitore;
import Models.Rappresentante;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
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

public class InfoEnoteca {

    private Stage thisStage;
    private final Ricerca ricerca;

    private boolean modified = false, nomeEnotecaModified = false;
    private Enoteca enoteca;
    private Fornitore fornitore;

    @FXML
    private JFXTextField tfID;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXTextField tfQtaMin;

    @FXML
    private JFXTextField tfQtaMax;

    @FXML
    private JFXTextField tfMail;

    @FXML
    private JFXTextField tfTelefono;

    @FXML
    private JFXTextField tfStato;

    @FXML
    private JFXTextField tfRegione;

    @FXML
    private JFXTextField tfCitta;

    @FXML
    private JFXTextField tfVia;

    @FXML
    private JFXButton btnElimina;

    @FXML
    private JFXButton btnSalva;

    public void showStage() {
        thisStage.showAndWait();
    }

    public InfoEnoteca(Ricerca ricerca){
        this.ricerca = ricerca;

        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infoEnoteca.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Info Enoteca");



            //imposto i dati nei rispettivi componenents
            fornitore = ricerca.getWrapperEnoteca().fornitore;
            tfID.setText(String.valueOf(fornitore.getID()));
            tfQtaMax.setText(String.valueOf(fornitore.getQta_max()));
            tfQtaMin.setText(String.valueOf(fornitore.getQta_min()));
            tfTelefono.setText(fornitore.getTelefono());
            tfMail.setText(fornitore.getMail());
            tfNome.setText(fornitore.getNome());

            enoteca = ricerca.getWrapperEnoteca().enoteca;
            tfStato.setText(enoteca.getStato());
            tfRegione.setText(enoteca.getRegione());
            tfCitta.setText(enoteca.getCitta());
            tfVia.setText(enoteca.getVia());
            //cosa fare in caso di chisuura
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
        if (modified) {//se ho modificato qualcosa chiedo
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Modifica");
            alert.setHeaderText("Hai effettuato modifica all'enoteca");
            alert.setContentText("Vuoi salvare tali modifiche");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    try {
                        String[] strings = {};
                        boolean valid = true;
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        APIC a = new APIC(Fornitore.getTableFornitoriEnoteche());
                        clausolas.add(new Clausola("nome", "like", fornitore.getNome()));
                        if (a.select(strings, clausolas).toList(Fornitore.class).size() == 1 && nomeEnotecaModified) {
                            valid = false;
                        }
                        if (valid) {
                            System.out.println(fornitore.toString());
                            System.out.println(enoteca.toString());
                            fornitore.update();
                            enoteca.update();


                        } else {
                            Utility.createErrorWindow("Impossibile completare a causa di un'altra enoteca con lo stesso nome");
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
    private void valueModified(Event e){//in base a cosa modifico, acquisisco il nuovo valore
        modified = true;
        try {
            JFXTextField tfCur = (JFXTextField) e.getSource();
            switch (tfCur.getId()) {
                case "tfNome":
                    nomeEnotecaModified=true;
                    fornitore.setNome(tfNome.getText());
                    break;
                case "tfQtaMin":
                    fornitore.setQta_min(Integer.valueOf(tfQtaMin.getText()));
                    break;
                case "tfQtaMax":
                    fornitore.setQta_max(Integer.valueOf(tfQtaMax.getText()));
                    break;
                case "tfMail":
                    fornitore.setMail(tfMail.getText());
                    break;
                case "tfTelefono":
                    fornitore.setTelefono(tfTelefono.getText());
                    break;
                case "tfStato":
                    enoteca.setStato(tfStato.getText());
                    break;
                case "tfRegione":
                    enoteca.setRegione(tfRegione.getText());
                    break;
                case "tfCitta":
                    enoteca.setCitta(tfCitta.getText());
                    break;
                case "tfVia":
                    enoteca.setVia(tfVia.getText());
                    break;
            }
        }catch (Exception e1){
        }
    }

    @FXML
    private void btnPress(Event event){
        Button btnPressed = (Button)event.getSource();
        switch (btnPressed.getId()){
            case "btnElimina":
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma Eliminazione");
                alert.setHeaderText("Stai eliminadno l'enoteca \""+ fornitore.getNome()+"\", cosi facendo eliminerai anche i vini ad essa associati");
                alert.setContentText("Vuoi davvero eliminare l'enoteca?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        //in caso positivo inizio la ricerca dei vini associati
                        APIC aEnoteca = new APIC("enoteca");
                        APIC aFornitore = new APIC("fornitore");
                        String[] strings ={};
                        ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
                        ArrayList<Clausola> clausolasVino= new ArrayList<>();

                        enoteca.delete();

                        clausolasFornitore.add(new Clausola("ID", "=", fornitore.getID().toString()));
                        for(Fornitore cur: aFornitore.select(strings,clausolasFornitore).toList(Fornitore.class)){//collego i vini all'id dell'enoteca e li elimino
                            clausolasVino.add(new Clausola("idFornitore","=", cur.getID().toString()));
                            ricerca.eliminaVini(clausolasVino);
                            clausolasVino.remove(0);
                        }

                        //infine elimino l'enoteca e la sua controparte fornitore
                        fornitore.delete();

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
