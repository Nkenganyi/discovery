/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.controllers;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.entities.CreditCardLimit;

/**
 *
 * @author nkeng
 */
@RestController
@RequestMapping("/discovery")
public class TransactionController {

    @Autowired
    private ClientAccount clientAccount;
    
    private BigDecimal amount;
    private long clientAccountNumber;

    public TransactionController(BigDecimal amount, long clientAccountNumber) {
        this.amount = amount;
        this.clientAccountNumber = clientAccountNumber;
    }

    public TransactionController() {
    }
    
    
    public String message;
    
    String jpaName = "za.co.discovery_discovery_jar_0.0.1-SNAPSHOTPU";
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(jpaName);
    EntityManager em = emf.createEntityManager();

    @PostMapping("/withdraw")
    public String withdrawal(@RequestBody TransactionController tc) {

        try {
            em.getTransaction().begin();
            List<ClientAccount> accounts = (List<ClientAccount>) em.createQuery("SELECT ca FROM ClientAccount ca");
            double withdrawAmount = 0.0;
            for (int a = 0; a < accounts.size(); a++) {
                
                if (tc.clientAccountNumber == clientAccount.getClientAccountNumber()) {
                    clientAccount = accounts.get(a);
                    withdrawAmount = clientAccount.getDisplayBalance().doubleValue() - tc.amount.doubleValue();
                    if (clientAccount.getAccountTypeCode().equalsIgnoreCase("CHQ")) {
                        
                        clientAccount.setDisplayBalance((BigDecimal.valueOf(withdrawAmount)));
                        
                    } else if (clientAccount.getAccountTypeCode().equalsIgnoreCase("CCRD")
                            || clientAccount.getAccountTypeCode().equalsIgnoreCase("SVGS")) {
                        
                        CreditCardLimit ccl = em.find(CreditCardLimit.class, tc.clientAccountNumber);
                        
                        if (withdrawAmount <= ccl.getAccountLimit().doubleValue()) {

                             clientAccount.setDisplayBalance((BigDecimal.valueOf(withdrawAmount)));
                            message = "succesful";
                        }
                    }
                }

            }
            em.persist(this.clientAccount);
            
            em.getTransaction().commit();

        } catch (Exception ex) {
          em.getTransaction().rollback();
        }
        return message;
    }

}
