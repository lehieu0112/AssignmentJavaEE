/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.NumberFormat;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "LineItems")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LineItems.findAll", query = "SELECT l FROM LineItems l"),
    @NamedQuery(name = "LineItems.findByLineItemID", query = "SELECT l FROM LineItems l WHERE l.lineItemID = :lineItemID"),
    @NamedQuery(name = "LineItems.findByQuantity", query = "SELECT l FROM LineItems l WHERE l.quantity = :quantity"),
    @NamedQuery(name = "LineItems.findByLineItemTotal", query = "SELECT l FROM LineItems l WHERE l.lineItemTotal = :lineItemTotal")})
public class LineItems implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)   
    @Column(name = "lineItemID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineItemID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "lineItemTotal")
    private double lineItemTotal;
    @JoinColumn(name = "invoiceID", referencedColumnName = "invoiceID")
    @ManyToOne(optional = false)
    private Invoices invoiceID;
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    @ManyToOne(optional = false)
    private Products productID;

    public LineItems() {
    }

    public LineItems(Integer lineItemID) {
        this.lineItemID = lineItemID;
    }

    public LineItems(Integer lineItemID, int quantity, double lineItemTotal) {
        this.lineItemID = lineItemID;
        this.quantity = quantity;
        this.lineItemTotal = lineItemTotal;
    }

    public Integer getLineItemID() {
        return lineItemID;
    }

    public void setLineItemID(Integer lineItemID) {
        this.lineItemID = lineItemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLineItemTotal() {
        return lineItemTotal;
    }
    
    public String getFormatLineItemTotal(){
        NumberFormat c = NumberFormat.getNumberInstance();
        return c.format(this.lineItemTotal);
    }

    public void setLineItemTotal(double lineItemTotal) {
        this.lineItemTotal = lineItemTotal;
    }

    public Invoices getInvoice() {
        return invoiceID;
    }

    public void setInvoice(Invoices invoice) {
        this.invoiceID = invoice;
    }

    public Products getProduct() {
        return productID;
    }

    public void setProduct(Products product) {
        this.productID = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lineItemID != null ? lineItemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LineItems)) {
            return false;
        }
        LineItems other = (LineItems) object;
        if ((this.lineItemID == null && other.lineItemID != null) || (this.lineItemID != null && !this.lineItemID.equals(other.lineItemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LineItems[ lineItemID=" + lineItemID + " ]";
    }
    
}
