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
public class PurchaseOrder {
    protected int order_num;
    protected int customer_id;
    protected int product_id;
    protected int quandity;
    protected double shipping_cost;
    protected String sales_date;
    protected String shipping_date;
    protected String freight_company;

    public PurchaseOrder(int order_num, int customer_id, int product_id, 
            int quandity, double shipping_cost, String sales_date, 
            String shipping_date, String freight_company) {
        this.order_num = order_num;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quandity = quandity;
        this.shipping_cost = shipping_cost;
        this.sales_date = sales_date;
        this.shipping_date = shipping_date;
        this.freight_company = freight_company;
    }

    public int getOrder_num() {
        return order_num;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getQuandity() {
        return quandity;
    }

    public double getShipping_cost() {
        return shipping_cost;
    }

    public String getSales_date() {
        return sales_date;
    }

    public String getShipping_date() {
        return shipping_date;
    }

    public String getFreight_company() {
        return freight_company;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        //make it display as a JSON Object...
        sb.append("{");
        
        sb.append("order_num : ");
        sb.append(order_num);
        sb.append(",");
        
        sb.append("customer_id : ");
        sb.append(customer_id);
        sb.append(",");
        
        sb.append("product_id : ");
        sb.append(product_id);
        sb.append(",");
        
        sb.append("quandity : ");
        sb.append(quandity);
        sb.append(",");
        
        sb.append("shipping_cost : ");
        sb.append(shipping_cost);
        sb.append(",");
        
        sb.append("sales_date : '");
        sb.append(sales_date);
        sb.append("',");
        
        sb.append("shipping_date : '");
        sb.append(shipping_date);
        sb.append("',");
        
        sb.append("freight_company : '");
        sb.append(new String(Base64.encode(freight_company.getBytes()))); //base64 encode for safety
        sb.append("'");
        
        sb.append("}");
        
        return sb.toString();
    }
}
