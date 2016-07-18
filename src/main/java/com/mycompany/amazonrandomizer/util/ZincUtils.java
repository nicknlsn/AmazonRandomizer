/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

    /**
     * this method does what you think it does
     * @param item
     * @param maxPrice
     * @return 
     */
    public static JSONObject placeOrder(String item, String maxPrice) {
        JSONObject returnJson = new JSONObject();
        
        try {
            JSONObject orderJson = new JSONObject();

            // so
            // put the retailer attribute in
            orderJson.put("retailer", "amazon");
            
            // create the product object
            JSONObject product = new JSONObject();
            product.put("product_id", item);
            product.put("quantity", 1);
            // variants are not necessary, but needed for certain items
//            JSONArray variants = new JSONArray();
            
            // seller selection criteria
            // we can use this to specify a seller so we don't need to worry
            // about there being more than one seller, only problem is 
            // passing that seller id to this code from the other code
            JSONObject selSelCrit = new JSONObject();
            selSelCrit.put("max_item_price", maxPrice);
            selSelCrit.put("prime", false);
            product.put("seller_selection_criteria", selSelCrit);
            
            // put product in array
            JSONArray products = new JSONArray();
            products.put(product);
            
            // put the products array in orderJson
            orderJson.put("products", products);
            
            // put max_price in orderJson
            // 0 for testing purposes, change to maxPrice to actually buy
            orderJson.put("max_price", 0); 
            
            // create address object for shipping address
            JSONObject shippingAddress = new JSONObject();
            shippingAddress.put("first_name", "nick"); // so how do we get this info?
            
            System.out.println(orderJson);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnJson;
    }

    /**
     * this method makes a call to the zinc api to get product details. the
     * specific detail we need is the shipping cost, it needs to be zero.
     *
     * @return
     */
    public static JSONObject getProductPrices(String item) {
        JSONObject returnJson = new JSONObject();

        String productDetailsUrl = "https://api.zinc.io/v1/products/" + item + "/offers?retailer=amazon";
        String clientToken = Constants.zincClientToken + ":";

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

            InputStreamReader reader = new InputStreamReader((conn.getInputStream()));
            BufferedReader br = new BufferedReader(reader);

            String output = "";
            String jsonText = "";
//            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonText += output;
            }
//            System.out.println(jsonText);

            returnJson = new JSONObject(jsonText);

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnJson;
    }

    public static JSONObject getProductDetailsDemo() {
        JSONObject returnJson = null;
        String productDetailsUrl = "https://api.zinc.io/v1/products/B00X5RV14Y?retailer=amazon";
        String clientToken = Constants.zincClientToken + ":";

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

            InputStreamReader reader = new InputStreamReader((conn.getInputStream()));

//            jsonObject = new JSONObject(reader);
            BufferedReader br = new BufferedReader(reader);

            String output = "";
            String jsonText = "";
            System.out.println("getProductDetailsDemo: Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonText += output;
            }
            System.out.println(jsonText);

            returnJson = new JSONObject(jsonText);

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conn.disconnect();
        }

        return returnJson;
    }

    /**
     * http://docs.zincapi.com/#place-an-order
     * https://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily
     */
    public static JSONObject testPlaceOrder() {
        JSONObject returnJson = null;

        JSONObject data = new JSONObject(); // will hold all the objects
        JSONArray products = new JSONArray(); // list of products to buy, maybe just use  List products<JSONObject>?
        JSONObject shipping_address = new JSONObject(); // address object
        String shipping_method; // cheapest, fastest, or free
        JSONObject billing_address = new JSONObject(); // address object
        JSONObject payment_method = new JSONObject(); // payment method object
        JSONObject retailer_credentials = new JSONObject(); // retailer credentials object

        String createOrderUrl = "https://api.zinc.io/v1/orders";
        URL url = null;
        HttpURLConnection conn = null;
        String clientToken = Constants.zincClientToken + ":";

        try {
            JSONObject productDetails = getProductDetailsDemo();
            System.out.println("from place order: ");

            String productId = productDetails.getString("asin");
//            System.out.println("product id: " + productId);

            // set up json objects -- need to do this still, obviously
            // make post request
            url = new URL(createOrderUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            String encoded = new String(new Base64().encode(clientToken.getBytes()));
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            byte[] postData = data.toString().getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }

            if (conn.getResponseCode() != 200) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
            }

            InputStreamReader reader = new InputStreamReader((conn.getInputStream()));

            BufferedReader br = new BufferedReader(reader);

            String output = "";
            String jsonText = "";
            System.out.println("testPlaceOrder: Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonText += output;
            }
            System.out.println(jsonText); // IT WORKS!!!

            returnJson = new JSONObject(jsonText);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnJson;
    }
}
