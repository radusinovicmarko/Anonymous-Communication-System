package org.unibl.etf.sni.acs.services;

import org.unibl.etf.sni.acs.models.dto.LoginRequestDTO;
import org.unibl.etf.sni.acs.models.dto.LoginResponseDTO;
import org.unibl.etf.sni.acs.models.dto.UserDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}
