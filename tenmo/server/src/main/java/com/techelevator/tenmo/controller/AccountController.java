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
   @RequestMapping(path = "/account/balance", method = RequestMethod.POST)

   public Transfer updateAccount (@RequestBody Transfer transfer,
                                 @RequestParam BigDecimal transferAmount,
                                 Principal principal)
   {
       //principal is going to give us the senders name
       String sender = principal.getName();
       accountDao.getAccountByUsername(sender);

       //this gives us the transfer count ID
       int transferAccount = transfer.getAccount_To();

       //this gives us our recieving account username
       String recipient = userDao.getUserNameByAccountId(transferAccount);

       //this actually provides us with the account of the recipient
       Account receivedAccount = accountDao.getAccountByUsername(recipient);

       //So when this happens we update both accounts based on transferAMOUNT

       Transfer newTransfer = new Transfer();
       if(accountDao.updateAccount(sender, recipient, transferAmount)){
           newTransfer =  transferDao.createTransfer(transfer);
       }
       return newTransfer;

   }

}
