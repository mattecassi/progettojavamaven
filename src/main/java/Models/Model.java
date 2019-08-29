package Models;

import API.API;
import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Utils.APIReturn;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Model {

    protected String table;
    protected APIC apic;
    protected Integer ID; //E' la PK di tt le tabelle



    /**
     * Funzione che si occupa di inserire l'oggetto nel db
     * e di settarne l'ID.
     *
     * @return APIreturn oggetto che contiene i dati di ritorno(Posso non controllarlo)
     * @throws Exception In caso di errore ritorna un'eccezione con l'errore
     *
     */
    public APIReturn insert() throws Exception {

//        System.out.println(this.toString());
        APIReturn apiReturn = apic.insert(new JSONObject(this.toString()));
        JSONObject tmp = (JSONObject) apiReturn.getData().get(0);
        this.setID(tmp.getInt("ID"));
        return apiReturn;

    }

    public APIReturn update() throws Exception{

        try {
            if (this.getID() == null)
                throw new Exception("Non puoi modificare un oggetto " + this.getClass().getSimpleName() + " senza specificarne l\'ID\nProva ad istanziarlo!!");

            ArrayList<Clausola> clausolaArrayList = new ArrayList<>();
            clausolaArrayList.add(new Clausola("ID", "=", this.getID().toString()));

            JSONObject jsonObject = new JSONObject(this.toString());
//            System.out.println(jsonObject);

            ArrayList<UpdateElem> updateElems = new ArrayList<>();


            jsonObject.keys().forEachRemaining(key -> updateElems.add(new UpdateElem(key, jsonObject.get(key).toString())));


            return this.apic.update(updateElems, clausolaArrayList);


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }



    public APIReturn delete() throws Exception{
        try {
            if (this.getID() == null)
                throw new Exception("Non puoi modificare un oggetto " + this.getClass().getSimpleName() + " senza specificarne l\'ID\nProva ad istanziarlo!!");

            List<Clausola> list = new ArrayList<>();
            list.add(new Clausola("ID","=",this.getID().toString()));
            return this.apic.delete(list);


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public APIC getApic() {
        return apic;
    }

    public void setApic(APIC apic) {
        this.apic = apic;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    /**
     * Questa funzione verrà usato quando devo creare un elemento
     * appartenente ad una gerarchia.
     * Es. se devo creare un enoteca DEVO specificargli l'ID di un fornitore
     * @param tabellaEsterna nome della tabella esterna alla quale mi riferisco
     * @throws Exception
     */
    protected void checkBeforeInsert(String tabellaEsterna) throws Exception{
            if (this.getID() == null)
                throw new Exception("Per creare un "+this.getClass().getCanonicalName()+" bisogna specificare l'ID(Esistente) di un " + tabellaEsterna);
    }

    /**
     * Funzione che verrà lanciata ogni volta che si
     * istanzia un oggetto model.
     * 1) SETTO LA TABELLA
     * 2) ISTANZION APIC
     * @param table
     */
    public void config(String table){

        this.table = table;
        this.apic = new APIC(table);

    }

}
