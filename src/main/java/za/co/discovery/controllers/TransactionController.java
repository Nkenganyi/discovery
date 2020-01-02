/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.discovery.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.entities.Client;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.entities.CreditCardLimit;
import za.co.discovery.entities.CurrencyConversionRate;
import za.co.discovery.entities.Transactions;

/**
 *
 * @author nkeng
 */
@RestController
@RequestMapping("/discovery")
public class TransactionController {

    @Autowired
    private ClientAccount clientAccount;
    @Autowired
    private Transactions transaction;
    @Autowired
    private Client client;
    @Autowired
    private CurrencyConversionRate currencyConversionRate;
    CreditCardLimit creditCardLimit;
    public String message;

    String jpaName = "za.co.discovery_discovery_jar_0.0.1-SNAPSHOTPU";
    EntityManagerFactory emf;
    EntityManager em;

    @PostMapping("/withdraw")
    public String withdrawals(@RequestBody Transactions transaction) {

        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        // Checking If the Amount is Valid
        if (transaction.getAmount() % 2 != 0) {
            return "Invalid Amount Coins Not Allowed!!!";
        } else {
            clientAccount = em.find(ClientAccount.class, transaction.getAccountNumber());

            // checking if the Account Number Exist
            if (clientAccount != null) {

                List<CreditCardLimit> limits = em.createQuery("SELECT ccl FROM CreditCardLimit ccl").getResultList();
                for (CreditCardLimit limit : limits) {
                    if (limit.getClientAccountNumber().getClientAccountNumber() == transaction.getAccountNumber()) {
                        this.creditCardLimit = limit;
                    }
                }

                // checking for the different Cards
                if (clientAccount.getAccountTypeCode().equalsIgnoreCase("CCRD")) {

                    //TO the the Account Number to Get The Account Limit
                    if (transaction.getAmount() > creditCardLimit.getAccountLimit().doubleValue()) {

                        // MESSAGE  = "Exit Limit Try a Lesser Amount";   CCRD
                        return "Exit Limit Try a Lesser Amount";
                    } else {
                        try {
                            double balance = clientAccount.getDisplayBalance().doubleValue() - transaction.getAmount();

                            clientAccount.setDisplayBalance(BigDecimal.valueOf(balance));

                            this.transaction = transaction;

                            em.persist(clientAccount);
                            em.persist(this.transaction);

                            em.getTransaction().commit();

                            System.err.println("Successfull");

                            return "Done!!!!!!!!!!!!!";

                        } catch (Exception e) {
                            em.getTransaction().rollback();
                        }
                    }

                } else if (clientAccount.getAccountTypeCode().equalsIgnoreCase("CHQ")) {

                    try {
                        if (transaction.getAmount() > 10000) {

                            return "Insufficient Fund";

                        } else {

                            double balance = clientAccount.getDisplayBalance().doubleValue() + (-transaction.getAmount());

                            clientAccount.setDisplayBalance(BigDecimal.valueOf(balance));

                            this.transaction = transaction;

                            em.persist(clientAccount);
                            em.persist(this.transaction);

                            em.getTransaction().commit();

                            System.err.println("Successfull");

                            return clientAccount.toString();  // to See the Changes

                        }
                    } catch (Exception e) {
                        em.getTransaction().rollback();
                    }
                } else if (clientAccount.getAccountTypeCode().equalsIgnoreCase("SVGS")) {

                    if (clientAccount.getDisplayBalance().doubleValue() < transaction.getAmount()) {

                        return "Insufficient Fund";

                    } else {

                        double balance = clientAccount.getDisplayBalance().doubleValue() - transaction.getAmount();

                        clientAccount.setDisplayBalance(BigDecimal.valueOf(balance));

                        this.transaction = transaction;

                        em.persist(this.clientAccount);
                        em.persist(this.transaction);

                        em.getTransaction().commit();

                        System.err.println("Successfull");
                        return "DOne!!!!!!!!!!!!!";
                    }
                }

            } else {
                return "Account Number does't Exist";
            }

        }// End Of If Statement To Check Valid Amount

        return null;

    }

