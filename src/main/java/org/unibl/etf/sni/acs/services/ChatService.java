package org.unibl.etf.sni.acs.services;

import org.unibl.etf.sni.acs.models.dto.MessageRequestDTO;
import org.unibl.etf.sni.acs.models.dto.UserDTO;

import java.util.List;

public interface ChatService {
    void addUser(UserDTO user);
    void removeUser(String username);
    void send(String messageRequest);
    List<UserDTO> getActiveUsers();
}
