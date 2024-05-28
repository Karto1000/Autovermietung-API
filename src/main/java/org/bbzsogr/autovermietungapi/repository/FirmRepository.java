package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Firm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FirmRepository extends CrudRepository<Firm, Integer> {
    @Query("SELECT f FROM Firm f JOIN User u ON f.user.id = u.id WHERE u.email = :email")
    Optional<Firm> findByEmail(String email);


    @Query("SELECT f FROM Firm f JOIN Place p ON f.place.id = p.id WHERE f.name LIKE %:q%")
    List<Firm> search(String q);
}
