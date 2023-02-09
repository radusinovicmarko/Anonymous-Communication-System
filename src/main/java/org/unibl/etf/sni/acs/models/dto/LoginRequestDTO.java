package org.unibl.etf.sni.acs.models.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
    private String keystorePassword;
}
