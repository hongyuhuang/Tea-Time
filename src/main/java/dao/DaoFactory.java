/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hongyuhuang
 */
public class DaoFactory {
      
    public static ProductDAO getProductDAO() {
        return JdbiDaoFactory.getProductDAO();
    }
    
    public static CustomerDAO getCustomerDAO() {
        return JdbiDaoFactory.getCustomerDAO();
    }
}
