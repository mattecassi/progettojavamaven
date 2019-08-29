package Client.controller;

import Client.excel.*;
import Utils.Utility;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Vendita {
    @FXML
    public void dragOver(DragEvent event) {
        System.out.println("Entered");
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
    }

    @FXML
    public void dragDropped(DragEvent event) throws IOException {
        List<File> files = event.getDragboard().getFiles();
        /*Image img = new Image(new FileInputStream(files.get(0)));
        imgView.setImage(img);*/
        File file = files.get(0);
        //System.out.println(file.getName());
        ExcelFile excelFile = new ExcelFile();
        if (file.getName().compareToIgnoreCase("Stampa Articoli Periodo.xls")==0){
            ReadExcel readExcel = new ReadExcel();
            System.out.println(file.getAbsolutePath());
            readExcel.setInputFile(file.getAbsolutePath());
            Sheet sheet = readExcel.read();
            boolean printValue = false;
            int counterAttributi=0;
            for (int j = 0; j < sheet.getRows(); j++) {
                for (int i = 0; i < sheet.getColumns(); i++) {
                    Cell cell = sheet.getCell(i, j);
                    CellType type = cell.getType();
                    if(printValue) {
                        // System.out.println("Counter = " + counterAttributi);
                        switch (counterAttributi){
                            case 0:
                                excelFile.setId(Integer.valueOf(cell.getContents()));
                                break;
                            case 1:
                                excelFile.setNome(cell.getContents());
                                break;
                            case 2:
                                excelFile.setSconto(Integer.valueOf(cell.getContents()));
                                break;
                            case 3:
                                excelFile.setQta(Integer.valueOf(cell.getContents()));
                                break;
                            case 4:
                                excelFile.setImporto(Integer.valueOf(cell.getContents()));
                                System.out.println(excelFile.toString());
                                break;
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
                                    excelFile.setId(Integer.valueOf(cell.getContents()));
                                    break;
                                }
                        }
                        counterAttributi++;
                    }
                    if(cell.getContents().compareToIgnoreCase("importo")==0) {
                        System.out.println("inizio a leggere");
                        printValue = true;
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
    }

}
