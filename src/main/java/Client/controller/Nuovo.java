package Client.controller;

import Client.AlertWindow;
import Client.Vino;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;

public class Nuovo{

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXButton btnInserisci;

    @FXML
    private JFXTextField tfTipo;

    @FXML
    private JFXTextField tfAnnata;

    @FXML
    private JFXTextField tfCantina;

    @FXML
    private JFXTextField tfQta;

    @FXML
    private JFXTextField tfFornitore;

    @FXML
    private JFXTextField tfStato,tfRegione,tfUvaggio;

    //TODO controllare che qta e annata siano dei numeri
    @FXML
    void inserisciElement(){
        if (tfStato.getText().isEmpty() || tfRegione.getText().isEmpty() || tfUvaggio.getText().isEmpty() || tfAnnata.getText().isEmpty() || tfTipo.getText().isEmpty() || tfQta.getText().isEmpty() || tfFornitore.getText().isEmpty() || tfCantina.getText().isEmpty() || tfNome.getText().isEmpty()){
            AlertWindow.createErrorWindow("Inserisci tutti i campi");
        } else{
            String nome = tfNome.getText();
            String cantina = tfCantina.getText();
            String fornitore = tfFornitore.getText();
            String qtaString = tfQta.getText();
            Integer qta = Integer.valueOf(qtaString);
            String annataString = tfAnnata.getText();
            Integer annata = Integer.valueOf(annataString);
            String tipo = tfTipo.getText();
            String stato = tfStato.getText();
            String regione = tfRegione.getText();
            String uvaggio = tfUvaggio.getText();
            tfTipo.setText(null);
            tfUvaggio.setText(null);
            tfRegione.setText(null);
            tfStato.setText(null);
            tfCantina.setText(null);
            tfFornitore.setText(null);
            tfQta.setText(null);
            tfAnnata.setText(null);
            tfNome.setText(null);
            Vino ordine = new Vino(nome,tipo,fornitore,cantina,uvaggio,stato,regione,qta,annata);

            System.out.println(ordine.toString());
        }
    }
}
