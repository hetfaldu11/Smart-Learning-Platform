package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.Language;
import com.fm.smartlearningplatform.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LangaugeTest {

    @Autowired
    private LanguageService languageService;

    private Language language;

    @BeforeEach
    public void beforeEach(){

        language = new Language();

        language.setName("Hindi" + System.currentTimeMillis());
        language.setCode("hn" + System.currentTimeMillis());

        languageService.save(language);
    }

    @Test
    public void createLanguage(){
        Language language = languageService.findById(this.language.getId());
        assertNotNull(language.getId());
        assertEquals(this.language.getName(),language.getName());
    }

    @Test
    public void readLanguage(){
        Language foundLanguage = languageService.findById(language.getId());
        assertNotNull(foundLanguage);
        assertEquals(language.getId(), foundLanguage.getId());
        assertEquals(language.getName(), foundLanguage.getName());
    }

    @Test
    public void updateLanguage(){
        Language foundLanguage = languageService.findById(language.getId());
        foundLanguage.setName("English" + System.currentTimeMillis());
        languageService.save(foundLanguage);
        Language updatedLanguage = languageService.findById(language.getId());
        assertEquals(foundLanguage.getName(), updatedLanguage.getName());
    }

    @Test
    public void deleteLanguage(){

        Long languageId = language.getId();
        languageService.deleteById(languageId);
        Language deletedLanguage = languageService.findById(languageId);
        assertNull(deletedLanguage);
    }

}
