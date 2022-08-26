package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.loading.PrivateClassLoader;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private UserDao userDao;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getAccountByUsername (Principal principal)
    throws UsernameNotFoundException {

        return accountDao.getAccountByUsername(principal.getName());
    }

    @PreAuthorize("hasRole('USER')")
   @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (Principal principal) {

        return accountDao.getBalanceByUserName(principal.getName());
    }


    @PreAuthorize("hasRole('USER')")
   @RequestMapping(path = "/account/balance", method = RequestMethod.POST)

   public Transfer updateAccount (@RequestBody Transfer transfer,
                                 Principal principal)
   {
       //principal is going to give us the senders name
       String sender = principal.getName();
       accountDao.getAccountByUsername(sender);

       //this actually provides us with the account of the recipient
       Account receivedAccount = accountDao.getAccountByUsername(transfer.getAccountToUsername());
       transfer.setAccount_To(receivedAccount.getAccountId());
       transfer.setAccount_from(accountDao.getAccountByUsername(sender).getAccountId());

       // Set account from to logged in user
       transfer.setAccountFromUsername(sender);
       transfer.setAccountToUsername(transfer.getAccountToUsername());

       //So when this happens we update both accounts based on transferAMOUNT

       if(accountDao.updateAccount(sender, transfer.getAccountToUsername(), transfer.getTransferAmount())){
           transfer.setTransferId(transferDao.createTransfer(transfer).getTransferId());
       }
       return transfer;
   }

}


