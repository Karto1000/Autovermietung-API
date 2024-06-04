package org.bbzsogr.autovermietungapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bbzsogr.autovermietungapi.model.Rental;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDTO implements IntoEntity<Rental> {
    @NotNull(message = "Start is required")
    private Integer start;

    @NotNull(message = "End is required")
    private Integer end;

    @Override
    public Rental intoEntity() {
        return Rental.builder()
                .end(this.end)
                .start(this.start)
                .build();
    }
}
