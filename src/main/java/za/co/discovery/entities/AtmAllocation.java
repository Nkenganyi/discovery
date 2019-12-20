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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ATM_ALLOCATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtmAllocation.findAll", query = "SELECT a FROM AtmAllocation a"),
    @NamedQuery(name = "AtmAllocation.findByAtmAllocationId", query = "SELECT a FROM AtmAllocation a WHERE a.atmAllocationId = :atmAllocationId"),
    @NamedQuery(name = "AtmAllocation.findByAtmId", query = "SELECT a FROM AtmAllocation a WHERE a.atmId = :atmId"),
    @NamedQuery(name = "AtmAllocation.findByDenominationId", query = "SELECT a FROM AtmAllocation a WHERE a.denominationId = :denominationId"),
    @NamedQuery(name = "AtmAllocation.findByCount", query = "SELECT a FROM AtmAllocation a WHERE a.count = :count")})
public class AtmAllocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATM_ALLOCATION_ID")
    private Integer atmAllocationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ATM_ID")
    private int atmId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DENOMINATION_ID")
    private int denominationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COUNT")
    private int count;

    public AtmAllocation() {
    }

    public AtmAllocation(Integer atmAllocationId) {
        this.atmAllocationId = atmAllocationId;
    }

    public AtmAllocation(Integer atmAllocationId, int atmId, int denominationId, int count) {
        this.atmAllocationId = atmAllocationId;
        this.atmId = atmId;
        this.denominationId = denominationId;
        this.count = count;
    }

    public Integer getAtmAllocationId() {
        return atmAllocationId;
    }

    public void setAtmAllocationId(Integer atmAllocationId) {
        this.atmAllocationId = atmAllocationId;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    public int getDenominationId() {
        return denominationId;
    }

    public void setDenominationId(int denominationId) {
        this.denominationId = denominationId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atmAllocationId != null ? atmAllocationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtmAllocation)) {
            return false;
        }
        AtmAllocation other = (AtmAllocation) object;
        if ((this.atmAllocationId == null && other.atmAllocationId != null) || (this.atmAllocationId != null && !this.atmAllocationId.equals(other.atmAllocationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.AtmAllocation[ atmAllocationId=" + atmAllocationId + " ]";
    }
    
}
