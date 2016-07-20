/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.util;

import com.paypal.core.LoggingManager;
import com.paypal.ipn.IPNMessage;
import com.sample.util.Configuration;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nick
 */
@WebServlet(name = "IPNListener", urlPatterns = {"/IPNListener"})
public class IPNListener extends HttpServlet {

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
        
        Map<String, String> configurationMap = Configuration.getConfig();
        IPNMessage ipnListener = new IPNMessage(request, configurationMap);
        boolean isIpnVerified = ipnListener.validate();
        String transactionType = ipnListener.getTransactionType();
        Map<String, String> map = ipnListener.getIpnMap();
        
        
        
//        LoggingManager.info(IPNListener.class, "****** IPN (name:value) pair : " 
//        + map + " " + "########### TransactionType : " + transactionType + 
//                " ================ IPN verified : " + isIpnVerified);
        
        // test the stuff from the IPN simulator i think
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        } // IT WORKS!
        
        // TODO
        // we should probably add an entry to the database to store the fact
        // that we got money from someone, and then store another entry in the
        // database for when they are sent an item, that way we can keep track
        // of whether or not orders that have been ordered have actually gone out
        
        // send the order off to the buy method, send the whole map so we can get
        // order details from it
        AmazonAPIUtils.buy(map);
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
