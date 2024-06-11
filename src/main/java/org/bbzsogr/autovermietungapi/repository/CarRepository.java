package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE c.brand LIKE %:brand% OR c.model LIKE %:model%")
    List<Car> search(String brand, String model);

    @Query("SELECT c FROM Car c WHERE (c.brand LIKE %:brand% OR c.model LIKE %:model%) AND c.rental IS NULL")
    List<Car> searchNotRented(String brand, String model);
}
