package Models;

import Utils.APIReturn;

public class Enoteca extends Model {


    private String via;
    private String citta;
    private String regione;
    private String stato;


    public Enoteca() {
        config("enoteca");
    }

    public Enoteca(String via, String citta, String regione, String stato) {
        config("enoteca");
        this.via = via;
        this.citta = citta;
        this.regione = regione;
        this.stato = stato;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public APIReturn insert() throws Exception {
        checkBeforeInsert("Fornitore");
        return super.insert();
    }

    @Override
    public String toString() {
        return "{" +
                "via:'" + via + '\'' +
                ", citta:'" + citta + '\'' +
                ", regione:'" + regione + '\'' +
                ", stato:'" + stato + '\'' +
                ", ID:" + ID +
                '}';
    }
}
