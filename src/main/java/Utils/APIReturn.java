package Utils;

import Models.Model;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIReturn{

    private String status;
    private JSONArray data;
    private String errorMsg;

    /**
     * Questa funzione verrà principalmente usata in APIC dove,
     * si occuperà di prendere un JSONobject che contiene la risposta
     * tipica dell'API
     *
      * @param ret
     * @throws JSONException
     */
    public APIReturn(JSONObject ret) throws JSONException {

        this.status = ret.getString("stato");
        if (this.status.equalsIgnoreCase("error")){
            this.errorMsg = (String) new JSONArray(ret.getString("data")).get(0);
            this.data = null;
        }else{
            this.errorMsg = null;
            this.data = new JSONArray(ret.getString("data"));
        }
    }


    /**
     * Questa funzione si occupa di convertire this.data,
     * che è un JSONArray contenente tutti i risultati della select,
     * in un arrayList di oggetti.
     * Questi oggetti devono estendere Model
     * Come parametro gli devi passare nomeclasse.class
     *
     * @param typeParameterClass es Fornitore.class
     * @param <T> Deve estendere Model
     * @return Ritorna un arrayList di T
     */
    public <T extends Model> ArrayList<T> toList(Class<T> typeParameterClass){

        if (getStatus().equalsIgnoreCase("error") )//|| this.getData().isEmpty())
            return null;

        ArrayList<T> ret = new ArrayList<T>();

        Gson g = new Gson();
        T tmp;

        for (Object ele: this.getData()){
//            System.out.println(ele.toString());
            tmp = g.fromJson(ele.toString(),typeParameterClass);
            ret.add(tmp);
           // System.out.println(tmp);
        }

        return ret;

    }

    /**
     * Funzione che funziona esattamente come toList ma ritorna un'observableList
     * Questa funzione verrà usata dall'applicazione fx!!
     * @param typeParameterClass come nel toList
     * @param <T> come nel toList
     * @return
     */
    public <T extends Model> ObservableList<T> toObservableList(Class<T> typeParameterClass){
        return FXCollections.observableArrayList(this.toList(typeParameterClass));
    }



    public APIReturn(String status, JSONArray ret) {
        this.status = status;
        if (this.status.equalsIgnoreCase("error")){
            this.errorMsg = ret.getString(0);
            this.data = null;
        }else{
            this.errorMsg = null;
            this.data = ret;
        }
    }

    public String getStatus() {
        return status;
    }


    public JSONArray getData() {
        return data;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
