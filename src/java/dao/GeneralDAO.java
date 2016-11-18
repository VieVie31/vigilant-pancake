/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Customer;
import entity.Manufacturer;
import entity.Product;
import entity.ProductCode;
import entity.PurchaseOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;


/**
 *
 * @author orisserm
 */
public class GeneralDAO {
   public final DataSource dataSource;
   
   public GeneralDAO(DataSource ds) {
       dataSource = ds;
   }
 
   public ArrayList<Manufacturer> getManufacturerList() {
       String sql = "SELECT * FROM APP.MANUFACTURER";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           ResultSet rs = stmt.executeQuery();
           
           ArrayList<Manufacturer> out = new ArrayList<Manufacturer>();
           
           while (rs.next()) {
               Manufacturer m = new Manufacturer(
                   rs.getInt(1),    //MANUFACTURER_ID
                   rs.getString(2), //NAME
                   rs.getString(3), //ADDRESSLINE1
                   rs.getString(4), //ADDRESSLINE2
                   rs.getString(5), //CITY
                   rs.getString(6), //STATE
                   rs.getString(7), //ZIP
                   rs.getString(8), //PHONE
                   rs.getString(9), //FAX
                   rs.getString(10),    //EMAIL
                   rs.getString(11) //REP
           );
               
               out.add(m);
           }
           
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (SQLException e) {
           return null;
       }
   }
   
   public Manufacturer getManufacturerById(int manufacturer_id) {
       String sql = "SELECT * FROM APP.MANUFACTURER WHERE MANUFACTURER_ID = ?";
               
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setInt(1, manufacturer_id);
           
           ResultSet rs = stmt.executeQuery();
           rs.next();
           
           Manufacturer out;
           out = new Manufacturer(
                   rs.getInt(1),    //MANUFACTURER_ID
                   rs.getString(2), //NAME
                   rs.getString(3), //ADDRESSLINE1
                   rs.getString(4), //ADDRESSLINE2
                   rs.getString(5), //CITY
                   rs.getString(6), //STATE
                   rs.getString(7), //ZIP
                   rs.getString(8), //PHONE
                   rs.getString(9), //FAX
                   rs.getString(10),    //EMAIL
                   rs.getString(11) //REP
           );
           
           rs.close();
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (Exception e) {
           return null;
       }
   }
   
   public ArrayList<ProductCode> getProductCodeList() {
       String sql = "SELECT * FROM APP.PRODUCT_CODE";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           ResultSet rs = stmt.executeQuery();
           
           ArrayList<ProductCode> out = new ArrayList<ProductCode>();
           
           while (rs.next()) {
               ProductCode pc = new ProductCode(
                       rs.getString(1),
                       rs.getString(2),
                       rs.getString(3)
               );
               
               out.add(pc);
           }
           
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (SQLException e) {
           return null;
       }
   }
   
