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
 * @author nick
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

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
        try {
//            System.out.println(request.getParameter("fn"));
//            System.out.println(request.getParameter("ln"));
//            System.out.println(request.getParameter("e"));
//            System.out.println(request.getParameter("un"));
//            System.out.println(request.getParameter("p"));

            String firstName = request.getParameter("fn");
            String lastName = request.getParameter("ln");
            String email = request.getParameter("e");
            String userName = request.getParameter("un");
            String password = request.getParameter("p");

            if (firstName.equals(null) || lastName.equals(null)
                    || email.equals(null) || userName.equals(null)
                    || password.equals(null) || firstName.equals("")
                    || lastName.equals("") || email.equals("")
                    || userName.equals("") || password.equals("")) {
                response.sendError(500);
            } else {
                // must have all this info to add the user
                Properties userInfo = new Properties();
                userInfo.put("firstName", firstName);
                userInfo.put("lastName", lastName);
                userInfo.put("email", email);
                userInfo.put("userName", userName);
                userInfo.put("pwd", password);

                // TODO more server side sanitization and validation here
                // make sure the email address isn't already registered
                String query = "SELECT email FROM users WHERE email=\"" + userInfo.getProperty("email") + "\""; // SQL INJECTION VULNERABILITY!!! add a method to JDBCUtils.java to check for a registered email address
                ResultSet rs = JDBCUtils.getResultSet(query);

                HttpSession session = request.getSession();

                if (rs.next()) { // if email is already there
                    response.getWriter().write("email_error");
                    session.setAttribute("loggedin", false);
                } else { // if email is good to use
                    // insert new user into database
                    JDBCUtils.addUser(userInfo);

                    session.setAttribute("userName", userName);
                    session.setAttribute("loggedin", true);
                    
                    query = "SELECT id FROM users WHERE userName=\"" + userName + "\"";
                    ResultSet userId = JDBCUtils.getResultSet(query);
                    userId.next();
                    int id = userId.getInt(1);
                    session.setAttribute("id", id);
                    // now redirect to the login page or the home page
//                response.sendRedirect("index.html");
//                request.getRequestDispatcher("index.html").forward(request, response); // test this more
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(SignUp.class.getName()).log(Level.SEVERE, null, ex);
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
