
import API.APIC;
import ClientUtils.Clausola;
import Models.Compito;
import Models.Vino;
import Utils.Utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){


        try {


            APIC apic = new APIC("compito");
            for(Compito c: apic.select().toList(Compito.class)){
                c.delete();
            }

            ArrayList<Compito> list = new ArrayList<>();
            for (int count = 1; count < 5; count++){

            list.add(new Compito("GIORNALIERO " + count ));
            list.add(new Compito("SETTIMANALE " + count,0));
            list.add(new Compito("OCCASIONALE " + count, LocalDate.now().plusDays(3L)));
            }
            for (Compito c: list)
                c.insert();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Errore " + e.getMessage());
        }
    }

}
