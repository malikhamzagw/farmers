package com.farms.farmdatacollector.apiModel.request;

import com.farms.farmdatacollector.enums.FieldLength;
import com.farms.farmdatacollector.model.PlantedCrop;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class PlantedCropApiModel {
    @NotBlank(message = "FarmName not provided")
    @Size(max = FieldLength.FARM_NAME, message = "Farm Name should be less than " + FieldLength.FARM_NAME)
    private String farmName;
    @NotEmpty(message = "CropType not provided")
    @Size(max = FieldLength.CROP_TYPE, message = "Crop Type should be less than " + FieldLength.CROP_TYPE)
    private String cropType;
    @NotEmpty(message = "season not provided")
    @Size(max = FieldLength.SEASON, message = "Season should be less than " + FieldLength.SEASON)
    private String season;
    @PositiveOrZero(message = "PlantedArea Parameter is invalid")
    private double plantedArea;
    @PositiveOrZero(message = "PlantedArea Parameter is invalid")
    private double expectedProduct;

    public PlantedCrop toPlantedCropModel(){
        return PlantedCrop.builder()
                .farmName(this.farmName)
                .cropType(this.cropType)
                .expectedProduct(this.expectedProduct)
                .plantedArea(this.plantedArea)
                .status("PLANTED")
                .season(this.season)
                .harvestedDate(LocalDateTime.now()).build();
    }

}
