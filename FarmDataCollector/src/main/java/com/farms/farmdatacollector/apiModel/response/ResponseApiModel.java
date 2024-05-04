package com.farms.farmdatacollector.apiModel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class ResponseApiModel {

    private int status;
    private String message;

}
