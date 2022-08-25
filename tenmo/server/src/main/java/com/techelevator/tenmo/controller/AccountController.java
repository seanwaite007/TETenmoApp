package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.management.loading.PrivateClassLoader;
import java.math.BigDecimal;
import java.security.Principal;


@RestController
public class AccountController {

    private AccountDao accountDao;

    public AccountController( AccountDao accountDao) {

        this.accountDao = accountDao;

    }

    // get an account by userName;

   @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance (Principal principal) {

        return accountDao.getBalanceByUserName(principal.getName());

    }


}
