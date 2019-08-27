package Client.excel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {

    private String inputFile;
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public Sheet read() throws IOException {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines
            return sheet;

        } catch (BiffException e) {
            e.printStackTrace();

        }
        return null;
    }
}
