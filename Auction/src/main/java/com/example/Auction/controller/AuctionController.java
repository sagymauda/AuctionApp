package com.example.Auction.controller;


import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
import com.example.Auction.service.AuctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@RestController
@RequestMapping(value = "/bid")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BidResponse> auction(@RequestBody BidRequest bidRequest) {
        log.info(" received and Bid Request With details : {}", bidRequest);

        BidResponse bidResponse = auctionService.startBidsAuction(bidRequest);

        return bidResponse == null ?
                new ResponseEntity<>(new BidResponse(0.0,"no bid from company found ", Timestamp.from(Instant.now())), HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(bidResponse, HttpStatus.OK);



    }

}
