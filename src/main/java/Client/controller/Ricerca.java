package Client.controller;

import API.APIC;
//import Client.Vino;

import ClientUtils.Clausola;
import Models.Cantina;
import Models.Fornitore;
import Models.Rappresentante;
import Models.Vino;
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
    private TableView tblViewListaRappresentate;

    @FXML
    private TableColumn<Fornitore, String> tblColumnRappresentanteMail, tblColumnRappresentanteNome;

    @FXML
    private TableColumn<Fornitore, Integer> tblColumnRappresentanteMin, tblColumnRappresentanteMax, tblColumnRappresentanteTelefono;

    @FXML
    private TableColumn<Rappresentante, Integer> tblColumnRappresentanteID;

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
        //tblColumnRappresentanteMin.setCellValueFactory(new Cal);
//       tblColumnRappresentanteMax.setCellValueFactory();
//        tblColumnRappresentanteTelefono.setCellValueFactory();
//        tblColumnRappresentanteMail.setCellValueFactory();
//        tblColumnRappresentanteNome.setCellValueFactory();
    }

    @FXML
    public void loadAllTbl() throws Exception {
        if (!allLoaded) {


            APIC a = new APIC("vino");
            String[] colonne = {};
            List<Clausola> clausolas = new ArrayList<Clausola>();

            ObservableList<WrapperVino> wrapperVinos = FXCollections.observableArrayList();
            for (Vino vino : a.select(colonne, clausolas).toObservableList(Vino.class)) {
                wrapperVinos.add(new WrapperVino(vino));
            }

            for (WrapperVino cur : wrapperVinos) {
                System.out.println(cur.vino.getNome() + " " + cur.cantina.getNome());
            }

            loadTblVino();
            tblViewListaVino.setItems(wrapperVinos);


            APIC b = new APIC("cantina");
            tblViewListaCantina.setItems(b.select(colonne, clausolas).toObservableList(Cantina.class));

            APIC c = new APIC("rappresentante");
            tblViewListaRappresentate.setItems(c.select(colonne, clausolas).toObservableList(Rappresentante.class));


            loadTblCantina();

            //loadTblRappresentante();

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

    //Questi due metodi servono per passare il selected item allo stage che mostra tutte le info relative al vino
    //Il primo crea il nuovo stage, il secondo si occupa di fornire il vino selezionato
    @FXML
    private void openContext(Event event) {
        try {
            InfoVino infoVino = new InfoVino(this);
            infoVino.showStage();
        }catch (Exception e){
            Utility.createErrorWindow("Nessuna azione disponibile, selezionare una riga");
        }
    }

    public WrapperVino getWrapperVino() {
        WrapperVino cur = (WrapperVino) tblViewListaVino.getSelectionModel().getSelectedItem();
        return cur;
    }
}
