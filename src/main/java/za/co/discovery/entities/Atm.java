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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.stereotype.Service;

/**
 *
 * @author nkeng
 */
@Service
@Entity
@Table(name = "ATM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atm.findAll", query = "SELECT a FROM Atm a"),
    @NamedQuery(name = "Atm.findByAtmId", query = "SELECT a FROM Atm a WHERE a.atmId = :atmId"),
    @NamedQuery(name = "Atm.findByName", query = "SELECT a FROM Atm a WHERE a.name = :name"),
    @NamedQuery(name = "Atm.findByLocation", query = "SELECT a FROM Atm a WHERE a.location = :location")})
public class Atm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATM_ID")
    private Integer atmId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LOCATION")
    private String location;

    public Atm() {
    }

    public Atm(Integer atmId) {
        this.atmId = atmId;
    }

    public Atm(Integer atmId, String name, String location) {
        this.atmId = atmId;
        this.name = name;
        this.location = location;
    }

    public Integer getAtmId() {
        return atmId;
    }

    public void setAtmId(Integer atmId) {
        this.atmId = atmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atmId != null ? atmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atm)) {
            return false;
        }
        Atm other = (Atm) object;
        if ((this.atmId == null && other.atmId != null) || (this.atmId != null && !this.atmId.equals(other.atmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "za.co.discovery.entities.Atm[ atmId=" + atmId + " ]";
    }
    
}
