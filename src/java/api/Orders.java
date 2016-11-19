/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import controller.FuncTools;
import entity.PurchaseOrder;
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
 * @author VieVie31
 */
public class Orders extends HttpServlet {

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
        //not logged --> f*** yourself !!!
        if (!FuncTools.checkSessionLogin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        
        request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String command = (String) request.getParameter("command");
        
        if (command == null) {
            out.print("{action_status : 'fail', motif : 'no command'}");
            return;
        }
        
        switch (command) {
            case "get_orders":
                out.print(getOrders(request));
                return;
            case "make_order":
                out.print(makeOrder(request));
                return;
            case "del_order":
                out.print(delOrder(request));
                return;
            default:
                out.print("{action_status : 'fail'}");
        }
    }
    
    /**
     * Delete command order of the connected client by order id.
     * eg. Orders?command=del_order&order_num=31415
     * 
     * @param request the servlet request
     * @return a json object as string with attribute <code>action_status</code>
     * and value 'done' if succeded else 'fail'.
     */
    public String delOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        try {
            return (FuncTools.getGeneralDAO().deletePurchaseOrder(
                        Integer.parseInt((String) request.getParameter("order_num")),
                        FuncTools.getGeneralDAO()
                                .getCustomerByEmail((String) session.getAttribute("email"))
                                .getCustomer_id()
                    ) 
                    ? "{action_status : 'done'}"
                    : "{action_status : 'fail'}");
        } catch (SQLException e) {
            return "{action_status : 'fail'}";
        } catch (Exception e) {
            return "{action_status : 'fail'}";
        }
    }
    
    /**
     * Make an order.
     * eg. Orders?command=make_order&order_num=666&product_id=980005&quandity=50&shipping_cost=12.7&sales_date=2016-11-16&shipping_date=2016-11-16&freight_company=BAMBAM
     * 
     * @param request the servlet request
     * @return a json object as string with attribute <code>action_status</code>
     * and value 'done' if succeded else 'fail'.
     */
    public String makeOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        PurchaseOrder po;
        
        try {
            po = new PurchaseOrder(
                    Integer.parseInt((String) request.getParameter("order_num")),
                    FuncTools.getGeneralDAO().getCustomerByEmail((String) session.getAttribute("email")).getCustomer_id(), //do not let other customer take order for another custoemr
                    Integer.parseInt((String) request.getParameter("product_id")),
                    Integer.parseInt((String) request.getParameter("quandity")),
                    Double.parseDouble((String) request.getParameter("shipping_cost")),
                    (String) request.getParameter("sales_date"),
                    (String) request.getParameter("shipping_date"),
                    (String) request.getParameter("freight_company")
            );
            
            return (FuncTools.getGeneralDAO().insertPurchaseOrder(po) 
                    ? "{action_status : 'done'}"
                    : "{action_status : 'fail'}");
        } catch (SQLException e) {
            return "{action_status : 'fail'}";
        } catch (Exception e) {
            return "{action_status : 'fail'}";
        }
        
    }
    
    /**
     * Get the list of purchase order of the current client.
     * eg. Orders?command=get_orders
     * 
     * @param request the servlet request
     * @return a json object as string with attribute <code>action_status</code>
     * and value 'done' if succeded else 'fail'.
     * If <code>action_status</code> is set to 'done' then there is another 
     * attribute : <code>orders</code> with the list of orders as value.
     */
    public String getOrders(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        String email = (String) session.getAttribute("email");
        
        try {
            ArrayList<PurchaseOrder> po;
            po = FuncTools.getGeneralDAO().getPurchaseOrder(email);
            
            if (po == null || po.isEmpty())
                return "{ orders : [], action_status : 'done'}";
            
            StringBuilder sb = new StringBuilder();
            sb.append("{ orders : [");
        
            for (int i = 0; i < po.size() - 1; i++) {
                sb.append(po.get(i).toString());
                sb.append(",\n");
            }

            if (!po.isEmpty())
                sb.append(po.get(po.size() - 1).toString());

            sb.append("], action_status : 'done'}");
            
            return sb.toString();
        } catch (SQLException ex) {
            return "{action_status : 'fail'}";
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
