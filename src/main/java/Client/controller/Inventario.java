package Client.controller;
import Client.AlertWindow;
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
            AlertWindow.createErrorWindow("Inserisci un numero valido");

        }else {
            String qtaString = tfQta.getText();
            try{
            Integer qta = Integer.valueOf(qtaString);
            if (qta < 0)
                AlertWindow.createErrorWindow("Inserire numero positivo");
            else{
                tfQta.setText(null);
            }
             }catch (Exception stringInput){
                AlertWindow.createErrorWindow("Inserire un numero");
            }finally {
                tfQta.setText(null);
            }
        }
    }
}
