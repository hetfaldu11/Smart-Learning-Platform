package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.repository.user.*;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public RoleService (RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public Role createRole(String name){

        if(name==null) {
            throw new RuntimeException("Name is null.");
        }

        if(roleRepository.existsByName(name))
            throw new RuntimeException("Role is already exist.");

        Role role = Role.builder()
                .name(name)
                .build();

        return roleRepository.save(role);
    }

    // ─── Update ────────────────────────────────────────────────

    @Transactional
    public Role updateRole(Long id, String newName) {

        if(newName==null) {
            throw new RuntimeException("Name is null.");
        }

        if(roleRepository.existsByName(newName))
            throw new RuntimeException("Role is already exist.");

        Role role = roleRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Role is not exist."));

        role.setName(newName);

        return roleRepository.save(role);
    }

    // ─── Find ────────────────────────────────────────────────

    public  boolean existsByIdAndDeletedAtIsNull(Long id) {
        return roleRepository.existsByIdAndDeletedAtIsNull(id);
    }

    public Role findByIdAndDeletedAtIsNull(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Role is not existed."));

        if(role.getDeletedAt() != null)
            throw new RuntimeException("Role is deleted.");
        return role;
    }

   public  boolean existsByNameAndDeletedAtIsNull(String name){
        return roleRepository.existsByNameAndDeletedAtIsNull(name);
    }

    public Role findByNameAndDeletedAtIsNull(String name){
        Role role = roleRepository.findByName(name)

                .orElseThrow(()->new RuntimeException("Role is not existed."));

        if(role.getDeletedAt() != null)
            throw new RuntimeException("Role is deleted.");
        return role;
    }

    public List<Role> findByDeletedAtIsNull(){
        return roleRepository.findByDeletedAtIsNull();
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role is not exist."));

        if(role.getDeletedAt() != null){
            throw  new RuntimeException("Role is already deleted.");
        }

        userRoleRepository.deleteByRoleId(id);

        role.setDeletedAt(LocalDateTime.now());

        roleRepository.save(role);
    }
}
