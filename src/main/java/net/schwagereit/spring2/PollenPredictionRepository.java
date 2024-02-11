package net.schwagereit.spring2;

import org.springframework.data.repository.CrudRepository;

public interface PollenPredictionRepository extends CrudRepository<PollenPrediction, String> {

    PollenPrediction findByDatum(String id);
}