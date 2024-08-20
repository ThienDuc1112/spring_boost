package com.example.springboot.demo.services;

import com.example.springboot.demo.models.Role;

public interface IRoleService {
    public Role getOrCreate(String roleName);
}
