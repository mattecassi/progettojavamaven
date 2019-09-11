package Client.controller;

import API.APIC;
import Client.Main2;
import ClientUtils.Clausola;
import Models.Vino;
import Utils.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Layer {
    //private Button btnRicerca;

    @FXML
    public void changeScene(Event event) throws Exception{
        try {
            boolean done=false;
            FXMLLoader loader = new FXMLLoader();
            Button btnPressed = (Button)event.getSource();
            switch (btnPressed.getId()){
                case "btnRicerca":
                    loader.setLocation(getClass().getResource("/view/ricerca.fxml"));
                    break;
                case "btnAggiorna":
                    loader.setLocation(getClass().getResource("/view/vendita.fxml"));
                    break;
                case "btnCheckList":
                    loader.setLocation(getClass().getResource("/view/checkList.fxml"));
                    break;
                case "btnNuovo":
                    loader.setLocation(getClass().getResource("/view/Nuovo.fxml"));
                    break;
                case "btnInventario":
                    Main2 main = new Main2();
                    Stage stage = main.createStage("/view/inventario.fxml", "Inventario");
                    done=true;//TODO NON SO COSA SIA
//                    Inventario inventario = new Inventario();
//                    ArrayList<Vino> list;
//                    APIC a = new APIC("vino");
//                    String[] strings = {};
//                    ArrayList<Clausola> clausolas = new ArrayList<Clausola>();
//                    inventario.load(a.select(strings,clausolas).toList(Vino.class));
                    break;
            }
            if(!done){
                if(btnPressed.getId().equalsIgnoreCase("btnNuovo")){
                    BorderPane newLoadedBp = (BorderPane)loader.load();
                    Main2.rootLayout.setCenter(newLoadedBp);
                }else {
                    AnchorPane newLoadedAp = (AnchorPane) loader.load();
                    Main2.rootLayout.setCenter(newLoadedAp);

                    //Eseguo questo passaggio per eseguire il loading della tabella
                    //ogni volta che apro quella pagina
                    if (loader.getController() instanceof Ricerca) {
                        ((Ricerca) loader.getController()).loadAllTbl();
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.consume();
    }

}
