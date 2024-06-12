package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rental r WHERE r.car.id = :id")
    boolean existsByCarId(Integer id);

    @Query("SELECT r FROM Rental r WHERE (r.car.model LIKE %:q% OR r.car.brand LIKE %:q% OR r.car.firm.name LIKE %:q%) AND r.user.id = :userId")
    List<Rental> search(String q, Integer userId);
}
