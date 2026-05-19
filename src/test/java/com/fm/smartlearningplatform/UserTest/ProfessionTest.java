package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.service.user.ProfessionService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfessionTest {

    private final ProfessionService professionService;
    private final JdbcTemplate jdbcTemplate;
    Profession profession1;
    Profession profession2;


    @Autowired
    public ProfessionTest(ProfessionService professionService, JdbcTemplate jdbcTemplate)
    {
        this.professionService= professionService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.profession1= professionService.createProfession("profession1");
        this.profession2= professionService.createProfession("profession2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE professions RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createProfession()
    {
        Long id = profession1.getId();
        assertNotNull(professionService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->professionService.createProfession(null));

        assertThrows(RuntimeException.class,()->professionService.createProfession("profession1"));

        assertEquals(profession1.getName(),professionService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findProfession()
    {
        Long id1 = profession1.getId();
        Long id2 = profession2.getId();

        assertTrue(professionService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(professionService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(profession1,professionService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(profession1,professionService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(professionService.existsByNameAndDeletedAtIsNull(("profession1")));

        assertFalse(professionService.existsByNameAndDeletedAtIsNull("profession3"));

        assertEquals(profession1,professionService.findByNameAndDeletedAtIsNull("profession1"));

        assertNotEquals(profession1,professionService.findByNameAndDeletedAtIsNull("profession2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updateProfession() {
        Long id = profession1.getId();

        assertThrows(RuntimeException.class,()->professionService.updateProfession(id,null));

        assertThrows(RuntimeException.class,()->professionService.updateProfession(id,"profession2"));

        assertDoesNotThrow(()->professionService.updateProfession(id,"profession3"));

        assertEquals("profession3",professionService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deleteProfession(){

        Long id = profession1.getId();

        assertEquals(profession1,professionService.findByIdAndDeletedAtIsNull(id));

        professionService.deleteById(id);

        assertThrows(RuntimeException.class,() -> professionService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> professionService.findByIdAndDeletedAtIsNull(id));
    }
}

