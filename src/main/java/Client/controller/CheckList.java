package Client.controller;

import Client.Main2;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CheckList implements Initializable {

    Main2 main=new Main2();

    @FXML
    private AnchorPane menuAP;

    @FXML
    private GridPane gridPaneContainer;

    @FXML
    private JFXButton btnAddCompito;

    private VBox v = new VBox(10);
    private VBox vSettimana = new VBox(10);



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
    }

    public void load(){
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


        ArrayList<Compito> lista = new ArrayList<>();
        try {
            lista = Compito.getCompitiNonSvoltiQuestaSettimana();
        }catch (Exception e){
            System.out.println(e);
        }
//        System.out.println("Stampa lista");
        Integer tipo;
        for (Compito c: lista) {
//            System.out.println(c);
            tipo = c.getTipoCompito();
            switch (tipo){
                case 1:{

                    if (LocalDate.parse(c.getDataCompitoOff()).equals(LocalDate.now()))
                        v.getChildren().add(getCardForTask(c,tipo));
                    else {
                        c.setDescrizione(c.getDescrizione() + " " + c.getDataCompitoOff());
                        vSettimana.getChildren().add(getCardForTask(c, tipo));
                    }
                }break;
                case 2:{
                    v.getChildren().add(getCardForTask(c,tipo));
                }break;
                case 3:{
                    c.setDescrizione(c.getDescrizione() + " " + Utility.convertNumberIntoDayOfTheWeek(c.getDow()));
                    vSettimana.getChildren().add(getCardForTask(c,tipo));
                }break;
            }
        }




        gridPaneContainer.add(fin,0,1);
        gridPaneContainer.add(finSettimana,1,1);

    }

    private JFXCheckBox getCardForTask(Compito c,Integer tipo){

//        System.out.println(c);
        JFXCheckBox checkBox = new JFXCheckBox(c.getDescrizione());
        checkBox.setId(c.getID().toString());
        checkBox.setAlignment(Pos.CENTER);
        checkBox.getStylesheets().add("/css/checkList.css");
        checkBox.getStyleClass().add("custom-jfx-check-box card");



        checkBox.setOnAction((event) -> {
            boolean selected = checkBox.isSelected();
            System.out.println("Mi sto riferendo a " + c);
            try {
                c.svolgi();

                switch (tipo){
                    case 1:{

                        if (LocalDate.parse(c.getDataCompitoOff()).equals(LocalDate.now()))
                            v.getChildren().remove(checkBox);
                        else
                            vSettimana.getChildren().remove(checkBox);
                    }break;
                    case 2:{
                        v.getChildren().remove(checkBox);
                    }break;
                    case 3:{
                        vSettimana.getChildren().remove(checkBox);
                    }break;
                }
            }catch (Exception e){
                Utility.createErrorWindow("Errore durante lo svolgimento del compitino");
                System.out.println(e.getMessage());
            }
            System.out.println("CheckBox Action (selected: " + selected + ")");
        });

        return checkBox;

    }


    @FXML
    void addCompito(ActionEvent event) {

        NuovoCheckList nuovoCheckList = new NuovoCheckList(this);
        nuovoCheckList.showStage();
    }


}
