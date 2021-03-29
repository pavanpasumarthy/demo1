package com.example.demo1.ui.Controller;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.Shared.dto.AddressDto;
import com.example.demo1.Shared.dto.UserDto;
import com.example.demo1.service.AddressesService;
import com.example.demo1.service.UserServices;
import com.example.demo1.ui.model.Response.UserRep;
import com.example.demo1.ui.model.Response.AddressResponse;
import com.example.demo1.ui.model.Response.OperationalStatusModel;
import com.example.demo1.ui.model.userRequests.UserDetailsRequestModel;

@RestController
@RequestMapping("/user") // mapping the resources
public class UserController {
	@Autowired
	UserServices userService;
	@Autowired
	AddressesService addresssesService;
	ModelMapper modelMapper = new ModelMapper();
	// @GetMapping("getDetails/{id}")
	// which accepts data in xml format or json format

	@GetMapping(path = "getDetails/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public UserRep getUsers(@PathVariable String id) {
		UserRep userResponse = new UserRep();
		UserDto dtoObj = userService.getUserByUserId(id);
		BeanUtils.copyProperties(dtoObj, userResponse);
		return userResponse;
	}

	// @PostMapping("createUser")
	@PostMapping(path = "createUser", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public UserRep createUser(@RequestBody UserDetailsRequestModel us) throws Exception {
		UserRep userResponse = new UserRep();
		// UserDto dtoObj = new UserDto();
		// BeanUtils.copyProperties(us, dtoObj);

		UserDto dtoObj = modelMapper.map(us, UserDto.class);
		UserDto createUser = userService.createUser(dtoObj);
		// BeanUtils.copyProperties(createUser, userResponse);
		userResponse = modelMapper.map(createUser, UserRep.class);
		return userResponse;
	}

	@PutMapping(path = "UpdateUser/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public UserRep updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
		UserRep userResponse = new UserRep();
		UserDto dtoObj = new UserDto();
		BeanUtils.copyProperties(userDetailsRequestModel, dtoObj);
		UserDto updateUser = userService.updateUser(id, dtoObj);
		BeanUtils.copyProperties(updateUser, userResponse);
		return userResponse;
	}

	@DeleteMapping(path = "deleteUser/{userId}")
	public OperationalStatusModel deleteUser(@PathVariable String userId) {
		OperationalStatusModel returnValue = new OperationalStatusModel();
		returnValue.setOperationName("Delete");
		userService.DeleteUser(userId);
		returnValue.setOperationStatus("Sucess");
		return returnValue;
	}

	@GetMapping
	public List<UserRep> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "2") int limit) {
		List<UserRep> returnValue = new ArrayList<UserRep>();
		List<UserDto> users = userService.getUsers(page, limit);
		for (UserDto userdto : users) {
			UserRep usermodel = new UserRep();
			BeanUtils.copyProperties(userdto, usermodel);
			returnValue.add(usermodel);
		}
		return returnValue;
	}

	@GetMapping(path = "addresses/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public List<AddressResponse> getAddresses(@PathVariable String id) {
		List<AddressResponse> addressesResponse = new ArrayList<>();

		List<AddressDto> adressesDto = addresssesService.getAddresses(id);
		Type listType = new TypeToken<List<AddressResponse>>() {
		}.getType();
		addressesResponse = modelMapper.map(adressesDto, listType);
		return addressesResponse;

	}
	@GetMapping(path = "/{userId}/addresses/{addressId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public AddressResponse getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

		AddressDto addressesDto = addresssesService.getAddress(addressId);
           return modelMapper.map(addressesDto, AddressResponse.class);
	}
}
