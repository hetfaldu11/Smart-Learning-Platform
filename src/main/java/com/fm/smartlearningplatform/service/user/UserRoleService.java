package com.fm.smartlearningplatform.service.user;

import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserRole;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // ─── Create ────────────────────────────────────────────────

    @Transactional
    public UserRole createUserRole(User user, Role role){

        if(userRepository.existsById(user.getId()) == false)
            throw new RuntimeException("User is not existed.");

        if(roleRepository.existsById(role.getId()) == false)
            throw new RuntimeException("Role is not existed.");

        if(userRoleRepository.existsByUserAndRole(user,role))
            throw new RuntimeException("UserRole is already existed.");

        UserRole userRole= user.addRole(role);

        userRepository.save(user);

        return userRole;
    }

    @Transactional
    public UserRole addRole(Long userId, Long roleId){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User is not existed."));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role is not existed."));

        if(userRoleRepository.existsByUserAndRole(user,role))
            throw new RuntimeException("UserRole is already existed.");

        UserRole userRole = user.addRole(role);

        userRepository.save(user);

        return userRole;
    }

    // ─── Find ────────────────────────────────────────────────

    public List<UserRole> findByUserId(Long id){
        if(userRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("User is not exists.");

        return userRoleRepository.findByUserId(id);
    }

    public List<UserRole> findByRoleId(Long id){
        if(roleRepository.existsByIdAndDeletedAtIsNull(id) == false)
            throw new RuntimeException("Role is not exists.");

        return userRoleRepository.findByUserId(id);
    }

    public boolean existByUserIdAndRoleId(Long userId,Long roleId){
        return userRoleRepository.existsByUserIdAndRoleId(userId,roleId);
    }

    public UserRole findByUserIdAndRoleId(Long userId,Long roleId){
        if(userRepository.existsByIdAndDeletedAtIsNull(userId) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(roleRepository.existsByIdAndDeletedAtIsNull(roleId) == false) {
            throw new RuntimeException("role  is Not exists.");
        }

        return userRoleRepository.findByUserIdAndRoleId(userId,roleId).orElseThrow(() -> new RuntimeException("UserRole is not existed."));
    }

    public boolean existByUserAndRole(User user,Role role){
        return userRoleRepository.existsByUserAndRole(user,role);
    }

    public UserRole findByUserAndRole(User user,Role role){
        if(userRepository.existsByIdAndDeletedAtIsNull(user.getId()) == false) {
            throw new RuntimeException("User is not exists.");
        }

        if(roleRepository.existsByIdAndDeletedAtIsNull(role.getId()) == false) {
            throw new RuntimeException("Role is not exists.");
        }
        return userRoleRepository.findByUserAndRole(user,role).orElseThrow(() -> new RuntimeException("UserRole is not existed."));
    }

    // ─── Delete ────────────────────────────────────────────────
    @Transactional
    public void deleteById(Long id){
        if(userRoleRepository.existsById(id) == false)
            throw new RuntimeException("UserRole is not existed.");

        userRoleRepository.deleteById(id);
    }
}
