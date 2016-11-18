/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author orisserm
 */
public class Customer {
    protected int customer_id;
    protected String discount_code; //char ??
    protected String zip; //int ??
    protected String name;
    protected String adress_line_1;
    protected String adress_line_2;
    protected String city;
    protected String state;
    protected String phone;
    protected String fax;
    protected String email;
    protected int credit_limit;
    
    protected Customer() {}

    public Customer(int customer_id, String discount_code, String zip, 
            String name, String adress_line_1, String adress_line_2, 
            String city, String state, String phone, String fax, String email, 
            int credit_limit) {
        this.customer_id = customer_id;
        this.discount_code = discount_code;
        this.zip = zip;
        this.name = name;
        this.adress_line_1 = adress_line_1;
        this.adress_line_2 = adress_line_2;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.credit_limit = credit_limit;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getDiscount_code() {
        return discount_code;
    }

    public String getZip() {
        return zip;
    }

    public String getName() {
        return name;
    }

    public String getAdress_line_1() {
        return adress_line_1;
    }

    public String getAdress_line_2() {
        return adress_line_2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public int getCredit_limit() {
        return credit_limit;
    }

    public void setName(String name) throws Exception {
        if (name == null)
            throw new Exception();
        
        this.name = name;
    }

    public void setAdress_line_1(String adress_line_1) throws Exception {
        if (adress_line_1 == null)
            throw new Exception();
        
        this.adress_line_1 = adress_line_1;
    }

    public void setAdress_line_2(String adress_line_2) throws Exception {
        if (adress_line_2 == null)
            throw new Exception();
        
        this.adress_line_2 = adress_line_2;
    }

    public void setCity(String city) throws Exception {
        if (city == null)
            throw new Exception();
        
        this.city = city;
    }

    public void setState(String state) throws Exception {
        if (state == null)
            throw new Exception();
        
        this.state = state;
    }

    public void setPhone(String phone) throws Exception {
        if (phone == null)
            throw new Exception();
        
        this.phone = phone;
    }

    public void setFax(String fax) throws Exception {
        if (fax == null)
            throw new Exception();
        
        this.fax = fax;
    }

    public void setEmail(String email) throws Exception {
        if (email == null)
            throw new Exception();
        
        this.email = email;
    }
 
}
