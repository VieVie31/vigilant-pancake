/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import controller.FuncTools;
import entity.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mac
 */
public class CustomerProfile extends HttpServlet {

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
        //not logged --> do nothing SofB !!
        if (!FuncTools.checkSessionLogin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String command = (String) request.getParameter("command");
        
        Customer customer;
        
        try {
            customer = FuncTools.getGeneralDAO().getCustomerByEmail(email);
        } catch (SQLException ex) {
            out.print("{action_status : \"fail\"}");
            return;
        }
        
        if (customer == null) {
            out.print("{action_status : \"fail\"}");
            return;
        }
        
        /*
        ADDRESSLINE1 = ?, "
               + "ADDRESSLINE2 = ?, "
               + "CITY = ?, "
               + "STATE = ?, "
               + "PHONE = ?, "
               + "FAX = ?, "
               + "EMAIL = ? "
               + "WHERE EMAIL = ?";
        */
        switch (command) {
            case "CHANGE_NAME":
                try {
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerName(
                            email,
                            (String) request.getParameter("value")) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_ADDRESSLINE1":
                try {
                    customer.setAdress_line_1((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_ADDRESSLINE2":
                try {
                    customer.setAdress_line_2((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_CITY":
                try {
                    customer.setCity((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_STATE":
                try {
                    customer.setState((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_PHONE":
                try {
                    customer.setPhone((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_FAX":
                try {
                    customer.setFax((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            case "CHANGE_EMAIL":
                try {
                    customer.setEmail((String) request.getParameter("value"));
                    out.print(
                        FuncTools.getGeneralDAO().changeCustomerInfo(
                            email,
                            customer) ?
                        "{action_status : \"done\"}" :
                        "{action_status : \"fail\"}");
                    
                    //update the email to kept logged the guy...
                    session.setAttribute("email", (String) request.getParameter("value"));
                } catch (Exception e) {
                    out.print("{\"action_status\" : \"fail\"}");
                }
                break;
            default:
                out.print("{\"action_status\" : \"nothing_done\"}");
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
