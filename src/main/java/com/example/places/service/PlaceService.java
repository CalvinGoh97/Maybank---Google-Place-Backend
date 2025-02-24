package com.example.places.service;

import org.springframework.data.domain.Page;
import com.example.places.model.Place;
import java.util.List;

public interface PlaceService {
    List<Place> getAllPlaces();
    Page<Place> getPlacesPaginated(int page, int size);
    Place savePlace(Place place);
    void deletePlace(Long id);
    Place getPlace(Long id);
    boolean existsByPlaceId(String placeId);
    Place getPlaceDetailsFromGoogle(String placeId);
}