package org.bbzsogr.autovermietungapi.utils;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class SuccessResponse<T> {
    public T data;
    public HttpStatus status;

    public static <R> SuccessResponse<R> ok(R data) {
        return (SuccessResponse<R>) SuccessResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build();
    }
}
