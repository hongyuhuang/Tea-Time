<%-- 
    Document   : create-account
    Created on : 20/08/2022, 3:12:45 pm
    Author     : hongyuhuang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tea Time | Create Account</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <main>
            <%@include file="WEB-INF/jspf/navigation.jspf"%>

            <h1>Create an Account</h1>
            <fieldset>

                <legend>Account Details</legend>

                <%
                    String validation = (String) session.getAttribute("create-account-validation");
                    validation = validation != null ? validation : "";
                %>

                <p><%= validation%></p>

                <form action="create-account" method="POST">

                    <label>Username:</label><input type="text" name="username"/>
                    <label>First Name</label><input type="text" name="firstName"/>
                    <label>Last Name</label><input type="text" name="surname">
                    <label>Email:</label><input type="email" name="emailAddress"/>
                    <label>Address:</label><textarea name="shippingAddress"></textarea>
                    <label>Password:</label><input type="password" name="password"/>

                    <button type="submit">Create Account</button>
                </form>

            </fieldset>
        </main>
    </body>
</html>
