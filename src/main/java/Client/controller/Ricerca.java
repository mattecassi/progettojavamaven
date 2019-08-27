package Client.controller;

import Client.ListaVini;
import Client.Vino;
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
    public void loadElement(Event event) throws IOException {
        if(!tblLoaded) {
            System.out.println("OK");
            tblLoaded=true;
            Vino bottiglia;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ListaVini listaVini = new ListaVini();
            bottiglia = new Vino("Verdicchio","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
            listaVini.addVino(bottiglia);
            bottiglia = new Vino("Ciao","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
            listaVini.addVino(bottiglia);
            bottiglia = new Vino("Come","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
            listaVini.addVino(bottiglia);
            ObservableList<Vino> list = listaVini.getList();
            comboBox.setItems(list);
            tblColumnNome.setCellValueFactory(new PropertyValueFactory<Vino, String>("nome"));
            tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<Vino, String>("annata"));
            tblColumnCantina.setCellValueFactory(new PropertyValueFactory<Vino, String>("cantina"));
            tblColumnTipo.setCellValueFactory(new PropertyValueFactory<Vino, String>("tipo"));
            tblColumnRegione.setCellValueFactory(new PropertyValueFactory<Vino, String>("regione"));
            tblColumnStato.setCellValueFactory(new PropertyValueFactory<Vino, String>("stato"));
            tblColumnUvaggio.setCellValueFactory(new PropertyValueFactory<Vino, String>("uvaggio"));
            tblColumnQta.setCellValueFactory(new PropertyValueFactory<Vino, String>("qta"));
            tblColonnaFornitore.setCellValueFactory(new PropertyValueFactory<Vino, String>("fornitore"));
            tblviewLista.setItems(list);
        }
        event.consume();
    }

    @FXML
    void searchElement(Event event){
        JFXTextField tfAttivo = (JFXTextField)event.getSource();
        tfAttivo.getText();
    }

}
