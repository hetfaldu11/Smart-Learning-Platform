package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.*;
import com.fm.smartlearningplatform.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MainUserTest {

    private final UserService userService;

    private final UserProfileService userProfileService;

    private final UserAuthorizationService userAuthorizationService;

    private final UserPreferenceService userPreferenceService;

    private final UserVerificationService userVerificationService;

    private final UserSocialLinkService userSocialLinkService;

    private final SkillService skillService;

    private final LanguageService languageService;

    private final InterestService interestService;

    private final ProfessionService professionService;

    @Autowired
    public MainUserTest(
            UserService userService,
            UserProfileService userProfileService,
            UserAuthorizationService userAuthorizationService,
            UserPreferenceService userPreferenceService,
            UserVerificationService userVerificationService,
            UserSocialLinkService userSocialLinkService,
            SkillService skillService,
            LanguageService languageService,
            InterestService interestService,
            ProfessionService professionService
    ) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.userAuthorizationService = userAuthorizationService;
        this.userPreferenceService = userPreferenceService;
        this.userVerificationService = userVerificationService;
        this.userSocialLinkService = userSocialLinkService;
        this.skillService = skillService;
        this.languageService = languageService;
        this.interestService = interestService;
        this.professionService = professionService;
    }

    private User user1;

    @BeforeEach
    public void beforeEach() {

        // =========================
        // USER
        // =========================

        User user = User.builder()
                .email(System.currentTimeMillis() + "@gmail.com")
                .passwordHash("password")
                .build();

        userService.saveUser(user);

        // =========================
        // PROFESSION
        // =========================

        Profession profession = Profession.builder()
                .name("Student" + System.currentTimeMillis())
                .build();

        professionService.save(profession);

        // =========================
        // USER PROFILE
        // =========================

        Address homeAddress = Address.builder()
                .state("Gujarat")
                .build();

        UserProfile userProfile = UserProfile.builder()
                .firstName("Het")
                .lastName("Faldu")
                .phoneNumber("7016960254" + System.currentTimeMillis())
                .homeAddress(homeAddress)
                .educationLevel(EducationLevel.BACHELOR)
                .gender(Gender.MALE)
                .profession(profession)
                .user(user)
                .build();

        user.setUserProfile(userProfile);

        userProfileService.save(userProfile);

        // =========================
        // USER ROLES
        // =========================

        user.addRole(UserRole.STUDENT);
        user.addRole(UserRole.INSTRUCTOR);

        // =========================
        // SKILLS
        // =========================

        Skill skill1 = Skill.builder()
                .name("Java" + System.currentTimeMillis())
                .build();

        Skill skill2 = Skill.builder()
                .name("Spring" + System.currentTimeMillis())
                .build();

        skillService.save(skill1);
        skillService.save(skill2);

        user.addSkill(skill1);
        user.addSkill(skill2);

        // =========================
        // INTERESTS
        // =========================

        Interest interest1 = Interest.builder()
                .name("AI" + System.currentTimeMillis())
                .build();

        Interest interest2 = Interest.builder()
                .name("ML" + System.currentTimeMillis())
                .build();

        interestService.save(interest1);
        interestService.save(interest2);

        user.addInterest(interest1);
        user.addInterest(interest2);

        // =========================
        // SOCIAL LINKS
        // =========================

        UserSocialLink github = UserSocialLink.builder()
                .platform(Platform.GITHUB)
                .url("github.com")
                .user(user)
                .build();

        UserSocialLink instagram = UserSocialLink.builder()
                .platform(Platform.INSTAGRAM)
                .url("instagram.com")
                .user(user)
                .build();

        user.addLink(github);
        user.addLink(instagram);

        userSocialLinkService.save(github);
        userSocialLinkService.save(instagram);

        // =========================
        // VERIFICATION
        // =========================

        UserVerification verification = UserVerification.builder()
                .twoFactorEnabled(true)
                .user(user)
                .build();

        user.setUserVerification(verification);

        userVerificationService.save(verification);

        // =========================
        // LANGUAGES
        // =========================

        Language language1 = Language.builder()
                .name("Hindi")
                .code("hn")
                .build();

        Language language2 = Language.builder()
                .name("English")
                .code("en")
                .build();

        languageService.save(language1);
        languageService.save(language2);

        // =========================
        // USER PREFERENCE
        // =========================

        UserPreference preference = UserPreference.builder()
                .theme(Theme.DARK)
                .language(language1)
                .user(user)
                .build();

        user.setUserPreference(preference);

        userPreferenceService.save(preference);

        // =========================
        // FINAL SAVE
        // =========================

        userService.saveUser(user);

        this.user1 = user;
    }

    @Test
    public void createUser() {

        Long id = user1.getId();

        User user = userService.findById(id);

        assertNotNull(user);

//        assertNotNull(user.getUserProfile());
//
//        assertNotNull(user.getSkills());
//
//        assertNotNull(user.getInterests());
//
//        assertNotNull(user.getUserPreference());
//
//        assertNotNull(user.getUserVerification());
    }
}