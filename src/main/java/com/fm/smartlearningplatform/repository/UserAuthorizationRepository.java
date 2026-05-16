package com.fm.smartlearningplatform.repository;


import com.fm.smartlearningplatform.model.UserAuthorization;
import com.fm.smartlearningplatform.model.UserAuthorizationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorizationRepository extends JpaRepository<UserAuthorization, UserAuthorizationId> {
}
