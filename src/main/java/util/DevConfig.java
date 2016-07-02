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

            String onOpenshift = null;
            onOpenshift = System.getenv("OPENSHIFT_MYSQL_DB_HOST");

            if (onOpenshift != null) {
                // ok, so, this will create the right credentials to access the database at openshift
                constants.dbDriver = "com.mysql.jdbc.Driver";
                constants.dbUrl = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/AmazonRandomizer"; // for some reason OPENSHIFT_MYSQL_DB_URL doesn't work
                constants.dbUsername = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
                constants.dbPassword = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
            } else {
                // this will do it for the local dev environment. you'll need to put your local db creds in dev.properties on your machine
                Properties dev = new Properties();
                String filename = "dev.properties";
                inputstream = getClass().getClassLoader().getResourceAsStream(filename);
                dev.load(inputstream);

                constants.dbDriver = dev.getProperty("dbDriver");
                constants.dbUrl = dev.getProperty("dbUrl");
                constants.dbUsername = dev.getProperty("dbUsername");
                constants.dbPassword = dev.getProperty("dbPassword");
                
                inputstream.close();
            }
        } catch (IOException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(DevConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
