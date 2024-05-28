package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rental r WHERE r.id.car.id = :carId")
    boolean existsByCarId(Integer id);
}
