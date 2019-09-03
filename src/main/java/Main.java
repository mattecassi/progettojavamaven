import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Operazione;
import Models.Vino;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){


        try {

            APIC a = new APIC("vino");
            Vino vino = a.get(1122,Vino.class);
            System.out.println(vino);
            String[] arr = {};
//            List<UpdateElem> lue = new ArrayList<>();
//            lue.add(new UpdateElem("qta","10"));
            List<Clausola> l = new ArrayList<>();
//            a.update(lue,l);

            for (Vino v: a.select(arr,l).toList(Vino.class)){
                System.out.println(v);
            }



        }catch (Exception e){
            System.out.println("Errore" + e.getMessage());
        }
        /*java.util.Date date=new java.util.Date();
        System.out.println(date);
*/
    }

}
