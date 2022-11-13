package com.example.Auction.service;

import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
import com.example.Auction.model.DiffBidResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BidsService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${timeout}")
    private Integer timeout;

    @Value("#{'${urls}'.split(',')}")
    private List<String> urls;


    public List<BidResponse> getBids(BidRequest bidRequest) {


        List<CompletableFuture<BidResponse>> companiesBids =
                urls.stream()
                        .map(url -> CompletableFuture.supplyAsync(() -> companyApi(url, bidRequest))
                                .orTimeout(timeout, TimeUnit.SECONDS)
                                .exceptionally(e -> {
                                    log.error("Failed to call api for site {}", url);
                                    return null;
                                }))
                        .collect(Collectors.toList());

        return CompletableFuture.allOf(companiesBids.toArray(new CompletableFuture[0]))
                .thenApply(
                        future -> {

                            return companiesBids.stream()
                                    .map(CompletableFuture::join)
                                    .filter(bidResponse -> bidResponse.getBidValue() != null)
                                    .collect(Collectors.toList());
                        }).join();
    }

    private BidResponse companyApi(String url, BidRequest bidRequest) {
        BidResponse response = new BidResponse();
        try {
            response = restTemplate.postForObject(url, bidRequest, BidResponse.class);

        } catch (Exception ex) {

            log.error("error from the service {} ", ex.getMessage());
        }
        return response;
    }

}