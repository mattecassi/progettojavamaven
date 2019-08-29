package Models;

import Utils.APIReturn;

public class CompitoSettimanale extends Model {


    private Integer giornoDaRipetere;
    private Integer idCompitoS;


    public CompitoSettimanale() {
        config("compito_settimanale");
    }

    public CompitoSettimanale(Integer giornoDaRipetere,Integer idCompitoS) {
        config("compito_settimanale");
        this.setGiornoDaRipetere(giornoDaRipetere);
        this.setID(ID);
        this.idCompitoS = idCompitoS;

      }


    @Override
    public String toString() {
        return "{ idCompitoS:" + idCompitoS +
                ", giornoDaRipetere:" + giornoDaRipetere +
                ", ID:" + ID +
                '}';
    }


    public Integer getIdCompitoS() {
        return idCompitoS;
    }

    public void setIdCompitoS(Integer idCompitoS) {
        this.idCompitoS = idCompitoS;
    }



    public Integer getGiornoDaRipetere() {
        return giornoDaRipetere;
    }

    public void setGiornoDaRipetere(Integer giornoDaRipetere) {
        if (giornoDaRipetere != null && giornoDaRipetere >= 1 && giornoDaRipetere <= 7)
            this.giornoDaRipetere = giornoDaRipetere;
    }

}
