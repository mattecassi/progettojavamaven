//--module-path C:\Users\bizza\Desktop\ApplicationOOp\FX\javafx-sdk-12.0.2\lib --add-modules javafx.controls,javafx.fxml

package Client;
import API.APIC;
import Client.controller.Ricerca;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Fornitore;
import Utils.APIReturn;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main2 extends Application {
    public static Stage window;
    private Pane newLoadedPane, mainPane;
    FXMLLoader loader = new FXMLLoader();
    @FXML
    Button btn;

    public Stage createStage(String fxml, String title) throws Exception{


        System.out.println("Sono una prova");

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

    public  void changeScene(String fxml) throws Exception{
        newLoadedPane = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(newLoadedPane);
        window.setScene(scene);
        window.show();
        }

    private Stage primaryStage;
    static public BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dalla Gioconda");
        //  TODO RITAGLIARE L'ICONA
        primaryStage.getIcons().add(new Image("/asset/box.png"));
        initRootLayout();
        showPersonOverview("/view/logo.fxml");
    }

    void prova(){
        System.out.println("Sono prova");
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            System.out.println("SONO QUI" + System.getProperty("user.dir"));
            System.out.println(Main2.class.getResource("/view/layer.fxml"));

            loader.setLocation(Main2.class.getResource("/view/layer.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void change(){
        showPersonOverview("/view/aggiorna.fxml");
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview(String fxml) {
        try {
            // Load person overview.
            loader.setLocation(Main2.class.getResource(fxml));
            AnchorPane personOverview = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //Enter data using BufferReader
        /*Vino bottiglia;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ListaVini listaVini = new ListaVini();
        bottiglia = new Vino("Verdicchio","Bianco","Boh","CasaBizzarri","AH","Italia","Marche",10,1998);
        listaVini.addVino(bottiglia);
        for (int i=0;i<1;i++){
            bottiglia = new Vino();
            System.out.println("Inserisci nome");
            bottiglia.setNome(reader.readLine());
            System.out.println("Inserisci uvaggio");
            bottiglia.setUvaggio(reader.readLine());
            System.out.println("Inserisci cantina");
            bottiglia.setCantina(reader.readLine());
            System.out.println("Inserisci stato");
            bottiglia.setStato(reader.readLine());
            System.out.println("Inserisci regione");
            bottiglia.setRegione(reader.readLine());
            System.out.println("Inserisci tipo");
            bottiglia.setTipo(reader.readLine());
            System.out.println("Inserisci qta");
            bottiglia.setQta(reader.read());
            System.out.println("Inserisci annata");
            bottiglia.setAnnata(reader.read());
            listaVini.addVino(bottiglia);


        }*/




        launch(args);
    }
}
