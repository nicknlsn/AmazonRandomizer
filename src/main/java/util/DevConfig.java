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

    void applyConfiguration(Class<Constants> aClass) {
        try {
            Constants constants = aClass.newInstance();

            InputStream inputstream;

            // load dev.properties and populate Constants with it's data
            Properties dev = new Properties();
            String filename = "dev.properties";

            inputstream = getClass().getClassLoader().getResourceAsStream(filename);

            if (inputstream == null) {
                //throw new FileNotFoundException("property file '" + filename + "' not found in the classpath");
                // load openshift stuff here? im thinking of doing something like this
                constants.dbDriver = dev.getProperty("com.mysql.jdbc.Driver");
                constants.dbUrl = System.getenv("OPENSHIFT_MYSQL_DB_URL"); // do i need to add the database name here?
                constants.dbUsername = dev.getProperty("OPENSHIFT_MYSQL_DB_USERNAME");
                constants.dbPassword = dev.getProperty("OPENSHIFT_MYSQL_DB_PASSWORD");
            } else {
                dev.load(inputstream);

                constants.dbDriver = dev.getProperty("dbDriver");
                constants.dbUrl = dev.getProperty("dbUrl");
                constants.dbUsername = dev.getProperty("dbUsername");
                constants.dbPassword = dev.getProperty("dbPassword");
            }

            inputstream.close();
        } catch (IOException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(DevConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
