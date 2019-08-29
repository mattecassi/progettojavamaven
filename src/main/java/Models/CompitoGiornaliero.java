package Models;

import Utils.APIReturn;

public class CompitoGiornaliero extends Model {


    private Integer idCompitoG;

    public CompitoGiornaliero() {
        config("compito_giornaliero");
    }


    public CompitoGiornaliero(Integer idCompitoG) {
        config("compito_giornaliero");
        this.setIdCompitoG(idCompitoG);
    }

    public Integer getIdCompitoG() {
        return idCompitoG;
    }

    public void setIdCompitoG(Integer idCompitoG) {
        this.idCompitoG = idCompitoG;
    }

    @Override
    public String toString() {
        return "{" +
                "ID:" + ID +
                ", idCompitoG:" +idCompitoG+
                '}';
    }
}
