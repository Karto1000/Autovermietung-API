package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Permission;
import org.bbzsogr.autovermietungapi.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query("""
                SELECT p FROM Permission p
                JOIN RolePermission rp ON p.id = rp.permission.id
                WHERE rp.role.id = :roleId
            """)
    List<Permission> getPermissionsByRoleId(Integer roleId);
}
