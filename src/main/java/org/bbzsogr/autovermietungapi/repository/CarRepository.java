package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE c.brand LIKE %:q% OR c.model LIKE %:q%")
    List<Car> search(String q);

    @Query("SELECT c FROM Car c WHERE (c.brand LIKE %:q% OR c.model LIKE %:q%) AND c.rental IS NULL")
    List<Car> searchNotRented(String q);
}
