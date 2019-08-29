package Models;

public class TipoOperazione extends Model {


    private String descrizione;

    public TipoOperazione() {
        config("tipo_operazione");
    }

    public TipoOperazione(String descrizione) {
        config("tipo_operazione");
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "{" +
                "descrizione:'" + descrizione + '\'' +
                ", ID:" + ID +
                '}';
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
