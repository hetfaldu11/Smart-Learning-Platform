package com.fm.smartlearningplatform.repository;

import com.fm.smartlearningplatform.model.UserSocialLink;
import com.fm.smartlearningplatform.model.UserSocialLinkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSocialLinkRepository extends JpaRepository<UserSocialLink, UserSocialLinkId>{

}
