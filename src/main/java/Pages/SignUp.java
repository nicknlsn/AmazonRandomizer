/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

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
import util.JDBCUtils;

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
            
            String userName = request.getParameter("un");

            // must have all this info to add the user
            Properties userInfo = new Properties();
            userInfo.put("firstName", request.getParameter("fn"));
            userInfo.put("lastName", request.getParameter("ln"));
            userInfo.put("email", request.getParameter("e"));
            userInfo.put("userName", userName);
            userInfo.put("pwd", request.getParameter("p"));
            
            // TODO more server side sanitization and validation here

            // make sure the email address isn't already registered
            String query = "SELECT email FROM users WHERE email=\"" + userInfo.getProperty("email") + "\""; // SQL INJECTION VULNERABILITY!!! add a method to JDBCUtils.java to check for a registered email address
            ResultSet rs = JDBCUtils.getResultSet(query);
            if (rs.next()) { // if email is already there
                response.getWriter().write("email_error");
            } else { // if email is good to use
                // insert new user into database
                HttpSession session = request.getSession();
                session.setAttribute("userName", userName);
                JDBCUtils.addUser(userInfo);
                
                // now redirect to the login page or the home page
//                response.sendRedirect("index.html");
//                request.getRequestDispatcher("index.html").forward(request, response); // test this more
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
