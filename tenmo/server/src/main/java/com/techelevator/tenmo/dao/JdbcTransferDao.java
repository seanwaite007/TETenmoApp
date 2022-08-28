package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql =
                "INSERT INTO transfer (amount_to_transfer, account_to, account_from, account_to_username, " +
                        "account_from_username, transfer_status) " +
                        "VALUES (?, ?, ?, ?, ?, ?) " +
                        "RETURNING transfer_id ;";

         Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                 transfer.getTransferAmount(),
                 transfer.getAccount_To(),
                 transfer.getAccount_from(),
                 transfer.getAccountToUsername(),
                 transfer.getAccountFromUsername(),
                 transfer.getTransferStatus());

         transfer.setTransferId(newId);

        return transfer;
    }

    @Override
    public List<Transfer> getTransfersByAccount(String userName) {
        List<Transfer> transfers = new ArrayList<Transfer>();

        try {
            String sql =
                    "SELECT transfer_id, amount_to_transfer, account_to, account_from, " +
                            "account_to_username, account_from_username, transfer_status " +
                            "FROM transfer " +
                            "JOIN account ON transfer.account_from = account.account_id " +
                            "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                            "WHERE username = ?;";

            String sql2 =
                    "SELECT transfer_id, amount_to_transfer, account_to, account_from, " +
                            "account_to_username, account_from_username, transfer_status " +
                            "FROM transfer " +
                            "JOIN account ON transfer.account_to = account.account_id " +
                            "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                            "WHERE username = ?;";

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
            SqlRowSet results2 = jdbcTemplate.queryForRowSet(sql2, userName);
            while (results.next() && results2.next()) {
                transfers.add(mapRowToTransfer(results));
                transfers.add(mapRowToTransfer(results2));
                }
            } catch(UsernameNotFoundException e){
            System.out.println("Invalid username!");;
        } return transfers;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql =
                "SELECT transfer_id, amount_to_transfer, account_to, account_from, account_to_username, account_from_username, transfer_status " +
                        "FROM transfer " +
                        "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if(results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }







    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferAmount(rowSet.getBigDecimal("amount_to_transfer"));
        transfer.setAccount_from(rowSet.getInt("account_from"));
        transfer.setAccount_To(rowSet.getInt("account_to"));
        transfer.setAccountToUsername(rowSet.getString("account_to_username"));
        transfer.setAccountFromUsername(rowSet.getString("account_from_username"));
        transfer.setTransferStatus(rowSet.getString("transferStatus"));
        return transfer;
    }

}
