package com.example.Auction.model;

import lombok.Data;

@Data
public class BidRequest {

    private String userName;
    private String address;
    private BidStrategy bidStrategy;

}
