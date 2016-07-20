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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Thom
 */
@WebServlet(name = "GetAddress", urlPatterns = {"/GetAddress"})
public class GetAddress extends HttpServlet {

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
            try {

                HttpSession session = request.getSession();

                String userName = (String) session.getAttribute("userName");
                int userId = (int) session.getAttribute("id");

                String query = "SELECT id FROM users WHERE id=\"" + userId + "\" AND userName=\"" + userName + "\""; // SQL INJECTION VULNERABILITY!!! add a method to JDBCUtils.java to check for a registered email address
                ResultSet rs = JDBCUtils.getResultSet(query);
//                System.out.println(rs.next());
                if (rs.next()) {
                    // User exists
                    ResultSet address = JDBCUtils.getAddress(userName, userId);
                    JSONObject obj = new JSONObject();
                    if (address.next()) {
                        System.out.println(address.getString("street1"));
                        System.out.println(address.getString("street2"));
                        System.out.println(address.getString("city"));
                        System.out.println(address.getString("state"));
                        System.out.println(address.getString("zip"));
                        System.out.println(address.getString("country"));
                        System.out.println(address.getString("phone"));

                        obj.put("s1", address.getString("street1"));
                        obj.put("s2", address.getString("street2"));
                        obj.put("c", address.getString("city"));
                        obj.put("s", address.getString("state"));
                        obj.put("z", address.getString("zip"));
                        obj.put("ctr", address.getString("country"));
                        obj.put("ph", address.getString("phone"));
                        response.getWriter().write(obj.toString());
                    }

                } else {
                    // User doesn't exist
                }
            } catch (SQLException ex) {
                Logger.getLogger(GetAddress.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(GetAddress.class.getName()).log(Level.SEVERE, null, ex);
            }
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
