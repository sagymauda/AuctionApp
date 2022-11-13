package com.example.Auction.mapper;

import com.example.Auction.dto.BidRequestDto;
import com.example.Auction.dto.BidResponseDto;
import com.example.Auction.model.BidRequest;
import com.example.Auction.model.BidResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BidsMapper {

    @Autowired
    private ModelMapper modelMapper;

    public BidRequestDto BidRequestToDto(BidRequest bidRequest) {
        return modelMapper.map(bidRequest, BidRequestDto.class);
    }

    public BidRequest DtoToBidRequest(BidRequestDto bidRequestDto) {
        return modelMapper.map(bidRequestDto, BidRequest.class);
    }

    public BidResponse DtoToBidResponse(BidResponseDto bidResponseDto) {
        return modelMapper.map(bidResponseDto, BidResponse.class);
    }

    public BidResponseDto BidResponseToDto(BidResponse bidResponse) {
        return modelMapper.map(bidResponse, BidResponseDto.class);
    }

}
