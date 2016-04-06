/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Customers;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class CustomersFacade extends AbstractFacade<Customers> {
    @PersistenceContext(unitName = "FinalAssigmentJavaEEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomersFacade() {
        super(Customers.class);
    }
    
    public boolean isExistCustomer(Customers customer){
        boolean isExist = false;
        Query q = getEntityManager().createNamedQuery("Customers.findByCustomerEmail");
        q.setParameter("customerEmail", customer.getCustomerEmail());       
        if(q.getResultList().size()>0){
            isExist = true;
            Customers c =(Customers) q.getResultList().get(0);
            customer.setCustomerID(c.getCustomerID());
        }
        return isExist;
    }
    
}
