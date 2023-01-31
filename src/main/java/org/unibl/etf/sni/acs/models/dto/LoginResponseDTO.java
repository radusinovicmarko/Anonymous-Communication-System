package org.unibl.etf.sni.acs.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponseDTO extends UserDTO {
    private String token;
}
