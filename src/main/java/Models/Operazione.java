package Models;

import Utils.Utility;

import java.time.LocalDateTime;
import java.util.Date;

public class Operazione extends Model{


    private Integer idvino;
    private String data_operazione;
    private Integer qta;
    private Double sconto;
    private Double importo;
    private String descrizione;
    private Integer tipoOperazione;





//    private Date

    public Operazione() {
        config("operazione");
    }

    public Operazione(Integer idvino, String data_operazione, Integer qta, Double sconto, Double importo, String descrizione, Integer tipoOperazione) {
        config("operazione");
        this.idvino = idvino;
        this.data_operazione = data_operazione;
        this.qta = qta;
        this.sconto = sconto;
        this.importo = importo;
        this.descrizione = descrizione;
        this.tipoOperazione = tipoOperazione;
    }


    public Integer getIdvino() {
        return idvino;
    }

    public void setIdvino(Integer idvino) {
        this.idvino = idvino;
    }

    public String getData_operazione() {
        return data_operazione;
    }




    public void setData_operazione(String data_operazione) {
        this.data_operazione = data_operazione;
    }



    public Integer getQta() {
        return qta;
    }

    public void setQta(Integer qta) {
        this.qta = qta;
    }

    public Double getSconto() {
        return sconto;
    }

    public void setSconto(Double sconto) {
        this.sconto = sconto;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getTipoOperazione() {
        return tipoOperazione;
    }

    public void setTipoOperazione(Integer tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
    }

    @Override
    public String toString() {
        return "{" +
                "idvino:" + idvino +
                ", data_operazione:'" + data_operazione.replace("T"," ") +"'" +
                ", qta:" + qta +
                ", sconto:" + sconto +
                ", importo:" + importo +
                ", descrizione:'" + descrizione + '\'' +
                ", tipoOperazione:" + tipoOperazione +
                ", ID:" + ID +
                '}';
    }
}
