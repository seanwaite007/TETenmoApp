package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.UpdateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.relational.core.sql.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JdbcAccountDaoTest extends BaseDaoTests{


    private Account testAccount;

    private JdbcAccountDao sut;

    private User testUser;

    private User testUser2;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        UserDao userDao = new JdbcUserDao(jdbcTemplate);
        userDao.create("testUser", "123");
        userDao.create("testUser2", "1234");
        testUser = userDao.findByUsername("testUser");
        testUser2 = userDao.findByUsername("testUser2");

        sut = new JdbcAccountDao(jdbcTemplate);
    }

    //HappyPathCases
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


    //GetBalance HappyPath
    @Test
    public void getBalanceByUsername() {
        BigDecimal testBalance = sut.getBalanceByUserName(testUser.getUsername());
        BigDecimal actualBalance = new BigDecimal("1000.00");

        BigDecimal testBalance2 = sut.getBalanceByUserName(testUser2.getUsername());
        BigDecimal actualBalance2 = new BigDecimal("1000.00");

        Assert.assertEquals(testBalance, actualBalance);
        Assert.assertEquals(testBalance2, actualBalance2);
    }

    //GetBalance non-existent name
    @Test(expected = NullPointerException.class)
    public void getBalanceByUsernameNotFound(){
        sut.getBalanceByUserName("Invalid Name");
    }

    //Edge cases for update account
    @Test(expected = UpdateException.class)
    public void updateAccountTestFailNotEnoughTEBucks() {
        // Arrange
        sut.updateAccount(testUser.getUsername(), testUser2.getUsername(), new BigDecimal("10000"));
        //Throw

    }
    @Test(expected = UpdateException.class)
    public void updateAccountTestFailSameAccount() {
        // Arrange
        sut.updateAccount(testUser.getUsername(), testUser.getUsername(), new BigDecimal("100"));
        //Throw

    }
    @Test(expected = UpdateException.class)
    public void updateAccountTestFailZeroTransfer() {
        // Arrange
        sut.updateAccount(testUser.getUsername(), testUser2.getUsername(), new BigDecimal("0"));
        //Throw

    }

    @Test(expected = UsernameNotFoundException.class)
    public void updateAccountTestFailNullUser() {
        // Arrange
        sut.updateAccount(testUser.getUsername(), null, new BigDecimal("10000"));
        //Throw

    }

    @Test(expected = UsernameNotFoundException.class)
    public void getAccountByUsernameInvalidName(){
        sut.getAccountByUsername("InvalidName");
    }


    @Test
    public void getAccountByUsername() {
        Account testAccount = sut.getAccountByUsername(testUser.getUsername());

        Account actualAccount = sut.getAccountByUsername("testUser");

        assertAccountsMatch(testAccount, actualAccount);

    }

    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());

    }



}