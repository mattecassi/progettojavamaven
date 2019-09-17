package Client.controller;

import Client.Main2;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Layer {
    //IN BASE AL BOTTONO CHE CLICCO, CARICO NEL CENTRO DELLA BORDERPANE UNA SCENA DIVERSA
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
                case "btnInventario": //IN QUESTO CASO CREO UN NUOVO STAGE
                    Main2 main = new Main2();
                    Stage stage = main.createStage("/view/inventario.fxml", "Inventario");
                    done=true;//TODO NON SO COSA SIA
                    break;
            }
            if(!done){ //IMPOSTO IL RELATIVO FILE
                    AnchorPane newLoadedAp = (AnchorPane) loader.load();
                    Main2.rootLayout.setCenter(newLoadedAp);
//                    if (loader.getController() instanceof Ricerca) {
//                        ((Ricerca) loader.getController()).loadAllTbl();
//                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        event.consume();
    }

}
