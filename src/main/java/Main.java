import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Fornitore;
import Models.Operazione;
import Models.Vino;
import Utils.excelWriter;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){


        try {

            APIC a = new APIC("operazione");
            String[] col = {};
            List<Clausola> lc = new ArrayList<>();
            lc.add(new Clausola("descrizione","=","Prova data"));
            for (Operazione operazione: a.select(col,lc).toList(Operazione.class)){
                System.out.println(operazione);
            }

            excelWriter excelWriter = new excelWriter("D:\\proveFileExcel\\provamia.xls");
            excelWriter.createExcelStorico("01/08/2019","31/08/2019");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Errore " + e.getMessage());
        }
    }

}
