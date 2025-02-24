package com.example.places.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.places.model.Place;
import com.example.places.service.PlaceService;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class PlaceController {
    
    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
    
    private final PlaceService placeService;
    
    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
    
    @GetMapping
    public ResponseEntity<Page<Place>> getAllPlaces(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("REQUEST - Get all places with page: {} and size: {}", page, size);
        Page<Place> places = placeService.getPlacesPaginated(page, size);
        logger.info("RESPONSE - Returned {} places", places.getContent().size());
        return ResponseEntity.ok(places);
    }
    
    @PostMapping
    public ResponseEntity<Place> savePlace(@RequestBody Place place) {
        logger.info("REQUEST - Save place: {}", place);
        if (placeService.existsByPlaceId(place.getPlaceId())) {
            logger.warn("Place with ID {} already exists", place.getPlaceId());
            return ResponseEntity.badRequest().build();
        }
        Place savedPlace = placeService.savePlace(place);
        logger.info("RESPONSE - Saved place: {}", savedPlace);
        return ResponseEntity.ok(savedPlace);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        logger.info("REQUEST - Delete place with id: {}", id);
        placeService.deletePlace(id);
        logger.info("RESPONSE - Deleted place with id: {}", id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlace(@PathVariable Long id) {
        logger.info("REQUEST - Get place with id: {}", id);
        Place place = placeService.getPlace(id);
        logger.info("RESPONSE - Retrieved place: {}", place);
        return ResponseEntity.ok(place);
    }
    
    @GetMapping("/details/{placeId}")
    public ResponseEntity<Place> getPlaceDetails(@PathVariable String placeId) {
        logger.info("REQUEST - Get place details from Google API for placeId: {}", placeId);
        Place place = placeService.getPlaceDetailsFromGoogle(placeId);
        logger.info("RESPONSE - Retrieved place details: {}", place);
        return ResponseEntity.ok(place);
    }
}