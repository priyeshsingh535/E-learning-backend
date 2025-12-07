package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    private String role_Id;

    private String roleName;

    @ManyToMany
    @JoinTable(name="roles_users")
    private Set<User> users=new HashSet<>();
}
