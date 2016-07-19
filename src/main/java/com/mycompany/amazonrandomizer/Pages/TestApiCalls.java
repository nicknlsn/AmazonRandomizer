/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.Pages;

import com.mycompany.amazonrandomizer.util.AmazonAPIUtils;
import com.mycompany.amazonrandomizer.util.Constants;
import com.mycompany.amazonrandomizer.util.JDBCUtils;
import com.mycompany.amazonrandomizer.util.ZincUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nick
 */
@WebServlet(name = "TestApiCalls", urlPatterns = {"/TestApiCalls"})
public class TestApiCalls extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // now we do it like this:
//        String item = AmazonAPIUtils.getRandomItem("1000");
//        ZincUtils.placeOrder("B01587FRPM");
//        AmazonAPIUtils.buy();
        
//        try {
//
//            // NEW ATTEMPT:
//            String item = null;
//            String itemDetails = null;
//            String maxPrice = "500";
//            List<String> items = new ArrayList<>();
//            Boolean goodItem = false;
//
//            // 1. continue to loop until a good item is found
//            while (!goodItem) {
//                // 2. get a bunch of random items
//                System.out.println("getting random items");
//                // this will return at most 100 items
//                items = AmazonAPIUtils.getRandomItems(maxPrice);
//
//                // 3. loop through these 
//                System.out.println("found " + items.size() + " items");
//                for (int i = 0; i < items.size(); i++) {
//                    // now do all that stuff
//                    item = items.get(i);
//                    System.out.println("item: " + item);
//
//                    // 4. make sure this item has not been purchased before
//                    System.out.println("checking database for duplicate item");
//                    Boolean duplicateItem = false;
//                    ResultSet rs = JDBCUtils.checkForItem(item);
//                    while (rs != null && rs.next()) {
//                        if (rs.getString("productId").equals(item)) {
//                            duplicateItem = true;
//                            System.out.println("duplicate item found: " + item);
//                            break;
//                        }
//                    }
//                    if (duplicateItem) {
//                        continue; // try next item in items list
//                    }
//
//                    // 5. make sure this item is appropriate to send
//                    itemDetails = AmazonAPIUtils.getItemDetails(item);
//                    if (itemDetails != null) {
//                        System.out.println("checking for bad words");
//                        Boolean badWordFound = false;
//                        for (String word : Constants.blacklist) {
//                            if (itemDetails.contains(word)) {
//                                System.out.println("found a bad word: " + word);
//                                badWordFound = true;
//                                break;
//                            }
//                        }
//
//                        if (badWordFound) {
//                            continue; // move on to next item
//                        }
//                    }
//
//                    // 6. make sure this item has free shipping not not prime
//                    System.out.println("checking for shipping price");
//                    JSONObject productPrices = ZincUtils.getProductPrices(item);
//                    if (productPrices != null && productPrices.has("offers")) {
//                        JSONArray offers = productPrices.getJSONArray("offers");
//
//                        // we just check the first one in the array. not sure if
//                        // this is the best thing to do here but it seems that 
//                        // all the json returned from zinc puts the one with 
//                        // free shipping first
//                        Boolean prime = offers.getJSONObject(0).getBoolean("prime");
//
//                        if (prime) {
//                            System.out.println("item requires prime");
//                            continue;
//                        }
//
//                        // if prime is false then this should never be null
//                        // if it is it will throw an exception, but it does
//                        // not stop the program from running and eventually a 
//                        // good item will be found
//                        double shipPrice = offers.getJSONObject(0).getDouble("ship_price");
//                        if (shipPrice > 0) {
//                            System.out.println("item has shipping charge of " + Double.toString(shipPrice));
//                            continue;
//                        }
//                        
//                        // 7. make sure the price does not exceed the maxPrice
//                        System.out.println("checking price");
//                        String productPrice = offers.getJSONObject(0).getString("price").replace(".", "");
//                        if (Double.parseDouble(productPrice) > Double.parseDouble(maxPrice)) {
//                            System.out.println("item exceeds max price");
//                            continue;
//                        }
//                    }
//                    
//                    // if we get here then we have a good item
//                    goodItem = true;
//                    break;
//                }
//            }
//
//            // the while loop is over, so a good item has been found
//            System.out.println("good item found");
//
//        } catch (SQLException ex) {
//            Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JSONException ex) {
//            Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
//        }

        // so we will do something like this
//        int i = 0;
//        while (i++ < 1) { // keep going for testing purposes
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            Boolean goodItem = false;
//
//            while (!goodItem) { // keep going if the item is bad
//                try {
//                    Thread.sleep(500);
//
//                    List<String> items = new ArrayList<>();
//                    String item = null;
//                    String itemDetails = null;
//                    Random rand = new Random();
//
//                    // get an item
//                    System.out.println("getting an item");
//                    items = AmazonAPIUtils.getRandomItems("500");
//
//                    // loop through these items
//                    
//                    
//                    if (items.size() > 0) {
//                        item = items.get(rand.nextInt(items.size()));
//                    }
//
//                    // get the item details
//                    if (item != null) {
//                        System.out.println("found an item: " + item);
//
//                        // 1.  make sure this item has never been purchased
//                        System.out.println("checking database for duplicate item");
//                        Boolean duplicateItem = false;
//                        ResultSet rs = JDBCUtils.checkForItem(item);
//                        while (rs != null && rs.next()) {
//                            if (rs.getString("productId").equals(item)) {
//                                duplicateItem = true;
//                                System.out.println("duplicate item found: " + item);
//                                break;
//                            }
//                        }
//
//                        if (duplicateItem) {
//                            continue;
//                        }
//
////                        System.out.println("getting item details");
//                        itemDetails = AmazonAPIUtils.getItemDetails(item);
//                    } else {
//                        continue;
//                    }
//
//                    // 2.  check the details for bad words
//                    if (itemDetails != null) {
//                        System.out.println("checking for bad words");
//                        Boolean badWordFound = false;
//                        for (String word : Constants.blacklist) {
//                            if (itemDetails.contains(word)) {
//                                System.out.println("found a bad word: " + word);
//                                badWordFound = true;
//                                break;
//                            }
//                        }
//
//                        if (badWordFound) {
//                            continue;
//                        }
//                    }
//
//                    // 3,  make sure there is not a shipping charge
//                    // TODO FINISH THIS! we are getting varying json back from zinc
//                    // which makes it hard to find the information we need.
//                    System.out.println("checking for shipping price");
//                    JSONObject productPrices = ZincUtils.getProductPrices(item);
////                    System.out.println("json: " + productPrices.toString());
//                    if (productPrices != null && productPrices.has("offers")) {
//                        JSONArray offers = productPrices.getJSONArray("offers");
////                        System.out.println("offers array length: " + offers.length());
//                        Boolean prime = offers.getJSONObject(0).getBoolean("prime");
////                        System.out.println("ship price: " + Double.toString(shipPrice));
//
//                        if (prime) {
//                            System.out.println("item requires prime");
//                            continue;
//                        }
//
//                        double shipPrice = offers.getJSONObject(0).getDouble("ship_price");
//                        if (shipPrice > 0) {
//                            System.out.println("item has shipping charge of " + Double.toString(shipPrice));
//                            continue;
//                        }
//                    }
//
//                    // if we get here the item should be good
//                    goodItem = true;
//
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SQLException ex) {
//                    Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (JSONException ex) {
//                    Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//            // while loop over, a good item was found
//            System.out.println("good item found");
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
