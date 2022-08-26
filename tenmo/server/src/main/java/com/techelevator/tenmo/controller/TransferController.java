package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
public class TransferController {

    @Autowired
    private TransferDao transferDao;

//    // List of transfers
//    @RequestMapping(path = "/account/transfer", method = RequestMethod.GET)
//    public List<Transfer> getAllTransfers(){
//        return transferDao.getAllTransfers();
//    }

    // get transfer by transferId
    @RequestMapping(path = "/account/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferById(transferId);
    }

    @RequestMapping(path = "/account/transfer", method = RequestMethod.GET)
    public List<Transfer> getListOfTransfersByAccount(Principal principal) {

        return transferDao.getTransfersByAccount(principal.getName());
    }

}
