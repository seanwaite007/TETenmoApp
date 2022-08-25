package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.management.loading.PrivateClassLoader;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private UserDao userDao;

    // remove constructor due to @Autowired ??
//    public AccountController( AccountDao accountDao) {
//
//        this.accountDao = accountDao;
//
//    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getAccountByUsername (@PathVariable String userName)
    throws UsernameNotFoundException {

        return accountDao.getAccountByUsername(userName);
    }

    // get balance by userName;
   @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (Principal principal) {

        return accountDao.getBalanceByUserName(principal.getName());
    }


    // update account
    @RequestMapping(path = "/account/balance", method = RequestMethod.PUT)
    public boolean updateAccount (@RequestBody Transfer transfer,
                                  @RequestParam BigDecimal transferAmount,
                                  Principal principal) {

        String sender = principal.getName();
        int transferAccount = transfer.getAccount_To();
        String recipient = userDao.getUserNameByAccountId(transferAccount);

        accountDao.getAccountByUsername(sender);
        Account receivedAccount = accountDao.getAccountByUsername(recipient);

        accountDao.updateAccount(sender, recipient, transferAmount);

        return accountDao.updateAccount(sender, recipient, transferAmount);
    }

}
