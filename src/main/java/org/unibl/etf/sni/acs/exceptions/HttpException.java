package org.unibl.etf.sni.acs.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends RuntimeException {
    private HttpStatus status;
    private Object data;

    public HttpException() {
        this(HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public HttpException(Object data) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, data);
    }

    public HttpException(HttpStatus status, Object data) {
        this.status = status;
        this.data = data;
    }
}
