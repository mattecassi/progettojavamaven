package Client.controller;

import API.API;
import API.APIC;
import Client.ListaVini;
//import Client.Vino;

import Client.Main2;
import ClientUtils.Clausola;
import Models.Fornitore;
import Models.Vino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Ricerca {
    private String[] values = new String[9];
    final private String[] campi = {"nome","anno","idCantina","tipo","idFornitore","uvaggio","stato","regione","qta"};
    private boolean tblLoaded = false;
    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXTextField tfCantina;

    @FXML
    private JFXTextField tfTipo;

    @FXML
    private JFXTextField tfUvaggio;

    @FXML
    private JFXTextField tfStato;

    @FXML
    private JFXTextField tfQta;

    @FXML
    private JFXTextField tfRegione;

    @FXML
    private  JFXTextField tfAggiungiQuantitaVino;

    @FXML
    private TableView tblviewLista;

    @FXML
    private TableColumn<Vino, String> tblColumnNome, tblColumnTipo, tblColumnAnnata, tblColumnCantina, tblColumnUvaggio, tblColumnStato, tblColumnRegione, tblColumnQta, tblColonnaFornitore;

    @FXML
    private AnchorPane apRicerca;

    @FXML
    private ContextMenu cMenu;

    @FXML
    private ComboBox<String> cmbVinoNome;


    private void loadTbl(){
        tblColumnNome.setCellValueFactory(new PropertyValueFactory<Vino, String>("nome"));
        tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<Vino, String>("anno"));
        tblColumnTipo.setCellValueFactory(new PropertyValueFactory<Vino, String>("tipo"));
        tblColumnQta.setCellValueFactory(new PropertyValueFactory<Vino, String>("qta"));
        tblColumnRegione.setCellValueFactory(new PropertyValueFactory<Vino, String>("costo"));
        tblColumnStato.setCellValueFactory(new PropertyValueFactory<Vino, String>("prezzoVendita"));


        tblColumnUvaggio.setCellValueFactory(new PropertyValueFactory<Vino, String>("idCantina"));
        tblColumnCantina.setCellValueFactory(new PropertyValueFactory<Vino, String>("idCantina"));
        tblColonnaFornitore.setCellValueFactory(new PropertyValueFactory<Vino, String>("idFornitore"));
    }

    @FXML
    public void loadElement(Event event) throws Exception {
        if(!tblLoaded) {
            System.out.println("OK");
            tblLoaded=true;
            loadTbl();
            APIC a = new APIC("vino");
            APIReturn ret;
            String[] colonne = {};
            List<Clausola> clausolas = new ArrayList<Clausola>();
            ret = a.select(colonne,clausolas);
            tblviewLista.setItems(ret.toObservableList(Vino.class));
        }
        event.consume();
    }

/*
    void test(){
        try{

            APIC a = new APIC("vino");
            APIReturn ret;
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
            clausolas.add(new Clausola("nome","=", "Bianco Bizzo"));
            ret=a.select(strings,clausolas);
            tblviewLista.setItems(ret.toObservableList(Vino.class));
        }
        catch (Exception e){

        }
    }*/

    @FXML
    void searchElement(Event event) {
        JFXTextField tfAttivo = (JFXTextField) event.getSource();
        switch (tfAttivo.getId()){
            case "tfNome":
                values[0]=tfAttivo.getText();
                break;
            case "tfAnnata":
                values[1]=tfAttivo.getText();
                break;
            case "tfCantina":
                values[2]=tfAttivo.getText();
                break;
            case "tfTipo":
                values[3]=tfAttivo.getText();
                break;
            case "tfFornitore":
                values[4]=tfAttivo.getText();
                break;
            case "tfUvaggio":
                values[5]=tfAttivo.getText();
                break;
            case "tfStato":
                values[6]=tfAttivo.getText();
                break;
            case "tfRegione":
                values[7]=tfAttivo.getText();
                break;
            case "tfQta":
                values[8]=tfAttivo.getText();
                break;
        }
        try {
            loadTbl();
            APIC a = new APIC("vino");
            APIReturn ret;
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
            for (int i=0;i<9;i++) {
                if (values[i] != null){
                    clausolas.add(new Clausola(campi[i], "like", "%"+values[i]+"%"));
                System.out.println(campi[i] + " " + values[i]);}
            }
            ret = a.select(strings, clausolas);
            tblviewLista.setItems(ret.toObservableList(Vino.class));
        } catch (Exception e) {

        }
    }


    @FXML
    private void searchCmb(){
        APIC a = new APIC("vino");
        APIReturn ret;
        String[] strings = {};
        ArrayList<Clausola> clausolas = new ArrayList<>();
        try {
            loadTbl();
            clausolas.add(new Clausola(campi[0], "like", "%"+cmbVinoNome.getSelectionModel().getSelectedItem()+"%"));
            strings[0] = "nome";
            //mbVinoNome.setItems(a.select(strings, clausolas).toObservableList(Vino.class));
            cmbVinoNome.show();
            ret = a.select(strings, clausolas);
            tblviewLista.setItems(ret.toObservableList(Vino.class));
        } catch (Exception e) {

        }

    }


    @FXML
    private void openContext(Event event){
        Stage dialog = new Stage();
        AnchorPane popUpPane;
        Vino cur = (Vino)tblviewLista.getSelectionModel().getSelectedItem();
        MenuItem option = (MenuItem)event.getTarget();
        Main2 main2 = new Main2();
        try {
            switch (option.getId()) {
                case "miInfo":
                    break;
                case "miAggiungi":
                    popUpPane = FXMLLoader.load(getClass().getResource("/view/aggiungiVino.fxml"));
                    dialog.setScene(new Scene(popUpPane));
                    dialog.showAndWait();
                    System.out.println(tfAggiungiQuantitaVino.getText());
                    break;
                case "miRimuovi":
                    break;
                case "miElimina":
                    break;
            }
        }catch (Exception e){
           e.printStackTrace();
        }
    }
}
