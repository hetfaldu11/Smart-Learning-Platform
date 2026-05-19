package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Gender;
import com.fm.smartlearningplatform.service.user.GenderService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GenderTest {

    private final GenderService genderService;
    private final JdbcTemplate jdbcTemplate;
    Gender gender1;
    Gender gender2;


    @Autowired
    public GenderTest(GenderService genderService, JdbcTemplate jdbcTemplate)
    {
        this.genderService= genderService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.gender1= genderService.createGender("gender1");
        this.gender2= genderService.createGender("gender2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE genders RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createGender()
    {
        Long id = gender1.getId();
        assertNotNull(genderService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->genderService.createGender(null));

        assertThrows(RuntimeException.class,()->genderService.createGender("gender1"));

        assertEquals(gender1.getName(),genderService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findGender()
    {
        Long id1 = gender1.getId();
        Long id2 = gender2.getId();

        assertTrue(genderService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(genderService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(gender1,genderService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(gender1,genderService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(genderService.existsByNameAndDeletedAtIsNull(("gender1")));

        assertFalse(genderService.existsByNameAndDeletedAtIsNull("gender3"));

        assertEquals(gender1,genderService.findByNameAndDeletedAtIsNull("gender1"));

        assertNotEquals(gender1,genderService.findByNameAndDeletedAtIsNull("gender2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updateGender() {
        Long id = gender1.getId();

        assertThrows(RuntimeException.class,()->genderService.updateGender(id,null));

        assertThrows(RuntimeException.class,()->genderService.updateGender(id,"gender2"));

        assertDoesNotThrow(()->genderService.updateGender(id,"gender3"));

        assertEquals("gender3",genderService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deleteGender(){

        Long id = gender1.getId();

        assertEquals(gender1,genderService.findByIdAndDeletedAtIsNull(id));

        genderService.deleteById(id);

        assertThrows(RuntimeException.class,() -> genderService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> genderService.findByIdAndDeletedAtIsNull(id));
    }
}

