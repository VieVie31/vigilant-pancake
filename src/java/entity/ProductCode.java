/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author mac
 */
public class ProductCode {
    protected String prod_code;
    protected String discout_code;
    protected String description;

    public ProductCode(String prod_code, String discout_code, String description) {
        this.prod_code = prod_code;
        this.discout_code = discout_code;
        this.description = description;
    }

    public String getProd_code() {
        return prod_code;
    }

    public String getDiscout_code() {
        return discout_code;
    }

    public String getDescription() {
        return description;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{");
        
        sb.append("product_code : '");
        sb.append(getProd_code());
        sb.append("',");
        
        sb.append("discount_code : '");
        sb.append(getDiscout_code());
        sb.append("',");
        
        sb.append("description : '");
        sb.append(getDescription());
        sb.append("'");
        
        sb.append('}');
        
        return sb.toString();
    }
}
