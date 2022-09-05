/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import domain.Customer;
import java.util.Collection;

/**
 *
 * @author hongyuhuang
 */
public interface CustomerDAO {

    /**
     * Adds a customer to the DAO.
     *
     * @param aCustomer The customer to add.
     */
    void save(Customer aCustomer);
    
    /**
     * Checks if a given username and password matches an existing customer.
     * 
     * @param username The username of the customer we are checking against.
     * @param password The password of the customer we are checking against.
     * @return If the customer with the matching username and password exist in the DAO.
     */
    boolean match(String username, String password);

    /**
     * Returns the customer matching the given username.
     *
     * @param username The username of the customer to retrieve.
     * @return The customer matching the username.
     */
    Customer getByUsername(String username);

    /**
     * Deletes a customer from the DAO.
     *
     * @param aStudent The customer to delete.
     */
    void delete(Customer aCustomer);
}
