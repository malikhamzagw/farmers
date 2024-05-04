package com.farms.farmdatacollector.apiModel.request;

import com.farms.farmdatacollector.enums.FieldLength;
import com.farms.farmdatacollector.model.HarvestedCrop;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class HarvestedCropApiModel {
    @NotBlank(message = "FarmName not provided")
    @Size(max = FieldLength.FARM_NAME, message = "Farm Name should be less than " + FieldLength.FARM_NAME)
    private String farmName;
    @NotEmpty(message = "CropType not provided")
    @Size(max = FieldLength.CROP_TYPE, message = "Crop Type should be less than " + FieldLength.CROP_TYPE)
    private String cropType;
    @NotEmpty(message = "season not provided")
    @Size(max = FieldLength.SEASON, message = "Season should be less than " + FieldLength.SEASON)
    private String season;
    @PositiveOrZero(message = "HarvestedProduct Parameter is invalid")
    private double harvestedProduct;

    public HarvestedCrop toHarvestedCropModel(){
        return HarvestedCrop.builder()
                .farmName(this.farmName)
                .cropType(this.cropType)
                .actualHarvest(this.harvestedProduct)
                .status("HARVESTED")
                .season(this.season)
                .harvestedDate(LocalDateTime.now())
                .build();
    }


}
