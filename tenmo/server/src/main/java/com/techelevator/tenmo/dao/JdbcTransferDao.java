package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public Transfer getTransferByAccount(String userName) {
        return null;
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
