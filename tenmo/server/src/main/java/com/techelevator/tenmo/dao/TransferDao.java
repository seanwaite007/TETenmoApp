package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    //TODO: CRUD operations for transfer




    List<Transfer> getAllTransfers();

    Transfer getTransferByAccount (String userName);

}
