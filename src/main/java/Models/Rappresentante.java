package Models;

import Utils.APIReturn;

public class Rappresentante extends Model {



    private String nome_rappresentante;
    private String cognome;


    public Rappresentante() {
        config("rappresentante");
    }

    @Override
    public APIReturn insert() throws Exception {
        checkBeforeInsert("Fornitore");
        return super.insert();
    }

    public Rappresentante(String nome_rappresentante, String cognome) {
        config("rappresentante");
        this.nome_rappresentante = nome_rappresentante;
        this.cognome = cognome;
    }

    public String getNome_rappresentante() {
        return nome_rappresentante;
    }

    public void setNome_rappresentante(String nome_rappresentante) {
        this.nome_rappresentante = nome_rappresentante;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "{" +
                "nome_rappresentante:'" + nome_rappresentante + '\'' +
                ", cognome:'" + cognome + '\'' +
                ", ID:" + ID +
                '}';
    }
}
