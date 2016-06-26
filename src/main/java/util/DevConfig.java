/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nick
 */
public class DevConfig {

    void applyConfiguration(Class<Constants> aClass) throws IOException {
        try {
            Constants c = aClass.newInstance();
            
            InputStream inputstream;
            
            // load dev.properties and populat Constants with it's data
            Properties devProperties = new Properties();
            String filename = "dev.properties";
            
            inputstream = getClass().getClassLoader().getResourceAsStream(filename);
            
            if (inputstream != null) {
                devProperties.load(inputstream);
            } else {
                throw new FileNotFoundException("property file '" + filename + "' not found in the classpath");
            }
            
            c.dbDriver = devProperties.getProperty("dbDriver");
            c.dbUrl = devProperties.getProperty("dbUrl");
            c.dbUsername = devProperties.getProperty("dbUsername");
            c.dbPassword = devProperties.getProperty("dbPassword");
            
            inputstream.close();
        } catch (InstantiationException ex) {
            Logger.getLogger(DevConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DevConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
