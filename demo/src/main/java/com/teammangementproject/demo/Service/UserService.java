package com.teammangementproject.demo.Service;

import com.teammangementproject.demo.model.Roles;
import com.teammangementproject.demo.model.User;
import com.teammangementproject.demo.repository.RoleRepo;
import com.teammangementproject.demo.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email,String password, Set<String> strRoles){
        if (userRepo.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }

        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Set<Roles> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Roles teamMember = roleRepo.findByName(Roles.ERole.ROLE_TEAM_MEMBER)
                    .orElseThrow(() -> new RuntimeException("Error: Role 'TEAM_MEMBER' not found."));
            roles.add(teamMember);
        } else {
            for (String role : strRoles) {
                switch (role.toLowerCase()) {
                    case "manager" -> {
                        Roles manager = roleRepo.findByName(Roles.ERole.ROLE_PROJECT_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'PROJECT_MANAGER' not found."));
                        roles.add(manager);
                    }
                    case "member" -> {
                        Roles member = roleRepo.findByName(Roles.ERole.ROLE_TEAM_MEMBER)
                                .orElseThrow(() -> new RuntimeException("Error: Role 'TEAM_MEMBER' not found."));
                        roles.add(member);
                    }
                    default -> throw new RuntimeException("Error: Role '" + role + "' is not supported.");
                }
            }
        }
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public Optional<User> findUserById(Long id){
        return userRepo.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " does not exist.");
        }
        userRepo.deleteById(id);
    }

    public User updateUserRoles(Long userId, Set<String> newRoles) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));

        if (newRoles == null || newRoles.isEmpty()) {
            throw new IllegalArgumentException("At least one role must be specified.");
        }

        Set<Roles> roles = new HashSet<>();
        for (String role : newRoles) {
            switch (role.toLowerCase()) {
                case "manager" -> {
                    Roles manager = roleRepo.findByName(Roles.ERole.ROLE_PROJECT_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    roles.add(manager);
                }
                case "member" -> {
                    Roles member = roleRepo.findByName(Roles.ERole.ROLE_TEAM_MEMBER)
                            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    roles.add(member);
                }
                default -> throw new IllegalArgumentException("Unsupported role: " + role);
            }
        }
        user.setRoles(roles);
        return userRepo.save(user);
    }
}
