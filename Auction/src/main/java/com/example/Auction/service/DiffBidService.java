package com.example.Auction.service;

import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
import com.example.Auction.model.DiffBidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DiffBidService {

    @Autowired
    private ApiRequestService apiRequestService;

    @Value("${timeout}")
    private Integer timeout;

    @Value("#{'${urls}'.split(',')}")
    private List<String> urls;

    public List<Double> getBidsWithDifferentPayloads(BidRequest bidRequest) {
        CompletableFuture<BidResponse> bidResponse1
                = CompletableFuture.supplyAsync(() -> apiRequestService.companyApi(urls.get(0), bidRequest))
                .orTimeout(timeout, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.error("Failed to call api for site {}", urls.get(0));
                    return new BidResponse(0.0, Timestamp.from(Instant.now()),bidRequest.getBidStrategy()) ;
                });

        CompletableFuture<BidResponse> bidResponse2
                = CompletableFuture.supplyAsync(() -> apiRequestService.companyApi(urls.get(1), bidRequest))
                .orTimeout(timeout, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.error("Failed to call api for site {}", urls.get(1));
                    return new BidResponse(0.0,Timestamp.from(Instant.now()),bidRequest.getBidStrategy()) ;
                });

        CompletableFuture<DiffBidResponse> diffBidResponse
                = CompletableFuture.supplyAsync(() -> apiRequestService.diffCompanyApi(urls.get(2), bidRequest))
                .orTimeout(timeout, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.error("Failed to call api for site {}", urls.get(2));
                    return new DiffBidResponse("no company","none",0.0);
                });

        List<Double> joinedAnswers = CompletableFuture.allOf(bidResponse1, bidResponse2, diffBidResponse)
                .thenApply(combine -> {
                    List<Double> answer = new ArrayList<>();
                    BidResponse bid1 = bidResponse1.join();
                    BidResponse bid2 = bidResponse2.join();
                    DiffBidResponse diffBid = diffBidResponse.join();
                    answer.add(bid1.getBidValue());
                    answer.add(bid2.getBidValue());
                    answer.add(diffBid.getBidValue());

                    return answer;
                }).join();
        return joinedAnswers;
    }
//       final CompletableFuture<List<Double>> bidsList = completableFutureAllOf.thenApply(()->new ArrayList<Double>
//               (bidResponse1.join().getBidValue(),bidResponse2.join().getBidValue(), diffBidResponse.join().getBidValue()));


//                .thenAccept(combine->{
////                    List<Double> combined = Stream.of(bidResponse1, bidResponse2, diffBidResponse)
////                            .map(CompletableFuture::join)
////                            .filter()
////                            .collect(Collectors.toList());
//
//                    BidResponse bid1 = bidResponse1.join();
//                    BidResponse bid2 = bidResponse2.join();
//                    DiffBidResponse diffBid = diffBidResponse.join();
//
//
//                }).join();






}
