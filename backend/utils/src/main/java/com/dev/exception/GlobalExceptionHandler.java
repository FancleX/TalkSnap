package com.dev.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * The application system exception
     *
     * @param e
     */
    @ExceptionHandler(value = Exception.class)
    public void applicationException(Exception e) {
        LOG.error("Application exception: " + e.getMessage());
    }

    /**
     * 401 unauthorized exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = { InvalidAuthException.class, MissingRequestHeaderException.class } )
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Map<String, String> invalidAuthException(Exception e) {
        LOG.error(e.getClass().getName() + ": " + e.getMessage());
        Map<String, String> res = new HashMap<>();
        res.put("message", "Invalid or expired authorization.");
        return res;
    }

}
