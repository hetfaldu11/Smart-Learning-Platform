package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Profession;
import com.fm.smartlearningplatform.repository.user.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessionServiceImpl implements ProfessionService{

    @Autowired
    ProfessionRepository professionRepository;

    @Override
    public void save(Profession profession) {
        professionRepository.save(profession);
    }

    @Override
    public Profession findById(Long id) {
        return professionRepository.findById(id).orElse(null);
    }

    @Override
    public Profession findByName(String name) {
        return professionRepository.findByName(name).orElse(null);
    }

    @Override
    public void deleteById(Long id){
        professionRepository.deleteById(id);
    }
}
