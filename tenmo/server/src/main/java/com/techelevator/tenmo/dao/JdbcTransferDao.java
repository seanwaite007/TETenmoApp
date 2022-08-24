package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    @Override
    public boolean sendMoney(BigDecimal transferAmount) {
        boolean success = false;

        return success;
    }

    @Override
    public List<Transfer> getAllTransfers() {

        return null;
    }

    @Override
    public Transfer getTransferById(int transferId) {

        return null;
    }
}
