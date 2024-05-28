package org.bbzsogr.autovermietungapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDTO {
    @NotNull(message = "Start is required")
    private Integer start;

    @NotNull(message = "End is required")
    private Integer end;
}
