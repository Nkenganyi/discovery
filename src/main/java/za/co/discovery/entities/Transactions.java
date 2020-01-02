/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.stereotype.Service;

/**
 *
 * @author nkeng
 */
@Entity
@Service
public class Transactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
    private String transactionType;
    private double amount;
    private Long accountNumber;

    public Transactions() {
    }

    public Transactions(long transactionId, String transactionType, double transactionAmount, long accountNumber) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = transactionAmount;
        this.accountNumber = accountNumber;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.transactionId ^ (this.transactionId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transactions other = (Transactions) obj;
        if (this.transactionId != other.transactionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transactions{" + "transactionId=" + transactionId + 
                ", transactionType=" + transactionType + ", transactionAmount=" 
                + amount + ", accountNumber=" + accountNumber + '}';
    }
    
    
    
    

  
}
