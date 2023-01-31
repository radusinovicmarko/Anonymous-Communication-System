package org.unibl.etf.sni.acs.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.acs.exceptions.UnauthorizedException;
import org.unibl.etf.sni.acs.models.dto.LoginResponseDTO;
import org.unibl.etf.sni.acs.repositories.UserRepository;
import org.unibl.etf.sni.acs.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public LoginResponseDTO findById(Integer id) {
        return mapper.map(repository.findById(id).orElseThrow(UnauthorizedException::new), LoginResponseDTO.class);
    }
}
