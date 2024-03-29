package Client.controller;
import API.APIC;
import Client.Main2;
import ClientUtils.Clausola;
import Models.Vino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Inventario{

    private List<Vino> list;
    private  Vino vino;

    private boolean startup=true;

    @FXML
    private JFXTextField tfCantina;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXTextField tfTipo;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXTextField tfQta;

    //Tabella per errori
    @FXML
    private TableView tblErrore;

    @FXML
    private Label lblTesto;

    @FXML
    private TableColumn<Vino,String> tblColumnNome, tblColumnTipo;
    @FXML
    private TableColumn<Vino,Integer> tblColumnAnnata, tblColumnCantina, tblColumnQtaIns, tblColumnQtaDb;

    @FXML
    private JFXButton btnUpdate;


    @FXML
    public void checkInventario() {
        try {

            ObservableList<Vino> errors = FXCollections.observableArrayList();//CREO UN OBSERVABLE LIST PER I VINI CON QUANTITA SBAGLIATA
            APIC a = new APIC("vino");//CARICO TUTTI I VINI CHE HO NEL DB E LI SALVO IN UNA LISTITERATOR
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
            list = a.select(strings, clausolas).toList(Vino.class);
            ListIterator<Vino> listIterator = list.listIterator();
            vino = listIterator.next();
            btnNext.setText("Next");
            tfNome.setText(vino.getNome());
            tfTipo.setText(vino.getTipo());
            tfAnnata.setText(String.valueOf(vino.getAnno()));
            tfCantina.setText(String.valueOf(vino.getIdCantina()));
            btnNext.setOnAction(event -> {//OGNI VOLTA CHE PREMO NEXT MI CONTROLLA LA QUANTITA E VADO AVANTI FINO ALLA FINE
                try {
                    if (vino.getQta() == Integer.valueOf(tfQta.getText())) {
                        Utility.createSuccessWindow("Corretto");
                    } else {
                        Utility.createErrorWindow("Errore, doveva essere: " + vino.getQta().toString());
                        vino.setIdFornitore(Integer.valueOf(tfQta.getText()));
                        errors.add(vino);
                    }
                } catch (NumberFormatException nfe) {
                    Utility.createErrorWindow("Il campo qta deve essere un numero");
                    return;
                }
                if (listIterator.hasNext()) {
                    vino = listIterator.next();
                    tfNome.setText(vino.getNome());
                    tfTipo.setText(vino.getTipo());
                    tfAnnata.setText(String.valueOf(vino.getAnno()));
                    tfCantina.setText(String.valueOf(vino.getIdCantina()));
                    tfQta.setText("");
                } else {
                    Utility.createWarningWindow("Sono finiti i vini da controllare");
                    try {//UNA VOLTA FINITO FACCIO VEDERE SE CI SONO DEI VINI CON QUANTITA SBAGLIATA E CHIEDO DI AGGIORNARE IL DB
                        if (!errors.isEmpty()) {
                            //MODIFICO LA SCENE
                            tfQta.setVisible(false);
                            tfCantina.setVisible(false);
                            tfAnnata.setVisible(false);
                            tfTipo.setVisible(false);
                            tfNome.setVisible(false);
                            btnNext.setVisible(false);


                            btnUpdate.setVisible(true);
                            tblErrore.setVisible(true);
                            lblTesto.setVisible(true);

                            tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                            tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<>("anno"));
                            tblColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                            tblColumnCantina.setCellValueFactory(new PropertyValueFactory<>("idCantina"));
                            tblColumnQtaDb.setCellValueFactory(new PropertyValueFactory<>("qta"));
                            tblColumnQtaIns.setCellValueFactory(new PropertyValueFactory<>("idFornitore"));

                            tblErrore.setItems(errors);

                            btnUpdate.setOnAction(event1 -> {//DECIDO DI FARE L'UPDATE DELLE QTA
                                APIC update = new APIC("vino");
                                for(Vino cur: errors){
                                    try {
                                        ArrayList<Clausola> clausolasUpdate = new ArrayList<>();
                                        clausolasUpdate.add(new Clausola("nome","like",cur.getNome()));
                                        Vino vinoToUpdate=update.select(strings,clausolasUpdate).toList(Vino.class).get(0);
                                        //System.out.println(vinoToUpdate.toString());
                                        vinoToUpdate.setQta(cur.getIdFornitore());
                                        vinoToUpdate.update();
                                        cur.setQta(cur.getIdFornitore());
                                    } catch (Exception e) {
                                        Utility.createErrorWindow(e.getMessage());
                                    }
                                }
                                Utility.createSuccessWindow("Database aggiornato con successo");

                                tblErrore.setItems(errors);
                                tblErrore.refresh();
                            });


                        }
                    }catch (Exception e){
                        Utility.createErrorWindow(e.getMessage());
                    }
                    return;
                }
            });
        }catch (Exception e){
            Utility.createWarningWindow(e.getMessage());
        }
    }
}
