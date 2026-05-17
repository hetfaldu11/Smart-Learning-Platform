package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.UserInterest;

import java.util.List;

public interface UserInterestService {

    public UserInterest addInterestToUser(Long userId, Long interestId);

    // ─── Find ────────────────────────────────────────────────

    public List<UserInterest> getUserInterests(Long userId) ;

    public List<UserInterest> getInterestUsers(Long interestId) ;

    public UserInterest findByUserIdAndInterestId(Long userId, Long interestId);

    // ─── Remove Interest from User ──────────────────────────────

    public void removeInterestFromUser(Long userId, Long interestId);

    public void deleteById(Long id);
}
