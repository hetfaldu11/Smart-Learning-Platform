package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long id);
    void deleteById(Long id);
    Role createRole(String name);
    List<Role> findAll();
}
