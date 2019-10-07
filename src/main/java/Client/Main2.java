

package Client;
import Utils.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;


public class Main2 extends Application {
    public static Stage window;
    FXMLLoader loader = new FXMLLoader();

    private Stage primaryStage;
    static public BorderPane rootLayout;


    //CREO LO STAGE INIZIALE, POI INIZIALIZZO IL LAYOUOT E CARICO IL PRIMO TAB
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dalla Gioconda");
        //  TODO RITAGLIARE L'ICONA
        primaryStage.getIcons().add(new Image("/asset/box.png"));
        try {
            initRootLayout();
            showTab("/view/logo.fxml");
        }catch (Exception e) {Utility.createErrorWindow(e.getMessage());}
    }

    //INIZIALIZZO PER LA PRIMA VOLTA IL ROOTLAYOUT COMPOSTO DA UNA BORDERPANE
    public void initRootLayout() throws  Exception{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main2.class.getResource("/view/layer.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //CARICO IL FILE FXML E LO IMPOSTO AL CENTRO DELLA BORDERPANE
    public void showTab(String fxml) throws Exception{
        try {
            loader.setLocation(Main2.class.getResource(fxml));
            AnchorPane personOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //PASSO IL PATH DEL FILE FXML, UNA STRINGA CONTENENTE IL TITOLO CHE VOGLIO DARE ALLO STAGE E CREA UNO STAGE DI TIPO APPLICATION MODAL
    public Stage createStage(String fxml, String title) throws Exception{
        Stage popUpStage;
        Scene popUpScene;
        Pane popUpPane;
        popUpStage = new Stage();
        popUpPane = FXMLLoader.load(getClass().getResource(fxml));
        popUpScene = new Scene(popUpPane);
        popUpStage.setScene(popUpScene);
        popUpStage.getIcons().add(new Image("/asset/box.png"));
        popUpStage.setTitle(title);
        popUpStage.initOwner(window);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.show();
        return popUpStage;
    }

    public static void main(String[] args){
        try {
            launch(args);
        }catch (Exception e){
            Utility.createErrorWindow(e.getMessage());
        }
    }
}
