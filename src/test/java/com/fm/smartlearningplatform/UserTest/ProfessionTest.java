package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.service.user.ProfessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProfessionTest {

    @Autowired
    private ProfessionService professionService;

    private Profession profession;

    @BeforeEach
    public void beforeEach(){

        profession = new Profession();

        profession.setName("Student" + System.currentTimeMillis());

        professionService.save(profession);
    }

    @Test
    public void createProfession(){
        Profession profession = professionService.findById(this.profession.getId());
        assertNotNull(profession.getId());
        assertEquals(this.profession.getName(),profession.getName());
    }

    @Test
    public void readProfession(){
        Profession foundProfession = professionService.findById(profession.getId());
        assertNotNull(foundProfession);
        assertEquals(profession.getId(), foundProfession.getId());
        assertEquals(profession.getName(), foundProfession.getName());
    }

    @Test
    public void updateProfession(){

        Profession foundProfession = professionService.findById(profession.getId());

        foundProfession.setName("Engineer" + System.currentTimeMillis());

        professionService.save(foundProfession);

        Profession updatedProfession = professionService.findById(profession.getId());

        assertEquals(foundProfession.getName(), updatedProfession.getName());
    }

    @Test
    public void deleteProfession(){

        Long professionId = profession.getId();
        professionService.deleteById(professionId);
        Profession deletedProfession = professionService.findById(professionId);
        assertNull(deletedProfession);
    }
}