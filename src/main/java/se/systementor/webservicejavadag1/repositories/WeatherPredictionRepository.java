    package se.systementor.webservicejavadag1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.systementor.webservicejavadag1.models.WeatherPrediction;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
@Repository
public interface WeatherPredictionRepository extends CrudRepository<WeatherPrediction, UUID> {
    @Override
    Optional<WeatherPrediction> findById(UUID id);

    @Override
    List<WeatherPrediction> findAll();



}
