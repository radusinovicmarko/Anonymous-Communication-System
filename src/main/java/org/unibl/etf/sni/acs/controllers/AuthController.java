package org.unibl.etf.sni.acs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.acs.exceptions.UnauthorizedException;
import org.unibl.etf.sni.acs.models.dto.JwtUserDTO;
import org.unibl.etf.sni.acs.models.dto.LoginRequestDTO;
import org.unibl.etf.sni.acs.models.dto.LoginResponseDTO;
import org.unibl.etf.sni.acs.models.dto.UserDTO;
import org.unibl.etf.sni.acs.services.AuthService;
import org.unibl.etf.sni.acs.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;
    private UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/state")
    public LoginResponseDTO state(Authentication authentication) {
        if (authentication == null)
            throw new UnauthorizedException();
        return userService.findById(((JwtUserDTO) authentication.getPrincipal()).getId());
    }
}
