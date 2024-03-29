package Client.controller;

import API.APIC;
import Client.excel.*;
import Models.Operazione;
import Models.Vino;
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
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

            File file = files.get(0);
            Operazione o;
            //controllo che sia un file xls
            if (FilenameUtils.getExtension(file.toString()).equalsIgnoreCase("xls")){


                ReadExcel readExcel = new ReadExcel();
                readExcel.setInputFile(file.getAbsolutePath());
                Sheet sheet = readExcel.read();
                boolean printValue = false;
                boolean errore = false; //in caso di errore non inserisco le operazioni

                Cell celldata = sheet.getCell("C1");//ottengo la cella contenente la data
                APIC a = new APIC("vino");
                ArrayList<Operazione> listOperazioni = new ArrayList<>();

                for (int j = 0; j < sheet.getRows(); j++) { //scorro le righe
                    if (!printValue) {
                        Cell cell = sheet.getCell(0, j);//ottengo la cella
                        if (cell.getContents().equalsIgnoreCase("codice")) {
                            printValue = true; //da qui in poi leggo
                        }
                    }else{
                        if (sheet.getCell(0,j).getContents().isEmpty())
                            break;
                        o = new Operazione();
                        //gestiona data con conversioni
                        String data = celldata.getContents();
                        DateTimeFormatter dtf;
                        if (data.startsWith("0"))
                             dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm.ss");
                        else
                            dtf = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm.ss");
                        LocalDateTime dt = LocalDateTime.parse(data, dtf);
                        o.setData_operazione(dt);
                        o.setTipoOperazione(2); //2 sarà la vendita
                        o.setIdvino(Integer.valueOf(sheet.getCell(0, j).getContents()));
                        try {
                            Vino v = a.get(o.getIdvino(),Vino.class); //controllo esistenza vino con quell'id
                            o.setDescrizione(sheet.getCell(1, j).getContents()); //descrizione del vino//
                            o.setSconto(Double.valueOf(sheet.getCell(2, j).getContents().replace(",",".")));
                            o.setQta(Integer.valueOf(sheet.getCell(3, j).getContents()));
                            o.setImporto(Double.valueOf(sheet.getCell(4, j).getContents().replace(",",".")));

                            if (v.getQta() - o.getQta() < 0)
                                Utility.createWarningWindow("Attento, possiedi " +v.getQta() + "bottiglie di" +
                                         " " + v.getNome() +
                                        " e ne vuoi vendere " + o.getQta()
                                + "Controlla l'inventario perchè potrebbero esserci degli errori");

                            listOperazioni.add(o);
                        }catch (Exception e){
                            //add var errore
                            errore = true;
                            Utility.createErrorWindow(e.getMessage());
                        }
                        if (errore)
                            break;
                    }
                }

                if (!errore) {
                    //Inserisco tutte le operazioni quando sono sicuro che dovrebbe essere tutto ok
                    for (Operazione operazione : listOperazioni) {
                        System.out.println(operazione.toString());
                        try {
                            operazione.insert(); //inserimento operazione
                        } catch (Exception e) {
                            Utility.createErrorWindow("Errore durante operazione inserimento:\n" + e.getMessage());
                        }
                    }

                        Utility.createSuccessWindow("AGGIORNAMENTO AVVENUTO CON SUCCESSO");

                }

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
