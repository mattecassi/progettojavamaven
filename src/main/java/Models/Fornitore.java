package Models;

import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Utils.APIReturn;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fornitore extends Model {


    private Integer ID;
    private String nome;
    private Integer qta_min;
    private Integer qta_max;
    private String telefono;
    private String mail;



    public Fornitore() {
        this.setTable("fornitore");
        this.apic = new APIC(this.getTable());
    }

    public Fornitore(Integer ID, String nome, Integer qtaMin, Integer qta_max, String telefono, String mail) {
        this.table = "fornitore";
        this.apic = new APIC(this.getTable());
        this.ID = ID;
        this.nome = nome;
        this.qta_min = qtaMin;
        this.qta_max = qta_max;
        this.telefono = telefono;
        this.mail = mail;
    }




    @Override
    public APIReturn insert() {

       APIReturn apiReturn = apic.insert(new JSONObject(this.toString()));
        JSONObject tmp = (JSONObject) apiReturn.getData().get(0);
        this.setID(tmp.getInt("ID"));
        return apiReturn;

    }


    //todo scrivere questi metodi della minchia
    @Override
    public APIReturn delete() {
        List<Clausola> list = new ArrayList<>();
        list.add(new Clausola("ID","=",this.getID().toString()));
        return  this.apic.delete(list);
    }

    @Override
    public APIReturn update() {

        ArrayList<Clausola> clausolaArrayList = new ArrayList<>();
        clausolaArrayList.add(new Clausola("ID","=",this.getID().toString()));

        ArrayList<UpdateElem> updateElems = new ArrayList<>();
        updateElems.add(new UpdateElem("nome",this.getNome()));
        updateElems.add(new UpdateElem("telefono",this.getTelefono()));
        updateElems.add(new UpdateElem("mail",this.getMail()));
        updateElems.add(new UpdateElem("qta_min",this.getQta_min().toString()));
        updateElems.add(new UpdateElem("qta_max",this.getQta_max().toString()));

        APIReturn apiReturn = this.apic.update(updateElems,clausolaArrayList);

        return apiReturn;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQta_min() {
        return qta_min;
    }

    public void setQta_min(Integer qta_min) {
        this.qta_min = qta_min;
    }

    public Integer getQta_max() {
        return qta_max;
    }

    public void setQta_max(Integer qta_max) {
        this.qta_max = qta_max;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "{" +
                "ID:" + ID +
                ", nome:'" + nome + '\'' +
                ", qta_min:" + qta_min +
                ", qta_max:" + qta_max +
                ", telefono:'" + telefono + '\'' +
                ", mail:'" + mail + '\'' +
                '}';
    }

}
