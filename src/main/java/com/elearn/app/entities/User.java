package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    private String userId;

    private String title;

    private String about;

    private boolean active;

    private Date created_at;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    private boolean emailVerified;

    private boolean smsVerified;

    private String name;

    private String password;

    private String profilePath;

    private String recentOTP;

    @ManyToMany(mappedBy = "users", cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Role> roles=new HashSet<>();

    public void assignRole(Role role)
    {
        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRoles(Role role)
    {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }


}
