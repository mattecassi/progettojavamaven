package Client.controller;

import Models.Vino;
import Utils.Utility;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AggiungiVino {
    private Integer qta;
    private Vino vino;
    @FXML
    private JFXTextField tfAggiungiQuantitaVino;

    public AggiungiVino(Vino vino) {
        this.vino = vino;
    }

    @FXML
    void aggiungiQta(ActionEvent event) {
        if(tfAggiungiQuantitaVino.getText().isEmpty() || Integer.valueOf(tfAggiungiQuantitaVino.getText()) <= 0){
            Utility.createErrorWindow("Inserisci Valore");
        }
        else{
            qta = Integer.valueOf(tfAggiungiQuantitaVino.getText());
            tfAggiungiQuantitaVino.setText("");
            System.out.println(qta);
            System.out.println(vino.getQta());
            Integer qtaCur = vino.getQta();
            vino.setQta(qtaCur+qta);
            System.out.println(vino.getQta());
        }
    }

}
