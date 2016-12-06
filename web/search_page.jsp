<%-- 
    Document   : search_page
    Created on : 16 nov. 2016, 18:56:40
    Author     : VieVie31
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search !</title>
        
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/notif.js"></script>
    </head>
    <body>
        <h1>SEARCH PRODUCT</h1>
        
        <div id="search_form">
            
            <input id="product_id" type="text" placeholder="product_id..." onchange="update_product_list();"></br>
            ${product_code_select}</br>
            ${manufacturer_id_select}</br>
            <input id="description" type="text" placeholder="description..." onchange="update_product_list();">
        </div>
        
        <div id="results_form">
            <table id="results_table" style="max-width: 700px;">
            </table>
        </div>
            
        <a href='${isLogged ? "CartCall" : "Login"}' class="disconnect" id="disconnect" style="position:fixed;right:5px;">
            ${isLogged ? "GO 2 CART" : "LOGIN"}
        </a>
            
    <nav>
        <li>
            <ul>
                <a href='Profile'>PROFIL</a>
            </ul>
            <ul>
                <a href='CartCall'>MY CART</a>
            </ul>
            <ul>
                <a href='OrdersCall'>MY ORDERS</a>
            </ul>
            <ul>
                <a href='SearchPage'>SEARCH</a>
            </ul>
            <ul>
                <a href='Disconnect'>DISCONNECTED</a>
            </ul>
        </li>
    </nav>
        
        <script>
            function update_product_list() {
                product_id = parseInt($("#product_id")[0].value);
                product_code = $("#product_code")[0].value;
                manufacturer_id = parseInt($("#manufacturer_id")[0].value);
                description = encodeURIComponent($("#description")[0].value);
                
                var xhr = new XMLHttpRequest();
                
                if ('' + product_id != 'NaN') { //if product id -> only search with this...
                    xhr.open('GET', 'Search?product_id=' + product_id);
                    console.log("search by product_id");
                } else { //prepare a complex request...
                    req = 'Search?';
                    
                    if ('' + manufacturer_id != 'NaN')
                        req += 'manufacturer_id=' + manufacturer_id + '&';
                    
                    if (product_code != '')
                        req += 'product_code=' + product_code + '&';
                    
                    if (description != '')
                        req += 'description=' + description;
                    
                    console.log(req);
                    xhr.open('GET', req);
                }
                
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) { 
                        var response = eval("(" + xhr.responseText + ")");
                        update_table(response.search_results);
                    } else if (xhr.readyState == 4 && xhr.status != 200) { //maybe session out...
                        notify("problem with search engine... :'(") ;
                    }
                };

                xhr.send(null);
            }
            
            function get_product_codes() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'GeneralInfos?command=get_product_codes');
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) { 
                        var response = eval("(" + xhr.responseText + ")");
                        
                        product_codes_lst = response.product_codes_lst;
                     
                        pc = $(".product_code");
                        
                        for (i = 0; i < pc.length; i++) {
                            v = pc[i];
                            for (j = 0; j < product_codes_lst.length; j++) {
                                if (v.innerText == product_codes_lst[j].product_code)
                                    v.innerText = product_codes_lst[j].description;
                            }
                            
                        }
                        
                    } else if (xhr.readyState == 4 && xhr.status != 200) { //maybe session out...
                        notify("problem with search engine... :'(") ;
                    }
                };

                xhr.send(null);
            }
            
            function update_table(search_results) {
                console.log(search_results);
                
                $("#results_table tr").remove(); //clear to display the new filtered rows
                
                if ('' + search_results == '' + []) {
                    notify("No Result Found... :'(");
                    return;
                }
                
                var table = $('#results_table')[0];
                
                for (i = 0; i < search_results.length; i++) {
                    t = search_results[i];
                    
                    row = table.insertRow(0);
                    
                    product_id      = row.insertCell(0);
                    product_code    = row.insertCell(1);
                    manufacturer_id = row.insertCell(2);
                    purchase_cost   = row.insertCell(3);
                    quantity_on_hand= row.insertCell(4);
                    description     = row.insertCell(5);
                    
                    product_id.innerHTML      = t.product_id;
                    product_code.innerHTML    = t.product_code;
                    manufacturer_id.innerHTML = "<a href='ManufacturerPage?id=" + t.manufacturer_id + "'>" + t.manufacturer_id + "</a>";
                    purchase_cost.innerHTML   = t.purchase_cost;
                    quantity_on_hand.innerHTML= t.quantity_on_hand;
                    description.innerHTML     = atob(t.description);
                    
                    product_code.className = "product_code";
                    
                    add_to_cart = row.insertCell(6);
                    add_to_cart.innerHTML = "<button onclick='adds_to_cart(" + t.product_id + ")'>+1</button>";
                    
                }
                
                //add header
                row = table.insertRow(0);
                row.style.color = "white";
                row.style.backgroundColor = "grey";
                
                product_id      = row.insertCell(0);
                product_code    = row.insertCell(1);
                manufacturer_id = row.insertCell(2);
                purchase_cost   = row.insertCell(3);
                quantity_on_hand= row.insertCell(4);
                description     = row.insertCell(5);

                product_id.innerHTML      = "<strong>Product Id</strong>";
                product_code.innerHTML    = "<strong>Product Type</strong>";
                manufacturer_id.innerHTML = "<strong>Manufacturer</strong>";
                purchase_cost.innerHTML   = "<strong>Cost</strong>";
                quantity_on_hand.innerHTML= "<strong>Quantity</strong>";
                description.innerHTML     = "<strong>Description</strong>";
            }
        
            function adds_to_cart(product_id) {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'CartManager?command=add_article&product_id=' + product_id);
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) { 
                        var response = eval("(" + xhr.responseText + ")");
                        notify("product " + product_id + " added to cart !! :D")
                    } else if (xhr.readyState == 4 && xhr.status != 200) { //maybe session out...
                        notify("failed to add to cart... :'(") ;
                    }
                };

                xhr.send(null);
            }
            
            update_product_list();
        
            setInterval(get_product_codes, 1000);
        </script>
    </body>
</html>
