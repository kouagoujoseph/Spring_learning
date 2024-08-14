package com.example.demo.controller;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.JwtService;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;
    @PostMapping("/inscription")
    public void inscription(@RequestBody User user){
      log.info("inscription");
      this.userService.inscription(user);
    }

    @PostMapping("/connexion")
    public Map<String, String> connexion(String username, String password) throws Exception {
      final Authentication authentificate= authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
      ); 
       if (authentificate.isAuthenticated()) {

        return this.jwtService.generate(username);
        
       }else{
           throw new Exception("Non reussi");
       }
      
    }
    
}
