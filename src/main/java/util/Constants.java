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
 *
 * @author nick
 */
public final class Constants {

    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String dbDriver;
    
    static {
        try {
            new DevConfig().applyConfiguration(Constants.class);
        } catch (IOException ex) {
            Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
