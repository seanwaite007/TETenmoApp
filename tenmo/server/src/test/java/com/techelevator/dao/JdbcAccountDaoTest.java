package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JdbcAccountDaoTest extends BaseDaoTests{


    private Account testAccount;

    private JdbcAccountDao sut;

    private User testUser;

    private User testUser2;


    private static final Account ACCOUNT_1 = new Account(2001,
            1001, BigDecimal.valueOf(1000));
    private static final Account ACCOUNT_2 = new Account(2002,
            1002, BigDecimal.valueOf(1000));


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserDao userDao = new JdbcUserDao(jdbcTemplate);
        userDao.create("testUser", "123");
        userDao.create("testUser2", "1234");
        testUser = userDao.findByUsername("testUser");
        testUser2 = userDao.findByUsername("testUser2");

        sut = new JdbcAccountDao(jdbcTemplate);
        testAccount = new Account(0, 0, BigDecimal.valueOf(1000));
    }

    @Test
    public void updateAccountTestSuccess() {
        // Arrange
        sut.updateAccount(testUser.getUsername(),
                testUser2.getUsername(), new BigDecimal("10"));

        BigDecimal fromValue = sut.getBalanceByUserName(testUser.getUsername());
        BigDecimal expectedValue = new BigDecimal("990.00");

        BigDecimal toAccount = sut.getBalanceByUserName(testUser2.getUsername());
        BigDecimal expectedValue2 = new BigDecimal("1010.00");

        Assert.assertEquals(fromValue, expectedValue);
        Assert.assertEquals(toAccount, expectedValue2);
    }

    @Test
    public void getAllAccountsCheckSize() {
        int actualListSize = sut.getAllAccounts().size();
        int expectedSize = 2;

        Assert.assertEquals(actualListSize, expectedSize);
    }

    @Test
    public void getBalanceByUsername() {
        BigDecimal testBalance = sut.getBalanceByUserName(testUser.getUsername());
        BigDecimal actualBalance = new BigDecimal("1000.00");

        BigDecimal testBalance2 = sut.getBalanceByUserName(testUser2.getUsername());
        BigDecimal actualBalance2 = new BigDecimal("1000.00");

        Assert.assertEquals(testBalance, actualBalance);
        Assert.assertEquals(testBalance2, actualBalance2);
    }

    @Test
    public void getAccountByUsername() {
        Account testAccount = sut.getAccountByUsername(testUser.getUsername());
        Account actualAccount = ;

        Assert.assertEquals(testAccount, );

    }


    // Ask about testing when throwing an exception
    @Test
    public void updateAccountTestFailNotEnoughFunds() {
        // Arrange
        sut.updateAccount(testUser.getUsername(),
                testUser2.getUsername(), new BigDecimal("10000"));

        BigDecimal fromValue = sut.getBalanceByUserName(testUser.getUsername());
        BigDecimal expectedValue = new BigDecimal("990.00");

        BigDecimal toAccount = sut.getBalanceByUserName(testUser2.getUsername());
        BigDecimal expectedValue2 = new BigDecimal("1010.00");

        Assert.assertEquals(fromValue, expectedValue);
        Assert.assertEquals(toAccount, expectedValue2);
    }


}