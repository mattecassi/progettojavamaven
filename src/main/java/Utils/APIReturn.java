package Utils;

import Models.Model;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APIReturn{

    private String status;
    private JSONArray data;

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
        this.data = new JSONArray(ret.getString("data"));
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

        if (getStatus().equalsIgnoreCase("error"))
            return null;

        ArrayList<T> ret = new ArrayList<T>();

        Gson g = new Gson();
        T tmp;

        for (Object ele: this.getData()){
            tmp = g.fromJson(ele.toString(),typeParameterClass);
            ret.add(tmp);
           // System.out.println(tmp);
        }

        return ret;

    }



    public APIReturn(String status, JSONArray data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }
}
