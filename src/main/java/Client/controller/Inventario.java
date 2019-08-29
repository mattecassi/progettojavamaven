package Client.controller;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Inventario{

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
    void checkInventario(ActionEvent event) {
        if (tfQta.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci un numero valido");

        }else {
            String qtaString = tfQta.getText();
            try{
            Integer qta = Integer.valueOf(qtaString);
            if (qta < 0)
                Utility.createErrorWindow("Inserire numero positivo");
            else{
                tfQta.setText(null);
            }
             }catch (Exception stringInput){
                Utility.createErrorWindow("Inserire un numero");
            }finally {
                tfQta.setText(null);
            }
        }
    }
}
