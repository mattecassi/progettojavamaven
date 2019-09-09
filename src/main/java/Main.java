import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Fornitore;
import Models.Operazione;
import Models.TipoVino;
import Models.Vino;
import Utils.Utility;
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

            String[] arr = {"nome"};
            ArrayList<Clausola> l = new ArrayList<>();
            l.add(new Clausola("nome"," LIKE ","%Fornitore%"));
//            for (Fornitore f : Fornitore.getFornitoriRappresentanti(arr,l)){
//                System.out.println(f);
//            }
            System.out.println("POST");
            Utility.loadDataForCmb(Fornitore.getTableFornitoriRappresentanti(),"nome","Fornitore",Fornitore.class);




        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Errore " + e.getMessage());
        }
    }

}
