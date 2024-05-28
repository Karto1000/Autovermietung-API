package org.bbzsogr.autovermietungapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bbzsogr.autovermietungapi.model.Place;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDTO implements IntoEntity<Place> {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "PLZ is required")
    private Integer plz;

    @Override
    public Place intoEntity() {
        return Place.builder()
                .name(name)
                .plz(plz)
                .build();
    }
}
