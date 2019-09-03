import API.APIC;
import Models.Operazione;
import Models.Vino;
import com.google.gson.Gson;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args){


        try {
            APIC a = new APIC("vino");
            Vino v = a.get(1,Vino.class);
            System.out.println(v.toString());
        }catch (Exception e){
            System.out.println("Errore" + e.getMessage());
        }
        /*java.util.Date date=new java.util.Date();
        System.out.println(date);
*/
    }

}
