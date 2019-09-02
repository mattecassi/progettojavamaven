package Client.controller;

import API.APIC;
import Client.ListaVini;
//import Client.Vino;

import ClientUtils.Clausola;
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
            tblColumnNome.setCellValueFactory(new PropertyValueFactory<Vino, String>("nome"));
            tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<Vino, String>("anno"));
            tblColumnCantina.setCellValueFactory(new PropertyValueFactory<Vino, String>("idCantina"));
            tblColumnTipo.setCellValueFactory(new PropertyValueFactory<Vino, String>("tipo"));
            tblColumnQta.setCellValueFactory(new PropertyValueFactory<Vino, String>("qta"));
            tblColumnUvaggio.setCellValueFactory(new PropertyValueFactory<Vino, String>("uvaggio"));
            tblColumnRegione.setCellValueFactory(new PropertyValueFactory<Vino, String>("regione"));
            tblColumnStato.setCellValueFactory(new PropertyValueFactory<Vino, String>("stato"));
            tblColonnaFornitore.setCellValueFactory(new PropertyValueFactory<Vino, String>("fornitore"));

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

    @FXML
    void searchElement(Event event){
        JFXTextField tfAttivo = (JFXTextField)event.getSource();
        tfAttivo.getText();
    }

}
