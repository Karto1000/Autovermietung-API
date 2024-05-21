package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{
    User findByEmail(String email);
}
