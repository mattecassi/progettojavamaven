package Client.controller;

import API.APIC;
import Client.Main2;
import Models.Vino;
import Utils.APIReturn;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Nuovo{

    @FXML
    private JFXTextField tfNome;

    @FXML
    private JFXButton btnInserisci,btnFornitore,btnRappresentante,btnEnoteca,btnTipoVino;

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
    private JFXTextField tfCodice,tfPrezzoVendita,tfCosto;

    //TODO controllare che qta e annata siano dei numeri
    @FXML
    void inserisciElement(){
        APIC a = new APIC("vino");
        APIReturn ret;
        //|| tfCodice.getText().isEmpty()
        if (tfCosto.getText().isEmpty() || tfPrezzoVendita.getText().isEmpty()  || tfAnnata.getText().isEmpty() || tfTipo.getText().isEmpty() || tfQta.getText().isEmpty() || tfFornitore.getText().isEmpty() || tfCantina.getText().isEmpty() || tfNome.getText().isEmpty()){
            Utility.createErrorWindow("Inserisci tutti i campi");
        } else{

            String nome = tfNome.getText();
            Integer idCantina = Integer.valueOf(tfCantina.getText());
            Integer idFornitore = Integer.valueOf(tfFornitore.getText());
            Integer qta = Integer.valueOf(tfQta.getText());
            Integer annata = Integer.valueOf(tfAnnata.getText());
            String tipo = tfTipo.getText();
            Double costo = Double.valueOf(tfCosto.getText());
            Double prezzoVendita = Double.valueOf(tfPrezzoVendita.getText());
            Integer codice = Integer.valueOf(tfCodice.getText());

            tfTipo.setText(null);
            tfCosto.setText(null);
            tfPrezzoVendita.setText(null);
            tfCodice.setText(null);
            tfCantina.setText(null);
            tfFornitore.setText(null);
            tfQta.setText(null);
            tfAnnata.setText(null);
            tfNome.setText(null);


            Vino ordine = new Vino();
            ordine.setNome(nome);
            ordine.setAnno(annata);
            ordine.setCosto(costo);
            ordine.setIdCantina(idCantina);
            ordine.setTipo(tipo);
            ordine.setQta(qta);
            ordine.setPrezzoVendita(prezzoVendita);
            //ordine.setidFornitore(idFornitore);
            try {
                ordine.insert();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println(ordine.toString());
        }
    }

    @FXML
    private void changeScene(Event event) throws Exception {
        Main2 main = new Main2();
        Stage stage;

        FXMLLoader loader = new FXMLLoader();
        Button btnPressed = (Button)event.getSource();
        switch (btnPressed.getId()){
            case "btnFornitore":
                stage = main.createStage("/view/nuovoFornitore.fxml", "Nuovo Fornitore");
                break;
            case "btnCantina":
                stage = main.createStage("/view/nuovoCantina.fxml", "Nuova Cantina");
                break;
            case "btnTipoVino":
                stage = main.createStage("/view/nuovoTipoVino.fxml", "Nuovo Tipo Vino");
                break;
        }

    }
}
