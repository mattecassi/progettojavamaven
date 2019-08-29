package Models;

public class Cantina extends Model{

    private String nome;
    private String regione;
    private String stato;
    private String via;
    private String uvaggio;
    private Integer idrappresentante;


    public Cantina() {
        config("cantina");
    }

    public Cantina(Integer ID, String nome, String regione, String stato, String via, String uvaggio, Integer idrappresentante) {
        config("cantina");
        this.ID = ID;
        this.nome = nome;
        this.regione = regione;
        this.stato = stato;
        this.via = via;
        this.uvaggio = uvaggio;
        this.idrappresentante = idrappresentante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getUvaggio() {
        return uvaggio;
    }

    public void setUvaggio(String uvaggio) {
        this.uvaggio = uvaggio;
    }

    public Integer getIdrappresentante() {
        return idrappresentante;
    }

    public void setIdrappresentante(Integer idrappresentante) {
        this.idrappresentante = idrappresentante;
    }

    @Override
    public String toString() {
        return "{" +
                "nome:'" + nome + '\'' +
                ", regione:'" + regione + '\'' +
                ", stato:'" + stato + '\'' +
                ", via:'" + via + '\'' +
                ", uvaggio:'" + uvaggio + '\'' +
                ", idrappresentante:" + idrappresentante +
                '}';
    }
}
