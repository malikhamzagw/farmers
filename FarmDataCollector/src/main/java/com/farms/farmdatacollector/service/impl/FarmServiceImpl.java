package com.farms.farmdatacollector.service.impl;

import com.farms.farmdatacollector.apiModel.request.HarvestedCropApiModel;
import com.farms.farmdatacollector.apiModel.request.PlantedCropApiModel;
import com.farms.farmdatacollector.apiModel.request.ReportData;
import com.farms.farmdatacollector.apiModel.request.ReportInitData;
import com.farms.farmdatacollector.apiModel.response.ResponseApiModel;
import com.farms.farmdatacollector.enums.MessageEnum;
import com.farms.farmdatacollector.enums.StatusCode;
import com.farms.farmdatacollector.model.HarvestedCrop;
import com.farms.farmdatacollector.model.PlantedCrop;
import com.farms.farmdatacollector.repository.HarvestedCropRepository;
import com.farms.farmdatacollector.repository.PlantedCropRepository;
import com.farms.farmdatacollector.service.FarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FarmServiceImpl implements FarmService {

    final PlantedCropRepository plantedCropRepository;
    final HarvestedCropRepository harvestedCropRepository;
    public FarmServiceImpl(PlantedCropRepository plantedCropRepository, HarvestedCropRepository harvestedCropRepository) {
        this.plantedCropRepository = plantedCropRepository;
        this.harvestedCropRepository = harvestedCropRepository;
    }

    @Override
    public ResponseApiModel addPlantedCropData(PlantedCropApiModel plantedCropApiModel) {

        try {

            PlantedCrop alreadyPlantedCrop = plantedCropRepository.findByFarmNameAndSeasonAndCropType(plantedCropApiModel.getFarmName(), plantedCropApiModel.getSeason(), plantedCropApiModel.getCropType());
            if(!ObjectUtils.isEmpty(alreadyPlantedCrop)){
                return ResponseApiModel.builder().status(StatusCode.BAD_REQUEST).message(MessageEnum.REQUEST_ALREADY_PROCESSED + " for " + plantedCropApiModel.getFarmName()).build();
            }

            PlantedCrop plantedCrop = plantedCropApiModel.toPlantedCropModel();
            PlantedCrop response = plantedCropRepository.save(plantedCrop);
            log.info("Planted crop saved successfully");
            if(ObjectUtils.isEmpty(response))
                return ResponseApiModel.builder().status(StatusCode.BAD_REQUEST).message(MessageEnum.UNABLE_TO_PROCESS_REQUEST).build();

            return ResponseApiModel.builder().status(StatusCode.SUCCESS).message(MessageEnum.REQUEST_PROCESSED_SUCCESSFULLY).build();
        }
        catch (Exception exception){
            log.info(MessageEnum.UNABLE_TO_PROCESS_REQUEST + exception.getMessage());
            return ResponseApiModel.builder().status(StatusCode.BAD_REQUEST).message(MessageEnum.UNABLE_TO_PROCESS_REQUEST).build();
        }
    }

    @Override
    public ResponseApiModel addHarvestedCropData(HarvestedCropApiModel harvestedCropApiModel) {
        try {

            HarvestedCrop alreadyHarvestedCrop = harvestedCropRepository.findByFarmNameAndSeasonAndCropType(harvestedCropApiModel.getFarmName(), harvestedCropApiModel.getSeason(), harvestedCropApiModel.getCropType());
            if(!ObjectUtils.isEmpty(alreadyHarvestedCrop)){
                return ResponseApiModel.builder().status(StatusCode.BAD_REQUEST).message(MessageEnum.REQUEST_ALREADY_PROCESSED + " for " + alreadyHarvestedCrop.getFarmName()).build();
            }

            HarvestedCrop harvestedCrop = harvestedCropApiModel.toHarvestedCropModel();
            HarvestedCrop response = harvestedCropRepository.save(harvestedCrop);
            log.info("Harvest crop saved successfully");
            if(ObjectUtils.isEmpty(response))
                return ResponseApiModel.builder().status(StatusCode.BAD_REQUEST).message(MessageEnum.UNABLE_TO_PROCESS_REQUEST).build();

            return ResponseApiModel.builder().status(StatusCode.SUCCESS).message(MessageEnum.REQUEST_PROCESSED_SUCCESSFULLY).build();
        }
        catch (Exception exception){
            log.info(MessageEnum.UNABLE_TO_PROCESS_REQUEST + exception.getMessage());
            return ResponseApiModel.builder().status(StatusCode.BAD_REQUEST).message(MessageEnum.UNABLE_TO_PROCESS_REQUEST).build();
        }
    }


    @Override
    public ReportData generateReports (String season){

        ReportData reportDataAll =  new ReportData();

        List<ReportInitData> reportInitData = new ArrayList<>();
        List<PlantedCrop> plantedCropList = plantedCropRepository.findBySeason(season);
        plantedCropList.forEach(t -> {
            HarvestedCrop harvestedCrop = harvestedCropRepository.findByFarmNameAndSeason(t.getFarmName(), t.getSeason());
            reportInitData.add(ReportInitData.builder()
                            .farmName(t.getFarmName())
                            .plantedArea(t.getPlantedArea())
                            .expectedProduct(t.getExpectedProduct())
                            .season(t.getSeason())
                            .harvestedProduct(ObjectUtils.isEmpty(harvestedCrop) ? 0 : harvestedCrop.getActualHarvest())
                            .cropType(t.getCropType())
                            .build());
        });

        List<HarvestedCrop> harvestedCropList = harvestedCropRepository.findBySeason(season);

        Map<String, ReportInitData> groupedData = new HashMap<>();

        for (PlantedCrop planting : plantedCropList) {

            String cropType = planting.getCropType();
            double plantingArea = planting.getPlantedArea();
            double expectedProduct = planting.getExpectedProduct();

            if (groupedData.containsKey(cropType)) {
                ReportInitData reportData = groupedData.get(cropType);
                reportData.setPlantedArea(reportData.getPlantedArea() + plantingArea);
                reportData.setExpectedProduct(reportData.getExpectedProduct() + expectedProduct);
            } else {
                ReportInitData reportData = new ReportInitData();
                reportData.setPlantedArea(plantingArea);
                reportData.setExpectedProduct(expectedProduct);
                groupedData.put(cropType, reportData);
            }
        }

        for (HarvestedCrop harvesting : harvestedCropList) {
            String cropType = harvesting.getCropType();
            double actualHarvested = harvesting.getActualHarvest();
            if (groupedData.containsKey(cropType)) {
                ReportInitData reportData = groupedData.get(cropType);
                reportData.setHarvestedProduct(reportData.getHarvestedProduct() + actualHarvested);
            } else {
                ReportInitData reportData = new ReportInitData();
                reportData.setHarvestedProduct(actualHarvested);
                groupedData.put(cropType, reportData);
            }
        }

        List<ReportInitData> reportDataByType = new ArrayList<>();
        groupedData.forEach( (t, t1) -> {
            reportDataByType.add(ReportInitData.builder()
                    .cropType(t)
                            .plantedArea(t1.getPlantedArea())
                    .expectedProduct(t1.getExpectedProduct())
                    .harvestedProduct(t1.getHarvestedProduct())
                    .build());
        });

        reportDataAll.setReportByFarms(reportInitData);
        reportDataAll.setReportByCroptype(reportDataByType);
        return reportDataAll;
    }

}
