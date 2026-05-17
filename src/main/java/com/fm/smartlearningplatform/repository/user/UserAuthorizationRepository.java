package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserAuthorization;
import com.fm.smartlearningplatform.model.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorizationRepository extends JpaRepository<UserAuthorization, Long> {

    Optional<UserAuthorization>  findByUserIdAndUserRole(Long userId, UserRole userRole);
    Optional<UserAuthorization>  findByUserAndUserRole(User user, UserRole userRole);
}
