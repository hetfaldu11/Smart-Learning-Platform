package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.service.user.LanguageService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LanguageTest {

    private final LanguageService languageService;
    private final JdbcTemplate jdbcTemplate;
    Language language1;
    Language language2;


    @Autowired
    public LanguageTest(LanguageService languageService, JdbcTemplate jdbcTemplate)
    {
        this.languageService= languageService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.language1= languageService.createLanguage("language1", "code1");
        this.language2= languageService.createLanguage("language2", "code2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE languages RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createLanguage()
    {
        Long id = language1.getId();
        assertNotNull(languageService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->languageService.createLanguage(null, "code1"));

        assertThrows(RuntimeException.class,()->languageService.createLanguage("language4", null));

        assertThrows(RuntimeException.class,()->languageService.createLanguage("language1","code2"));

        assertEquals(language1.getName(),languageService.findByIdAndDeletedAtIsNull(id).getName());

        assertEquals(language1.getCode(),languageService.findByIdAndDeletedAtIsNull(id).getCode());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findLanguage()
    {
        Long id1 = language1.getId();
        Long id2 = language2.getId();

        assertTrue(languageService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(languageService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(language1,languageService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(language1,languageService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(languageService.existsByNameAndDeletedAtIsNull(("language1")));

        assertFalse(languageService.existsByNameAndDeletedAtIsNull("language3"));

        assertEquals(language1,languageService.findByNameAndDeletedAtIsNull("language1"));

        assertNotEquals(language1,languageService.findByNameAndDeletedAtIsNull("language2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updateLanguage() {
        Long id = language1.getId();

        assertThrows(RuntimeException.class,()->languageService.updateLanguageName(id,null));

        assertThrows(RuntimeException.class,()->languageService.updateLanguageName(id,"language2"));

        assertDoesNotThrow(()->languageService.updateLanguageName(id,"language3"));

        assertEquals("language3",languageService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deleteLanguage(){

        Long id = language1.getId();

        assertEquals(language1,languageService.findByIdAndDeletedAtIsNull(id));

        languageService.deleteById(id);

        assertThrows(RuntimeException.class,() -> languageService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> languageService.findByIdAndDeletedAtIsNull(id));
    }
}