   public boolean deletePurchaseOrder(int order_num, int customer_id) {
       String sql = "DELETE FROM APP.PURCHASE_ORDER WHERE ORDER_NUM = ? AND CUSTOMER_ID = ?";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setInt(1, order_num);
           stmt.setInt(2, customer_id);
           
           stmt.executeUpdate();
           stmt.close();
           connection.close();
           
           return true;
       } catch (SQLException e) {
           return false;
       }
       
   }
   
   public boolean insertPurchaseOrder(PurchaseOrder po) {
       String sql = "INSERT INTO APP.PURCHASE_ORDER VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setInt(1, po.getOrder_num());
           stmt.setInt(2, po.getCustomer_id());
           stmt.setInt(3, po.getProduct_id());
           stmt.setInt(4, po.getQuandity());
           stmt.setDouble(5, po.getShipping_cost());
           stmt.setString(6, po.getSales_date());
           stmt.setString(7, po.getShipping_date());
           stmt.setString(8, po.getFreight_company());
           
           stmt.executeUpdate();
           stmt.close();
           connection.close();
       } catch (SQLException e) {
           return false;
       }
       
       return true;
   }
   
   public ArrayList<PurchaseOrder> getPurchaseOrder(String email) {
       Customer customer = getCustomerByEmail(email);
       
       String sql = "SELECT * FROM APP.PURCHASE_ORDER WHERE CUSTOMER_ID = ? ORDER BY SALES_DATE";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setInt(1, customer.getCustomer_id());
           
           ResultSet rs = stmt.executeQuery();
           
           ArrayList<PurchaseOrder> out = new ArrayList<PurchaseOrder>();
           
           while (rs.next()) {
               PurchaseOrder  p = new PurchaseOrder(
                       rs.getInt(1),    //order_num
                       rs.getInt(2),    //customer_id
                       rs.getInt(3),    //product_id
                       rs.getInt(4),    //quandity
                       rs.getDouble(5), //shipping_cost
                       rs.getDate(6).toString(), //sales_date
                       rs.getDate(7).toString(), //shipping_date
                       rs.getString(8)  //freight_company
               );
               
               out.add(p);
           }
           
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (SQLException e) {}
       
       return null;
   }
   
   public ArrayList<Product> searchProduct(ProductSearchRequestBuilder searchRequest) {
       String sql = searchRequest.build();
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           ResultSet rs = stmt.executeQuery();
           
           ArrayList<Product> out = new ArrayList<Product>();
           
           while (rs.next()) {
               //PRODUCT_ID MANUFACTURER_ID PRODUCT_CODE PURCHASE_COST QUANTITY_ON_HAND MARKUP AVAILABLE DERSCRITPON
               Product p = new Product(
                       rs.getInt(1),    //PRODUCT_ID
                       rs.getString(3), //PRODUCT_CODE
                       rs.getInt(2),    //MANUFACTURER_ID
                       rs.getDouble(4),  //PURCHASE_COST
                       rs.getInt(5),    //QUANTITY_ON_HAND
                       rs.getDouble(6), //MARKUP
                       rs.getString(7).equals("TRUE"), //AVAILABLE
                       rs.getString(8)  //DERSCRITPON
               );
               
               out.add(p);
           }
           
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (SQLException e) {
           return null;
       }
       
   }
   
   public boolean changeCustomerInfo(String email, Customer c) {
       String sql = "UPDATE app.CUSTOMER SET "
               + "NAME = ?, "
               + "ADDRESSLINE1 = ?, "
               + "ADDRESSLINE2 = ?, "
               + "CITY = ?, "
               + "STATE = ?, "
               + "PHONE = ?, "
               + "FAX = ?, "
               + "EMAIL = ? "
               + "WHERE EMAIL = ?";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setString(1, c.getName());
           stmt.setString(2, c.getAdress_line_1());
           stmt.setString(3, c.getAdress_line_2());
           stmt.setString(4, c.getCity());
           stmt.setString(5, c.getState());
           stmt.setString(6, c.getPhone());
           stmt.setString(7, c.getFax());
           stmt.setString(8, c.getEmail());
           
           stmt.setString(9, email);
           
           stmt.executeUpdate();
           stmt.close();
           connection.close();
       } catch (SQLException e) {
           return false;
       }
       
       return true;
   }
   
   public boolean changeCustomerName(String email, String name) {
       String sql = "UPDATE APP.CUSTOMER SET NAME = ? WHERE EMAIL = ?";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setString(1, name);
           stmt.setString(2, email);
           
           stmt.executeUpdate();
           stmt.close();
           connection.close();
       } catch (SQLException e) {
           return false;
       }
       
       return true;
   }
   
   public Customer getCustomerByEmail(String email) {
       String sql = "SELECT * FROM APP.CUSTOMER WHERE EMAIL = ?";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setString(1, email);
           
           ResultSet rs = stmt.executeQuery();
           rs.next();
                   
           Customer out;
           out = new Customer(
                   rs.getInt(1), 
                   rs.getString(2),
                   rs.getString(3),
                   rs.getString(4),
                   rs.getString(5),
                   rs.getString(6),
                   rs.getString(7),
                   rs.getString(8),
                   rs.getString(9),
                   rs.getString(10),
                   rs.getString(11),
                   rs.getInt(12)
           );
                   
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (SQLException e) {
            return null;
       }
       
   }
   
   public  boolean checkUserPassword(String email, String password) {
    Integer pass;
       try {
           pass = Integer.parseInt(password);
       } catch (Exception e) {
           return false;
       }
       
       String sql = "SELECT COUNT(EMAIL)  AS EXISTS_USER FROM APP.CUSTOMER " +
                    "WHERE EMAIL = ? AND  CUSTOMER_ID = ?";
       
       try {
           Connection connection = dataSource.getConnection();
           PreparedStatement stmt = connection.prepareStatement(sql);
           
           stmt.setString(1, email);
           stmt.setInt(2, pass);
           
           ResultSet rs = stmt.executeQuery();
           rs.next();
           
           boolean out = rs.getInt("EXISTS_USER") == 1;
           
           rs.close();
           stmt.close();
           connection.close();
           
           return out;
       } catch (SQLException e) {
           return false;
       }
   }
}
