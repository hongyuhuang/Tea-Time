<%-- 
    Document   : sign-in
    Created on : 20/08/2022, 3:12:33 pm
    Author     : hongyuhuang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tea Time | Sign In</title>
        <link rel="stylesheet" href="css/style.css"/>

    </head>
    <body>
        <main>
            <%@include file="WEB-INF/jspf/navigation.jspf"%>

            <h1>Sign In</h1>

            <p>Please sign in to continue.</p>

            <%
                String validation = (String) session.getAttribute("sign-in-validation");
                validation = validation != null ? validation : "";
            %>

            <p><%= validation%></p>

            <fieldset>

                <legend>Sign In</legend>

                <form action="sign-in" method="POST">

                    <label>Username:</label><input type="text" name="username"/>
                    <label>Password:</label><input type="password" name="password"/>

                    <button type="submit">Sign In</button>
                </form>

            </fieldset>

            <p>If you don't have an account then you can <a href="create-account.jsp">create one</a>.</p>


        </main>
    </body>
</html>
