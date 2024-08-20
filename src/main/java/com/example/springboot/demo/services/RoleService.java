package com.example.springboot.demo.services;

import com.example.springboot.demo.models.Role;
import com.example.springboot.demo.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class RoleService implements IRoleService{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getOrCreate(String roleName) {
        Role role = roleRepository.findByRoleName(roleName);
        if(role == null){
            Role newRole = new Role();
            newRole.setRoleName(roleName);
            roleRepository.save(role);
            return newRole;
        }
        return role;
    }

    public void save(Role role){
        roleRepository.save(role);
    }
}
