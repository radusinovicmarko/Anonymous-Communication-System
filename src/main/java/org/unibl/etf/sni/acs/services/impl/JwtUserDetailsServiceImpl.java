package org.unibl.etf.sni.acs.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.acs.exceptions.UnauthorizedException;
import org.unibl.etf.sni.acs.models.dto.JwtUserDTO;
import org.unibl.etf.sni.acs.repositories.UserRepository;
import org.unibl.etf.sni.acs.services.JwtUserDetailsService;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {
    private final UserRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public JwtUserDetailsServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mapper.map(repository.findByUsername(username).orElseThrow(UnauthorizedException::new), JwtUserDTO.class);
    }
}
