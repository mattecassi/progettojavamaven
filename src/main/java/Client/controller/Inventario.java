package Client.controller;
import API.APIC;
import ClientUtils.Clausola;
import Models.Vino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Inventario{

    private boolean startup=true;

    @FXML
    private JFXTextField tfCantina;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXTextField tfTipo;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXTextField tfQta;

    @FXML
    private void loadFirst(){

    }

    @FXML
    void checkInventario() {
        btnNext.setText("Next");
        ArrayList<Vino> list;
        APIC a = new APIC("vino");
        String[] strings = {};
        ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
        try {
            list = a.select(strings, clausolas).toList(Vino.class);
            ListIterator<Vino> listIterator = list.listIterator();
            Vino start;
            start = listIterator.next();
            tfNome.setText(start.getNome());
            tfAnnata.setText(String.valueOf(start.getAnno()));
            tfTipo.setText(start.getTipo());
            tfCantina.setText(String.valueOf(start.getIdCantina()));
            btnNext.setOnAction(event1 -> {
                System.out.println("Sono stato premuto");
                System.out.println(tfQta.getText().toString());
                if (tfQta.getText().isEmpty() || Integer.valueOf(tfQta.getText()) < 0) {
                    Utility.createErrorWindow("Inserisci un valore");
                    System.out.println("Errore");
                } else {
                    Vino vino = start;
                    System.out.println("Vino in considerazione: "+ vino.toString());
                    if (Integer.valueOf(tfQta.getText()) == vino.getQta()) {
                        System.out.println("Corretto, passo al prossimo");
                        tfNome.setText(vino.getNome());
                        tfAnnata.setText(String.valueOf(vino.getAnno()));
                        tfTipo.setText(vino.getTipo());
                        tfCantina.setText(String.valueOf(vino.getIdCantina()));
                        tfQta.setText("");
                        if(listIterator.hasNext())
                            vino=listIterator.next();
                        else{
                            Utility.createSuccessWindow("Hai finito i vini da controllare");
                        }
                    }else{
                        Utility.createErrorWindow("Il numero inserito non combacia, doveva essere: " + vino.getQta().toString());
                        tfQta.setText("");
                    }
                }
            });
        } catch (Exception e) {
            e.getMessage();
        }/*
        if (start) {
            if (tfQta.getText().isEmpty()) {
                Utility.createErrorWindow("Inserisci un numero valido");
            } else {
                String qtaString = tfQta.getText();
                try {
                    Integer qta = Integer.valueOf(qtaString);
                    if (qta < 0)
                        Utility.createErrorWindow("Inserire numero positivo");
                    else {
                        if (qta != vino.getQta())
                            tfQta.setText(null);
                    }
                } catch (Exception stringInput) {
                    Utility.createErrorWindow("Inserire un numero");
                }
            }
        }else{
            start=true;
        }*/
    }
}
