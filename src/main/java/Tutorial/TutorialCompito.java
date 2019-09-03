package Tutorial;

import API.APIC;
import ClientUtils.Clausola;
import Models.Compito;
import Models.CompitoGiornaliero;
import Models.CompitoMensile;
import Models.CompitoSettimanale;
import Utils.APIReturn;
import Utils.Utility;

import java.util.ArrayList;
import java.util.Date;

public class TutorialCompito {
  /*  public static void main(String[] args) {
        try {


            Date d = new Date();
            System.out.println(Utility.convertStringSQLiteToLocalDateTime(d.toInstant().toString().substring(0,d.toInstant().toString().length() - 1)));

            Compito c = new Compito("Lavare le tende"); //desc obbligatoria
            c.delete();

            c.insert();

            System.out.println(c);

            //creazione compito mensile del quale non importa in quale giorno/quale settimana vada eseguito
            CompitoMensile cm = new CompitoMensile(1, 1, c.getID());
            CompitoMensile cmd = new CompitoMensile(1, 3, c.getID());

            cm.insert();
            cmd.insert();

            System.out.println(cm);

            cm.setGiornoDaRipetere(2);
            cm.update();

            System.out.println(cmd);

            //creazione compito giornaliero
            CompitoGiornaliero cg = new CompitoGiornaliero(c.getID());
            cg.insert();
            System.out.println(cg);


            //creazione compito settimanle dove specifichiamo che vada fatto ogni lunedì e martedì
            CompitoSettimanale cs = new CompitoSettimanale(1, c.getID());
            CompitoSettimanale csm = new CompitoSettimanale(2, c.getID());
            cs.insert();
            csm.insert();
            System.out.println(cs);
            System.out.println(csm);


            //es select da API
            APIC a = new APIC("compito_settimanale");
            ArrayList<Clausola> listClausole = new ArrayList<>();
            String[] arr = {};


            APIReturn result = a.select(arr, listClausole);
            for (CompitoSettimanale vino : result.toList(CompitoSettimanale.class)) {
                System.out.println(vino);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
*/
}
