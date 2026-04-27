package com.example.countrycityapi.repository;

import com.example.countrycityapi.model.City;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CityRepository {

    private static final List<City> CITIES = List.of(
            new City(1L, "Paris", 1L, 2148000L, "75000", "Capital city of France"),
            new City(2L, "Lyon", 1L, 522000L, "69000", "Major city in southeastern France"),
            new City(3L, "Marseille", 1L, 873000L, "13000", "Port city on the Mediterranean coast"),
            new City(4L, "Toulouse", 1L, 504000L, "31000", "City in southern France"),
            new City(5L, "Nice", 1L, 348000L, "06000", "Popular city on the French Riviera"),
            new City(6L, "Nantes", 1L, 325000L, "44000", "Large city in western France"),
            new City(7L, "Berlin", 2L, 3645000L, "10115", "Capital city of Germany"),
            new City(8L, "Hamburg", 2L, 1841000L, "20095", "Major port city in northern Germany"),
            new City(9L, "Munich", 2L, 1472000L, "80331", "City in Bavaria"),
            new City(10L, "Cologne", 2L, 1086000L, "50667", "City on the Rhine river"),
            new City(11L, "Frankfurt", 2L, 759000L, "60311", "Financial center of Germany"),
            new City(12L, "Stuttgart", 2L, 635000L, "70173", "Industrial city in southwest Germany"),
            new City(13L, "Madrid", 3L, 3266000L, "28001", "Capital city of Spain"),
            new City(14L, "Barcelona", 3L, 1620000L, "08001", "Major coastal city in Catalonia"),
            new City(15L, "Valencia", 3L, 791000L, "46001", "City on Spain's southeastern coast"),
            new City(16L, "Seville", 3L, 688000L, "41001", "Historic city in Andalusia"),
            new City(17L, "Zaragoza", 3L, 675000L, "50001", "City in northeastern Spain"),
            new City(18L, "Milan", 4L, 1379000L, "20121", "Economic center of Italy"),
            new City(19L, "Rome", 4L, 2873000L, "00100", "Capital city of Italy"),
            new City(20L, "Naples", 4L, 909000L, "80100", "Large city in southern Italy"),
            new City(21L, "Turin", 4L, 846000L, "10121", "City in northern Italy"),
            new City(22L, "Bologna", 4L, 390000L, "40121", "Historic university city in Italy")
    );

    public List<City> getAllCities() {
        return CITIES;
    }

    public List<City> getCitiesByCountryId(Long countryId) {
        return CITIES.stream()
                .filter(city -> city.getCountryId().equals(countryId))
                .toList();
    }

    public Optional<City> getCityById(Long cityId) {
        return CITIES.stream()
                .filter(city -> city.getId().equals(cityId))
                .findFirst();
    }
}
