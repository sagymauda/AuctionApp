package com.example.Auction.service;

import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
import com.example.Auction.model.DiffBidResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ApiRequestService {

    @Autowired
    private RestTemplate restTemplate;

    public BidResponse companyApi(String url, BidRequest bidRequest) {
        BidResponse response = new BidResponse();
        try {
            response = restTemplate.postForObject(url, bidRequest, BidResponse.class);

        } catch (Exception ex) {

            log.error("error from the service {} ", ex.getMessage());
        }
        return response;
    }
    public DiffBidResponse diffCompanyApi(String url, BidRequest bidRequest) {
        DiffBidResponse response = new DiffBidResponse();
        try {
            response = restTemplate.postForObject(url, bidRequest, DiffBidResponse.class);

        } catch (Exception ex) {

            log.error("error from the service {} ", ex.getMessage());
        }
        return response;
    }

}
