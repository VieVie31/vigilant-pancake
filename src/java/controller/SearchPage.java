/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Manufacturer;
import entity.ProductCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mac
 */
@WebServlet(name = "SearchPage", urlPatterns = {"/SearchPage"})
public class SearchPage extends HttpServlet {

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
        
        
        request.setAttribute("product_code_select", makeProductCodeSelect());
        request.setAttribute("manufacturer_id_select", makeManufacturerIdSelect());    
        
        request.getRequestDispatcher("search_page.jsp").forward(request, response);
    }

    public String makeProductCodeSelect() {
        StringBuilder sb = new StringBuilder();
        
        try {
            ArrayList<ProductCode> pc = FuncTools.getGeneralDAO().getProductCodeList();
            
            sb.append("<SELECT id='product_code' name='product_code' onchange=\"update_product_list();\">");
            
            sb.append("<option value=''>Product Code...</option>");
            
            for (ProductCode p : pc) {
                sb.append("<option value='");
                sb.append(p.getProd_code());
                sb.append("'>");
                sb.append(p.getDescription());
                sb.append("</option>");
            }
            
            sb.append("</SELECT>");
            
        } catch (SQLException e) {
            return "";
        }

        return sb.toString();
    }
    
    public String makeManufacturerIdSelect() {
        StringBuilder sb = new StringBuilder();
        
        try {
            ArrayList<Manufacturer> pc = FuncTools.getGeneralDAO().getManufacturerList();
            
            sb.append("<SELECT id='manufacturer_id' name='manufacturer_id' onchange=\"update_product_list();\">");
            
            sb.append("<option value=''>Manufacturer...</option>");
            
            for (Manufacturer p : pc) {
                sb.append("<option value='");
                sb.append(p.getManufacturer_id());
                sb.append("'>");
                sb.append(p.getName());
                sb.append(" - ");
                sb.append(p.getManufacturer_id()); //for duplicate names in duplicate location
                sb.append("</option>");
            }
            
            sb.append("</SELECT>");
            
        } catch (SQLException e) {
            return "";
        }
        
        return sb.toString();
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
