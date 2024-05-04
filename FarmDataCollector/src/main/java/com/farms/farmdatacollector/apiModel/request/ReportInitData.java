package com.farms.farmdatacollector.apiModel.request;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportInitData {

    private String farmName;
    private String cropType;
    private String season;
    private double plantedArea;
    private double harvestedProduct;
    private double expectedProduct;
}
