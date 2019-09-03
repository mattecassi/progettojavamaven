package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.TipoVino;
import Models.Vino;
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
            nuovo.setTipo(tfTipoVino.getText());
            //ordine.setidFornitore(idFornitore);
            try {
                String[] strings={"tipo"};
                ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
                ret = a.select(strings,clausolas);
                ArrayList<TipoVino> list = ret.toList(TipoVino.class);
                for(TipoVino str: list){
                    if(str.getTipo().equalsIgnoreCase(tfTipoVino.getText())){
                        presente=true;
                        Utility.createErrorWindow("Gia presente");
                        break;
                    }
                }
                if(!presente)
                    nuovo.insert();
                tfTipoVino.setText(null);
            }catch (Exception e){
                Utility.createErrorWindow(e.getMessage());
            }
        }
    }

}
