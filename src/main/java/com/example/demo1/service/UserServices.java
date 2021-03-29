package com.example.demo1.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.demo1.Shared.dto.UserDto;
@Service
public interface UserServices extends UserDetailsService {
  UserDto createUser(UserDto user);
  UserDto getUser(String email);
  UserDto getUserByUserId(String userid);
  UserDto updateUser(String userid,UserDto dto);
  void DeleteUser(String id);
  List<UserDto> getUsers(int page,int limit);
}
