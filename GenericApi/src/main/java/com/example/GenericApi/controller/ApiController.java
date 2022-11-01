package com.example.GenericApi.controller;

import com.example.GenericApi.model.BidRequest;
import com.example.GenericApi.model.BidResponse;
import com.example.GenericApi.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@RestController
public class ApiController {

    @Value("${number}")
    private Double number;

    @PostMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BidResponse> BidRequest(@RequestBody BidRequest bidRequest) {

        log.warn("A new Request has Entered The generic Api{}",bidRequest);

        log.info("The Number From Application Properties is {}",number);


        log.info("A new Request has Entered The generic Api{}",bidRequest);
        ResponseEntity<BidResponse> responseEntity;
        double num = Utils.generateNumberInRangOf(1, 0);
        log.info("The Number is  {}", number);

        if (num > number) {
            responseEntity = new ResponseEntity<>(new BidResponse(Utils.generateNumberInRangOf(10, 1), Timestamp.from(Instant.now()),bidRequest.getBidStrategy()), HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(new BidResponse(null, Timestamp.from(Instant.now()),bidRequest.getBidStrategy()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
        log.info("The answer returned From api is {}", responseEntity.getBody());

        return responseEntity;

    }

}
