package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.Cantina;
import Models.Fornitore;
import Models.Rappresentante;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

import java.util.ArrayList;

public class NuovoCantina {
    @FXML
    private JFXButton tfAdd;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXTextField tfUvaggio;

    @FXML
    private JFXTextField tfStato;

    @FXML
    private JFXTextField tfRegione;

    @FXML
    private JFXTextField tfVia;

    @FXML
    private JFXComboBox<String> cmbFornitore;

    @FXML
    private void loadCmbFornitore(){
        APIC a = new APIC("rappresentante");
        APIReturn ret;
        String[] strings={"nome_rappresentante"};
        ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
        try {
            ObservableList<String> nomi = FXCollections.observableArrayList();
            nomi.add("");
            ObservableList<Rappresentante> rappresentantes = a.select(strings,clausolas).toObservableList(Rappresentante.class);

            for(Rappresentante cur: rappresentantes){
                nomi.add(cur.getNome_rappresentante());
            }

            cmbFornitore.setItems(nomi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void insertElement(Event event) {

        if(tfNome.getText().isEmpty() || tfUvaggio.getText().isEmpty() || tfStato.getText().isEmpty() || tfRegione.getText().isEmpty() || tfVia.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi obbligatori");
        }else{
            Cantina cantina = new Cantina();

            cantina.setNome(tfNome.getText());
            cantina.setUvaggio(tfUvaggio.getText());
            cantina.setStato(tfStato.getText());
            cantina.setRegione(tfRegione.getText());
            cantina.setVia(tfVia.getText());

            String nomeFornitore = cmbFornitore.getSelectionModel().getSelectedItem();

            if(nomeFornitore!=""){
                APIC a = new APIC("rappresentante");
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
                clausolas.add(new Clausola("nome_rappresentante","=",nomeFornitore));
                try {
                    Rappresentante rappresentante = a.select(strings,clausolas).toObservableList(Rappresentante.class).get(0);
                    System.out.println(rappresentante.toString());
                    cantina.setIdrappresentante(rappresentante.getID());
                    cantina.getIdrappresentante();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                boolean presente = false;
                APIC a = new APIC("cantina");
                APIReturn ret;
                String[] strings={"nome"};
                ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
                clausolas.add(new Clausola("nome", "LIKE", cantina.getNome()));
                if(a.select(strings,clausolas).toList(Cantina.class).isEmpty()) {
                    cantina.insert();
                    tfNome.setText("");
                    tfUvaggio.setText("");
                    tfStato.setText("");
                    tfRegione.setText("");
                    tfVia.setText("");
                    cmbFornitore.getSelectionModel().selectFirst();
                }
                else{
                    Utility.createErrorWindow("Presente");
                }
            }catch (Exception e){
                Utility.createErrorWindow(cantina.getIdrappresentante()+" "+e.getMessage());
            }

        }

    }
}
