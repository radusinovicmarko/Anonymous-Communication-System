package org.unibl.etf.sni.acs.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.acs.exceptions.UnauthorizedException;
import org.unibl.etf.sni.acs.models.dto.*;
import org.unibl.etf.sni.acs.models.entities.UserEntity;
import org.unibl.etf.sni.acs.repositories.UserRepository;
import org.unibl.etf.sni.acs.services.AuthService;
import org.unibl.etf.sni.acs.services.ChatService;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ChatService chatService;
    private final PasswordEncoder passwordEncoder;
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, ModelMapper mapper, ChatService chatService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.chatService = chatService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(UnauthorizedException::new);
            if (passwordEncoder.matches(loginRequest.getKeystorePassword(), userEntity.getKeystore_password())) {
                JwtUserDTO jwtUser = (JwtUserDTO) auth.getPrincipal();
                LoginResponseDTO response = mapper.map(userEntity, LoginResponseDTO.class);
                response.setToken(generateJwt(jwtUser));
                chatService.addUser(mapper.map(userEntity, UserDTO.class));
                response.setActiveUsers(chatService.getActiveUsers());
                return response;
            } else {
                throw new UnauthorizedException();
            }
        } catch (Exception exception) {
            throw new UnauthorizedException();
        }
    }

    private String generateJwt(JwtUserDTO user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }
}
