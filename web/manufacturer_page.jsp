<%-- 
    Document   : manufacturer_page
    Created on : 18 nov. 2016, 10:35:40
    Author     : VieVie31
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manufacturer</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/notif.js"></script>
        <script type="text/javascript" src="js/loader.js"></script>
    </head>
    <body>
        <h1>Manufacturer </h1>
        
        <div style='display:${manufacturer_found ? "none" : "block"};'>
            <h2 style="color:red;">MANUFACTURER NOT FOUND... :'(</h2>
        </div>
            
        <div style='display:${manufacturer_found ? "block" : "none"};'>
            
            <table style="position:relative;left:30%;">
                <tr>
                    <td>ID</td>
                    <td>${manufacturer.manufacturer_id}</td>
                </tr>
                
                <tr>
                    <td>NAME</td>
                    <td>${manufacturer.name}</td>
                </tr>
                
                <tr>
                    <td>ADDRESS LINE 1</td>
                    <td>${manufacturer.addressline1}</td>
                </tr>
                
                <tr>
                    <td>ADDRESS LINE 1</td>
                    <td>${manufacturer.addressline2}</td>
                </tr>
                
                <tr>
                    <td>CITY</td>
                    <td>${manufacturer.city}</td>
                    <td>
                        <details>
                            <summary>Try to localise</summary>
                            <img alt="NOT FOUND" src='http://maps.googleapis.com/maps/api/staticmap?center=${manufacturer.city}&zoom=15&format=png&sensor=false&size=300x200&maptype=roadmap&style=feature:road|element:geometry|color:0x805745|visibility:on&style=feature:administrative|visibility:off&style=element:labels|visibility:off&style=feature:water|element:labels|color:0x8080ff|visibility:off'/>
                        </details>
                    </td>
                </tr>
                
                <tr>
                    <td>STATE</td>
                    <td>${manufacturer.state}</td>
                </tr>
                
                <tr>
                    <td>ZIP</td>
                    <td>${manufacturer.zip}</td>
                </tr>
                
                <tr>
                    <td>PHONE</td>
                    <td>${manufacturer.phone}</td>
                </tr>
                
                <tr>
                    <td>FAX</td>
                    <td>${manufacturer.fax}</td>
                </tr>
                
                <tr>
                    <td>EMAIL</td>
                    <td>${manufacturer.email}</td>
                    <td>
                        <a class="cool_link" href="mailto:${manufacturer.email}">
                            CONTACT
                        </a>
                    </td>
                </tr>
                
            </table>
        </div>
                            
        <a class="cool_link" style="position:relative;left:50%;top:20px;" href="SearchPage">MAKE SEARCH</a>
    </body>
</html>
