package com.example.Auction.service;

import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
import com.example.Auction.model.BidStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.example.Auction.model.BidStrategy.FIRST_STRATEGY;

@Slf4j
@Service
public class AuctionService {
    @Autowired
    private BidsService bidsService;

    public BidResponse startAuction(BidRequest bidRequest) {

        List<BidResponse> bids = bidsService.getBids(bidRequest);

        return chooseBidByStrategy(bidRequest.getBidStrategy(),bids);

    }


    private BidResponse chooseBidByStrategy(BidStrategy strategy, List<BidResponse> bidResponses) {

        log.info("Going inside findBidByStrategy with list {}", bidResponses);

        if (bidResponses.size() == 0)
            return new BidResponse(null, null, null);

        if (bidResponses.size() == 1)
            return bidResponses.get(0);

        List<BidResponse> sortedBidResponseList = bidResponses.stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(BidResponse::getBidValue)))
                .collect(Collectors.toList());

        BidResponse answer = Objects.equals(strategy, FIRST_STRATEGY) ?
                sortedBidResponseList.get(0) :
                sortedBidResponseList.get(1);

        log.info("what has been returned from the service to the controller is {}", answer);
        return answer;

    }
}
