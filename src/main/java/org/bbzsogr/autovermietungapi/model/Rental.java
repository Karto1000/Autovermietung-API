package org.bbzsogr.autovermietungapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Rental {
    @EmbeddedId
    private RentalId id;

    @Column(nullable = false)
    private Integer start;

    @Column(nullable = false)
    private Integer end;
}