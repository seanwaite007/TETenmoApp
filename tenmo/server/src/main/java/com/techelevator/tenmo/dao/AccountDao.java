package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    Account getAccountByUsername(String userName) throws UsernameNotFoundException;

    BigDecimal getBalanceByUserName(String username);

    boolean updateAccount(String userName, String userName2, BigDecimal amountToTransfer);


}