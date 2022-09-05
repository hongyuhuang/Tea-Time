/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dao.CustomerCollectionsDAO;
import dao.CustomerDAO;
import dao.DaoExceptionMapper;
import dao.DaoFactory;
import domain.Customer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 *
 * @author hongyuhuang
 */
@WebServlet(urlPatterns = {"/create-account"})
public class CreateAccountServlet extends HttpServlet {

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
        try {
            // extract the form data
            String username = request.getParameter("username");
            String firstName = request.getParameter("firstName");
            String surname = request.getParameter("surname");
            String emailAddress = request.getParameter("emailAddress");
            String shippingAddress = request.getParameter("shippingAddress");
            String password = request.getParameter("password");

            //create the customer object
            Customer customer = new Customer(username, firstName, surname, emailAddress, shippingAddress, password);

            //save the customer
            new Validator().assertValid(customer);
            dao.save(customer);
            
            response.sendRedirect("sign-in.jsp");
        } catch (ConstraintsViolatedException ex) {
            // get the violated constraints from the exception
            ConstraintViolation[] violations = ex.getConstraintViolations();

            // create a nice error message for the user
            String msg = "Please fix the following input problems:";

            msg += "<ul>";
            for (ConstraintViolation cv : violations) {
                msg += "<li>" + cv.getMessage() + "</li>";
            }
            msg += "</ul>";

            request.getSession().setAttribute("create-account-validation", msg);
            response.sendRedirect("create-account.jsp");

        } catch (Exception ex) {
            String msg = new DaoExceptionMapper().messageFromException(ex);
            request.getSession().setAttribute("create-account-validation", msg);
            response.sendRedirect("create-account.jsp");
        }
    }
}
