package com.moisesmtzg.crudexamples.controllers;


import com.moisesmtzg.crudexamples.configurations.login.LoginRquest;
import com.moisesmtzg.crudexamples.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRquest loginRquest){
        log.info("Logging user: {}", loginRquest);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRquest.username(),
                loginRquest.password()
        ));
        //authentication is ok
        log.info("auth correct");
        String token = jwtService.generateToken(loginRquest.username());
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        return result;
    }
}
