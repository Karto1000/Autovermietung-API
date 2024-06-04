package org.bbzsogr.autovermietungapi.model;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @OneToOne
    private Car car;

    @Column(nullable = false)
    private Integer start;

    @Column(nullable = false)
    private Integer end;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rental rental = (Rental) obj;
        return id.equals(rental.id);
    }
}