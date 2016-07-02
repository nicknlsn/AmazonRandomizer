/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nick
 */
public class JDBCUtils {

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
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
        if (query.substring(0, 6).toLowerCase().equals("select")) {
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
     * used to add a user to the database. 
     * @param userInfo 
     */
    public static void addUser(Properties userInfo) {
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement("INSERT INTO users (firstName, lastName, email, userName, pwd) VALUES (?,?,?,?,?)");
            pstmt.setString(1, userInfo.getProperty("firstName"));
            pstmt.setString(2, userInfo.getProperty("lastName"));
            pstmt.setString(3, userInfo.getProperty("email"));
            pstmt.setString(4, userInfo.getProperty("userName"));
            pstmt.setString(5, userInfo.getProperty("pwd"));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * runs a query based on the user input info for logging in.
     * @param userInfo
     * @return 
     */
    public static ResultSet logIn(Properties userInfo) {
        rs = null;
        try {
            pstmt = conn.prepareStatement("SELECT userName, pwd FROM users where userName=?");
            pstmt.setString(1, userInfo.getProperty("userName"));
            rs = pstmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     * Closes resources
     * this should probably be called from somewhere, but im not sure where
     * because it seems that the connection to the db should just be left open
     * while the user is logged in and doing stuff. so maybe after logging out?
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
        if (pstmt != null) {
            try {
                //System.out.println("Closing Statement...");
                pstmt.close();
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