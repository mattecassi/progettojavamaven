package Client.controller;

import Client.excel.*;
import Models.Operazione;
import Utils.Utility;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Vendita {


    @FXML
    public void dragOver(DragEvent event) {
       // System.out.println("Entered");
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
    }

    @FXML
    public void dragDropped(DragEvent event) {
        try {

            List<File> files = event.getDragboard().getFiles();
        /*Image img = new Image(new FileInputStream(files.get(0)));
        imgView.setImage(img);*/
            File file = files.get(0);
            //System.out.println(file.getName());
//      System.out.println("Stampa file" + FilenameUtils.getExtension(file.toString()));

            Operazione o;
            //controllo che sia un file xls
            if (FilenameUtils.getExtension(file.toString()).equalsIgnoreCase("xls")){


                ReadExcel readExcel = new ReadExcel();
                readExcel.setInputFile(file.getAbsolutePath());
                Sheet sheet = readExcel.read();
                boolean printValue = false;
                int counterAttributi=0;


                Cell celldata = sheet.getCell("C1");//ottengo la cella
                System.out.println(celldata.getContents());

                o = new Operazione();
                o.setData_operazione(celldata.getContents());
                o.setTipoOperazione(2); //2 sarà la vendita


                for (int j = 0; j < sheet.getRows(); j++) { //scorro le righe


                    for (int i = 0; i < sheet.getColumns(); i++) { //scorro le colonne
                        Cell cell = sheet.getCell(i, j);//ottengo la cella

                        if(printValue) { //questo valore sarà a true se solo se l'ultima cella letta ha come contenuto "Importo"
                            switch (counterAttributi){

                                case 0: //codice vino
                                {
//                                    System.out.println(Integer.valueOf(cell.getContents()));
                                    o.setIdvino(Integer.valueOf(cell.getContents()));

                                }
                                    break;
                                case 1:
                                    o.setDescrizione(cell.getContents()); //descrizione del vino//
                                    break;
                                case 2:
                                   o.setSconto(Double.valueOf(cell.getContents()));
                                    break;
                                case 3:
                                    o.setQta(Integer.valueOf(cell.getContents()));
                                    break;
                                case 4: {
                                    o.setImporto(Double.valueOf(cell.getContents()));

                                    //IO SUPPONGO CHE, NEL MOMENTO IN CUI IO VADO AD EFFETTURARE L'INSERIMENTO
                                    //IL VINO ESISTA GIA' NEL DB, NEL CASO GLI DO UN MSG D'ERRORE
                                    System.out.println("PRE INSERT" + o.toString());
                                    try {
//                                        o.insert();
                                    }catch (Exception e){
                                        Utility.createErrorWindow("Errore durante l'inserimento di un operazione: " + e.getMessage() + "\n Operazione:" + o.toString());
                                    }

                                }break;
                                default:
                                    // System.out.println(excelFile.toString());
                                    if(cell.getContents().isEmpty()) {
                                        //System.out.println(excelFile.toString());
                                        //System.out.println("Ho finito di leggere");
                                        printValue = false;
                                    }
                                    else {
                                        counterAttributi=0;
                                        //System.out.println(excelFile.toString());
                                        o.setIdvino(Integer.valueOf(cell.getContents()));
                                        break;
                                    }
                            }
                            counterAttributi++;
                        }

                        if(cell.getContents().equalsIgnoreCase("importo")) {
                            printValue = true;
                            //da qui in poi leggo
                        }
                    }
                }
            /*BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null)
                System.out.println(st);
            */
            }
            else {
                Utility.createErrorWindow("File non corretto");
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
            Utility.createErrorWindow(ioe.getMessage());
        }

    }

}
