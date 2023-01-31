package org.unibl.etf.sni.acs.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.acs.exceptions.UnauthorizedException;
import org.unibl.etf.sni.acs.models.dto.JwtUserDTO;
import org.unibl.etf.sni.acs.models.dto.LoginRequestDTO;
import org.unibl.etf.sni.acs.models.dto.LoginResponseDTO;
import org.unibl.etf.sni.acs.models.entities.UserEntity;
import org.unibl.etf.sni.acs.repositories.UserRepository;
import org.unibl.etf.sni.acs.services.AuthService;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(UnauthorizedException::new);
            JwtUserDTO jwtUser = (JwtUserDTO) auth.getPrincipal();
            LoginResponseDTO response = mapper.map(userEntity, LoginResponseDTO.class);
            response.setToken(generateJwt(jwtUser));
            return response;
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
