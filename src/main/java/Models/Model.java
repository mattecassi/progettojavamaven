package Models;

import API.API;
import API.APIC;
import ClientUtils.UpdateElem;
import Utils.APIReturn;
import org.json.JSONArray;

import java.util.List;

public abstract class Model {

    protected String table;
    protected APIC apic;

    public abstract APIReturn insert();
    public abstract APIReturn delete();
    public abstract APIReturn update();

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public APIC getApic() {
        return apic;
    }

    public void setApic(APIC apic) {
        this.apic = apic;
    }


}
