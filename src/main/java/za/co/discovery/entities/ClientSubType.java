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
@Table(name = "CLIENT_SUB_TYPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientSubType.findAll", query = "SELECT c FROM ClientSubType c"),
    @NamedQuery(name = "ClientSubType.findByClientSubTypeCode", query = "SELECT c FROM ClientSubType c WHERE c.clientSubTypeCode = :clientSubTypeCode"),
    @NamedQuery(name = "ClientSubType.findByClientTypeCode", query = "SELECT c FROM ClientSubType c WHERE c.clientTypeCode = :clientTypeCode"),
    @NamedQuery(name = "ClientSubType.findByDescription", query = "SELECT c FROM ClientSubType c WHERE c.description = :description")})
public class ClientSubType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CLIENT_SUB_TYPE_CODE")
    private String clientSubTypeCode;
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

    public ClientSubType() {
    }

    public ClientSubType(String clientSubTypeCode) {
        this.clientSubTypeCode = clientSubTypeCode;
    }

    public ClientSubType(String clientSubTypeCode, String clientTypeCode, String description) {
        this.clientSubTypeCode = clientSubTypeCode;
        this.clientTypeCode = clientTypeCode;
        this.description = description;
    }

    public String getClientSubTypeCode() {
        return clientSubTypeCode;
    }

    public void setClientSubTypeCode(String clientSubTypeCode) {
        this.clientSubTypeCode = clientSubTypeCode;
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
        hash += (clientSubTypeCode != null ? clientSubTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientSubType)) {
            return false;
        }
        ClientSubType other = (ClientSubType) object;
        if ((this.clientSubTypeCode == null && other.clientSubTypeCode != null) || (this.clientSubTypeCode != null && !this.clientSubTypeCode.equals(other.clientSubTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.ClientSubType[ clientSubTypeCode=" + clientSubTypeCode + " ]";
    }
    
}
