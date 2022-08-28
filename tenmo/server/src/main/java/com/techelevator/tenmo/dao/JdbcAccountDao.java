package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.security.UpdateException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {


        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Account getAccountByUsername(String username) {

        String sql = "SELECT account.user_id, account_id, balance FROM account" +
                " JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                " WHERE username = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public BigDecimal getBalanceByUserName(String username) throws NullPointerException {

        Account account = null;

        String sql = "SELECT * FROM account " +
                " JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                " WHERE username = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()) {
            account = mapRowToAccount(rowSet);
        }
        return account.getBalance();
    }

    @Override
    public boolean updateAccount(String userName, String userName2, BigDecimal amountToTransfer)
            throws UsernameNotFoundException {
        boolean success = false;

        Account fromAccount = getAccountByUsername(userName);
        Account toAccount = getAccountByUsername(userName2);

        if (fromAccount.getBalance().compareTo(amountToTransfer) < 0)
            throw new UpdateException("Sorry you don't enough TE Bucks");
        if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0)
            throw new UpdateException("Sorry you can't send 0 TE Bucks");
        if (fromAccount.equals(toAccount))
            throw new UpdateException("Duplicate account error");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amountToTransfer));

        toAccount.setBalance(toAccount.getBalance().add(amountToTransfer));

        String sql = "UPDATE account" +
                " SET balance = ?" +
                " WHERE account_id = ?; ";

        String sql2 = "UPDATE account" +
                " SET balance = ?" +
                " WHERE account_id = ?; ";

        jdbcTemplate.update(sql, fromAccount.getBalance(), fromAccount.getAccountId());
        jdbcTemplate.update(sql2, toAccount.getBalance(), toAccount.getAccountId());
        success = true;

        return success;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
