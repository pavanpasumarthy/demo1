package com.example.demo1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo1.Shared.dto.AddressDto;

@Service
public interface AddressesService {
	public List<AddressDto> getAddresses(String id);

	AddressDto getAddress(String addressId);
}
