package com.example.Auction.service;

import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.Auction.model.BidStrategy.FIRST_STRATEGY;

@Slf4j
@Service
public class AuctionServiceDiffResponses {

    @Autowired
    private BidsService bidsService;
    @Autowired
    private DiffBidService diffBidService;

    public Double startAuction(BidRequest bidRequest) {

        List<Double> bidss = diffBidService.getBidsWithDifferentPayloads(bidRequest);

        return chooseBidByStrategy(bidRequest.getBidStrategy(), bidss);

    }

    private Double chooseBidByStrategy(BidStrategy strategy, List<Double> bidResponses) {

        log.info("Going inside findBidByStrategy with list {}", bidResponses);

        if (bidResponses.size() == 0)
            return 0.0;

        if (bidResponses.size() == 1)
            return bidResponses.get(0);

        List<Double> sortedBidsList = bidResponses.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        Double answer = Objects.equals(strategy, FIRST_STRATEGY) ?
                sortedBidsList.get(0) :
                sortedBidsList.get(1);

        log.info("what has been returned from the service to the controller is {}", answer);
        return answer;

    }

}


