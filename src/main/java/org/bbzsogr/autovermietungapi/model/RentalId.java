package org.bbzsogr.autovermietungapi.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RentalId implements Serializable {
    @ManyToOne
    private User user;

    @OneToOne
    private Car car;
}
