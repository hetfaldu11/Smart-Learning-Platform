//package com.fm.smartlearningplatform.UserTest;
//
//import com.fm.smartlearningplatform.model.user.EducationLevel;
//import com.fm.smartlearningplatform.service.user.EducationLevelService;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class EducationLevelTest {
//
//    private final EducationLevelService educationLevelService;
//    private final JdbcTemplate jdbcTemplate;
//    EducationLevel educationLevel1;
//    EducationLevel educationLevel2;
//
//
//    @Autowired
//    public EducationLevelTest(EducationLevelService educationLevelService, JdbcTemplate jdbcTemplate)
//    {
//        this.educationLevelService= educationLevelService;
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    @BeforeEach
//    void beforeEach(){
//        this.educationLevel1= educationLevelService.createEducationLevel("educationLevel1");
//        this.educationLevel2= educationLevelService.createEducationLevel("educationLevel2");
//    }
//
//
//    @AfterEach
//    void afterEach() {
//        jdbcTemplate.execute("TRUNCATE TABLE education_levels RESTART IDENTITY CASCADE;");
//    }
//
//
//    // ─── Create ──────────────────────────────────────────────────────
//
//
//    @Test
//    @Order(0)
//    public void createEducationLevel()
//    {
//        Long id = educationLevel1.getId();
//        assertNotNull(educationLevelService.findByIdAndDeletedAtIsNull(id));
//
//        assertThrows(RuntimeException.class,()->educationLevelService.createEducationLevel(null));
//
//        assertThrows(RuntimeException.class,()->educationLevelService.createEducationLevel("educationLevel1"));
//
//        assertEquals(educationLevel1.getName(),educationLevelService.findByIdAndDeletedAtIsNull(id).getName());
//    }
//
//    // ─── Find ──────────────────────────────────────────────────────
//
//    @Test
//    @Order(1)
//    public void findEducationLevel()
//    {
//        Long id1 = educationLevel1.getId();
//        Long id2 = educationLevel2.getId();
//
//        assertTrue(educationLevelService.existsByIdAndDeletedAtIsNull(id1));
//
//        assertFalse(educationLevelService.existsByIdAndDeletedAtIsNull(4L));
//
//        assertEquals(educationLevel1,educationLevelService.findByIdAndDeletedAtIsNull(id1));
//
//        assertNotEquals(educationLevel1,educationLevelService.findByIdAndDeletedAtIsNull(id2));
//
//        assertTrue(educationLevelService.existsByNameAndDeletedAtIsNull(("educationLevel1")));
//
//        assertFalse(educationLevelService.existsByNameAndDeletedAtIsNull("educationLevel3"));
//
//        assertEquals(educationLevel1,educationLevelService.findByNameAndDeletedAtIsNull("educationLevel1"));
//
//        assertNotEquals(educationLevel1,educationLevelService.findByNameAndDeletedAtIsNull("educationLevel2"));
//    }
//
//    // ─── Update ──────────────────────────────────────────────────────
//
//    @Test
//    @Order(2)
//    public void updateEducationLevel() {
//        Long id = educationLevel1.getId();
//
//        assertThrows(RuntimeException.class,()->educationLevelService.updateEducationLevel(id,null));
//
//        assertThrows(RuntimeException.class,()->educationLevelService.updateEducationLevel(id,"educationLevel2"));
//
//        assertDoesNotThrow(()->educationLevelService.updateEducationLevel(id,"educationLevel3"));
//
//        assertEquals("educationLevel3",educationLevelService.findByIdAndDeletedAtIsNull(id).getName());
//    }
//
//    // ─── Delete ──────────────────────────────────────────────────────
//
//    @Test
//    @Order(3)
//    public void deleteEducationLevel(){
//
//        Long id = educationLevel1.getId();
//
//        assertEquals(educationLevel1,educationLevelService.findByIdAndDeletedAtIsNull(id));
//
//        educationLevelService.deleteById(id);
//
//        assertThrows(RuntimeException.class,() -> educationLevelService.deleteById(4L));
//
//        assertThrows(RuntimeException.class,() -> educationLevelService.findByIdAndDeletedAtIsNull(id));
//    }
//}
//
