package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableInfoRow {

    protected Integer cid;
    protected String name;
    protected String type;
    protected boolean notnull;
    protected String dflt_value;
    protected boolean pk;


    public TableInfoRow(ResultSet rs) {

        try {
            this.cid = rs.getInt("cid");

            this.name = rs.getString("name");
            this.type = rs.getString("type");


            if (rs.getInt("notnull") == 1)
                this.notnull = true;
            else
                this.notnull = false;


            this.dflt_value = rs.getString("dflt_value");
            if (rs.getInt("pk") == 1)
                this.pk = true;
            else
                this.pk = false;
        }catch (SQLException e){
            System.out.println("Errore creazione TABLEINFOROW" + e.getLocalizedMessage());
            return ;
        }
    }

    public TableInfoRow(Integer cid, String name, String type, int notnull, String dflt_value, int pk) {

        this.cid = cid;

        this.name = name;
        this.type = type;


        if (notnull == 1)
            this.notnull = true;
        else
            this.notnull = false;


        this.dflt_value = dflt_value;
        if (pk == 1)
            this.pk = true;
        else
            this.pk = false;

    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNotnull() {
        return notnull;
    }

    public void setNotnull(boolean notnull) {
        this.notnull = notnull;
    }

    public String getDflt_value() {
        return dflt_value;
    }

    public void setDflt_value(String dflt_value) {
        this.dflt_value = dflt_value;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }
}
