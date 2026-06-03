package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.address.AddressDto;
import com.fm.smartlearningplatform.user.dto.userProfile.request.CreateUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.request.PatchUserProfileRequest;
import com.fm.smartlearningplatform.user.dto.userProfile.response.UserProfileResponse;
import com.fm.smartlearningplatform.user.model.Address;
import com.fm.smartlearningplatform.user.model.EducationLevel;
import com.fm.smartlearningplatform.user.model.Gender;
import com.fm.smartlearningplatform.user.model.Profession;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserProfile;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T19:53:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public AddressDto toDto(Address address) {
        if ( address == null ) {
            return null;
        }

        String street = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String fullAddress = null;

        street = address.getStreet();
        city = address.getCity();
        state = address.getState();
        country = address.getCountry();
        postalCode = address.getPostalCode();
        fullAddress = address.getFullAddress();

        AddressDto addressDto = new AddressDto( street, city, state, country, postalCode, fullAddress );

        return addressDto;
    }

    @Override
    public Address toEntity(AddressDto dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setStreet( dto.street() );
        address.setCity( dto.city() );
        address.setState( dto.state() );
        address.setCountry( dto.country() );
        address.setPostalCode( dto.postalCode() );
        address.setFullAddress( dto.fullAddress() );

        return address;
    }

    @Override
    public UserProfile toEntity(CreateUserProfileRequest request, User user, EducationLevel educationLevel, Profession profession, Gender gender) {
        if ( request == null && user == null && educationLevel == null && profession == null && gender == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        if ( request != null ) {
            userProfile.firstName( request.firstName() );
            userProfile.lastName( request.lastName() );
            userProfile.aboutMe( request.aboutMe() );
            userProfile.dateOfBirth( request.dateOfBirth() );
            userProfile.homeAddress( toEntity( request.homeAddress() ) );
            userProfile.workAddress( toEntity( request.workAddress() ) );
            userProfile.instituteName( request.instituteName() );
            userProfile.profilePictureUrl( request.profilePictureUrl() );
        }
        userProfile.user( user );
        userProfile.educationLevel( educationLevel );
        userProfile.profession( profession );
        userProfile.gender( gender );

        return userProfile.build();
    }

    @Override
    public UserProfileResponse toResponse(UserProfile profile) {
        if ( profile == null ) {
            return null;
        }

        Long userId = null;
        Long educationLevelId = null;
        Long professionId = null;
        Long genderId = null;
        String firstName = null;
        String lastName = null;
        String aboutMe = null;
        LocalDate dateOfBirth = null;
        AddressDto homeAddress = null;
        AddressDto workAddress = null;
        String instituteName = null;
        String profilePictureUrl = null;

        userId = profile.getId();
        educationLevelId = profileEducationLevelId( profile );
        professionId = profileProfessionId( profile );
        genderId = profileGenderId( profile );
        firstName = profile.getFirstName();
        lastName = profile.getLastName();
        aboutMe = profile.getAboutMe();
        dateOfBirth = profile.getDateOfBirth();
        homeAddress = toDto( profile.getHomeAddress() );
        workAddress = toDto( profile.getWorkAddress() );
        instituteName = profile.getInstituteName();
        profilePictureUrl = profile.getProfilePictureUrl();

        UserProfileResponse userProfileResponse = new UserProfileResponse( userId, firstName, lastName, aboutMe, educationLevelId, professionId, genderId, dateOfBirth, homeAddress, workAddress, instituteName, profilePictureUrl );

        return userProfileResponse;
    }

    @Override
    public void update(PatchUserProfileRequest request, UserProfile profile) {
        if ( request == null ) {
            return;
        }

        if ( request.firstName() != null ) {
            profile.setFirstName( request.firstName() );
        }
        if ( request.lastName() != null ) {
            profile.setLastName( request.lastName() );
        }
        if ( request.aboutMe() != null ) {
            profile.setAboutMe( request.aboutMe() );
        }
        if ( request.dateOfBirth() != null ) {
            profile.setDateOfBirth( request.dateOfBirth() );
        }
        if ( request.homeAddress() != null ) {
            profile.setHomeAddress( toEntity( request.homeAddress() ) );
        }
        if ( request.workAddress() != null ) {
            profile.setWorkAddress( toEntity( request.workAddress() ) );
        }
        if ( request.instituteName() != null ) {
            profile.setInstituteName( request.instituteName() );
        }
        if ( request.profilePictureUrl() != null ) {
            profile.setProfilePictureUrl( request.profilePictureUrl() );
        }
    }

    private Long profileEducationLevelId(UserProfile userProfile) {
        EducationLevel educationLevel = userProfile.getEducationLevel();
        if ( educationLevel == null ) {
            return null;
        }
        return educationLevel.getId();
    }

    private Long profileProfessionId(UserProfile userProfile) {
        Profession profession = userProfile.getProfession();
        if ( profession == null ) {
            return null;
        }
        return profession.getId();
    }

    private Long profileGenderId(UserProfile userProfile) {
        Gender gender = userProfile.getGender();
        if ( gender == null ) {
            return null;
        }
        return gender.getId();
    }
}
