package com.fm.smartlearningplatform.verification.service;

import com.fm.smartlearningplatform.verification.dto.response.UserVerificationResponse;
import com.fm.smartlearningplatform.verification.mapper.UserVerificationMapper;
import com.fm.smartlearningplatform.verification.repository.UserVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class TempService {

    public final UserVerificationRepository userVerificationRepository;
    public final UserVerificationMapper userVerificationMapper;

    @Transactional
    public UserVerificationResponse findById(Long id){
        return userVerificationMapper.toResponse(userVerificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Temo servcie not found.")));
    }
}
