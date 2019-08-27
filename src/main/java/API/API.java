package API;

import DAO.DAO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class API {

    /*
          Qui arrivano le richieste di bizzuccio
          E io le passo a un router che si occupa di separarle
          {
                table: tabella
                action: azione da eseguire (es. get,delete,select etc..
                data: JSONobject che contiene tutt il necessario
          }

     */

    /**
     * Funzione che verrà usata per far comunicare client e server
     * Riceve un JSONobject contenente 3 campi base
     * {
     *                  table: tabella
     *                 action: azione da eseguire (es. get,delete,select etc..
     *                 data: JSONobject che contiene tutt il necessario
     * }
     *
     * La funzione esegue l'action sulla tabella table
     * @param jsonObject
     * @return
     * JSONobject contenente
     * {
     *     stato: success|error
     *     data: dati di ritorno
     *
     * }
     */
    public static JSONObject API(JSONObject jsonObject) {
        JSONObject data;
        String action;
        DAO dao;
        JSONObject ret = new JSONObject();
        String stato ="";
        String dato_ret ="";
        try {
            //System.out.println(jsonObject);
            dao = new DAO(jsonObject.getString("table"));
            data = jsonObject.getJSONObject("data");
            action = jsonObject.getString("action");
            //System.out.println(data);
        }catch (SQLException eqsql){
            return new JSONObject("{stato: error, messaggio:\"Errore durante la creazione del DAO, controllare che il nome della tabella sia corretto e che nulla si sia scassato\"}");
        }catch (JSONException ejs){
            return new JSONObject("{stato: error, messaggio:\"Controllare che esistano i parametri [table,action e data] e che nulla si sia scassato\"}");
        }

        switch (action){

            case "select":{

                /**
                 * In questo caso data deve contenere i seguenti campi
                 * listColumn : [campo1,campo2,etc...] dove ci chiede i campi che vuole
                 * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
                 */
                try {
                    JSONArray listColumn = data.getJSONArray("listColumn");
                    JSONArray clausole = data.getJSONArray("clausole");
                    stato = "success";
                    //devo fare getString su ge
                    dato_ret = dao.select(listColumn,clausole);
                    dao.close();
                }catch (SQLException esql){

                    stato = "Error";
                    dato_ret = "[\"Errore durante l\'esecuzione della query "+esql.getMessage()+"\"]";


                }catch (JSONException ejson){

                    stato = "Error";
                    dato_ret = "\"Controllare che esistano i parametri [listColumn e clausole] e che nulla si sia scassato\"";
                }

            }break;
            case "update":{

                /**
                 * In questo caso data deve contenere i seguenti campi
                 * listUpdate : [{campo:valore},{campo:valore}...] dove ci chiede i campi che vuole
                 * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
                 */
                try {

                    //System.out.println("passo prima dei get");
                    JSONArray listUpdate = data.getJSONArray("listUpdate");
                    JSONArray clausole = data.getJSONArray("clausole");

                    stato = "success";
                    dato_ret= "[]";

                    dao.update(listUpdate,clausole);
                    dao.close();
                }catch (SQLException esql){

                    stato = "Error";
                    dato_ret = "[\"Errore durante l\'esecuzione della query UPDATE "+esql.getMessage()+"\"]";


                }catch (JSONException ejson){

                    stato = "Error";
                    dato_ret = "[\"Controllare che esistano i parametri [listUpdate e clausole] e che nulla si sia scassato\"]";
                }


            }break;
            case "delete":{
                /**
                 * In questo caso data deve contenere i seguenti campi
                 * clausole : [{campo:nomecampo,operazione:op,leftop:valore},..,{}] array di JSON dove sono presenti le clausole
                 */
                try {
                    JSONArray clausole = data.getJSONArray("clausole");
                    stato = "success";
                    dao.delete(clausole);
                    dato_ret= "[]";
                    dao.close();
                }catch (SQLException esql){

                    stato = "Error";
                    dato_ret = "[\"Errore durante l\'esecuzione della query DELETE "+esql.getMessage()+"\"]";


                }catch (JSONException ejson){

                    stato = "Error";
                    dato_ret = "[\"Controllare che esistano i parametri [listUpdate e clausole] e che nulla si sia scassato\"]";
                }

            }break;
            case "insert":{

                /**
                 * In questo caso data deve contenere un oggetto JSON
                 * contenente tutte le coppie chiave:valore
                 * che andrò ad inserire
                 */
                try {
                   Integer id = dao.insert(data);
                  // System.out.println(id);
                    stato = "success";
                    dato_ret="[{\"ID\":"+id+"}]";
                    //dao.close();
                }catch (SQLException esql){

                    stato = "Error";
                    dato_ret = "[\"Errore durante l\'esecuzione della query INSERT "+esql.getMessage()+"\"]";


                }catch (JSONException ejson){

                    stato = "Error";
                    dato_ret = "[\"Controllare che esistano il camp [data] e che nulla si sia scassato\"]";
                }finally {
                    try {
                        dao.close();
                    } catch (SQLException e) {
                        stato = "Errore chiusura connessione";
                        dato_ret = "[Errore chiusura connessio]";
                    }
                }


            }break;

        }
        /**
         * Se nessuno di questi va a buon fine i casi sono due
         * 1) O c'è un azione che non esiste
         * 2) Oppure un operazione che è prevista dai router
         * TODO implementare nel futuro prossimo
         */

        ret.put("stato",stato);
        ret.put("data",dato_ret);

//        System.out.println("RET" + ret);

        return  ret;
    }



}
