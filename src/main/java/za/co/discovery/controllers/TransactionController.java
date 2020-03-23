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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.entities.Atm;
import za.co.discovery.entities.AtmAllocation;
import za.co.discovery.entities.Client;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.entities.CreditCardLimit;
import za.co.discovery.entities.CurrencyConversionRate;
import za.co.discovery.entities.Denomination;
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
    @Autowired
    private Atm atm;
    @Autowired
    private Denomination deno;
    @Autowired
    private AtmAllocation atmAllocation;
    public String message;
    List<AtmAllocation> atmAllocations = new ArrayList<>();

    String jpaName = "za.co.discovery_discovery_jar_0.0.1-SNAPSHOTPU";
    EntityManagerFactory emf;
    EntityManager em;

    @CrossOrigin(origins = "*")
    @PostMapping("/withdraw")
    public String withdrawals(@RequestBody Transactions transaction) {

        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        // Checking If the Amount is Valid
        if (false) {
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
                this.atm = em.find(Atm.class, transaction.getAtmId());
                if (atm != null) {
                    this.atmAllocations = em.createQuery("SELECT a FROM AtmAllocation a WHERE a.atmId =" + transaction.getAtmId()).getResultList();
                    List<Denomination> denos = em.createQuery("SELECT de FROM Denomination de").getResultList();
                    //List<BigDecimal> denov = em.createQuery("SELECT d.value FROM Denomination d").getResultList();
                    denos.sort((d1, d2) -> d2.getValue().compareTo(d1.getValue()));
                    int[] counts = new int[denos.size()];
                    double bigDecimal = 0.0;
                    for (int i = 0; i < denos.size(); i++) {
                        this.deno = denos.get(i);
                        if (transaction.getAmount() >= denos.get(i).getValue().doubleValue()) {
                            
                            counts[i] = (int) (transaction.getAmount() / denos.get(i).getValue().doubleValue());
                            int count = counts[i];
                            transaction.setAmount(transaction.getAmount() % denos.get(i).getValue().doubleValue());
                            this.atmAllocation = this.atmAllocations.stream().filter((aa)
                                    -> aa.getDenominationId() == deno.getDenominationId()).collect(Collectors.toList()).get(0);
                            this.atmAllocation.setCount(this.atmAllocation.getCount() - count);
                        }
                    }
                  
                    List<String> summary = new ArrayList<>();
                    for (int k = 0; k < counts.length; k++) {
                        if (counts[k] != 0) {
                            summary.add(denos.get(k).getValue() + " * " + counts[k] + " = " + (denos.get(k).getValue().doubleValue() * counts[k]) + "\n");
                            bigDecimal += counts[k] * denos.get(k).getValue().doubleValue();
                        }
                    }
                    if (clientAccount.getAccountTypeCode().equalsIgnoreCase("CCRD")) {
                        if (transaction.getAmount() > creditCardLimit.getAccountLimit().doubleValue()) {

                            return "Exit Limit Try a Lesser Amount";
                        } else {
                            try {
                                double balance = clientAccount.getDisplayBalance().doubleValue() - bigDecimal;

                                clientAccount.setDisplayBalance(BigDecimal.valueOf(balance));

                                this.transaction = transaction;

                                em.persist(atmAllocation);
                                em.persist(clientAccount);
                                em.persist(this.transaction);

                                em.getTransaction().commit();

                                // System.err.println("Successfull");
                                return summary.toString();

                            } catch (Exception e) {
                                em.getTransaction().rollback();
                            }
                        }

                    } else if (clientAccount.getAccountTypeCode().equalsIgnoreCase("CHQ")) {

                        try {
                            if (transaction.getAmount() > 10000) {

                                return "Insufficient Fund";

                            } else {

                                double balance = clientAccount.getDisplayBalance().doubleValue() - bigDecimal;

                                clientAccount.setDisplayBalance(BigDecimal.valueOf(balance));

                                this.transaction = transaction;

                                em.persist(clientAccount);
                                em.persist(atmAllocation);
                                em.persist(this.transaction);

                                em.getTransaction().commit();

                                System.err.println("Successfull");

                                return summary.toString();  // to See the Changes

                            }
                        } catch (Exception e) {
                            em.getTransaction().rollback();
                        }
                    } else if (clientAccount.getAccountTypeCode().equalsIgnoreCase("SVGS")) {

                        if (clientAccount.getDisplayBalance().doubleValue() < transaction.getAmount()) {

                            return "Insufficient Fund";

                        } else {

                            double balance = clientAccount.getDisplayBalance().doubleValue() - bigDecimal;

                            clientAccount.setDisplayBalance(BigDecimal.valueOf(balance));

                            this.transaction = transaction;

                            em.persist(this.clientAccount);
                            em.persist(atmAllocation);
                            em.persist(this.transaction);

                            em.getTransaction().commit();

                            System.err.println("Successfull");
                            return summary.toString();
                        }
                    }
                }

            } else {
                return "Account Number does't Exist";
            }

        }// End Of If Statement To Check Valid Amount

        return null;

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/display")
    public List<ClientAccount> displayAccounts(@RequestHeader long idNumber) {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();
        List<ClientAccount> customerAccounts = new ArrayList<>();
        List<ClientAccount> accounts = (List<ClientAccount>) em.createQuery("SELECT ca FROM ClientAccount ca").getResultList();

        customerAccounts.sort((ca1, ca2) -> ca2.getDisplayBalance().compareTo(ca1.getDisplayBalance()));

        return customerAccounts;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("highestAccountBalancePerClient")
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

    @CrossOrigin(origins = "*")
    @GetMapping("highestAccountBalance")
    public List<ClientAccount> highBalance() {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Client> clients = em.createQuery("SELECT cl FROM Client cl").getResultList();
        List<ClientAccount> clientAccounts = em.createQuery("SELECT ca FROM ClientAccount ca").getResultList();
        List<ClientAccount> highestAccount = new ArrayList<>();
        List<ClientAccount> perAccount = new ArrayList<>();

        for (int i = 0; i < clientAccounts.size(); i++) {
            if (clientAccounts.get(i).getClientId() == clients.get(i).getClientId()) {
                perAccount.add(clientAccounts.get(i));
            }
        }
        perAccount.sort((ca1, ca2) -> ca2.getDisplayBalance().compareTo(ca1.getDisplayBalance()));
        highestAccount.add(perAccount.get(0));

        return highestAccount;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("balanceInRandValue")
    public String balanceInRandValue(@RequestHeader long clientAccountNumber) {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<BigDecimal> rate = new ArrayList<>();
        List<ClientAccount> clientAccounts = new ArrayList<>();

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
                currencyConversionRate = em.find(CurrencyConversionRate.class, cl.getCurrencyCode());

                rate.add(currencyConversionRate.getRate());
            }
        }

        return rate + " : " + " \n";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("financialPositionPerClient")
    public List<String> financialPositionPerClient() {
        emf = Persistence.createEntityManagerFactory(jpaName);
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<String> listOfFinancialPositionPerClient = new ArrayList<>();
        double aggregateLoan = 0;
        double aggregateBalance = 0;
        double totalAggregate = 0;

        List<Client> clientList = em.createQuery("SELECT c FROM Client c").getResultList();

        for (Client clientList1 : clientList) {
            aggregateLoan = 0;
            aggregateBalance = 0;
            List<ClientAccount> clientAccounts = em.createQuery("SELECT a FROM ClientAccount a where a.clientId =" + clientList1.getClientId()).getResultList();
            for (ClientAccount clientAccount1 : clientAccounts) {
                if (clientAccount1.getDisplayBalance().doubleValue() <= 0) {
                    aggregateLoan = aggregateLoan + clientAccount1.getDisplayBalance().doubleValue();
                } else {
                    aggregateBalance = aggregateBalance + clientAccount1.getDisplayBalance().doubleValue();
                }
            }
            totalAggregate = aggregateBalance + aggregateLoan;
            listOfFinancialPositionPerClient.add(clientList1.getClientId() + " : " + clientList1.getTitle() + " ; " + " : " + clientList1.getName() + " : " + clientList1.getSurname() + " : " + aggregateBalance + " : " + aggregateLoan + " : " + totalAggregate);
        }

        return listOfFinancialPositionPerClient;
    }
}
