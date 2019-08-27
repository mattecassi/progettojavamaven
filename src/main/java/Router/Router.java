package Router;

import DAO.DAO;

import java.sql.SQLException;

public class Router {

    protected String table; //tabella con la quale si andrà ad interagire
    protected DAO dao;
    protected String action; //cosa andrò a fare



    public Router(String table) {
        this.table = table;
    }





}
