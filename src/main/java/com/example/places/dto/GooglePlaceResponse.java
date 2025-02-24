package com.example.places.dto;

public class GooglePlaceResponse {
    private Result result;
    private String status;
    
    public Result getResult() {
        return result;
    }
    
    public void setResult(Result result) {
        this.result = result;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public static class Result {
        private String name;
        private String formatted_address;
        private Geometry geometry;
        private String place_id;
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getFormattedAddress() {
            return formatted_address;
        }
        
        public void setFormattedAddress(String formatted_address) {
            this.formatted_address = formatted_address;
        }
        
        public Geometry getGeometry() {
            return geometry;
        }
        
        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
        
        public String getPlaceId() {
            return place_id;
        }
        
        public void setPlaceId(String place_id) {
            this.place_id = place_id;
        }
    }
    
    public static class Geometry {
        private Location location;
        
        public Location getLocation() {
            return location;
        }
        
        public void setLocation(Location location) {
            this.location = location;
        }
    }
    
    public static class Location {
        private Double lat;
        private Double lng;
        
        public Double getLat() {
            return lat;
        }
        
        public void setLat(Double lat) {
            this.lat = lat;
        }
        
        public Double getLng() {
            return lng;
        }
        
        public void setLng(Double lng) {
            this.lng = lng;
        }
    }
}