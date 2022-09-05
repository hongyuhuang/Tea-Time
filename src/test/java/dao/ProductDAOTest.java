/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import domain.Product;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
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
public class ProductDAOTest {

    private ProductCollectionsDAO productsCollections;
    private ProductDAO productsJdbi;
    
    private Product p1;
    private Product p2;
    private Product p3;

    @BeforeEach
    public void setUp() {
        productsCollections = new ProductCollectionsDAO();
        p1 = new Product("WD1234", "Slimy Widget", "A widget that is covered in some kind of nasty shmoo.", "Widgets", new BigDecimal("7.32"), new BigDecimal(35));
        p2 = new Product("WD1235", "Green Widget", "A widget that has gone mouldy.", "Widgets", new BigDecimal("21.43"), new BigDecimal(3));
        p3 = new Product("DH8832", "Dodgy Doohicky", "A doohicky that might work, or it might not...", "Doohickies", new BigDecimal("12.32"), new BigDecimal(5));
        productsCollections.saveProduct(p1);
        productsCollections.saveProduct(p2);
        
        productsJdbi = JdbiDaoFactory.getProductDAO();
        productsJdbi.saveProduct(p1);
        productsJdbi.saveProduct(p2);

    }

    @AfterEach
    public void tearDown() {
        productsCollections.removeProduct(p1);
        productsCollections.removeProduct(p2);
        productsCollections.removeProduct(p3);
        
        productsJdbi.removeProduct(p1);
        productsJdbi.removeProduct(p2);
        productsJdbi.removeProduct(p3);
    }

    @Test
    public void testFilterByCategory() {
        assertThat(productsCollections.filterByCategory("Widgets"), hasItem(p1));
        assertThat(productsCollections.filterByCategory("Widgets"), hasItem(p2));
        
        assertThat(productsJdbi.filterByCategory("Widgets"), hasItem(p1));
        assertThat(productsJdbi.filterByCategory("Widgets"), hasItem(p2));
    }

    @Test
    public void testGetCategories() {
        assertThat(productsCollections.getCategories(), Matchers.containsInAnyOrder("Widgets"));
        
        assertThat(productsJdbi.getCategories(), Matchers.containsInAnyOrder("Widgets"));
    }

    @Test
    public void testGetProducts() {
        assertThat(productsCollections.getProducts(), hasSize(2));
        assertThat(productsCollections.getProducts(), Matchers.containsInAnyOrder(p1, p2));
        
        assertThat(productsJdbi.getProducts(), hasSize(2));
        assertThat(productsJdbi.getProducts(), Matchers.containsInAnyOrder(p1, p2));
    }

    @Test
    public void testRemoveProduct() {
        assertThat(productsCollections.getProducts(), hasItem(p2));
        assertThat(productsCollections.getProducts(), hasSize(2));
        productsCollections.removeProduct(p2);
        assertThat(productsCollections.getProducts(), not(hasItem(p2)));
        assertThat(productsCollections.getProducts(), hasSize(1));
        
        assertThat(productsJdbi.getProducts(), hasItem(p2));
        assertThat(productsJdbi.getProducts(), hasSize(2));
        productsJdbi.removeProduct(p2);
        assertThat(productsJdbi.getProducts(), not(hasItem(p2)));
        assertThat(productsJdbi.getProducts(), hasSize(1));
    }

    @Test
    public void testSaveProduct() {
        assertThat(productsCollections.getProducts(), not(hasItem(p3)));
        assertThat(productsCollections.getProducts(), hasSize(2));
        productsCollections.saveProduct(p3);
        assertThat(productsCollections.getProducts(), hasItem(p3));
        assertThat(productsCollections.getProducts(), hasSize(3));
        
        assertThat(productsJdbi.getProducts(), not(hasItem(p3)));
        assertThat(productsJdbi.getProducts(), hasSize(2));
        productsJdbi.saveProduct(p3);
        assertThat(productsJdbi.getProducts(), hasItem(p3));
        assertThat(productsJdbi.getProducts(), hasSize(3));
    }

    @Test
    public void testSearchById() {
        assertThat(productsCollections.searchById("WD1234"), is(p1));
        assertThat(productsCollections.searchById("WD1235"), is(p2));
        
        assertThat(productsJdbi.searchById("WD1234"), is(p1));
        assertThat(productsJdbi.searchById("WD1235"), is(p2));
    }
}
