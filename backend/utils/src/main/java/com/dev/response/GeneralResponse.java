package com.dev.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralResponse<T> {

    // status code
    private int code;

    // response data
    private T data;

    // response message
    private String message;

}
