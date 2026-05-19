package com.fm.smartlearningplatform.UserTest;


import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.service.user.RoleService;
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
public class RoleTest {
    private final RoleService roleService;
    private final JdbcTemplate jdbcTemplate;
    Role role1;
    Role role2;


    @Autowired
    public RoleTest(RoleService roleService, JdbcTemplate jdbcTemplate)
    {
        this.roleService= roleService;
        this.jdbcTemplate = jdbcTemplate;
    }


    @BeforeEach
    void beforeEach(){
        this.role1= roleService.createRole("role1");
        this.role2= roleService.createRole("role2");
    }


    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("TRUNCATE TABLE roles RESTART IDENTITY CASCADE;");
    }


    // ─── Create ──────────────────────────────────────────────────────


    @Test
    @Order(0)
    public void createRole()
    {
        Long id = role1.getId();
        assertNotNull(roleService.findByIdAndDeletedAtIsNull(id));

        assertThrows(RuntimeException.class,()->roleService.createRole(null));

        assertThrows(RuntimeException.class,()->roleService.createRole("role1"));

        assertEquals(role1.getName(),roleService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Find ──────────────────────────────────────────────────────

    @Test
    @Order(1)
    public void findRole()
    {
        Long id1 = role1.getId();
        Long id2 = role2.getId();

        assertTrue(roleService.existsByIdAndDeletedAtIsNull(id1));

        assertFalse(roleService.existsByIdAndDeletedAtIsNull(4L));

        assertEquals(role1,roleService.findByIdAndDeletedAtIsNull(id1));

        assertNotEquals(role1,roleService.findByIdAndDeletedAtIsNull(id2));

        assertTrue(roleService.existsByNameAndDeletedAtIsNull(("role1")));

        assertFalse(roleService.existsByNameAndDeletedAtIsNull("role3"));

        assertEquals(role1,roleService.findByNameAndDeletedAtIsNull("role1"));

        assertNotEquals(role1,roleService.findByNameAndDeletedAtIsNull("role2"));
    }

    // ─── Update ──────────────────────────────────────────────────────

    @Test
    @Order(2)
    public void updateRole() {
        Long id = role1.getId();

        assertThrows(RuntimeException.class,()->roleService.updateRole(id,null));

        assertThrows(RuntimeException.class,()->roleService.updateRole(id,"role2"));

        assertDoesNotThrow(()->roleService.updateRole(id,"role3"));

        assertEquals("role3",roleService.findByIdAndDeletedAtIsNull(id).getName());
    }

    // ─── Delete ──────────────────────────────────────────────────────

    @Test
    @Order(3)
    public void deleteRole(){

        Long id = role1.getId();

        assertEquals(role1,roleService.findByIdAndDeletedAtIsNull(id));

        roleService.deleteById(id);

        assertThrows(RuntimeException.class,() -> roleService.deleteById(4L));

        assertThrows(RuntimeException.class,() -> roleService.findByIdAndDeletedAtIsNull(id));
    }
}

