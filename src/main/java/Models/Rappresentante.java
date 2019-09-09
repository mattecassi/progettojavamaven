package Models;

import API.APIC;
import Utils.APIReturn;

public class Rappresentante extends Model {



    public Rappresentante() {
        config("rappresentante");
    }

    @Override
    public APIReturn insert() throws Exception {
        checkBeforeInsert("Fornitore");
        return super.insert();
    }



    /**
     * Funzione che ritorna il fornitore per ottenere le cose di cui ho bisogno
     * @return
     * @throws Exception
     */
    public Fornitore getFornitore() throws Exception{
        if (this.ID == null)
            throw new Exception("ID FORNITORE NON SPECIFICATO");
        return this.apic.get(this.getID(),Fornitore.class);
    }






    @Override
    public String toString() {
        return "{" +
                " ID:" + ID +
                '}';
    }
}
