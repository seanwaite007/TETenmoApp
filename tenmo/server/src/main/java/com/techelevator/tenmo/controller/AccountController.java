package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getAccountByUsername (Principal principal)
    throws UsernameNotFoundException {

        return accountDao.getAccountByUsername(principal.getName());
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.FOUND)
   @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (Principal principal) {

        return accountDao.getBalanceByUserName(principal.getName());
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
   @RequestMapping(path = "/account/balance", method = RequestMethod.POST)
   public Transfer updateAccount (@RequestBody Transfer transfer, Principal principal)
   {
       //prinicpal is the logged in users info, transfer is requesting from postman and we fill in from there

       //Account From/Principal
       String sender = principal.getName();
       accountDao.getAccountByUsername(sender);
       transfer.setAccount_from(accountDao.getAccountByUsername(sender).getAccountId());
       transfer.setAccountFromUsername(sender);

       //Account Sent/Transfer
       Account receivedAccount = accountDao.getAccountByUsername(transfer.getAccountToUsername());
       transfer.setAccount_To(receivedAccount.getAccountId());
       transfer.setAccountToUsername(transfer.getAccountToUsername());

       if(accountDao.updateAccount(sender, transfer.getAccountToUsername(), transfer.getTransferAmount())){
           transfer.setTransferId(transferDao.createTransfer(transfer).getTransferId());

       }else if(!accountDao.updateAccount(sender, transfer.getAccountToUsername(), transfer.getTransferAmount())){
           transfer.setTransferId(transferDao.createTransfer(transfer).getTransferId());
        }

       return transfer;
   }

}


