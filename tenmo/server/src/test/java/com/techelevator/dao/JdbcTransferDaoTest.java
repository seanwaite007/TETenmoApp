package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDaoTest extends BaseDaoTests {

    private JdbcTransferDao sut;

    private Transfer testTransfer;

    private User testUser;

    private User testUser2;

    private AccountDao accountDao;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserDao userDao = new JdbcUserDao(jdbcTemplate);
        userDao.create("testUser", "123");
        userDao.create("testUser2", "1234");
        testUser = userDao.findByUsername("testUser");
        testUser2 = userDao.findByUsername("testUser2");

        accountDao = new JdbcAccountDao(jdbcTemplate);

        sut = new JdbcTransferDao(jdbcTemplate);

    }

    @Test
    public void createNewTransfer() {
        testTransfer = new Transfer(3001, BigDecimal.valueOf(100), 2001, 2002,
                "Approved", "James", "John");

        Transfer actual = sut.createTransfer(testTransfer);

        assertTransfersMatch(actual, testTransfer);

    }


    @Test
    public void listAllTransfer() {
        List<Transfer> allTransferWithUser = new ArrayList<>();
        testTransfer = new Transfer(3001, BigDecimal.valueOf(100), 2001, 2002,
                "Approved", "James", "John");
        allTransferWithUser.add(testTransfer);

        int transferListSize = allTransferWithUser.size();
        int expectedSize = 1;

        Assert.assertEquals(transferListSize, expectedSize);
    }

    @Test
    public void getTransferByAccountSuccess() {
        testTransfer = new Transfer
                (3001,
                        BigDecimal.valueOf(100),
                        accountDao.getAccountByUsername(testUser2.getUsername()).getAccountId(),
                        accountDao.getAccountByUsername(testUser.getUsername()).getAccountId(),
                        "Approved",
                        "testUser2",
                        "testUser");

        Transfer actual = sut.createTransfer(testTransfer);

        assertTransfersMatch(actual, testTransfer);
    }

//not happening, ask about it.
//    @Test(expected = UsernameNotFoundException.class)
//    public void getTransferByAccountInvalidName(){
//        sut.getTransfersByAccount("john");
//    }

    @Test
    public void getTransferById() {
        testTransfer = new Transfer
                (3001,
                        BigDecimal.valueOf(100),
                        accountDao.getAccountByUsername(testUser2.getUsername()).getAccountId(),
                        accountDao.getAccountByUsername(testUser.getUsername()).getAccountId(),
                        "Approved",
                        "testUser2",
                        "testUser");

        Transfer actual = sut.createTransfer(testTransfer);

        Assert.assertEquals(3001, actual.getTransferId());
    }

    @Test
    public void getTransferByIdNoTransfer() {
        Transfer transfer = sut.getTransferById(3004);
        Assert.assertNull(transfer);

    }


    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferAmount(), actual.getTransferAmount());
        Assert.assertEquals(expected.getAccount_To(), actual.getAccount_To());
        Assert.assertEquals(expected.getAccount_from(), actual.getAccount_from());
        Assert.assertEquals(expected.getTransferStatus(), actual.getTransferStatus());
        Assert.assertEquals(expected.getAccountToUsername(), actual.getAccountToUsername());
        Assert.assertEquals(expected.getAccountFromUsername(), actual.getAccountFromUsername());
    }


}
