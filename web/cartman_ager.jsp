<%-- 
    Document   : cartman_ager
    Created on : 22 nov. 2016, 21:21:58
    Author     : Eloanleboss
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
        <h1>Cart Manager </h1>

        <div id="results_form">
            <table id="results_table" style="max-width: 700px;">
            </table>
        </div>

        <button  style="position:fixed;left:85%;top:150px;" onclick="buy();">
            BUY !
        </button>
       
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

        <script type="text/javascript" >
            var articles = null;
            var fret_company = "default fret company";//prompt("choose ur fret_company : ", "postte");
            function article_list() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'CartManager?command=get_articles');
                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        var response = eval("(" + xhr.responseText + ")");
                        console.log(response);
                        display_articles(response.articles);
                        articles = response.articles;
                    } else if (xhr.readyState == 4 && xhr.status != 200) { //maybe session out...
                        notify("failed to load cart :/ ");
                    }
                };

                xhr.send(null);
            }

            function display_articles(lst) {
                if ('' + lst == '' + []) {
                    notify("No Result Found :/");
                    return;
                }

                var table = $('#results_table')[0];
                for (i = 0; i < lst.length; i++) {
                    t = lst[i];

                    row = table.insertRow(0);

                    product_id = row.insertCell(0);
                    product_code = row.insertCell(1);
                    manufacturer_id = row.insertCell(2);
                    purchase_cost = row.insertCell(3);
                    quantity_on_hand = row.insertCell(4);
                    description = row.insertCell(5);

                    product_id.innerHTML = t.product_id;
                    product_code.innerHTML = t.product_code;
                    manufacturer_id.innerHTML = "<a href='ManufacturerPage?id=" + t.manufacturer_id + "'>" + t.manufacturer_id + "</a>";
                    purchase_cost.innerHTML = t.purchase_cost;
                    quantity_on_hand.innerHTML = t.quantity_on_hand;
                    description.innerHTML = atob(t.description);

                    product_code.className = "product_code";
                    // a changer
                    del_to_cart = row.insertCell(6);
                    del_to_cart.innerHTML = "<button onclick='dels_to_cart(" + t.product_id + ")' style='background:red'>-1</button>";
                }

                row = table.insertRow(0);
                row.style.color = "white";
                row.style.backgroundColor = "grey";

                product_id = row.insertCell(0);
                product_code = row.insertCell(1);
                manufacturer_id = row.insertCell(2);
                purchase_cost = row.insertCell(3);
                quantity_on_hand = row.insertCell(4);
                description = row.insertCell(5);

                product_id.innerHTML = "<strong>Product Id</strong>";
                product_code.innerHTML = "<strong>Product Type</strong>";
                manufacturer_id.innerHTML = "<strong>Manufacturer</strong>";
                purchase_cost.innerHTML = "<strong>Cost</strong>";
                quantity_on_hand.innerHTML = "<strong>Quantity (in stock)</strong>";
                description.innerHTML = "<strong>Description</strong>";

            }

            function dels_to_cart(d) {
                console.log("delete");
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'CartManager?command=del_article&product_id=' + d);
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
                        notify(" not found in cart :/");

                        article_list();
                    } else if (xhr.readyState === 4 && xhr.status !== 200) { //maybe session out...
                        notify("failed to del from cart :/ ");
                    }
                };

                xhr.send(null);

            }

            function buy() {
                var cnt = 0;
                articles.forEach(function (art) {
                    //console.log(art);

                    var xhr = new XMLHttpRequest();
                    var d = new Date();
                    var date = '' + d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getUTCDate();

                    var tmp = 'Orders?command=make_order&order_num=' + parseInt(Math.random() * 10000000); //+ (Math.round(d)) + ''
                    tmp += '&product_id=' + art.product_id;
                    tmp += '&quandity=' + 1;
                    tmp += '&shipping_cost=' + art.purchase_cost;
                    tmp += '&sales_date=' + date;
                    tmp += '&shipping_date=' + date;
                    tmp += '&freight_company=' + fret_company;//atob(art.freight_company);

                    console.log(tmp);

                    xhr.open('GET', tmp);

                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            var response = eval("(" + xhr.responseText + ")");
                            console.log(response);
                            if (response.action_status == "fail")
                                notify("failed make an order on " + art.product_id + " :/ ");
                            dels_to_cart(art.product_id);
                            
                            cnt ++;
                            
                            if (cnt == articles.length)
                                window.location = "OrdersCall";
                        } else if (xhr.readyState === 4 && xhr.status !== 200) { //maybe session out...
                            notify("failed make an order on " + art.product_id + " :/ ");
                        }
                    };

                    xhr.send(null);

                });
                
            }



            article_list();


        </script>
    </body>
</html>
