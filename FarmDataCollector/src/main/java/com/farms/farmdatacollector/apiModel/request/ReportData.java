package com.farms.farmdatacollector.apiModel.request;

import com.farms.farmdatacollector.enums.FieldLength;
import com.farms.farmdatacollector.model.HarvestedCrop;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportData {

    List<ReportInitData> reportByFarms;
    List<ReportInitData> reportByCroptype;


//    private String farmName;
//    private String cropType;
//    private String season;
//    private double plantedArea;
//    private double harvestedProduct;
//
}
