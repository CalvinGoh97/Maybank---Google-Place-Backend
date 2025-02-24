package com.example.places.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.example.places.model.Place;
import com.example.places.repository.PlaceRepository;
import com.example.places.service.PlaceService;
import com.example.places.dto.GooglePlaceResponse;

@Service
public class PlaceServiceImpl implements PlaceService {
    
    private static final Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);
    
    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${google.maps.api.key}")
    private String googleApiKey;
    
    @Override
    @Transactional(readOnly = true)
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Place> getPlacesPaginated(int page, int size) {
        return placeRepository.findAll(PageRequest.of(page, size));
    }
    
    @Override
    @Transactional
    public Place savePlace(Place place) {
        logger.info("Saving place: {}", place);
        return placeRepository.save(place);
    }
    
    @Override
    @Transactional
    public void deletePlace(Long id) {
        logger.info("Deleting place with id: {}", id);
        placeRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Place getPlace(Long id) {
        logger.info("Getting place with id: {}", id);
        return placeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Place not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByPlaceId(String placeId) {
        return placeRepository.existsByPlaceId(placeId);
    }
    
    @Override
    @Transactional
    public Place getPlaceDetailsFromGoogle(String placeId) {
        logger.info("Fetching place details from Google API for placeId: {}", placeId);
        
        try {
            String url = String.format(
                "https://maps.googleapis.com/maps/api/place/details/json" +
                "?place_id=%s" +
                "&fields=name,formatted_address,geometry" +  // Specify the fields we need
                "&key=%s",
                placeId,
                googleApiKey
            );
            
            logger.debug("Calling Google API URL: {}", url);
            GooglePlaceResponse response = restTemplate.getForObject(url, GooglePlaceResponse.class);
            
            if (response != null && response.getResult() != null) {
                Place place = new Place();
                place.setName(response.getResult().getName());
                place.setAddress(response.getResult().getFormattedAddress());
                place.setLatitude(response.getResult().getGeometry().getLocation().getLat());
                place.setLongitude(response.getResult().getGeometry().getLocation().getLng());
                place.setPlaceId(placeId);
                
                return savePlace(place);
            }
            
            logger.error("No result found for placeId: {}", placeId);
            throw new RuntimeException("Place not found in Google API");
        } catch (Exception e) {
            logger.error("Error fetching place details from Google API: {}", e.getMessage());
            throw new RuntimeException("Error fetching place details: " + e.getMessage());
        }
    }
}