package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.TipoVino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class NuovoTipoVino {

    @FXML
    private JFXTextField tfTipoVino;

    @FXML
    private void insertElement(){
        //CONTROLLO CHE TUTTI I CAMPI SIANO STATI INSERITI
        if (tfTipoVino.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi");
        } else{
        //CARICO LA TABELLA DEI TIPI DI VINI, CONTROLLO CHE SIA STATO INSERITO UN NUMERO
            APIC a = new APIC("tipo_vino");
            TipoVino nuovo = new TipoVino();
            nuovo.setTipo(Utility.replaceAllDeniedChar(tfTipoVino.getText()));
            try {
                //CONTROLLO CHE NON ESISTA GIA NEL DB, POI INSERISCO E RESETTO IL TF
                String[] strings={"tipo"};
                ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
                clausolas.add(new Clausola("tipo", "LIKE", nuovo.getTipo()));
                if(a.select(strings,clausolas).toList(TipoVino.class).isEmpty())
                    nuovo.insert();
                else
                    Utility.createErrorWindow("Presente");
                tfTipoVino.setText(null);
                Utility.createSuccessWindow("Inserimento avvenuto con successo");
            }catch (Exception e){
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }

}
