package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForeignKey {

    protected String table;
    protected String from;
    protected String to;


    public ForeignKey(ResultSet rs) throws SQLException {
            setTable(rs.getString("table"));
            setFrom(rs.getString("from"));
            setTo(rs.getString("to"));

    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
