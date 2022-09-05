/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import domain.Customer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author hongyuhuang
 */
public interface CustomerJdbiDAO extends CustomerDAO {

    @Override
    @SqlUpdate("INSERT INTO CUSTOMER(username, firstName, surname, emailAddress, shippingAddress, password) values (:username, :firstName, :surname, :emailAddress, :shippingAddress, :password)")
    public void save(@BindBean Customer aCustomer);

    @Override
    @SqlQuery("select exists (select * from CUSTOMER where username = :username and password = :password)")
    public boolean match(@Bind("username") String username, @Bind("password") String password);
    
    @Override
    @SqlQuery("SELECT * FROM CUSTOMER WHERE username = :username")
    @RegisterBeanMapper(Customer.class)
    public Customer getByUsername(@Bind("username") String username);
    
    @Override
    @SqlUpdate("DELETE FROM CUSTOMER WHERE username = :username")
    public void delete(@BindBean Customer aCustomer);
}
