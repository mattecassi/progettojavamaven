package ClientUtils;

import org.json.JSONArray;

import java.util.List;

public class Clausola {

    private String campo;
    private String operazione;
    private String leftop;


    public Clausola(String campo, String operazione, String leftop) {
        this.campo = campo;
        this.operazione = operazione;
        this.leftop = leftop;
    }


    @Override
    public String toString() {
        return "{" +
                "campo:\"" + campo + '"' +
                ", operazione:\"" + operazione + '"' +
                ", leftop:\"" + leftop + '"' +
                '}';
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    public String getLeftop() {
        return leftop;
    }

    public void setLeftop(String leftop) {
        this.leftop = leftop;
    }

    public static JSONArray getJSONArrayFromList(List<Clausola> list){

        JSONArray jsonArray = new JSONArray();

        for (Clausola elem: list) {
            jsonArray.put(elem.toString());
        }
        return jsonArray;

    }
}
