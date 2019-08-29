package Utils;

import javafx.scene.control.Alert;
import org.json.JSONArray;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

}
