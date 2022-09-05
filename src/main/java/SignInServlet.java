/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dao.CustomerDAO;
import dao.DaoFactory;
import domain.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hongyuhuang
 */
@WebServlet(urlPatterns = {"/sign-in"})
public class SignInServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CustomerDAO dao = DaoFactory.getCustomerDAO();

        // extract the form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        HttpSession session = request.getSession();
        
        if(dao.match(username, password)){   
            Customer customer = dao.getByUsername(username);
            session.setAttribute("customer", customer);
            response.sendRedirect("index.jsp");
        }
        else{
            session.setAttribute("sign-in-validation", "You have entered an invalid username or password");
            response.sendRedirect("sign-in.jsp");
        }
    }
}
