package com.example.springboot.demo.services;

import com.example.springboot.demo.DTOs.Product.UserDTO;
import com.example.springboot.demo.config.AppConstant;
import com.example.springboot.demo.config.UserInfoConfig;
import com.example.springboot.demo.exceptions.APIException;
import com.example.springboot.demo.mapper.ProductMapper;
import com.example.springboot.demo.models.Role;
import com.example.springboot.demo.models.User;
import com.example.springboot.demo.repositories.RoleRepository;
import com.example.springboot.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserService implements UserDetailsService, IUserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Override
    public UserDTO register(UserDTO userDTO) {
        try{
            Role role = roleService.getOrCreate(userDTO.getRoleName());
            User user = ProductMapper.mapper.mapToUser(userDTO);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
            return userDTO;
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email){
        Optional<User> user = userRepository.findByEmail(email);

        return user.map(UserInfoConfig::new).orElseThrow(() -> new UsernameNotFoundException("User with the email:" + email + "didn't exist!!!" ));
    }

    public void addNewUser(String fullName, String mobileNumber, String email, String password, String... roleNames) {
        User user = new User(fullName, mobileNumber, email, password);

        // Fetch the roles based on the provided role names
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleService.getOrCreate(roleName);
            if (role == null) {
                throw new IllegalArgumentException("Role with name '" + roleName + "' not found.");
            }
            roles.add(role);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}
