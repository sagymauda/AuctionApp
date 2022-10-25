package com.example.Auction.service;

import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
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

    @Value("${url}")
    private Integer url;

    @Value("#{'${arrayOfStrings}'.split(',')}")
    private List<String> companies;

    public BidsService() {
    }

    public List<BidResponse> getBids(BidRequest bidRequest) {

        List<CompletableFuture<BidResponse>> companiesBids =
                companies.stream()
                        .map(company -> getBid(bidRequest, url + "{" + company + "}"))
                        .collect(Collectors.toList());

        return retrieveFutureBidsFromApis(companiesBids);
    }

    private CompletableFuture<BidResponse> getBid(BidRequest bidRequest, String url) {

        log.warn("- - - - - Employees Service async call - - - - -");
        return CompletableFuture.supplyAsync(() -> companyApi(url, bidRequest))
                .orTimeout(timeout, TimeUnit.SECONDS)
                .exceptionally(e -> {
                    log.error("Failed to call api for site {}", url);
                    return null;
                });
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

    private List<BidResponse> retrieveFutureBidsFromApis(List<CompletableFuture<BidResponse>> listOfCompletableFutures) {

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(listOfCompletableFutures.toArray(new CompletableFuture[0]));

        //after the completion of execution for all threads, collect all the return values from all the threads.
        List<BidResponse> allCompletableFuture = allFutures.thenApply(
                future -> {
                    return listOfCompletableFutures.stream()
                            .map(CompletableFuture::join)
                            .filter(bidResponse -> bidResponse.getBidValue() != null)
                            .collect(Collectors.toList());
                }).join();

        log.info("The List After Taking down NOnNull is {}", allCompletableFuture);
        return allCompletableFuture;
    }
}