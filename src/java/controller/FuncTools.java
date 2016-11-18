/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.GeneralDAO;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author mac
 */
public class FuncTools {
    public static GeneralDAO getGeneralDAO() throws SQLException {
        return new GeneralDAO(getDataSource());
    }
    
    public static DataSource getDataSource() throws SQLException {
        org.apache.derby.jdbc.ClientDataSource ds = new org.apache.derby.jdbc.ClientDataSource();
        ds.setDatabaseName("sample");
        ds.setUser("app");
        ds.setPassword("app");
        // The host on which Network Server is running
        ds.setServerName("localhost");
        // port on which Network Server is listening
        ds.setPortNumber(1527);
        return ds;
    }
    
    public static boolean checkSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        String email = null;
        String password = null;
        
        try {
            email = (String) session.getAttribute("email");
            password = (String) session.getAttribute("password");
        } catch (Exception e) {}
        
        email = (email == null) ? "" : email;
        password = (password == null) ? "" : password;
        
        try {
            if (getGeneralDAO().checkUserPassword(email, password)) {
                session.setAttribute("connected", true);
                return true;
            } else {
                session.setAttribute("connected", false);
                return false;
            }
        } catch (Exception e) {}
        
        return false;
    }
}
