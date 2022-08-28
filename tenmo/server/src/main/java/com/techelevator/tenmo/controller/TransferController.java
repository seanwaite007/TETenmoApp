package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private UserDao userDao;

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/account/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferById(transferId);
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/account/transfer", method = RequestMethod.GET)
    public List<Transfer> getListOfTransfersByAccount(Principal principal) {
        try {
            return transferDao.getTransfersByAccount(principal.getName());
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getListOfUsers() {
        return userDao.findAll();
    }

}
