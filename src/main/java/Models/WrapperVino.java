package Models;

import API.APIC;

public class WrapperVino extends  Model {

    public Vino vino;
    public Cantina cantina;
    public Fornitore fornitore;

    private String vinoNome, cantinaNome, fornitoreNome, tipo, uvaggio, stato, regione;
    private double costo, prezzoVendita;
    private int annata, qta;

    public WrapperVino(Vino vino) throws Exception { //CARICO TUTTI I DATI RELATIVI AD UN VINO
        APIC a = new APIC("cantina");
        APIC b = new APIC("fornitore");
        this.vino= vino;
        cantina=a.get(vino.getIdCantina(),Cantina.class);
        fornitore=b.get(vino.getIdFornitore(),Fornitore.class);

        vinoNome=this.vino.getNome();
        tipo=this.vino.getTipo();
        costo=this.vino.getCosto();
        prezzoVendita=this.vino.getPrezzoVendita();
        annata=this.vino.getAnno();
        qta=this.vino.getQta();

        cantinaNome=cantina.getNome();
        stato=cantina.getStato();
        regione=cantina.getRegione();
        uvaggio=cantina.getUvaggio();

        fornitoreNome=fornitore.getNome();

    }

    public String getVinoNome() {
        return vinoNome;
    }

    public String getCantinaNome() {
        return cantinaNome;
    }

    public String getFornitoreNome() {
        return fornitoreNome;
    }

    public String getTipo() {
        return tipo;
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

    public double getCosto() {
        return costo;
    }

    public double getPrezzoVendita() {
        return prezzoVendita;
    }

    public int getAnnata() {
        return annata;
    }

    public int getQta() {
        return qta;
    }
}
