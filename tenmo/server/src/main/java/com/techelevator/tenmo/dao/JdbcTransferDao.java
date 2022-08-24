package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean sendMoney(Transfer transfer) {

        String sqlSelect =
                "SELECT amount_to_transfer" +
                        "FROM transfer" +
                        "WHERE transfer_id = ?;";

        String sql =
                "UPDATE account" +
                        "SET balance = ?" +
                        "WHERE account_to = ?;";
        String sql2 =
                "UPDATE account" +
                        "SET balance = ?" +
                        "WHERE account_from = ?;";


//        updateTransfer(transfer)
//        BigDecimal transferAmount = transfer.getAmount()


        return jdbcTemplate.update(sql, sql2, transfer.getTransferAmount()) == 1;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> transfers = new ArrayList<Transfer>();
        String sql =
                "SELECT transfer_id, amount_to_transfer, account_to, account_from " +
                        "FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        Transfer transfer = null;
        String sql =
                "SELECT transfer_id, amount_to_transfer, account_to, account_from " +
                        "FROM transfer" +
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
        transfer.setAccountId(rowSet.getInt("account_id"));
        transfer.setTransferAmount(rowSet.getBigDecimal("amount_to_transfer"));
        transfer.setAccount_from(rowSet.getInt("account_from"));
        transfer.setAccount_To(rowSet.getInt("account_to"));
        return transfer;
    }

}
