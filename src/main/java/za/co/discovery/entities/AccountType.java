/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.entities;

import java.io.Serializable;
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
@Table(name = "ACCOUNT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountType.findAll", query = "SELECT a FROM AccountType a"),
    @NamedQuery(name = "AccountType.findByAccountTypeCode", query = "SELECT a FROM AccountType a WHERE a.accountTypeCode = :accountTypeCode"),
    @NamedQuery(name = "AccountType.findByDescription", query = "SELECT a FROM AccountType a WHERE a.description = :description"),
    @NamedQuery(name = "AccountType.findByTransactional", query = "SELECT a FROM AccountType a WHERE a.transactional = :transactional")})
public class AccountType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ACCOUNT_TYPE_CODE")
    private String accountTypeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "TRANSACTIONAL")
    private Boolean transactional;

    public AccountType() {
    }

    public AccountType(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public AccountType(String accountTypeCode, String description) {
        this.accountTypeCode = accountTypeCode;
        this.description = description;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getTransactional() {
        return transactional;
    }

    public void setTransactional(Boolean transactional) {
        this.transactional = transactional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountTypeCode != null ? accountTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountType)) {
            return false;
        }
        AccountType other = (AccountType) object;
        if ((this.accountTypeCode == null && other.accountTypeCode != null) || (this.accountTypeCode != null && !this.accountTypeCode.equals(other.accountTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.AccountType[ accountTypeCode=" + accountTypeCode + " ]";
    }
    
}
