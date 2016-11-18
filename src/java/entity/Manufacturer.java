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
public class Manufacturer {
    protected int manufacturer_id;
    protected String name;
    protected String addressline1;
    protected String addressline2;
    protected String city;
    protected String state;
    protected String zip;
    protected String phone;
    protected String fax;
    protected String email;
    protected String rep;

    public Manufacturer(int manufacturer_id, String name, String addressline1, String addressline2, String city, String state, String zip, String phone, String fax, String email, String rep) {
        this.manufacturer_id = manufacturer_id;
        this.name = name;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.rep = rep;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public String getName() {
        return name;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
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

    public String getRep() {
        return rep;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("{");
        
        sb.append("manufacturer_id : ");
        sb.append(manufacturer_id);
        sb.append(",");
        
        sb.append("address_line_1 : '");
        sb.append(addressline1);
        sb.append("',");
        
        sb.append("address_line_2 : '");
        sb.append(addressline2);
        sb.append("',");
        
        sb.append("city : '");
        sb.append(city);
        sb.append("',");
        
        sb.append("state : '");
        sb.append(state);
        sb.append("',");
        
        sb.append("zip : '");
        sb.append(zip);
        sb.append("',");
        
        sb.append("phone : '");
        sb.append(phone);
        sb.append("',");
        
        sb.append("fax : '");
        sb.append(fax);
        sb.append("',");
        
        sb.append("email : '");
        sb.append(email);
        sb.append("',");
        
        sb.append("rep : '");
        sb.append(rep);
        sb.append("'");
        
        sb.append("}");
        
        return sb.toString();
    }
}
