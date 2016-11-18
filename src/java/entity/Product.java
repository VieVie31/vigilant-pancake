/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.sun.xml.internal.messaging.saaj.util.Base64;

/**
 *
 * @author mac
 */
public class Product {
    protected int product_id;
    protected String product_code;
    protected int manufacturer_id;
    protected double purchase_cost;
    protected int quantity_on_hand;
    protected double markup;
    protected boolean available;
    protected String description;

    public Product(int product_id, String product_code, int manufacturer_id, 
            double purchase_cost, int quantity_on_hand, double markup, 
            boolean available, String description) {
        this.product_id = product_id;
        this.product_code = product_code;
        this.manufacturer_id = manufacturer_id;
        this.purchase_cost = purchase_cost;
        this.quantity_on_hand = quantity_on_hand;
        this.markup = markup;
        this.available = available;
        this.description = description;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public double getPurchase_cost() {
        return purchase_cost;
    }

    public int getQuantity_on_hand() {
        return quantity_on_hand;
    }

    public double getMarkup() {
        return markup;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        //make it display as a JSON Object...
        sb.append("{");
        
        sb.append("product_id : ");
        sb.append(product_id);
        sb.append(",");
        
        sb.append("product_code : '");
        sb.append(product_code);
        sb.append("',");
        
        sb.append("manufacturer_id : ");
        sb.append(manufacturer_id);
        sb.append(",");
        
        sb.append("purchase_cost : ");
        sb.append(purchase_cost);
        sb.append(",");
        
        sb.append("quantity_on_hand : ");
        sb.append(quantity_on_hand);
        sb.append(",");
        
        sb.append("markup : ");
        sb.append(markup);
        sb.append(",");
        
        sb.append("available : ");
        sb.append(available);
        sb.append(",");
        
        sb.append("description : '");
        sb.append(new String(Base64.encode(description.getBytes()))); //base64 encode for safety ??
        sb.append("'");
        
        sb.append("}");
        
        return sb.toString();
    }
}
