package com.example.Auction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiffBidResponse {

    private String companyName;
    private String CompanyCountry;
    private Double bidValue;

}