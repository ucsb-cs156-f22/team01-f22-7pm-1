package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.spring.backenddemo.services.TidesQueryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description="Tides info from https://tidesandcurrents.noaa.gov/")
@Slf4j
@RestController
@RequestMapping("/api/tides")

public class TidesController {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    TidesQueryService tidesQueryService;

    @ApiOperation(value="Get tide information given a station and date", notes="Tide data uploaded to tidesandcurrents.noaa.gov by the National Oceanic and Atmospheric Administration & National Ocean Service")
    @GetMapping("/get")
    public ResponseEntity<String> getTides(
        @ApiParam("beginDate, e.g. 20221018") @RequestParam String beginDate,
        @ApiParam("endDate, e.g. 20221020") @RequestParam String endDate,
        @ApiParam("station, e.g. 9411340") @RequestParam String station
    ) throws JsonProcessingException {
        log.info("beginDate={}, endDate={}, station={}", beginDate, endDate, station);
        String result = tidesQueryService.getJSON(beginDate,endDate,station);
        return ResponseEntity.ok().body(result);
    }

}