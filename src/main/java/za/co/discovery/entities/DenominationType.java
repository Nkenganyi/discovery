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
@Table(name = "DENOMINATION_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DenominationType.findAll", query = "SELECT d FROM DenominationType d"),
    @NamedQuery(name = "DenominationType.findByDenominationTypeCode", query = "SELECT d FROM DenominationType d WHERE d.denominationTypeCode = :denominationTypeCode"),
    @NamedQuery(name = "DenominationType.findByDescription", query = "SELECT d FROM DenominationType d WHERE d.description = :description")})
public class DenominationType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "DENOMINATION_TYPE_CODE")
    private String denominationTypeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    public DenominationType() {
    }

    public DenominationType(String denominationTypeCode) {
        this.denominationTypeCode = denominationTypeCode;
    }

    public DenominationType(String denominationTypeCode, String description) {
        this.denominationTypeCode = denominationTypeCode;
        this.description = description;
    }

    public String getDenominationTypeCode() {
        return denominationTypeCode;
    }

    public void setDenominationTypeCode(String denominationTypeCode) {
        this.denominationTypeCode = denominationTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (denominationTypeCode != null ? denominationTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DenominationType)) {
            return false;
        }
        DenominationType other = (DenominationType) object;
        if ((this.denominationTypeCode == null && other.denominationTypeCode != null) || (this.denominationTypeCode != null && !this.denominationTypeCode.equals(other.denominationTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.DenominationType[ denominationTypeCode=" + denominationTypeCode + " ]";
    }
    
}
