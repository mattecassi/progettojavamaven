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
    private Button btnAdd;

    @FXML
    private void insertElement(){
        APIC a = new APIC("tipo_vino");
        APIReturn ret;
        boolean presente=false;
        if (tfTipoVino.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi");
        } else{
            TipoVino nuovo = new TipoVino();
            nuovo.setTipo(Utility.replaceAllDeniedChar(tfTipoVino.getText()));
            //ordine.setidFornitore(idFornitore);
            try {
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
