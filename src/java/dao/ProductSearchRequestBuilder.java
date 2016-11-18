/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author mac
 */
public class ProductSearchRequestBuilder {
    protected int product_id;
    protected String product_code;
    protected int manufacturer_id;
    protected double purchase_cost;
    protected boolean cost_less_or_equal;
    //protected int quantity_on_hand;
    protected double markup;
    protected boolean only_available;
    protected String description;
    
    public ProductSearchRequestBuilder() {}
    
    public ProductSearchRequestBuilder filterByProductId(int product_id) {
        this.product_id = product_id;
        return this;
    }
    
    public ProductSearchRequestBuilder filterByProductCode(String product_code) {
        this.product_code = product_code;
        return this;
    }
    
    public ProductSearchRequestBuilder filterByManufacturerId(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
        return this;
    }
    
    public ProductSearchRequestBuilder filterByCost(double purchase_cost, boolean cost_less_or_equal) {
        this.purchase_cost = purchase_cost;
        this.cost_less_or_equal = cost_less_or_equal;
        return this;
    }
    
    public ProductSearchRequestBuilder filterByAvailable(boolean only_available) {
        this.only_available = only_available;
        return this;
    }
    
    public ProductSearchRequestBuilder filterByDescription(String description) {
        this.description = description;
        return this;
    }
    
    public String build() {
        //FIXME : clean the user inputs from potential injections...
        StringBuilder sb = new StringBuilder();
        
        sb.append("SELECT * FROM APP.PRODUCT ");
        
        
        ArrayList<String> tmp = new ArrayList<String>();        
        
        if (product_id != 0)
            tmp.add("PRODUCT_ID = " + product_id + " ");
        
        if (product_code != null)
            tmp.add("PRODUCT_CODE = '" + product_code + "' ");
        
        if (manufacturer_id != 0)
            tmp.add("MANUFACTURER_ID = " + manufacturer_id + " ");
        
        if (purchase_cost != 0)
            tmp.add("PURCHASE_COST " + (cost_less_or_equal ? "<=" : ">=") + " " + purchase_cost + " ");
        
        if (only_available)
            tmp.add("AVAILABLE = 'TRUE' ");
        
        if (description != null) 
            tmp.add("LOWER(DESCRIPTION) LIKE LOWER('%" + description + "%')");
        
        
        if (tmp.isEmpty())
            return sb.toString();
        
        sb.append(" WHERE ");
        
        for (int i = 0; i < tmp.size() - 1; i++) {
            sb.append(tmp.get(i));
            sb.append(" AND ");
        }
        
        sb.append(tmp.get(tmp.size() - 1));
        
        return sb.toString();
    }
}
