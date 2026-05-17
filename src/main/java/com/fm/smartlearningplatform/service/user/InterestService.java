package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Interest;

import java.util.List;

public interface InterestService {
    Interest findById(Long id);
    void deleteById(Long id);
    Interest createInterest(String name);
    List<Interest> findAll();
}
