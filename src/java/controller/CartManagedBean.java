/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.CustomersFacade;
import ejb.InvoicesFacade;
import ejb.LineItemsFacade;
import ejb.ProductsFacade;
import entities.Invoices;
import entities.LineItems;
import entities.Products;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class CartManagedBean {

    @Inject
    private InvoicesFacade invoiceEJB;
    @Inject
    private CustomersFacade customerEJB;
    @Inject
    private LineItemsFacade lineItemEJB;
    @Inject
    private ProductsFacade productEJB;

    private Invoices invoice = new Invoices();

    public Invoices getInvoice() {
        return invoice;
    }

    private double getInvoiceTotal() {
        double t = 0;
        for (LineItems lineItem : invoice.getLineItemsList()) {
            t = t + lineItem.getLineItemTotal();
        }
        return t;
    }

    public void setInvoice(Invoices invoice) {
        this.invoice = invoice;
    }

    public String addLineItem(int id) {
        LineItems lineItem = new LineItems();
        Products p = productEJB.find(id);
        lineItem.setProduct(p);
        int quantity = 1;
        lineItem.setQuantity(quantity);
        invoice.addItem(lineItem);
        invoice.setInvoiceTotal(getInvoiceTotal());
        return "cart.xhtml";
    }

    public String removeLineItem(int id) {
        LineItems lineItem = new LineItems();
        Products p = productEJB.find(id);
        lineItem.setProduct(p);
        invoice.removeItem(lineItem);
        invoice.setInvoiceTotal(getInvoiceTotal());
        return "cart.xhtml";
    }

    public String updateLineItem(int id, int quantity) {
        LineItems lineItem = new LineItems();
        Products p = productEJB.find(id);
        lineItem.setProduct(p);
        lineItem.setQuantity(quantity);
        invoice.updateItem(lineItem);
        invoice.setInvoiceTotal(getInvoiceTotal());
        return "cart.xhtml";
    }

    public void insertInvoice() {
        boolean trans = false;
        Invoices invoice1 = new Invoices();
        try {
            if (customerEJB.isExistCustomer(invoice.getCustomer())) {
                customerEJB.edit(invoice.getCustomer());
            } else {
                customerEJB.create(invoice.getCustomer());
            }
            trans = true;
        } catch (Exception e) {
            System.out.println(e);
            trans = false;
        }
        if (trans) {
            try {
                invoice1.setCustomer(invoice.getCustomer());
                invoice1.setInvoiceTotal(invoice.getInvoiceTotal());
                invoiceEJB.create(invoice1);
                trans = true;
            } catch (Exception e) {
                System.out.println(e);
                trans = false;
                customerEJB.remove(invoice.getCustomer());
            }
        }
        if (trans) {
            try {
                for (LineItems item : invoice.getLineItemsList()) {
                    item.setInvoice(invoice1);
                    lineItemEJB.create(item);
                }
                trans = true;
            } catch (Exception e) {
                System.out.println(e);
                trans = false;
                invoiceEJB.remove(invoice1);  
                customerEJB.remove(invoice.getCustomer());
            }
        }
        if(trans){
            invoice = new Invoices();
        }       
    }

}
