/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import controller.FuncTools;
import entity.Manufacturer;
import entity.Product;
import entity.ProductCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mac
 */
public class GeneralInfos extends HttpServlet {

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
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        
        request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String command = (String) request.getParameter("command");

        switch (command) {
            case "get_product_codes":
                out.print(getProductCodes());
                return;
            case "get_manufacturer":
                out.print(getManufacturerById(request.getParameter("manufacturer_id")));
                return;
            default:
                out.print("{\"action_status\" : \"nothing_done\"}");
        }
    }
    
    public String getManufacturerById(String id) {
        try {
            Manufacturer m = FuncTools.getGeneralDAO().getManufacturerById(
                Integer.parseInt(id));
            
            if (m == null)
                return "{action_status : 'fail'}";
            
            return "{action_status : 'done', manufacturer : " + m.toString() + "}";
        } catch (Exception e) {
            return "{action_status : 'fail'}";
        }
    }
    
    public String getProductCodes() {
        try {
            ArrayList<ProductCode> pc = FuncTools.getGeneralDAO().getProductCodeList();
            
            //out results in JSON format
            StringBuilder sb = new StringBuilder();
            
            sb.append("{ product_codes_lst : [");
            
            for (int i = 0; i < pc.size() - 1; i++) {
                sb.append(pc.get(i).toString());
                sb.append(",\n");
            }
            
            if (!pc.isEmpty())
                sb.append(pc.get(pc.size() - 1).toString());
            
            sb.append("]}");
            
            return sb.toString();
        } catch (SQLException ex) {
            return "{action_status : \"fail\"}";
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
