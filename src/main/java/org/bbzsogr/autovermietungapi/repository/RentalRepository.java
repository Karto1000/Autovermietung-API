package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rental r WHERE r.car.id = :id")
    boolean existsByCarId(Integer id);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Rental r WHERE r.user.id = :id")
    boolean existsByUserId(Integer id);

    @Query("SELECT r FROM Rental r WHERE r.car.id = :carId AND r.user.id = :userId")
    Optional<Rental> findByCarIdAndUserId(Integer carId, Integer userId);
}
