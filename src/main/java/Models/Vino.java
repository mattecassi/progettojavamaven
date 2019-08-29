package Models;

import Utils.APIReturn;

public class Vino extends Model {

    //Inserire tutti i campi
    private String nome;
    private Integer idCantina;
    private Integer anno;
    private String tipo;
    private Integer qta; //quantità che possiedo
    private Double costo;//quanto l'ho pagato
    private Double prezzoVendita; //prezzo di vendita dell'oggetto

    public Vino() {
        config("vino");
    }

    public Vino(Integer ID, String nome, Integer idCantina, Integer anno, String tipo, Integer qta, Double costo, Double prezzoVendita) {
        config("vino");
        this.ID = ID;
        this.nome = nome;
        this.idCantina = idCantina;
        this.anno = anno;
        this.tipo = tipo;
        this.qta = qta;
        this.costo = costo;
        this.prezzoVendita = prezzoVendita;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdCantina() {
        return idCantina;
    }

    public void setIdCantina(Integer idCantina) {
        this.idCantina = idCantina;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQta() {
        return qta;
    }

    public void setQta(Integer qta) {
        this.qta = qta;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPrezzoVendita() {
        return prezzoVendita;
    }

    public void setPrezzoVendita(Double prezzoVendita) {
        this.prezzoVendita = prezzoVendita;
    }

    @Override
    public String toString() {
        return "{" +
                "nome:'" + nome + '\'' +
                ", idCantina:" + idCantina +
                ", anno:" + anno +
                ", tipo:'" + tipo + '\'' +
                ", qta:" + qta +
                ", costo:" + costo +
                ", prezzoVendita:" + prezzoVendita +
                ", ID:"+ this.getID() +
                '}';
    }
}
