/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * demonstrate calling the api to get details about a product.
 *
 * @author nick
 */
public class ZincUtils {

    public static JSONObject getProductDetailsDemo() {
        JSONObject jsonObject = null;
        String productDetailsUrl = "https://api.zinc.io/v1/products/B00X5RV14Y?retailer=amazon";
        final String clientToken = Constants.zincClientToken + ":";

        URL url = null;
        HttpURLConnection conn = null;

        try {
            url = new URL(productDetailsUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json"); // maybe i don't need this?

            String encoded = new String(new Base64().encode(clientToken.getBytes()));
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            if (conn.getResponseCode() != 200) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
            }

            jsonObject = new JSONObject(new InputStreamReader((conn.getInputStream())));
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return jsonObject;
    }

    public static void testPlaceOrder() {
        JSONObject data = new JSONObject();
        JSONObject product = new JSONObject();

        JSONObject productDetails = getProductDetailsDemo();

//        System.out.println(productId);
    }
}
