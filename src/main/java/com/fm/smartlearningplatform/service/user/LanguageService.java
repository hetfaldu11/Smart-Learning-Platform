package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Language;

public interface LanguageService {
    public void save(Language language);
    public Language findById(Long id);
    public void deleteById(Long id);

}
