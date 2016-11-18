/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import controller.FuncTools;
import dao.ProductSearchRequestBuilder;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mac
 */
public class Search extends HttpServlet {

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
        request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        ProductSearchRequestBuilder sr = new ProductSearchRequestBuilder();
        
        //stack filters for search
        try {
            int product_id = Integer.parseInt(request.getParameter("product_id"));
            if (product_id > 0)
                sr = sr.filterByProductId(product_id);
        } catch (Exception e) {}
        
        try {
            String product_code = (String) request.getParameter("product_code");
            if (product_code != null)
                sr = sr.filterByProductCode(product_code);
        } catch (Exception e) {}
        
        try {
            int manufacturer_id = Integer.parseInt(request.getParameter("manufacturer_id"));
            if (manufacturer_id > 0)
                sr = sr.filterByManufacturerId(manufacturer_id);
        } catch (Exception e) {}
        
        try {
            String description = (String) request.getParameter("description");
            if (description != null)
                sr = sr.filterByDescription(description);
        } catch (Exception e) {}
        
        try {
            boolean only_available = Integer.parseInt(request.getParameter("only_available")) == 1;
            if (only_available)
                sr = sr.filterByAvailable(only_available);
        } catch (Exception e) {}
        
        try {
            double purchase_cost = Double.parseDouble(request.getParameter("purchase_cost"));
            boolean cost_less_or_equal = Integer.parseInt(request.getParameter("cost_less_or_equal")) == 1;
            if (purchase_cost > 0)
                sr = sr.filterByCost(purchase_cost, cost_less_or_equal);
        } catch (Exception e) {}
        
        //make the request
        try {
            ArrayList<Product> products = FuncTools.getGeneralDAO().searchProduct(sr);
            
            //out results in JSON format
            StringBuilder sb = new StringBuilder();
            
            sb.append("{ search_results : [");
            
            for (int i = 0; i < products.size() - 1; i++) {
                sb.append(products.get(i).toString());
                sb.append(",\n");
            }
            
            if (!products.isEmpty())
                sb.append(products.get(products.size() - 1).toString());
            
            sb.append("]}");
            
            out.print(sb.toString());
        } catch (SQLException ex) {
            out.print("erreur... :'(");
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
