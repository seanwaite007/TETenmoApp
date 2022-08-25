package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {


        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO: CRUD METHODS JDBC

    //username, amountTransferred UPDATE 1 method


    @Override
    public Account getAccountByUsername(String username) {

        String sql = "SELECT user_id, account_id, balance FROM account" +
                     " JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                     " WHERE username = ? ";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if(rowSet.next()){
            return mapRowToAccount(rowSet);
        };
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public BigDecimal getBalanceByUserName(String username) {

            Account account = null;

            String sql = "SELECT * FROM account " +
                    " JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                    " WHERE username = ? ";

            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
            if(rowSet.next()) {
                account = mapRowToAccount(rowSet);
            }
            return account.getBalance();
        }



    //TODO: Method that will be called to update accounts, this is our one method, it will take in a user and the amount
    //TODO: THIS is how we update 2 at once, remember the catcards

    @Override
    public boolean updateAccount(String userName, BigDecimal amountToTransfer) {
        boolean success= false;

        Account updateAccount = getAccountByUsername(userName);

        updateAccount.setBalance(updateAccount.getBalance().add(amountToTransfer));

        String sql = "UPDATE account" +
                     " SET balance" +
                     " WHERE username = ?; ";

        if (getAccountByUsername(userName) != null) {
            jdbcTemplate.update(sql, updateAccount.getBalance(), userName);
            success = true;
        }
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
