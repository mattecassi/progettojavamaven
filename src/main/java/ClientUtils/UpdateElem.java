package ClientUtils;

import org.json.JSONArray;

import java.util.List;

public class UpdateElem {

    private String campo;
    private String valore;

    public UpdateElem(String campo, String valore) {
        this.campo = campo;
        this.valore = valore;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    @Override
    public String toString() {
        return "{" +
                "campo:'" + campo + '\'' +
                ", valore:'" + valore + '\'' +
                '}';
    }


    public static JSONArray convertListUpdateElemToJSONArray(List<UpdateElem> list){

        JSONArray jsonArray = new JSONArray();

        for (UpdateElem elem: list) {
            jsonArray.put(elem.toString());
        }
        return jsonArray;

    }
}
