/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
 * @author nkeng
 */
@Entity
@Table(name = "CREDIT_CARD_LIMIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CreditCardLimit.findAll", query = "SELECT c FROM CreditCardLimit c"),
    @NamedQuery(name = "CreditCardLimit.findByCreditCardLimitId", query = "SELECT c FROM CreditCardLimit c WHERE c.creditCardLimitId = :creditCardLimitId"),
    @NamedQuery(name = "CreditCardLimit.findByAccountLimit", query = "SELECT c FROM CreditCardLimit c WHERE c.accountLimit = :accountLimit")})
public class CreditCardLimit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CREDIT_CARD_LIMIT_ID")
    private Integer creditCardLimitId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACCOUNT_LIMIT")
    private BigDecimal accountLimit;
    @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER", referencedColumnName = "CLIENT_ACCOUNT_NUMBER")
    @ManyToOne(optional = false)
    private ClientAccount clientAccountNumber;

    public CreditCardLimit() {
    }

    public CreditCardLimit(Integer creditCardLimitId) {
        this.creditCardLimitId = creditCardLimitId;
    }

    public CreditCardLimit(Integer creditCardLimitId, BigDecimal accountLimit) {
        this.creditCardLimitId = creditCardLimitId;
        this.accountLimit = accountLimit;
    }

    public Integer getCreditCardLimitId() {
        return creditCardLimitId;
    }

    public void setCreditCardLimitId(Integer creditCardLimitId) {
        this.creditCardLimitId = creditCardLimitId;
    }

    public BigDecimal getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(BigDecimal accountLimit) {
        this.accountLimit = accountLimit;
    }

    public ClientAccount getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(ClientAccount clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditCardLimitId != null ? creditCardLimitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreditCardLimit)) {
            return false;
        }
        CreditCardLimit other = (CreditCardLimit) object;
        if ((this.creditCardLimitId == null && other.creditCardLimitId != null) || (this.creditCardLimitId != null && !this.creditCardLimitId.equals(other.creditCardLimitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.CreditCardLimit[ creditCardLimitId=" + creditCardLimitId + " ]";
    }
    
}
