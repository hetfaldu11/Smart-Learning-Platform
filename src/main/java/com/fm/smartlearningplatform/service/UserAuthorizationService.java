package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.UserAuthorization;
import com.fm.smartlearningplatform.model.UserAuthorizationId;

public interface UserAuthorizationService {
    public void save(UserAuthorization userAuthorization);
    public UserAuthorization findById(UserAuthorizationId id);
    public void deleteById(UserAuthorizationId id);
}