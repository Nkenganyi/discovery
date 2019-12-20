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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nkeng
 */
@Entity
@Table(name = "CURRENCY_CONVERSION_RATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CurrencyConversionRate.findAll", query = "SELECT c FROM CurrencyConversionRate c"),
    @NamedQuery(name = "CurrencyConversionRate.findByCurrencyCode", query = "SELECT c FROM CurrencyConversionRate c WHERE c.currencyCode = :currencyCode"),
    @NamedQuery(name = "CurrencyConversionRate.findByConversionIndicator", query = "SELECT c FROM CurrencyConversionRate c WHERE c.conversionIndicator = :conversionIndicator"),
    @NamedQuery(name = "CurrencyConversionRate.findByRate", query = "SELECT c FROM CurrencyConversionRate c WHERE c.rate = :rate")})
public class CurrencyConversionRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CONVERSION_INDICATOR")
    private String conversionIndicator;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATE")
    private BigDecimal rate;

    public CurrencyConversionRate() {
    }

    public CurrencyConversionRate(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CurrencyConversionRate(String currencyCode, String conversionIndicator, BigDecimal rate) {
        this.currencyCode = currencyCode;
        this.conversionIndicator = conversionIndicator;
        this.rate = rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getConversionIndicator() {
        return conversionIndicator;
    }

    public void setConversionIndicator(String conversionIndicator) {
        this.conversionIndicator = conversionIndicator;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currencyCode != null ? currencyCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrencyConversionRate)) {
            return false;
        }
        CurrencyConversionRate other = (CurrencyConversionRate) object;
        if ((this.currencyCode == null && other.currencyCode != null) || (this.currencyCode != null && !this.currencyCode.equals(other.currencyCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.CurrencyConversionRate[ currencyCode=" + currencyCode + " ]";
    }
    
}
