package org.bbzsogr.autovermietungapi.repository;

import org.bbzsogr.autovermietungapi.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Integer> {
    @Query("SELECT p FROM Place p WHERE p.name LIKE %?1%")
    List<Place> search(String query);
}
