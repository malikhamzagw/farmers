package com.farms.farmdatacollector.service;

import com.farms.farmdatacollector.apiModel.request.HarvestedCropApiModel;
import com.farms.farmdatacollector.apiModel.request.PlantedCropApiModel;
import com.farms.farmdatacollector.apiModel.request.ReportData;
import com.farms.farmdatacollector.apiModel.response.ResponseApiModel;

public interface FarmService {

    ResponseApiModel addPlantedCropData(PlantedCropApiModel plantedCropApiModel);
    ResponseApiModel addHarvestedCropData(HarvestedCropApiModel harvestedCropApiModel);
    ReportData generateReports(String season);

}
