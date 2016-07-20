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
import java.util.Map;
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
     * http://docs.zincapi.com/#place-an-order
     *
     * @param item
     * @param maxPrice
     * @return
     */
    public static JSONObject placeOrder(Map<String, String> orderDetails) {
        JSONObject responseJson = new JSONObject();

        System.out.println("building json");

        try {
            JSONObject orderJson = new JSONObject();

            /* 
            required attributes: 
            1. retailer
            2. products
            3. shipping_address
            4. shipping_method
            5. billing_address
            6. payment_method
            7. retailer_credentials
            
            /* other attributes we will use:
             8. max_price 
            
             */
            // 1.
            // put the retailer attribute in
            orderJson.put("retailer", "amazon");

            // 2.
            // create the products array
            JSONArray products = new JSONArray();
            JSONObject product = createProductObject(orderDetails);
            // put product in array
            products.put(product);
            // put the products array in orderJson
            orderJson.put("products", products);

            // 3. 
            // create shipping address object
            JSONObject shipAddress = createShipAddressObject(orderDetails);
            orderJson.put("shipping_address", shipAddress);

            // 4. 
            // insert shipping method attribute
            orderJson.put("shipping_method", "cheapest");

            // 5.
            // create billing address object
            JSONObject billAddress = createBillAddressObject(orderDetails);
            orderJson.put("billing_address", billAddress);

            // 6. 
            // create payment method object
            JSONObject paymentMethod = createPaymentMethod(orderDetails);
            orderJson.put("payment_method", paymentMethod);

            // 7. 
            // create retailer credentials object
            JSONObject retailerCredentials = createRetailerCredentialsObject();
            orderJson.put("retailer_credentials", retailerCredentials);

            // 8.
            // put max_price in orderJson
            // NOTE: this is the last thing that will prevent an order from 
            // actually going through. use 0 for testing purposes, change to 
            // maxPrice to actually put the order through
            orderJson.put("max_price", 0); // replace with: orderDetails.get("maxPrice")

            //===--- finally: send the json to the api ---===//
            responseJson = sendRequest(orderJson);
            //===-----------------------------------------===//

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return responseJson;
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
            // make post request -- this is where the order is placed
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

    private static JSONObject createProductObject(Map<String, String> orderDetails) {
        JSONObject product = new JSONObject();

        try {

            // TODO create the product object
            product.put("product_id", orderDetails.get("item"));
            product.put("quantity", 1);
            // variants are not necessary, but needed for certain items
            // im not sure if they are required for items that have variant
            // options
//            JSONArray variants = new JSONArray();

            // seller selection criteria
            JSONObject selSelCrit = createSellerSelectionCriteriaObject(orderDetails);
            product.put("seller_selection_criteria", selSelCrit);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

    private static JSONObject createSellerSelectionCriteriaObject(Map<String, String> orderDetails) {
        JSONObject selSelCrit = new JSONObject();

        try {

            // seller selection criteria
            // we can use this to specify a seller so we don't need to worry
            // about there being more than one seller, only problem is 
            // passing that seller id to this code from the other code
            selSelCrit.put("max_item_price", orderDetails.get("maxPrice"));
            selSelCrit.put("prime", false);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return selSelCrit;
    }

    private static JSONObject createShipAddressObject(Map<String, String> orderDetails) {
        JSONObject shipAddress = new JSONObject();

        try {

            // TODO finish this
            shipAddress.put("first_name", orderDetails.get("first_name"));
            shipAddress.put("last_name", orderDetails.get("last_name"));
            shipAddress.put("address_line1", orderDetails.get("address_street"));
            shipAddress.put("address_line2", "");
            shipAddress.put("zip_code", orderDetails.get("address_zip"));
            shipAddress.put("city", orderDetails.get("address_city"));
            shipAddress.put("state", orderDetails.get("address_state"));
            shipAddress.put("country", orderDetails.get("address_country_code"));
            shipAddress.put("phone_number", "4803131685"); // just use my phone number for now...

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return shipAddress;
    }

    private static JSONObject createBillAddressObject(Map<String, String> orderDetails) {
        JSONObject billAddress = new JSONObject();

        try {

            billAddress.put("first_name", "Thom");
            billAddress.put("last_name", "Allen");
            billAddress.put("address_line1", "1119 S 12th St.");
            billAddress.put("address_line2", "");
            billAddress.put("zip_code", "81401");
            billAddress.put("city", "Montrose");
            billAddress.put("state", "CO");
            billAddress.put("country", "US");
            billAddress.put("phone_number", "4803131685"); // just use my phone number for now...

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return billAddress;
    }

    private static JSONObject createPaymentMethod(Map<String, String> orderDetails) {
        JSONObject paymentMethod = new JSONObject();

        try {

            paymentMethod.put("name_on_card", Constants.nameOnCard);
            paymentMethod.put("number", Constants.cardNumber);
            paymentMethod.put("security_code", Constants.securityCode);
            paymentMethod.put("expiration_month", Constants.expMonth);
            paymentMethod.put("expiration_year", Constants.expYear);
            paymentMethod.put("use_gift", false);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return paymentMethod;
    }

    private static JSONObject createRetailerCredentialsObject() {
        JSONObject retailerCredentials = new JSONObject();

        try {

            retailerCredentials.put("email", Constants.amazonEmail);
            retailerCredentials.put("password", Constants.amazonPassword);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retailerCredentials;
    }

    private static JSONObject sendRequest(JSONObject orderJson) {
        JSONObject responseJson = new JSONObject();

        // for testing
        System.out.println(orderJson.toString());

        String createOrderUrl = "https://api.zinc.io/v1/orders";
        URL url = null;
        HttpURLConnection conn = null;
        String clientToken = Constants.zincClientToken + ":";

        try {
            // make post request -- this is where the order is placed
            url = new URL(createOrderUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            String encoded = new String(new Base64().encode(clientToken.getBytes()));
            conn.setRequestProperty("Authorization", "Basic " + encoded);

            byte[] postData = orderJson.toString().getBytes(StandardCharsets.UTF_8);
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
            System.out.println("placeOrder: Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                jsonText += output;
            }
            System.out.println(jsonText); // IT WORKS!!!

            responseJson = new JSONObject(jsonText);

        } catch (JSONException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZincUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return responseJson;
    }
}
