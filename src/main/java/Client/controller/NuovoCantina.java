package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.Cantina;
import Models.Fornitore;
import Models.Model;
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
    private JFXComboBox<String> cmbRappresentante;

    @FXML
    private void loadCmbRappresentante(){
        try {
            System.out.println("ok");
            String[] strings = {""};
            ArrayList<Clausola> clausolas = new ArrayList<>();
            ObservableList<String> nomi = FXCollections.observableArrayList();
            for(Fornitore cur: Fornitore.getFornitoriRappresentanti(strings,clausolas)){
                System.out.println(cur.toString());
                nomi.add(cur.getNome());
            }
            cmbRappresentante.setItems(Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(),"nome","",Fornitore.class));
        }catch (Exception e){
            e.getMessage();
        }
    }

    @FXML
    void insertElement(Event event) {

        if(tfNome.getText().isEmpty() || tfUvaggio.getText().isEmpty() || tfStato.getText().isEmpty() || tfRegione.getText().isEmpty() || tfVia.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi obbligatori");
        }else{
            Cantina cantina = new Cantina();
            cantina.setNome(Utility.replaceAllDeniedChar(tfNome.getText()));
            cantina.setUvaggio(Utility.replaceAllDeniedChar(tfUvaggio.getText()));
            cantina.setStato(Utility.replaceAllDeniedChar(tfStato.getText()));
            cantina.setRegione(Utility.replaceAllDeniedChar(tfRegione.getText()));
            cantina.setVia(Utility.replaceAllDeniedChar(tfVia.getText()));

            String nomeFornitore = cmbRappresentante.getSelectionModel().getSelectedItem();

            if(nomeFornitore!=""){
                APIC a = new APIC("fornitore");
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
//                Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(),"nome","Fornitore",Fornitore.class);
                clausolas.add(new Clausola("nome","=",nomeFornitore));
                try {
                    Rappresentante rappresentante = a.select(strings,clausolas).toObservableList(Rappresentante.class).get(0);
                    System.out.println(rappresentante.toString());
                    cantina.setIdrappresentante(rappresentante.getID());
                    cantina.getIdrappresentante();

                } catch (Exception e) {
                    e.getMessage();
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
                    cmbRappresentante.getSelectionModel().selectFirst();
                    Utility.createSuccessWindow("Inserimento avvenuto con successo");
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
