package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    protected String connectionString;
    protected String databaseName;
    protected Connection connection;



    public GetConnection(){
        this.connectionString = "jdbc:sqlite:D:/db2.db";
        this.connection = connect();
    }

    public GetConnection(String connectionString) {
        this.connectionString = connectionString;
        this.connection = connect();
    }

    protected Connection connect(){

        Connection conn = null;
        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(this.getConnectionString());

//            System.out.println("Connection to SQLite has been established.");

            return  conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  null;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
