package Client.controller;

import API.APIC;
//import Client.Vino;

import ClientUtils.Clausola;
import Models.*;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.sound.sampled.Line;
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

            //CANTINA
            APIC b = new APIC("cantina");
            tblViewListaCantina.setItems(b.select().toObservableList(Cantina.class));
            loadTblCantina();
            cmbCantinaNome.setItems(Utility.loadDataForCmb("cantina", "nome", "", Cantina.class));
            cmbCantinaRegione.setItems(Utility.loadDataForCmb("cantina", "regione", "", Cantina.class));
            cmbCantinaStato.setItems(Utility.loadDataForCmb("cantina", "stato", "", Cantina.class));
            cmbCantinaUvaggio.setItems(Utility.loadDataForCmb("cantina", "uvaggio", "", Cantina.class));
            cmbCantinaVia.setItems(Utility.loadDataForCmb("cantina", "via", "", Cantina.class));


            //RAPPRESENTANTE
            tblViewListaRappresentate.setItems(Fornitore.getFornitoriRappresentanti(colonne, clausolas));
            loadTblRappresentante();
            cmbRappresentanteNome.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "nome", "", Fornitore.class));
            cmbRappresentanteMail.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "mail", "", Fornitore.class));
            cmbRappresentanteTelefono.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(), "telefono", "", Fornitore.class));
//            cmbRappresentanteQtaMax.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(),"qta_max","",Fornitore.class));
//            cmbRappresentanteQtaMin.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(),"qta_min","",Fornitore.class));


            //TIPO
            APIC c = new APIC("tipo_vino");
            tblViewListaTipo.setItems(c.select().toObservableList(TipoVino.class));
            loadTblTipo();


            allLoaded = true;
        }
    }


    @FXML
    void searchElement(Event event) {
        JFXTextField tfAttivo = (JFXTextField) event.getSource();
        switch (tfAttivo.getId()) {
            case "tfNome":
                values[0] = tfAttivo.getText();
                break;
            case "tfAnnata":
                values[1] = tfAttivo.getText();
                break;
            case "tfCantina":
                values[2] = tfAttivo.getText();
                break;
            case "tfTipo":
                values[3] = tfAttivo.getText();
                break;
            case "tfFornitore":
                values[4] = tfAttivo.getText();
                break;
            case "tfUvaggio":
                values[5] = tfAttivo.getText();
                break;
            case "tfStato":
                values[6] = tfAttivo.getText();
                break;
            case "tfRegione":
                values[7] = tfAttivo.getText();
                break;
            case "tfQta":
                values[8] = tfAttivo.getText();
                break;
        }
        try {
            APIC aVino = new APIC("vino");
            APIC aCantina = new APIC("cantina");
            APIC aFornitore = new APIC("fornitore");
            APIReturn ret;
            String[] strings = {};
            ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
            for (int i = 0; i < 9; i++) {
                if (values[i] != null) {
                    switch (i) {
                        case 2:
                            clausolas.add(new Clausola("nome", "like", "%" + values[i] + "%"));
                            ret = aCantina.select(strings, clausolas);
                            clausolas.remove(clausolas.size() - 1);
                            break;
                        case 4:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        default:
                            clausolas.add(new Clausola(campi[i], "like", "%" + values[i] + "%"));
                            break;
                    }
                    System.out.println(campi[i] + " " + values[i]);
                }
            }
            ret = aVino.select(strings, clausolas);
            ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
            for (Vino cur : ret.toList(Vino.class)) {
                System.out.println(cur.toString());
                wrapperVinos.add(new WrapperVino(cur));
            }
            tblViewListaVino.setItems(wrapperVinos);
        } catch (Exception e) {

        }
    }


    @FXML
    private void searchCmb() {
        tblLoaded = true;
        APIC a = new APIC("vino");
        APIReturn ret;
        String[] strings = {};
        ArrayList<Clausola> clausolas = new ArrayList<>();
        try {
            loadTblVino();
            clausolas.add(new Clausola(campi[0], "like", "%" + cmbVinoNome.getSelectionModel().getSelectedItem() + "%"));
            ObservableList<String> nomi = FXCollections.observableArrayList();
            ObservableList<Vino> vinos = a.select(strings, clausolas).toObservableList(Vino.class);

            for (Vino cur : vinos) {
                nomi.add(cur.getNome());
            }
            cmbVinoNome.setItems(nomi);
            cmbVinoNome.hide();
            cmbVinoNome.show();

            //tblViewListaVino.setItems(vinos);
        } catch (Exception e) {

        }

    }

    @FXML
    private void cmbSearchRappresentante() {

    }


    //Questi due metodi servono per passare il selected item allo stage che mostra tutte le info relative al vino
    //Il primo crea il nuovo stage, il secondo si occupa di fornire il vino selezionato

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

    //TIPO VINO

    private TipoVino getTipoVino(){
        return (TipoVino) tblViewListaTipo.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void openContextTipo(Event event){
        try{
            TipoVino tipoVino = getTipoVino();
            tfTipoNome.setText(tipoVino.getTipo());
            tfTipoID.setText(String.valueOf(tipoVino.getID()));
        }catch (Exception e){
        }
    }

    @FXML
    private void btnPress(Event event) {
        Button btnPressed = (Button) event.getSource();
        TipoVino tipoVino = new TipoVino(Integer.valueOf(tfTipoID.getText()),tfTipoNome.getText());
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
                                clausolas.add(new Clausola("tipo","like",tipoVino.getTipo()));
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
    public void eliminaVini(ArrayList<Clausola> clausolas) throws Exception{
        APIC a = new APIC("vino");
        String[] strings = {};
        for(Vino cur: a.select(strings,clausolas).toList(Vino.class)){
            cur.delete();
        }
    }
}
