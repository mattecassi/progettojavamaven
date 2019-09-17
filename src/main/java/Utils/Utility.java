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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        HashSet<String> hashSet = new HashSet<>();//HO SCOPERTO CHE NON SERVE PIU IL CAMPO VUOTO, BASTA FARE nomeComboBox.getSelectionModel().clearSelection();
        APIC ap = new APIC(table);
        String[] arrClausole = {field};
        List<Clausola> l = new ArrayList<>();
        l.add(new Clausola(field," LIKE ", "%" + value + "%"));
        for (T f : ap.select(arrClausole,l).toObservableList(typeParameter)){
            //System.out.println(f);
            jsonObject = new JSONObject(f.toString());
            hashSet.add((String) jsonObject.get(field));

        }
        nomi.addAll(hashSet);
        return nomi;

    }

    public static String convertNumberIntoDayOfTheWeek(Integer n){
        switch (n){
            case 1 :{
                return "LUNEDI'";
            }
            case 2 :{
                return "MARTEDI'";
            }

            case 3 : {
                return "MERCOLEDI'";
            }

            case 4:{
                return "GIOVEDI'";
            }

            case 5:{
                return "VENERDI'";
            }

            case 6:{
                return "SABATO";
            }
            case 0:{
                return "DOMENICA";
            }
            default: {
                return "DOMENICA";
            }
        }
    }

    public static <T extends Model> ObservableList<Integer> loadDataForCmbInteger(String table, String field,Integer value, Class<T> typeParameter) throws Exception {

        JSONObject jsonObject;
        ObservableList<Integer> nume = FXCollections.observableArrayList();
        HashSet<Integer> hashSet = new HashSet<>();
        APIC ap = new APIC(table);
        String[] arrClausole = {field};
        List<Clausola> l = new ArrayList<>();
        if (value != null)
            l.add(new Clausola(field,"=", value.toString() ));

        for (T f : ap.select(arrClausole,l).toObservableList(typeParameter)){
            //System.out.println(f);
            jsonObject = new JSONObject(f.toString());
            hashSet.add((Integer) jsonObject.get(field));
        }
        nume.addAll(hashSet);
        return nume;

    }

    public static <T extends Model> ObservableList<Double> loadDataForCmbDouble(String table, String field,Double value, Class<T> typeParameter) throws Exception {

        JSONObject jsonObject;
        ObservableList<Double> nume = FXCollections.observableArrayList();
        APIC ap = new APIC(table);
        HashSet<Double> hashSet = new HashSet<>();
        String[] arrClausole = {field};
        List<Clausola> l = new ArrayList<>();
        if (value != null)
            l.add(new Clausola(field,"=", value.toString() ));
        for (T f : ap.select(arrClausole,l).toObservableList(typeParameter)){
            //System.out.println(f);
            jsonObject = new JSONObject(f.toString());
            nume.add((Double) jsonObject.get(field));
        }
        nume.addAll(hashSet);
        return nume;

    }


    public static String replaceAllDeniedChar(String s){

        String first =  s.replace('"',' ');
        return first.replace('\\',' ');
    }


}
