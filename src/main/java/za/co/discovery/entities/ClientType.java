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
@Table(name = "CLIENT_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientType.findAll", query = "SELECT c FROM ClientType c"),
    @NamedQuery(name = "ClientType.findByClientTypeCode", query = "SELECT c FROM ClientType c WHERE c.clientTypeCode = :clientTypeCode"),
    @NamedQuery(name = "ClientType.findByDescription", query = "SELECT c FROM ClientType c WHERE c.description = :description")})
public class ClientType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CLIENT_TYPE_CODE")
    private String clientTypeCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    public ClientType() {
    }

    public ClientType(String clientTypeCode) {
        this.clientTypeCode = clientTypeCode;
    }

    public ClientType(String clientTypeCode, String description) {
        this.clientTypeCode = clientTypeCode;
        this.description = description;
    }

    public String getClientTypeCode() {
        return clientTypeCode;
    }

    public void setClientTypeCode(String clientTypeCode) {
        this.clientTypeCode = clientTypeCode;
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
        hash += (clientTypeCode != null ? clientTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientType)) {
            return false;
        }
        ClientType other = (ClientType) object;
        if ((this.clientTypeCode == null && other.clientTypeCode != null) || (this.clientTypeCode != null && !this.clientTypeCode.equals(other.clientTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.ClientType[ clientTypeCode=" + clientTypeCode + " ]";
    }
    
}
