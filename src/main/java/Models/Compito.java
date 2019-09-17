package Models;

import API.APIC;
import ClientUtils.Clausola;
import Utils.APIReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Compito extends Model {

    //per il dow userai sempre queste!!!
    public static final Integer SUNDAY = 0;
    public static final Integer MONDAY = 1;
    public static final Integer TUESDAY = 2;
    public static final Integer WEDNESDAY = 3;
    public static final Integer THURSDAY = 4;
    public static final Integer FRIDAY = 5;
    public static final Integer SATURDAY = 6;

    //nel caso non importasse la data mettere domenica
    //così lui sà che deve completarla entro domenica


    private String descrizione;
    //verrà usata solo in fase di toString
    private String dataCompitoOff;

    private LocalDate dataCompito;
    private Integer dow;

    public Compito() {
        config("compito");
    }

    public Compito(String desc) {
        config("compito");
        this.descrizione = desc;
    }

    public Compito(String desc,Integer dow) throws Exception {
        config("compito");
        this.descrizione = desc;
        this.setDow(dow);
    }
    public Compito(String desc,LocalDate dataCompito) throws Exception{
        config("compito");
        this.descrizione = desc;
        this.setDataCompito(dataCompito);
    }


    /**
     * Funzione che calcola la data di quando questo
     * compito andrebbe svolto  e lo inserisce nel db!!
     * @throws Exception
     */
    public void svolgi() throws Exception{
        LocalDate oggi = LocalDate.now();
        CompitoSvolto compitoSvolto = new CompitoSvolto();
        compitoSvolto.setIdCompito(this.getID());
        if (this.dow == null && this.dataCompitoOff != null){
            //OCCASIONALE
            compitoSvolto.setDataRisoluzioneObj(LocalDate.parse(this.dataCompitoOff));
        }else if (this.dow != null && this.dataCompitoOff == null){
            //compito settimanale
            int giornoAttuale = oggi.getDayOfWeek().getValue();
            long add;
            if (this.dow == 0 && giornoAttuale <= 7){
                //caso sia domenica
                add = 7L - (long)giornoAttuale;
                compitoSvolto.setDataRisoluzioneObj(LocalDate.now().plusDays(add));
            }else if (dow != 0 && giornoAttuale <= dow){
                add = (long)dow - (long)giornoAttuale;
                compitoSvolto.setDataRisoluzioneObj(LocalDate.now().plusDays(add));
            }else if (this.dow != 0){
                throw new Exception("Errore, il giorno nel quale avresti dovuto svolgere questo compito è passato");
            }
        }else {
            //giornaliero
            compitoSvolto.setDataRisoluzioneObj(oggi);
        }
        System.out.println(compitoSvolto);
        compitoSvolto.insert();


    }



    /**
     * Funzione che restituisce tutti i compiti che devi svolgere questa settimana
     * @return ArrayList di compiti
     * @throws Exception
     */
    public static ArrayList<Compito> getCompitiNonSvoltiQuestaSettimana() throws Exception{
        APIC db = new APIC("compitiDaSvolgereQuestaSettimana");
        return db.select().toList(Compito.class);
    }

    //TODO FUNZIONE CHE MI RICONOSCE IL TIPO DI COMPITO

    /**
     * Funzione che si occupa di restituire il tipo del compito
     * 1 se occasionale
     * 2 se giornaliero
     * 3 se settimanale
     * @return
     */
    public Integer getTipoCompito(){

        if (this.dow == null && this.dataCompitoOff == null)
            return 2;
        else if (this.dow == null)
            return 1;
        else
            return 3;

    }


    /**
     * Creo un compito settimanale dandogli il giorno
      * @param dow giorno della settimana che deve essere
     *            compreso tra 1 e 7
     */
    public void setDow(Integer dow)throws Exception{
        if (dow >= SUNDAY && dow <= SATURDAY){
            this.dow = dow;
            this.dataCompito = null;
        }else {
            throw new Exception("Il giorno che gli passo deve essere compreso tra 1 e 7");
        }
    }

    /**
     * Creo un compito che non importa quando venga eseguito
     * ma è settimanale. Di default è di domenica così sò
     * che entro quel giorno deve essere eseguito
     */
    public void setDow(){
        this.dataCompito = null;
        this.dow = this.SUNDAY;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    @Override
    public String toString() {
        return "{" +
                " descrizione:\"" + descrizione + '"' +
                ", dataCompitoOff:\"" + dataCompitoOff + '"' +
                ", dow:" + dow +
                ", ID:" + ID +
                '}';
    }

    public LocalDate getDataCompito() {
        return dataCompito;
    }

    public void setDataCompito(LocalDate dataCompito) throws Exception{
        if (dataCompito.compareTo(LocalDate.now()) < 0)
            throw new Exception("Puoi creare compiti occasionali da oggi["+LocalDate.now().toString()+"] in poi.");
        this.dataCompito = dataCompito;
        this.dataCompitoOff = dataCompito.toString();
        this.dow = null;
    }

    public String getDataCompitoOff() {
        return dataCompitoOff;
    }

    public void setDataCompitoOff(String dataCompitoOff) throws Exception{
        this.dataCompitoOff = dataCompitoOff;
        this.setDataCompito(LocalDate.parse(this.dataCompitoOff));
    }

    @Override
    public APIReturn delete() throws Exception {
        APIC apicCompitoSvolto = new APIC("compitoSvolto");
        List<Clausola> l = new ArrayList<>();
        l.add(new Clausola("idCompito","=",this.getID().toString()));
        apicCompitoSvolto.delete(l);
        return super.delete();
    }

    public Integer getDow() {
        return dow;
    }
}
