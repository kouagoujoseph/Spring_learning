package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.TypeRole;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService implements UserDetailsService{

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public void inscription(User user){

        String passwordhash= this.passwordEncoder.encode(user.getPassword());

        if (!user.getEmail().contains("@")) {
            throw new RuntimeException("votre mail est invalide");
        }
        if (!user.getEmail().contains(".")) {
            throw new RuntimeException("votre mail est invalide");
        }
        Optional<User> userOptional=this.userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new RuntimeException("votre mail est dédjà utilisé");
        }
        Role role= new Role();
        role.setLibelle(TypeRole.USER);
         user.setRole(role);
        user.setPassword(passwordhash);
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return this.userRepository.findByEmail(username).orElse(null);
       
    }

}