    @PostMapping("/display")
    public List<ClientAccount> displayAccounts(@RequestHeader long idNumber) {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();
        List<ClientAccount> customerAccounts = new ArrayList<>();
        List<ClientAccount> accounts = (List<ClientAccount>) em.createQuery("SELECT ca FROM ClientAccount ca").getResultList();
        for (ClientAccount ca : accounts) {
            if (ca.getClientId() == idNumber) {
                customerAccounts.add(ca);
            }
        }
          customerAccounts.sort((ca1, ca2) -> ca2.getDisplayBalance().compareTo(ca1.getDisplayBalance()));
          
        return customerAccounts;
    }

    
    @PostMapping("highestAccountBalancePerClient")
    public ClientAccount highestAccountBalancePerCLient(@RequestHeader long idNumber) {

        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<ClientAccount> clientAccounts = em.createQuery("SELECT ca FROM ClientAccount ca").getResultList();

        List<ClientAccount> allClientAccounts = clientAccounts.stream().filter((account)
                -> account.getClientId() == idNumber).collect(Collectors.toList());

        allClientAccounts.sort((ca1, ca2) -> ca2.getDisplayBalance().compareTo(ca1.getDisplayBalance()));
        
        clientAccount = allClientAccounts.get(0);
        
        return clientAccount;
    }

    
    @PostMapping("balanceInRandValue")
    public String balanceInRandValue(@RequestHeader long clientAccountNumber) {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<BigDecimal> rate = new ArrayList<>();
        List<ClientAccount>clientAccounts = new ArrayList<>();

        clientAccount = em.find(ClientAccount.class, clientAccountNumber);

        if (this.clientAccount == null) {
           
            message = "No Accounts to Display ";

        } else {
            clientAccounts = em.createQuery("SELECT ca FROM ClientAccount ca WHERE ca.clientId ="
                    + this.clientAccount.getClientId()).getResultList();

            for (int a = 0; a < clientAccounts.size(); a++) {

                currencyConversionRate = em.find(CurrencyConversionRate.class,
                        clientAccounts.get(a).getCurrencyCode());

                BigDecimal balance;

                if (currencyConversionRate.getConversionIndicator().equalsIgnoreCase("*")) {

                    balance = BigDecimal.valueOf(clientAccounts.get(a).getDisplayBalance().doubleValue()
                            * currencyConversionRate.getRate().doubleValue());

                    clientAccounts.get(a).setDisplayBalance(balance);

                } else if (currencyConversionRate.getConversionIndicator().equalsIgnoreCase("/")) {

                    balance = BigDecimal.valueOf(clientAccounts.get(a).getDisplayBalance().doubleValue()
                            / currencyConversionRate.getRate().doubleValue());

                    clientAccounts.get(a).setDisplayBalance(balance);
                }

            }// end of Loop

            for (ClientAccount cl : clientAccounts) {
                //Finding the Conversion Rate For a Particular ClientAccount
                currencyConversionRate = em.find(CurrencyConversionRate.class, cl.getCurrencyCode());

                rate.add(currencyConversionRate.getRate());
            }
        }

        return rate + " : " + " \n";
    }
    
    @PostMapping("financialPositionPerClient")
    public List<String> financialPositionPerClient() {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<String> listOfFinancialPositionPerClient = new ArrayList<>();
        double aggregateLoan = 0;
        double aggregateBalance = 0;
        double totalAggregate = 0;

       List<Client>clientList = em.createQuery("SELECT c FROM Client c").getResultList();
        
        for (int a = 0; a < clientList.size(); a++) {
            aggregateLoan = 0;
            aggregateBalance = 0;
           List<ClientAccount> clientAccounts = em.createQuery("SELECT a FROM ClientAccount a where a.clientId =" + clientList.get(a).getClientId()).getResultList();
         
            for (int b = 0; b < clientAccounts.size(); b++) {
             
                if (clientAccounts.get(b).getDisplayBalance().doubleValue() <= 0) {
                    aggregateLoan = aggregateLoan + clientAccounts.get(b).getDisplayBalance().doubleValue();
                } else {
                    aggregateBalance = aggregateBalance + clientAccounts.get(b).getDisplayBalance().doubleValue();
                }

            }
            totalAggregate = aggregateBalance + aggregateLoan;

            listOfFinancialPositionPerClient.add(clientList.get(a).getClientId() + " : " 
                    + clientList.get(a).getTitle() + " ; " + " : " + clientList.get(a).getName() 
                    + " : " + clientList.get(a).getSurname()+ " : " + aggregateBalance +
                    " : " + aggregateLoan + " : " + totalAggregate);
           
        }
      
        return listOfFinancialPositionPerClient;
    }
}
