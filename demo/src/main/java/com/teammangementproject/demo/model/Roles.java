package com.teammangementproject.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public enum ERole{
        ROLE_TEAM_MEMBER,
        ROLE_PROJECT_MANAGER
    }

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ERole name;
}
