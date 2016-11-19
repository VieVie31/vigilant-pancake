<%-- 
    Document   : login
    Created on : 15 nov. 2016, 15:11:38
    Author     : VieVie31
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript" src="js/notif.js"></script>
    </head>
    <body>
        <div id=login-form">
            <h1>Login</h1>

            <notif class="notification" style="display:${sessionScope.try_to_log_in};color:red;">Bad inputs... :'(</notif>

            <fieldset>
                <form action="Login" method="POST">
                    E-Mail : </br>
                    <input id="email" type="text" name="email" placeholder="email..."> </br>

                    Password : </br>
                    <input id="password" type="password" name="password" placeholder="password..."> </br>

                    <input id="submit" type="submit" value="GO !">
                </form>
            </fieldset>
        </div>
            
        <div id="nofitication_center"></div>
    </body>
</html>
