package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;
    private User testUser;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
        testUser = new User(0, "TestUsername", "TestPassword", "USER");

    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void findIdByUsername(){
        int actualId = sut.findIdByUsername("bob");
        int expectedId = 1001;

        Assert.assertEquals(actualId, expectedId);
    }


    @Test(expected = UsernameNotFoundException.class)
    public void findIdByUsernameNameNotFound(){
        sut.findByUsername("john");
    }

    @Test
    public void listAllUsers(){
       int actualListSize =  sut.findAll().size();
       int expectedListSize = 2;

        Assert.assertEquals(actualListSize, expectedListSize);
    }

    @Test
    public void findByUsername(){
        String testUser = String.valueOf(sut.findByUsername("bob"));

        String expected = "User{id=1001, username='bob', activated=true, authorities=[Authority{name=ROLE_USER}]}";

        Assert.assertEquals(testUser,expected);

    }
    @Test(expected = UsernameNotFoundException.class)
    public void findByUsernameNameNotFound(){
        sut.findByUsername("john");
    }

}
