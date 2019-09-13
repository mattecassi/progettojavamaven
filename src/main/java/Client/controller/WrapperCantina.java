package Client.controller;

import API.APIC;
import ClientUtils.Clausola;
import Models.Cantina;
import Models.Fornitore;
import Models.Rappresentante;

import java.util.ArrayList;

public class WrapperCantina {

    Cantina cantina;
    Fornitore rappresentante;

    public WrapperCantina(Cantina cantina) {
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
