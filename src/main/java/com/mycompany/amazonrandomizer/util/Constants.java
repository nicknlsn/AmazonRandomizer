/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import java.util.List;

/**
 * use this class to store all kinds of variables that we need throughout the
 * app
 *
 * @author nick
 */
public final class Constants {

    // database credentials
    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String dbDriver;

    // zinc client token
    public static String zincClientToken;

    // amazon api credentials
    public static String awsAccessKeyId;
    public static String awsSecretKey;
    public static String awsAssociateTag;
    public static String awsEndpoint;

    // amazon login credentials
    public static String amazonEmail;
    public static String amazonPassword;

    // credit card information
    public static String nameOnCard;
    public static String cardNumber;
    public static String securityCode;
    public static int expMonth;
    public static int expYear;

    // bad words
    public static List<String> blacklist;

    static {
        new DevConfig().applyConfiguration(Constants.class);
    }
}
