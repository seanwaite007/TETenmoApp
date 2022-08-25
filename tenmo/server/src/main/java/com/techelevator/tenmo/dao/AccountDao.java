package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    //TODO: CRUD METHODS


    //Create is being handled in User, because we need it to make a default object and make sure a balance of 1000 is set


    //Read operations will be granted to authorized users and make sure that it is based on username not id

    Account getAccountByUsername(String userName) throws UsernameNotFoundException;

    BigDecimal getBalanceByUserName(String username);

    boolean updateAccount(String userName, String userName2, BigDecimal amountToTransfer);

    List<Account> getAllAccounts();




}