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
import java.util.Arrays;
import java.util.List;
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

//        ZincUtils.getProductDetailsDemo();
//        ZincUtils.testPlaceOrder();
//        response.sendRedirect(AmazonAPIUtils.getRandomItem());
//        System.out.println("random item: " + AmazonAPIUtils.getRandomItem());
//        String item = AmazonAPIUtils.getRandomItem();
//        System.out.println("random item: " + item);
//        System.out.println("item details: " + AmazonAPIUtils.getItemDetails(item));
        // so we will do something like this
//        List<String> blacklist = Arrays.asList("swear", "swear word");
        int i = 0;
        while (i++ < 1) { // keep going for testing purposes
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
            }
            Boolean goodItem = false;

            while (!goodItem) { // keep going if the item is bad
                try {
                    Thread.sleep(500);

                    String item = null;
                    String itemDetails = null;

                    // get an item
                    System.out.println("getting an item");
                    item = AmazonAPIUtils.getRandomItem();

                    // get the item details
                    if (item != null) {
                        System.out.println("found an item: " + item);

                        // make sure this item has never been purchased
                        System.out.println("checking database for duplicate item");
                        Boolean duplicateItem = false;
                        ResultSet rs = JDBCUtils.checkForItem(item);
                        while (rs != null && rs.next()) {
                            if (rs.getString("productId").equals(item)) {
                                duplicateItem = true;
                                System.out.println("duplicate item found: " + item);
                                break;
                            }
                        }

                        if (duplicateItem) {
                            continue;
                        }

                        System.out.println("getting item details");
                        itemDetails = AmazonAPIUtils.getItemDetails(item);
                    } else {
                        continue;
                    }

                    // check the details for bad words
                    if (itemDetails != null) {
                        System.out.println("checking for bad words");
                        Boolean badWordFound = false;
                        for (String word : Constants.blacklist) {
                            if (itemDetails.contains(word)) {
                                System.out.println("found a bad word: " + word);
                                badWordFound = true;
                                break;
                            }
                        }

                        if (badWordFound) {
                            continue;
                        }
                    }

                    // make sure there is not a shipping charge
                    System.out.println("checking for shipping price");
                    JSONObject productPrices = ZincUtils.getProductPrices(item);
//                    System.out.println("json: " + productPrices.toString());
                    if (productPrices != null && productPrices.has("offers")) {
                        JSONArray offers = productPrices.getJSONArray("offers");
//                        JSONObject offersObject = offers.toJSONObject(offers);
                        System.out.println("offers: " + offers.toString());
//                        System.out.println("offersOject json: " + offersObject.toString());
//                        if (offersObject != null && offersObject.has("ship_price")) {
//                            int shipPrice = offersObject.getInt("ship_price");
//                            System.out.println("ship price: " + shipPrice);
//                        }
                    }

                    // if we get here the item should be good
                    goodItem = true;

                } catch (InterruptedException ex) {
                    Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(TestApiCalls.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // while loop over, a good item was found
            System.out.println("good item found");
        }
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
