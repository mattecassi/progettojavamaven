import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;

public class ProvaCreazioneFileExcel {

    //location del file
    private static final String EXCEL_FILE_LOCATION = "D:\\proveFileExcel\\MyFirstExcel.xls";


    /**
     * Funzione che mi permette di inserire all'interno della riga e della colonna specificata
     * il testo da me desiderato
     * @param excelSheet
     * @param column
     * @param row
     * @param msg
     * @throws WriteException
     */
    private void addLabel(WritableSheet excelSheet,Integer column, Integer row, String msg) throws WriteException {
        Label label = new Label(column, row, msg);
        excelSheet.addCell(label);
    }

    public static void main(String[] args) {

        //1. Create an Excel file
        WritableWorkbook myFirstWbook = null;
        try {

            myFirstWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));

            // create an Excel sheet
            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

            // add something into the Excel sheet
            Label label = new Label(0, 0, "Test Count");
            excelSheet.addCell(label);

            label = new Label(1, 0, "Result");
            excelSheet.addCell(label);

            label = new Label(1, 1, "Passed");
            excelSheet.addCell(label);


            label = new Label(1, 2, "Passed 2");
            excelSheet.addCell(label);

            //per salvare devo fare questo
            myFirstWbook.write();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException ex){
           ex.printStackTrace();
        }
        finally {

            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException ex){
                    ex.printStackTrace();
                }
            }


        }

        }
}
