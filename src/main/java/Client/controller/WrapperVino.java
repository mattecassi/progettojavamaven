package Client.controller;

import API.APIC;
import Models.Cantina;
import Models.Fornitore;
import Models.Vino;

public class WrapperVino {

    Vino vino;
    Cantina cantina;
    Fornitore fornitore;

    public WrapperVino(Vino vino) throws Exception {
        APIC a = new APIC("cantina");
        APIC b = new APIC("fornitore");
        this.vino= vino;
        cantina=a.get(vino.getIdCantina(),Cantina.class);
        //fornitore=b.get(vino.getIdFornitore(),Fornitore.class);
    }

}
