package com.example.demo1.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo1.io.entity.*;
import com.example.demo1.Shared.Utility;
import com.example.demo1.Shared.dto.AddressDto;
import com.example.demo1.Shared.dto.UserDto;
import com.example.demo1.exceptions.UserServiceException;
import com.example.demo1.repositery.UserRepositry;
import com.example.demo1.service.UserServices;
import com.example.demo1.ui.model.Response.ErrorMessages;

@Service
public class userServiceImpl implements UserServices {
	@Autowired
	private UserRepositry userrepo;
	@Autowired
	private Utility utilityObj;
	@Autowired
	private BCryptPasswordEncoder encryptPassword;

	@Override
	public UserDto createUser(UserDto userdto) {
		if (userdto.getEmail() == null || userdto.getFirstName() == null || userdto.getPassword() == null
				|| userdto.getLastName() == null)
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrormessages());

		if (userrepo.findByEmail(userdto.getEmail()) != null)
			throw new RuntimeException("User Already Exists");
		//UserEntity userentity = new UserEntity();
		for(int i=0;i<userdto.getAddresses().size();i++)
		{
			AddressDto address= userdto.getAddresses().get(i);
			address.setUserDetails(userdto);
			address.setAddressId(utilityObj.addressIdGenrator());
			userdto.getAddresses().set(i, address);
		}
		ModelMapper modelmapperObject = new ModelMapper();
		
		//UserEntity userentity = new UserEntity();
		// BeanUtils.copyProperties(userdto, userentity);
	
		UserEntity userentity = modelmapperObject.map(userdto, UserEntity.class);
		//AddressDto ad= userdto.getAdresses().get(1);
         //  System.out.println(ad.getAddressId());
		
		String uid = utilityObj.userIdGenrator();
		userentity.setuserId(uid);
		userentity.setEncryptedPassword(encryptPassword.encode(userdto.getPassword()));
		///the below code is used to extract the adress individually from the dto and set them back to the entity
		/*List<AddressEntity> userDummy= new ArrayList<AddressEntity>();
		for(int i=0;i<userdto.getAddresses().size();i++)
		{
			AddressEntity adressEntity= modelmapperObject.map(userdto.getAddresses().get(i),AddressEntity.class);
			adressEntity.setUserDetails(userentity);
		userDummy.add(adressEntity);
		}
		userentity.setAddresses(userDummy);*/
		UserEntity storedDetails = userrepo.save(userentity);
		UserDto returnValue = modelmapperObject.map(storedDetails, UserDto.class);
		 //BeanUtils.copyProperties(storedDetails, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userentity = userrepo.findByEmail(username);
		if (userentity == null)
			throw new UsernameNotFoundException(username);

		return new User(userentity.getEmail(), userentity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userentity = userrepo.findByEmail(email);
		if (userentity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userentity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userid) {
		UserDto userdto = new UserDto();
		UserEntity userentity = userrepo.findByUserId(userid);
		if (userentity == null)
			throw new UsernameNotFoundException(userid);
		BeanUtils.copyProperties(userentity, userdto);
		return userdto;
	}

	@Override
	public UserDto updateUser(String userid, UserDto dto) {
		UserDto userdto = new UserDto();
		UserEntity userentity = userrepo.findByUserId(userid);
		if (userentity == null)
			throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrormessages());
		userentity.setFirstName(dto.getFirstName());
		userentity.setLastName(dto.getLastName());
		UserEntity updatedUserEntity = userrepo.save(userentity);
		BeanUtils.copyProperties(updatedUserEntity, userdto);
		return userdto;
	}

	@Override
	public void DeleteUser(String userid) {
		UserEntity userentity = userrepo.findByUserId(userid);
		if (userentity == null)
			throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrormessages());
		userrepo.delete(userentity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<UserDto>();
		Pageable peagabaleObject = PageRequest.of(page, limit);
		Page<UserEntity> userPage = userrepo.findAll(peagabaleObject);
		List<UserEntity> users = userPage.getContent();
		for (UserEntity userentity : users) {
			UserDto userdto = new UserDto();
			BeanUtils.copyProperties(userentity, userdto);
			returnValue.add(userdto);
		}
		return returnValue;
	}
	

}
