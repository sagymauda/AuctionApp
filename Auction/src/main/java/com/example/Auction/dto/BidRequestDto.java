package com.example.Auction.dto;

import com.example.Auction.model.BidStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidRequestDto {

    private String userName;
    private String address;
    private BidStrategy bidStrategy;
}
