package org.unibl.etf.sni.acs.models.dto;

import lombok.Data;

@Data
public class MessageRequestDTO {
    private String senderUsername;
    private String receiverUsername;
    private String data;
    private Boolean syn;
    private Boolean sendCert;
    private Boolean ackCert;
    private Boolean sendKey;
    private Boolean ackKey;
    private Boolean fin;
}
