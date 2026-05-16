package com.fm.smartlearningplatform.service;
import com.fm.smartlearningplatform.model.Interest;

public interface InterestService {
    void save(Interest interest);
    Interest findById(Long id);
    void deleteById(Long id);
}
