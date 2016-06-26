/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nick
 */
public class JDBCUtils {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    /**
     * Opens a connection only if it is not open yet
     */
    private static Connection getConnection() {
        if (conn == null) {
            System.out.println("Connecting to database...");
//            System.out.println("dbDriver: " + Constants.dbDriver);
//            System.out.println("dbuUrl: " + Constants.dbUrl);
//            System.out.println("dbuUsername: " + Constants.dbUsername);
//            System.out.println("dbuPassword: " + Constants.dbPassword);

            try {
                Class.forName(Constants.dbDriver);
                conn = DriverManager.getConnection(Constants.dbUrl, Constants.dbUsername, Constants.dbPassword);
//                System.out.println("dbUrl: " + Constants.dbUrl);
                System.out.println("Successfully connected!!!");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }

    /**
     * Returns the result set of a query, returns null if not a 'select...'
     * query
     *
     * @param query
     * @return
     */
    public static ResultSet getResultSet(String query) {
        rs = null;
        if (query.substring(0, 6).equals("select")) {
            conn = getConnection();
            try {
                System.out.println("Executing query: " + query);
                rs = conn.createStatement().executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /**
     * Used for inserting, updating, or deleting records
     *
     * @param query
     * @throws SQLException
     */
    public static void execute(String query) {
        conn = getConnection();
        try {
            System.out.println("Query: " + query);
            conn.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes resources
     */
    public static void closeAll() {
        if (conn != null) {
            //System.out.println("Closing connection...");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                //System.out.println("Closing Statement...");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                //System.out.println("Closing result set...");
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
