package DAO;

import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Utils.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {


    protected ArrayList<TableInfoRow> columnList;
    protected ArrayList<ForeignKey> foreignKeyList;
    protected ArrayList<String> primaryKey; //caso chiavi composte
    protected GetConnection connection;
    protected String table;

    public DAO(String table) throws SQLException {
        this.connection = new GetConnection();
        this.table  = table;
        this.columnList = new ArrayList<TableInfoRow>();
        this.primaryKey = new ArrayList<String>();
        this.foreignKeyList = new ArrayList<ForeignKey>();
        config();
    }

    /**
     * Funzione che riceve una query e la lancia
     * ATTENZIONE!!!
     * solo query del tipo update insert delete
     * @param sql
     * @throws SQLException
     */
    protected void execQueryUID(String sql) throws SQLException{

        Statement s = this.getConnection().connection.createStatement();
        s.executeUpdate(sql);
        s.close();

    }

    /**
     *  Questa funzione si occupa di trasformare un array di JSON del tipo
     *                          {
     *      *                      campo: nome campo da controllare (es nome)
     *      *                      operazione: operazione da eseguire (es LIKE,=, <> etc...)
     *      *                      leftop: quello da mettere a sinistra (valori)
     *      *                 }
     * Nella stringa WHERE campo operazione leftop AND campo...
     * @param clausole
     * @return Stringa clausola o stringa vuota
     * @throws JSONException
     *
     * TODO In futuro dovrei pensare un modo per poter specificare OR o AND
     * Finchè non è richiesto lascio stare
     *
     */
    protected String creaClausola(JSONArray clausole) throws JSONException{

        //creo la parte di query che si occupa delle clausole
        if(clausole != null && !clausole.isEmpty()){

            StringBuffer sql = new StringBuffer(" WHERE ");
            int count = 0;
//            JSONObject tmp = null;
            Clausola tmp;
            for (Object elem: clausole) {
                //System.out.println(((JSONObject) elem).get("campo"));


                //System.out.println(elem.toString());
                tmp = new Gson().fromJson(elem.toString(),Clausola.class);
                //System.out.println(tmp);


                String valore = tmp.getLeftop();

                if (valore.equalsIgnoreCase("null"))
                    sql.append(tmp.getCampo() + " " + tmp.getOperazione()+ " " + "NULL");
                else
                    sql.append(tmp.getCampo() +" "+ tmp.getOperazione() +" "+  "\"" +  tmp.getLeftop()+ "\"");
                if ( count < clausole.length() - 1) {
                    sql.append(" AND ");
                    count++;
                }
                //System.out.println(elem);
            }
            return sql.toString();
        }else{
            return new String("");
        }

    }


    /**
     *
     * @param listUpdate array di JSON aventi la seguente struttura
     *                   {
     *                      campo: valore
     *                      valore: valore
     *
     *                   }
     * @param clausole
     * @throws SQLException
     * @throws JSONException
     */
    public void update(JSONArray listUpdate,JSONArray clausole) throws SQLException,JSONException, JsonSyntaxException {

        if (listUpdate == null || listUpdate.isEmpty())
            return;
        StringBuffer sql = new StringBuffer("UPDATE " + this.getTable() + " SET ");

        Gson gson = new Gson();
        int count = 0;
        UpdateElem tmp;
        for (Object elem : listUpdate) {
            tmp = gson.fromJson((String) elem, UpdateElem.class);


            String valore = tmp.getValore();
           if (valore.equalsIgnoreCase("null") || valore == null)
                sql.append(tmp.getCampo() + "=" +"null");
            else
                sql.append(tmp.getCampo() + "=" +"\"" + valore +"\"");

            if (count < listUpdate.length() - 1)
            {
                sql.append(",");
                count++;
            }
        }

        sql.append(" " + creaClausola(clausole));

        System.out.println(sql.toString());

        execQueryUID(sql.toString());


    }

    /**
     * Funzione che esegue una delete dal db
     * @param clausole
     * @throws SQLException
     * @throws JSONException
     */
    public void delete(JSONArray clausole) throws SQLException,JSONException{

        String sql = new String("DELETE FROM " + this.getTable() + " " + creaClausola(clausole));
        System.out.println(sql.toString());
        execQueryUID(sql.toString());




    }


    /**
     *
     * @param listColumn JSON array che contiene la lista dei campi che si vogliono ottenere
     *                   es [nome,telfono] oppure [] (in questo caso prende tutto)
     * @param clausole JSON array che contiene dei json di questo tipo
     *                 {
     *                      campo: nome campo da controllare (es nome)
     *                      operazione: operazione da eseguire (es LIKE,=, <> etc...)
     *                      leftop: quello da mettere a sinistra (
     *                 }
     * @return stringa formato json array
     */
    public String select(JSONArray listColumn,JSONArray clausole) throws JSONException,SQLException {


        //creo la prima parte della query (fino a from table)
        StringBuffer sql = new StringBuffer("SELECT ");
        if (listColumn == null || listColumn.isEmpty())
            sql.append(" * ");
        else {
            for (Object elem: listColumn) {

                sql.append(elem + ",");
                //System.out.println(elem);
            }

            sql = Utility.removeLast(sql);


        }
        sql.append(" FROM " + this.getTable()+ " " + creaClausola(clausole));



        StringBuffer result = new StringBuffer("[");
        Statement s = null;
        s = this.connection.getConnection().createStatement();

          //  System.out.println(sql);
            ResultSet rs = s.executeQuery(sql.toString());

            JSONObject jsonReturn = null;
            JSONArray jsonReturnArray = new JSONArray();
//            jsonReturnArray.put()
            while (rs.next()){
                jsonReturn = new JSONObject();
                result.append("{");


                if (listColumn == null || listColumn.isEmpty()) {

                    for (TableInfoRow t : this.getColumnList()) {

//                        result.append("\"" + t.getName() + "\"" + ":" + "\"" + rs.getString(t.getName()) + "\",");
                        jsonReturn.put(t.getName(),rs.getString(t.getName()));
                    }
                }else {


                    for (Object elem : listColumn) {
//                        result.append("\"" + elem + "\"" + ":" + "\"" + rs.getString(new String(elem.toString())) + "\",");
                        jsonReturn.put(elem.toString(),rs.getString(elem.toString()));
                    }

                }


//                result = new StringBuffer(result.substring(0,result.length() - 1));
//                result.append("},");

                jsonReturnArray.put(jsonReturn);
            }


//            System.out.println(jsonReturnArray.toString());
//            if (result.length() > 1)
//                result = new StringBuffer(result.substring(0,result.length() - 1));
//
//            result.append("]");

                s.close();


        return jsonReturnArray.toString();

    }

    /**
     * Funzione che inserisce un singolo elemento
     * @param json oggetto json che continene le coppie
     *             colonna : valore
     */
    public Integer insert(JSONObject json) throws SQLException{


        Statement s = this.getConnection().connection.createStatement();
        StringBuffer sql = new StringBuffer("INSERT INTO " + this.getTable() + " " );
        StringBuffer values = new StringBuffer("");
        StringBuffer valori = new StringBuffer("");


        int count = 0;
        for (TableInfoRow tb : this.getColumnList()){

            try {

                //prendo il dato dal json object
                //se ciò funziona aggiungo il nome del dato ai values
                //e il valore ai valori
                String val = json.get(tb.getName()).toString();
                //System.out.println(val);

                if (val.length() == 0 || val.equalsIgnoreCase("null")) //evito che siano un null
                    valori.append("null,");
                else
                    valori.append("\""+ val +"\",");

                values.append(tb.getName()+",");


            }catch (Exception e){
                //non faccio nulla
              //  System.out.println(tb.getName());

            }


        }
        if (valori.length() >= 1) {
            valori = new StringBuffer(valori.substring(0, valori.length() - 1));
            values = new StringBuffer(values.substring(0, values.length() - 1));
            sql.append("("+values.toString()+")" + " VALUES " + "(" + valori + ");");
            System.out.println(sql.toString());
            s.executeUpdate(sql.toString());
            s.close();
            s = this.getConnection().connection.createStatement();
            Integer id =s.executeQuery("SELECT last_insert_rowid() as id;").getInt("ID");
            s.close();
            return id;
        }

        return 0;


    }





    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }


    public void showColumnInfo(){
        for (TableInfoRow elem: this.getColumnList())
            System.out.println( elem.getName() + " " + elem.isNotnull());

        for (ForeignKey elem: this.getForeignKeyList())
            System.out.println( elem.getFrom() + " " + elem.getTo() + " " + elem.getTable());

    }

    /**
     * Funzione che va a riempire gli array columnList e primaryKey
      */
    public void config() throws SQLException{

            Statement s = this.connection.getConnection().createStatement();


            //configuro la lista delle colonne
            ResultSet rs = s.executeQuery("PRAGMA table_info("+this.table+")");

            while (rs.next()){

                TableInfoRow tir =new TableInfoRow(rs);
                this.columnList.add(tir);
                if (tir.isPk() == true)
                    this.primaryKey.add(tir.getName());
            }


            //configuro la lista delle chiavi esterne
             rs = s.executeQuery("PRAGMA foreign_key_list("+this.table+")");
            while (rs.next()){
                ForeignKey fk = new ForeignKey(rs);
               this.foreignKeyList.add(fk);
              /*  System.out.println( fk.getTable() + " " +fk.getFrom() + " " + fk.getTo());
                System.out.println( rs.getString("table") + " " +rs.getString("from") + " " + rs.getString("to"));
                */
            }

            s.close();


    }

    public void close() throws SQLException {
            this.connection.getConnection().close();

    }

    public ArrayList<TableInfoRow> getColumnList() {
        return columnList;
    }

    public void setColumnList(ArrayList<TableInfoRow> columnList) {
        this.columnList = columnList;
    }

    public ArrayList<String> getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ArrayList<String> primaryKey) {
        this.primaryKey = primaryKey;
    }


    public GetConnection getConnection() {
        return connection;
    }

    public ArrayList<ForeignKey> getForeignKeyList() {
        return foreignKeyList;
    }

    public void setForeignKeyList(ArrayList<ForeignKey> foreignKeyList) {
        this.foreignKeyList = foreignKeyList;
    }
}
