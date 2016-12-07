<%-- 
    Document   : ordre_gestion
    Created on : 22 nov. 2016, 21:24:34
    Author     : Eloan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/notif.js"></script>
    </head>
    <body>
        <h1> Order Manager</h1>
        <div id="results_form" style="left: 100px;">
            <table id="results_table" style="max-width: 700px; left: 100px;">
            </table>
        </div>

    <nav style="">
        <details>
            <summary style="color:white;">Navigation</summary>
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
        </details>
    </nav>
        
        
        <script type="text/javascript" >
            function order_list() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'Orders?command=get_orders');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var response = eval("(" + xhr.responseText + ")");
                        console.log(response.orders);
                        display_orders(response.orders);
                    } else if (xhr.readyState == 4 && xhr.status != 200) { //maybe session out...
                        notify("failed to load order :/ ");
                    }
                };

                xhr.send(null);

            }
            
            function del_ordr(d) {
                console.log("delete");
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'Orders?command=del_order&order_num=' + d);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var response = eval("(" + xhr.responseText + ")");
                        console.log(response);
                        var table = $('#results_table')[0];
                        lst = table.children[0].children;
                        for (i = 1; i < lst.length; i++) {
                            if (lst[i].children[0].innerText === '' + d) {
                                table.deleteRow(i);
                                return;
                            }
                        }
                        notify(" not found in order liste :/");

                    } else if (xhr.readyState === 4 && xhr.status !== 200) { //maybe session out...
                        notify("failed to del from order liste :/ ");
                    }
                };

                xhr.send(null);
            }
            
            function update_order(order_num) {
                console.log("update");
                
                if (parseInt($("#" + order_num + "_q")[0].value) <= 0) {
                    notify ("no order with null or < 0 quantity !!");
                    return;
                }
                
                var xhr = new XMLHttpRequest();
                
                var tmp = "Orders?command=update_order";
                tmp += "&order_num=" + order_num;
                tmp += "&quandity="  + $("#" + order_num + "_q")[0].value;
                tmp += "&freight_company=" + encodeURIComponent($("#" + order_num + "_fc")[0].value);
                console.log(tmp);
                
                xhr.open('GET', tmp);
                
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        var response = eval("(" + xhr.responseText + ")");
                        console.log(response);
                        
                        notify((response.action_status == 'done') 
                               ? "successfully update order : " + order_num
                               : "failed to update bro :/ "
                        );
                    } else if (xhr.readyState === 4 && xhr.status !== 200) {
                        notify("failed to update bro :/ ");
                    }
                };

                xhr.send(null);
            }
            
            function display_orders(lst) {
                if ('' + lst == '' + []) {
                    notify("No Result Found :/");
                    return;
                }

                var table = $('#results_table')[0];
                for (i = 0; i < lst.length; i++) {
                    t = lst[i];
                    row = table.insertRow(0);
                    order_num = row.insertCell(0);
                    customer_id = row.insertCell(1);
                    product_id = row.insertCell(2);
                    quandity = row.insertCell(3);
                    shipping_cost = row.insertCell(4);
                    sales_date = row.insertCell(5);
                    shipping_date = row.insertCell(6);
                    freight_company = row.insertCell(7);
                    del_order = row.insertCell(8);
                    del_order.innerHTML = "<button onclick='del_ordr(" + t.order_num + ")' style='background:red'>-1</button>";

                    update_case = row.insertCell(9);
                    update_case.innerHTML = "<button onclick='update_order(" + t.order_num + ")'>UpDate</button>";

                    order_num.innerHTML = t.order_num;
                    customer_id.innerHTML = t.customer_id;
                    product_id.innerHTML = t.product_id;
                    quandity.innerHTML = "<input id='" + t.order_num + "_q' type='number' step='1' value='" + t.quandity + "' min='1' max='10000'>";
                    shipping_cost.innerHTML = t.shipping_cost;
                    customer_id.innerHTML = t.customer_id;
                    sales_date.innerHTML = t.sales_date;
                    shipping_date.innerHTML = t.shipping_date;
                    freight_company.innerHTML = "<input id='" + t.order_num + "_fc' type='text' value='" + atob(t.freight_company) + "'>";

                    order_num.className = "product_code";


                }

                row = table.insertRow(0);
                row.style.color = "white";
                row.style.backgroundColor = "grey";

                order_num = row.insertCell(0);
                customer_id = row.insertCell(1);
                product_id = row.insertCell(2);
                quandity = row.insertCell(3);
                shipping_cost = row.insertCell(4);
                sales_date = row.insertCell(5);
                shipping_date = row.insertCell(6);
                freight_company = row.insertCell(7);


                order_num.innerHTML = "<strong>Order Num</strong>";
                customer_id.innerHTML = "<strong>Customer ID</strong>";
                product_id.innerHTML = "<strong>Product ID</strong>";
                quandity.innerHTML = "<strong>Quantity</strong>";
                shipping_cost.innerHTML = "<strong>Shipping Cost</strong>";
                customer_id.innerHTML = "<strong>Customer ID</strong>";
                sales_date.innerHTML = "<strong>Sales Date</strong>";
                shipping_date.innerHTML = "<strong>Shipping Date</strong>";
                freight_company.innerHTML = "<strong>Freight Company</strong>";


            }
            order_list();
        </script>
    </body>
</html>
