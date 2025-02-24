package com.example.places.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.places.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByPlaceId(String placeId);
}