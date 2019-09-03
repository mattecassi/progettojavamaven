package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.Fornitore;
import Models.TipoVino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class NuovoFornitore {
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXTextField tfMail;

    @FXML
    private JFXTextField tfQtaMin;

    @FXML
    private JFXTextField tfQtaMax;

    @FXML
    private JFXTextField tfTelefono;

    @FXML
    void insertElement(ActionEvent event) {
        if(tfNome.getText().isEmpty() || tfMail.getText().isEmpty() || tfQtaMin.getText().isEmpty() || tfQtaMax.getText().isEmpty() || tfTelefono.getText().isEmpty()){
            System.out.println("Provo");
            Utility.createErrorWindow("Inserisci tutti i campi");
        }else{
            //Button btnPressed = (Button)event.getSource();
            Fornitore fornitore = new Fornitore();
            try {
                fornitore.setNome(tfNome.getText());
                fornitore.setMail(tfMail.getText());
                fornitore.setQta_max(Integer.valueOf(tfQtaMax.getText()));
                fornitore.setQta_min((Integer.valueOf(tfQtaMin.getText())));
                fornitore.setTelefono(tfNome.getText());
            }catch (Exception e){
                Utility.createErrorWindow("Errore in " + e.getMessage());
            }

            try {
                boolean presente = false;
                APIC a = new APIC("fornitore");
                APIReturn ret;
                String[] strings={"nome"};
                ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
                ret = a.select(strings,clausolas);
                ArrayList<Fornitore> list = ret.toList(Fornitore.class);
                for(Fornitore current: list){
                    System.out.println(current.toString());
                    if(current.getNome().equalsIgnoreCase(fornitore.getNome())){
                        presente=true;
                        Utility.createErrorWindow("Gia presente");
                        break;
                    }
                }
                if(!presente){
                    fornitore.insert();
                    tfNome.setText(null);
                    tfQtaMax.setText(null);
                    tfQtaMin.setText(null);
                    tfTelefono.setText(null);
                    tfMail.setText(null);
                }
            }catch (Exception e){
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }
}
