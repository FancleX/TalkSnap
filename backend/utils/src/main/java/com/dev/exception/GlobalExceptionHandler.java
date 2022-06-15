package com.dev.exception;

import com.dev.response.GeneralResponse;
import com.dev.response.HTTPResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

/**
 * Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // global logger
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @ExceptionHandler(value = Exception.class)
    public GeneralResponse<String> applicationException(Exception e) {
        logger.info("Application exception: " + e.getMessage());
        return HTTPResult.fail(e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    public GeneralResponse<String> businessException(BusinessException e) {
        logger.info("Business exception: " + e.getMsg());
        return HTTPResult.fail(e.getMsg());
    }

}
