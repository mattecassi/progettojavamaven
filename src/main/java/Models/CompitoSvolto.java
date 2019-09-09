package Models;

import java.time.LocalDate;

public class CompitoSvolto extends Model {

    private String dataRisoluzione;
    private Integer idCompito;
    private Integer idUser;
    private LocalDate dataRisoluzioneObj;


    public CompitoSvolto() {
        config("compitoSvolto");
    }

    @Override
    public String toString() {
        return "{" +
                "idCompito:" + idCompito +
                ", dataRisoluzione:" + dataRisoluzione +
                ", idUser:" + idUser +
                ", ID:" + ID +
                '}';
    }

    public CompitoSvolto(Integer idCompito, LocalDate dataRisoluzioneObj) {
        config("compitoSvolto");
        this.idCompito = idCompito;
        this.dataRisoluzioneObj = dataRisoluzioneObj;
        this.dataRisoluzione = dataRisoluzioneObj.toString();
    }



    public String getDataRisoluzione() {
        return dataRisoluzione;
    }

    private void setDataRisoluzione(String dataRisoluzione) {
        this.dataRisoluzione = dataRisoluzione;
    }

    public Integer getIdCompito() {
        return idCompito;
    }

    public void setIdCompito(Integer idCompito) {
        this.idCompito = idCompito;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public LocalDate getDataRisoluzioneObj() {
        return dataRisoluzioneObj;
    }

    public void setDataRisoluzioneObj(LocalDate dataRisoluzioneObj) {
        this.dataRisoluzioneObj = dataRisoluzioneObj;
        this.dataRisoluzione = dataRisoluzioneObj.toString();
    }
}
