package com.example.Auction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidResponse {

    private Double bidValue;
    private String selectedCompany;
    private Timestamp timestamp;

}
