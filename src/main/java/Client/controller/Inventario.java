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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.rmi.CORBA.Util;
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
    private TableColumn<Vino,String> tblColumnNome, tblColumnTipo;
    @FXML
    private TableColumn<Vino,Integer> tblColumnAnnata, tblColumnCantina, tblColumnQtaIns, tblColumnQtaDb;

    public Inventario(){
    }

    public void load(ArrayList<Vino> list){
        this.list=list;
//          this.vino=vino;
//          tfQta.getId();
//        for(Vino vino: this.list){
//            System.out.println((vino.toString()));
//        }
//        System.out.println(this.list.get(0).getNome());
//        System.out.println("tutto ok");
//            setFirst(vino);
//        for(Vino vino: list){
//            System.out.println(vino.toString());
//        }
    }

//    private void setFirst(Vino vino){
//        tfNome.setText(vino.getNome());
//        tfAnnata.setText(String.valueOf(vino.getAnno()));
//        tfTipo.setText(vino.getTipo());
//        tfCantina.setText(String.valueOf(vino.getIdCantina()));
//    }

    @FXML
    public void checkInventario() {
        try {
            ObservableList<Vino> errors = FXCollections.observableArrayList();
            APIC a = new APIC("vino");
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
            btnNext.setOnAction(event -> {
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
                    try {
                        if (!errors.isEmpty()) {

                            tfQta.setVisible(false);
                            tfCantina.setVisible(false);
                            tfAnnata.setVisible(false);
                            tfTipo.setVisible(false);
                            tfNome.setVisible(false);
                            btnNext.setVisible(false);

                            tblErrore.setVisible(true);

                            tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                            tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<>("anno"));
                            tblColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                            tblColumnCantina.setCellValueFactory(new PropertyValueFactory<>("idCantina"));
                            tblColumnQtaDb.setCellValueFactory(new PropertyValueFactory<>("qta"));
                            tblColumnQtaIns.setCellValueFactory(new PropertyValueFactory<>("idFornitore"));

                            tblErrore.setItems(errors);
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
    }/*
        if (start) {
            if (tfQta.getText().isEmpty()) {
                Utility.createErrorWindow("Inserisci un numero valido");
            } else {
                String qtaString = tfQta.getText();
                try {
                    Integer qta = Integer.valueOf(qtaString);
                    if (qta < 0)
                        Utility.createErrorWindow("Inserire numero positivo");
                    else {
                        if (qta != vino.getQta())
                            tfQta.setText(null);
                    }
                } catch (Exception stringInput) {
                    Utility.createErrorWindow("Inserire un numero");
                }
            }
        }else{
            start=true;
        }*/
}
