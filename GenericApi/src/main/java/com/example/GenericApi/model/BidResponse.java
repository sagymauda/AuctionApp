package com.example.GenericApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidResponse {

    private Double bidValue;
    private String  companyName;;
    private Timestamp timestamp;
}
