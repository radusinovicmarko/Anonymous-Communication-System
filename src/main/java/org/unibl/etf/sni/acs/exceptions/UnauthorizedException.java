package org.unibl.etf.sni.acs.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnauthorizedException extends HttpException {
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, null);
    }
    public UnauthorizedException(Object data) {
        super(HttpStatus.UNAUTHORIZED, data);
    }
}
