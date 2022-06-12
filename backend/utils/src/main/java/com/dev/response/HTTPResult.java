package com.dev.response;

import java.net.HttpURLConnection;

/**
 * General response for http request.
 */
public class HTTPResult {

    /**
     * Successful process the request.
     *
     * @param data data to be sent
     * @return general response body
     * @param <T> type of data
     */
    public static <T> GeneralResponse<T> ok(T data) {
        return new GeneralResponse<>(HttpURLConnection.HTTP_OK, data, "succeed");
    }

    /**
     * Fail to process the request.
     *
     * @param message the error message
     * @return general response body
     * @param <T> null
     */
    public static <T> GeneralResponse<T> fail(String message) {
        return new GeneralResponse<>(HttpURLConnection.HTTP_INTERNAL_ERROR, null, message);
    }


}
