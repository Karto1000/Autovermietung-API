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
@ToString
public class PlaceDTO implements IntoEntity<Place> {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Integer plz;

    @Override
    public Place intoEntity() {
        return Place.builder()
                .name(name)
                .plz(plz)
                .build();
    }
}
