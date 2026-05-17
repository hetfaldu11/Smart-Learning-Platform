package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.*;

public interface UserAuthorizationService {
    public void save(UserAuthorization userAuthorization);
    public UserAuthorization findById(Long id);
    public void deleteById(Long id);
    public UserAuthorization findByUserAndRole(User user, UserRole userRole);
    public UserAuthorization findByUserIdAndRole(Long userId, UserRole userRole);
}