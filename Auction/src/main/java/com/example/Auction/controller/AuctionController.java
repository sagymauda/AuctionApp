package com.example.Auction.controller;

import com.example.Auction.dto.BidRequestDto;
import com.example.Auction.dto.BidResponseDto;
import com.example.Auction.mapper.BidsMapper;
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

    @Autowired
    private BidsMapper mapper;

    @PostMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BidResponseDto> auction(@RequestBody BidRequestDto bidRequestDto) {
        log.info(" received and Bid Request With details : {}", bidRequestDto);

        BidResponseDto bidResponseDto = mapper.BidResponseToDto(auctionService.startAuction(mapper.DtoToBidRequest(bidRequestDto)));

        return bidResponseDto == null ?
                new ResponseEntity<>(new BidResponseDto(0.0, "no bid from company found ", Timestamp.from(Instant.now())), HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(bidResponseDto, HttpStatus.OK);

    }

}
