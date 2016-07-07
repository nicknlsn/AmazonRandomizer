/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.amazonrandomizer.Pages;

import com.mycompany.amazonrandomizer.util.JDBCUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thom
 */
@WebServlet(name = "AddressHandler", urlPatterns = {"/AddressHandler"})
public class AddressHandler extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();

            int userId = (int) session.getAttribute("id");
            String budget = request.getParameter("b");
            String street1 = request.getParameter("s1");
            String street2 = request.getParameter("s2");
            String city = request.getParameter("c");
            String state = request.getParameter("s");
            String zip = request.getParameter("z");
            String country = request.getParameter("ctr");
            String phone = request.getParameter("ph");

            System.out.println(userId);
            System.out.println(budget);
            System.out.println(street1);
            System.out.println(street2);
            System.out.println(city);
            System.out.println(state);
            System.out.println(zip);
            System.out.println(country);
            System.out.println(phone);
            System.out.println(session.getAttribute("userName"));

            if (budget.equals("") || budget == null
                    || street1.equals("") || street1 == null
                    || city.equals("") || city == null
                    || state.equals("") || state == null
                    || zip.equals("") || zip == null
                    || country.equals("") || country == null
                    || phone.equals("") || phone == null) {

            } else {
                System.out.print(userId);
                Properties userAddress = new Properties();
                userAddress.put("userId", userId);
                userAddress.put("street1", street1);
                userAddress.put("street2", street2);
                userAddress.put("city", city);
                userAddress.put("state", state);
                userAddress.put("zip", zip);
                userAddress.put("country", country);
                userAddress.put("phone", phone);
                System.out.print(userAddress);

//                String query = "SELECT userId FROM address WHERE userName=\"" + userId + "\""; // SQL INJECTION VULNERABILITY!!! add a method to JDBCUtils.java to check for a registered email address
                JDBCUtils.setAddress(userAddress);
            }
//            try {
//            } //catch (SQLException ex) {
//            Logger.getLogger(Pages.SignUp.class.getName()).log(Level.SEVERE, null, ex);
        }

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
