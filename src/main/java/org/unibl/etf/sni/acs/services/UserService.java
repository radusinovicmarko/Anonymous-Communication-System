package org.unibl.etf.sni.acs.services;

import org.unibl.etf.sni.acs.models.dto.LoginResponseDTO;

public interface UserService {
    LoginResponseDTO findById(Integer id);
}
