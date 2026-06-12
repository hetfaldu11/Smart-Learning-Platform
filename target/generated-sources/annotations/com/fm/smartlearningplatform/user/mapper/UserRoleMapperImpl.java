package com.fm.smartlearningplatform.user.mapper;

import com.fm.smartlearningplatform.user.dto.userRole.response.UserRoleResponse;
import com.fm.smartlearningplatform.user.model.Role;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.model.UserRole;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-08T19:44:46+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class UserRoleMapperImpl implements UserRoleMapper {

    @Override
    public UserRoleResponse toResponse(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        Long userId = null;
        Long roleId = null;
        String roleName = null;

        userId = userRoleUserId( userRole );
        roleId = userRoleRoleId( userRole );
        roleName = userRoleRoleName( userRole );

        UserRoleResponse userRoleResponse = new UserRoleResponse( userId, roleId, roleName );

        return userRoleResponse;
    }

    private Long userRoleUserId(UserRole userRole) {
        User user = userRole.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private Long userRoleRoleId(UserRole userRole) {
        Role role = userRole.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getId();
    }

    private String userRoleRoleName(UserRole userRole) {
        Role role = userRole.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getName();
    }
}
