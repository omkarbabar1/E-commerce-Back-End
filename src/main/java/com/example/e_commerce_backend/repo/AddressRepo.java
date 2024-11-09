package com.example.e_commerce_backend.repo;

import com.example.e_commerce_backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
