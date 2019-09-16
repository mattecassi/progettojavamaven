package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.*;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sun.nio.cs.ArrayEncoder;

import java.io.IOException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Optional;

public class InfoRappresentante {

    private Stage thisStage;
    private final Ricerca ricerca;
    private String tipo;

    private boolean modified = false, nomeRappresentanteModified = false;
    private Fornitore rappresentante;

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
    private JFXButton btnElimina;

    @FXML
    private JFXButton btnSalva;

    public void showStage() {
        thisStage.setResizable(false);
        thisStage.showAndWait();
    }


    public InfoRappresentante(Ricerca ricerca, String tipo) {
        this.ricerca = ricerca;
        this.tipo = tipo;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/infoRappresentante.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            if (tipo.equals("rappresentante")) {
                thisStage.setTitle("Info Rappresentante");
                rappresentante = ricerca.getRappresentante();
            } else {
                thisStage.setTitle("Info Fornitore");
                rappresentante = ricerca.getFornitore();
            }

            tfID.setText(String.valueOf(rappresentante.getID()));
            tfQtaMax.setText(String.valueOf(rappresentante.getQta_max()));
            tfQtaMin.setText(String.valueOf(rappresentante.getQta_min()));
            tfTelefono.setText(rappresentante.getTelefono());
            tfMail.setText(rappresentante.getMail());
            tfNome.setText(rappresentante.getNome());

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

    private void closeProcedure() {
        if (modified) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Modifica");
            if (tipo.equals("rappresentante"))
                alert.setHeaderText("Hai effettuato modifica al rappresentante");
            else
                alert.setHeaderText("Hai effettuato modifica al fornitore");
            alert.setContentText("Vuoi salvare tali modifiche");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    try {
                        String[] strings = {};
                        boolean valid = true;
                        ArrayList<Clausola> clausolas = new ArrayList<>();
                        APIC a;
                        switch (tipo) {
                            case "rappresentante":
                                a = new APIC(Fornitore.getTableFornitoriRappresentanti());
                                break;
                            default:
                                a = new APIC("fornitore");


                        }
                        clausolas.add(new Clausola("nome", "like", rappresentante.getNome()));
                        if (a.select(strings, clausolas).toList(Fornitore.class).size() == 1 && nomeRappresentanteModified) {
                            valid = false;
                        }
                        if (valid) {
                            rappresentante.update();
                            System.out.println(rappresentante.toString());
                        } else {
                            Utility.createErrorWindow("Impossibile completare a causa di un altro rappresentante con lo stesso nome");
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
    private void valueModified(Event e) {
        modified = true;
        Integer integer;
        try {
            JFXTextField tfCur = (JFXTextField) e.getSource();
            switch (tfCur.getId()) {
                case "tfNome":
                    nomeRappresentanteModified = true;
                    rappresentante.setNome(tfNome.getText());
                    break;
                case "tfQtaMin":
                    integer = Integer.valueOf(tfQtaMin.getText());
                    rappresentante.setQta_min(integer);
                    break;
                case "tfQtaMax":
                    integer = Integer.valueOf(tfQtaMax.getText());
                    rappresentante.setQta_max(integer);
                    break;
                case "tfMail":
                    rappresentante.setMail(tfMail.getText());
                    break;
                case "tfTelefono":
                    rappresentante.setTelefono(tfTelefono.getText());
                    break;
            }
        } catch (Exception e1) {
        }
    }

    @FXML
    private void btnPress(Event event) {
        Button btnPressed = (Button) event.getSource();
        switch (btnPressed.getId()) {
            case "btnElimina":
                APIC aEnoteca = new APIC("enoteca");
                String[] stringsEnoteca = {};
                ArrayList<Clausola> clausolasEnoteca = new ArrayList<>();
                clausolasEnoteca.add(new Clausola("ID", "=", rappresentante.getID().toString()));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma Eliminazione");
                alert.setHeaderText("Stai eliminando il rappresentante \"" + rappresentante.getNome() + "\", cosi fancendo eliminerai anche tutte le cantine associate (e i relativi vini!!)!");
                alert.setContentText("Vuoi davvero eliminare la cantina?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        if (aEnoteca.select(stringsEnoteca,clausolasEnoteca).toList(Fornitore.class).isEmpty()) {
                            APIC a = new APIC("cantina");
                            String[] strings = {};
                            ArrayList<Clausola> clausolasCantina = new ArrayList<>();
                            ArrayList<Clausola> clausolasVino = new ArrayList<>();
                            clausolasCantina.add(new Clausola("idRappresentante", "=", rappresentante.getID().toString()));
                            for (Cantina cur : a.select(strings, clausolasCantina).toList(Cantina.class)) {
                                clausolasVino.add(new Clausola("idCantina", "=", cur.getID().toString()));
                                ricerca.eliminaVini(clausolasVino);
                                clausolasVino.remove(0);
                                cur.delete();
                            }
                            clausolasVino.add(new Clausola("idFornitore", "=", rappresentante.getID().toString()));
                            ricerca.eliminaVini(clausolasVino);
                            APIC aRappre = new APIC("rappresentante");
                            ArrayList<Clausola> clausolasRappresentante = new ArrayList<>();
                            clausolasRappresentante.add(new Clausola("id", "=", rappresentante.getID().toString()));
                            aRappre.select(strings, clausolasRappresentante).toList(Rappresentante.class).get(0).delete();
                            rappresentante.delete();
                        }else{
                            APIC aFornitore = new APIC("fornitore");
                            String[] strings = {};
                            ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
                            ArrayList<Clausola> clausolasVino = new ArrayList<>();

                            clausolasFornitore.add(new Clausola("ID","=",rappresentante.getID().toString()));
                            WrapperEnoteca wrapperEnoteca = new WrapperEnoteca(rappresentante);


                            Enoteca enoteca=wrapperEnoteca.enoteca;
                            enoteca.delete();

                            for (Fornitore cur : aFornitore.select(strings, clausolasFornitore).toList(Fornitore.class)) {
                                clausolasVino.add(new Clausola("idFornitore", "=", cur.getID().toString()));
                                ricerca.eliminaVini(clausolasVino);
                                clausolasVino.remove(0);
                                cur.delete();
                            }

                            rappresentante.delete();

                        }
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
