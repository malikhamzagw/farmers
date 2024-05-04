package com.farms.farmdatacollector.repository;

import com.farms.farmdatacollector.model.HarvestedCrop;
import com.farms.farmdatacollector.model.PlantedCrop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HarvestedCropRepository extends JpaRepository<HarvestedCrop, Long> {
    List<HarvestedCrop> findBySeason(String season);
    HarvestedCrop findByFarmNameAndSeason(String farmName, String season);
    HarvestedCrop findByFarmNameAndSeasonAndCropType(String farmName, String season, String cropType);

}
