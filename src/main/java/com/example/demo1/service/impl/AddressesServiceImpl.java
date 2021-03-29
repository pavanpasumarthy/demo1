package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.Shared.dto.AddressDto;
import com.example.demo1.io.entity.AddressEntity;
import com.example.demo1.io.entity.UserEntity;
import com.example.demo1.repositery.AddressesRepositry;
import com.example.demo1.repositery.UserRepositry;
import com.example.demo1.service.AddressesService;

@Service
public class AddressesServiceImpl implements AddressesService {

	@Autowired
	UserRepositry userRepositry;
	@Autowired
	AddressesRepositry addressesRepositry;

	@Override
	public List<AddressDto> getAddresses(String id) {
		// TODO Auto-generated method stub
		List<AddressDto> returnList = new ArrayList<AddressDto>();
		UserEntity userEntityObject = userRepositry.findByUserId(id);
		if (userEntityObject == null)
			return returnList;
		Iterable<AddressEntity> addresses = addressesRepositry.findAllByUserDetails(userEntityObject);
		ModelMapper mapper = new ModelMapper();
		for (AddressEntity address : addresses) {

			returnList.add(mapper.map(address, AddressDto.class));
		}

		return returnList;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		AddressDto returnValue = null;

		AddressEntity addressEntity = addressesRepositry.findByAddressId(addressId);

		if (addressEntity != null) {
			returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
		}

		return returnValue;
	}

}
