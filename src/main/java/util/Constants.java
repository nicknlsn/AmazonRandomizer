/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * use this class to store all kinds of variables that we need throughout the app
 * @author nick
 */
public final class Constants {

    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String dbDriver;
    
    static {
            new DevConfig().applyConfiguration(Constants.class);
    }
}
