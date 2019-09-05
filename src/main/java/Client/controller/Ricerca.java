package Client.controller;

import API.APIC;
import Client.ListaVini;
//import Client.Vino;

import ClientUtils.Clausola;
import Models.Fornitore;
import Models.Vino;
import Utils.APIReturn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    private TableView tblviewLista;

    @FXML
    private TableColumn<Vino, String> tblColumnNome, tblColumnTipo, tblColumnAnnata, tblColumnCantina, tblColumnUvaggio, tblColumnStato, tblColumnRegione, tblColumnQta, tblColonnaFornitore;

    @FXML
    private AnchorPane apRicerca;

    @FXML
    private ComboBox comboBox;


    @FXML
    public void openComboBox(){
        /*System.out.println("ok");
        Vino bottiglia = new Vino("Verdicchio", "Bianco", "Bizzarri", "Boh","Uva", "Italia","Emilia Romagna", 10, 1998);
        ObservableList<Vino> list = FXCollections.observableArrayList();
        comboBox.setItems(list);
        comboBox.setCellFactory(new PropertyValueFactory<Vino,String>("Nome"));*/
    }

    private void loadTbl(){
        tblColumnNome.setCellValueFactory(new PropertyValueFactory<Vino, String>("nome"));
        tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<Vino, String>("anno"));
        tblColumnCantina.setCellValueFactory(new PropertyValueFactory<Vino, String>("idCantina"));
        tblColumnTipo.setCellValueFactory(new PropertyValueFactory<Vino, String>("tipo"));
        tblColumnQta.setCellValueFactory(new PropertyValueFactory<Vino, String>("qta"));
        tblColumnUvaggio.setCellValueFactory(new PropertyValueFactory<Vino, String>("idCantina"));
        tblColumnRegione.setCellValueFactory(new PropertyValueFactory<Vino, String>("costo"));
        tblColumnStato.setCellValueFactory(new PropertyValueFactory<Vino, String>("prezzoVendita"));
        //if(new PropertyValueFactory<Vino, String>("idFornitore") == new PropertyValueFactory<Fornitore, String>("idFornitore"))
        tblColonnaFornitore.setCellValueFactory(new PropertyValueFactory<Vino, String>("idFornitore"));
    }

    @FXML
    public void loadElement(Event event) throws Exception {
        if(!tblLoaded) {
            System.out.println("OK");
            tblLoaded=true;
            Vino bottiglia;
            /*BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ListaVini listaVini = new ListaVini();
            bottiglia = new Vino("Verdicchio","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
            listaVini.addVino(bottiglia);
            bottiglia = new Vino("Ciao","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
            listaVini.addVino(bottiglia);
            bottiglia = new Vino("Come","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
            listaVini.addVino(bottiglia);
            ObservableList<Vino> list = listaVini.getList();
            comboBox.setItems(list);*/
            loadTbl();

            APIC a = new APIC("vino");
            APIReturn ret;

            String[] colonne = {};
            List<Clausola> clausolas = new ArrayList<Clausola>();
            /*clausolas.add(new Clausola("ID","<","20"));
            clausolas.add(new Clausola("ID","IS NOT","NULL"));
           */
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
}
