package Utils;

import API.APIC;
import ClientUtils.Clausola;
import Models.Fornitore;
import Models.Model;
import Models.Rappresentante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static StringBuffer removeLast(StringBuffer result){

        if (result.length() > 1){
            return new StringBuffer(result.substring(0,result.length() - 1));
        }else {
            return  new StringBuffer("");
        }

    }

    public static LocalDateTime convertStringSQLiteToLocalDateTime(String datetime){
        datetime.replace("T"," ");
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.from(f.parse(datetime));
    }

    public static JSONArray convertStringArrayIntoJSONArray(String[] arr){

        JSONArray ret = new JSONArray();

        for (String str: arr){
            ret.put(str);
        }

        return ret;

    }

    static public void createErrorWindow(String s){
        Alert error = new Alert(Alert.AlertType.ERROR, s);
        error.setHeaderText(null);
        error.showAndWait();
    }

    static public void createWarningWindow(String s){
        Alert error = new Alert(Alert.AlertType.WARNING, s);
        error.setHeaderText(null);
        error.showAndWait();

    }

    static public void createSuccessWindow(String s){
        Alert error = new Alert(Alert.AlertType.INFORMATION, s);
        error.setHeaderText(null);
        error.showAndWait();
    }


    /**
     * Metodo che serve a bizzo per realizzare le sue bellissime combobox
     * @param table
     * @param field
     * @param value
     * @param typeParameter
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T extends Model> ObservableList<String> loadDataForCmb(String table, String field,String value, Class<T> typeParameter) throws Exception {

        JSONObject jsonObject;

        ObservableList<String> nomi = FXCollections.observableArrayList();
        nomi.add("");

        APIC ap = new APIC(table);
        String[] arrClausole = {field};
        List<Clausola> l = new ArrayList<>();
        l.add(new Clausola(field," LIKE ", "%" + value + "%"));
        for (T f : ap.select(arrClausole,l).toObservableList(typeParameter)){
            //System.out.println(f);
            jsonObject = new JSONObject(f.toString());
            nomi.add(jsonObject.getString(field));

        }
        return nomi;


    }

}
