package com.farms.farmdatacollector.controller;

import com.farms.farmdatacollector.apiModel.request.HarvestedCropApiModel;
import com.farms.farmdatacollector.apiModel.request.PlantedCropApiModel;
import com.farms.farmdatacollector.apiModel.response.ResponseApiModel;
import com.farms.farmdatacollector.enums.StatusCode;
import com.farms.farmdatacollector.service.FarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/farmers")
@Validated
public class FarmController {
    private FarmService farmService;
    public FarmController(FarmService farmService){
        this.farmService = farmService;
    }


    /**
     * @param plantedCropApiModel
     * @return ResponseEntity
     *
     * This API is used to save the data of planted area
     * It serves the PlantedApiModel and after basic java validation
     * it saves the data in H2 database
     * if there would be any issue then bad request error will be sent back
     *
     */
    @PostMapping("/planted")
    public ResponseEntity<ResponseApiModel> addPlantedData(@Valid @RequestBody PlantedCropApiModel plantedCropApiModel) {

        ResponseApiModel responseApiModel = farmService.addPlantedCropData(plantedCropApiModel);
        if(responseApiModel.getStatus() == StatusCode.SUCCESS){
            return ResponseEntity.ok().body(responseApiModel);
        }
        else {
            return ResponseEntity.badRequest().body(responseApiModel);
        }
    }

    /**
     * @param harvestedCropApiModel
     * @return ResponseEntity
     *
     * This API is used to save the data of harvested area
     * It serves the PlantedApiModel and after basic java validation
     * it saves the data in H2 database
     * if there would be any issue then bad request error will be sent back
     *
     */

    @PostMapping("/harvested")
    public ResponseEntity addHarvestedData(@Valid @RequestBody HarvestedCropApiModel harvestedCropApiModel) {
        ResponseApiModel responseApiModel = farmService.addHarvestedCropData(harvestedCropApiModel);
        if(responseApiModel.getStatus() == StatusCode.SUCCESS){
            return ResponseEntity.ok().body(responseApiModel);
        }
        else {
            return ResponseEntity.badRequest().body(responseApiModel);
        }
    }

}
