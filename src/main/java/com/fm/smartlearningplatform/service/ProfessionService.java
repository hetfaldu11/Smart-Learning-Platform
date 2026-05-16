package com.fm.smartlearningplatform.service;

import com.fm.smartlearningplatform.model.Profession;

public interface ProfessionService {
    public void save(Profession profession);
    public Profession findById(Long id);
    public Profession findByName(String name);
    public void deleteById(Long id);
}
