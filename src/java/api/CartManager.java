/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.*;
import controller.FuncTools;
import dao.ProductSearchRequestBuilder;
import entity.Product;
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
public class CartManager extends HttpServlet {

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
        
        request.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String command = (String) request.getParameter("command");
        
        switch (command) {
            case "get_articles":
                out.print(getArticles(request));
                return;
            case "add_article":
                out.print(addArticle(
                        request,
                        Integer.parseInt(
                                request.getParameter("product_id")))
                );
                return;
            case "del_article":
                out.print(delArticle(
                        request,
                        Integer.parseInt(
                                request.getParameter("product_id")))
                );
                return;
            case "empty_cart":
                request.getSession().setAttribute(
                        "articles", 
                        new ArrayList<Product>()
                );
                out.print("{action_status : 'done'}");
                return;
            default:
                out.print("{action_status : 'fail'}");
        }
    }
    
    
    /**
     * Delete article by id from the cart.
     * eg : CartManager?command=del_article&product_id=666
     * 
     * @param request the HttpServletRequest request
     * @param product_id the product id to remove from the cart
     * @return a json object as string with attribute action_status
     * and value 'done' if succeded else 'fail'.
     */
    protected String delArticle(HttpServletRequest request, int product_id) {
        //!!\\ : del just 1 occurence of the article if exist !!!
        
        HttpSession session = request.getSession();
        
        ArrayList<Product> myCart = (ArrayList<Product>) session.getAttribute("articles");
        
        if (product_id == 0)
            return "{action_status : 'fail'}";
        
        if (myCart == null)
            myCart = new ArrayList<Product>();
        
        for (Product p : myCart) {
            if (p.getProduct_id() == product_id) {
                myCart.remove(p);
                session.setAttribute("articles", myCart);
                return "{action_status : 'done'}";      
            }
        }
        
        //nothing was removed...
        return "{action_status : 'fail'}";
    }

    /**
     * Add article by id to the cart.
     * eg : CartManager?command=add_article&product_id=666
     * 
     * @param request the HttpServletRequest request
     * @param product_id the product id to add to the cart
     * @return a json object as string with attribute <code>action_status</code>
     * and value 'done' if succeded else 'fail'.
     */
    protected String addArticle(HttpServletRequest request, int product_id) {
        HttpSession session = request.getSession();
        ArrayList<Product> myCart = (ArrayList<Product>) session.getAttribute("articles");
        
        if (product_id == 0)
            return "{action_status : 'fail'}";
        
        ProductSearchRequestBuilder sr = new ProductSearchRequestBuilder();
        sr.filterByProductId(product_id);
        
        try {
            ArrayList<Product> products = FuncTools.getGeneralDAO().searchProduct(sr);
            
            if (products.isEmpty())
                return "{action_status : 'fail'}";
            
            if (myCart == null)
                myCart = new ArrayList<Product>();
            
            myCart.add(products.get(0));
            
            session.setAttribute("articles", myCart);
            
            return "{action_status : 'done'}";
        } catch (SQLException ex) {
            return "{action_status : 'fail'}";
        }
    }
    
    /**
     * Get the list of all articles in the cart.
     * eg : CartManager?command=get_articles
     * 
     * @param request servlet request
     * @return a json object as string with attribute <code>articles</code>
     * and value the list of products.
     */
    protected String getArticles(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        
        HttpSession session = request.getSession();
        
        ArrayList<Product> myCart = (ArrayList<Product>) session.getAttribute("articles");
        
        if (myCart == null || myCart.isEmpty()) //nothing in the cart...
            return "{articles : []}";
        
        sb.append("{ articles : [");
        
        for (int i = 0; i < myCart.size() - 1; i++) {
            sb.append(myCart.get(i).toString());
            sb.append(",\n");
        }

        if (!myCart.isEmpty())
            sb.append(myCart.get(myCart.size() - 1).toString());
        
        sb.append("]}");
        
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
