package Models;

import Utils.APIReturn;

public class CompitoMensile extends Model {



    private Integer giornoDaRipetere;//[1-7]
    private Integer settimanaDaRipetere;//[1-4]
    private Integer idCompitoM;

    public CompitoMensile() {
        config("compito_mensile");
    }

    public CompitoMensile(Integer giornoDaRipetere, Integer settimanaDaRipetere,Integer idCompitoM) {
        config("compito_mensile");
        this.setGiornoDaRipetere(giornoDaRipetere);
        this.setSettimanaDaRipetere(settimanaDaRipetere);
        this.idCompitoM = idCompitoM;
    }

    public Integer getIdCompitoM() {
        return idCompitoM;
    }

    public void setIdCompitoM(Integer idCompitoM) {
        this.idCompitoM = idCompitoM;
    }


    public Integer getGiornoDaRipetere() {
        return giornoDaRipetere;
    }

    public void setGiornoDaRipetere(Integer giornoDaRipetere) {
        if ( giornoDaRipetere != null && giornoDaRipetere >= 1 && giornoDaRipetere <= 7 )
                this.giornoDaRipetere = giornoDaRipetere;
    }

    public Integer getSettimanaDaRipetere() {
        return settimanaDaRipetere;
    }

    public void setSettimanaDaRipetere(Integer settimanaDaRipetere) {
        if ( settimanaDaRipetere != null && settimanaDaRipetere >= 1 && settimanaDaRipetere <= 4)
            this.settimanaDaRipetere = settimanaDaRipetere;
    }

    @Override
    public String toString() {
        return "{" +
                "idCompitoM:" + idCompitoM +
                ",giornoDaRipetere:" + giornoDaRipetere +
                ", settimanaDaRipetere:" + settimanaDaRipetere +
                ", ID:" + ID +
                '}';
    }
}
