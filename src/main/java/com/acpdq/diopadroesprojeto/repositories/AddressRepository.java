package com.acpdq.diopadroesprojeto.repositories;

import com.acpdq.diopadroesprojeto.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {
}
