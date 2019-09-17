package Models;

import API.APIC;

public class WrapperEnoteca extends Model{
    public Fornitore fornitore;
    public Enoteca enoteca;

    private String nome,stato,via,citta,regione,mail,telefono;
    private int qta_max, qta_min;


    public WrapperEnoteca(Fornitore fornitore) throws Exception { //CARICO TUTTI I DATI RELATIVI AD UNA ENOTECA
        APIC a = new APIC("enoteca");
        this.fornitore= fornitore;
        enoteca=a.get(this.fornitore.getID(),Enoteca.class);

        nome=this.fornitore.getNome();
        mail=this.fornitore.getMail();
        telefono=this.fornitore.getTelefono();
        stato=enoteca.getStato();
        regione=enoteca.getRegione();
        citta=enoteca.getCitta();
        via=enoteca.getVia();
        qta_max=this.fornitore.getQta_max();
        qta_min=this.fornitore.getQta_min();
    }

    public String getNome() {
        return nome;
    }

    public String getStato() {
        return stato;
    }

    public String getVia() {
        return via;
    }

    public String getCitta() {
        return citta;
    }

    public String getRegione() {
        return regione;
    }

    public String getMail() {
        return mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getQta_max() {
        return qta_max;
    }

    public int getQta_min() {
        return qta_min;
    }

    @Override
    public String toString() {
        return "WrapperEnoteca{" +
                "nome='" + nome + '\'' +
                ", stato='" + stato + '\'' +
                ", via='" + via + '\'' +
                ", citta='" + citta + '\'' +
                ", regione='" + regione + '\'' +
                ", mail='" + mail + '\'' +
                ", telefono='" + telefono + '\'' +
                ", qta_max=" + qta_max +
                ", qta_min=" + qta_min +
                '}';
    }
}
