package API;

import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Utils.APIReturn;
import Utils.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

//API che userà bizzuccio
public class APIC {

    private String table;
    private JSONObject data;

    public static final String SELECT = "select";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String INSERT = "insert";

    /**
     * Per la string action usare una delle stringhe statiche
     * @param table
     */
    public APIC(String table) {
        this.table = table;
        this.data = new JSONObject();
        this.data.put("table",table);
    }


    private APIReturn handleReturn() throws Exception{
        APIReturn ret = new APIReturn(API.API(this.data));
//        System.out.println(ret.getStatus());
        if (ret.getStatus().equalsIgnoreCase("error")){
            throw new Exception( this.table + " " + ret.getErrorMsg());
        }else {
            return ret;
        }
    }




    /**
     * Funzione che esegue la select
     * @param listColumn
     * @param clausole
     * @return
     */
    public APIReturn select(String[] listColumn, List<Clausola> clausole) throws Exception{
        this.data.put("action",APIC.SELECT);
        JSONObject data = new JSONObject();
        data.put("listColumn", Utility.convertStringArrayIntoJSONArray(listColumn));
        data.put("clausole",Clausola.getJSONArrayFromList(clausole));
        this.data.put("data",data);
        return handleReturn();
    }



    //todo MODIFICARE UPDATE E DELETE IN MODO CHE NON PRENDANO PIU' DEI JSONArray ma delle liste

    /**
     * In questo caso data deve contenere i seguenti campi
     * listUpdate : [{campo:valore},{campo:valore}...] dove ci chiede i campi che vuole
     * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
     */
    public APIReturn update(List<UpdateElem> listUpdate, List<Clausola> clausole) throws Exception{

        this.data.put("action",APIC.UPDATE);
        JSONObject data = new JSONObject();
        data.put("listUpdate",UpdateElem.convertListUpdateElemToJSONArray(listUpdate));
        data.put("clausole",Clausola.getJSONArrayFromList(clausole));
        this.data.put("data",data);
        return handleReturn();
    }


    /**
     * In questo caso data deve contenere un oggetto JSON
     * contenente tutte le coppie chiave:valore
     * che andrò ad inserire
     */
    public APIReturn insert(JSONObject dati) throws Exception{
        this.data.put("action",APIC.INSERT);
        this.data.put("data",dati);
//        System.out.println(this.data.getJSONObject("data"));
        return handleReturn();

    }

    /**
     * In questo caso data deve contenere i seguenti campi
     * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
     */
    public APIReturn delete(List<Clausola> clausole) throws Exception{
        this.data.put("action",APIC.DELETE);
        JSONObject data = new JSONObject();
        data.put("clausole",Clausola.getJSONArrayFromList(clausole));
        this.data.put("data",data);
        return handleReturn();
    }











    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
        this.data.put("table",table);
    }


    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }





    //Funzioni deprecate
    /**
     * Funzione che prende due parametri
     * ListColumn che contiene le colonne che voglio
     * es.[nome,cognome,...]
     * Clausole contiene le condizioni del where
     * es. [{campo:nome,operazione:\"=\",leftop=cassi},..]
     * @param listColumn
     * @param clausole
     * @return
     * @deprecated
     */
    public APIReturn select(JSONArray listColumn,JSONArray clausole){

        this.data.put("action",APIC.SELECT);
        JSONObject data = new JSONObject();
        data.put("listColumn",listColumn);
        data.put("clausole",clausole);
        this.data.put("data",data);
        return new APIReturn(API.API(this.data));

    }

    /**
     * In questo caso data deve contenere i seguenti campi
     * listUpdate : [{campo:valore},{campo:valore}...] dove ci chiede i campi che vuole
     * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
     * @deprecated
     */
    public APIReturn update(JSONArray listUpdate,JSONArray clausole){

        this.data.put("action",APIC.UPDATE);

        //convert un array di UpdateElem in un JSONArray
        //tutte queste conversioni sono per fare in modo che bizzo sia sano e salvo


        JSONObject data = new JSONObject();
        data.put("listUpdate",listUpdate);
        data.put("clausole",clausole);
        this.data.put("data",data);
        return new APIReturn(API.API(this.data));
    }

    /**
     * In questo caso data deve contenere i seguenti campi
     * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
     * @deprecated
     */
    public APIReturn delete(JSONArray clausole){
        this.data.put("action",APIC.DELETE);
        JSONObject data = new JSONObject();
        data.put("clausole",clausole);
        this.data.put("data",data);
        return new APIReturn(API.API(this.data));
    }


}
