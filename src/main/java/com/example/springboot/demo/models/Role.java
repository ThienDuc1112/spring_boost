package com.example.springboot.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roleName;

    public Role(){}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
