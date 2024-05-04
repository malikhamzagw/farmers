package com.farms.farmdatacollector.controller;

import com.farms.farmdatacollector.apiModel.request.ReportData;
import com.farms.farmdatacollector.service.FarmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReportController {
    private FarmService farmService;
    public ReportController(FarmService farmService){
        this.farmService = farmService;
    }

    @GetMapping("/reports/{season}")
    public String getReportForSeason(@PathVariable String season, Model model) {

        ReportData reportDataList = farmService.generateReports(season);
        model.addAttribute("reportDataList", reportDataList);
        return "report-template";
    }
}