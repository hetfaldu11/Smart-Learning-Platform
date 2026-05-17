package com.fm.smartlearningplatform.service.user;
import com.fm.smartlearningplatform.model.user.Interest;

public interface InterestService {
    void save(Interest interest);
    Interest findById(Long id);
    void deleteById(Long id);
}
