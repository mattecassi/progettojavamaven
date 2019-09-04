package Tutorial;

import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Models.Vino;
import Utils.APIReturn;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tutorial {

    /**
     * APIC
     * Quest'oggetto ti permetterà di effettuare delle bellissime query
     * Infatti quest'oggetto prende un unico parametro che è per l'appunto
     * il nome della tabella.
     * Tutte le funzioni principali ritornano un APIReturn
     * Dopodichè posso richiamare le funzioni
     * select
     * update
     * delete
     * insert
     *
     * Guardare la classe APIReturn
     *
     * La grande differenza tra oggetto e apic è che nell'oggetto
     * agisco su un singolo elemento mentre con l'apic posso creare delle update/delete
     * che operano su vasta scala
     *
     * ES query che creerà la funzione oggetto
     * update vino set nome="Cassi Rosso" where ID=1
     *
     * ES query apic che posso creare con APIC
     * update vino set nome="Cassi Rosso" where costo=15.00 AND nome="Cassi Rosso"
     *
     * HO aggiornato le classi APIC e Model, nel caso non vada a buon fine l'operazione di insert|update|delete
     * hai un Exception, coglione.
     */
    public static void main(String[] args){

     //1. Istanzialo
     APIC a = new APIC("vino");
     APIReturn ret;


        /**
         * INSERT
         * Anche l'insert può essere fatto tramite APIC
         * però io consiglio di farlo tramite le classi Model
         *
         */

        try {


        Vino vino = new Vino();
     //   vino.setID(10);
        vino.setAnno(2001);
        vino.setCosto(10.00);
        vino.setPrezzoVendita(20.00);
        vino.setNome("Prova inserimento con ID");
        vino.setTipo("Rosso"); //questa è un fk,stai attento che esista in tipo vino
        vino.setIdCantina(37);
        //Metodo consigliato(con oggetto)
        ret = vino.insert();

        //Caso apic
        List<UpdateElem> updateElems = new ArrayList<UpdateElem>();
        updateElems.add(new UpdateElem("costo","15.00"));
        List<Clausola> clausolaListUpdate = new ArrayList<Clausola>();
//        System.out.println("Update di più elementi" + a.update(updateElems,clausolaListUpdate).getData().toString());


        /**
         * La delete tramite APIC accetta solo la lista clausole
         * Mentre la versione oggetto elimina quella singola istanza
         */
  //      vino.delete();
        //APIC

        /**
         * SELECT
         * La funzione select prende due parametri
         * 1) String[] --parametri che mi interessano
         *     String[] arr = {}; se lo instanzio in questo modo mi ritorna tutti i parametri
         * 2) List<Clausola> --clausole che comporranno il where
         */

        String[] colonne = {};
        List<Clausola> clausolas = new ArrayList<Clausola>();
        clausolas.add(new Clausola("anno","=","2001"));
        /*clausolas.add(new Clausola("ID","IS NOT","NULL"));
        */
        ret = a.select(colonne,clausolas);

        System.out.println("Lista elem");
//        ret.toObservableList(Vino.class);
        for (Vino elem: ret.toList(Vino.class)){ //toList(classe.class) mi restituisce un'array list contenente tt gli elementi
            System.out.println(elem.toString());
        }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

}
