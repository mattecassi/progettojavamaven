package Models;

import API.APIC;
import ClientUtils.Clausola;
import ClientUtils.UpdateElem;
import Utils.APIReturn;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fornitore extends Model {


    private String nome;
    private Integer qta_min;
    private Integer qta_max;
    private String telefono;
    private String mail;



    public Fornitore() {
        config("fornitore");
    }

    public Fornitore(Integer ID, String nome, Integer qtaMin, Integer qta_max, String telefono, String mail) {
        config("fornitore");
        this.ID = ID;
        this.nome = nome;
        this.qta_min = qtaMin;
        this.qta_max = qta_max;
        this.telefono = telefono;
        this.mail = mail;
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
                ", nome:\"" + nome + '"' +
                ", qta_min:" + qta_min +
                ", qta_max:" + qta_max +
                ", telefono:'" + telefono + '\'' +
                ", mail:\"" + mail + '"' +
                '}';
    }

}
