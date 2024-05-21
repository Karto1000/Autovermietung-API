package org.bbzsogr.autovermietungapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class RolePermission {

    @Id
    @ManyToOne
    private Permission permission;

    @Id
    @ManyToOne
    private Role role;
}
