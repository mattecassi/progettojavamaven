package Client.controller;

import API.APIC;
//import Client.Vino;

import ClientUtils.Clausola;
import Models.*;
import Utils.Utility;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ricerca {


    private String[] values = new String[9];
    final private String[] campi = {"nome", "anno", "idCantina", "tipo", "idFornitore", "uvaggio", "stato", "regione", "qta"};
    private boolean tblLoaded = false, allLoaded = false;
    @FXML
    private AnchorPane apRicerca;

    @FXML
    private Tab tabVino;

    @FXML
    private ComboBox<String> cmbVinoNome, cmbVinoTipo, cmbVinoCantina, cmbVinoUvaggio, cmbVinoStato, cmbVinoRegione,  cmbVinoFornitore;

    @FXML
    private ComboBox<Integer> cmbVinoQta, cmbVinoAnnata;

    @FXML
    private TableView tblViewListaVino;

    @FXML
    private TableColumn<WrapperVino, String> tblColumnNome, tblColumnTipo, tblColumnCantina, tblColumnUvaggio, tblColumnStato, tblColumnRegione, tblColonnaFornitore;

    @FXML
    private TableColumn<WrapperVino, Integer> tblColumnAnnata, tblColumnQta;

    @FXML
    private Tab tabCantina;

    @FXML
    private TableView tblViewListaCantina;

    @FXML
    private TableColumn<Cantina, String> tblColumnCantinaId, tblColumnCantinaNome, tblColumnCantinaStato, tblColumnCantinaRegione, tblColumnCantinaVia, tblColumnCantinaUvaggio, tblColumnCantinaIdRappr;

    @FXML
    private JFXComboBox<String> cmbCantinaNome, cmbCantinaStato, cmbCantinaRegione, cmbCantinaUvaggio, cmbCantinaVia;

    //FORNITORE

    @FXML
    private TableView tblViewListaFornitore;

    @FXML
    private TableColumn<Fornitore, String> tblColumnFornitoreNome, tblColumnFornitoreMail, tblColumnFornitoreTelefono;

    @FXML
    private TableColumn<Fornitore, Integer> tblColumnFornitoreQtaMin, tblColumnFornitoreQtaMax, tblColumnFornitoreID;

    @FXML
    private ComboBox<String> cmbFornitoreNome, cmbFornitoreMail, cmbFornitoreTelefono;

    @FXML
    private ComboBox<Integer> cmbFornitoreQtaMin, cmbFornitoreQtaMax;


    //ENOTECA

    @FXML
    private TableView<WrapperEnoteca> tblViewListaEnoteca;

    @FXML
    private TableColumn<WrapperEnoteca,String> tblColumnEnotecaNome, tblColumnEnotecaMail, tblColumnEnotecaTelefono, tblColumnEnotecaStato, tblColumnEnotecaRegione, tblColumnEnotecaCitta, tblColumnEnotecaVia;

    @FXML
    private TableColumn<WrapperEnoteca,Integer> tblColumnEnotecaQtaMin, tblColumnEnotecaQtaMax;

    @FXML
    private ComboBox<String> cmbEnotecaNome, cmbEnotecaMail, cmbEnotecaTelefono, cmbEnotecaStato, cmbEnotecaRegione, cmbEnotecaCitta, cmbEnotecaVia;

    @FXML
    private ComboBox<Integer> cmbEnotecaQtaMin, cmbEnotecaQtaMax;

    //RAPPRESENTANTE

    @FXML
    private TableView tblViewListaRappresentate;

    @FXML
    private TableColumn<Fornitore, String> tblColumnRappresentanteMail, tblColumnRappresentanteNome;

    @FXML
    private TableColumn<Fornitore, Integer> tblColumnRappresentanteMin, tblColumnRappresentanteMax, tblColumnRappresentanteTelefono;

    @FXML
    private TableColumn<Rappresentante, Integer> tblColumnRappresentanteID;

    @FXML
    private JFXComboBox<String> cmbRappresentanteNome, cmbRappresentanteMail, cmbRappresentanteTelefono;

    @FXML
    private JFXComboBox<Integer> cmbRappresentanteQtaMin, cmbRappresentanteQtaMax;

    @FXML
    private TableView tblViewListaTipo;

    @FXML
    private TableColumn<TipoVino, String> tblColumnTipoNome;

    @FXML
    private JFXTextField tfTipoNome, tfTipoID;

    @FXML
    private Button btnSalva, btnElimina;

    public Ricerca() {
    }


    private void loadTblVino() {
        tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("vinoNome"));
        tblColumnAnnata.setCellValueFactory(new PropertyValueFactory<>("annata"));
        tblColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblColumnQta.setCellValueFactory(new PropertyValueFactory<>("qta"));
        tblColumnRegione.setCellValueFactory(new PropertyValueFactory<>("regione"));
        tblColumnStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        tblColumnUvaggio.setCellValueFactory(new PropertyValueFactory<>("uvaggio"));
        tblColumnCantina.setCellValueFactory(new PropertyValueFactory<>("cantinaNome"));
        tblColonnaFornitore.setCellValueFactory(new PropertyValueFactory<>("fornitoreNome"));
    }

    private void loadTblCantina() {
        tblColumnCantinaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnCantinaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnCantinaRegione.setCellValueFactory(new PropertyValueFactory<>("regione"));
        tblColumnCantinaStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        tblColumnCantinaVia.setCellValueFactory(new PropertyValueFactory<>("via"));
        tblColumnCantinaUvaggio.setCellValueFactory(new PropertyValueFactory<>("uvaggio"));
        tblColumnCantinaIdRappr.setCellValueFactory(new PropertyValueFactory<>("idrappresentante"));
    }

    private void loadTblRappresentante() {
        tblColumnRappresentanteID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnRappresentanteMin.setCellValueFactory(new PropertyValueFactory<>("qta_min"));
        tblColumnRappresentanteMax.setCellValueFactory(new PropertyValueFactory<>("qta_max"));
        tblColumnRappresentanteTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tblColumnRappresentanteMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tblColumnRappresentanteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    private void loadTblFornitore(){
        tblColumnFornitoreID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnFornitoreNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnFornitoreQtaMax.setCellValueFactory(new PropertyValueFactory<>("qta_max"));
        tblColumnFornitoreQtaMin.setCellValueFactory(new PropertyValueFactory<>("qta_min"));
        tblColumnFornitoreMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tblColumnFornitoreTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    private void loadTblEnoteca(){
        tblColumnEnotecaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnEnotecaQtaMax.setCellValueFactory(new PropertyValueFactory<>("qta_max"));
        tblColumnEnotecaQtaMin.setCellValueFactory(new PropertyValueFactory<>("qta_min"));
        tblColumnEnotecaMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tblColumnEnotecaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tblColumnEnotecaStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        tblColumnEnotecaCitta.setCellValueFactory(new PropertyValueFactory<>("citta"));
        tblColumnEnotecaRegione.setCellValueFactory(new PropertyValueFactory<>("regione"));
        tblColumnEnotecaVia.setCellValueFactory(new PropertyValueFactory<>("via"));
    }

    private void loadTblTipo() {
        tblColumnTipoNome.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    @FXML
    public void loadAllTbl() throws Exception {
        if (!allLoaded) {

            //VINO
            APIC a = new APIC("vino");
            String[] colonne = {};
            List<Clausola> clausolas = new ArrayList<Clausola>();

            ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
            for (Vino vino : a.select().toObservableList(Vino.class)) {
                wrapperVinos.add(new WrapperVino(vino));
            }
            tblViewListaVino.setItems(wrapperVinos);
            loadTblVino();
            cmbVinoNome.setItems(Utility.loadDataForCmb("vino","nome","",Vino.class));
            cmbVinoTipo.setItems(Utility.loadDataForCmb("vino","tipo","",Vino.class));
            cmbVinoUvaggio.setItems(Utility.loadDataForCmb("cantina","uvaggio","",Cantina.class));
            cmbVinoCantina.setItems(Utility.loadDataForCmb("cantina","nome","",Cantina.class));
            cmbVinoStato.setItems(Utility.loadDataForCmb("cantina","stato","",Cantina.class));
            cmbVinoRegione.setItems(Utility.loadDataForCmb("cantina","regione","",Cantina.class));
            cmbVinoFornitore.setItems(Utility.loadDataForCmb("fornitore","nome","",Fornitore.class));
            cmbVinoAnnata.setItems(Utility.loadDataForCmbInteger("vino","anno",null,Vino.class));
            cmbVinoQta.setItems(Utility.loadDataForCmbInteger("vino","qta",null,Vino.class));


            //CANTINA
            APIC b = new APIC("cantina");
            tblViewListaCantina.setItems(b.select().toObservableList(Cantina.class));
            loadTblCantina();
            cmbCantinaNome.setItems(Utility.loadDataForCmb("cantina", "nome", "", Cantina.class));
            cmbCantinaRegione.setItems(Utility.loadDataForCmb("cantina", "regione", "", Cantina.class));
            cmbCantinaStato.setItems(Utility.loadDataForCmb("cantina", "stato", "", Cantina.class));
            cmbCantinaUvaggio.setItems(Utility.loadDataForCmb("cantina", "uvaggio", "", Cantina.class));
            cmbCantinaVia.setItems(Utility.loadDataForCmb("cantina", "via", "", Cantina.class));

            //FORNITORE
            APIC c = new APIC("fornitore");
            tblViewListaFornitore.setItems(c.select().toObservableList(Fornitore.class));
            loadTblFornitore();
            cmbFornitoreNome.setItems(Utility.loadDataForCmb("fornitore","nome","", Fornitore.class));
            cmbFornitoreMail.setItems(Utility.loadDataForCmb("fornitore","mail","", Fornitore.class));
            cmbFornitoreTelefono.setItems(Utility.loadDataForCmb("fornitore","telefono","", Fornitore.class));
            cmbFornitoreQtaMin.setItems(Utility.loadDataForCmbInteger("fornitore","qta_min",null, Fornitore.class));
            cmbFornitoreQtaMax.setItems(Utility.loadDataForCmbInteger("fornitore","qta_max",null, Fornitore.class));

            //RAPPRESENTANTE
            tblViewListaRappresentate.setItems(Fornitore.getFornitoriRappresentanti(colonne, clausolas));
            loadTblRappresentante();
            cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", "", Fornitore.class));
            cmbRappresentanteMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "mail", "", Fornitore.class));
            cmbRappresentanteTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "telefono", "", Fornitore.class));
            cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_max", null, Fornitore.class));
            cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_min", null, Fornitore.class));

            //ENOTECA
            ObservableList<WrapperEnoteca> enotecaObservableList = FXCollections.observableArrayList();
            for(Fornitore cur: Fornitore.getFornitoriEnoteca(colonne,clausolas)){
                WrapperEnoteca wrapperEnoteca = new WrapperEnoteca(cur);
                System.out.println(wrapperEnoteca.toString());
                enotecaObservableList.add(wrapperEnoteca);
            }
            tblViewListaEnoteca.setItems(enotecaObservableList);
            loadTblEnoteca();
            cmbEnotecaNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(),"nome","", Fornitore.class));
            cmbEnotecaMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(),"mail","", Fornitore.class));
            cmbEnotecaTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(),"telefono","", Fornitore.class));
            cmbEnotecaStato.setItems(Utility.loadDataForCmb("enoteca","stato","", Enoteca.class));
            cmbEnotecaRegione.setItems(Utility.loadDataForCmb("enoteca","regione","", Enoteca.class));
            cmbEnotecaCitta.setItems(Utility.loadDataForCmb("enoteca","citta","", Enoteca.class));
            cmbEnotecaVia.setItems(Utility.loadDataForCmb("enoteca","via","", Enoteca.class));
            cmbEnotecaQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriEnoteche(),"qta_min",null, Fornitore.class));
            cmbEnotecaQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriEnoteche(),"qta_max",null, Fornitore.class));


            //TIPO
            APIC e = new APIC("tipo_vino");
            tblViewListaTipo.setItems(e.select().toObservableList(TipoVino.class));
            loadTblTipo();


            allLoaded = true;
        }
    }

    //Questi due metodi servono per passare il selected item allo stage che mostra tutte le info relative al vino
    //Il primo crea il nuovo stage, il secondo si occupa di fornire il vino selezionato

    //VINO

    @FXML
    private void openContextVino(Event event) {
        try {
            InfoVino infoVino = new InfoVino(this);
            infoVino.showStage();
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public WrapperVino getWrapperVino() {
        WrapperVino cur = (WrapperVino) tblViewListaVino.getSelectionModel().getSelectedItem();
        return cur;
    }

    private void searchVino(Event event, boolean selected){
        try {
            APIC aVino = new APIC("vino");
            APIC aCantina = new APIC("cantina");
            APIC aFornitore =new APIC("fornitore");
            String[] strings = {};
            ArrayList<Clausola> clausolasVino = new ArrayList<>();
            ArrayList<Clausola> clausolasCantina = new ArrayList<>();
            ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();

            if (cmbVinoNome.getSelectionModel().getSelectedItem() != "" && cmbVinoNome.getSelectionModel().getSelectedItem() != null) {
                clausolasVino.add(new Clausola("nome", "like", "%" + cmbVinoNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbVinoTipo.getSelectionModel().getSelectedItem() != "" && cmbVinoTipo.getSelectionModel().getSelectedItem() != null) {
                clausolasVino.add(new Clausola("tipo", "like", "%" + cmbVinoTipo.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbVinoFornitore.getSelectionModel().getSelectedItem() != "" && cmbVinoFornitore.getSelectionModel().getSelectedItem() != null) {
                clausolasFornitore.add(new Clausola("nome", "like", "%" + cmbVinoFornitore.getSelectionModel().getSelectedItem() + "%"));
            }


            if (cmbVinoCantina.getSelectionModel().getSelectedItem() != "" && cmbVinoCantina.getSelectionModel().getSelectedItem() != null) {
                clausolasCantina.add(new Clausola("nome", "like", "%" + cmbVinoCantina.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbVinoUvaggio.getSelectionModel().getSelectedItem() != "" && cmbVinoUvaggio.getSelectionModel().getSelectedItem() != null) {
                clausolasCantina.add(new Clausola("uvaggio", "like", "%" + cmbVinoUvaggio.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbVinoStato.getSelectionModel().getSelectedItem() != "" && cmbVinoStato.getSelectionModel().getSelectedItem() != null) {
                clausolasCantina.add(new Clausola("stato", "like", "%" + cmbVinoStato.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbVinoRegione.getSelectionModel().getSelectedItem() != "" && cmbVinoRegione.getSelectionModel().getSelectedItem() != null) {
                clausolasCantina.add(new Clausola("regione", "like", "%" + cmbVinoRegione.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!String.valueOf(cmbVinoQta.getSelectionModel().getSelectedItem()).equals("") && cmbVinoQta.getSelectionModel().getSelectedItem() != null) {
                clausolasVino.add(new Clausola("qta", "=", String.valueOf(cmbVinoQta.getSelectionModel().getSelectedItem())));
            }
            if (!String.valueOf(cmbVinoAnnata.getSelectionModel().getSelectedItem()).equals("") && cmbVinoAnnata.getSelectionModel().getSelectedItem() != null) {
                clausolasVino.add(new Clausola("anno", "=", String.valueOf(cmbVinoAnnata.getSelectionModel().getSelectedItem())));
            }


            if (!selected) {
                switch (cur.getId()) {
                    case "cmbVinoNome":
                        if (cmbVinoNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbVinoNome.hide();
                        cmbVinoNome.setItems(Utility.loadDataForCmb("vino", "nome", cmbVinoNome.getSelectionModel().getSelectedItem(), Vino.class));
                        cmbVinoNome.show();
                        break;
                    case "cmbVinoTipo":
                        if (cmbVinoTipo.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteMail.hide();
                        cmbVinoTipo.setItems(Utility.loadDataForCmb("vino", "tipo", cmbVinoTipo.getSelectionModel().getSelectedItem(), Vino.class));
                        cmbVinoTipo.show();
                        break;
                    case "cmbVinoCantina":
                        if (cmbVinoCantina.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbVinoCantina.hide();
                        cmbVinoCantina.setItems(Utility.loadDataForCmb("cantina", "nome", cmbVinoCantina.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbVinoCantina.show();
                        break;
                    case "cmbVinoUvaggio":
                        if (cmbVinoUvaggio.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbVinoUvaggio.hide();
                        cmbVinoUvaggio.setItems(Utility.loadDataForCmb("cantina", "uvaggio", cmbVinoUvaggio.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbVinoUvaggio.show();
                        break;
                    case "cmbVinoStato":
                        if (cmbVinoStato.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbVinoStato.hide();
                        cmbVinoStato.setItems(Utility.loadDataForCmb("cantina", "stato", cmbVinoStato.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbVinoStato.show();
                        break;
                    case "cmbVinoRegione":
                        if (cmbVinoRegione.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbVinoRegione.hide();
                        cmbVinoRegione.setItems(Utility.loadDataForCmb("cantina", "regione", cmbVinoRegione.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbVinoRegione.show();
                        break;
                    case "cmbRappresentanteQtaMax":
                        //TODO CERCARE DI METTERE A POSTO LA RICERCA CON NUMERI
//                        if (String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMax.hide();
//                        cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_max", cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem(), Fornitore.class));
//                        cmbCantinaVia.show();
                        break;
                    case "cmbRappresentanteQtaMin":
//                        if (String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMin.hide();
//                        System.out.println("ok");
//                            try {
//                                System.out.println("ok2");
//                                cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_min", cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem(), Fornitore.class));
//                                cmbRappresentanteQtaMin.show();
//                                System.out.println("ok3");
//                        }catch (Exception e){Utility.createErrorWindow(e.getMessage());}
                        break;

                }
            }
            ObservableList<Cantina> cantinaObservableList = aCantina.select(strings,clausolasCantina).toObservableList(Cantina.class);
            ObservableList<Fornitore>  fornitoreObservableList = aFornitore.select(strings,clausolasFornitore).toObservableList(Fornitore.class);
            ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
            for (Vino vino : aVino.select(strings,clausolasVino).toObservableList(Vino.class)) {
                if(!cantinaObservableList.isEmpty()){
                    for(Cantina cantina: cantinaObservableList){
                        if(cantina.getID()==vino.getIdCantina()){
                            for(Fornitore fornitore: fornitoreObservableList) {
                                if(fornitore.getID()==vino.getIdFornitore())
                                    wrapperVinos.add(new WrapperVino(vino));
                            }
                        }
                    }
                }

            }
            tblViewListaVino.setItems(wrapperVinos);
        } catch (Exception e) {
            Utility.createWarningWindow("Controllare i dati inseriti");
        }
    }

    @FXML
    private void selectVino(Event event){
        searchVino(event,true);
    }

    @FXML
    private void typeVino(Event event){
        searchVino(event,false);
    }

    //CANTINA
    @FXML
    private void openContextCantina(Event event) {
        try {
            InfoCantina infoCantina = new InfoCantina(this);
            infoCantina.showStage();
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public WrapperCantina getWrapperCantina() {
        Cantina cantina = (Cantina) tblViewListaCantina.getSelectionModel().getSelectedItem();
        WrapperCantina cur = new WrapperCantina(cantina);
        return cur;
    }

    private void searchCantina(Event event, boolean selected) {
        try {
            APIC a = new APIC("cantina");
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();
            if (cmbCantinaNome.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("nome", "like", "%" + cmbCantinaNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbCantinaVia.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("via", "like", "%" + cmbCantinaVia.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbCantinaUvaggio.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("uvaggio", "like", "%" + cmbCantinaUvaggio.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbCantinaStato.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("stato", "like", "%" + cmbCantinaStato.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbCantinaRegione.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("regione", "like", "%" + cmbCantinaRegione.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!selected) {
                switch (cur.getId()) {
                    case "cmbCantinaNome":
                        if (cmbCantinaNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbCantinaNome.hide();
                        cmbCantinaNome.setItems(Utility.loadDataForCmb("cantina", "nome", cmbCantinaNome.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbCantinaNome.show();
                        break;
                    case "cmbCantinaVia":
                        if (cmbCantinaVia.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbCantinaVia.hide();
                        cmbCantinaVia.setItems(Utility.loadDataForCmb("cantina", "via", cmbCantinaVia.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbCantinaVia.show();
                        break;
                    case "cmbCantinaUvaggio":
                        if (cmbCantinaUvaggio.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbCantinaUvaggio.hide();
                        cmbCantinaUvaggio.setItems(Utility.loadDataForCmb("cantina", "uvaggio", cmbCantinaUvaggio.getSelectionModel().getSelectedItem(), Cantina.class));
                        cmbCantinaUvaggio.show();
                        break;
                    case "cmbCantinaStato":
                        if (cmbCantinaStato.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbCantinaStato.hide();
                        cmbCantinaStato.show();
                        cmbCantinaStato.setItems(Utility.loadDataForCmb("cantina", "stato", cmbCantinaStato.getSelectionModel().getSelectedItem(), Cantina.class));
                        break;
                    case "cmbCantinaRegione":
                        if (cmbCantinaRegione.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbCantinaRegione.hide();
                        cmbCantinaRegione.show();
                        cmbCantinaRegione.setItems(Utility.loadDataForCmb("cantina", "regione", cmbCantinaRegione.getSelectionModel().getSelectedItem(), Cantina.class));
                        break;

                }
            }
            tblViewListaCantina.setItems(a.select(strings, clausolas).toObservableList(Cantina.class));
        } catch (Exception e) {
            Utility.createErrorWindow(e.getMessage());
        }
    }

    @FXML
    private void selectCantina(Event event) {
        searchCantina(event, true);
    }

    @FXML
    private void typeCantina(Event event) {
        searchCantina(event, false);
    }

    //FORNITORE
    @FXML
    private void openContextFornitore(Event event) {
        try {
            InfoRappresentante infoRappresentante = new InfoRappresentante(this);
            infoRappresentante.showStage();
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public Fornitore getFornitore() {
        Fornitore cur = (Fornitore) tblViewListaRappresentate.getSelectionModel().getSelectedItem();
        return cur;
    }

    private void searchFornitore(Event event, boolean selected) {
        try {
            APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();
            if (cmbRappresentanteNome.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteNome.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("nome", "like", "%" + cmbRappresentanteNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals("") && cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_max", "=", String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem())));
            }
            if (!String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals("") && cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_min", "=", String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem())));
            }
            if (cmbRappresentanteMail.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteMail.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("mail", "like", "%" + cmbRappresentanteMail.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("telefono", "like", "%" + cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!selected) {
                switch (cur.getId()) {
                    case "cmbRappresentanteNome":
                        if (cmbRappresentanteNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteNome.hide();
                        cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", cmbRappresentanteNome.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteNome.show();
                        break;
                    case "cmbRappresentanteMail":
                        if (cmbRappresentanteMail.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteMail.hide();
                        cmbRappresentanteMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "mail", cmbRappresentanteMail.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteMail.show();
                        break;
                    case "cmbRappresentanteTelefono":
                        if (cmbRappresentanteTelefono.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteTelefono.hide();
                        cmbRappresentanteTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "telefono", cmbRappresentanteTelefono.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteTelefono.show();
                        break;
                    case "cmbRappresentanteQtaMax":
                        //TODO CERCARE DI METTERE A POSTO LA RICERCA CON NUMERI
//                        if (String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMax.hide();
//                        cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_max", cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem(), Fornitore.class));
//                        cmbCantinaVia.show();
                        break;
                    case "cmbRappresentanteQtaMin":
//                        if (String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMin.hide();
//                        System.out.println("ok");
//                            try {
//                                System.out.println("ok2");
//                                cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_min", cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem(), Fornitore.class));
//                                cmbRappresentanteQtaMin.show();
//                                System.out.println("ok3");
//                        }catch (Exception e){Utility.createErrorWindow(e.getMessage());}
                        break;

                }
            }
            tblViewListaRappresentate.setItems(a.select(strings, clausolas).toObservableList(Fornitore.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectFornitore(Event event) {
        searchFornitore(event, true);
    }

    @FXML
    private void typeFornitore(Event event) {
        searchFornitore(event, false);
    }


    //RAPPRESENTANTE
    @FXML
    private void openContextRappresentante(Event event) {
        try {
            InfoRappresentante infoRappresentante = new InfoRappresentante(this);
            infoRappresentante.showStage();
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public Fornitore getRappresentante() {
        Fornitore cur = (Fornitore) tblViewListaRappresentate.getSelectionModel().getSelectedItem();
        return cur;
    }

    private void searchRappresentante(Event event, boolean selected) {
        try {
            APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();
            if (cmbRappresentanteNome.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteNome.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("nome", "like", "%" + cmbRappresentanteNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals("") && cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_max", "=", String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem())));
            }
            if (!String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals("") && cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_min", "=", String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem())));
            }
            if (cmbRappresentanteMail.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteMail.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("mail", "like", "%" + cmbRappresentanteMail.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("telefono", "like", "%" + cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!selected) {
                switch (cur.getId()) {
                    case "cmbRappresentanteNome":
                        if (cmbRappresentanteNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteNome.hide();
                        cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", cmbRappresentanteNome.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteNome.show();
                        break;
                    case "cmbRappresentanteMail":
                        if (cmbRappresentanteMail.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteMail.hide();
                        cmbRappresentanteMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "mail", cmbRappresentanteMail.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteMail.show();
                        break;
                    case "cmbRappresentanteTelefono":
                        if (cmbRappresentanteTelefono.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteTelefono.hide();
                        cmbRappresentanteTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "telefono", cmbRappresentanteTelefono.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteTelefono.show();
                        break;
                    case "cmbRappresentanteQtaMax":
                        //TODO CERCARE DI METTERE A POSTO LA RICERCA CON NUMERI
//                        if (String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMax.hide();
//                        cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_max", cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem(), Fornitore.class));
//                        cmbCantinaVia.show();
                        break;
                    case "cmbRappresentanteQtaMin":
//                        if (String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMin.hide();
//                        System.out.println("ok");
//                            try {
//                                System.out.println("ok2");
//                                cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_min", cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem(), Fornitore.class));
//                                cmbRappresentanteQtaMin.show();
//                                System.out.println("ok3");
//                        }catch (Exception e){Utility.createErrorWindow(e.getMessage());}
                        break;

                }
            }
            tblViewListaRappresentate.setItems(a.select(strings, clausolas).toObservableList(Fornitore.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectRappresentante(Event event) {
        searchRappresentante(event, true);
    }

    @FXML
    private void typeRappresentante(Event event) {
        searchRappresentante(event, false);
    }

    //ENOTECA

    @FXML
    private void openContextEnoteca(Event event) {
        try {
            InfoRappresentante infoRappresentante = new InfoRappresentante(this);
            infoRappresentante.showStage();
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public Fornitore getEnoteca() {
        Fornitore cur = (Fornitore) tblViewListaRappresentate.getSelectionModel().getSelectedItem();
        return cur;
    }

    private void searchEnoteca(Event event, boolean selected) {
        try {
            APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();
            if (cmbRappresentanteNome.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteNome.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("nome", "like", "%" + cmbRappresentanteNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals("") && cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_max", "=", String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem())));
            }
            if (!String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals("") && cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_min", "=", String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem())));
            }
            if (cmbRappresentanteMail.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteMail.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("mail", "like", "%" + cmbRappresentanteMail.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() != "" && cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("telefono", "like", "%" + cmbRappresentanteTelefono.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!selected) {
                switch (cur.getId()) {
                    case "cmbRappresentanteNome":
                        if (cmbRappresentanteNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteNome.hide();
                        cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", cmbRappresentanteNome.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteNome.show();
                        break;
                    case "cmbRappresentanteMail":
                        if (cmbRappresentanteMail.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteMail.hide();
                        cmbRappresentanteMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "mail", cmbRappresentanteMail.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteMail.show();
                        break;
                    case "cmbRappresentanteTelefono":
                        if (cmbRappresentanteTelefono.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbRappresentanteTelefono.hide();
                        cmbRappresentanteTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "telefono", cmbRappresentanteTelefono.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbRappresentanteTelefono.show();
                        break;
                    case "cmbRappresentanteQtaMax":
                        //TODO CERCARE DI METTERE A POSTO LA RICERCA CON NUMERI
//                        if (String.valueOf(cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMax.hide();
//                        cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_max", cmbRappresentanteQtaMax.getSelectionModel().getSelectedItem(), Fornitore.class));
//                        cmbCantinaVia.show();
                        break;
                    case "cmbRappresentanteQtaMin":
//                        if (String.valueOf(cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem()).equals(""))
//                            cmbRappresentanteQtaMin.hide();
//                        System.out.println("ok");
//                            try {
//                                System.out.println("ok2");
//                                cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_min", cmbRappresentanteQtaMin.getSelectionModel().getSelectedItem(), Fornitore.class));
//                                cmbRappresentanteQtaMin.show();
//                                System.out.println("ok3");
//                        }catch (Exception e){Utility.createErrorWindow(e.getMessage());}
                        break;

                }
            }
            tblViewListaRappresentate.setItems(a.select(strings, clausolas).toObservableList(Fornitore.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectEnoteca(Event event) {
        searchEnoteca(event, true);
    }

    @FXML
    private void typeEnoteca(Event event) {
        searchEnoteca(event, false);
    }


    //TIPO VINO

    private TipoVino getTipoVino() {
        return (TipoVino) tblViewListaTipo.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void openContextTipo(Event event) {
        try {
            TipoVino tipoVino = getTipoVino();
            tfTipoNome.setText(tipoVino.getTipo());
            tfTipoID.setText(String.valueOf(tipoVino.getID()));
        } catch (Exception e) {
        }
    }

    @FXML
    private void btnPress(Event event) {
        Button btnPressed = (Button) event.getSource();
        TipoVino tipoVino = new TipoVino(Integer.valueOf(tfTipoID.getText()), tfTipoNome.getText());
        System.out.println(tipoVino.toString());
        try {

            switch (btnPressed.getId()) {
                case "btnTipoElimina":
                    try {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Conferma Eliminazione");
                        alert.setHeaderText("Stai eliminando il tipo \"" + tipoVino.getTipo() + "\", eliminerai ogni vino ad esso associato!");
                        alert.setContentText("Vuoi davvero eliminare?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            try {
                                ArrayList<Clausola> clausolas = new ArrayList<>();
                                clausolas.add(new Clausola("tipo", "like", tipoVino.getTipo()));
                                eliminaVini(clausolas);
                                tipoVino.delete();
                                System.out.println("Salvo");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Non salvo");
                        }
                    } catch (Exception e) {
                        Utility.createErrorWindow(e.getMessage());
                    }
                    break;
            }
        } catch (Exception exceptionVino) {
            Utility.createErrorWindow("Seleziona un tipo di vino valido");
        }
    }

    @FXML
    public void eliminaVini(ArrayList<Clausola> clausolas) throws Exception {
        APIC a = new APIC("vino");
        String[] strings = {};
        for (Vino cur : a.select(strings, clausolas).toList(Vino.class)) {
            cur.delete();
        }
    }
}
