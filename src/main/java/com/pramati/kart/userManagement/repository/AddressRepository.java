package com.pramati.kart.userManagement.repository;

import com.pramati.kart.userManagement.entity.Address;
import com.pramati.kart.userManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
