package org.unibl.etf.sni.acs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.acs.exceptions.UnauthorizedException;
import org.unibl.etf.sni.acs.models.dto.*;
import org.unibl.etf.sni.acs.services.AuthService;
import org.unibl.etf.sni.acs.services.ChatService;
import org.unibl.etf.sni.acs.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;
    private UserService userService;
    private ChatService chatService;

    @Autowired
    public AuthController(AuthService authService, UserService userService, ChatService chatService) {
        this.authService = authService;
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequestDTO logoutRequest) { chatService.removeUser(logoutRequest.getUsername()); }

    @GetMapping("/state")
    public LoginResponseDTO state(Authentication authentication) {
        if (authentication == null)
            throw new UnauthorizedException();
        return userService.findById(((JwtUserDTO) authentication.getPrincipal()).getId());
    }
}
