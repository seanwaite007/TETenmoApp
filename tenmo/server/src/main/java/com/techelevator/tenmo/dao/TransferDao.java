package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    boolean sendMoney(BigDecimal transferAmount);

    List<Transfer> getAllTransfers();

    Transfer getTransferById (int transferId);

}
