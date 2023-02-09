package org.unibl.etf.sni.acs.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponseDTO extends UserDTO {
    private String token;
    private List<UserDTO> activeUsers;
}
