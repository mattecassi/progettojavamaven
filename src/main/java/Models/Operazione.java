package Models;

import API.APIC;
import Utils.APIReturn;
import Utils.Utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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



    @Override
    public APIReturn insert() throws Exception {
        APIReturn retOperazione = super.insert();
        APIC apicVino = new APIC("vino");
        Vino v = apicVino.get(this.getIdvino(),Vino.class);
        if (this.getTipoOperazione() == 2){ //2 è l'id dell'operazione vendita
            v.setQta(v.getQta() - this.getQta());
        }else if (this.getTipoOperazione() == 1){
            v.setQta(v.getQta() + this.getQta());
        }
        v.update();
        return retOperazione;
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

    public void setData_operazione(LocalDateTime data_operazione) {
        this.data_operazione = data_operazione.format(DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm:ss"));
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
                ", data_operazione:'" + data_operazione +"'" +
                ", qta:" + qta +
                ", sconto:" + sconto +
                ", importo:" + importo +
                ", descrizione:'" + descrizione + '\'' +
                ", tipoOperazione:" + tipoOperazione +
                ", ID:" + ID +
                '}';
    }
}
