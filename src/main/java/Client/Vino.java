package Client;

public class Vino {
    String nome,tipo,fornitore,cantina,uvaggio,stato,regione;
    int qta, annata;

    public Vino(String nome, String tipo, String fornitore, String cantina, String uvaggio, String stato, String regione, int qta, int annata) {
        this.nome = nome;
        this.tipo = tipo;
        this.fornitore = fornitore;
        this.cantina = cantina;
        this.uvaggio = uvaggio;
        this.stato = stato;
        this.regione = regione;
        this.qta = qta;
        this.annata = annata;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFornitore() {
        return fornitore;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }

    public void setCantina(String cantina) {
        this.cantina = cantina;
    }

    public void setUvaggio(String uvaggio) {
        this.uvaggio = uvaggio;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public void setQta(int qta) {
        this.qta = qta;
    }

    public void setAnnata(int annata) {
        this.annata = annata;
    }

    public String getCantina() {
        return cantina;
    }

    public String getUvaggio() {
        return uvaggio;
    }

    public String getStato() {
        return stato;
    }

    public String getRegione() {
        return regione;
    }

    public int getQta() {
        return qta;
    }

    public int getAnnata() {
        return annata;
    }

    public Vino() {

    }

    @Override
    public String toString() {
        return "Vino{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fornitore='" + fornitore + '\'' +
                ", cantina='" + cantina + '\'' +
                ", uvaggio='" + uvaggio + '\'' +
                ", stato='" + stato + '\'' +
                ", regione='" + regione + '\'' +
                ", qta=" + qta +
                ", annata=" + annata +
                '}';
    }
}
