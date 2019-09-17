package Client.controller;

import Client.Main2;
import Client.controller.NuovoPackage.NuovoCheckList;
import Models.Compito;
import Utils.Utility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckList implements Initializable {

    Main2 main=new Main2();

    @FXML
    private AnchorPane menuAP;

    @FXML
    private GridPane GridPaneContainer;

    @FXML
    private JFXButton btnAddCompito;

    @FXML
    private JFXCheckBox JFXCheckBoxEliminazione;


    private VBox v = new VBox(10);
    private VBox vSettimana = new VBox(10);



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //CONFIFURO LE VARIE IMPOSTAZIONI GRAFICHE DEGLI SCROLL PANE E VBOX
        VBox fin = new VBox();
        v.paddingProperty().setValue(new Insets(10,10,10,10));
        VBox finSettimana = new VBox();
        vSettimana.paddingProperty().setValue(new Insets(10,10,10,10));
        v.setAlignment(Pos.CENTER);
        vSettimana.setAlignment(Pos.CENTER);

        ScrollPane sc = new ScrollPane();
        ScrollPane scSettimana = new ScrollPane();
        fin.getChildren().add(sc);
        finSettimana.getChildren().add(scSettimana);
        fin.setFillWidth(true);
        v.setFillWidth(true);
        sc.setFitToWidth(true);
        VBox.setVgrow(sc, Priority.ALWAYS);
        sc.setVmax(500);
        sc.setContent(v);

        finSettimana.setFillWidth(true);
        vSettimana.setFillWidth(true);
        scSettimana.setFitToWidth(true);
        VBox.setVgrow(scSettimana, Priority.ALWAYS);
        scSettimana.setVmax(500);
        scSettimana.setContent(vSettimana);



        try {
        Integer tipo;//conterrà il tipo di evento [giornaliero,settimanale,occasionale]
        //scorro tutti i compiti non svolti di questa settimana
        for (Compito c : Compito.getCompitiNonSvoltiQuestaSettimana()) {
//            System.out.println(c);
            tipo = c.getTipoCompito();
            switch (tipo){ //in base al tipo scelgo in quale VBOX buttarlo
                case 1:{

                    if (LocalDate.parse(c.getDataCompitoOff()).equals(LocalDate.now()))
                        v.getChildren().add(getCardForTask(c,v));
                    else {
                        c.setDescrizione(c.getDescrizione() + " DATA:" + c.getDataCompitoOff());
                        vSettimana.getChildren().add(getCardForTask(c,vSettimana));
                    }
                }break;
                case 2:{
                    v.getChildren().add(getCardForTask(c,v));
                }break;
                case 3:{
                    c.setDescrizione(c.getDescrizione() + " " + Utility.convertNumberIntoDayOfTheWeek(c.getDow()));
                    vSettimana.getChildren().add(getCardForTask(c,vSettimana));
                }break;
            }
        }
        }catch (Exception e){
            Utility.createErrorWindow("ERRORE DURANTE LA RICEZIONE DEI DATI|CREAZIONE VBOX\n"+e.getMessage());
        }

        //aggiungo gli elementi al gridPaneContainer
        GridPaneContainer.add(fin,0,1);
        GridPaneContainer.add(finSettimana,1,1);

    }


    /**
     * Funzione che, dato un compito,genera la combo box
     * e configura l'operazione di click soopra di essa
     *
     * @param c
     * @param hisContainer
     * @return
     */
    public JFXCheckBox getCardForTask(Compito c,VBox hisContainer){

//        System.out.println(c);
        JFXCheckBox checkBox = new JFXCheckBox(c.getDescrizione());
        checkBox.setId(c.getID().toString());
        checkBox.setAlignment(Pos.CENTER);
        checkBox.getStylesheets().add("/css/checkList.css");
        checkBox.getStyleClass().add("custom-jfx-check-box");



        //SETTO COSA FARE SUL CLICK
        checkBox.setOnAction((event) -> {
            System.out.println("Mi sto riferendo a " + c);
            try {
                //In base al fatto che la checkbox sia stata selezionata o meno
                //elimino o copio
                if (this.JFXCheckBoxEliminazione.isSelected())
                    c.delete();
                else
                    c.svolgi();
                //rimuovo l'elemento dal suo container
                hisContainer.getChildren().remove(checkBox);

            }catch (Exception e){
                Utility.createErrorWindow("Errore durante lo svolgimento|eliminazione del compitino");
                System.out.println(e.getMessage());
            }
        });

        return checkBox;

    }



    @FXML
    void addCompito(ActionEvent event) {

        NuovoCheckList nuovoCheckList = new NuovoCheckList(this);
        nuovoCheckList.showStage();
    }


    public VBox getV() {
        return v;
    }

    public VBox getvSettimana() {
        return vSettimana;
    }
}
