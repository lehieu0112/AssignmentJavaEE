/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "Invoices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoices.findAll", query = "SELECT i FROM Invoices i"),
    @NamedQuery(name = "Invoices.findByInvoiceID", query = "SELECT i FROM Invoices i WHERE i.invoiceID = :invoiceID"),
    @NamedQuery(name = "Invoices.findByInvoiceTotal", query = "SELECT i FROM Invoices i WHERE i.invoiceTotal = :invoiceTotal")})
public class Invoices implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)    
    @Column(name = "invoiceID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "invoiceTotal")
    private double invoiceTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceID")
    private List<LineItems> lineItemsList;
    @JoinColumn(name = "customerID", referencedColumnName = "customerID")
    @ManyToOne(optional = false)
    private Customers customerID;

    public Invoices() {
        invoiceTotal = 0;
        lineItemsList = new ArrayList<LineItems>();
        customerID = new Customers();
    }
    
    public void addItem(LineItems item){
        int  productID = item.getProduct().getProductID();
        int quantity = item.getQuantity();
        item.setLineItemTotal(item.getProduct().getPrice()*item.getQuantity());
        for(int i=0;i<lineItemsList.size();i++){
            LineItems lineItem = lineItemsList.get(i);
            if(lineItem.getProduct().getProductID() == productID){
               int currentQuantity = lineItem.getQuantity();
               lineItem.setQuantity(quantity+currentQuantity);
               lineItem.setLineItemTotal(lineItem.getProduct().getPrice()*lineItem.getQuantity());
               return;
            }
        }
        lineItemsList.add(item);
    }
    
    public void updateItem(LineItems item){
        int productID = item.getProduct().getProductID();
        int quantity = item.getQuantity();
        for(int i=0;i<lineItemsList.size();i++){
            LineItems lineItem = lineItemsList.get(i);
            if(lineItem.getProduct().getProductID() == productID){            
               lineItem.setQuantity(quantity);
               lineItem.setLineItemTotal(lineItem.getProduct().getPrice()*lineItem.getQuantity());
               return;
            }
        }
    }
    
    public void removeItem(LineItems item){
        int productID = item.getProduct().getProductID();
        for(int i=0;i<lineItemsList.size();i++){
            LineItems lineItem = lineItemsList.get(i);
            if(lineItem.getProduct().getProductID() == productID){
               lineItemsList.remove(i);
               return;
            }
        }
    }

    public Invoices(Integer invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Integer getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Integer invoiceID) {
        this.invoiceID = invoiceID;
    }

    public double getInvoiceTotal() {
        return invoiceTotal;
    }
    
    public String getFormatInvoiceTotal(){
        NumberFormat c = NumberFormat.getNumberInstance();
        return c.format(this.invoiceTotal);
    }
    
    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    @XmlTransient
    public List<LineItems> getLineItemsList() {
        return lineItemsList;
    }

    public void setLineItemsList(List<LineItems> lineItemsList) {
        this.lineItemsList = lineItemsList;
    }

    public Customers getCustomer() {
        return customerID;
    }

    public void setCustomer(Customers customer) {
        this.customerID = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceID != null ? invoiceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoices)) {
            return false;
        }
        Invoices other = (Invoices) object;
        if ((this.invoiceID == null && other.invoiceID != null) || (this.invoiceID != null && !this.invoiceID.equals(other.invoiceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Invoices[ invoiceID=" + invoiceID + " ]";
    }
    
}
