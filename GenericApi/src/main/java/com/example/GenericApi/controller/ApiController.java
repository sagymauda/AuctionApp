package com.example.GenericApi.controller;

import com.example.GenericApi.model.BidRequest;
import com.example.GenericApi.model.BidResponse;
import com.example.GenericApi.utils.Utils;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping(value = "/api/{companyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BidResponse> BidRequest(@PathVariable String companyName, @RequestBody BidRequest bidRequest) {
        ResponseEntity<BidResponse> responseEntity;
        double num = Utils.generateNumberInRangOf(1, 0);
        log.info("The Number is  {}", num);

        if (num > 0.5) {
            responseEntity = new ResponseEntity<>(new BidResponse(Utils.generateNumberInRangOf(10, 1),companyName, Timestamp.from(Instant.now())), HttpStatus.OK);
        } else {
            throw new java.lang.RuntimeException("this is bad From" + companyName + "Service");
        }
        log.info("The answer returned From"+ companyName +"api is {}", responseEntity.getBody());

        return responseEntity;

    }

}
