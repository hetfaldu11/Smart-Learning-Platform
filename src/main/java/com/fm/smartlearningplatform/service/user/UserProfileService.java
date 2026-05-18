package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.*;
import com.fm.smartlearningplatform.repository.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserProfileService {

    ProfessionRepository professionRepository;

    UserRepository userRepository;

    UserProfileRepository userProfileRepository;

    GenderRepository genderRepository;

    EducationLevelRepository educationLevelRepository;

    @Autowired
    public UserProfileService(UserRepository userRepository, UserProfileRepository userProfileRepository, GenderRepository genderRepository, EducationLevelRepository educationLevelRepository, ProfessionRepository professionRepository){
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.genderRepository = genderRepository;
        this.educationLevelRepository = educationLevelRepository;
        this.professionRepository = professionRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public User addUserProfile(User user,UserProfile userProfile){

        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()) == false){
            throw new RuntimeException("User is not exists.");
        }

        if(userProfileRepository.existsById(userProfile.getId())){
            throw new RuntimeException("User profile is already exists.");
        }

        if(userProfile.getGender() != null && genderRepository.existsById(userProfile.getGender().getId()) == false){
            throw new RuntimeException("Gender is not exists.");
        }

        if(userProfile.getEducationLevel() != null && educationLevelRepository.existsById(userProfile.getEducationLevel().getId()) == false){
            throw new RuntimeException("Education level is not exist.");
        }

        if(userProfile.getProfession() != null && professionRepository.existsById(userProfile.getProfession().getId()) == false){
            throw new RuntimeException("Profession is not exist.");
        }

        if(userProfile.getFirstName() == null){
            throw new RuntimeException("First name is not exist.");
        }

        if(userProfile.getLastName() == null){
            throw new RuntimeException("Last name is not exist.");
        }

        user.setUserProfile(userProfile);

        return userRepository.save(user);
    }

    @Transactional
    public User addUserProfile(Long userId,UserProfile userProfile) {

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User is not exists."));

        if(userProfileRepository.existsById(userProfile.getId())){
            throw new RuntimeException("User profile is already exists.");
        }

        if(userProfile.getGender() != null && genderRepository.existsById(userProfile.getGender().getId()) == false){
            throw new RuntimeException("Gender is not exists.");
        }

        if(userProfile.getEducationLevel() != null && educationLevelRepository.existsById(userProfile.getEducationLevel().getId()) == false){
            throw new RuntimeException("Education level is not exist.");
        }

        if(userProfile.getProfession() != null && professionRepository.existsById(userProfile.getProfession().getId()) == false){
            throw new RuntimeException("Profession is not exist.");
        }

        if(userProfile.getFirstName() == null){
            throw new RuntimeException("First name is not exist.");
        }

        if(userProfile.getLastName() == null){
            throw new RuntimeException("Last name is not exist.");
        }

        user.setUserProfile(userProfile);

        return userRepository.save(user);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public User updateFirstName(Long userId, String firstName){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(firstName == null || firstName.isBlank()){
            throw new RuntimeException("First name is required");
        }

        user.getUserProfile().setFirstName(firstName);

        return userRepository.save(user);
    }

    @Transactional
    public User updateLastName(Long userId, String lastName){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(lastName == null || lastName.isBlank()){
            throw new RuntimeException("First name is required");
        }

        user.getUserProfile().setLastName(lastName);

        return userRepository.save(user);
    }

    @Transactional
    public User updateProfilePictureUrl(Long userId, String profilePictureUrl){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(profilePictureUrl == null || profilePictureUrl.isBlank()){
            throw new RuntimeException("Profile picture url is null.");
        }

        user.getUserProfile().setProfilePictureUrl(profilePictureUrl);

        return userRepository.save(user);
    }

    @Transactional
    public User updateGender(Long userId, Gender newGender){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        Gender gender = genderRepository.findByIdAndDeletedAtIsNull(newGender.getId())
                .orElseThrow(() -> new RuntimeException("Gender not found."));

        user.getUserProfile().setGender(newGender);

        return userRepository.save(user);
    }

    @Transactional
    public User updateEducationLevel(Long userId, EducationLevel newEducationLevel){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        EducationLevel educationLevel = educationLevelRepository.findByIdAndDeletedAtIsNull(newEducationLevel.getId())
                .orElseThrow(() -> new RuntimeException("Education level not found."));

        user.getUserProfile().setEducationLevel(newEducationLevel);

        return userRepository.save(user);
    }

    @Transactional
    public User updateDateOfBirth(Long userId, LocalDate newDOB){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(newDOB == null){
            throw new RuntimeException("Date of birth is null.");
        }

        user.getUserProfile().setDateOfBirth(newDOB);

        return userRepository.save(user);
    }

    @Transactional
    public User updateInstituteName(Long userId, String instituteName){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(instituteName == null || instituteName.isBlank()){
            throw new RuntimeException("Institute name is null");
        }

        user.getUserProfile().setInstituteName(instituteName);

        return userRepository.save(user);
    }

    @Transactional
    public User updateAboutMe(Long userId, String aboutMe){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(aboutMe == null || aboutMe.isBlank()){
            throw new RuntimeException(" AboutMe is required");
        }

        user.getUserProfile().setAboutMe(aboutMe);

        return userRepository.save(user);
    }

    @Transactional
    public User updateHomeAddress(Long userId, Address newHomeAdress){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(newHomeAdress == null){
            throw new RuntimeException("HomeAddress no is required");
        }

        user.getUserProfile().setHomeAddress(newHomeAdress);

        return userRepository.save(user);
    }

    @Transactional
    public User updateWorkAddress(Long userId, Address newWorkAdress){

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getUserProfile() == null){
            throw new RuntimeException("User profile is not exists.");
        }

        if(newWorkAdress == null ){
            throw new RuntimeException("workAddress no is required");
        }

        user.getUserProfile().setWorkAddress(newWorkAdress);

        return userRepository.save(user);
    }
}