package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
