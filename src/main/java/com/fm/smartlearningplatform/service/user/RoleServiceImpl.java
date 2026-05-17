package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    //  ─────Create───────────────────────────────────────────

    @Override
    public Role createRole(String name) {
        Role role = Role.builder()
                .name(name)
                .build();

        roleRepository.save(role);
        return role;
    }

    //  ─────Find─────────────────────────────────────────────

    @Override
    public Role findById(Long id) {
        return roleRepository.findByIdAndDeletedAtIsNull(id).orElse(null);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findByDeletedAtIsNull();
    }

    //  ─────Delete─────────────────────────────────────────────

    @Override
    public void deleteById(Long id){
        Role role = roleRepository.findByIdAndDeletedAtIsNull(id).orElse(null);
        userRoleRepository.deleteByRoleId(id);
        if(role == null)
            return;
        role.setDeletedAt(LocalDateTime.now());
        roleRepository.save(role);
    }
}