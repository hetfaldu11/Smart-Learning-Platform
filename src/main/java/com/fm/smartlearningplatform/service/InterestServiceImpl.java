package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.Interest;
import com.fm.smartlearningplatform.model.User;
import com.fm.smartlearningplatform.repository.InterestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestServiceImpl implements InterestService{

    @Autowired
    InterestRepository interestRepository;

    @Override
    public void save(Interest interest) {
        interestRepository.save(interest);
    }

    @Override
    public Interest findById(Long id) {
        return interestRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        Interest interest = interestRepository.findById(id).orElse(null);
        for(User user : interest.getUsers()){
            user.removeInterest(interest);
        }
        interestRepository.delete(interest);
    }
}
