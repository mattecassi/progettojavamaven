import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Fornitore;
import Utils.APIReturn;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){



        //prova inserimento
        Fornitore f = new Fornitore();
        f.setNome("PaolinoPaperino");
        f.setQta_min(1);
        f.setQta_max(3);
        f.setMail("bizza251@gmail.com");
        APIReturn  retf = f.insert();
        System.out.println(retf.getStatus() + "\n" + retf.getData());


        //prova update
        f.setMail("provaUp@gmail.com");
        f.setNome("provaUp");
        retf = f.update();
        System.out.println(retf.getStatus() + "\n" + retf.getData());
        System.out.println(f);



//        prova delete
   //     f.delete();


        //es select da API
        APIC a = new APIC("fornitore");

        ArrayList<UpdateElem> list = new ArrayList<UpdateElem>();
        ArrayList<Clausola> listClausole = new ArrayList<>();
//        listClausole.add(new Clausola("ID","","21"));
        String[] arr = {};

        APIReturn result = a.select(arr, listClausole);

        for (Fornitore fornitore: result.toList(Fornitore.class)){
             System.out.println(fornitore);
         }

    }

}
