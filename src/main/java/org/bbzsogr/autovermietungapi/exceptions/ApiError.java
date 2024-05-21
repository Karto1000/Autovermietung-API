package org.bbzsogr.autovermietungapi.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * A Class that contains the data of an error in the Api
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private String message;
    private HttpStatus status;
}
