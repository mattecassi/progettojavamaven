package Client.controller;

import Client.Main2;
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

public class Layer {
    //private Button btnRicerca;

    @FXML
    public void changeScene(Event event) throws Exception{
        try {
            // Load person overview.
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
                    break;
            }
            if(!done){
                if(btnPressed.getId().equalsIgnoreCase("btnNuovo")){
                    BorderPane newLoadedBp = (BorderPane)loader.load();
                    Main2.rootLayout.setCenter(newLoadedBp);
                }else {
                    AnchorPane newLoadedAp = (AnchorPane) loader.load();
                    Main2.rootLayout.setCenter(newLoadedAp);
                }// Set person overview into the center of root layout.

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.consume();
    }

}
