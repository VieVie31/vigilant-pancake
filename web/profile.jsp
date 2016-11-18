<%-- 
    Document   : profile
    Created on : 15 nov. 2016, 21:15:31
    Author     : mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="jquery.js"></script>
        <script type="text/javascript" src="js/notif.js"></script>
        <script type="text/javascript" src="js/loader.js"></script>
    </head>
    <body>
        <h1>Hello Sir !</h1>
        
        <nav>
            <a id="disconnect" href="Disconnect">Disconnect</a>
        </nav>
        
        <table>
            
            <tr>
                <td>EMAIL</td>
                <td id="email">${customer.getEmail()}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="email_input" type="text" value="${customer.getEmail()}">
                        <button onclick="change('CHANGE_EMAIL', 'email_input', 'email', 'email changed !! :D', 'fail to change email... :\'(');">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>NAME</td>
                <td id="name">${customer.name}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="name_input" type="text" value="${customer.name}">
                        <button onclick="change_name();">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>ADDRESS 1</td>
                <td id="ad1">${customer.getAdress_line_1()}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="ad1_input" type="text" value="${customer.getAdress_line_1()}">
                        <button onclick="change('CHANGE_ADDRESSLINE1', 'ad1_input', 'ad1', 'address line 1 changed !! :D', 'fail to change address line 1... :\'(');">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>ADDRESS 2</td>
                <td id="ad2">${customer.getAdress_line_2()}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="ad2_input" type="text" value="${customer.getAdress_line_2()}">
                        <button onclick="change('CHANGE_ADDRESSLINE2', 'ad2_input', 'ad2', 'address line 2 changed !! :D', 'fail to change address line 2... :\'(');">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>CITY</td>
                <td id="city">${customer.city}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="city_input" type="text" value="${customer.city}">
                        <button onclick="change('CHANGE_CITY', 'city_input', 'city', 'city changed !! :D', 'fail to change city... :\'(');">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>STATE</td>
                <td id="state">${customer.state}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="state_input" type="text" value="${customer.state}">
                        <button onclick="change('CHANGE_STATE', 'state_input', 'state', 'state changed !! :D', 'fail to change state... :\'(');">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>PHONE</td>
                <td id="phone">${customer.phone}</td>
                <td>
                    <details>
                        <summary>Change</summary>
                        <input id="phone_input" type="text" value="${customer.phone}">
                        <button onclick="change('CHANGE_PHONE', 'phone_input', 'phone', 'phone changed !! :D', 'fail to change phone... :\'(');">SAVE</button>
                    </details>
                </td>
            </tr>
            
            <tr>
                <td>CREDIT LIMIT</td>
                <td>${customer.credit_limit} $</td>
            </tr>
            
            <tr>
                <td>ORDERS</td>
                <td>
                    <a class="cool_link" href="#">See Orders</a>
                </td>
                <td>
                    <div id="orders_graph_stats" style="width: 777px; height: 90%;"></div>
                </td>
            </tr>
        </table>
            
        <script>
            function get_orders_lst() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'Orders?command=get_orders');
                
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        orders = eval("(" + xhr.responseText + ")");
                        
                        function get_order_infos(order) {
                            return [order.sales_date, order.quandity, order.shipping_cost];
                        }

                        L = [["DATE", "QUANTITY", ["COST"]]];
                        for (i = 0; i < orders.orders.length; i++) {
                                L[i + 1] = get_order_infos(orders.orders[i]);
                        }
                        
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawChart);
                        
                        function drawChart() {
                            var data = google.visualization.arrayToDataTable(L);

                            var options = {
                              title: 'Orders stats',
                              hAxis: {title: 'Year',  titleTextStyle: {color: '#333'}},
                              vAxis: {minValue: 0}
                            };

                            var chart = new google.visualization.AreaChart(document.getElementById('orders_graph_stats'));
                            chart.draw(data, options);
                        }
      
                    } else if (xhr.readyState == 4 && xhr.status != 200)
                        notify("failed to load orders data...");
                };
                
                xhr.send(null);
            }

            function change_name() {
                var name = encodeURIComponent($('#name_input').val());

                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'CustomerProfile?command=CHANGE_NAME&value=' + name);

                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) { 
                        var response = eval("(" + xhr.responseText + ")");
                        if (response.action_status == "done") {
                            $('#name')[0].innerText = $('#name_input').val();
                            notify("Your name has been changed !! :D");
                        } else {
                            notify("failed to change name... :'(");
                        }
                    } else if (xhr.readyState == 4 && xhr.status != 200) { //maybe session out...
                        notify("failed to change name... :'(") ;
                    }
                };

                xhr.send(null);
            }  
            
            function change(command, input_id, output_id, success_msg, error_msg) {
                var name = encodeURIComponent($('#' + input_id).val());

                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'CustomerProfile?command=' + command + '&value=' + name);

                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && xhr.status == 200) { 
                        var response = eval("(" + xhr.responseText + ")");
                        if (response.action_status == "done") {
                            $('#' + output_id)[0].innerText = $('#' + input_id).val();
                            notify(success_msg);
                        } else
                            notify(error_msg);
                    } else if (xhr.readyState == 4 && xhr.status != 200) //maybe session out...
                        notify(error_msg) ;
                };

                xhr.send(null);
            }
            
            
            get_orders_lst();
        </script>
    </body>
</html>
