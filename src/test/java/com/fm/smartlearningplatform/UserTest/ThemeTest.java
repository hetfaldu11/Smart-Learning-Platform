package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Theme;
import com.fm.smartlearningplatform.service.user.ThemeService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ThemeTest {

    private final ThemeService themeService;
    private final JdbcTemplate jdbcTemplate;
    Theme theme1;
    Theme theme2;


    @Autowired
    public ThemeTest(ThemeService themeService, JdbcTemplate jdbcTemplate)
    {
        this.themeService= themeService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.theme1= themeService.createTheme("theme1");
        this.theme2= themeService.createTheme("theme2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE themes RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createTheme()
    {
        Long id = theme1.getId();
        assertNotNull(themeService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->themeService.createTheme(null));

        assertThrows(RuntimeException.class,()->themeService.createTheme("theme1"));

        assertEquals(theme1.getName(),themeService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findTheme()
    {
        Long id1 = theme1.getId();
        Long id2 = theme2.getId();

        assertTrue(themeService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(themeService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(theme1,themeService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(theme1,themeService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(themeService.existsByNameAndDeletedAtIsNull(("theme1")));

        assertFalse(themeService.existsByNameAndDeletedAtIsNull("theme3"));

        assertEquals(theme1,themeService.findByNameAndDeletedAtIsNull("theme1"));

        assertNotEquals(theme1,themeService.findByNameAndDeletedAtIsNull("theme2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updateTheme() {
        Long id = theme1.getId();

        assertThrows(RuntimeException.class,()->themeService.updateTheme(id,null));

        assertThrows(RuntimeException.class,()->themeService.updateTheme(id,"theme2"));

        assertDoesNotThrow(()->themeService.updateTheme(id,"theme3"));

        assertEquals("theme3",themeService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deleteTheme(){

        Long id = theme1.getId();

        assertEquals(theme1,themeService.findByIdAndDeletedAtIsNull(id));

        themeService.deleteById(id);

        assertThrows(RuntimeException.class,() -> themeService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> themeService.findByIdAndDeletedAtIsNull(id));
    }
}

