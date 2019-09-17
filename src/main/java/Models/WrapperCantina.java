package Models;

import API.APIC;
import ClientUtils.Clausola;

import java.util.ArrayList;

public class WrapperCantina extends  Model{

    public Cantina cantina;
    public Fornitore rappresentante;

    public WrapperCantina(Cantina cantina) {//CARICO TUTTI I DATI RELATIVO AD UNA CANTINA
        this.cantina = cantina;
        if (this.cantina.getIdrappresentante() != null) {
            APIC a = new APIC(Fornitore.getTableFornitoriRappresentanti());
            try {
                String[] strings = {};
                ArrayList<Clausola> clausolas = new ArrayList<>();
                clausolas.add(new Clausola("id", "=", this.cantina.getIdrappresentante().toString()));
                rappresentante = a.select(strings, clausolas).toList(Fornitore.class).get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            rappresentante=new Fornitore(0,"Sconosciuto",0,0,"Sconosciuto", "Sconosciuto");
        }
    }
}
