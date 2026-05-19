package com.fm.smartlearningplatform.UserTest;


import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.service.user.InterestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class InterestTest {

    private final InterestService interestService;
    private final JdbcTemplate jdbcTemplate;
    Interest interest1;
    Interest interest2;


    @Autowired
    public InterestTest(InterestService interestService, JdbcTemplate jdbcTemplate)
    {
        this.interestService= interestService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.interest1= interestService.createInterest("interest1");
        this.interest2= interestService.createInterest("interest2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE interests RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createInterest()
    {
        Long id = interest1.getId();
        assertNotNull(interestService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->interestService.createInterest(null));

        assertThrows(RuntimeException.class,()->interestService.createInterest("interest1"));

        assertEquals(interest1.getName(),interestService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findInterest()
    {
        Long id1 = interest1.getId();
        Long id2 = interest2.getId();

        assertTrue(interestService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(interestService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(interest1,interestService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(interest1,interestService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(interestService.existsByNameAndDeletedAtIsNull(("interest1")));

        assertFalse(interestService.existsByNameAndDeletedAtIsNull("interest3"));

        assertEquals(interest1,interestService.findByNameAndDeletedAtIsNull("interest1"));

        assertNotEquals(interest1,interestService.findByNameAndDeletedAtIsNull("interest2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updateInterest() {
        Long id = interest1.getId();

        assertThrows(RuntimeException.class,()->interestService.updateInterest(id,null));

        assertThrows(RuntimeException.class,()->interestService.updateInterest(id,"interest2"));

        assertDoesNotThrow(()->interestService.updateInterest(id,"interest3"));

        assertEquals("interest3",interestService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deleteInterest(){

        Long id = interest1.getId();

        assertEquals(interest1,interestService.findByIdAndDeletedAtIsNull(id));

        interestService.deleteById(id);

        assertThrows(RuntimeException.class,() -> interestService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> interestService.findByIdAndDeletedAtIsNull(id));
    }
}

