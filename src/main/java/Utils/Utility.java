package Utils;

import org.json.JSONArray;

public class Utility {

    public static StringBuffer removeLast(StringBuffer result){

        if (result.length() > 1){
            return new StringBuffer(result.substring(0,result.length() - 1));
        }else {
            return  new StringBuffer("");
        }

    }

    public static JSONArray convertStringArrayIntoJSONArray(String[] arr){

        JSONArray ret = new JSONArray();

        for (String str: arr){
            ret.put(str);
        }

        return ret;

    }


}
