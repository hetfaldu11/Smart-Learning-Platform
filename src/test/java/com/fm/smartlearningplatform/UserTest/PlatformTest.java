package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.service.user.PlatformService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlatformTest {

    private final PlatformService platformService;
    private final JdbcTemplate jdbcTemplate;
    Platform platform1;
    Platform platform2;


    @Autowired
    public PlatformTest(PlatformService platformService, JdbcTemplate jdbcTemplate)
    {
        this.platformService= platformService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.platform1= platformService.createPlatform("platform1");
        this.platform2= platformService.createPlatform("platform2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE platforms RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createPlatform()
    {
        Long id = platform1.getId();
        assertNotNull(platformService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->platformService.createPlatform(null));

        assertThrows(RuntimeException.class,()->platformService.createPlatform("platform1"));

        assertEquals(platform1.getName(),platformService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findPlatform()
    {
        Long id1 = platform1.getId();
        Long id2 = platform2.getId();

        assertTrue(platformService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(platformService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(platform1,platformService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(platform1,platformService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(platformService.existsByNameAndDeletedAtIsNull(("platform1")));

        assertFalse(platformService.existsByNameAndDeletedAtIsNull("platform3"));

        assertEquals(platform1,platformService.findByNameAndDeletedAtIsNull("platform1"));

        assertNotEquals(platform1,platformService.findByNameAndDeletedAtIsNull("platform2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updatePlatform() {
        Long id = platform1.getId();

        assertThrows(RuntimeException.class,()->platformService.updatePlatform(id,null));

        assertThrows(RuntimeException.class,()->platformService.updatePlatform(id,"platform2"));

        assertDoesNotThrow(()->platformService.updatePlatform(id,"platform3"));

        assertEquals("platform3",platformService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deletePlatform(){

        Long id = platform1.getId();

        assertEquals(platform1,platformService.findByIdAndDeletedAtIsNull(id));

        platformService.deleteById(id);

        assertThrows(RuntimeException.class,() -> platformService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> platformService.findByIdAndDeletedAtIsNull(id));
    }
}

