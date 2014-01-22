package com.cloudfordev.controlpanel.app.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

        private static final long serialVersionUID = -8808921350815256295L;
        
}