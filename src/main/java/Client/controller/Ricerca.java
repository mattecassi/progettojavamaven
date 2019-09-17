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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Ricerca implements Initializable {

    public boolean allLoaded = false;
    @FXML
    private AnchorPane apRicerca;

    @FXML
    private Tab tabVino;

    @FXML
    private ComboBox<String> cmbVinoNome, cmbVinoTipo, cmbVinoCantina, cmbVinoUvaggio, cmbVinoStato, cmbVinoRegione, cmbVinoFornitore;

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
    private TableColumn<WrapperEnoteca, String> tblColumnEnotecaNome, tblColumnEnotecaMail, tblColumnEnotecaTelefono, tblColumnEnotecaStato, tblColumnEnotecaRegione, tblColumnEnotecaCitta, tblColumnEnotecaVia;

    @FXML
    private TableColumn<WrapperEnoteca, Integer> tblColumnEnotecaQtaMin, tblColumnEnotecaQtaMax;

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

    //IMPOSTO IN QUALE COLONNA DOVRANNO ANDARE I DATI
    private void initTblVino() {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
                loadAllTbl();
        }catch (Exception e){
            Utility.createErrorWindow(e.getMessage());
        }
    }

    //IMPOSTO I DATI DELLA TABELLA DEI VINI
    public void loadTblVino() {
        APIC a = new APIC("vino");

        ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
        try {
            for (Vino vino : a.select().toObservableList(Vino.class)) {
                //CREO IL WRAPPER VINO PER OTTENERE TUTTE LE INFORMAZIONI PER OGNI VINO
                wrapperVinos.add(new WrapperVino(vino));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //IMPOSTO L'OBSERVABLE LIST DI WRAPPER VINO
        tblViewListaVino.setItems(wrapperVinos);
    }
    //IMPOSTO IN QUALE COLONNA DOVRANNO ANDARE I DATI
    private void initTblCantina() {
        tblColumnCantinaId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnCantinaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnCantinaRegione.setCellValueFactory(new PropertyValueFactory<>("regione"));
        tblColumnCantinaStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        tblColumnCantinaVia.setCellValueFactory(new PropertyValueFactory<>("via"));
        tblColumnCantinaUvaggio.setCellValueFactory(new PropertyValueFactory<>("uvaggio"));
        tblColumnCantinaIdRappr.setCellValueFactory(new PropertyValueFactory<>("idrappresentante"));
    }
    //IMPOSTO I DATI DELLA TABELLA DELLE CANTINE
    public void loadTblCantina() {
        APIC b = new APIC("cantina");
        try {
            tblViewListaCantina.setItems(b.select().toObservableList(Cantina.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //IMPOSTO IN QUALE COLONNA DOVRANNO ANDARE I DATI
    private void initTblRappresentante() {
        tblColumnRappresentanteID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnRappresentanteMin.setCellValueFactory(new PropertyValueFactory<>("qta_min"));
        tblColumnRappresentanteMax.setCellValueFactory(new PropertyValueFactory<>("qta_max"));
        tblColumnRappresentanteTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tblColumnRappresentanteMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tblColumnRappresentanteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }
    //IMPOSTO I DATI DELLA TABELLA DEI RAPPRESENTANTI
    public void loadTblRappresentante() {
        String[] strings = {};
        ArrayList<Clausola> clausolas = new ArrayList<>();
        try {
            tblViewListaRappresentate.setItems(Fornitore.getFornitoriRappresentanti(strings, clausolas));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //IMPOSTO IN QUALE COLONNA DOVRANNO ANDARE I DATI
    private void initTblFornitore() {
        tblColumnFornitoreID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tblColumnFornitoreNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblColumnFornitoreQtaMax.setCellValueFactory(new PropertyValueFactory<>("qta_max"));
        tblColumnFornitoreQtaMin.setCellValueFactory(new PropertyValueFactory<>("qta_min"));
        tblColumnFornitoreMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        tblColumnFornitoreTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }
    //IMPOSTO I DATI DELLA TABELLA DEI FORNITORI, INCLUDO ANCHE RAPPRESENTANTI E ENOTECHE
    public void loadTblFornitore() {
        APIC c = new APIC("fornitore");
        try {
            tblViewListaFornitore.setItems(c.select().toObservableList(Fornitore.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //IMPOSTO IN QUALE COLONNA DOVRANNO ANDARE I DATI
    private void iniTblEnoteca() {
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
    //IMPOSTO I DATI DELLA TABELLA DELL'ENOTECHE
    public void loadTblEnoteca() {
        String[] strings = {};
        ArrayList<Clausola> clausolas = new ArrayList<>();
        ObservableList<WrapperEnoteca> enotecaObservableList = FXCollections.observableArrayList();
        try {
            for (Fornitore cur : Fornitore.getFornitoriEnoteca(strings, clausolas)) {
                WrapperEnoteca wrapperEnoteca = null;
                wrapperEnoteca = new WrapperEnoteca(cur);
                enotecaObservableList.add(wrapperEnoteca);
                tblViewListaEnoteca.setItems(enotecaObservableList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //IMPOSTO IN QUALE COLONNA DOVRANNO ANDARE I DATI
    private void initTblTipo() {
        tblColumnTipoNome.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    //IMPOSTO I DATI DELLA TABELLA DEI TIPI DI VINO
    public void loadTblTipo() {
        APIC e = new APIC("tipo_vino");
        try {
            tblViewListaTipo.setItems(e.select().toObservableList(TipoVino.class));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    //INIZIALIZZO E CARICO TUTTE LE TABELLE E CARICO NELLE COMBOBOX I RISPETTIVI DATI
    @FXML
    public void loadAllTbl() throws Exception {
        if (!allLoaded) {
            String[] colonne = {};
            List<Clausola> clausolas = new ArrayList<Clausola>();
            //VINO
            loadTblVino();
            initTblVino();
            //CARICO TUTTI I DATI NELLE COMBOBOX DEI VINI
            cmbVinoNome.setItems(Utility.loadDataForCmb("vino", "nome", "", Vino.class));
            cmbVinoTipo.setItems(Utility.loadDataForCmb("vino", "tipo", "", Vino.class));
            cmbVinoUvaggio.setItems(Utility.loadDataForCmb("cantina", "uvaggio", "", Cantina.class));
            cmbVinoCantina.setItems(Utility.loadDataForCmb("cantina", "nome", "", Cantina.class));
            cmbVinoStato.setItems(Utility.loadDataForCmb("cantina", "stato", "", Cantina.class));
            cmbVinoRegione.setItems(Utility.loadDataForCmb("cantina", "regione", "", Cantina.class));
            cmbVinoFornitore.setItems(Utility.loadDataForCmb("fornitore", "nome", "", Fornitore.class));
            cmbVinoAnnata.setItems(Utility.loadDataForCmbInteger("vino", "anno", null, Vino.class));
            cmbVinoQta.setItems(Utility.loadDataForCmbInteger("vino", "qta", null, Vino.class));


            //CANTINA
            loadTblCantina();
            initTblCantina();
            //CARICO TUTTI I DATI NELLE COMBOBOX DELLE CANTINE
            cmbCantinaNome.setItems(Utility.loadDataForCmb("cantina", "nome", "", Cantina.class));
            cmbCantinaRegione.setItems(Utility.loadDataForCmb("cantina", "regione", "", Cantina.class));
            cmbCantinaStato.setItems(Utility.loadDataForCmb("cantina", "stato", "", Cantina.class));
            cmbCantinaUvaggio.setItems(Utility.loadDataForCmb("cantina", "uvaggio", "", Cantina.class));
            cmbCantinaVia.setItems(Utility.loadDataForCmb("cantina", "via", "", Cantina.class));

            //FORNITORE
            loadTblFornitore();
            initTblFornitore();
            //CARICO TUTTI I DATI NELLE COMBOBOX DEI FORNITORI
            cmbFornitoreNome.setItems(Utility.loadDataForCmb("fornitore", "nome", "", Fornitore.class));
            cmbFornitoreMail.setItems(Utility.loadDataForCmb("fornitore", "mail", "", Fornitore.class));
            cmbFornitoreTelefono.setItems(Utility.loadDataForCmb("fornitore", "telefono", "", Fornitore.class));
            cmbFornitoreQtaMin.setItems(Utility.loadDataForCmbInteger("fornitore", "qta_min", null, Fornitore.class));
            cmbFornitoreQtaMax.setItems(Utility.loadDataForCmbInteger("fornitore", "qta_max", null, Fornitore.class));

            //RAPPRESENTANTE
            //CARICO TUTTI I DATI NELLE COMBOBOX DEI RAPPRESENTANTI
            loadTblRappresentante();
            initTblRappresentante();
            cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", "", Fornitore.class));
            cmbRappresentanteMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "mail", "", Fornitore.class));
            cmbRappresentanteTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "telefono", "", Fornitore.class));
            cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_max", null, Fornitore.class));
            cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriRappresentanti(), "qta_min", null, Fornitore.class));

            //ENOTECA
            //CARICO TUTTI I DATI NELLE COMBOBOX DELLE ENOTECHE
            loadTblEnoteca();
            iniTblEnoteca();
            cmbEnotecaNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(), "nome", "", Fornitore.class));
            cmbEnotecaMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(), "mail", "", Fornitore.class));
            cmbEnotecaTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(), "telefono", "", Fornitore.class));
            cmbEnotecaStato.setItems(Utility.loadDataForCmb("enoteca", "stato", "", Enoteca.class));
            cmbEnotecaRegione.setItems(Utility.loadDataForCmb("enoteca", "regione", "", Enoteca.class));
            cmbEnotecaCitta.setItems(Utility.loadDataForCmb("enoteca", "citta", "", Enoteca.class));
            cmbEnotecaVia.setItems(Utility.loadDataForCmb("enoteca", "via", "", Enoteca.class));
            cmbEnotecaQtaMin.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriEnoteche(), "qta_min", null, Fornitore.class));
            cmbEnotecaQtaMax.setItems(Utility.loadDataForCmbInteger(Fornitore.getTableFornitoriEnoteche(), "qta_max", null, Fornitore.class));


            //TIPO
            loadTblTipo();
            initTblTipo();


            allLoaded = true;
        }
    }

    //VINO

    //SU RICHIESTA DI CONTEXTMENU SU UNA RIGA DI TABELLA, CREO UN NUOVO STAGE
    @FXML
    private void openContextVino(Event event) {
        try {
            if(getWrapperVino()!=null) {
                InfoVino infoVino = new InfoVino(this);
                infoVino.showStage();
            }
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }
    //METODO PER CARICARE IL DATO SELEZIONATO NEL NUOVO STAGE
    public WrapperVino getWrapperVino() {
        WrapperVino cur = (WrapperVino) tblViewListaVino.getSelectionModel().getSelectedItem();
        return cur;
    }

    //RICERCA TRAMITE COMBOBOX DI UN VINO
    private void searchVino(Event event, boolean selected) {
        try {
            APIC aVino = new APIC("vino");
            APIC aCantina = new APIC("cantina");
            APIC aFornitore = new APIC("fornitore");
            String[] strings = {};
            ArrayList<Clausola> clausolasVino = new ArrayList<>();
            ArrayList<Clausola> clausolasCantina = new ArrayList<>();
            ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();

            //IN BASE ALLA COMBOBOX IN CUI STO SCRIVENDO, CREO UNA NUOVA CLAUSOLA CHE USERO PER LA RICERCA
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

            //SE STO SCRIVENDO NELLA COMBO BOX (QUINDI NON HO SELEZIONATO L'ELEMENTO DAL MENU A TENDINA), APRO IL MENU A TENDINA CON I RISULTATI SIMILI A CIO CHE HO SCRITTO
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
                    case "cmbVinoFornitore":
                        if (cmbVinoFornitore.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbVinoFornitore.hide();
                        cmbVinoFornitore.setItems(Utility.loadDataForCmb("fornitore", "nome", cmbVinoFornitore.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbVinoFornitore.show();
                        break;
                }
            }

            //EFFETTUO LA RICERCA DEL VINO TENENDO A MENTE DI TUTTE LE CLAUSOLE CHE HO CREATO
            ObservableList<Cantina> cantinaObservableList = aCantina.select(strings, clausolasCantina).toObservableList(Cantina.class);
            ObservableList<Fornitore> fornitoreObservableList = aFornitore.select(strings, clausolasFornitore).toObservableList(Fornitore.class);
            ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
            for (Vino vino : aVino.select(strings, clausolasVino).toObservableList(Vino.class)) {
                if (!cantinaObservableList.isEmpty()) {
                    for (Cantina cantina : cantinaObservableList) {
                        if (cantina.getID() == vino.getIdCantina()) {
                            for (Fornitore fornitore : fornitoreObservableList) {
                                if (fornitore.getID() == vino.getIdFornitore())
                                    wrapperVinos.add(new WrapperVino(vino));
                            }
                        }
                    }
                }

            }
            //INFINE CARICO NELLA TABELLA I RISULTATI
            tblViewListaVino.setItems(wrapperVinos);
        } catch (Exception e) {
            //Utility.createWarningWindow("Controllare i dati inseriti");
        }
    }

    //METODO CHIAMATO QUANDO SELEZIONO UN ELEMENTO DAL MENU A TENDINA
    @FXML
    private void selectVino(Event event) {
        searchVino(event, true);
    }
    //METODO CHIAMATO QUANDO SCRIVO QUALCOSA NELLA COMBOBOX
    @FXML
    private void typeVino(Event event) {
        searchVino(event, false);
    }

    //CANTINA ANALOGO A VINO
    @FXML
    private void openContextCantina(Event event) {
        try {
            if(getWrapperCantina()!=null) {
                InfoCantina infoCantina = new InfoCantina(this);
                infoCantina.showStage();
            }
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
            //Utility.createErrorWindow(e.getMessage());
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

    //FORNITORE ANALOGO A VINO
    @FXML
    private void openContextFornitore(Event event) {
        try {
            if(getFornitore()!=null) {
                InfoRappresentante infoFornitore = new InfoRappresentante(this, "fornitore");
                infoFornitore.showStage();
            }
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public Fornitore getFornitore() {
        Fornitore cur = (Fornitore) tblViewListaFornitore.getSelectionModel().getSelectedItem();
        return cur;
    }

    private void searchFornitore(Event event, boolean selected) {
        try {
            APIC a = new APIC("fornitore");
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();
            if (cmbFornitoreNome.getSelectionModel().getSelectedItem() != "" && cmbFornitoreNome.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("nome", "like", "%" + cmbFornitoreNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbFornitoreMail.getSelectionModel().getSelectedItem() != "" && cmbFornitoreMail.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("mail", "like", "%" + cmbFornitoreMail.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbFornitoreTelefono.getSelectionModel().getSelectedItem() != "" && cmbFornitoreTelefono.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("telefono", "like", "%" + cmbFornitoreTelefono.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!String.valueOf(cmbEnotecaQtaMax.getSelectionModel().getSelectedItem()).equals("") && cmbEnotecaQtaMax.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_max", "=", String.valueOf(cmbEnotecaQtaMax.getSelectionModel().getSelectedItem())));
            }
            if (!String.valueOf(cmbFornitoreQtaMin.getSelectionModel().getSelectedItem()).equals("") && cmbFornitoreQtaMin.getSelectionModel().getSelectedItem() != null) {
                clausolas.add(new Clausola("qta_min", "=", String.valueOf(cmbFornitoreQtaMin.getSelectionModel().getSelectedItem())));
            }

            if (!selected) {
                switch (cur.getId()) {
                    case "cmbFornitoreNome":
                        if (cmbFornitoreNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbFornitoreNome.hide();
                        cmbFornitoreNome.setItems(Utility.loadDataForCmb("fornitore", "nome", cmbFornitoreNome.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbFornitoreNome.show();
                        break;
                    case "cmbFornitoreMail":
                        if (cmbFornitoreMail.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbFornitoreMail.hide();
                        cmbFornitoreMail.setItems(Utility.loadDataForCmb("fornitore", "mail", cmbFornitoreMail.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbFornitoreMail.show();
                        break;
                    case "cmbFornitoreTelefono":
                        if (cmbFornitoreTelefono.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbFornitoreTelefono.hide();
                        cmbFornitoreTelefono.setItems(Utility.loadDataForCmb("fornitore", "telefono", cmbFornitoreTelefono.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbFornitoreTelefono.show();
                        break;
                }
            }
            tblViewListaFornitore.setItems(a.select(strings, clausolas).toObservableList(Fornitore.class));
        } catch (Exception e) {
           // e.printStackTrace();
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


    //RAPPRESENTANTE ANALOGO A VINO
    @FXML
    private void openContextRappresentante(Event event) {
        try {
            if(getRappresentante()!=null) {
                InfoRappresentante infoRappresentante = new InfoRappresentante(this, "rappresentante");
                infoRappresentante.showStage();
            }
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
                }
            }
            tblViewListaRappresentate.setItems(a.select(strings, clausolas).toObservableList(Fornitore.class));
        } catch (Exception e) {
           // e.printStackTrace();
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

    //ENOTECA ANALOGO A VINO

    @FXML
    private void openContextEnoteca(Event event) {
        try {
                if(getWrapperEnoteca()!=null) {
                    InfoEnoteca infoEnoteca = new InfoEnoteca(this);
                    infoEnoteca.showStage();
                }
        } catch (Exception e) {
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public WrapperEnoteca getWrapperEnoteca() {
        WrapperEnoteca cur = (WrapperEnoteca) tblViewListaEnoteca.getSelectionModel().getSelectedItem();
        return cur;
    }

    private void searchEnoteca(Event event, boolean selected) {
        try {
            APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
            APIC b = new APIC("enoteca");
            String[] strings = {};
            ArrayList<Clausola> clausolasFornitore = new ArrayList<>();
            ArrayList<Clausola> clausolasEnoteca = new ArrayList<>();
            JFXComboBox cur = (JFXComboBox) event.getSource();
            if (cmbEnotecaNome.getSelectionModel().getSelectedItem() != "" && cmbEnotecaNome.getSelectionModel().getSelectedItem() != null) {
                clausolasFornitore.add(new Clausola("nome", "like", "%" + cmbEnotecaNome.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!String.valueOf(cmbEnotecaQtaMax.getSelectionModel().getSelectedItem()).equals("") && cmbEnotecaQtaMax.getSelectionModel().getSelectedItem() != null) {
                clausolasFornitore.add(new Clausola("qta_max", "=", String.valueOf(cmbEnotecaQtaMax.getSelectionModel().getSelectedItem())));
            }
            if (!String.valueOf(cmbEnotecaQtaMin.getSelectionModel().getSelectedItem()).equals("") && cmbEnotecaQtaMin.getSelectionModel().getSelectedItem() != null) {
                clausolasFornitore.add(new Clausola("qta_min", "=", String.valueOf(cmbEnotecaQtaMin.getSelectionModel().getSelectedItem())));
            }
            if (cmbEnotecaMail.getSelectionModel().getSelectedItem() != "" && cmbEnotecaMail.getSelectionModel().getSelectedItem() != null) {
                clausolasFornitore.add(new Clausola("mail", "like", "%" + cmbEnotecaMail.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbEnotecaTelefono.getSelectionModel().getSelectedItem() != "" && cmbEnotecaTelefono.getSelectionModel().getSelectedItem() != null) {
                clausolasFornitore.add(new Clausola("telefono", "like", "%" + cmbEnotecaTelefono.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbEnotecaStato.getSelectionModel().getSelectedItem() != "" && cmbEnotecaStato.getSelectionModel().getSelectedItem() != null) {
                clausolasEnoteca.add(new Clausola("stato", "like", "%" + cmbEnotecaStato.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbEnotecaRegione.getSelectionModel().getSelectedItem() != "" && cmbEnotecaRegione.getSelectionModel().getSelectedItem() != null) {
                clausolasEnoteca.add(new Clausola("regione", "like", "%" + cmbEnotecaRegione.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbEnotecaRegione.getSelectionModel().getSelectedItem() != "" && cmbEnotecaRegione.getSelectionModel().getSelectedItem() != null) {
                clausolasEnoteca.add(new Clausola("telefono", "like", "%" + cmbEnotecaRegione.getSelectionModel().getSelectedItem() + "%"));
            }
            if (cmbEnotecaVia.getSelectionModel().getSelectedItem() != "" && cmbEnotecaVia.getSelectionModel().getSelectedItem() != null) {
                clausolasEnoteca.add(new Clausola("telefono", "like", "%" + cmbEnotecaVia.getSelectionModel().getSelectedItem() + "%"));
            }
            if (!selected) {
                switch (cur.getId()) {
                    case "cmbEnotecaNome":
                        if (cmbEnotecaNome.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaNome.hide();
                        cmbEnotecaNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(), "nome", cmbEnotecaNome.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbEnotecaNome.show();
                        break;
                    case "cmbEnotecaMail":
                        if (cmbEnotecaMail.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaMail.hide();
                        cmbEnotecaMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(), "mail", cmbEnotecaMail.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbEnotecaMail.show();
                        break;
                    case "cmbEnotecaTelefono":
                        if (cmbEnotecaTelefono.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaTelefono.hide();
                        cmbEnotecaTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriEnoteche(), "telefono", cmbEnotecaTelefono.getSelectionModel().getSelectedItem(), Fornitore.class));
                        cmbEnotecaTelefono.show();
                        break;
                    case "cmbEnotecaStato":
                        if (cmbEnotecaStato.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaStato.hide();
                        cmbEnotecaStato.setItems(Utility.loadDataForCmb("enoteca", "stato", cmbEnotecaStato.getSelectionModel().getSelectedItem(), Enoteca.class));
                        cmbEnotecaStato.show();
                        break;
                    case "cmbEnotecaRegione":
                        if (cmbEnotecaRegione.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaRegione.hide();
                        cmbEnotecaRegione.setItems(Utility.loadDataForCmb("enoteca", "regione", cmbEnotecaRegione.getSelectionModel().getSelectedItem(), Enoteca.class));
                        cmbEnotecaRegione.show();
                        break;
                    case "cmbEnotecaCitta":
                        if (cmbEnotecaCitta.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaCitta.hide();
                        cmbEnotecaCitta.setItems(Utility.loadDataForCmb("enoteca", "citta", cmbEnotecaCitta.getSelectionModel().getSelectedItem(), Enoteca.class));
                        cmbEnotecaCitta.show();
                        break;
                    case "cmbEnotecaVia":
                        if (cmbEnotecaVia.getSelectionModel().getSelectedItem().equalsIgnoreCase(""))
                            cmbEnotecaVia.hide();
                        cmbEnotecaVia.setItems(Utility.loadDataForCmb("enoteca", "via", cmbEnotecaTelefono.getSelectionModel().getSelectedItem(), Enoteca.class));
                        cmbEnotecaVia.show();
                        break;
                }
            }
            ObservableList<Enoteca> enotecaObservableList = b.select(strings, clausolasEnoteca).toObservableList(Enoteca.class);
            ObservableList<WrapperEnoteca> wrapperEnotecas = FXCollections.observableArrayList();
            for (Fornitore fornitore : a.select(strings, clausolasFornitore).toObservableList(Fornitore.class)) {
                for (Enoteca enoteca : enotecaObservableList) {
                    if (enoteca.getID() == fornitore.getID()) {
                        wrapperEnotecas.add(new WrapperEnoteca(fornitore));
                    }
                }
            }
            tblViewListaEnoteca.setItems(wrapperEnotecas);
        } catch (Exception e) {
            //e.printStackTrace();
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


    //TIPO VINOANALOGO A VINO

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
    //PRENDO IL NOME DEL TIPO DI VINO E DOPO AVERE CHIESTO CONFERMA ELIMININO IL TIPO E TUTTI I VINI CHE HANNO QUEL TIPO
    @FXML
    private void btnPress(Event event) {
        Button btnPressed = (Button) event.getSource();
        try {
            TipoVino tipoVino = new TipoVino(Integer.valueOf(tfTipoID.getText()), tfTipoNome.getText());
            System.out.println(tipoVino.toString());
            try {

                switch (btnPressed.getId()) {
                    case "btnTipoElimina":
                        try {
                            //POP UP PER LA CONFERMA DELL'ELIMINAZIONE
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Conferma Eliminazione");
                            alert.setHeaderText("Stai eliminando il tipo \"" + tipoVino.getTipo() + "\", eliminerai ogni vino ad esso associato!");
                            alert.setContentText("Vuoi davvero eliminare?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    //CLAUSOLO PER TIPO DEL VINO CHE DOVRO ELIMINARE
                                    ArrayList<Clausola> clausolas = new ArrayList<>();
                                    clausolas.add(new Clausola("tipo", "like", tipoVino.getTipo()));
                                    eliminaVini(clausolas);
                                    tipoVino.delete();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            Utility.createErrorWindow(e.getMessage());
                        } finally {
                            event.consume();
                        }
                        break;
                }
            } catch (Exception exceptionVino) {
                Utility.createErrorWindow("Seleziona un tipo di vino valido");
            }
        } catch (Exception e) {
            Utility.createWarningWindow("Prima di eliminare un tipo devi selezionarlo dalla tabella");
        }
    }

    //Passare per parametro un campo su cui eseguire il WHERE e elimina tutti i vini trovati
    public void eliminaVini(ArrayList<Clausola> clausolas) throws Exception {
        APIC a = new APIC("vino");
        String[] strings = {};
        for (Vino cur : a.select(strings, clausolas).toList(Vino.class)) {
            cur.delete();
        }
    }
}
