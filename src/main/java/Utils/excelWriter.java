package Utils;

import API.APIC;
import ClientUtils.Clausola;
import Models.Operazione;
import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class excelWriter {

    private WritableWorkbook writableWorkbook;
    private String filePath;
    private WritableSheet writableSheet;
    private static final Integer MONTHLY = 1;
    private static final Integer ANNUAL = 2;




    public excelWriter(String filePath) throws IOException {
        this.filePath = filePath;
        this.writableWorkbook = Workbook.createWorkbook(new File(getFilePath()));
        this.writableSheet = this.writableWorkbook.createSheet("Sheet 1",0);
    }

    /**
     * Funzione che mi permette di inserire all'interno della riga e della colonna specificata
     * il testo da me desiderato
     * @param column
     * @param row
     * @param msg
     * @throws WriteException
     */
    private void addLabel(Integer column, Integer row, String msg) throws WriteException {
        Label label = new Label(column, row, msg);
        this.writableSheet.addCell(label);
    }
    private void addLabelFormat(Integer column, Integer row, String msg,WritableCellFormat cellFormat) throws WriteException {
        Label label = new Label(column, row, msg,cellFormat);
        this.writableSheet.addCell(label);

    }


    /**
     * Funzione che si occupa di generare un file excel
     * contenente lo storico compreso tra data inizio e data fine
     *
     * @param dataInizio 19/11/2019
     * @param dataFine  20/11/2020
     * @throws Exception
     */
    public void createExcelStorico(String dataInizio,String dataFine)throws Exception{

        //todo scrivere codice per ottenere il periodo che vogliamo analizzare
        String[] arr ={};
        List<Clausola> l = new ArrayList<>();
        l.add(new Clausola("data_operazione",">=",dataInizio + " 00:00:00"));
        l.add(new Clausola("data_operazione","<=",dataFine + " 23:59:59"));


        //segno data inizio e data fine
        System.out.println(dataInizio + " " + dataFine);
        addLabel(0,1,"Data INIZIO:");
        addLabel(1,1,dataInizio);
        addLabel(2,1,"Data fine:");
        addLabel(3,1,dataFine);



        //formato celle di indendazione
        WritableCellFormat cFormat = new WritableCellFormat();
        WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
        cFormat.setFont(font);

        addLabelFormat(0,2,"Codice",cFormat);
        addLabelFormat(1,2,"Descrizione",cFormat);
        addLabelFormat(2,2,"Sconto",cFormat);
        addLabelFormat(3,2,"Qta",cFormat);
        addLabelFormat(4,2,"Importo",cFormat);

        APIC apic = new APIC("operazione");
        int count = 3;
        for (Operazione operazione: apic.select(arr,l).toList(Operazione.class)){
//            System.out.println(operazione);
//            if (operazione.getQta() != null && operazione.getSconto() != null && operazione.getDescrizione()!= null) {
                addLabel(0, count, operazione.getIdvino().toString());
                addLabel(1, count, operazione.getDescrizione());
                try {
                    addLabel(2, count, operazione.getSconto().toString());
                }catch (Exception e){
                    addLabel(2, count, "0");
                }
                try {
                    addLabel(3, count, operazione.getQta().toString());
                }catch (Exception e){
                    addLabel(3, count, "Non nota");
                }
                try {
                    addLabel(4, count, operazione.getImporto().toString());
                }catch (Exception e){
                    addLabel(4, count, "Non noto");
                }

                count++;
//            }
        }

        for(int x=0;x<5;x++)
        {
            CellView cell = this.writableSheet.getColumnView(x);
            cell.setAutosize(true);
            this.writableSheet.setColumnView(x, cell);
        }



        this.writableWorkbook.write();
        this.writableWorkbook.close();
    }


    public WritableWorkbook getWritableWorkbook() {
        return writableWorkbook;
    }

    public void setWritableWorkbook(WritableWorkbook writableWorkbook) {
        this.writableWorkbook = writableWorkbook;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public WritableSheet getWritableSheet() {
        return writableSheet;
    }

    public void setWritableSheet(WritableSheet writableSheet) {
        this.writableSheet = writableSheet;
    }
}
