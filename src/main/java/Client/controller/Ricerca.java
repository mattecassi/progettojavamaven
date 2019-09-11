package Client.controller;

import API.API;
import API.APIC;
import Client.ListaVini;
//import Client.Vino;

import Client.Main2;
import ClientUtils.Clausola;
import Models.Cantina;
import Models.Fornitore;
import Models.Rappresentante;
import Models.Vino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.beans.value.ObservableSetValue;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {
    private String[] values = new String[9];
    final private String[] campi = {"nome","anno","idCantina","tipo","idFornitore","uvaggio","stato","regione","qta"};
    private boolean tblLoaded = false, allLoaded = false;
    @FXML
    private AnchorPane apRicerca;

    @FXML
    private Tab tabVino;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXTextField tfCantina;

    @FXML
    private JFXTextField tfTipo;

    @FXML
    private JFXTextField tfUvaggio;

    @FXML
    private JFXTextField tfFornitore;

    @FXML
    private JFXTextField tfStato;

    @FXML
    private JFXTextField tfRegione;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXTextField tfQta;

    @FXML
    private JFXComboBox<String> cmbVinoNome;

    @FXML
    private TableView tblViewListaVino;

    @FXML
    private TableColumn<WrapperVino,String> tblColumnNome, tblColumnTipo,  tblColumnCantina, tblColumnUvaggio, tblColumnStato, tblColumnRegione,  tblColonnaFornitore;

    @FXML
    private TableColumn<Vino,Integer> tblColumnAnnata, tblColumnQta;

    @FXML
    private Tab tabCantina;

    @FXML
    private TableView tblViewListaCantina;

    @FXML
    private  TableColumn<Cantina,String> tblColumnCantinaId, tblColumnCantinaNome, tblColumnCantinaStato, tblColumnCantinaRegione, tblColumnCantinaVia, tblColumnCantinaUvaggio, tblColumnCantinaIdRappr;

    @FXML
    private  TableView tblViewListaRappresentate;

    @FXML
    private  TableColumn<Fornitore,String> tblColumnRappresentanteMail, tblColumnRappresentanteNome;

    @FXML
    private  TableColumn<Fornitore,Integer> tblColumnRappresentanteMin, tblColumnRappresentanteMax, tblColumnRappresentanteTelefono;

    @FXML
    private TableColumn<Rappresentante,Integer> tblColumnRappresentanteID;

    @FXML
    private JFXTextField tfCantinaId;

    @FXML
    private JFXTextField tfCantinaIdRappr;

    @FXML
    private JFXTextField tfCantinaUvaggio;

    @FXML
    private JFXTextField tfCantinaStato;

    @FXML
    private JFXTextField tfCantinaNome;

    @FXML
    private JFXTextField tfCantinaVia;

    @FXML
    private JFXTextField tfCantinaRegione;

    public Ricerca(){

    }


    private void loadTblVino(){
        tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<>("anno"));
        tblColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblColumnQta.setCellValueFactory(new PropertyValueFactory<>("qta"));
        tblColumnRegione.setCellValueFactory(new PropertyValueFactory<>("costo"));
        tblColumnStato.setCellValueFactory(new PropertyValueFactory<>("prezzoVendita"));


        tblColumnUvaggio.setCellValueFactory(new PropertyValueFactory<>("idCantina"));
        tblColumnCantina.setCellValueFactory(new PropertyValueFactory<>("idCantina"));
        tblColonnaFornitore.setCellValueFactory(new PropertyValueFactory<>("idFornitore"));
    }

    private void loadTblCantina(){
        tblColumnCantinaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnCantinaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnCantinaRegione.setCellValueFactory(new PropertyValueFactory<>("regione"));
        tblColumnCantinaStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        tblColumnCantinaVia.setCellValueFactory(new PropertyValueFactory<>("via"));
        tblColumnCantinaUvaggio.setCellValueFactory(new PropertyValueFactory<>("uvaggio"));
        tblColumnCantinaIdRappr.setCellValueFactory(new PropertyValueFactory<>("idrappresentante"));
    }

    private void loadTblRappresentante(){
        tblColumnRappresentanteID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        //tblColumnRappresentanteMin.setCellValueFactory(new Cal);
//       tblColumnRappresentanteMax.setCellValueFactory();
//        tblColumnRappresentanteTelefono.setCellValueFactory();
//        tblColumnRappresentanteMail.setCellValueFactory();
//        tblColumnRappresentanteNome.setCellValueFactory();
    }

    @FXML
    public void loadAllTbl() throws Exception{
        if(!allLoaded) {



            APIC a = new APIC("vino");
            String[] colonne = {};
            List<Clausola> clausolas = new ArrayList<Clausola>();

            ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
            for(Vino vino: a.select(colonne,clausolas).toObservableList(Vino.class)){
                wrapperVinos.add(new WrapperVino(vino));
            }

            for(WrapperVino cur: wrapperVinos){
                System.out.println(cur.vino.getNome() +" "+cur.cantina.getNome());
            }

            tblViewListaVino.setItems(a.select(colonne,clausolas).toObservableList(Vino.class));


            APIC b = new APIC("cantina");
            tblViewListaCantina.setItems(b.select(colonne,clausolas).toObservableList(Cantina.class));

            APIC c = new APIC("rappresentante");
            tblViewListaRappresentate.setItems(c.select(colonne,clausolas).toObservableList(Rappresentante.class));


            loadTblVino();

            loadTblCantina();

            //loadTblRappresentante();

            allLoaded=true;
        }
    }


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
            loadTblVino();
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
            tblViewListaVino.setItems(ret.toObservableList(Vino.class));
        } catch (Exception e) {

        }
    }


    @FXML
    private void searchCmb(){
        tblLoaded=true;
        APIC a = new APIC("vino");
        APIReturn ret;
        String[] strings = {};
        ArrayList<Clausola> clausolas = new ArrayList<>();
        try {
            loadTblVino();
            clausolas.add(new Clausola(campi[0], "like", "%"+cmbVinoNome.getSelectionModel().getSelectedItem()+"%"));
            ObservableList<String> nomi = FXCollections.observableArrayList();
            ObservableList<Vino> vinos = a.select(strings,clausolas).toObservableList(Vino.class);

            for(Vino cur: vinos){
                nomi.add(cur.getNome());
            }
            cmbVinoNome.setItems(nomi);
            cmbVinoNome.hide();
            cmbVinoNome.show();

            tblViewListaVino.setItems(vinos);
        } catch (Exception e) {

        }

    }


    @FXML
    private void openContext(Event event){
        Stage dialog = new Stage();
        AnchorPane popUpPane;
        Vino cur = (Vino)tblViewListaVino.getSelectionModel().getSelectedItem();
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
                    //System.out.println(tfAggiungiQuantitaVino.getText());
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
