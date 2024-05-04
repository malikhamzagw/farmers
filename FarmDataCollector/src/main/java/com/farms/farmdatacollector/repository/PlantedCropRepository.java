package com.farms.farmdatacollector.repository;

import com.farms.farmdatacollector.model.PlantedCrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantedCropRepository extends JpaRepository<PlantedCrop, Long> {
    List<PlantedCrop> findBySeason(String season);
    List<PlantedCrop> findByCropTypeAndSeason(String cropType, String season);
    PlantedCrop findByFarmNameAndSeason(String farmName, String season);
    PlantedCrop findByFarmNameAndSeasonAndCropType(String farmName, String season, String cropType);

}
