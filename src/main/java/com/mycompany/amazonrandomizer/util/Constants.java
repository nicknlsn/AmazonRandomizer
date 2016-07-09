/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

/**
 * use this class to store all kinds of variables that we need throughout the app
 * @author nick
 */
public final class Constants {

    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String dbDriver;
    
    public static String zincClientToken;
    
    static {
            new DevConfig().applyConfiguration(Constants.class);
    }
}
