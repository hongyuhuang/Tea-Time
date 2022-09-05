/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import domain.Product;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/*
 *
 * @author hongyuhuang
 */
public interface ProductJdbiDAO extends ProductDAO {  
    @Override
    @SqlQuery("SELECT * FROM PRODUCT WHERE category = :category")
    @RegisterBeanMapper(Product.class)
    public Collection<Product> filterByCategory(@Bind("category")String category);
    
    @Override
    @SqlQuery("SELECT DISTINCT category FROM PRODUCT order by category")
    public Collection<String> getCategories();
    
    @Override
    @SqlQuery("SELECT * FROM PRODUCT ORDER BY productID")
    @RegisterBeanMapper(Product.class)
    public Collection<Product> getProducts();
    
    @Override
    @SqlUpdate("DELETE FROM PRODUCT WHERE productID = :productId")
    public void removeProduct(@BindBean Product aProduct);
    
    @Override
    @SqlUpdate("MERGE INTO PRODUCT(productID, name, description, category, listPrice, quantityInStock) values (:productId, :name, :description, :category, :listPrice, :quantityInStock)")
    public void saveProduct(@BindBean Product aProduct);
    
    @Override
    @SqlQuery("SELECT * FROM PRODUCT WHERE productID = :productId")
    @RegisterBeanMapper(Product.class)
    public Product searchById(@Bind("productId")String productId);
}
