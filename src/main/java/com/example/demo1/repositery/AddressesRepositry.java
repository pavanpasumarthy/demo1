package com.example.demo1.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo1.io.entity.*;
@Repository
public interface AddressesRepositry extends JpaRepository<AddressEntity, Long>{
List<AddressEntity> findAllByUserDetails(UserEntity userentity);
AddressEntity findByAddressId(String addressId);
}
