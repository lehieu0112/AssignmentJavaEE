/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.ProductsFacade;
import entities.Products;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Administrator
 */
@ManagedBean
@RequestScoped
public class ProductsManagedBean {
    @Inject
    private ProductsFacade productsEJB;
    private List<Products> productsList;
    private Products product = new Products();

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
    
    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
    
    public List<Products> doFindAllProducts(){
        return productsEJB.findAll();
    }
    
    public String searchProducts(int i){  
        switch(i){
            case 1:
                productsList = productsEJB.searchProducts(product.getProductName());
                break;
            case 2:
                productsList = productsEJB.searchProducts("NOTEBOOK");
                break;
            case 3:
                productsList = productsEJB.searchProducts("MOBILE");
                break;
            case 4:
                productsList = productsEJB.searchProducts("MTB");
                break;
            case 5:
                productsList = productsEJB.searchProducts("PC");
                break;
            default:break;                    
        }        
        return "search.xhtml";
    }
    
    public void doFindProductbyID(){
        this.product = productsEJB.find(product.getProductID());
    }
    
}
