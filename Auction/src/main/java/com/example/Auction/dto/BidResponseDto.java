package com.example.Auction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidResponseDto {

    private Double bidValue;
    private String selectedCompany;
    private Timestamp timestamp;
    }

