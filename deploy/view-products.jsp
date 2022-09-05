<%-- 
    Document   : view-products
    Created on : 20/08/2022, 3:14:03 pm
    Author     : hongyuhuang
--%>

<%@page import="domain.Product"%>
<%@page import="java.util.Collection"%>
<%@page import="dao.ProductDAO"%>
<%@page import="dao.DaoFactory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tea Time | Products</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <main>
            <%@include file="WEB-INF/jspf/navigation.jspf"%>

            <h1>Products</h1>
            
            <a href="view-products.jsp?category=All"><button class = "category">All</button></a>

            <%
                ProductDAO dao = DaoFactory.getProductDAO();

                Collection<String> categories = dao.getCategories();
                
                for (String category : categories) {
                    System.out.println(category);
                }
                

                for (String category : categories) {
            %>
            <a href="view-products.jsp?category=<%= category%>"><button class = "category"><%= category%></button></a>
                    <%
                        }
                    %>

            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Available</th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        // get the category from the query parameter
                        String category = request.getParameter("category");

                        // declare the students collection
                        Collection<Product> products;

                        // if there is no major parameter, or "All" is requested, return all students
                        if (category == null || category.equals("All")) {
                            products = dao.getProducts();
                        } else {
                            // otherwise, get the students for the requested major
                            products = dao.filterByCategory(category);
                        }
                        for (Product product : products) {
                    %>
                    <tr>
                        <td><%= product.getName()%></td>
                        <td><%= product.getDescription()%></td>
                        <td><%= product.getListPrice() %></td>
                        <td><%= product.getQuantityInStock()%></td>
                        <td><button>Buy</button></td>
                    </tr>
                    <%
                        }
                    %>
                    </main>
                    </body>
                    </html>
