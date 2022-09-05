/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Customer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hongyuhuang
 */
public class CustomerDAOTest {

    private CustomerCollectionsDAO customersCollections;
    private CustomerDAO customersJdbi;

    private Customer c1;
    private Customer c2;
    private Customer c3;

    @BeforeEach
    public void setUp() {
        customersCollections = new CustomerCollectionsDAO();
        c1 = new Customer("hagmy", "Mya", "Hagget", "hagmy@student.otago.ac.nz", "57 Heriot Row Dunedin", "hagmy");
        c2 = new Customer("huaho", "Hongyu", "Huang", "huaho@student.otago.ac.nz", "57 Heriot Row Dunedin", "huaho");
        c3 = new Customer("mcgsi", "Siobhan", "McGhee-Turnock", "mcgsi@student.otago.ac.nz", "57 Heriot Row Dunedin", "mcgsi");
        customersCollections.save(c1);
        customersCollections.save(c2);
        
        customersJdbi = JdbiDaoFactory.getCustomerDAO();
        customersJdbi.save(c1);
        customersJdbi.save(c2);
    }

    @AfterEach
    public void tearDown() {
        customersCollections.delete(c1);
        customersCollections.delete(c1);
        customersCollections.delete(c3);
        customersJdbi.delete(c1);
        customersJdbi.delete(c2);
        customersJdbi.delete(c3);
    }

    @Test
    public void testSave() {
        assertThat(customersCollections.getByUsername("hagmy"), is(c1));
        assertThat(customersCollections.getByUsername("huaho"), is(c2));
        assertThat(customersCollections.getByUsername("mcgsi"), not(is(c3)));

        customersCollections.save(c3);

        assertThat(customersCollections.getByUsername("mcgsi"), is(c3));
        
        assertThat(customersJdbi.getByUsername("hagmy"), is(c1));
        assertThat(customersJdbi.getByUsername("huaho"), is(c2));
        assertThat(customersJdbi.getByUsername("mcgsi"), not(is(c3)));

        customersJdbi.save(c3);

        assertThat(customersJdbi.getByUsername("mcgsi"), is(c3));
    }

    @Test
    public void testMatch() {
        assertTrue(customersCollections.match("hagmy", "hagmy"));
        assertTrue(customersCollections.match("huaho", "huaho"));
        assertFalse(customersCollections.match("hagmy", "1234"));
        assertFalse(customersCollections.match("huaho", "1234"));
        
        assertTrue(customersJdbi.match("hagmy", "hagmy"));
        assertTrue(customersJdbi.match("huaho", "huaho"));
        assertFalse(customersJdbi.match("hagmy", "1234"));
        assertFalse(customersJdbi.match("huaho", "1234"));
    }

    @Test
    public void testGetByUsername() {
        assertThat(customersCollections.getByUsername("hagmy"), is(c1));
        assertThat(customersCollections.getByUsername("huaho"), is(c2));
        
        assertThat(customersJdbi.getByUsername("hagmy"), is(c1));
        assertThat(customersJdbi.getByUsername("huaho"), is(c2));
    }

    @Test
    public void testDelete() {
        assertThat(customersCollections.getByUsername("hagmy"), is(c1));
        assertThat(customersCollections.getByUsername("huaho"), is(c2));

        customersCollections.delete(c2);

        assertThat(customersCollections.getByUsername("huaho"), not(is(c2)));
        
        assertThat(customersJdbi.getByUsername("hagmy"), is(c1));
        assertThat(customersJdbi.getByUsername("huaho"), is(c2));

        customersJdbi.delete(c2);

        assertThat(customersJdbi.getByUsername("huaho"), not(is(c2)));
    }
}
