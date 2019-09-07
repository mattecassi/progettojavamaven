import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Fornitore;
import Models.Operazione;
import Models.TipoVino;
import Models.Vino;
import Utils.excelWriter;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){


        try {

            for (Operazione o: new APIC("operazione").select().toList(Operazione.class)){
                System.out.println(o);
            }

            excelWriter excelWriter = new excelWriter("D:\\proveFileExcel\\provamia.xls");
            excelWriter.createExcelStorico(LocalDate.now(),LocalDate.now());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Errore " + e.getMessage());
        }
    }

}
